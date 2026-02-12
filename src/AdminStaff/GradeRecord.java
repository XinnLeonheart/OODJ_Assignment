/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author User
 */

import LogIn.LogIn;
import Navigation.NavigateToDashboard;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GradeRecord {
    private String studentId;
    private String studentName;
    private String classId;
    private String testName;
    private double mark;
    private String grade;

    public GradeRecord(String studentId, String studentName, String classId,
                       String testName, double mark, String grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.classId = classId;
        this.testName = testName;
        this.mark = mark;
        this.grade = grade;
    }

    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getClassId() { return classId; }
    public String getTestName() { return testName; }
    public double getMark() { return mark; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String toLine(String feedback, String timestamp) {
        return studentName + ";" + classId + ";" + testName + ";" +
               String.format("%.0f%%", mark) + ";" + feedback + ";" +
               timestamp + ";" + grade;
    }

    public static GradeRecord fromLine(String line) {
        String[] parts = line.split(";", -1);
        if (parts.length < 6) return null;

        String studentName = parts[0].trim();
        String classId = parts[1].trim();
        String testName = parts[2].trim();

        String markStr = parts[3].trim().replace("%", "");
        double mark;
        try { mark = Double.parseDouble(markStr); }
        catch (NumberFormatException e) { return null; }

        String grade = (parts.length >= 7) ? parts[6].trim() : "";

        return new GradeRecord("N/A", studentName, classId, testName, mark, grade);
    }
}

