/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import LogIn.LogIn;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 *
 * @author Xenia Thor
 */
public class Assessment extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(Assessment.class.getName());

    private final String filePath = "TextFiles/AssessmentMark.txt";
    private double overallCGPA = 0.0;
    private final java.util.Map<String, String> classIdToNameMap = new java.util.HashMap<>();

    public Assessment() {
        initComponents();
        loadClassNames();

        String studentID = LogIn.loggedInID;
        if (studentID == null || studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No logged-in student ID found!");
            return;
    }
    generateAssessmentMarkFromClassTestOnly(studentID);
    loadAssessmentMark(studentID);
    }

    private void loadClassNames() {
        try (BufferedReader br = new BufferedReader(new FileReader("TextFiles/Class.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(";");
                    if (parts.length >= 3) {
                        classIdToNameMap.put(parts[0].trim(), parts[2].trim());
                    }
                }
            }
        } catch (IOException e) {
            // File doesn't exist
        }
    }

    private final java.util.Map<String, java.util.List<Double>> classAssignmentMarks = new java.util.HashMap<>();
    private final java.util.Map<String, java.util.List<Double>> classTestMarks = new java.util.HashMap<>();

    

    private void loadAssessmentMark(String studentID) {

        DefaultTableModel model = (DefaultTableModel) jTableAssessment.getModel();
        model.setColumnIdentifiers(new String[] { "Class", "Test Name", "Test Marks", "Grade" });
        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            br.readLine(); // skip header in AssessmentMark.txt

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // AssessmentMark.txt format:
                // Class Id;Test Name;Test Marks;Grade
                String[] part = line.split(";", -1);
                if (part.length < 4) continue;

                String classId   = part[0].trim();
                String testName  = part[1].trim();
                String testMarks = part[2].trim();
                String grade     = part[3].trim();

                String className = classIdToNameMap.getOrDefault(classId, classId);

                model.addRow(new Object[] { className, testName, testMarks, grade });
            }

            label1.setText("Assessment Mark (Class Test)");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error reading assessment mark file: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


//    private void loadClassTestMarks(String studentID) {
//        classTestMarks.clear();
//        try (BufferedReader br = new BufferedReader(new FileReader("TextFiles/gradeclasstest.txt"))) {
//            String line;
//            br.readLine(); // Skip header
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(";");
//                // Student Name;Class Id;Test Name;Test Marks;Feedback;Timestamp
//                 if (parts.length >= 4) {
//                    String nameInFile = parts[0].trim();
//                     
//                    if (nameInFile.equalsIgnoreCase(studentID)) {
//                         String classId = parts[1].trim();
//                         // Check for % sign and remove it
//                         String markStr = parts[3].trim().replace("%", "");
//                         try {
//                            double mark = Double.parseDouble(markStr);
//                            classTestMarks.computeIfAbsent(classId, k -> new java.util.ArrayList<>()).add(mark);
//                         } catch (NumberFormatException e) {
//                             // ignore
//                         }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // Ignore
//        }
//    }

