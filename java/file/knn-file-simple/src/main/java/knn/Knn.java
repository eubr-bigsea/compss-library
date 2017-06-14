package knn;

/**
 *
 * Created by Lucas Miguel S Ponce.
 * Student of master degree in Computer's Science
 * UFMG - 1/2017
 * Algorithm:  K-NN (K Nearst Neighborhood)
 *
 *
 * Input: labels != 0
 *
 */

import java.io.*;
import java.util.StringTokenizer;

public class Knn {


    //loadfile_TestFile: to load all test records and split between N parts
    private static void loadfile_TestFile(double[][][] features, String fileName, int sizeTestPerFrag,  int numTotal) {

        long startTime = System.nanoTime();
        int garbage = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringTokenizer tokenizer;

            int index=0;
            int f = -1;
            String line;
            while ((line=br.readLine())!= null){

                if ((index % sizeTestPerFrag) == 0){
                    //System.out.printf("[INFO] loadfile_TestFile -> F %d - size %d \n",f,index); //to Debug
                    f++;
                    index=0;

                }

                tokenizer = new StringTokenizer(line,",");
                garbage = (int) Float.parseFloat(tokenizer.nextToken());
                int index2 = 0;
                while (tokenizer.hasMoreTokens()) {
                        features[f][index][index2] = Double.parseDouble(tokenizer.nextToken());
                        index2++;
                }

                index++;
            }


            long estimatedTime = System.nanoTime() - startTime;
            double seconds = (double)estimatedTime / 1000000000.0;
            System.out.printf("[INFO] loadfile_TestFile ->Time elapsed: %.2f seconds\n",seconds);

        } catch (FileNotFoundException e) {
            System.out.println("ERROR - KNN.loadfile_TestFile");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ERROR - KNN.loadfile_TestFile");
            e.printStackTrace();
        }
    }

    //loadfile_TrainFile: to load the training records
    private static void loadfile_TrainFile(double[][] features, int[] labels, String fileName, int numTotal) {

        long startTime = System.nanoTime();

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            StringTokenizer tokenizer;

            int index=0;
            String line;
            while ((line=br.readLine())!= null){
                tokenizer = new StringTokenizer(line,",");
                labels[index] = (int) Float.parseFloat(tokenizer.nextToken());
                int index2 = 0;
                while (tokenizer.hasMoreTokens()) {
                    features[index][index2] = Double.parseDouble(tokenizer.nextToken());
                    index2++;
                }
                index++;
            }
            br.close();

            long estimatedTime = System.nanoTime() - startTime;
            double seconds = (double)estimatedTime / 1000000000.0;
            System.out.printf("[INFO] loadfile_TrainFile  (%d lines) ->Time elapsed: %.2f seconds\n",index,seconds);

        } catch (FileNotFoundException e) {
            System.out.println("ERROR - KNN.loadfile_TrainFile");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ERROR - KNN.loadfile_TrainFile");
            e.printStackTrace();
        }
    }

    //distance: to calculate the distance between the record_a and the record_b
    public static double distance(double[] a, double[] b) {
        int sum = 0;
        for(int i = 0; i < a.length; i++) {
            sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(sum);
    }

    //getPopularElement: to choose the most popular element in the array
    public static int getPopularElement(int[] a){


        int count = 1, tempCount;
        int popular = a[0];
        int temp;
        for (int i = 0; i < (a.length - 2); i++){
            temp = a[i];
            tempCount = 0;
            for (int j = 1; j < (a.length -1); j++){
                if (temp == a[j])
                    tempCount++;
            }
            if (tempCount > count){
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    //getKN: do the prediction of one partition
    public static int[] getKN(int[][] neighborhood,int K){
        long startTime = System.nanoTime();

        int size = neighborhood.length;
        int[] result = new int[size];
        for (int i =0;i<size;i++){
            result[i] = getPopularElement(neighborhood[i]);
        }

        long estimatedTime0 = System.nanoTime() - startTime;
        double seconds0 = (double) estimatedTime0 / 1000000000.0;
        System.out.printf("\n[INFO] - getKN -> Time elapsed: %.2f seconds\n",seconds0);

        return result;
    }

    //savePredictionToFile: to save the predicition in a file
    public static void savePredictionToFile(int[] result, String filename){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
            for (int i = 0; i < result.length; i++) {
                if (result[i] != 0){
                    String value = result[i] + "\n";
                    fos.write(value.getBytes());
                }
            }
        } catch(IOException ioe) {
            System.out.println("[ERROR] - savePredictionToFile");
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("[ERROR] - savePredictionToFile");
                }
            }
        }

    }

    // ClassifyBlock: do the prediction
    public static int [] ClassifyBlock(double[][] test_features, int [] train_labels,
                                       double[][] train_features,int K, int sizeTestPerFrag){

        long startTime = System.nanoTime();

        System.out.println("Size of this test: "+ sizeTestPerFrag); //to debug
        double   [][] semi_features     = new double[sizeTestPerFrag][K+1];
        int [][] semi_labels = new int[sizeTestPerFrag][K+1];
        for (int s=0; s<test_features.length;s++) {

            //   if(s% 10000 == 0)
            //     System.out.println("Record:" + s); //to debug

            if (test_features[s][0] != Double.MAX_VALUE) {

                double tmp_dist = 0;
                int tmp_label = 0;

                //Setting up
                for (int d = 0; d < K; d++) {
                    semi_labels[s][d] = 0;
                    semi_features[s][d] = Double.MAX_VALUE;
                }

                for (int s2 = 0; s2 < train_features.length; s2++) { //last position

                    semi_features[s][K] = distance(train_features[s2], test_features[s]);
                    semi_labels[s][K] = train_labels[s2];

                    for (int j = K; j > 0; j--) { //Insert Sort
                        if (semi_features[s][j] < semi_features[s][j - 1]) {

                            tmp_label = semi_labels[s][j];
                            semi_labels[s][j] = semi_labels[s][j - 1];
                            semi_labels[s][j - 1] = tmp_label;

                            tmp_dist = semi_features[s][j];
                            semi_features[s][j] = semi_features[s][j - 1];
                            semi_features[s][j - 1] = tmp_dist;
                        }
                    }
                }

            }
        }

        int [] result = getKN(semi_labels,K);


        long estimatedTime = System.nanoTime() - startTime;
        double seconds = (double)estimatedTime / 1000000000.0;
        System.out.printf("\n[INFO] - ClassifierBlock -> Time elapsed: %.2f seconds\n",seconds);
        return result;
    }



    public static void main(String[] args) throws IOException {

        int numFrag = 0;
        int K = 0;
        String trainingName = "";
        String testName     = "";
        String outpath = "";
        int sizeTrain = 0;
        int sizeTest = 0;
        int numDim = 0;

        // Get and parse arguments
        int argIndex = 0;
        while (argIndex < args.length) {
            String arg = args[argIndex++];
            if (arg.equals("-K")) {
                K = Integer.parseInt(args[argIndex++]);
            } else if (arg.equals("-f")) {
                numFrag = Integer.parseInt(args[argIndex++]);
            }else if (arg.equals("-t")) {
                trainingName = args[argIndex++];
            }else if (arg.equals("-v")) {
                testName = args[argIndex++];
            }else if (arg.equals("-nd")) {
                numDim = Integer.parseInt(args[argIndex++]);
            }else if (arg.equals("-nt")) {
                sizeTrain  = Integer.parseInt(args[argIndex++]);
            }else if(arg.equals("-nv")) {
                sizeTest = Integer.parseInt(args[argIndex++]);
            }else if(arg.equals("-out")) {
                outpath = args[argIndex++];
            }
        }
        if (trainingName.equals("") || testName.equals("")){
            System.out.println("[ERROR] - You need to choose a file to train and test");
            System.exit(0);
        }

        /*
                Running

         */

        System.out.println("Running KNN.files_simple.Knn  with the following parameters:");
        System.out.println("- K Neighborhood: " + K);
        System.out.println("- Nodes: " + numFrag);
        System.out.println("- Training set: " + trainingName + "\tTraining size:" + sizeTrain);
        System.out.println("- Test set: " + testName +"\tTest size:" + sizeTest +"\n");


        int      [] train_labels   = new int   [sizeTrain];
        double [][] train_features = new double[sizeTrain][numDim];

        int         sizeTestPerFrag = (int) Math.ceil((float) sizeTest/numFrag);
        double [][][] test_features = new double[numFrag][sizeTestPerFrag][numDim];

        loadfile_TrainFile(train_features, train_labels, trainingName, sizeTrain); //Only in Master
        loadfile_TestFile(test_features, testName, sizeTestPerFrag, sizeTest);     //Only in Master

        int[] result_part_final;

        System.out.println("[INFO] - Classifier");

        //Split the TestSet and classifying it
        for (int f1 = 0 ; f1<numFrag;f1++) { //each test
            result_part_final = ClassifyBlock(test_features[f1], train_labels, train_features, K, sizeTestPerFrag);
            if(!outpath.equals("")) {
                File f = new File(outpath + "_part" + f1);
                f.createNewFile();
                savePredictionToFile(result_part_final, outpath + "_part" + f1);
            }
        }

    }
}