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
public class LecturerClassess extends FeedbackandGPA2 {
    
    CreateNewTest createNewTest;
    ViewClassessTest viewClassessTest;
    GradeClassessTest gradeClassessTest;

    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    //Add these instance variables to store the value
    private String lecturerName;
    private String lecturerClassId;
;
    
    
    /**
     * Creates new form LecturerClassess
     */
    public LecturerClassess(String lecturerName, String classId) {
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
        
        createNewTest = new CreateNewTest(this);
        viewClassessTest = new ViewClassessTest(this);
        gradeClassessTest = new GradeClassessTest(this);

        
        initSharedComponents();  // Initialize StudentGPA and ViewFeedbacks

        cardPanel.add(dashboard, "DASHBOARD");
        cardPanel.add(createNewTest, "Create_Test");
        cardPanel.add(viewClassessTest, "View_Test");
        cardPanel.add(gradeClassessTest, "Grade_Test");
        cardPanel.add(viewFeedbacks, "Feedbacks");//from parent
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
    
    public void showCreateTest() {
        cardLayout.show(cardPanel, "Create_Test");
    }
    
    public void showViewTest() {
        cardLayout.show(cardPanel, "View_Test");
    }
    
    public void showGradeTest() {
        cardLayout.show(cardPanel, "Grade_Test");
    }
    
    public void showFeedbacks() {
        cardLayout.show(cardPanel, "Feedbacks");  // SHOW FEEDBACKANDGPA
    }
    
    public void showStudentGPA(){
        cardLayout.show(cardPanel, "GPA");  // SHOW FEEDBACKANDGPA

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

        dashboard = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        All = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnMon = new javax.swing.JButton();
        btnTue = new javax.swing.JButton();
        Wed = new javax.swing.JButton();
        btnThu = new javax.swing.JButton();
        btnFri = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btngradeclassesstest = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnCreateNewTest = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnViewFeedbacks = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnViewClassessTest = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnstudentgpa = new javax.swing.JLabel();

        dashboard.setBackground(new java.awt.Color(228, 228, 228));
        dashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Leelawadee UI", 1, 24)); // NOI18N
        jLabel6.setText("Classess LOLIPOP ");
        dashboard.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 23, 283, -1));

        jLabel5.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel5.setText("January 20, 2025");
        dashboard.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 23, -1, -1));

        jLabel17.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        jLabel17.setText("04:00 AM");
        dashboard.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(682, 45, 80, -1));

        jLabel1.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabel1.setText("Courses");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
        jTable1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(jTable1);

        All.setText("All");
        All.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnMon.setText("Mon");
        btnMon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMonActionPerformed(evt);
            }
        });

        btnTue.setText("Tue");
        btnTue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTueActionPerformed(evt);
            }
        });

        Wed.setText("Wed");
        Wed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WedActionPerformed(evt);
            }
        });

        btnThu.setText("Thu");
        btnThu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThuActionPerformed(evt);
            }
        });

        btnFri.setText("Fri");
        btnFri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFriActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(All)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Wed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFri))
                    .addComponent(jButton1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 676, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(All)
                    .addComponent(btnMon)
                    .addComponent(btnTue)
                    .addComponent(Wed)
                    .addComponent(btnThu)
                    .addComponent(btnFri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        dashboard.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 730, 250));

        btngradeclassesstest.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btngradeclassesstest.setText("Grade Classess Test");
        btngradeclassesstest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btngradeclassesstestMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btngradeclassesstest, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btngradeclassesstest, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, 40));

        btnCreateNewTest.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnCreateNewTest.setText("Create New Test");
        btnCreateNewTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCreateNewTestMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCreateNewTest, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCreateNewTest, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 40));

        btnViewFeedbacks.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnViewFeedbacks.setText("View Feedbacks");
        btnViewFeedbacks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewFeedbacksMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewFeedbacks, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewFeedbacks, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 100, -1, 40));

        jLabel2.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("Feedbacks");
        dashboard.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 70, -1, -1));

        jLabel3.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 18)); // NOI18N
        jLabel3.setText("Features");
        dashboard.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        btnViewClassessTest.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnViewClassessTest.setText("View Classes Test");
        btnViewClassessTest.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnViewClassessTestMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewClassessTest, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnViewClassessTest, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, 40));

        btnstudentgpa.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btnstudentgpa.setText("Student GPA");
        btnstudentgpa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnstudentgpaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnstudentgpa, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnstudentgpa, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllActionPerformed
        // TODO add your handling code here:
        filterTable("All");
    }//GEN-LAST:event_AllActionPerformed

    private void btnMonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMonActionPerformed
        // TODO add your handling code here:
        filterTable("Mon");
    }//GEN-LAST:event_btnMonActionPerformed

    private void btnTueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTueActionPerformed
        // TODO add your handling code here:
        filterTable("Tue");
    }//GEN-LAST:event_btnTueActionPerformed

    private void WedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WedActionPerformed
        // TODO add your handling code here:
        filterTable("Wed");
    }//GEN-LAST:event_WedActionPerformed

    private void btnThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThuActionPerformed
        // TODO add your handling code here:
        filterTable("Thu");
    }//GEN-LAST:event_btnThuActionPerformed

    private void btnFriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFriActionPerformed
        // TODO add your handling code here:
        filterTable("Fri");
    }//GEN-LAST:event_btnFriActionPerformed

    private void btnCreateNewTestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateNewTestMouseClicked
        // TODO add your handling code here:
        showCreateTest();
    }//GEN-LAST:event_btnCreateNewTestMouseClicked

    private void btnViewClassessTestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewClassessTestMouseClicked
        // TODO add your handling code here:
        showViewTest();
    }//GEN-LAST:event_btnViewClassessTestMouseClicked

    private void btngradeclassesstestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngradeclassesstestMouseClicked
        // TODO add your handling code here:
        showGradeTest();
    }//GEN-LAST:event_btngradeclassesstestMouseClicked

    private void btnViewFeedbacksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewFeedbacksMouseClicked
        // TODO add your handling code here:
        showFeedbacks();
    }//GEN-LAST:event_btnViewFeedbacksMouseClicked

    private void btnstudentgpaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnstudentgpaMouseClicked
        // TODO add your handling code here:
        showStudentGPA();
    }//GEN-LAST:event_btnstudentgpaMouseClicked

    private void jTable1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1AncestorAdded


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton All;
    private javax.swing.JButton Wed;
    private javax.swing.JLabel btnCreateNewTest;
    private javax.swing.JButton btnFri;
    private javax.swing.JButton btnMon;
    private javax.swing.JButton btnThu;
    private javax.swing.JButton btnTue;
    private javax.swing.JLabel btnViewClassessTest;
    private javax.swing.JLabel btnViewFeedbacks;
    private javax.swing.JLabel btngradeclassesstest;
    private javax.swing.JLabel btnstudentgpa;
    private javax.swing.JPanel dashboard;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
