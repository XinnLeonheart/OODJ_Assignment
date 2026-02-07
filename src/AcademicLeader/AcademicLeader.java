/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AcademicLeader;

/**
 *
 * @author 
 */
public class AcademicLeader {

    public static void main(String[] args) {
        // Fake Academic Leader ID for testing
        String testLeaderID = "AL001";

        javax.swing.SwingUtilities.invokeLater(() -> {
            new AcademicLeaderDashboard(testLeaderID).setVisible(true);
        });
    }
}