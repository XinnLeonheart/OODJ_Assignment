/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package AdminStaff;

import LogIn.LogIn;
import Navigation.NavigateToDashboard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class GradingSystem extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GradingSystem.class.getName());

    /**
     * Creates new form DefineGradingSystem
     */
    public GradingSystem() {
        initComponents();
        displayGradeSelectionOnTable();
        loadAllFromGradeClassTest();
    }
    
    private final GradeFileRepository gradeRepo = new GradeFileRepository(INPUT_FILE);
    private static final String INPUT_FILE = "TextFiles/gradeclasstest.txt";

    public void searchStudent() {
        String keyword = tfSearchStudent.getText().trim().toLowerCase();
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) tableGrading.getModel();
        model.setRowCount(0);

        try {
            for (GradeRecord r : gradeRepo.readAll()) {

                // match by name (your file doesn't store student ID)
                if (!r.getStudentName().toLowerCase().contains(keyword)) continue;

                // if grade missing -> auto generate
                String grade = (r.getGrade() == null || r.getGrade().isBlank())
                        ? convertMarkToGrade(String.valueOf(r.getMark()))
                        : r.getGrade();

                model.addRow(new Object[]{
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getClassId(),
                    r.getTestName(),
                    String.format("%.0f", r.getMark()),
                    grade
                });
            }
        } catch (IOException ex) {
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

        List<String> outputLines = new ArrayList<>();

        // header (your file first line)
        outputLines.add("Student Name;Class Id;Test Name;Test Marks;Feedback;Timestamp;Grade");

        try {
            // We must read the ORIGINAL file lines to keep feedback/timestamp
            java.io.File file = new java.io.File(INPUT_FILE);
            boolean fileExists = file.exists();

            if (!fileExists) {
                javax.swing.JOptionPane.showMessageDialog(this, "File not found: " + INPUT_FILE);
                return;
            }

            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
                String line;
                boolean firstLine = true;

                while ((line = br.readLine()) != null) {
                    if (firstLine) { firstLine = false; continue; }
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split(";", -1);
                    if (parts.length < 6) continue;

                    String studentName = parts[0];
                    String classId = parts[1];
                    String testName = parts[2];

                    String markStr = parts[3].trim().replace("%", "");
                    double mark;
                    try { mark = Double.parseDouble(markStr); }
                    catch (NumberFormatException e) { continue; }

                    String feedback = parts[4];
                    String timestamp = parts[5];

                    String grade = convertMarkToGrade(String.valueOf(mark));

                    // build using GradeRecord (OOP)
                    GradeRecord record = new GradeRecord("N/A", studentName.trim(), classId.trim(), testName.trim(), mark, grade);
                    outputLines.add(record.toLine(feedback, timestamp));
                }
            }

            // overwrite file using repository
            gradeRepo.overwriteAllRawLines(outputLines);

            javax.swing.JOptionPane.showMessageDialog(this, "System auto-generated grades saved to gradeclasstest.txt!");
            loadAllFromGradeClassTest();

        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error saving gradeclasstest.txt: " + ex.getMessage());
        }
    }

    
    public void displayGradeSelectionOnTable(){
        String[] grade = {"A+", "A", "B+", "B", "C+", "C", "C-", "D", "F+", "F", "F-", ""};

        javax.swing.table.TableColumn gradeColumn = tableGrading.getColumnModel().getColumn(5);
        javax.swing.JComboBox<String> comboBox = new javax.swing.JComboBox<>(grade);
        gradeColumn.setCellEditor(new javax.swing.DefaultCellEditor(comboBox));
    }
    
    public void loadAllFromGradeClassTest() {
        javax.swing.table.DefaultTableModel model =
                (javax.swing.table.DefaultTableModel) tableGrading.getModel();
        model.setRowCount(0);

        try {
            for (GradeRecord r : gradeRepo.readAll()) {

                // If grade exists in file use it, else auto convert
                String grade = (r.getGrade() == null || r.getGrade().isBlank())
                        ? convertMarkToGrade(String.valueOf(r.getMark()))
                        : r.getGrade();

                model.addRow(new Object[]{
                    r.getStudentId(),
                    r.getStudentName(),
                    r.getClassId(),
                    r.getTestName(),
                    String.format("%.0f", r.getMark()),
                    grade
                });
            }
        } catch (IOException ex) {
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
                false, false, false, false, false, false
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
        java.awt.EventQueue.invokeLater(() -> new GradingSystem().setVisible(true));
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
