package files;

import java.io.*;
import java.util.*;


public class WordCount {

	private static String    DATA_FOLDER;
	private static String[]    filePaths;

	public static void main ( String[] args ) throws Exception {

		int numFrag = 0;
		boolean list = false;
		// Get and parse arguments
		int argIndex = 0;
		while (argIndex < args.length) {
			String arg = args[argIndex++];
			if (arg.equals("-in")) {
				DATA_FOLDER = args[argIndex++];
			} else if (arg.equals("-f")){
				numFrag = Integer.parseInt(args[argIndex++]);
			}if (arg.equals("-list")) {
				list = true;
			}
		}

		System.out.println("Running WordCount.files with the following parameters:");
		System.out.println("[INFO] - Filenames  " + DATA_FOLDER);

		filePaths = new String[numFrag];
		HashMap<String, Integer> result = new HashMap<String, Integer>();

		for (int i =0;i<numFrag;i++){
			filePaths[i] = DATA_FOLDER+"_"+String.format("%02d", i);
			HashMap<String, Integer> partialResult = map(filePaths[i]);
			result = mergeResults(partialResult, result);
		}
		System.out.println("[LOG] Result size = " + result.keySet().size());
		if (list){
			for (String key : result.keySet()){
				System.out.println("'"+key+"', "+result.get(key));
			}
		}



	}

	public static HashMap<String, Integer> mergeResults(HashMap<String, Integer> m1, HashMap<String, Integer> m2) {
		Iterator it1 = m1.entrySet().iterator();
		while (it1.hasNext()) {
			Map.Entry pair = (Map.Entry)it1.next();
			if(m2.containsKey(pair.getKey())){
				m2.put( pair.getKey().toString(), (m1.get(pair.getKey())) + m2.get(pair.getKey())  );
			}
			else{
				m2.put( pair.getKey().toString(), m1.get(pair.getKey()) );
			}
			it1.remove(); // avoids a ConcurrentModificationException
		}

		return m2;
	}



	public static HashMap<String, Integer> map(String filePath) {

		HashMap<String, Integer> res = new HashMap<String, Integer>();

		File file = new File(filePath);
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;

			while ((line = br.readLine()) != null) {
				String[] words = line.split(" ");
				for (String word : words) {
					if (res.containsKey(word)) {
						res.put(word, res.get(word) + 1);
					} else {
						res.put(word, 1);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return res;
	}
}
