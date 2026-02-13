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
        return studentId + ";" + studentName + ";" + classId + ";" + testName + ";" +
               String.format("%.0f", mark) + ";" + feedback + ";" + timestamp + ";" + grade;
    }


    public static GradeRecord fromLine(String line) {
        if (line == null) return null;
        String[] parts = line.split(";", -1);

        // must have at least: id, name, class, test, mark
        if (parts.length < 5) return null;

        String studentId = parts[0].trim();
        String studentName = parts[1].trim();
        String classId = parts[2].trim();
        String testName = parts[3].trim();

        String markStr = parts[4].trim().replace("%", "");
        double mark;
        try {
            mark = Double.parseDouble(markStr);
        } catch (NumberFormatException e) {
            return null;
        }

        // grade is last column if exists
        String grade = (parts.length >= 8) ? parts[7].trim() : "";

        return new GradeRecord(studentId, studentName, classId, testName, mark, grade);
    }
}

