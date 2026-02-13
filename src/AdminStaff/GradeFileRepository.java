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
import java.util.*;

public class GradeFileRepository {
    private final String filePath;

    public GradeFileRepository(String filePath) {
        this.filePath = filePath;
    }

    private File ensureFile() throws IOException {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        if (!file.exists()) file.createNewFile();
        return file;
    }

    public List<GradeRecord> readAll() throws IOException {
        File file = ensureFile();
        List<GradeRecord> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; }
                line = line.trim();
                if (line.isEmpty()) continue;

                GradeRecord r = GradeRecord.fromLine(line);
                if (r != null) list.add(r);
            }
        }
        return list;
    }

    public void overwriteAllRawLines(List<String> lines) throws IOException {
        File file = ensureFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        }
    }
}
