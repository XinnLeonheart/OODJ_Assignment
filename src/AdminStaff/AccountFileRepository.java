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
import java.util.ArrayList;
import java.util.List;

public class AccountFileRepository {

    private final String filePath;

    public AccountFileRepository(String filePath) {
        this.filePath = filePath;
    }

    // READ ALL ACCOUNTS
    public List<Account> readAll() throws IOException {
        List<Account> accounts = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent != null) parent.mkdirs();
            file.createNewFile();
            return accounts;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] data = line.split(";");
                if (data.length < 9) continue;

                Account acc = new Account(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].trim(),
                        data[3].trim(),
                        data[4].trim(),
                        data[5].trim(),
                        data[6].trim(),
                        data[7].trim(),
                        data[8].trim()
                );

                accounts.add(acc);
            }
        }

        return accounts;
    }

    // ADD ACCOUNT
    public void add(Account acc) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        if (!file.exists()) file.createNewFile();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(toLine(acc));
            bw.newLine();
        }
    }

    // UPDATE ACCOUNT
    public void update(Account updated) throws IOException {
        List<Account> all = readAll();
        boolean found = false;

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getAccID().equalsIgnoreCase(updated.getAccID())) {
                all.set(i, updated);
                found = true;
                break;
            }
        }

        if (!found) {
            throw new IOException("Account ID not found: " + updated.getAccID());
        }

        writeAll(all);
    }

    // DELETE ACCOUNT
    public void delete(String accID) throws IOException {
        List<Account> all = readAll();
        boolean removed = all.removeIf(a ->
                a.getAccID().equalsIgnoreCase(accID));

        if (!removed) {
            throw new IOException("Account ID not found: " + accID);
        }

        writeAll(all);
    }

    // AUTO GENERATE ID
    public String getNextAccId() throws IOException {
        String prefix = "ACC";
        int max = 0;

        for (Account a : readAll()) {
            String id = a.getAccID();
            if (id == null) continue;

            id = id.trim();
            if (!id.startsWith(prefix)) continue;

            String numPart = id.substring(prefix.length());
            try {
                int n = Integer.parseInt(numPart);
                if (n > max) max = n;
            } catch (NumberFormatException ignore) {}
        }

        return prefix + String.format("%03d", max + 1);
    }

    // WRITE ALL (Helper)
    private void writeAll(List<Account> accounts) throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        if (!file.exists()) file.createNewFile();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (Account acc : accounts) {
                bw.write(toLine(acc));
                bw.newLine();
            }
        }
    }

    // CONVERT ACCOUNT TO TEXT LINE
    private String toLine(Account a) {
        return a.getAccID() + ";" +
               a.getUserName() + ";" +
               a.getName() + ";" +
               a.getEmail() + ";" +
               a.getPassword() + ";" +
               a.getPhone() + ";" +
               a.getGender() + ";" +
               a.getAge() + ";" +
               a.getRole() + ";";
    }
}

