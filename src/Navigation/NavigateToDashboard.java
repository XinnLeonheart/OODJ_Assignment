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
import AcademicLeader.User;
import AdminStaff.AdminStaffDashboard;
import Lecturer.LecturerMain;
import Student.StudentDashboard;
import javax.swing.JFrame;


public class NavigateToDashboard {
    public static void goToAdminStaffDashboard(JFrame currentFrame, String adminName) {
        // Open the AdminStaffDashboard
        AdminStaffDashboard dashboard = new AdminStaffDashboard(adminName);
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
    
    public static void goToAcademicLeaderDashboard(javax.swing.JFrame currentFrame, AcademicLeader.User user) {
        // Open the AdminStaffDashboard
        AcademicLeaderDashboard academicLeaderDashboard = new AcademicLeaderDashboard(user.accID);
        academicLeaderDashboard.setVisible(true);
        academicLeaderDashboard.setLocationRelativeTo(null); 

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    
    public static void goToLecturerMainPage(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        LecturerMain lecturerMainPage = new LecturerMain();
        lecturerMainPage.setLocationRelativeTo(null);
        lecturerMainPage.setVisible(true);

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }        
}