//    private String calculateAverage(java.util.List<Double> marks) {
//        if (marks == null || marks.isEmpty()) {
//            return "-";
//        }
//        double sum = 0;
//        for (Double m : marks) {
//            sum += m;
//        }
//        return String.format("%.0f", sum / marks.size());
//    }

    private void savePDF() {
        String downloadsPath = System.getProperty("user.home") + java.io.File.separator + "Downloads";
        String studentID = LogIn.loggedInID != null ? LogIn.loggedInID : "unknown";
        String fileName = "AssessmentReport_" + studentID + ".pdf";
        String fullPath = downloadsPath + java.io.File.separator + fileName;

        DefaultTableModel model = (DefaultTableModel) jTableAssessment.getModel();
        int rowCount = model.getRowCount();
        int colCount = model.getColumnCount();

        // Build the text content for the PDF page
        StringBuilder content = new StringBuilder();
        int pageHeight = 842; // A4
        int pageWidth = 595;

        // Title
        content.append("BT /F1 18 Tf 50 ").append(pageHeight - 50).append(" Td (Assessment Report) Tj ET\n");

        // Student info
        String studentInfo = "Student ID: " + studentID + "    Name: "
                + (LogIn.loggedInName != null ? LogIn.loggedInName : "");
        content.append("BT /F1 11 Tf 50 ").append(pageHeight - 80).append(" Td (").append(escapePdf(studentInfo))
                .append(") Tj ET\n");

        // CGPA
        String cgpaLine = "Overall CGPA: " + String.format("%.2f", overallCGPA);
        content.append("BT /F1 11 Tf 50 ").append(pageHeight - 100).append(" Td (").append(escapePdf(cgpaLine))
                .append(") Tj ET\n");

        // Table header line
        int tableTop = pageHeight - 135;
        int[] colX = { 50, 180, 280, 380, 460 };
        int colW = 150;

        // Draw header background
        content.append("0.85 0.85 0.85 rg\n");
        content.append("50 ").append(tableTop - 5).append(" 480 20 re f\n");
        content.append("0 0 0 rg\n");

        // Header text
        String[] headers = { "Class", "Asg Avg", "Test Avg", "Grade", "CGPA" };
        for (int c = 0; c < colCount && c < headers.length; c++) {
            content.append("BT /F2 11 Tf ").append(colX[c] + 5).append(" ").append(tableTop).append(" Td (")
                    .append(headers[c]).append(") Tj ET\n");
        }

        // Table rows
        int y = tableTop - 22;
        for (int r = 0; r < rowCount; r++) {
            // Alternating row background
            if (r % 2 == 0) {
                content.append("0.95 0.95 0.95 rg\n");
                content.append("50 ").append(y - 5).append(" 480 20 re f\n");
                content.append("0 0 0 rg\n");
            }
            for (int c = 0; c < colCount && c < colX.length; c++) {
                Object val = model.getValueAt(r, c);
                String text = val != null ? val.toString() : "";
                content.append("BT /F1 10 Tf ").append(colX[c] + 5).append(" ").append(y).append(" Td (")
                        .append(escapePdf(text)).append(") Tj ET\n");
            }
            y -= 22;
            if (y < 50)
                break; // page overflow guard
        }

        // Draw table border lines
        content.append("0.6 0.6 0.6 RG 0.5 w\n");
        // outer border
        int tableBottom = y + 17;
        content.append("50 ").append(tableTop + 15).append(" 480 ").append(tableBottom - tableTop - 15)
                .append(" re S\n");
        // header separator
        content.append("50 ").append(tableTop - 5).append(" m 530 ").append(tableTop - 5).append(" l S\n");
        // column separators
        // column separators
        content.append("180 ").append(tableTop + 15).append(" m 180 ").append(tableBottom).append(" l S\n");
        content.append("280 ").append(tableTop + 15).append(" m 280 ").append(tableBottom).append(" l S\n");
        content.append("380 ").append(tableTop + 15).append(" m 380 ").append(tableBottom).append(" l S\n");
        content.append("460 ").append(tableTop + 15).append(" m 460 ").append(tableBottom).append(" l S\n");

        // Build PDF structure
        String stream = content.toString();
        byte[] streamBytes = stream.getBytes(StandardCharsets.ISO_8859_1);

        try {
            ArrayList<Long> offsets = new ArrayList<>();
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();

            baos.write("%PDF-1.4\n".getBytes(StandardCharsets.ISO_8859_1));

            // Obj 1: Catalog
            offsets.add((long) baos.size());
            baos.write("1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n".getBytes(StandardCharsets.ISO_8859_1));

            // Obj 2: Pages
            offsets.add((long) baos.size());
            baos.write(
                    "2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n".getBytes(StandardCharsets.ISO_8859_1));

            // Obj 3: Page
            offsets.add((long) baos.size());
            String pageObj = "3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Contents 4 0 R /Resources << /Font << /F1 5 0 R /F2 6 0 R >> >> >> endobj\n";
            baos.write(pageObj.getBytes(StandardCharsets.ISO_8859_1));

            // Obj 4: Content stream
            offsets.add((long) baos.size());
            String streamHeader = "4 0 obj << /Length " + streamBytes.length + " >> stream\n";
            baos.write(streamHeader.getBytes(StandardCharsets.ISO_8859_1));
            baos.write(streamBytes);
            baos.write("\nendstream endobj\n".getBytes(StandardCharsets.ISO_8859_1));

            // Obj 5: Font (Helvetica)
            offsets.add((long) baos.size());
            baos.write("5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n"
                    .getBytes(StandardCharsets.ISO_8859_1));

            // Obj 6: Font Bold (Helvetica-Bold)
            offsets.add((long) baos.size());
            baos.write("6 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >> endobj\n"
                    .getBytes(StandardCharsets.ISO_8859_1));

            // Cross-reference table
            long xrefStart = baos.size();
            baos.write("xref\n".getBytes(StandardCharsets.ISO_8859_1));
            baos.write(("0 " + (offsets.size() + 1) + "\n").getBytes(StandardCharsets.ISO_8859_1));
            baos.write("0000000000 65535 f \n".getBytes(StandardCharsets.ISO_8859_1));
            for (Long offset : offsets) {
                baos.write(String.format("%010d 00000 n \n", offset).getBytes(StandardCharsets.ISO_8859_1));
            }

            // Trailer
            baos.write("trailer << /Size ".getBytes(StandardCharsets.ISO_8859_1));
            baos.write(String.valueOf(offsets.size() + 1).getBytes(StandardCharsets.ISO_8859_1));
            baos.write(" /Root 1 0 R >>\n".getBytes(StandardCharsets.ISO_8859_1));
            baos.write("startxref\n".getBytes(StandardCharsets.ISO_8859_1));
            baos.write(String.valueOf(xrefStart).getBytes(StandardCharsets.ISO_8859_1));
            baos.write("\n%%EOF\n".getBytes(StandardCharsets.ISO_8859_1));

            // Write to file
            try (FileOutputStream fos = new FileOutputStream(fullPath)) {
                baos.writeTo(fos);
            }

            JOptionPane.showMessageDialog(this,
                    "PDF saved successfully!\n" + fullPath,
                    "Save Complete", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving PDF: " + e.getMessage(),
                    "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String escapePdf(String text) {
        return text.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnOK = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAssessment = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        label1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnOK.setBackground(new java.awt.Color(181, 172, 252));
        btnOK.setText("OK");
        btnOK.setPreferredSize(new java.awt.Dimension(100, 30));
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(181, 172, 252));
        btnSave.setText("Save to File Download");
        btnSave.setPreferredSize(new java.awt.Dimension(120, 30));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        jTableAssessment.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jTableAssessment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class", "Test Name", "Test Marks", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableAssessment.setRowHeight(25);
        jScrollPane1.setViewportView(jTableAssessment);

        jPanel1.setBackground(new java.awt.Color(181, 172, 252));

        label1.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        label1.setText("Assessment Mark");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(label1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnOKActionPerformed
        StudentDashboard main = new StudentDashboard();
        main.setVisible(true);
        this.dispose();
    }// GEN-LAST:event_btnOKActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSaveActionPerformed
        savePDF();
    }// GEN-LAST:event_btnSaveActionPerformed

    
    private void generateAssessmentMarkFromClassTestOnly(String studentID) {

        String testFile = "TextFiles/gradeclasstest.txt";
        String outFile  = "TextFiles/AssessmentMark.txt";

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(testFile));
             java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(outFile, false))) {

            bw.write("Class Id;Test Name;Test Marks;Grade");
            bw.newLine();

            br.readLine(); // skip header in gradeclasstest.txt

            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(";", -1);
                if (p.length < 8) continue;

                String sid = p[0].trim();
                if (!sid.equalsIgnoreCase(studentID)) continue;

                String classId   = p[2].trim();
                String testName  = p[3].trim();
                String testMarks = p[4].trim().replace("%", "");
                String grade     = p[7].trim();

                bw.write(safe(classId) + ";" + safe(testName) + ";" + safe(testMarks) + ";" + safe(grade));
                bw.newLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error generating AssessmentMark.txt: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private String safe(String s) {
    if (s == null) return "";
        return s.replace(";", ",").trim();
    }

//private double averageOrZero(java.util.List<Double> list) {
//    if (list == null || list.isEmpty()) return 0.0;
//    double sum = 0.0;
//    for (double v : list) sum += v;
//    return sum / list.size();
//}
//
//private String toGrade(double mark) {
//    if (mark >= 80) return "A+";
//    if (mark >= 75) return "A";
//    if (mark >= 70) return "A-";
//    if (mark >= 65) return "B+";
//    if (mark >= 60) return "B";
//    if (mark >= 55) return "B-";
//    if (mark >= 50) return "C+";
//    if (mark >= 45) return "C";
//    if (mark >= 40) return "D";
//    return "F";
//}
//
//private double toGpaPoint(String grade) {
//    switch (grade) {
//        case "A+": return 4.00;
//        case "A":  return 4.00;
//        case "A-": return 3.67;
//        case "B+": return 3.33;
//        case "B":  return 3.00;
//        case "B-": return 2.67;
//        case "C+": return 2.33;
//        case "C":  return 2.00;
//        case "D":  return 1.00;
//        default:   return 0.00;
//    }
//}

    

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
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
        // </editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Assessment().setVisible(true));
            }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableAssessment;
    private javax.swing.JLabel label1;
    // End of variables declaration//GEN-END:variables
}
