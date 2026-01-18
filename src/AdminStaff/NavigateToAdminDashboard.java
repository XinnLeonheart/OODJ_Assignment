/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author User
 */

import javax.swing.JFrame;

public class NavigateToAdminDashboard {
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
}
