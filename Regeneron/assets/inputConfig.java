package Regeneron.assets;
import Regeneron.main_simulator;

public class inputConfig {
    private static final int NUM_FILES = FileCount.countFiles("input");
    public static void verifyFilesToRun(int filesToRun) {
        System.out.print(NUM_FILES);
        while (filesToRun < 1 || filesToRun > NUM_FILES) {
            if (filesToRun < 1) {
                System.out.print("you have to scan at least 1 file");
                System.out.print("how many files do you want to run: ");
                filesToRun = main_simulator.input.nextInt();
            } else if (filesToRun >NUM_FILES) {
                System.out.println("there are only " + NUM_FILES + " files!");
                System.out.print("how many files do you want to run: ");
                filesToRun = main_simulator.input.nextInt();
            }
        }
    }
    public static void verifyFileStartNum(int fileStartNum, int filesToRun){
        while (fileStartNum >= NUM_FILES || fileStartNum + filesToRun > NUM_FILES) {
            if (fileStartNum > FileCount.countFiles("input") ) {
                System.out.print("Error: input file does not exist");
                System.out.print("where do you want to start (begins at 1): ");
                fileStartNum = main_simulator.input.nextInt();
            } else if (filesToRun > NUM_FILES) {
                System.out.println("Error: exceed current number of inputFiles");
                System.out.print("where do you want to start (begins at 1): ");
                fileStartNum = main_simulator.input.nextInt();
            }
        }
    }
}
