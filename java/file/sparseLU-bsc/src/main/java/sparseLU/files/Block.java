/*
 *  Copyright 2002-2015 Barcelona Supercomputing Center (www.bsc.es)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package sparseLU.files;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class Block {
	
	private int BLOCK_SIZE;
	private double [][] data;
	
	public Block (int i, int j, int msize, int bsize) {
		BLOCK_SIZE = bsize;
		data = new double[BLOCK_SIZE][BLOCK_SIZE];
		
		data = initBlock(i, j, msize, BLOCK_SIZE);
	}

	public Block (String filename, int bsize) {
		BLOCK_SIZE = bsize;
		data = new double[BLOCK_SIZE][BLOCK_SIZE];
		
		FileReader filereader = null;
		BufferedReader br = null;
		try {
			filereader = new FileReader(filename);
			br = new BufferedReader(filereader);			// Get a buffered reader. More Efficient
			StringTokenizer tokens;
			String nextLine;
			for(int i = 0; i < BLOCK_SIZE; i++) {
				nextLine = br.readLine();
				tokens = new StringTokenizer(nextLine);
				for(int j = 0; j < BLOCK_SIZE && tokens.hasMoreTokens(); j++) {
					data[i][j] = Double.parseDouble(tokens.nextToken());
				}
			}
			br.close();
			filereader.close();
		} catch ( FileNotFoundException fnfe ) {
			fnfe.printStackTrace();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (filereader != null) {
				try {
					filereader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static double[][] initBlock(int ii, int jj, int N, int M) {
        double[][] block = new double[M][M];
        int initVal = 1325;
        for (int k = 0; k < N; k++) {
        	for (int l = 0; l < N; l++) {
        		if (!isNull(k,l)) {
        			for (int i = 0; i < M; i++) {
        				for (int j = 0; j < M; j++) {
        					initVal = (3125 * initVal) % 65536;
        					if (k == ii && l == jj) {
        						block[i][j] = ((initVal - 32768.0) / 16384.0);
        					}
        				}
        			}
        		}
        	}
        }
        return block;
    }

    private static boolean isNull(int ii, int jj) {
        boolean nullEntry = false;
        if ((ii < jj) && (ii%3 != 0)) nullEntry = true;
        if ((ii > jj) && (jj%3 != 0)) nullEntry = true;
        if (ii%2 == 1)                nullEntry = true;
        if (jj%2 == 1)                nullEntry = true;
        if (ii == jj)                 nullEntry = false;
        if (ii == jj - 1)             nullEntry = false;
        if (ii - 1 == jj)             nullEntry = false;

        return nullEntry;
    }

	protected void printBlock() {
		for(int i = 0; i < BLOCK_SIZE; i++) {
			for(int j = 0; j < BLOCK_SIZE; j++) {
				System.out.print( data[i][j] + " " );
			}
			System.out.println();
		}
	}

	public void blockToDisk(String filename) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream (filename);
			for (int i = 0; i < BLOCK_SIZE; i++) {
				for (int j = 0; j < BLOCK_SIZE; j++) {
					String str = (new Double(data[i][j])).toString() + " ";
					fos.write(str.getBytes());
				}
				fos.write( "\n".getBytes() );
			}
			fos.close();
		} catch ( FileNotFoundException fnfe ) {
			fnfe.printStackTrace();
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void lu0() {
		for (int k = 0; k < BLOCK_SIZE; k++) {
	    	for (int i = k + 1; i < BLOCK_SIZE; i++) {
	    		this.data[i][k] = this.data[i][k]/this.data[k][k];
	            for (int j = k + 1; j < BLOCK_SIZE; j++) {
	            	this.data[i][j] -= this.data[i][k]*this.data[k][j];
	            }
	    	}
		}
	}
		
	public void bdiv(Block diag) {
		for (int i = 0; i < BLOCK_SIZE; i++) {
			for (int k = 0; k < BLOCK_SIZE; k++) {
				this.data[i][k] = this.data[i][k]/diag.data[k][k];
	            for (int j = k + 1; j < BLOCK_SIZE; j++) {
	            	this.data[i][j] -= this.data[i][k]*diag.data[k][j];
	            }
			}
		}
	}
	
	public void bmod(Block row, Block col) {
		for (int i = 0; i < BLOCK_SIZE; i++) {
	    	for (int j = 0; j < BLOCK_SIZE; j++) {
	    		for (int k = 0; k < BLOCK_SIZE; k++) {
	    			this.data[i][j] -= row.data[i][k]*col.data[k][j];
	    		}
	    	}
		}
	}
	
	public void fwd(Block diag) {
		for (int j = 0; j < BLOCK_SIZE; j++) {
			for (int k = 0; k < BLOCK_SIZE; k++) { 
				for (int i = k + 1; i < BLOCK_SIZE; i++) {
					this.data[i][j] -= diag.data[i][k]*this.data[k][j];
				}
			}
		}
	}
	
}
