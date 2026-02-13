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
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultCellEditor;
import java.util.Map;
import javax.swing.JTable;
import java.util.LinkedHashMap;
import javax.swing.table.DefaultTableCellRenderer;

public class AssignLecturer extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(AssignLecturer.class.getName());

    /**
     * Creates new form AssignLecturer
     */
    
    private static final String ACCOUNT_FILE = "TextFiles/Account.txt";
    private static final String LECTURER_FILE = "TextFiles/LecturerAssigned.txt";
    private AccountFileRepository accountRepo;
    private LecturerAssignRepository lecturerRepo;
    private static final String DEFAULT_LEADER_DISPLAY = "-- Select Academic Leader --";
    private static final String PENDING_VALUE_IN_FILE  = "PENDING";
    
    public AssignLecturer() {
        initComponents();

        accountRepo = new AccountFileRepository(ACCOUNT_FILE);
        lecturerRepo = new LecturerAssignRepository(LECTURER_FILE);

        tableAssignLecturer.setSelectionMode(
            javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );

        displayAcademicLeaderListOnTable();
        loadLecturerInformationOnTable();
    }
    
    public void displayAcademicLeaderListOnTable() {
        JComboBox<String> academicLeaderCombo = new JComboBox<>();
        academicLeaderCombo.addItem(DEFAULT_LEADER_DISPLAY);

        try {
            for (Account a : accountRepo.readAll()) {
                if (a.getRole() != null && a.getRole().equalsIgnoreCase("Academic Leader")) {
                    academicLeaderCombo.addItem(a.getName());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading academic leaders");
            return;
        }

        tableAssignLecturer.getColumnModel().getColumn(3)
                .setCellEditor(new DefaultCellEditor(academicLeaderCombo));
    }

    public void loadLecturerInformationOnTable() {
        DefaultTableModel model = (DefaultTableModel) tableAssignLecturer.getModel();
        model.setRowCount(0);

        Map<String, String> assignedLeaders;
        try {
            assignedLeaders = lecturerRepo.readAllAssignments();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading lecturer assignments");
            assignedLeaders = new LinkedHashMap<>();
        }

        try {
            for (Account a : accountRepo.readAll()) {
                if (a.getRole() != null && a.getRole().equalsIgnoreCase("Lecturer")) {

                    String leaderFromFile = assignedLeaders.get(a.getAccID());
                    String leader;

                    if (leaderFromFile == null || leaderFromFile.trim().isEmpty()
                            || leaderFromFile.equalsIgnoreCase(PENDING_VALUE_IN_FILE)) {
                        leader = DEFAULT_LEADER_DISPLAY;
                    } else {
                        leader = leaderFromFile.trim();
                    }

                    model.addRow(new Object[]{
                        a.getAccID(),
                        a.getName(),
                        a.getEmail(),
                        leader
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading lecturers");
        }

        displayAcademicLeaderListOnTable();
    }
    
    public void searchLecturer() {
        String searchText = tfSearchLecturer.getText().trim().toLowerCase();

        // If empty -> reload all lecturers
        if (searchText.isEmpty()) {
            loadLecturerInformationOnTable();
            return;
        }

        // Load assignments lecturerID -> leader
        Map<String, String> assignedLeaders = new LinkedHashMap<>();
        File file = new File(LECTURER_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    String[] data = line.split(";");
                    // lecturerID;lecturerName;lecturerEmail;academicLeader
                    if (data.length >= 4) {
                        assignedLeaders.put(data[0].trim(), data[3].trim());
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error loading lecturer assignments");
            }
        }

        DefaultTableModel model = (DefaultTableModel) tableAssignLecturer.getModel();
        model.setRowCount(0);

        java.util.List<Integer> matchedRows = new java.util.ArrayList<>();

        // Read Account.txt and ONLY add lecturers:
        // - matched lecturers first
        // - then remaining lecturers
        java.util.List<String[]> lecturers = new java.util.ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) 
                    continue;

                String[] data = line.split(";");
                if (data.length < 9) 
                    continue;

                String role = data[8].trim();
                if (!role.equalsIgnoreCase("Lecturer")) 
                    continue;  // ONLY display lecturers

                lecturers.add(data);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error searching lecturer");
            return;
        }

        // 1) Add matched lecturers first
        for (String[] data : lecturers) {
            String accID = data[0].trim();
            String name  = data[2].trim();
            String email = data[3].trim();

            boolean isMatch = accID.toLowerCase().contains(searchText)
                           || name.toLowerCase().contains(searchText);

            if (!isMatch) continue;

            String leaderFromFile = assignedLeaders.get(accID);
            String leader = (leaderFromFile == null || leaderFromFile.trim().isEmpty()
                    || leaderFromFile.equalsIgnoreCase(PENDING_VALUE_IN_FILE))
                    ? DEFAULT_LEADER_DISPLAY
                    : leaderFromFile.trim();

            model.addRow(new Object[]{accID, name, email, leader});
            matchedRows.add(model.getRowCount() - 1);
        }
        
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No lecturer found.");
        }

        // 2) Add remaining lecturers (not matched)
        for (String[] data : lecturers) {
            String accID = data[0].trim();
            String name  = data[2].trim();
            String email = data[3].trim();

            boolean isMatch = accID.toLowerCase().contains(searchText)
                           || name.toLowerCase().contains(searchText);

            if (isMatch) continue;

            String leaderFromFile = assignedLeaders.get(accID);
            String leader = (leaderFromFile == null || leaderFromFile.trim().isEmpty()
                    || leaderFromFile.equalsIgnoreCase(PENDING_VALUE_IN_FILE))
                    ? DEFAULT_LEADER_DISPLAY
                    : leaderFromFile.trim();

            model.addRow(new Object[]{accID, name, email, leader});
        }

        displayAcademicLeaderListOnTable();

        // Highlight matched rows
        if (!matchedRows.isEmpty()) {
            tableAssignLecturer.clearSelection();
            for (int row : matchedRows) {
                tableAssignLecturer.addRowSelectionInterval(row, row);
            }
            int firstRow = matchedRows.get(0);
            tableAssignLecturer.scrollRectToVisible(
                tableAssignLecturer.getCellRect(firstRow, 0, true)
            );
        }
    }

    public void saveAssignInfoIntoDatabase() {
        DefaultTableModel model = (DefaultTableModel) tableAssignLecturer.getModel();
        Map<String, String> lecturerIdToLeader = new LinkedHashMap<>();

        int pendingCount = 0;

        for (int i = 0; i < model.getRowCount(); i++) {
            String lecturerID = model.getValueAt(i, 0).toString();
            Object leaderObj = model.getValueAt(i, 3);

            String leaderText = (leaderObj == null) ? "" : leaderObj.toString().trim();

            // If user didn't select, store PENDING in file
            if (leaderText.isEmpty() || leaderText.equals(DEFAULT_LEADER_DISPLAY)) {
                pendingCount++;
                lecturerIdToLeader.put(lecturerID, PENDING_VALUE_IN_FILE);

                // Keep the table showing "-- Select Academic Leader --"
                model.setValueAt(DEFAULT_LEADER_DISPLAY, i, 3);
            } else {
                lecturerIdToLeader.put(lecturerID, leaderText);
            }
        }

        // Show warning but DO NOT stop saving
        if (pendingCount > 0) {
            JOptionPane.showMessageDialog(
                this,
                "Warning: " + pendingCount + " lecturer(s) have not been assigned.\n" +
                "They will be saved as '" + PENDING_VALUE_IN_FILE + "'.",
                "Pending Assignment",
                JOptionPane.WARNING_MESSAGE
            );
        }

        try {
            lecturerRepo.saveAllAssignments(lecturerIdToLeader, accountRepo);
            JOptionPane.showMessageDialog(this, "Saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error saving lecturer assignment:\n" + e.getMessage());
        }
    }


    
    public void clearTextField(){
        tfSearchLecturer.setText("");
        loadLecturerInformationOnTable();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAssignLecturer = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnSearchCourse = new javax.swing.JButton();
        tfSearchLecturer = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        jLabel1.setText("Assign Lecturer ");

        tableAssignLecturer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Lecturer ID", "Lecturer Name", "Lecturer Email", "Academic Leader"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableAssignLecturer);
        if (tableAssignLecturer.getColumnModel().getColumnCount() > 0) {
            tableAssignLecturer.getColumnModel().getColumn(0).setResizable(false);
            tableAssignLecturer.getColumnModel().getColumn(1).setResizable(false);
            tableAssignLecturer.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel2.setText("Search Lecturer (ID/ Name):");

        btnSearchCourse.setText("Search");
        btnSearchCourse.addActionListener(this::btnSearchCourseActionPerformed);

        btnSave.setText("Save");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

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
                .addGap(403, 403, 403))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(27, 27, 27)
                            .addComponent(tfSearchLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnSearchCourse)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnClear))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfSearchLecturer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearchCourse)
                    .addComponent(btnClear))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveAssignInfoIntoDatabase();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSearchCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchCourseActionPerformed
        searchLecturer();
    }//GEN-LAST:event_btnSearchCourseActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearTextField();
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
        java.awt.EventQueue.invokeLater(() -> new AssignLecturer().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearchCourse;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableAssignLecturer;
    private javax.swing.JTextField tfSearchLecturer;
    // End of variables declaration//GEN-END:variables
}
