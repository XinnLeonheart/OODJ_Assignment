/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Navigation;

/**
 *
 * @author User
 */

import AdminStaff.AdminStaffDashboard;
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
        StudentDashboard studentDashboard = new AdminStaffDashboard();
        studentDashboard.setVisible(true);
        studentDashboard.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    
    public static void goToAcademicLeaderDashboard(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        AcademicLeaderDashboard academicLeaderDashboard = new AdminStaffDashboard();
        academicLeaderDashboard.setVisible(true);
        academicLeaderDashboard.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
    
    public static void goToLecturerDashboard(JFrame currentFrame) {
        // Open the AdminStaffDashboard
        LecturerDashboard lecturerDashboard = new AdminStaffDashboard();
        lecturerDashboard.setVisible(true);
        lecturerDashboard.setLocationRelativeTo(null); // center on screen

        // Close the current frame
        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }        
}
