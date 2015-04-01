package knn;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class KNNDriver {
	public static void main(String[] args) throws Exception {
		// Read in the training data set Dtrain
		ArrayList<DataEntry> DTrain = readArff(new File("trainProdSelection.arff"), "train");
		//System.out.println(DTrain);
		
		// Read in the test data set Dtest
		ArrayList<DataEntry> DTest = readArff(new File("testProdSelection.arff"), "test");
		//System.out.println(DTest);
		
		// set k
		int k = 3;
		
		// Scale Numeric Attributes in DTrain and DTest
		scaleNumericAttributes(DTrain);
		scaleNumericAttributes(DTest);
		//System.out.println(DTest);
		
		// start knn
		KNN knn = new KNN();
		
		// set weights for each attributes
		double[] weights = new double[]{0.17, 0.17, 0.17, 0.17, 0.17, 0.17};
		
		// compute knn
		for (DataEntry e: DTest) {
			knn.classify(DTrain, e, k, weights);
		}
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
		for (int i = 0; i < 4; i++) {
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
