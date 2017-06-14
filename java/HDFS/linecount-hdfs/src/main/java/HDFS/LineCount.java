package HDFS;

import integration.Block;
import integration.HDFS;
import storage.StorageException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lucasmsp on 24/03/17.
 */
public class LineCount {

        private static String    HDFS_FILE;


        public static void main(String args[]) throws StorageException {

            String defaultfs = "";
            int numFrag = 0;
            // Get and parse arguments
            int argIndex = 0;
            while (argIndex < args.length) {
                String arg = args[argIndex++];
                if (arg.equals("-in")) {
                    HDFS_FILE = args[argIndex++];
                }else if (arg.equals("-hdfs")) {
                    defaultfs = args[argIndex++];
                }else if (arg.equals("-f")) {
                    numFrag = Integer.parseInt(args[argIndex++]);
                }

            }

            System.out.println("Running LineCount.files with the following parameters:");
            System.out.println("[INFO] - Filenames in HDFS  " + HDFS_FILE);


            HDFS dfs = new HDFS(defaultfs);

            ArrayList<Block> FILE_SPLITS = dfs.findALLBlocks(HDFS_FILE);
            numFrag = FILE_SPLITS.size();
            System.out.println("[INFO] - Num Frag  " + numFrag);

            // Compute result
            int[][] result = new  int[numFrag][2];
            System.out.println("[INFO] Computing result");
            int i = 0;
            for (Block b : FILE_SPLITS) {
               result[i] = LineCount(b);
                i++;
            }

            int count =0;
            for (i = 0; i < result.length; ++i)
                count+=result[i][0];
            System.out.println("[INFO] Result size = " + count);

        }


        public static int[]  LineCount(Block b1) {
            int[] count= new int[2];
            String lines;
            while(b1.HasRecords()) {
                lines = b1.getRecord();
                count[0]++;
            }

            return count;
        }

    }
