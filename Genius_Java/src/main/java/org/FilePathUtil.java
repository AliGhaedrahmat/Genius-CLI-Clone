package org;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathUtil {

    private static String getProjectRoot() {
        try {
            Path currentPath = Paths.get("").toAbsolutePath();
            Path parentDirectory = currentPath.getParent();
            return parentDirectory.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDatabaseFilePath(String filename) {
        String path = getProjectRoot();
        path += File.separator + "Genius_Java"+File.separator+ "Database" + File.separator +filename+ ".json";

        return path;
    }
}
