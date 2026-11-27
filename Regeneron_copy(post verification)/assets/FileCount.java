package Regeneron.assets;
import java.io.File;

public class FileCount {
    public static int countFiles(String finalLocation) {
        String directoryPath = "Data/" + finalLocation;
        File directory = new File(directoryPath);
        System.out.println(directory.getAbsolutePath());
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                int fileCount = 0;
                for (File file : files) {
                    if (file.isFile()) {
                        fileCount++;
                    }
                }
                return fileCount;
            }
        } else {
            System.out.println("The specified path is not a valid directory or does not exist.");
        }
        return 0;
    }
}