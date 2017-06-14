package HDFS;

import integration.Block;

import storage.StorageException;
import storage.StorageItf;


import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 * Created by lucasmsp on 24/03/17.
 */
public class LineCount {

        public static String        HDFS_FILE = "TO DO YET";
        public static StorageItf    storage;

        public static void main(String args[]) throws StorageException {

            ArrayList<Block> FILE_SPLITS  = storage.getBlocks(0);
            int numFrag = FILE_SPLITS.size();

            System.out.println("Running HDFS.LineCount with the following parameters:");
            System.out.println("[INFO] - Filenames in HDFS  " + HDFS_FILE);
            System.out.println("[INFO] - Num Frag 1 " + numFrag);

            // Compute result
            int[][] result = new  int[numFrag][2];
            System.out.println("[INFO] Computing result");
            int i = 0;
            for (Block b : FILE_SPLITS){
                result[i]  = LineCount(b);
                i++;
            }

            int count  = 0;

            for (i = 0; i < result.length; ++i) {
                count += result[i][0];
            }
            System.out.println("[INFO] Result size 1 = " + count);



            ArrayList<Block> FILE_SPLITS2 = storage.getBlocks(1);
            int numFrag2 = FILE_SPLITS2.size();
            System.out.println("[INFO] - Num Frag 2 " + numFrag2);
            int[][] result2 = new  int[numFrag2][2];
            System.out.println("[INFO] Computing result");
            i = 0;
            for (Block b : FILE_SPLITS2){
                result2[i]  = LineCount(b);
                i++;
            }
            int count2 = 0;
            for (i = 0; i < result2.length; ++i) {

                count2+=result2[i][0];
            }
            System.out.println("[INFO] Result size 2 = " + count2);
        }


        public static int[]  LineCount(Block b1) {
            java.net.InetAddress localMachine = null;
            try {
                localMachine = java.net.InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            System.out.println("Hostname of local machine: " + localMachine.getHostName());
            //b1 = (Block) b1.clone();

            int[] count= new int[2];
            String lines;
            while(b1.HasRecords()) {
                lines = b1.getRecord();
                count[0]++;
            }

            return count;
        }

    }
