/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Lecturer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;      // For writing to file
import java.io.FileWriter;          // For file writing
import java.io.FileNotFoundException; // For file not found exception
import java.util.ArrayList;         // For ArrayList class
import java.util.List;              // For List interface

/**
 *
 * @author justl
 */
public class LecturerProfile extends FeedbackandGPA2 {
 
    private String lecturerName;
    private String lecturerCourseId;
    private String lecturerAccountId; // To identify the account in file
    private String lecturerPassword;
    /**
     * Creates new form LecturerProfile
     */
    public LecturerProfile(String lecturerName, String classId) {
        super(lecturerName, classId);// call parent
        
        //Store the values as instance variables
        this.lecturerName = lecturerName;
        this.lecturerClassId = classId;
        initComponents();
        
        // Load lecturer data from file
        loadLecturerData();
    }

    public String getClassId(){
        return this.lecturerClassId;
    }
    
    public String getLecturerName(){
        return this.lecturerName;
    }
    
    public String getLecturerCourseId(){
    // Return the course ID of the logged-in lecturer
    // This depends on how you store lecturer data
        return this.lecturerCourseId; // or however you access it
    }
    
    private void loadLecturerData() {
        try (BufferedReader br = new BufferedReader(new FileReader("Account.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                
                // Check if this is the current lecturer's record
                // Format: ACC001;ali;ali;ali@mail.uni.edu.my;ali;0012345678;Male;18;Admin Staff;
                if (parts.length >= 9) {
                    String name = parts[1]; // Name is at index 1
                    
                    if (name.equalsIgnoreCase(lecturerName)) {
                        lecturerAccountId = parts[0]; // Account ID
                        jTextFieldlecturename.setText(parts[1]); // Name
                        jPasswordField1.setText(parts[2]); // Password
                        lecturerPassword = parts[2];
                        jTextFieldclassid.setText(lecturerClassId); // Class ID
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "Account.txt file not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error reading account data: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateLecturerProfile() {
        List<String> lines = new ArrayList<>();
        boolean updated = false;
        
        try {
            // Read all lines from the file
            BufferedReader br = new BufferedReader(new FileReader("Account.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                
                // Check if this is the current lecturer's record
                if (parts.length >= 9 && parts[1].equalsIgnoreCase(lecturerName)) {
                    // Update the record with new values
                    String newName = jTextFieldlecturename.getText().trim();
                    String newPassword = new String(jPasswordField1.getPassword()).trim();
                    String newClassId = jTextFieldclassid.getText().trim();
                    
                    // Validate inputs
                    if (newName.isEmpty() || newPassword.isEmpty() || newClassId.isEmpty()) {
                        JOptionPane.showMessageDialog(this, 
                            "All fields must be filled!", 
                            "Validation Error", 
                            JOptionPane.WARNING_MESSAGE);
                        br.close();
                        return;
                    }
                    
                    // Reconstruct the line with updated data
                    parts[1] = newName; // Update name
                    parts[2] = newPassword; // Update password
                    // Note: Class ID is not typically stored in Account.txt
                    // If you need to store it, you may need to modify the file structure
                    
                    line = String.join(";", parts);
                    updated = true;
                    
                    // Update instance variables
                    this.lecturerName = newName;
                    this.lecturerPassword = newPassword;
                    this.lecturerClassId = newClassId;
                }
                lines.add(line);
            }
            br.close();
            
            if (!updated) {
                JOptionPane.showMessageDialog(this, 
                    "Lecturer record not found!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Write all lines back to the file
            BufferedWriter bw = new BufferedWriter(new FileWriter("Account.txt"));
            for (String updatedLine : lines) {
                bw.write(updatedLine);
                bw.newLine();
            }
            bw.close();
            
            JOptionPane.showMessageDialog(this, 
                "Profile updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, 
                "Account.txt file not found!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error updating profile: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
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

        dashboard = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldlecturename = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1logout = new javax.swing.JButton();
        jTextFieldclassid = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();

        dashboard.setBackground(new java.awt.Color(228, 228, 228));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        jLabel6.setText("Lecturer Profile");
        dashboard.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 23, 283, -1));

        jLabel5.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel5.setText("January 20, 2025");
        jLabel5.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel5AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        dashboard.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 23, -1, -1));

        jLabel17.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel17.setText("04:00 AM");
        jLabel17.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel17AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        dashboard.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 45, 80, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("Lecturer Name:");
        dashboard.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        jTextFieldlecturename.setText("lolipop");
        jTextFieldlecturename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldlecturenameActionPerformed(evt);
            }
        });
        dashboard.add(jTextFieldlecturename, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Lecturer Password:");
        dashboard.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Class ID:");
        dashboard.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, -1, 20));

        jButton1logout.setText("Log Out");
        jButton1logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1logoutActionPerformed(evt);
            }
        });
        dashboard.add(jButton1logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, -1));

        jTextFieldclassid.setText("C001");
        jTextFieldclassid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldclassidActionPerformed(evt);
            }
        });
        dashboard.add(jTextFieldclassid, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, -1, -1));

        jPasswordField1.setText("jPasswordField1");
        dashboard.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, -1, -1));

        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        dashboard.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel5AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel5AncestorAdded
        // TODO add your handling code here:
        Timer timer = new Timer(1000, new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               LocalDateTime now = LocalDateTime.now();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
               jLabel5.setText(formatter.format(now));
           }
       });
       timer.start();
    }//GEN-LAST:event_jLabel5AncestorAdded

    private void jLabel17AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel17AncestorAdded
        // TODO add your handling code here:
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                jLabel17.setText(formatter.format(now));
            }
        });
        timer.start();
    }//GEN-LAST:event_jLabel17AncestorAdded

    private void jButton1logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1logoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1logoutActionPerformed

    private void jTextFieldlecturenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldlecturenameActionPerformed
        // TODO add your handling code here:
        jTextFieldclassid.setText(lecturerName);
    }//GEN-LAST:event_jTextFieldlecturenameActionPerformed

    private void jTextFieldclassidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldclassidActionPerformed
        // TODO add your handling code here:
        jTextFieldclassid.setText(lecturerCourseId);
    }//GEN-LAST:event_jTextFieldclassidActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        updateLecturerProfile();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel dashboard;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton1logout;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextFieldclassid;
    private javax.swing.JTextField jTextFieldlecturename;
    // End of variables declaration//GEN-END:variables
}
