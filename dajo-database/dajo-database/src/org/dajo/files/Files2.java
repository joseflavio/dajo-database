package org.dajo.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Files2 {

    public static boolean createDirectoryIfDoesntExist(final Path path) {
        if (Files.exists(path)) {
            return true;
        }
        try {
            Files.createDirectory(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}// class
