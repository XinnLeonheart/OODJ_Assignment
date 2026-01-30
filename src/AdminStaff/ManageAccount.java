/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author lolipop
 */

import Navigation.NavigateToDashboard;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageAccount extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ManageAccount.class.getName());

    /**
     * Creates new form ManageAccount
     */
    private static final String ACCOUNT_FILE = "src/TextFiles/Account.txt";
    
    public ManageAccount() {
        initComponents();
        tfAccountID.setEditable(false);
        tfEmailDomain.setEditable(false);
        autoGenerateAccID();
        loadAccountTable();
        
        tableAccountDetail.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
                getAccountInformation();
            }
        });
        
        tableAccountDetail.getColumnModel()
        .getColumn(9)
        .setCellRenderer(new ButtonRenderer());

        tableAccountDetail.getColumnModel()
                .getColumn(9)
                .setCellEditor(new ButtonEditor(new javax.swing.JCheckBox(), tableAccountDetail));
        
        cbRole.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                updateAgeBasedOnRole();
            }
        });
        updateAgeBasedOnRole();

    }
    
    public void searchAccount(){
        String searchText = tfSearch.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) tableAccountDetail.getModel();
        model.setRowCount(0);
        String searchAcc = tfSearch.getText().trim();
            
        if (searchText.isEmpty()) {
            tfSearch.setText("");
            JOptionPane.showMessageDialog(this, "Please enter Account ID or Name to search.");
            loadAccountTable();
            return;
        }    

        try (BufferedReader br = new BufferedReader (new FileReader(ACCOUNT_FILE))){
            String line;

            while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] data = line.split(";");

            String accID = data[0].toLowerCase();
            String username = data[1].toLowerCase();

            if (accID.contains(searchText) || username.contains(searchText)) {
                model.addRow(new Object[]{
                        data[0], data[1], data[2], data[3], data[4],
                        data[5], data[6], data[7], data[8], "Delete"
                });
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No matching account found.");
        }                       
        } catch (IOException e){
            JOptionPane.showMessageDialog(null, "Error reading file: " + e.getMessage());           
        }
    }   
    
    // Helper method to check if the search text follows the word order in the searchable text
    private boolean isOrderedMatch(String searchText, String searchableText) {
        String[] searchKeywords = searchText.split("\\s+");
        String[] searchableKeywords = searchableText.split("\\s+");

        int searchIndex = 0;
        for (String searchableKeyword : searchableKeywords) {
            if (searchableKeyword.equals(searchKeywords[searchIndex])) {
                searchIndex++;
                if (searchIndex == searchKeywords.length) {
                    return true;
                }
            }
        }
        return false;
    }  
    
    public void writeAccInfoIntoDatabase() throws IOException{
        try (FileWriter fw = new FileWriter(ACCOUNT_FILE, true)){
             fw.write(
                    tfAccountID.getText().trim() + ";" +
                    tfUserName.getText().trim() + ";" +
                    tfName.getText().trim() + ";" +
                    tfEmail.getText().trim() + tfEmailDomain.getText().trim() + ";" + 
                    tfPassword.getText().trim() + ";" + 
                    tfPhoneNum.getText().trim() + ";" +
                    cbGender.getSelectedItem().toString().trim() + ";" +
                    cbAge.getSelectedItem().toString().trim() + ";" +
                    cbRole.getSelectedItem().toString().trim() + ";" + 
                    "\n"
                );    
        
        fw.close();
        }
       
    }
    
    public void editAccInfoInDatabase() {
        String selectedAccID = tfAccountID.getText().trim();

        try {
            // 1. Read all lines
            BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE));
            java.util.ArrayList<String> lines = new java.util.ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
            br.close();

            // 2. Rewrite file with updated data
            try (FileWriter fw = new FileWriter(ACCOUNT_FILE)) {
                for (String existingLine : lines) {
                    String[] data = existingLine.split(";");

                    // Match by Account ID
                    if (data[0].trim().equals(selectedAccID)) {
                        fw.write(
                            tfAccountID.getText().trim() + ";" +
                            tfUserName.getText().trim() + ";" +
                            tfName.getText().trim() + ";" +
                            tfEmail.getText().trim() + tfEmailDomain.getText().trim() + ";" +
                            tfPassword.getText().trim() + ";" +
                            tfPhoneNum.getText().trim() + ";" +
                            cbGender.getSelectedItem().toString().trim() + ";" +
                            cbAge.getSelectedItem().toString().trim() + ";" +
                            cbRole.getSelectedItem().toString().trim() + ";\n"
                        );
                    } else {
                        fw.write(existingLine + "\n");
                    }
                }
            }

            loadAccountTable();
            clearAllTextFields();
            autoGenerateAccID();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error editing account:\n" + e.getMessage());
        }
    }

    public boolean makeSureTfAreFilled(){
       if (
                (tfAccountID == null || tfAccountID.getText().trim().isEmpty()) ||
                (tfUserName == null || tfUserName.getText().trim().isEmpty()) ||
                (tfName == null || tfName.getText().trim().isEmpty()) ||
                (tfEmail == null || tfEmail.getText().trim().isEmpty()) ||
                (tfPassword == null || tfPassword.getText().trim().isEmpty()) ||
                (tfPhoneNum == null || tfPhoneNum.getText().trim().isEmpty()) ||
                (cbGender == null || cbGender.getSelectedItem() == null || cbGender.getSelectedItem().toString().trim().isEmpty()) ||
                (cbAge == null || cbAge.getSelectedItem() == null || cbAge.getSelectedItem().toString().trim().isEmpty()) ||
                (cbRole == null || cbRole.getSelectedItem() == null || cbRole.getSelectedItem().toString().trim().isEmpty())) {

                JOptionPane.showMessageDialog(
                this,
                "You must fill in all the fields!",
                "Missing Information",
                JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }     
        
    
    public boolean makeSureNameNotContainNumber() {
        String name = tfName.getText().trim();

        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(
                this,
                "Name can only contain letters!",
                "Invalid Name",
                JOptionPane.ERROR_MESSAGE
            );
            tfName.requestFocus();
            return false;
        }
        return true;
    }

    
    public boolean makeSurePhoneNoIsValid() {
        String phoneNum = tfPhoneNum.getText().trim();

        // 1. Verify Number is Numeric
        if (!phoneNum.matches("\\d+")) {
            JOptionPane.showMessageDialog(
                this,
                "Phone number must contain digits only.",
                "Invalid Phone Number",
                JOptionPane.ERROR_MESSAGE
            );
            tfPhoneNum.requestFocus();
            return false;
        }

        // 2. Must start with 00 to represent +
        if (!phoneNum.startsWith("00")) {
            JOptionPane.showMessageDialog(
                this,
                "Phone number must start with 00 to represent +.\nExample: 0060123456789",
                "Invalid Phone Number",
                JOptionPane.ERROR_MESSAGE
            );
            tfPhoneNum.requestFocus();
            return false;
        }

        // 3. Length check
        if (phoneNum.length() < 10 || phoneNum.length() > 15) {
            JOptionPane.showMessageDialog(
                this,
                "Phone number length is invalid.\n"
              + "Please enter a valid international phone number.",
                "Invalid Phone Number",
                JOptionPane.ERROR_MESSAGE
            );
            tfPhoneNum.requestFocus();
            return false;
        }

        // Valid international phone number
        return true;
    }
    
    public boolean makeSuretfEmailNotContainDomain(){
        String registerEmail = tfEmail.getText().trim();
        
        if (registerEmail.contains("@")){
            JOptionPane.showMessageDialog(null,
                    "Please DO NOT include an email domain!\n"
                    + "Example: 'tp123456' instead of 'tp123456@mail.uni.edu.my'",
                    "Invalid Email Format",
                    JOptionPane.ERROR_MESSAGE);
            tfEmail.requestFocus();  
            return false;
        }        
        return true;
    }
    
    public boolean makeSureAccIDNotSame() {
        String newAccID = tfAccountID.getText().trim();

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                String existingAccID = data[0].trim();

                if (existingAccID.equalsIgnoreCase(newAccID)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Account ID already exists!",
                        "Duplicate Account ID",
                        JOptionPane.ERROR_MESSAGE
                    );
                    autoGenerateAccID();
                    return false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking Account ID");
            return false;
        }
        return true;
    }
    
    public boolean makeSureUsernameNotSame() {
        String newUsername = tfUserName.getText().trim();

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                String existingUsername = data[1].trim();

                if (existingUsername.equalsIgnoreCase(newUsername)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Username already exists!\nPlease choose another username.",
                        "Duplicate Username",
                        JOptionPane.ERROR_MESSAGE
                    );
                    tfUserName.setText("");
                    tfUserName.requestFocus();
                    return false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking username");
            return false;
        }
        return true;
    }
        
    public boolean makeSureEmailNotSame() {
        String newEmail = tfEmail.getText().trim() + tfEmailDomain.getText().trim();

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                String existingEmail = data[3].trim();

                if (existingEmail.equalsIgnoreCase(newEmail)) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Email already exists!\nPlease use another email.",
                        "Duplicate Email",
                        JOptionPane.ERROR_MESSAGE
                    );
                    tfEmail.setText("");
                    tfEmail.requestFocus();
                    return false;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error checking email");
            return false;
        }
        return true;
    }
    
    public void autoGenerateAccID() {

        String prefix = "ACC";
        int nextID = 1;

        File file = new File(ACCOUNT_FILE);

        // If file does not exist or empty → start from ACC001
        if (!file.exists() || file.length() == 0) {
            tfAccountID.setText(prefix + "001");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String lastID = null;

            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(";");
                    lastID = data[0].trim(); 
                }
            }

            if (lastID != null && lastID.startsWith(prefix)) {
                int number = Integer.parseInt(lastID.substring(3));
                nextID = number + 1;
            }

            tfAccountID.setText(prefix + String.format("%03d", nextID));

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                "Error generating Account ID!");
        }
    }
    
    public void createAccount() throws IOException{       
        if (!makeSureTfAreFilled()) return;
        if (!makeSureNameNotContainNumber()) return;
        if (!makeSurePhoneNoIsValid()) return;
        if (!makeSuretfEmailNotContainDomain()) return;
        if (!makeSureAccIDNotSame()) return;
        if (!makeSureUsernameNotSame()) return;
        if (!makeSureEmailNotSame()) return;
        
        // Double confirmation to create the account
        String confirmAccountInformation = buildConfirmAccountInformation();
        
        int result = JOptionPane.showConfirmDialog(this,
                confirmAccountInformation,
                "Confirm Create Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        // Once user choose "YES", the account will be created
        if (result == JOptionPane.YES_OPTION){
            writeAccInfoIntoDatabase();
            loadAccountTable();
            JOptionPane.showMessageDialog(null, "Account has been created successfully!");
            clearAllTextFields();
            autoGenerateAccID();
        }
        
        else{
            JOptionPane.showMessageDialog(null, "Account create action has been cancelled");
            return;  
        }
    }
        
    private String buildConfirmAccountInformation() {
        return
            "Please confirm the following account details:\n\n"
          + "Account ID   : " + tfAccountID.getText() + "\n"
          + "Username     : " + tfUserName.getText() + "\n"
          + "Name         : " + tfName.getText() + "\n"
          + "Email        : " + tfEmail.getText() + tfEmailDomain.getText() + "\n"
          + "Password     : " + tfPassword.getText() + "\n"
          + "Phone No.    : " + tfPhoneNum.getText() + "\n"
          + "Gender       : " + cbGender.getSelectedItem() + "\n"
          + "Age          : " + cbAge.getSelectedItem() + "\n"
          + "Role         : " + cbRole.getSelectedItem() + "\n";
    }

    public void deleteAccount(String accID) throws IOException{
        // Double confirmation to delete the account
        int result = JOptionPane.showConfirmDialog(null,
                "Are you sure to delete this account?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (result != JOptionPane.YES_OPTION) {
        JOptionPane.showMessageDialog(null, "Account delete action has been cancelled");
        return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE));
            java.util.ArrayList<String> lines = new java.util.ArrayList<>();
            String line;

            while ((line = br.readLine()) != null) {
                if (!line.startsWith(accID + ";")) {
                    lines.add(line);
                }
            }
            br.close();

            try (FileWriter fw = new FileWriter(ACCOUNT_FILE)) {
                for (String l : lines) {
                    fw.write(l + "\n");
                }
            }

            loadAccountTable();
            clearAllTextFields();

            JOptionPane.showMessageDialog(this, "Account deleted successfully!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting account: " + e.getMessage());
        }
    }
    
    public void editAccount()throws IOException {
        int selectedRow = tableAccountDetail.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No record selected for editing!");
                return;
            }        
        
            if (!isDataChanged(selectedRow)) {
            JOptionPane.showMessageDialog(this,
                    "No changes detected.\nPlease modify at least one field before editing.",
                    "No Changes",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
            
        if (!makeSureTfAreFilled()) return;
        if (!makeSureNameNotContainNumber()) return;
        if (!makeSurePhoneNoIsValid()) return;
        if (!makeSuretfEmailNotContainDomain()) return;
        if (!makeSureUsernameNotSameOnEdit()) return;
        if (!makeSureEmailNotSameOnEdit()) return;       

        // Double confirmation to edit the account information
        String confirmAccountInformation = buildConfirmAccountInformation();
        
        int result = JOptionPane.showConfirmDialog(null,
                confirmAccountInformation,
                "Confirm Edit Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION){
            editAccInfoInDatabase();
            JOptionPane.showMessageDialog(this, "Account information has been edited successfully!");
        }
        else{
                JOptionPane.showMessageDialog(this, "Account information edit action has been cancelled");  
        }
                    
    }
    
    public boolean isDataChanged(int selectedRow) {

        String originalAccID   = tableAccountDetail.getValueAt(selectedRow, 0).toString();
        String originalUsername= tableAccountDetail.getValueAt(selectedRow, 1).toString();
        String originalName    = tableAccountDetail.getValueAt(selectedRow, 2).toString();
        String originalEmail   = tableAccountDetail.getValueAt(selectedRow, 3).toString();
        String originalPassword= tableAccountDetail.getValueAt(selectedRow, 4).toString();
        String originalPhone   = tableAccountDetail.getValueAt(selectedRow, 5).toString();
        String originalGender  = tableAccountDetail.getValueAt(selectedRow, 6).toString();
        String originalAge     = tableAccountDetail.getValueAt(selectedRow, 7).toString();
        String originalRole    = tableAccountDetail.getValueAt(selectedRow, 8).toString();

        String newEmail = tfEmail.getText().trim() + tfEmailDomain.getText().trim();

        return !(
            originalAccID.equals(tfAccountID.getText().trim()) &&
            originalUsername.equals(tfUserName.getText().trim()) &&
            originalName.equals(tfName.getText().trim()) &&
            originalEmail.equals(newEmail) &&
            originalPassword.equals(tfPassword.getText().trim()) &&
            originalPhone.equals(tfPhoneNum.getText().trim()) &&
            originalGender.equals(cbGender.getSelectedItem().toString()) &&
            originalAge.equals(cbAge.getSelectedItem().toString()) &&
            originalRole.equals(cbRole.getSelectedItem().toString())
        );
    }
    
    public boolean makeSureUsernameNotSameOnEdit() {
        String newUsername = tfUserName.getText().trim();
        String currentAccID = tfAccountID.getText().trim();

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                String accID = data[0].trim();
                String existingUsername = data[1].trim();

                if (!accID.equals(currentAccID) &&
                    existingUsername.equalsIgnoreCase(newUsername)) {

                    JOptionPane.showMessageDialog(this,
                            "Username already exists!",
                            "Duplicate Username",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    
    public boolean makeSureEmailNotSameOnEdit() {
        String newEmail = tfEmail.getText().trim() + tfEmailDomain.getText().trim();
        String currentAccID = tfAccountID.getText().trim();

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                String accID = data[0].trim();
                String existingEmail = data[3].trim();

                if (!accID.equals(currentAccID) &&
                    existingEmail.equalsIgnoreCase(newEmail)) {

                    JOptionPane.showMessageDialog(this,
                            "Email already exists!",
                            "Duplicate Email",
                            JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    
    public void getAccountInformation(){
        int selectedRow = tableAccountDetail.getSelectedRow();
        
        if (selectedRow != -1){
            btnCreate.setEnabled(false);
            tfAccountID.setText((String) tableAccountDetail.getValueAt(selectedRow, 0));
            
            String accID = (String) tableAccountDetail.getValueAt(selectedRow, 0);
            String userName = (String) tableAccountDetail.getValueAt(selectedRow, 1);
            String name = (String) tableAccountDetail.getValueAt(selectedRow, 2);
            String email = (String) tableAccountDetail.getValueAt(selectedRow, 3);
            String password = (String) tableAccountDetail.getValueAt(selectedRow, 4);
            String phoneNum = (String) tableAccountDetail.getValueAt(selectedRow, 5);
            String gender = (String) tableAccountDetail.getValueAt(selectedRow, 6);
            String age = (String) tableAccountDetail.getValueAt(selectedRow, 7);
            String accRole = (String) tableAccountDetail.getValueAt(selectedRow, 8);
            
            tfAccountID.setText(accID);
            tfUserName.setText(userName);
            tfName.setText(name);
            // Split email into ID and domain
            String[] emailParts = email.split("@", 2);
            tfEmail.setText(emailParts[0]);
            tfEmailDomain.setText("@" + emailParts[1]);
            
            tfPassword.setText(password);
            tfPassword.setText(password);
            tfPhoneNum.setText(phoneNum);
            cbGender.setSelectedItem(gender);
            cbAge.setSelectedItem(age);
            cbRole.setSelectedItem(accRole);   
        }
        else {
            btnCreate.setEnabled(true);
            cbRole.setEnabled(true);
            
            clearAllTextFields();
        }
    }
    
    public void clearAllTextFields() {
        tableAccountDetail.clearSelection();

        tfUserName.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfPassword.setText("");
        tfPhoneNum.setText("");
        
        if (cbGender.getItemCount() > 0) {
            cbGender.setSelectedIndex(0);
        }

        if (cbRole.getItemCount() > 0) {
            cbRole.setSelectedIndex(0);
        }

        if (cbAge.getItemCount() > 0) {
            cbAge.setSelectedIndex(0);
        }
        
        tfSearch.setText("");        
    }


    public void loadAccountTable() {
        getAccountInformation();
        DefaultTableModel model =
            (DefaultTableModel) tableAccountDetail.getModel();
        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(";");

                model.addRow(new Object[]{
                    data[0], data[1], data[2], data[3], data[4],
                    data[5], data[6], data[7], data[8], "Delete"
                });
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading accounts");
        }
    }
    
    private void updateAgeBasedOnRole() {
        String selectedRole = cbRole.getSelectedItem().toString();

        cbAge.removeAllItems(); // clear old ages

        int startAge;
        int endAge;

        if (selectedRole.equals("Student")) {
            startAge = 13;
            endAge = 40;
        } else {
            // Academic Leader, Lecturer, Admin Staff
            startAge = 18;
            endAge = 75;
        }

        for (int age = startAge; age <= endAge; age++) {
            cbAge.addItem(String.valueOf(age));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tfPhoneNum = new javax.swing.JTextField();
        tfUserName = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();
        tfPassword = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAccountDetail = new javax.swing.JTable();
        cbGender = new javax.swing.JComboBox<>();
        btnEdit = new javax.swing.JButton();
        btnCreate = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSearchAcc = new javax.swing.JButton();
        tfEmailDomain = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfAccountID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbRole = new javax.swing.JComboBox<>();
        btnBack = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        cbAge = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tfName.addActionListener(this::tfNameActionPerformed);

        jLabel7.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel7.setText("Name");

        jLabel8.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel8.setText("Age");

        jLabel9.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel9.setText("Gender");

        jLabel10.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel10.setText("Search Account (ID/Name):");

        jLabel11.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel11.setText("Phone Number");

        jLabel1.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        jLabel1.setText("Manage Account ");

        tfUserName.addActionListener(this::tfUserNameActionPerformed);

        tfEmail.addActionListener(this::tfEmailActionPerformed);

        jLabel2.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel2.setText("User Name");

        jLabel3.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel3.setText("Email");

        jLabel4.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel4.setText("Password");

        tableAccountDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Account ID", "User Name", "Name", "Email", "Password", "Phone Number", "Gender", "Age", "Role", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableAccountDetail.setColumnSelectionAllowed(true);
        tableAccountDetail.getTableHeader().setReorderingAllowed(false);
        tableAccountDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableAccountDetailMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableAccountDetail);
        tableAccountDetail.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableAccountDetail.getColumnModel().getColumnCount() > 0) {
            tableAccountDetail.getColumnModel().getColumn(0).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(1).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(2).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(3).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(4).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(5).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(6).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(7).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(8).setResizable(false);
            tableAccountDetail.getColumnModel().getColumn(9).setResizable(false);
        }

        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        btnEdit.setText("Edit");
        btnEdit.addActionListener(this::btnEditActionPerformed);

        btnCreate.setText("Create");
        btnCreate.addActionListener(this::btnCreateActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnSearchAcc.setText("Search");
        btnSearchAcc.addActionListener(this::btnSearchAccActionPerformed);

        tfEmailDomain.setText("@mail.uni.edu.my");
        tfEmailDomain.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfEmailDomain.setEnabled(false);
        tfEmailDomain.setFocusable(false);
        tfEmailDomain.addActionListener(this::tfEmailDomainActionPerformed);

        jLabel5.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel5.setText("ID");

        tfAccountID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfAccountID.setEnabled(false);
        tfAccountID.setSelectedTextColor(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel6.setText("Role");

        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin Staff", "Academic Leader", "Lecturer", "Student" }));

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel12.setText("Please enter 00 instead of (+)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(451, 451, 451))
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addGap(36, 36, 36))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addGap(107, 107, 107))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel9)
                                            .addGap(85, 85, 85))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbAge, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfEmailDomain, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnCreate)
                                .addGap(18, 18, 18)
                                .addComponent(btnEdit)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1020, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(17, 17, 17)
                                .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSearchAcc)
                                    .addComponent(btnClear))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(40, 40, 40))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAccountID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tfEmailDomain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCreate)
                    .addComponent(btnClear)
                    .addComponent(jLabel6)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchAcc))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNameActionPerformed

    private void tfUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfUserNameActionPerformed

    private void tfEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            editAccount();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error editing account:\n" + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnSearchAccActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchAccActionPerformed
        searchAccount();
    }//GEN-LAST:event_btnSearchAccActionPerformed

    private void tfEmailDomainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfEmailDomainActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfEmailDomainActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        try {
            createAccount();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Error creating account:\n" + e.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearAllTextFields();
        loadAccountTable();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tableAccountDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAccountDetailMouseClicked

    }//GEN-LAST:event_tableAccountDetailMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        NavigateToDashboard.goToAdminStaffDashboard(this);
    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ManageAccount().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSearchAcc;
    private javax.swing.JComboBox<String> cbAge;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAccountDetail;
    private javax.swing.JTextField tfAccountID;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfEmailDomain;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPassword;
    private javax.swing.JTextField tfPhoneNum;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JTextField tfUserName;
    // End of variables declaration//GEN-END:variables
}

