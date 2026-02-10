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

        cardPanel.add(dashboard, "DASHBOARD");
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

        jPanel1 = new javax.swing.JPanel();
        dashboard = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
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
        btndemostudentsubmit = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        dashboard1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        btndemostudentsubmit.setFont(new java.awt.Font("Leelawadee UI Semilight", 1, 12)); // NOI18N
        btndemostudentsubmit.setText("DEMO Student Submit");
        btndemostudentsubmit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btndemostudentsubmitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btndemostudentsubmit, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btndemostudentsubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        dashboard.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, -1, -1));

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

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(jTable2);

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

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 798, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dashboard1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllActionPerformed
        // TODO add your handling code here:
        filterTable("All");
    }//GEN-LAST:event_AllActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_jButton1ActionPerformed

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

    private void btngradeclassesstestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngradeclassesstestMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btngradeclassesstestMouseClicked

    private void btnCreateNewTestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateNewTestMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCreateNewTestMouseClicked

    private void btnViewFeedbacksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewFeedbacksMouseClicked
        // TODO add your handling code here:
        showFeedbacks();
    }//GEN-LAST:event_btnViewFeedbacksMouseClicked

    private void btnViewClassessTestMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewClassessTestMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnViewClassessTestMouseClicked

    private void btndemostudentsubmitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btndemostudentsubmitMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btndemostudentsubmitMouseClicked

    private void All1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_All1ActionPerformed
        // TODO add your handling code here:
        filterTable("All");
    }//GEN-LAST:event_All1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        loadTableData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnMon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMon1ActionPerformed
        // TODO add your handling code here:
        filterTable("Mon");
    }//GEN-LAST:event_btnMon1ActionPerformed

    private void btnTue1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTue1ActionPerformed
        // TODO add your handling code here:
        filterTable("Tue");
    }//GEN-LAST:event_btnTue1ActionPerformed

    private void Wed1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Wed1ActionPerformed
        // TODO add your handling code here:
        filterTable("Wed");
    }//GEN-LAST:event_Wed1ActionPerformed

    private void btnThu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThu1ActionPerformed
        // TODO add your handling code here:
        filterTable("Thu");
    }//GEN-LAST:event_btnThu1ActionPerformed

    private void btnFri1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFri1ActionPerformed
        // TODO add your handling code here:
        filterTable("Fri");
    }//GEN-LAST:event_btnFri1ActionPerformed

    private void btngradeclassessassignmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btngradeclassessassignmentMouseClicked
        // TODO add your handling code here:
        showGradeAssignment();
    }//GEN-LAST:event_btngradeclassessassignmentMouseClicked

    private void btnCreateNewAssignmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCreateNewAssignmentMouseClicked
        // TODO add your handling code here:
        showCreateAssignment();
    }//GEN-LAST:event_btnCreateNewAssignmentMouseClicked

    private void btnViewFeedbacks1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnViewFeedbacks1MouseClicked
        // TODO add your handling code here:
        showFeedbacks();
    }//GEN-LAST:event_btnViewFeedbacks1MouseClicked

    private void btnviewclassesassignmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnviewclassesassignmentMouseClicked
        // TODO add your handling code here:
        showViewAssignment();
    }//GEN-LAST:event_btnviewclassesassignmentMouseClicked

    private void btnstudentgpaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnstudentgpaMouseClicked
        // TODO add your handling code here:
        showStudentGPA();
    }//GEN-LAST:event_btnstudentgpaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton All;
    private javax.swing.JButton All1;
    private javax.swing.JButton Wed;
    private javax.swing.JButton Wed1;
    private javax.swing.JLabel btnCreateNewAssignment;
    private javax.swing.JLabel btnCreateNewTest;
    private javax.swing.JButton btnFri;
    private javax.swing.JButton btnFri1;
    private javax.swing.JButton btnMon;
    private javax.swing.JButton btnMon1;
    private javax.swing.JButton btnThu;
    private javax.swing.JButton btnThu1;
    private javax.swing.JButton btnTue;
    private javax.swing.JButton btnTue1;
    private javax.swing.JLabel btnViewClassessTest;
    private javax.swing.JLabel btnViewFeedbacks;
    private javax.swing.JLabel btnViewFeedbacks1;
    private javax.swing.JLabel btndemostudentsubmit;
    private javax.swing.JLabel btngradeclassessassignment;
    private javax.swing.JLabel btngradeclassesstest;
    private javax.swing.JLabel btnstudentgpa;
    private javax.swing.JLabel btnviewclassesassignment;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel dashboard1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
