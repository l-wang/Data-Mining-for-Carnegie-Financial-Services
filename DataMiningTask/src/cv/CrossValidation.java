package cv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import knn.DataEntry;
import knn.KNN;

public class CrossValidation {
	public static void main(String[] args) throws Exception {
		// Read in the training data set Dtrain
		ArrayList<DataEntry> DTrain = readArff(new File("trainProdSelection.arff"), "train");
		//System.out.println(DTrain);
		
//		// Read in the test data set Dtest
//		ArrayList<DataEntry> DTest = readArff(new File("testProdSelection.arff"), "test");
//		//System.out.println(DTest);
//		
		// set k
		int k = 3;
		
		// set weights for each attributes
		double[] weights = new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.17};
				
		// start knn
		KNN knn = new KNN();
		
		int k_fold = 2;
		int count = DTrain.size()/k_fold;
		int result = 0;
		for (int i=0; i<k_fold; i++){
			ArrayList<DataEntry> DTest_cv = new ArrayList<DataEntry>(DTrain.subList(count*i, count*(i+1)));
			ArrayList<DataEntry> DTrain_cv = new ArrayList<DataEntry>(DTrain.subList(0, count*i));
			DTrain_cv.addAll(DTrain.subList(count*(i+1), count*k_fold));
			System.out.println("===D_TEST_CV===");
			System.out.println(DTest_cv);
			System.out.println("===D_TRAIN_CV===");
			System.out.println(DTrain_cv);
			
			// Scale Numeric Attributes in DTrain and DTest
			scaleNumericAttributes(DTest_cv);
			scaleNumericAttributes(DTrain_cv);
			
			System.out.println("===CROSS_VALIDATION_RESULT===");
			String vote;
			// compute knn
			for (DataEntry e: DTest_cv) {
				vote = knn.classifyVote(DTrain_cv, e, k, weights);
				System.out.println(e+vote);
				if (!e.getLabel().equals(vote)) result++;
			}
		}
		System.out.println("===ERROR_RATE===");
		System.out.println(result);
		
		
		
		//System.out.println(DTest);
		
		
	}
	
	private static ArrayList<DataEntry> readArff(File file, String flag) throws Exception {
		ArrayList<DataEntry> data = new ArrayList<DataEntry>();
		
		FileReader fr;
		fr = new FileReader(file);		
		BufferedReader br = new BufferedReader(fr);
		String line;
		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (!line.equals("") && !line.startsWith("@") && !line.startsWith("%")) {
				String[] array = line.split(",");
				String[] attributes = new String[6]; // 
				String label;
				for (int i = 0; i < 6; i++) {
					attributes[i] = array[i];
				}
				if (flag.equals("train")) {
					label = array[array.length - 1];
				} else if (flag.equals("test")) {
					label = null;
				} else {
					br.close();
					throw new Exception("Invalid flag");
				}
				
				data.add(new DataEntry(attributes, label));
			}
		}
		br.close();
		return data;
	}
	
	private static void scaleNumericAttributes(ArrayList<DataEntry> data) {
		for (int i = 0; i < data.get(0).getNumericAttributes().length; i++) {
			// find max and min
			double max = data.get(0).getNumericAttributes()[i];
			double min = max;
			for (int j = 1; j < data.size(); j++) {
				double curr = data.get(j).getNumericAttributes()[i];
				if (curr > max) {
					max = curr;
				}
				if (curr < min) {
					min = curr;
				}
			}
			
			// convert values
			for (int j = 0; j < data.size(); j++) {
				double curr = data.get(j).getNumericAttributes()[i];
				data.get(j).getNumericAttributes()[i] = (curr - min) / (max - min);
			}
		}
	}
}
