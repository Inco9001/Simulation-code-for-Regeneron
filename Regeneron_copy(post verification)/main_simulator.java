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
    public static double starting_energy;
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
        starting_energy = vx*vx + vy*vy + 2*Math.abs(yAcceleration)*yø;
        testFileInput.close();

        recordData(0, vx, vy, xø, yø);


        while (numiterations > 0) {
            simulateBounce(yø, xø, slope, vx, vy, yAcceleration);
            numiterations -= 1;
        }
        try {output.close();} catch (IOException e){}
        // System.out.println(yø + " " + xø);
    }
        

public static void simulateBounce(double yStart, double xStart, double slope, double currentVx, double currentVy, double yAcc) {
    double totalTime = calculate.timeOfBounce(xStart, yStart, slope, currentVx, currentVy, yAcc);
    
    double currentX = xStart;
    double currentY = yStart;
    // for (int i = 1; i < 100; i++){
    //     double t = (totalTime)/(100.0);
        
    //     // Use the kinematic equations relative to the START of the bounce
    //     currentX = currentX + currentVx * t;
    //     currentY = currentY + (currentVy * t) + (0.5 * yAcc * t * t);
    //     currentVy = currentVy + (yAcc * t);


    //     recordData(t, currentVx, currentVy, currentX, currentY);
    // }
    currentX = xStart + currentVx * totalTime;
    currentY = yStart + (currentVy * totalTime) + (0.5 * yAcc * totalTime * totalTime);
    currentVy = currentVy + (yAcc * totalTime);
    
    double energyChange = starting_energy - currentVx*currentVx - currentVy*currentVy + 2*yAcc*currentY;
    if (currentVy < 0){
        currentVy = 0 - Math.sqrt(currentVy*currentVy + energyChange);
    }else {
        currentVy = Math.sqrt(currentVy*currentVy + energyChange );

    }
    // if(energyChange < 0){
    //     currentVy = Math.sqrt(currentVy*currentVy - + energyChange );
    // } else {
    //     currentVy += Math.sqrt(energyChange);
    // }
    // System.out.println("intitial: " + energyChange + " | Fixed: " + (starting_energy - currentVx*currentVx - currentVy*currentVy - 2*9.81*currentY) );
    // System.out.println(starting_energy);

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
        updateVariables(currentY, currentX, currentVx, currentVy, currentSlope);

    }


    public static void updateVariables(double y, double x, double newî, double newĵ, double newSlope) {
        yø = y;
        xø = x;
        vx = newî;
        vy = newĵ;
        slope = newSlope;
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