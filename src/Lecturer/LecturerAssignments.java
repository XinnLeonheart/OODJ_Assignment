/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Lecturer;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;
import javax.swing.JPanel;
import java.awt.CardLayout;

/**
 *
 * @author justl
 */
public class LecturerAssignments extends FeedbackandGPA2 {
    
    CreateNewAssignment createNewAssignment;
    ViewClassessAssignment viewClassessAssignment;
    GradeClassessAssignment gradeClassessAssignment;
    
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;
    
        //Add these instance variables to store the value
    private String lecturerName;
    private String lecturerClassId;
    /**
     * Creates new form LecturerAssignments
     */
    public LecturerAssignments(String lecturerName, String classId) {
        super(lecturerName, classId);// call parent
        
        //Store the values as instance variables
        this.lecturerName = lecturerName;
        this.lecturerClassId = classId;
        
        initComponents();
        loadTableData();
        setupTableSorter();
        setupCardLayout();
    }
    
    
    public String getClassId(){
        return this.lecturerClassId;
    }
    
    public String getLecturerName(){
        return this.lecturerName;
    }
    
    
    private void setupCardLayout(){
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        createNewAssignment = new CreateNewAssignment(this);
        viewClassessAssignment = new ViewClassessAssignment(this);
        gradeClassessAssignment = new GradeClassessAssignment(this);
       

        initSharedComponents();  // Initialize StudentGPA and ViewFeedbacks

        cardPanel.add(dashboard1, "DASHBOARD");
        cardPanel.add(createNewAssignment, "Create_Assignment");
        cardPanel.add(viewClassessAssignment, "View_Assignment");
        cardPanel.add(gradeClassessAssignment, "Grade_Assignment");
        cardPanel.add(viewFeedbacks, "Feedbacks");// from parent
        cardPanel.add(studentGPA, "GPA");//from parent
      
        this.setLayout(new java.awt.BorderLayout());
        this.removeAll();
        this.add(cardPanel, java.awt.BorderLayout.CENTER);
        
        cardLayout.show(cardPanel, "DASHBOARD");
    }
    
    public void showDashboard(){
        if (cardLayout != null && cardPanel != null) {
            cardLayout.show(cardPanel, "DASHBOARD");
        }
    }
    
    public void showCreateAssignment() {
        cardLayout.show(cardPanel, "Create_Assignment");
    }
    
    public void showViewAssignment() {
        cardLayout.show(cardPanel, "View_Assignment");
    }
    
    public void showGradeAssignment() {
        cardLayout.show(cardPanel, "Grade_Assignment");
    }
    
    public void showFeedbacks() {
        cardLayout.show(cardPanel, "Feedbacks");
    }
    
    public void showStudentGPA(){
        cardLayout.show(cardPanel, "GPA");
    }
    

//    public void ViewClassessTest(){
//        showViewTest();
//    }
    
