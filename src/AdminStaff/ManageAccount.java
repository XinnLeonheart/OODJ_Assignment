/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author lolipop
 */

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
        tableAccountDetail.getColumnModel()
        .getColumn(9)
        .setCellRenderer(new ButtonRenderer());

        tableAccountDetail.getColumnModel()
                .getColumn(9)
                .setCellEditor(new ButtonEditor(new javax.swing.JCheckBox(), tableAccountDetail));


    }
    
    public void searchAccount(){
        String searchText = tfSearch.getText().trim();
        DefaultTableModel model = (DefaultTableModel) tableAccountDetail.getModel();
        model.setRowCount(0);
        String searchAcc = tfSearch.getText().trim();
            
        if (searchAcc.isEmpty()) {
                tfSearch.setText("");
                JOptionPane.showMessageDialog(null, "Please enter Account ID or Name to search.");
                return;
        }    

        try (BufferedReader br = new BufferedReader (new FileReader(ACCOUNT_FILE))){
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                String accID = data[0].trim();
                String userName = data[1].trim();
                String name = data[2].trim();
                String email = data[3].trim();
                String password = data[4].trim();
                String phoneNum = data[5].trim();
                String gender = data[6].trim();
                String age = data[7].trim();
                String accRole = data[8].trim();

                String searchableText = accID + " " + userName;

                // Check if the search text matches the order in the searchable text
                if (isOrderedMatch(searchText.toLowerCase(), searchableText.toLowerCase())) {
                    // Add the matching record to the table
                    model.addRow(new Object[]{
                        accID,
                        userName,
                        name,
                        email,
                        password,
                        phoneNum,
                        gender,
                        age,
                        accRole,
                        "Delete"});
                }          
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
                    tfAge.getText().trim() + ";" +
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
                            tfAge.getText().trim() + ";" +
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
                (tfAge == null || tfAge.getText().trim().isEmpty()) ||
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

    
    public boolean makeSurePhoneNoIsNumeric(){
        String phoneNum = tfPhoneNum.getText().trim();
        try {
            Integer.parseInt(phoneNum);
            return true;
        }
        catch (NumberFormatException error){
            JOptionPane.showMessageDialog(null, "Please enter a valid phone number!");
            tfPhoneNum.requestFocus();
            return false;
        }        
    }
    
    public boolean makeSureAgeIsNumeric(){
        String age = tfAge.getText().trim();
        try {
            Integer.parseInt(age);
            return true;
        }
        catch (NumberFormatException error){
            JOptionPane.showMessageDialog(null, "Please enter a valid number!");
            tfAge.requestFocus();
            return false;
        }        
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
        if (!makeSurePhoneNoIsNumeric()) return;
        if (!makeSureAgeIsNumeric()) return;
        if (!makeSuretfEmailNotContainDomain()) return;
        if (!makeSureAccIDNotSame()) return;
        if (!makeSureUsernameNotSame()) return;
        if (!makeSureEmailNotSame()) return;
        
        String confirmAccountInformation =
            "Please confirm the following account details:\n\n"
          + "Account ID   : "       + tfAccountID.getText() + "\n"
          + "Username   : "         + tfUserName.getText() + "\n"
          + "Name           : "     + tfName.getText() + "\n"
          + "Email            : "   + tfEmail.getText() + "\n"
          + "Password    : "        + tfPassword.getText() + "\n"
          + "Phone No.   : "        + tfPhoneNum.getText() + "\n"
          + "Gender        : "      + cbGender.getSelectedItem() + "\n"
          + "Age               : "  + tfAge.getText() + "\n"
          + "Role             : "   + cbRole.getSelectedItem() + "\n";
        
        // Double confirmation to create the account
        int result = JOptionPane.showConfirmDialog(this,
                confirmAccountInformation,
                "Confirm Create Account",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        // Once user choose "YES", the account will be created
        if (result == JOptionPane.YES_OPTION){
            writeAccInfoIntoDatabase();
            JOptionPane.showMessageDialog(null, "Account has been created successfully!");
            clearAllTextFields();
            autoGenerateAccID();
        }
        
        else{
            JOptionPane.showMessageDialog(null, "Account create action has been cancelled");
            return;  
        }
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
        String newID = tfAccountID.getText().trim();
        String newUserName = tfUserName.getText().trim();
        String newEmail = tfEmail.getText().trim();
        String newPassword = tfPassword.getText().trim()+ tfEmailDomain.getText().trim();
        String newPhoneNo = tfPhoneNum.getText().trim();
        String newGender = cbGender.getSelectedItem().toString().trim();
        String newAge = tfAge.getText().trim();
        String newRole = cbRole.getSelectedItem().toString().trim();
        
        int selectedRow = tableAccountDetail.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "No record selected for editing!");
                return;
            }        
        
        makeSureTfAreFilled();
        makeSurePhoneNoIsNumeric();
        makeSuretfEmailNotContainDomain();
        makeSureAccIDNotSame();
        makeSureUsernameNotSame();
        makeSureEmailNotSame();
        
        // Double confirmation to edit the account information
        int result = JOptionPane.showConfirmDialog(null,
                "Are you sure to edit the information of this account?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (result == JOptionPane.YES_OPTION){
            editAccInfoInDatabase();
            JOptionPane.showMessageDialog(null, "Account information has been edited successfully!");
        }
        else{
                JOptionPane.showMessageDialog(null, "Account information edit action has been cancelled");
                return;  
        }
                    
    }
    
    public void displayInformation(){
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
            tfAge.setText(age);
            cbRole.setSelectedItem(accRole);   
        }
        else {
            btnCreate.setEnabled(true);
            cbRole.setEnabled(true);
            
            clearAllTextFields();
        }
    }
    
    public void clearAllTextFields(){
        tableAccountDetail.clearSelection();
        tfUserName.setText("");
        tfName.setText("");
        tfEmail.setText("");
        tfPassword.setText("");
        tfPhoneNum.setText("");
        cbGender.setSelectedItem(0);
        tfAge.setText("");
        cbRole.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) tableAccountDetail.getModel();
        model.setRowCount(0);
    }

    public void loadAccountTable() {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfName = new javax.swing.JTextField();
        tfAge = new javax.swing.JTextField();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tfName.addActionListener(this::tfNameActionPerformed);

        tfAge.addActionListener(this::tfAgeActionPerformed);

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
        jLabel1.setText("Account Manage ");

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
        tfEmailDomain.addActionListener(this::tfEmailDomainActionPerformed);

        jLabel5.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel5.setText("ID");

        tfAccountID.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Maiandra GD", 0, 14)); // NOI18N
        jLabel6.setText("Role");

        cbRole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin Staff", "Academic Leader", "Lecturer", "Student" }));

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnClear))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEdit)
                                .addGap(18, 18, 18)
                                .addComponent(btnCreate))
                            .addGroup(layout.createSequentialGroup()
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
                                        .addComponent(jLabel11)
                                        .addGap(36, 36, 36)
                                        .addComponent(tfPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbGender, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfAge, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfEmailDomain, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(17, 17, 17)
                                .addComponent(tfSearch)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSearchAcc))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(40, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(40, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate)
                    .addComponent(btnEdit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchAcc, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClear)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tfNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfNameActionPerformed

    private void tfAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAgeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAgeActionPerformed

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
        displayInformation();
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
    }//GEN-LAST:event_btnClearActionPerformed

    private void tableAccountDetailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableAccountDetailMouseClicked
        displayInformation();
    }//GEN-LAST:event_tableAccountDetailMouseClicked

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        NavigateToAdminDashboard.goToAdminStaffDashboard(this);
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
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTextField tfAge;
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
    private javax.swing.JButton button;
    private int row;
    private javax.swing.JTable table;

    public ButtonEditor(javax.swing.JCheckBox checkBox, javax.swing.JTable table) {
    super(checkBox);
    this.table = table;

    button = new javax.swing.JButton("Delete");
    button.setBackground(java.awt.Color.RED);
    button.setForeground(java.awt.Color.WHITE);

    button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public java.awt.Component getTableCellEditorComponent(
            javax.swing.JTable table, Object value,
            boolean isSelected, int row, int column) {

        this.row = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        String accID = table.getValueAt(row, 0).toString();

        try {
            ManageAccount parent =
                (ManageAccount) javax.swing.SwingUtilities.getWindowAncestor(button);
            parent.deleteAccount(accID);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(button,
                    "Error deleting account:\n" + e.getMessage());
        }

        return "Delete";
    }
}    


