package Regeneron.assets;

public class calculate {
public static double timeOfBounce(double x, double y, double slope, double î, double ĵ, double acceleration) {

    // System.out.println(x);
    // System.out.println(y);
    // System.out.println(slope);
    // System.out.println(î);
    // System.out.println(ĵ);
    // System.out.println(acceleration);
    double epsilon = 1e-7; 

    if( acceleration == 0) {
        double time1 = (slope * x - y)/(ĵ- slope * î);
        double time2 = (-slope * x - y)/(ĵ + slope * î);
        time1 = (Double.isNaN(time1) || time1 < epsilon) ? Double.MAX_VALUE: time1;
        time2 = (Double.isNaN(time2) || time2 < epsilon) ? Double.MAX_VALUE: time2;
        return (time1 < time2) ? time1: time2;
    }
    double[] times = new double[4];
    // Initialize with -1 to represent "no valid collision"
    for(int k=0; k<4; k++) times[k] = -1;

    // Line 1: y = slope * x  => (y - slope * x) = 0
    double top1 = -(ĵ - slope * î);
    double inner1 = Math.pow(top1, 2) - 2 * acceleration * (y - slope * x);
    
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

    for (int i = 0; i < 4; i++) {
        if (!Double.isNaN(times[i]) && times[i] > epsilon && times[i] < time) {
            time = times[i];
        }
    }
    // System.out.println(time);

    return time;
    }


    public static double timeOfBounce(double x, double y, double slope, double î, double ĵ, double accelerationY, double accelerationX) {
        double[] times = new double[4];
        double top1 = -(ĵ - slope * î);
        double top2 = -(ĵ + slope * î);
        double acceleration1 = accelerationY + accelerationX;
        double acceleration2 = accelerationY - accelerationX;
        double discriminate1 = Math.sqrt(Math.pow(top1, 2)-2*acceleration1*(y - slope*x));
        double discriminate2 = Math.sqrt(Math.pow(top2, 2)-2*acceleration2*(y + slope*x));
        times[0] = (top1 - discriminate1)/acceleration1;
        times[1] = (top1 + discriminate1)/acceleration1;
        times[2] = (top2 - discriminate2)/acceleration2;
        times[3] = (top2 + discriminate2)/acceleration2;
        double time = Double.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (times[i] > 0 && times[i] < time) {
                time = times[i];
            }
        }
        return time;
    }


    public static double correctSlope(double slope, double time, double acceleration, double ĵ, double y) {
        slope = Math.abs(slope);
        if (y + ĵ*time + acceleration*Math.pow(time,2) < 0) {
            slope = -slope;
        }
        return slope;
    }
}