    private void setupTableSorter(){
        model = (DefaultTableModel)jTable1.getModel();
        sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);
    }
    
    private void filterTable(String day){
        if(day.equals("All")){
            sorter.setRowFilter(null);
        }else{
            sorter.setRowFilter(RowFilter.regexFilter(day, 4));
        }
    }
    
    private void loadTableData(){
        String filePath = "src/TextFiles/lolicourse.txt";
        File file = new File(filePath);
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            //get the first line
            //get the columns name from the first line
            //set columns name to the jtable model
            String firstLine = br.readLine().trim();
            String[] columnsName = firstLine.split(";");
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            model.setColumnIdentifiers(columnsName);
            model.setRowCount(0);
            //get line from txt file
            Object[] tableLines = br.lines().toArray();

            //extract data from lines
            //set data to jtable model
            for(int i = 0; i < tableLines.length; i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(";");
                model.addRow(dataRow);
            }

        } catch (IOException ex) {
            System.getLogger(LecturerClassess.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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

        dashboard1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        All1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnMon1 = new javax.swing.JButton();
        btnTue1 = new javax.swing.JButton();
        Wed1 = new javax.swing.JButton();
        btnThu1 = new javax.swing.JButton();
        btnFri1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btngradeclassessassignment = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnCreateNewAssignment = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnViewFeedbacks1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnviewclassesassignment = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        btnstudentgpa = new javax.swing.JLabel();

        dashboard1.setBackground(new java.awt.Color(228, 228, 228));
        dashboard1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        jLabel7.setText("Assignment LOLIPOP ");
        dashboard1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 23, 283, -1));

        jLabel8.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel8.setText("January 20, 2025");
        dashboard1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 23, -1, -1));

        jLabel18.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel18.setText("04:00 AM");
        dashboard1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 45, 80, -1));

        jLabel4.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabel4.setText("Courses");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

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
        jScrollPane2.setViewportView(jTable1);

        All1.setText("All");
        All1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                All1ActionPerformed(evt);
            }
        });

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnMon1.setText("Mon");
        btnMon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMon1ActionPerformed(evt);
            }
        });

        btnTue1.setText("Tue");
        btnTue1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTue1ActionPerformed(evt);
            }
        });

        Wed1.setText("Wed");
        Wed1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Wed1ActionPerformed(evt);
            }
        });

        btnThu1.setText("Thu");
        btnThu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThu1ActionPerformed(evt);
            }
        });

        btnFri1.setText("Fri");
        btnFri1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFri1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(All1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMon1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTue1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Wed1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThu1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFri1))
                    .addComponent(jButton2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(All1)
                    .addComponent(btnMon1)
                    .addComponent(btnTue1)
                    .addComponent(Wed1)
                    .addComponent(btnThu1)
                    .addComponent(btnFri1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        dashboard1.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 730, 250));

        btngradeclassessassignment.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btngradeclassessassignment.setText("Grade Classess Assignment");
        btngradeclassessassignment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngradeclassessassignmentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btngradeclassessassignment, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btngradeclassessassignment, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 40));

        btnCreateNewAssignment.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnCreateNewAssignment.setText("Create New Assignment");
        btnCreateNewAssignment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCreateNewAssignmentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCreateNewAssignment, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCreateNewAssignment, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 40));

        btnViewFeedbacks1.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnViewFeedbacks1.setText("View Feedbacks");
        btnViewFeedbacks1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewFeedbacks1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewFeedbacks1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewFeedbacks1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, -1, 40));

        jLabel9.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabel9.setText("Feedbacks");
        dashboard1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, -1, -1));

        jLabel10.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabel10.setText("Features");
        dashboard1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        btnviewclassesassignment.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnviewclassesassignment.setText("View Classes Assignment");
        btnviewclassesassignment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnviewclassesassignmentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnviewclassesassignment, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnviewclassesassignment, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, 40));

        btnstudentgpa.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnstudentgpa.setText("Student GPA");
        btnstudentgpa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnstudentgpaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnstudentgpa, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnstudentgpa, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 810, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnstudentgpaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnstudentgpaMouseClicked
        // TODO add your handling code here:
        showStudentGPA();
    }//GEN-LAST:event_btnstudentgpaMouseClicked

    private void btnviewclassesassignmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnviewclassesassignmentMouseClicked
        // TODO add your handling code here:
        showViewAssignment();
    }//GEN-LAST:event_btnviewclassesassignmentMouseClicked

    private void btnViewFeedbacks1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewFeedbacks1MouseClicked
        // TODO add your handling code here:
        showFeedbacks();
    }//GEN-LAST:event_btnViewFeedbacks1MouseClicked

    private void btnCreateNewAssignmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateNewAssignmentMouseClicked
        // TODO add your handling code here:
        showCreateAssignment();
    }//GEN-LAST:event_btnCreateNewAssignmentMouseClicked

    private void btngradeclassessassignmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngradeclassessassignmentMouseClicked
        // TODO add your handling code here:
        showGradeAssignment();
    }//GEN-LAST:event_btngradeclassessassignmentMouseClicked

    private void btnFri1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFri1ActionPerformed
        // TODO add your handling code here:
        filterTable("Fri");
    }//GEN-LAST:event_btnFri1ActionPerformed

    private void btnThu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThu1ActionPerformed
        // TODO add your handling code here:
        filterTable("Thu");
    }//GEN-LAST:event_btnThu1ActionPerformed

    private void Wed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wed1ActionPerformed
        // TODO add your handling code here:
        filterTable("Wed");
    }//GEN-LAST:event_Wed1ActionPerformed

    private void btnTue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTue1ActionPerformed
        // TODO add your handling code here:
        filterTable("Tue");
    }//GEN-LAST:event_btnTue1ActionPerformed

    private void btnMon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMon1ActionPerformed
        // TODO add your handling code here:
        filterTable("Mon");
    }//GEN-LAST:event_btnMon1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void All1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_All1ActionPerformed
        // TODO add your handling code here:
        filterTable("All");
    }//GEN-LAST:event_All1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton All1;
    private javax.swing.JButton Wed1;
    private javax.swing.JLabel btnCreateNewAssignment;
    private javax.swing.JButton btnFri1;
    private javax.swing.JButton btnMon1;
    private javax.swing.JButton btnThu1;
    private javax.swing.JButton btnTue1;
    private javax.swing.JLabel btnViewFeedbacks1;
    private javax.swing.JLabel btngradeclassessassignment;
    private javax.swing.JLabel btnstudentgpa;
    private javax.swing.JLabel btnviewclassesassignment;
    private javax.swing.JPanel dashboard1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
