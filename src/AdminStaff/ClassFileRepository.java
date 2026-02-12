/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

package AdminStaff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClassFileRepository {
    private final String filePath;

    public ClassFileRepository(String filePath) {
        this.filePath = filePath;
    }

    private File ensureFile() throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        if (!file.exists()) file.createNewFile();
        return file;
    }

    public List<ClassRoom> readAll() throws IOException {
        File file = ensureFile();
        List<ClassRoom> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                ClassRoom c = ClassRoom.fromLine(line);
                if (c != null) list.add(c);
            }
        }
        return list;
    }

    public void append(ClassRoom c) throws IOException {
        File file = ensureFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(c.toLine());
            bw.newLine();
        }
    }

    public void overwriteAll(List<ClassRoom> classes) throws IOException {
        File file = ensureFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (ClassRoom c : classes) {
                bw.write(c.toLine());
                bw.newLine();
            }
        }
    }

    public void deleteById(String classId) throws IOException {
        List<ClassRoom> all = readAll();
        List<ClassRoom> kept = new ArrayList<>();

        for (ClassRoom c : all) {
            if (!c.getClassId().equalsIgnoreCase(classId)) {
                kept.add(c);
            }
        }
        overwriteAll(kept);
    }

    public List<ClassRoom> search(String keyword) throws IOException {
        String k = keyword.trim().toLowerCase();
        List<ClassRoom> result = new ArrayList<>();

        for (ClassRoom c : readAll()) {
            if (c.getClassId().toLowerCase().contains(k)
                    || c.getClassName().toLowerCase().contains(k)) {
                result.add(c);
            }
        }
        return result;
    }

    public String getNextClassId() throws IOException {
        List<ClassRoom> all = readAll();

        int max = 0;
        for (ClassRoom c : all) {
            String id = c.getClassId(); // e.g. C001
            if (id != null && id.startsWith("C")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > max) max = num;
                } catch (NumberFormatException ignore) { }
            }
        }

        int next = max + 1;
        return "C" + String.format("%03d", next);
    }
}