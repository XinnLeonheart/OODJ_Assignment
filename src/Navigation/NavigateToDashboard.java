/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Navigation;

/**
 *
 * @author User
 */

import AcademicLeader.AcademicLeaderDashboard;
import AdminStaff.AdminStaffDashboard;
import Lecturer.LecturerMain;
import Student.StudentDashboard;
import javax.swing.JFrame;


public class NavigateToDashboard {
    public static void goToAdminStaffDashboard(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        AdminStaffDashboard dashboard = new AdminStaffDashboard();
        dashboard.setVisible(true);
        dashboard.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    
    public static void goToStudentDashboard(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        StudentDashboard studentDashboard = new StudentDashboard();
        studentDashboard.setVisible(true);
        studentDashboard.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    
    public static void goToAcademicLeaderDashboard(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        AcademicLeaderDashboard academicLeaderDashboard = new AcademicLeaderDashboard();
        academicLeaderDashboard.setVisible(true);
        academicLeaderDashboard.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    
    public static void goToLecturerMainPage(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        LecturerMain lecturerMainPage = new LecturerMain();
        LecturerMain.setVisible(true);
        LecturerMain.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }        
}
