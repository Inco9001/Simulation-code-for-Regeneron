package Regeneron;
import Regeneron.assets.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileWriter;

public class main_simulator {
    public static Scanner testFileInput;
    public static Scanner input = new Scanner(System.in);
    public static FileWriter output;
    public static double vø; // ball initialVelocity
    public static double launch_angle; // ball launch angle in radians
    public static double cone_angle; // slop of the cone in radians
    public static double yø; //starting y coord of ball
    public static double xø; // starting x coord of ball
    public static double slope; // Wedge slope
    public static int numiterations;
    public static double yAcceleration; // the ay of the simulation
    //double xAcceleration = testFileInput.nextDouble(); // the ax of the simulation
    public static double vx; // starting x velocity
    public static double vy; // starting y velocity
    public static void main(String[] args) {
        System.out.print("how many files do you want to run: ");
        int filesToRun = input.nextInt();
        inputConfig.verifyFilesToRun(filesToRun);
        System.out.print("where do you want to start (begins at 1): ");
        int fileStartNum = input.nextInt();
        inputConfig.verifyFileStartNum(fileStartNum, filesToRun);
        for (int i = fileStartNum; i < fileStartNum+filesToRun; i++) {
            changeFiles.changeFileInput(i);
            changeFiles.changeFileOutput(i);
            runSimulation();
        }
    }

    public static void runSimulation() {
        //double m = testFileInput.nextDouble(); //ball mass
        vø = testFileInput.nextDouble(); // ball initialVelocity
        launch_angle = Math.toRadians(testFileInput.nextDouble()); // ball launch angle in radians
        cone_angle = Math.toRadians(testFileInput.nextDouble()); // cone angle
        yø = testFileInput.nextDouble(); //starting y coord of ball
        xø = testFileInput.nextDouble(); // starting x coord of ball
        numiterations = testFileInput.nextInt();
        yAcceleration = testFileInput.nextDouble(); // the ay of the simulation
        //double xAcceleration = testFileInput.nextDouble(); // the ax of the simulation
        slope = Math.tan(cone_angle);
        vx = vø * Math.cos(launch_angle); // starting x velocity
        vy = vø * Math.sin(launch_angle); // starting y velocity
        testFileInput.close();
        // The while loop continues as long as the current time is less than the end time
        // System.out.println(yø + " " + xø);
        // numiterations = 3;

        while (numiterations > 0) {
            simulateBounce(yø, xø, slope, vx, vy);
            numiterations -= 1;
        }
        try {output.close();} catch (IOException e){}
        // System.out.println(yø + " " + xø);
    }
        

public static void simulateBounce(double yStart, double xStart, double slope, double currentVx, double currentVy) {
    double yAcc = (xStart > 0) ? yAcceleration:0;
    double totalTime = calculate.timeOfBounce(xStart, yStart, slope, currentVx, currentVy, yAcc);
    double changeTime = -xStart/currentVx;
    changeTime = (changeTime < 0) ? Double.MAX_VALUE: changeTime;
    // We will update these variables during the loop
    double currentX = xStart;
    double currentY = yStart;
    double timeUsed = Math.min(totalTime, changeTime);

    for (int i = 1; i < 100; i++){
        double t = (timeUsed)/(100.0);
        
        // Use the kinematic equations relative to the START of the bounce
        currentX = currentX + currentVx * t;
        currentY = currentY + (currentVy * t) + (0.5 * yAcc * t * t);
        currentVy = currentVy + (yAcc * t);


        recordData(t, currentVx, currentVy, currentX, currentY);
    }
    // currentX = currentX + currentVx * timeUsed;
    // currentY = currentY + (currentVy * timeUsed) + (0.5 * yAcc * timeUsed * timeUsed);
    // currentVy = currentVy + (yAcc * timeUsed);
    // recordData(timeUsed, currentVx, currentVy, currentX, currentY);

    if (changeTime < totalTime){
        yAcc = (yAcc == 0) ? yAcceleration : 0;
        totalTime = calculate.timeOfBounce(currentX, currentY, slope, currentVx, currentVy, yAcc);
        for (int i = 1; i < 100; i++){
            double t = (totalTime)/(100.0);
            
            // Use the kinematic equations relative to the START of the bounce
            currentX = currentX + currentVx * t;
            currentY = currentY + (currentVy * t) + (0.5 * yAcc * t * t);
            currentVy = currentVy + (yAcc * t);


            recordData(t, currentVx, currentVy, currentX, currentY);
        }
        // currentX = currentX + currentVx * totalTime;
        // currentY = currentY + (currentVy * totalTime) + (0.5 * yAcc * totalTime * totalTime);
        // currentVy = currentVy + (yAcc * totalTime);
        // recordData(totalTime, currentVx, currentVy, currentX, currentY);

    }
    recordData(totalTime, currentVx, currentVy, currentX, currentY);

    double currentSlope = (currentX < 0) ? -Math.abs(slope) : Math.abs(slope);
    double bottom = Math.sqrt(currentSlope * currentSlope + 1.0);
    double nx = -currentSlope / bottom;
    double ny = 1.0 / bottom;
    double dotProduct = (currentVx * nx) + (currentVy * ny);
 
    if (dotProduct < 0) {
        currentVx = currentVx - 2 * dotProduct * nx;
        currentVy = currentVy - 2 * dotProduct * ny;
    }
        updateVariables(currentY, currentX, currentVx, currentVy);

    }


    public static void updateVariables(double y, double x, double newî, double newĵ) {
        yø = y;
        xø = x;
        vx = newî;
        vy = newĵ;
    }



    public static void recordData(double time, double î, double ĵ, double xø, double yø  ) {
        try {
            output.write("time: " + (time) + " | " );
            output.write("speed: " + î+"î + " + ĵ + "ĵ | " );
            output.write("position: ( " + xø +" , "+ yø + " ) |\n" );
        } catch (IOException e) {
        }
    }
}