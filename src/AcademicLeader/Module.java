/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AcademicLeader;

/**
 *
 * @author 
 */
public class Module {
    private String moduleID, moduleName, academicLeaderID, lecturerID;
    
    public Module(String moduleID) {
        this.moduleID = moduleID;
    }
    
    public Module(String moduleID, String moduleName) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
    }
    
    public Module(String moduleID, String moduleName, String academicLeaderID) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.academicLeaderID = academicLeaderID;
    }
    
    public Module(String moduleID, String moduleName, String academicLeaderID, String lecturerID) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.academicLeaderID = academicLeaderID;
        this.lecturerID = lecturerID;
    }

    public String getModuleID() {
        return moduleID;
    }
    
    public String getModuleName() { 
        return moduleName; 
    }
    
    @Override
    public String toString() {
        return moduleName;
    }
}