package ExpensiveComputation;
import java.util.Random;


/**
 * Created by lucasmsp on 27/03/17.
 */
public class ExpensiveComputation {

    public static void main(String args[]) {


        int numFrag=4;
        // Run wordcount app
        computeCalcAll(numFrag);

    }

    private static void computeCalcAll(int numFrag) {


        // Compute result
        double result[][]= new  double[numFrag][2];
        System.out.println("[INFO] Computing result");
        for (int i = 0; i < numFrag; ++i) {
            result[i] = Calc();
        }
        int count =0;
        System.out.println("[END] ");

    }


    public static double[]  Calc() {
        long startTime = System.nanoTime();
        double[] c = new double[2];

        int Min = 2;
        int Max = 10;


        for (int j=0;j<150;j++)
            for (int i=0;i<1000000;i++){
                float n = Min + (int)(Math.random() * ((Max - Min) + 1)) ;
                c[0] += Math.pow(n,100) - Math.pow(n,100) + Math.tan(n-i) - Math.tan(n-i);
            }



        long estimatedTime0 = System.nanoTime() - startTime;
        double seconds0 = (double) estimatedTime0 / 1000000000.0;
        System.out.printf("[INFO] - Calc() -> Time elapsed: %.2f seconds\n",seconds0);

        return c;
    }

}
