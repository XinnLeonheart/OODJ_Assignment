/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author User
 */

public class ClassRoom {
    private String classId;
    private String className;
    private String module;

    public ClassRoom(String classId, String className, String module) {
        this.classId = classId;
        this.className = className;
        this.module = module;
    }

    public String getClassId() { 
        return classId; 
    }
    public String getClassName() { 
        return className; 
    }
    public String getModule() { 
        return module;
    }

    public void setClassName(String className) {
        this.className = className; 
    }
    public void setModule(String module) {
        this.module = module; 
    }

    // Convert object -> file line
    public String toLine() {
        return classId + ";" + className + ";" + module;
    }

    // Convert file line -> object
    public static ClassRoom fromLine(String line) {
        String[] data = line.split(";");
        if (data.length < 3) return null;
        return new ClassRoom(data[0].trim(), data[1].trim(), data[2].trim());
    }
}

