package Navigation;

import LogIn.LogIn; 
import AdminStaff.AdminStaffDashboard;
import javax.swing.JFrame;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class NavigateToLogInPage {

    // static method, can be called from any JFrame
    public static void goToLogInPage(JFrame currentFrame) {
        LogIn logInPage = new LogIn();
        logInPage.setVisible(true);
        logInPage.setLocationRelativeTo(null); // center on screen

        if (currentFrame != null) {
            currentFrame.dispose();
        }
    }
}
