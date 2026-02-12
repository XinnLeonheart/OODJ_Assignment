/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminStaff;

import LogIn.LogIn;
import Navigation.NavigateToDashboard;

/**
 *
 * @author User
 */
public class DefineGradingSystem extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DefineGradingSystem.class.getName());

    /**
     * Creates new form DefineGradingSystem
     */
    public DefineGradingSystem() {
        initComponents();
        displayGradeSelectionOnTable();
        loadAllFromGradeClassTest();
    }
    
    private static final String INPUT_FILE = "src/TextFiles/gradeclasstest.txt";
    
    public void searchStudent(){
        String keyword = tfSearchStudent.getText().trim().toLowerCase();
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tableGrading.getModel();
        model.setRowCount(0);

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(INPUT_FILE))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";");
                if (parts.length < 4) continue;

                String studentName = parts[0].trim();
                String classId = parts[1].trim();
                String testName = parts[2].trim();
                String markStr = parts[3].trim().replace("%", "");

                // match by name (ID doesn't exist in file)
                if (!studentName.toLowerCase().contains(keyword)) continue;

                double mark;
                try { mark = Double.parseDouble(markStr); }
                catch (NumberFormatException e) { continue; }

                String grade = convertMarkToGrade(String.valueOf(mark));

                model.addRow(new Object[]{
                    "N/A",
                    studentName,
                    classId,
                    testName,
                    String.format("%.0f", mark),
                    grade
                });
            }
        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error reading gradeclasstest.txt: " + ex.getMessage());
        }
    }

    
    private String convertMarkToGrade(String markStr) {
        try {
            double mark = Double.parseDouble(markStr);
            if (mark >= 90) return "A+";
            if (mark >= 85) return "A";
            if (mark >= 80) return "B+";
            if (mark >= 75) return "B";
            if (mark >= 70) return "C+";
            if (mark >= 65) return "C";
            if (mark >= 60) return "C-";
            if (mark >= 55) return "D";
            if (mark >= 50) return "F+";
            if (mark >= 45) return "F";
            return "F-";
        } catch (NumberFormatException e) {
            return ""; // empty if invalid number
        }
    }
    
    public void saveGradingInformation() {
        // Step 1: Build a quick lookup key -> grade from JTable
        // Key format: studentName|classId|testName|testMark
        java.util.Map<String, String> gradeMap = new java.util.HashMap<>();
        javax.swing.table.DefaultTableModel tModel = (javax.swing.table.DefaultTableModel) tableGrading.getModel();

        for (int i = 0; i < tModel.getRowCount(); i++) {
            String studentName = tModel.getValueAt(i, 1).toString().trim();
            String classId = tModel.getValueAt(i, 2).toString().trim();
            String testName = tModel.getValueAt(i, 3).toString().trim();
            String testMark = tModel.getValueAt(i, 4).toString().trim();
            String grade = tModel.getValueAt(i, 5).toString().trim();

            String key = studentName + "|" + classId + "|" + testName + "|" + testMark;
            gradeMap.put(key, grade);
        }

        // Step 2: Read original file and rewrite with grade appended
        java.util.List<String> outputLines = new java.util.ArrayList<>();

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(INPUT_FILE))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    // Ensure header has Grade column
                    if (!line.toLowerCase().contains("grade")) {
                        outputLines.add(line + ";Grade");
                    } else {
                        outputLines.add(line);
                    }
                    continue;
                }

                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(";", -1);
                if (parts.length < 6) continue;

                String studentName = parts[0].trim();
                String classId = parts[1].trim();
                String testName = parts[2].trim();

                String markStr = parts[3].trim().replace("%", "");
                double mark;
                try { mark = Double.parseDouble(markStr); }
                catch (NumberFormatException e) { continue; }

                String testMarkKey = String.format("%.0f", mark);
                String key = studentName + "|" + classId + "|" + testName + "|" + testMarkKey;

                // If admin selected grade use it, else auto grade
                String grade = gradeMap.getOrDefault(key, convertMarkToGrade(String.valueOf(mark)));

                // Rebuild line: keep original 6 fields (including feedback, timestamp), then append grade
                String base6 = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + parts[4] + ";" + parts[5];
                outputLines.add(base6 + ";" + grade);
            }

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error reading gradeclasstest.txt: " + ex.getMessage());
            return;
        }

        // Step 3: Write all lines back (overwrite file)
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(INPUT_FILE, false))) {
            for (String out : outputLines) {
                bw.write(out);
                bw.newLine();
            }
            javax.swing.JOptionPane.showMessageDialog(this, "Grade updated into gradeclasstest.txt successfully!");
        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error writing gradeclasstest.txt: " + ex.getMessage());
        }
    }
    
    public void displayGradeSelectionOnTable(){
        String[] grade = {"A+", "A", "B+", "B", "C+", "C", "C-", "D", "F+", "F", "F-", ""};

        javax.swing.table.TableColumn gradeColumn = tableGrading.getColumnModel().getColumn(5);
        javax.swing.JComboBox<String> comboBox = new javax.swing.JComboBox<>(grade);
        gradeColumn.setCellEditor(new javax.swing.DefaultCellEditor(comboBox));
    }
    
    public void loadAllFromGradeClassTest() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tableGrading.getModel();
        model.setRowCount(0);

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(INPUT_FILE))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                if (line.trim().isEmpty()) continue;

                // Student Name;Class Id;Test Name;Test Marks;Feedback;Timestamp;Grade(optional)
                String[] parts = line.split(";", -1); // keep empty last column if any
                if (parts.length < 6) continue;

                String studentName = parts[0].trim();
                String classId = parts[1].trim();
                String testName = parts[2].trim();

                String markStr = parts[3].trim().replace("%", "");
                double mark;
                try { mark = Double.parseDouble(markStr); }
                catch (NumberFormatException e) { continue; }

                // If grade exists in file use it, else auto convert
                String grade = (parts.length >= 7) ? parts[6].trim() : convertMarkToGrade(String.valueOf(mark));

                model.addRow(new Object[]{
                    "N/A",
                    studentName,
                    classId,
                    testName,
                    String.format("%.0f", mark),
                    grade
                });
            }

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error reading gradeclasstest.txt: " + ex.getMessage());
        }
    }

    public void clearTextField(){
        tfSearchStudent.setText("");
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblGradingSystem = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGrading = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tfSearchStudent = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblGradingSystem.setFont(new java.awt.Font("Maiandra GD", 1, 24)); // NOI18N
        lblGradingSystem.setText("Grading System");

        tableGrading.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Student Name", "Class ID", "Test Name", "Test Mark", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableGrading);
        if (tableGrading.getColumnModel().getColumnCount() > 0) {
            tableGrading.getColumnModel().getColumn(0).setResizable(false);
            tableGrading.getColumnModel().getColumn(1).setResizable(false);
            tableGrading.getColumnModel().getColumn(2).setResizable(false);
            tableGrading.getColumnModel().getColumn(3).setResizable(false);
            tableGrading.getColumnModel().getColumn(4).setResizable(false);
            tableGrading.getColumnModel().getColumn(5).setResizable(false);
        }

        jLabel1.setText("Search Student (ID/ Name): ");

        btnSearch.setText("Search");
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        btnSave.setText("Save");
        btnSave.addActionListener(this::btnSaveActionPerformed);

        btnBack.setText("Back");
        btnBack.addActionListener(this::btnBackActionPerformed);

        btnClear.setText("Clear");
        btnClear.addActionListener(this::btnClearActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblGradingSystem)
                .addGap(345, 345, 345))
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSave)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(tfSearchStudent)
                            .addGap(18, 18, 18)
                            .addComponent(btnSearch)
                            .addGap(121, 121, 121)
                            .addComponent(btnClear))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblGradingSystem))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack)))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfSearchStudent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        searchStudent();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveGradingInformation();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        NavigateToDashboard.goToAdminStaffDashboard(this, LogIn.loggedInName);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearTextField();
        displayGradeSelectionOnTable();
        loadAllFromGradeClassTest();
    }//GEN-LAST:event_btnClearActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new DefineGradingSystem().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGradingSystem;
    private javax.swing.JTable tableGrading;
    private javax.swing.JTextField tfSearchStudent;
    // End of variables declaration//GEN-END:variables
}
