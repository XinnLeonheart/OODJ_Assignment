/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AdminStaff;

/**
 *
 * @author User
 */

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class LecturerAssignRepository {
    private final String filePath;

    public LecturerAssignRepository(String filePath) {
        this.filePath = filePath;
    }

    // Read all lecturer assignments: lecturerID -> academicLeaderName
    public Map<String, String> readAllAssignments() throws IOException {
        Map<String, String> map = new LinkedHashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null) parent.mkdirs();
            file.createNewFile();
            return map;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(";");
                // expected: lecturerID;lecturerName;lecturerEmail;academicLeader
                if (data.length < 4) continue;

                String lecturerID = data[0].trim();
                String academicLeader = data[3].trim();

                map.put(lecturerID, academicLeader);
            }
        }

        return map;
    }

    // Save all assignments back to file
    public void saveAllAssignments(Map<String, String> lecturerIdToLeader,
                                   AccountFileRepository accountRepo) throws IOException {

        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        if (!file.exists()) file.createNewFile();

        // Need lecturer name/email too -> read from Account repo
        Map<String, Account> lecturerAccounts = new LinkedHashMap<>();
        for (Account a : accountRepo.readAll()) {
            if (a.getRole() != null && a.getRole().equalsIgnoreCase("Lecturer")) {
                lecturerAccounts.put(a.getAccID(), a);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Map.Entry<String, String> e : lecturerIdToLeader.entrySet()) {
                String lecturerID = e.getKey();
                String leader = e.getValue();

                Account lecture = lecturerAccounts.get(lecturerID);
                if (lecture == null) continue; 

                String line = lecturerID + ";" +
                              lecture.getName() + ";" +
                              lecture.getEmail() + ";" +
                              leader;

                bw.write(line);
                bw.newLine();
            }
        }
    }
}
