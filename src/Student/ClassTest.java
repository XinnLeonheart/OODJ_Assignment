/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Student;

import LogIn.LogIn;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Xenia Thor
 */
public class ClassTest extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ClassTest.class.getName());

    private String classID;
    private String className;

    // Store loaded questions and answer selections
    private ArrayList<String[]> currentQuestions = new ArrayList<>();
    private ArrayList<ButtonGroup> answerGroups = new ArrayList<>();

    // Timer fields
    private javax.swing.Timer countdownTimer;
    private long testStartTime;
    private int remainingSeconds;
    private boolean timerActive = false;

    public ClassTest() {
        this.classID = LearningPage.currentClassID;
        this.className = LearningPage.currentClassName;
        initComponents();
        setupAfterInit();
    }

    public ClassTest(String classID, String className) {
        this.classID = classID;
        this.className = className;
        initComponents();
        setupAfterInit();
    }

    private void setupAfterInit() {
        lblTitle.setText("Class Test: " + className);
        setTitle("Class Test - " + className);
        loadClassTest();
        setLocationRelativeTo(null);
    }

    private void loadClassTest() {
        stopTimer();

        boolean locked = isClassLocked();

        if (locked) {
            lblStatus.setText("This class has been graded. You cannot retake the test.");
            lblStatus.setForeground(new Color(180, 0, 0));
            btnSubmit.setEnabled(false);
            loadQuestions(true);
            lblTimer.setText("No time limit");
            lblTimer.setForeground(Color.GRAY);
        } else {
            boolean alreadySubmitted = isAlreadySubmitted();
            if (alreadySubmitted) {
                lblStatus.setText("You have already submitted the class test for this class.");
                lblStatus.setForeground(new Color(180, 0, 0));
                btnSubmit.setEnabled(false);
                loadQuestions(true);
                lblTimer.setText("No time limit");
                lblTimer.setForeground(Color.GRAY);
            } else {
                lblStatus.setText("No submission yet for this class.");
                lblStatus.setForeground(Color.GRAY);
                btnSubmit.setEnabled(true);
                loadQuestions(false);

                int timerMinutes = getTimerDuration();
                if (timerMinutes > 0) {
                    startTimer(timerMinutes);
                } else {
                    lblTimer.setText("No time limit");
                    lblTimer.setForeground(Color.GRAY);
                }
            }
        }
    }

    private int getTimerDuration() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/Class"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 3 &&
                    parts[0].trim().equals(classID)) {
                    return Integer.parseInt(parts[2].trim());
                }
            }
        } catch (IOException | NumberFormatException e) {
            // File doesn't exist yet or invalid format
        }
        return 0;
    }

    private void startTimer(int minutes) {
        remainingSeconds = minutes * 60;
        testStartTime = System.currentTimeMillis();
        timerActive = true;
        updateTimerLabel();

        countdownTimer = new javax.swing.Timer(1000, e -> {
            remainingSeconds--;
            updateTimerLabel();
            if (remainingSeconds <= 0) {
                stopTimer();
                autoSubmitTest();
            }
        });
        countdownTimer.start();
    }

    private void stopTimer() {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        timerActive = false;
    }

    private void updateTimerLabel() {
        int mins = remainingSeconds / 60;
        int secs = remainingSeconds % 60;
        lblTimer.setText(String.format("Time Remaining: %02d:%02d", mins, secs));
        lblTimer.setForeground(Color.RED);
    }

    private void autoSubmitTest() {
        if (currentQuestions.isEmpty()) {
            return;
        }

        StringBuilder answers = new StringBuilder();
        for (int i = 0; i < answerGroups.size(); i++) {
            ButtonModel selected = answerGroups.get(i).getSelection();
            if (answers.length() > 0) {
                answers.append(",");
            }
            if (selected != null) {
                answers.append(selected.getActionCommand());
            } else {
                answers.append("-");
            }
        }

        long timeTakenSeconds = (System.currentTimeMillis() - testStartTime) / 1000;
        saveSubmission(answers.toString(), timeTakenSeconds);

        JOptionPane.showMessageDialog(this,
            "Time is up! Your test has been auto-submitted.",
            "Time Expired", JOptionPane.INFORMATION_MESSAGE);

        loadClassTest();
    }

    private boolean isClassLocked() {
        String studentID = LogIn.loggedInID;

        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/AssessmentMark"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split(";");
                if (parts.length >= 2 &&
                    parts[0].trim().equals(studentID) &&
                    parts[1].trim().equals(classID)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }

        return false;
    }

    private boolean isAlreadySubmitted() {
        String studentID = LogIn.accID;

        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/ClassTestSubmission"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 3 &&
                    parts[0].trim().equals(studentID) &&
                    parts[1].trim().equals(classID) &&
                    parts[2].trim().equals(className)) {
                    return true;
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }

        return false;
    }

    private void loadQuestions(boolean disabled) {
        questionsPanel.removeAll();
        currentQuestions.clear();
        answerGroups.clear();

        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/ClassTestQuestions"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 8 &&
                    parts[0].trim().equals(classID) &&
                    parts[1].trim().equals(className)) {
                    currentQuestions.add(parts);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading ClassTestQuestions file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (currentQuestions.isEmpty()) {
            JLabel noQ = new JLabel("No questions available for this class.");
            noQ.setFont(new Font("SansSerif", Font.ITALIC, 13));
            noQ.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            questionsPanel.add(noQ);
            btnSubmit.setEnabled(false);
        } else {
            for (int i = 0; i < currentQuestions.size(); i++) {
                String[] q = currentQuestions.get(i);
                questionsPanel.add(createQuestionPanel(i + 1, q, disabled));
                questionsPanel.add(Box.createVerticalStrut(10));
            }
        }

        questionsPanel.revalidate();
        questionsPanel.repaint();

        SwingUtilities.invokeLater(() -> questionsScrollPane.getVerticalScrollBar().setValue(0));
    }

    private JPanel createQuestionPanel(int qNumber, String[] questionData, boolean disabled) {
        String questionText = questionData[2].trim();
        String optA = questionData[3].trim();
        String optB = questionData[4].trim();
        String optC = questionData[5].trim();
        String optD = questionData[6].trim();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblQ = new JLabel("Q" + qNumber + ": " + questionText);
        lblQ.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblQ.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblQ);
        panel.add(Box.createVerticalStrut(5));

        ButtonGroup group = new ButtonGroup();
        String[] options = {"A", "B", "C", "D"};
        String[] optTexts = {optA, optB, optC, optD};

        for (int i = 0; i < 4; i++) {
            JRadioButton rb = new JRadioButton(options[i] + ". " + optTexts[i]);
            rb.setActionCommand(options[i]);
            rb.setFont(new Font("SansSerif", Font.PLAIN, 12));
            rb.setAlignmentX(Component.LEFT_ALIGNMENT);
            rb.setEnabled(!disabled);
            group.add(rb);
            panel.add(rb);
        }

        answerGroups.add(group);
        return panel;
    }

    private void submitTest() {
        if (currentQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions to submit.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder answers = new StringBuilder();
        for (int i = 0; i < answerGroups.size(); i++) {
            ButtonModel selected = answerGroups.get(i).getSelection();
            if (selected == null) {
                JOptionPane.showMessageDialog(this,
                    "Please answer all questions. Question " + (i + 1) + " is not answered.",
                    "Incomplete", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (answers.length() > 0) {
                answers.append(",");
            }
            answers.append(selected.getActionCommand());
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to submit your test for:\n" +
            "Class: " + className + "\n\n" +
            "Your lecturer will grade this test.",
            "Confirm Submission",
            JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        long timeTakenSeconds = timerActive ? (System.currentTimeMillis() - testStartTime) / 1000 : -1;
        stopTimer();
        saveSubmission(answers.toString(), timeTakenSeconds);

        JconfirmationClassTest confirmDialog = new JconfirmationClassTest(this, true, className);
        confirmDialog.setVisible(true);
    }

    private void saveSubmission(String answers, long timeTakenSeconds) {
        String studentID = LogIn.loggedInID;
        String studentName = LogIn.loggedInName;
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        ArrayList<String> existingLines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/ClassTestSubmission"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (!(parts.length >= 3 &&
                      parts[0].trim().equals(studentID) &&
                      parts[1].trim().equals(classID) &&
                      parts[2].trim().equals(className))) {
                    existingLines.add(line);
                }
            }
        } catch (IOException e) {
            // File doesn't exist yet
        }

        String newEntry = studentID + ";" + classID + ";" + className  + ";" + answers + ";" + dateTime + ";Submitted";
        if (timeTakenSeconds >= 0) {
            newEntry += ";" + timeTakenSeconds;
        }
        existingLines.add(newEntry);

        try (FileWriter fw = new FileWriter("src/TextFiles/ClassTestSubmission")) {
            for (String line : existingLines) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving submission: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        NotificationHelper.addNotification(studentID, "CLASSTEST",
             studentName + " has submitted class test for " + className + " (" + classID + ")");
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
        lblTitle = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        lblTimer = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        questionsScrollPane = new javax.swing.JScrollPane();
        questionsPanel = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Class Test");

        jPanel1.setBackground(new java.awt.Color(181, 172, 252));

        lblTitle.setFont(new java.awt.Font("Gabriola", 1, 24)); // NOI18N
        lblTitle.setText("Class Test");

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblTimer.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        lblTimer.setForeground(new java.awt.Color(128, 128, 128));
        lblTimer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTimer.setText("No time limit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(lblTimer, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTimer)
                    .addComponent(btnBack))
                .addGap(15, 15, 15))
        );

        lblStatus.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(128, 128, 128));
        lblStatus.setText(" ");

        questionsPanel.setLayout(new javax.swing.BoxLayout(questionsPanel, javax.swing.BoxLayout.Y_AXIS));
        questionsScrollPane.setViewportView(questionsPanel);

        btnSubmit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnSubmit.setText("Submit Test");
        btnSubmit.setPreferredSize(new java.awt.Dimension(150, 35));
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(questionsScrollPane)
                    .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(questionsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        stopTimer();
        LearningPage classPage = new LearningPage();
        classPage.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        submitTest();
    }//GEN-LAST:event_btnSubmitActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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

        java.awt.EventQueue.invokeLater(() -> new ClassTest().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTimer;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel questionsPanel;
    private javax.swing.JScrollPane questionsScrollPane;
    // End of variables declaration//GEN-END:variables
}
