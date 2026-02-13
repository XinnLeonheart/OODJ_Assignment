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

    public List<ClassLearning> readAll() throws IOException {
        File file = ensureFile();
        List<ClassLearning> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                ClassLearning c = ClassLearning.fromLine(line);
                if (c != null) list.add(c);
            }
        }
        return list;
    }

    public void append(ClassLearning c) throws IOException {
        File file = ensureFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(c.toLine());
            bw.newLine();
        }
    }

    public void overwriteAll(List<ClassLearning> classes) throws IOException {
        File file = ensureFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (ClassLearning c : classes) {
                bw.write(c.toLine());
                bw.newLine();
            }
        }
    }

    public void deleteById(String classId) throws IOException {
        List<ClassLearning> all = readAll();
        List<ClassLearning> kept = new ArrayList<>();

        for (ClassLearning c : all) {
            if (!c.getClassId().equalsIgnoreCase(classId)) {
                kept.add(c);
            }
        }
        overwriteAll(kept);
    }

    public List<ClassLearning> search(String keyword) throws IOException {
        String k = keyword.trim().toLowerCase();
        List<ClassLearning> result = new ArrayList<>();

        for (ClassLearning c : readAll()) {
            if (c.getClassId().toLowerCase().contains(k)
                    || c.getClassName().toLowerCase().contains(k)) {
                result.add(c);
            }
        }
        return result;
    }

    public String getNextClassId() throws IOException {
        List<ClassLearning> all = readAll();

        int max = 0;
        for (ClassLearning c : all) {
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