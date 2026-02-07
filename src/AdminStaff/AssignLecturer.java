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
    
    private static final String ACCOUNT_FILE = "src/TextFiles/Account.txt";
    private static final String LECTURER_FILE = "src/TextFiles/Lecturer.txt";

    public AssignLecturer() {
        initComponents();
        tableAssignLecturer.setSelectionMode(
            javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );
        displayAcademicLeaderListOnTable();
        loadLecturerInformationOnTable();
    }
    
    public void displayAcademicLeaderListOnTable() {
        JComboBox<String> academicLeaderCombo = new JComboBox<>();
        academicLeaderCombo.addItem("-- Select Academic Leader --");


        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");
                String name = data[2].trim();
                String role = data[8].trim();

                if (role.equalsIgnoreCase("Academic Leader")) {
                    academicLeaderCombo.addItem(name);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading academic leaders");
            return;
        }

        // Column 3 = Academic Leader dropdown-list
        tableAssignLecturer.getColumnModel()
                .getColumn(3) // Academic Leader column (index 3)
                .setCellEditor(new DefaultCellEditor(academicLeaderCombo));

        // Set JComboBox as renderer for column 3
        tableAssignLecturer.getColumnModel()
                .getColumn(3)
                .setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public java.awt.Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {

                        JComboBox<String> rendererCombo = new JComboBox<>();
                        rendererCombo.addItem("-- Select Academic Leader --");

                        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (line.trim().isEmpty()) continue;

                                String[] data = line.split(";");
                                String name = data[2].trim();
                                String role = data[8].trim();

                                if (role.equalsIgnoreCase("Academic Leader")) {
                                    rendererCombo.addItem(name);
                                }
                            }
                        } catch (IOException e) {
                            // ignore for rendering
                        }

                        rendererCombo.setSelectedItem(value);

                        if (isSelected) {
                            rendererCombo.setBackground(table.getSelectionBackground());
                        }

                        return rendererCombo;
                    }
                });

    }

    public void loadLecturerInformationOnTable(){
        DefaultTableModel model = (DefaultTableModel) tableAssignLecturer.getModel();
        model.setRowCount(0); // Clear table first
        
        Map<String, String> assignedLeaders = new LinkedHashMap<>();

        File lecturerFile = new File(LECTURER_FILE);
        if (lecturerFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(lecturerFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] data = line.split(";");
                    // data[0] = Lecturer ID
                    // data[3] = Academic Leader
                    assignedLeaders.put(data[0].trim(), data[3].trim());
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading lecturer assignments");
            }
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");

                String accID = data[0].trim();
                String name = data[2].trim();
                String email = data[3].trim();
                String role = data[8].trim();

                // Only show lecturers
                if (role.equalsIgnoreCase("Lecturer")) {
                    String academicLeader = assignedLeaders.getOrDefault(accID,
                        "-- Select Academic Leader --"
                );
                                    
                    model.addRow(new Object[] {
                        accID,
                        name,
                        email,
                        academicLeader    // Academic Leader ComboBox column
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading lecturers");
        }

        displayAcademicLeaderListOnTable(); // Set up the combo box in the column
    }
    
    public void searchLecturer() {
        java.util.List<Integer> matchedRows = new java.util.ArrayList<>();
        
        Map<String, String> assignedLeaders = new LinkedHashMap<>();

        File file = new File(LECTURER_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] data = line.split(";");
                    assignedLeaders.put(data[0].trim(), data[3].trim());
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error loading lecturer assignments");
            }
        }

        String searchText = tfSearchLecturer.getText().trim().toLowerCase();
        DefaultTableModel model =
            (DefaultTableModel) tableAssignLecturer.getModel();
        model.setRowCount(0);

        if (searchText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter Lecturer ID or Name to search.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {
            String line;

            // Match Lecturers
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");

                String accID = data[0].trim();
                String name = data[2].trim();
                String email = data[3].trim();
                String role = data[8].trim();

                // Only show LECTURERS
                if (role.equalsIgnoreCase("Lecturer") &&
                    (accID.toLowerCase().contains(searchText)
                    || name.toLowerCase().contains(searchText))) {

                    model.addRow(new Object[]{
                        accID,
                        name,
                        email,
                        assignedLeaders.getOrDefault(accID,
                            "-- Select Academic Leader --")    // Academic Leader ComboBox column
                    });
                    
                    matchedRows.add(model.getRowCount() - 1);
                    
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error searching lecturer");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNT_FILE))) {

            String line;

            // Second pass: remaining lecturers
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(";");

                String accID = data[0].trim();
                String name = data[2].trim();
                String email = data[3].trim();
                String role = data[8].trim();

                if (role.equalsIgnoreCase("Lecturer") &&
                    !(accID.toLowerCase().contains(searchText)
                    || name.toLowerCase().contains(searchText))) {

                    model.addRow(new Object[]{
                        accID,
                        name,
                        email,
                        assignedLeaders.getOrDefault(accID,
                            "-- Select Academic Leader --")    // Academic Leader ComboBox column
                    });
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading lecturers");
        }

        displayAcademicLeaderListOnTable();

            if (!matchedRows.isEmpty()) {
                tableAssignLecturer.clearSelection();

                for (int row : matchedRows) {
                    tableAssignLecturer.addRowSelectionInterval(row, row);
                }

                // Scroll to first matched row
                int firstRow = matchedRows.get(0);
                tableAssignLecturer.scrollRectToVisible(
                    tableAssignLecturer.getCellRect(firstRow, 0, true)
                );
            }
    }

    
    public void saveAssignInfoIntoDatabase(){
        DefaultTableModel model =
            (DefaultTableModel) tableAssignLecturer.getModel();

        Map<String, String> lecturerMap = new LinkedHashMap<>();

        // Load existing record
        File file = new File(LECTURER_FILE);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] data = line.split(";");
                    lecturerMap.put(data[0], line);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                    "Error reading lecturer file");
                return;
            }
        }

        // Update/ Add from table
        for (int i = 0; i < model.getRowCount(); i++) {

            String lecturerID = model.getValueAt(i, 0).toString();
            String lecturerName = model.getValueAt(i, 1).toString();
            String lecturerEmail = model.getValueAt(i, 2).toString();
            Object leaderObj = model.getValueAt(i, 3);

            if (leaderObj == null ||
                leaderObj.toString().isEmpty() ||
                leaderObj.toString().equals("-- Select Academic Leader --")) {

                JOptionPane.showMessageDialog(this,
                    "Please assign Academic Leader for all lecturers");
                return;
            }

            String academicLeader = leaderObj.toString();

            String newLine =
                lecturerID + ";" +
                lecturerName + ";" +
                lecturerEmail + ";" +
                academicLeader;

            lecturerMap.put(lecturerID, newLine); // overwrite if exists
        }

        // Rewrite file
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(LECTURER_FILE))) {

            for (String record : lecturerMap.values()) {
                bw.write(record);
                bw.newLine();
            }

            JOptionPane.showMessageDialog(this,
                "Lecturer assignment updated successfully");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error saving lecturer assignment");
        }
    }
    
    public void clearTextField(){
        tfSearchLecturer.setText("");
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
