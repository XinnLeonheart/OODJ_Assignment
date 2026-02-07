/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AcademicLeader;

/**
 *
 * @author 
 */
public class User {
    protected String accID, username, name, email, password, phone, gender, age, role;

    public User(String accID, String username, String name, String email,
                String password, String phone, String gender, String age, String role) {
        this.accID = accID;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }
}
