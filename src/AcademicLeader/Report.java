/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AcademicLeader;

/**
 *
 * @author 
 */
public class Report {
    private String reportID, academicLeaderID, reportType, referenceID, result;

    public Report(String reportID, String academicLeaderID,
                  String reportType, String referenceID, String result) {
        this.reportID = reportID;
        this.academicLeaderID = academicLeaderID;
        this.reportType = reportType;
        this.referenceID = referenceID;
        this.result = result;
    }
}