class ButtonRenderer extends javax.swing.JButton
        implements javax.swing.table.TableCellRenderer {

    public ButtonRenderer() {
        setText("Delete");
        setBackground(java.awt.Color.RED);
        setForeground(java.awt.Color.WHITE);
    }

    @Override
    public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        return this;
    }
}

class ButtonEditor extends javax.swing.DefaultCellEditor {

    private final javax.swing.JButton button;
    private String accID;
    private final javax.swing.JTable table;

    public ButtonEditor(javax.swing.JCheckBox checkBox, javax.swing.JTable table) {
        super(checkBox);
        this.table = table;

        button = new javax.swing.JButton("Delete");
        button.setBackground(java.awt.Color.RED);
        button.setForeground(java.awt.Color.WHITE);

        button.addActionListener(e -> {
            fireEditingStopped(); // stop cell editing first

            javax.swing.SwingUtilities.invokeLater(() -> {
                try {
                    ManageAccount parent =
                        (ManageAccount) javax.swing.SwingUtilities
                            .getWindowAncestor(table);
                    parent.deleteAccount(accID);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                        table,
                        "Error deleting account:\n" + ex.getMessage()
                    );
                }
            });
        });
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(
            javax.swing.JTable table, Object value,
            boolean isSelected, int row, int column) {

        accID = table.getValueAt(row, 0).toString();
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return "Delete"; 
    }
}
