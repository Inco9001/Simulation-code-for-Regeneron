package Regeneron.assets;

import java.io.File;

public class test {
    public static void main(String args[]) {
        double t = timeOfBounce(-5,8.66025403785,1.73205080757,2.59807621135,1.5,-1);
        System.out.print(t);
    }
public static double timeOfBounce(double x, double y, double slope, double î, double ĵ, double acceleration) {
    
    // Initialize with -1 to represent "no valid collision"
    double[] times = new double[4];
    for(int k=0; k<4; k++) times[k] = -1;

    // Line 1: y = slope * x  => (y - slope * x) = 0
    double top1 = -(ĵ - slope * î);
    double inner1 = Math.pow(top1, 2) -  2 * acceleration * (y - slope * x);
    
    // Line 2: y = -slope * x => (y + slope * x) = 0
    double top2 = -(ĵ + slope * î);
    double inner2 = Math.pow(top2, 2) - 2 * acceleration * (y + slope * x);

    // Only calculate sqrt if the ball can actually reach the line (discriminant >= 0)
    if (inner1 >= 0) {
        double disc1 = Math.sqrt(inner1);
        times[0] = (top1 - disc1) / acceleration;
        times[1] = (top1 + disc1) / acceleration;
    }

    if (inner2 >= 0) {
        double disc2 = Math.sqrt(inner2);
        times[2] = (top2 - disc2) / acceleration;
        times[3] = (top2 + disc2) / acceleration;
    }

    double time = Double.MAX_VALUE;
    double epsilon = 1e-7; 

    for (int i = 0; i < 4; i++) {
        // We only care about positive times in the future that aren't NaN
        if (!Double.isNaN(times[i]) && times[i] > epsilon && times[i] < time) {
            time = times[i];
        }
    }

    return time;
    }
}
