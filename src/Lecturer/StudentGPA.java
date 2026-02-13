/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Lecturer;

import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel; 
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
/**
 *
 * @author justl
 */
public class StudentGPA extends javax.swing.JPanel {

    private FeedbackandGPA2 parent;
    private List<String> testNames = new ArrayList<>();
    private List<String> assignmentNames = new ArrayList<>();
    private Map<String, Map<String, Double>> studentTestMarks = new HashMap<>();
    private Map<String, Map<String, Double>> studentAssignmentMarks = new HashMap<>();
    private Map<String, String> studentClassId = new HashMap<>(); // Store student's class ID
    /**
     * Creates new form StudentGPA
     */
    public StudentGPA(FeedbackandGPA2 parent) {
        this.parent = parent;
        initComponents();
        loadTableData();
    }

    private void loadTableData(){
        
        try{
            // Load test and assignment definitions
            loadTestDefinitions("TextFiles/classtest.txt");
            loadAssignmentDefinitions("TextFiles/assignment.txt");

            // Load student marks
            loadStudentTestMarks("TextFiles/gradeclasstest.txt");
            loadStudentAssignmentMarks("TextFiles/gradeassignment.txt");

            // Create and populate table
            createAndPopulateTable();
            
        } catch (IOException ex){
            System.getLogger(StudentGPA.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    
   /**
     * Load test definitions from classtest.txt
     */
    private void loadTestDefinitions(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");
                    if (parts.length >= 1) {
                        String testName = parts[0].trim();
                        if (!testNames.contains(testName)) {  // Avoid duplicates
                            testNames.add(testName);
                        }
                    }
                }
            }
        }
    }

