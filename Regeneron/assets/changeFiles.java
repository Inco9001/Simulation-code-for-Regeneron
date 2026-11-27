package Regeneron.assets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import Regeneron.main_simulator;

public class changeFiles {
        public static void changeFileInput(int file) {
        File inputFile = new File("Data/input/input" + file + ".in");
        try {
            main_simulator.testFileInput = new Scanner(inputFile);
        } catch (FileNotFoundException e){
            System.out.println("changeFileInput method is faulty");
        }
    }
    public static void changeFileOutput(int file) {
        File newFile = new File("Data/output/output" + file + ".out");
        try{
            main_simulator.output = new FileWriter(newFile);
        } catch (IOException e){
            System.out.println("changeFileOutput method is faulty");
        }
    }
}
