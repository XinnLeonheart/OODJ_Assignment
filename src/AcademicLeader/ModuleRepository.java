/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AcademicLeader;

/**
 *
 * @author User
 */

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleRepository {

    private final Path filePath;

    public ModuleRepository(String fileName) {
        // fileName should be "Module.txt"
        Path root = findProjectRootWithTextFiles();
        this.filePath = root.resolve("TextFiles").resolve(fileName);

        try {
            Files.createDirectories(filePath.getParent());
            if (!Files.exists(filePath)) Files.createFile(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Cannot create/access: " + filePath.toAbsolutePath(), e);
        }
    }

    private Path findProjectRootWithTextFiles() {
        Path start = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

        // Walk up until we find a folder that contains TextFiles
        Path p = start;
        for (int i = 0; i < 10 && p != null; i++) {
            if (Files.isDirectory(p.resolve("TextFiles"))) {
                return p;
            }
            p = p.getParent();
        }

        // If still not found, hard fail (so you KNOW what's wrong)
        throw new RuntimeException(
            "Cannot find project root containing 'TextFiles' folder. user.dir=" + start
        );
    }

    public List<String> readAllLines() throws IOException {
        if (!Files.exists(filePath)) return new ArrayList<>();
        return Files.readAllLines(filePath);
    }

    public void writeAllLines(List<String> lines) throws IOException {
        Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
    }

    public void appendLine(String line) throws IOException {
        Files.write(filePath, List.of(line), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
    }

    public File getFile() {
        return filePath.toFile();
    }
}