    /**
     * Load assignment definitions from assignment.txt
     */
    private void loadAssignmentDefinitions(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");
                    if (parts.length >= 1) {
                        String assignmentName = parts[0].trim();
                        if (!assignmentNames.contains(assignmentName)) {  // Avoid duplicates
                            assignmentNames.add(assignmentName);
                        }
                    }
                }
            }
        }
    }

    /**
     * Load student test marks from Gradeclasstest.txt
     */
    private void loadStudentTestMarks(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");
                    //Student Id;Student Name;Class Id;Test Name;Test Marks;Feedback;Timestamp;Grade
                    if (parts.length >= 5) {
                        String studentId = parts[0].trim(); 
                        String studentName = parts[1].trim();
                        String classId = parts[2].trim();
                        String testName = parts[3].trim();
                        double marks = Double.parseDouble(parts[4].trim());

                        //Store class id for this student
                        studentClassId.put(studentName, classId);
                        
                        studentTestMarks.computeIfAbsent(studentName, k -> new HashMap<>())
                                .put(testName, marks);
                    }
                }
            }
        }
    }

    /**
     * Load student assignment marks from Gradeassignment.txt
     */
    private void loadStudentAssignmentMarks(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    String[] parts = line.split(";");
                    //Student Id;Student Name;Class Id;Assignment Name;Assignment Marks;Feedback;Timestamp;Grade
                    if (parts.length >= 5) {
                        String studentId = parts[0].trim();
                        String studentName = parts[1].trim();
                        String classId = parts[2].trim();
                        String assignmentName = parts[3].trim();
                        double marks = Double.parseDouble(parts[4].trim());

                        // Store class ID for this student (if not already stored)
                        if (!studentClassId.containsKey(studentName)) {
                            studentClassId.put(studentName, classId);
                        }
                        
                        studentAssignmentMarks.computeIfAbsent(studentName, k -> new HashMap<>())
                                .put(assignmentName, marks);
                    }
                }
            }
        }
    }

    /**
     * Calculate GPA/Average for a student based on available marks
     * Only counts marks that exist (ignores missing data)
     */
    private String calculateGPA(String studentName) {
        List<Double> allMarks = new ArrayList<>();
        
        // Collect test marks
        Map<String, Double> testMarks = studentTestMarks.getOrDefault(studentName, new HashMap<>());
        for (String testName : testNames) {
            Double mark = testMarks.get(testName);
            if (mark != null && mark >= 0) {  // Only add valid marks
                allMarks.add(mark);
            }
        }
        
        // Collect assignment marks
        Map<String, Double> assignMarks = studentAssignmentMarks.getOrDefault(studentName, new HashMap<>());
        for (String assignmentName : assignmentNames) {
            Double mark = assignMarks.get(assignmentName);
            if (mark != null && mark >= 0) {  // Only add valid marks
                allMarks.add(mark);
            }
        }
        
        // Calculate average based on available marks only
        if (allMarks.isEmpty()) {
            return "-";
        } else {
            double average = allMarks.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            return String.format("%.2f", average);
        }
    }
    
    /**
     * Create table model with dynamic columns and populate with data
     */
    private void createAndPopulateTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Build column headers
        Vector<String> columnHeaders = new Vector<>();
        columnHeaders.add("Student Name");
        columnHeaders.add("Class ID");  // Add Class ID column

        // Add all test columns
        for (String testName : testNames) {
            columnHeaders.add(testName);
        }

        // Add all assignment columns
        for (String assignmentName : assignmentNames) {
            columnHeaders.add(assignmentName);
        }

        // Add GPA/Average column
        columnHeaders.add("Average");

        model.setColumnIdentifiers(columnHeaders);

        // Get all unique student names
        Set<String> allStudents = new HashSet<>();
        allStudents.addAll(studentTestMarks.keySet());
        allStudents.addAll(studentAssignmentMarks.keySet());

        // Add data rows for each student
        for (String studentName : allStudents) {
            Vector<Object> row = new Vector<>();
            row.add(studentName);

            // Add Class ID
            String classId = studentClassId.getOrDefault(studentName, "-");
            row.add(classId);

            // Add test marks
            Map<String, Double> testMarks = studentTestMarks.getOrDefault(studentName, new HashMap<>());
            for (String testName : testNames) {
                Double mark = testMarks.get(testName);
                row.add(mark != null ? mark : "-");
            }

            // Add assignment marks
            Map<String, Double> assignMarks = studentAssignmentMarks.getOrDefault(studentName, new HashMap<>());
            for (String assignmentName : assignmentNames) {
                Double mark = assignMarks.getOrDefault(assignmentName, null);
                row.add(mark != null ? mark : "-");
            }

            // Calculate and add GPA/Average (ALWAYS recalculated fresh)
            row.add(calculateGPA(studentName));

            model.addRow(row);
        }
    }

    public void saveTableToFile(String filePath) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            try (FileWriter fw = new FileWriter(filePath)) {
                // Write headers
                for (int col = 0; col < model.getColumnCount(); col++) {
                    fw.write(model.getColumnName(col));
                    if (col < model.getColumnCount() - 1) fw.write(";");
                }
                fw.write("\n");
                // Write data
                for (int row = 0; row < model.getRowCount(); row++) {
                    for (int col = 0; col < model.getColumnCount(); col++) {
                        Object value = model.getValueAt(row, col);
                        fw.write(value != null ? value.toString() : "");
                        if (col < model.getColumnCount() - 1) fw.write(";");
                    }
                    fw.write("\n");
                }
            }
            JOptionPane.showMessageDialog(this, "File saved successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
    /**
     * Refresh table data
     */
    public void refreshTableData() {
        testNames.clear();
        assignmentNames.clear();
        studentTestMarks.clear();
        studentAssignmentMarks.clear();
        studentClassId.clear();  // Clear class ID data too
        
        loadTableData();
        
        JOptionPane.showMessageDialog(this, "Table refreshed and GPA recalculated!");
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        dashboard1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        btnback = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabelclassesstest = new javax.swing.JLabel();
        jButtonrefresh = new javax.swing.JButton();
        btnsaverecord = new javax.swing.JButton();

        dashboard.setBackground(new java.awt.Color(228, 228, 228));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        jLabel6.setText("StudentGPA");
        dashboard.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 23, 600, -1));

        jLabel17.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel17.setText("04:00 AM");
        dashboard.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 45, 80, -1));

        dashboard1.setBackground(new java.awt.Color(228, 228, 228));
        dashboard1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel7.setText("January 20, 2025");
        dashboard1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 23, -1, -1));

        btnback.setText("back");
        btnback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbackActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabelclassesstest.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabelclassesstest.setText("Student GPA Record");

        jButtonrefresh.setText("Refresh");
        jButtonrefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonrefreshActionPerformed(evt);
            }
        });

        btnsaverecord.setText("Save Record");
        btnsaverecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsaverecordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(btnback)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonrefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(btnsaverecord))
                    .addComponent(jLabelclassesstest)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabelclassesstest)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnback)
                    .addComponent(jButtonrefresh)
                    .addComponent(btnsaverecord))
                .addGap(17, 17, 17))
        );

        dashboard1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 730, 380));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        dashboard.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        // TODO add your handling code here:
        if (parent != null) {
            parent.showDashboard();
        }
    }//GEN-LAST:event_btnbackActionPerformed

    private void jButtonrefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonrefreshActionPerformed
        // TODO add your handling code here:
        loadTableData();

    }//GEN-LAST:event_jButtonrefreshActionPerformed

    private void btnsaverecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsaverecordActionPerformed
        // TODO add your handling code here:
        saveTableToFile("TextFiles/StudentGPA.txt");
    }//GEN-LAST:event_btnsaverecordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btnsaverecord;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel dashboard1;
    private javax.swing.JButton jButtonrefresh;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelclassesstest;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
