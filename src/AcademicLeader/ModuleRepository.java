/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AcademicLeader;

/**
 *
 * @author User
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleRepository {

    private final Path filePath;

    public ModuleRepository(String fileName) {
        Path root = findProjectRootThatHasTextFiles();
        this.filePath = root.resolve("TextFiles").resolve(fileName);

        if (!Files.exists(filePath)) {
            throw new RuntimeException(
                "Module file NOT found: " + filePath.toAbsolutePath() +
                "\nPut Module.txt inside: " + root.resolve("TextFiles").toAbsolutePath()
            );
        }

        System.out.println("USING MODULE FILE: " + filePath.toAbsolutePath());
    }

    // Walk up until find the folder that actually contains TextFiles
    private Path findProjectRootThatHasTextFiles() {
        Path p = Paths.get(System.getProperty("user.dir")).toAbsolutePath();

        for (int i = 0; i < 20 && p != null; i++) {
            if (Files.isDirectory(p.resolve("TextFiles"))) {
                return p;
            }
            p = p.getParent();
        }

        throw new RuntimeException(
            "Cannot find 'TextFiles' folder by walking up from user.dir=" +
            System.getProperty("user.dir") +
            "\nMake sure your project has: <project>/TextFiles/"
        );
    }

    public List<String> readAllLines() throws IOException {
        return Files.readAllLines(filePath);
    }

    public void writeAllLines(List<String> lines) throws IOException {
        Files.write(filePath, lines, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public void appendLine(String line) throws IOException {
        Files.write(filePath, List.of(line), StandardOpenOption.APPEND);
    }

    public File getFile() {
        return filePath.toFile();
    }
}
   