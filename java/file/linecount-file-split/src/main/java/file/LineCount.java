package file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by lucasmsp on 24/03/17.
 */
public class LineCount {

        private static String    DATA_FOLDER;
        private static String[]    filePaths;


        public static void main(String args[]) {

            // Initialize file Names
            initializeVariables(args);
            // Run wordcount app
            computeLineCount();

        }

    private static void initializeVariables (String[] args) {


        int numFrag = 0;
        // Get and parse arguments
        int argIndex = 0;
        while (argIndex < args.length) {
            String arg = args[argIndex++];
            if (arg.equals("-in")) {
                DATA_FOLDER = args[argIndex++];
            }else if (arg.equals("-f")) {
                numFrag = Integer.parseInt(args[argIndex++]);
            }

        }
        System.out.println("Running LineCount.files with the following parameters:");
        System.out.println("[INFO] - Filenames  " + DATA_FOLDER);
        System.out.println("[INFO] - Num Frag  " + numFrag);


        filePaths = new String[numFrag];

        for (int i =0;i<numFrag;i++){
            filePaths[i]= DATA_FOLDER+"_"+String.format("%02d", i);
        }
    }

        private static void computeLineCount() {


            // Compute result
            int[][] result = new  int[filePaths.length][2];
            System.out.println("[INFO] Computing result");
            for (int i = 0; i < filePaths.length; ++i) {
                result[i] = LineCount(filePaths[i]);
            }
            int count =0;
            for (int i = 0; i < filePaths.length; ++i)
                count+=result[i][0];
            System.out.println("[INFO] Result size = " + count);

        }


        public static int []  LineCount(String filePath) {
            File file = new File(filePath);
            int [] count = new int[2];
            FileReader fr = null;
            BufferedReader br = null;
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    count[0]++;
                }
            } catch (Exception e) {
                System.err.println("ERROR: Cannot retrieve values from " + file.getName());
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e) {
                        System.err.println("ERROR: Cannot close buffered reader on file " + file.getName());
                        e.printStackTrace();
                    }
                }
                if (fr != null) {
                    try {
                        fr.close();
                    } catch (Exception e) {
                        System.err.println("ERROR: Cannot close file reader on file " + file.getName());
                        e.printStackTrace();
                    }
                }
            }

            return count;
        }

    }
