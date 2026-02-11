/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author lolipop
 */

import LogIn.LogIn;
import Navigation.NavigateToDashboard;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class CreateClass extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CreateClass.class.getName());

    /**
     * Creates new form CreateClass
     */
    
    private static final String CLASS_FILE = "src/TextFiles/Class.txt";
    private String originalTableData = "";

    public CreateClass() {
        initComponents();
        displayModuleSelectionOnTable();
        autoGenerateClassID();
        loadTableClass();        
    }
    
    public void autoGenerateClassID(){
        
        String prefix = "C";
        int nextID = 1;

        File file = new File(CLASS_FILE);

        // If file does not exist / empty -> start from C001
        if (!file.exists() || file.length() == 0) {
            tfClassID.setText(prefix + "001");
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
                int number = Integer.parseInt(lastID.substring(1));
                nextID = number + 1;
            }

            tfClassID.setText(prefix + String.format("%03d", nextID));

        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Error generating Class ID!");
        }
    }
    
    public void createClass() {
        String classID = tfClassID.getText().trim();
        String className = tfClassName.getText().trim();
        String module = cbModule.getSelectedItem().toString();

        if (classID.isEmpty() || className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }      
        
         try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLASS_FILE, true))) {
            bw.write(classID + ";" + className + ";" + module);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving class data");
            return;
        }

        JOptionPane.showMessageDialog(this, "Class created successfully!");
        
        loadTableClass();
        tfClassName.setText("");
        autoGenerateClassID();
    }
    
    public void saveResult(){
        if (tableClass.isEditing()) {
            tableClass.getCellEditor().stopCellEditing();
        }
            
       DefaultTableModel model = (DefaultTableModel) tableClass.getModel();

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No data to save.");
            return;
        }

        StringBuilder currentData = new StringBuilder();

        for (int i = 0; i < model.getRowCount(); i++) {
            currentData.append(
                model.getValueAt(i, 0) + ";" +
                model.getValueAt(i, 1) + ";" +
                model.getValueAt(i, 2)
            ).append("\n");
        }

        // Compare with original data
        if (currentData.toString().equals(originalTableData)) {
            JOptionPane.showMessageDialog(this, "No changes detected.");
            return;
        }

        // Save changes
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CLASS_FILE))) {
            bw.write(currentData.toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving changes.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Changes saved successfully!");
        loadTableClass();
    }
    
    public void displayModuleSelectionOnTable() {
        String[] modules = {
            "English", "Mathematics", "Science", "Statistics",
            "Physics", "Biology", "Chemistry", "Accounting"
        };

        JComboBox<String> moduleComboBox = new JComboBox<>(modules);

        tableClass.getColumnModel()
                  .getColumn(2)   // Module column index
                  .setCellEditor(new DefaultCellEditor(moduleComboBox));
        
        tableClass.getColumnModel()
          .getColumn(2)
          .setCellRenderer(new DefaultTableCellRenderer() {
              @Override
              public java.awt.Component getTableCellRendererComponent(
                      JTable table, Object value, boolean isSelected,
                      boolean hasFocus, int row, int column) {

                  JComboBox<String> rendererCombo = new JComboBox<>(modules);
                  rendererCombo.setSelectedItem(value);
                  return rendererCombo;
              }
          });
    }
    
    public void searchClass() {
        String searchText = tfSearchClass.getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) tableClass.getModel();
        model.setRowCount(0);
        
        if (searchText.isEmpty()) {
            tfSearchClass.setText("");
            JOptionPane.showMessageDialog(this, "Please enter Account ID or Name to search.");
            loadTableClass();
            return;
        }
                
        try (BufferedReader br = new BufferedReader (new FileReader(CLASS_FILE))){
            String line;

            while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] data = line.split(";");

            String classID = data[0].toLowerCase();
            String className = data[1].toLowerCase();

            if (classID.contains(searchText) || className.contains(searchText)) {
                model.addRow(new Object[]{
                        data[0], data[1], data[2], "Delete"
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
    
    public void loadTableClass(){    
        DefaultTableModel model =
            (DefaultTableModel) tableClass.getModel();
        model.setRowCount(0);

        StringBuilder snapshot = new StringBuilder();
        
        try (BufferedReader br = new BufferedReader(new FileReader(CLASS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(";");

                model.addRow(new Object[]{
                    data[0], data[1], data[2]
                });
                
                snapshot.append(line).append("\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading accounts");
        }
        
        originalTableData = snapshot.toString();
    }           
    
    // Refresh classID and clear text in the tfClassName
    public void clearTextFieldAndResult(){
        autoGenerateClassID();
        tfClassName.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jSpinner1 = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableClass = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfClassID = new javax.swing.JTextField();
        tfClassName = new javax.swing.JTextField();
        btnSearchClass = new javax.swing.JButton();
        btnCreateClass = new javax.swing.JButton();
        tfSearchClass = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbModule = new javax.swing.JComboBox<>();
        btnSaveClass = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableClass.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "Module"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableClass);
        if (tableClass.getColumnModel().getColumnCount() > 0) {
            tableClass.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        jLabel1.setText("Create Classes");

        jLabel2.setText("Search Class (Class ID/ Name):");

        jLabel3.setText("Class ID:");

        jLabel4.setText("Class Name:");

        tfClassID.setEditable(false);
        tfClassID.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        tfClassID.setEnabled(false);

        btnSearchClass.setText("Search");
        btnSearchClass.addActionListener(this::btnSearchClassActionPerformed);

        btnCreateClass.setText("Create");
        btnCreateClass.addActionListener(this::btnCreateClassActionPerformed);

        jLabel5.setText("Module:");

        cbModule.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "English", "Mathematics", "Science", "Statistics", "Physics", "Biology", "Chemistry", "Accounting" }));

        btnSaveClass.setText("Save");
        btnSaveClass.addActionListener(this::btnSaveClassActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnSaveClass)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(29, 29, 29)
                                        .addComponent(tfSearchClass, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnSearchClass))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnCreateClass)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel5))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tfClassID)
                                            .addComponent(tfClassName)
                                            .addComponent(cbModule, 0, 200, Short.MAX_VALUE))))
                                .addGap(18, 18, 18)
                                .addComponent(btnClear))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)
                        .addGap(237, 237, 237)
                        .addComponent(jLabel1)))
                .addGap(0, 50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfClassID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(tfClassName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbModule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreateClass)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnSearchClass)
                    .addComponent(tfSearchClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSaveClass)
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateClassActionPerformed
        createClass();
    }//GEN-LAST:event_btnCreateClassActionPerformed

    private void btnSaveClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveClassActionPerformed
        saveResult();
    }//GEN-LAST:event_btnSaveClassActionPerformed

    private void btnSearchClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchClassActionPerformed
        searchClass();
    }//GEN-LAST:event_btnSearchClassActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearTextFieldAndResult();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        NavigateToDashboard.goToAdminStaffDashboard(this, LogIn.loggedInName);
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
        java.awt.EventQueue.invokeLater(() -> new CreateClass().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnCreateClass;
    private javax.swing.JButton btnSaveClass;
    private javax.swing.JButton btnSearchClass;
    private javax.swing.JComboBox<String> cbModule;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable tableClass;
    private javax.swing.JTextField tfClassID;
    private javax.swing.JTextField tfClassName;
    private javax.swing.JTextField tfSearchClass;
    // End of variables declaration//GEN-END:variables
}
