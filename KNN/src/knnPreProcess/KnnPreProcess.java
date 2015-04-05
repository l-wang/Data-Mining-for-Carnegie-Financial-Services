package knnPreProcess;

import java.io.IOException;
import java.util.ArrayList;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class KnnPreProcess {
	private ArrayList<ArrayList<Double>> trainData;
	private ArrayList<ArrayList<Double>> testData;
	private ArrayList<Attribute> attributesAndResult;
	
	/*
	 * use this constructor for knn prediction
	 */
	public KnnPreProcess(String trainDataFile, String testDataFile) throws IOException {
		// Read in the training data set Dtrain
		ProcessInputData DTrain = new ProcessInputData(trainDataFile);
		ArrayList<Attribute> attributes = DTrain.getAttributeSet();
		attributesAndResult = new ArrayList<Attribute>(attributes);
		attributesAndResult.add(DTrain.getTargetAttribute());
		ArrayList<Instance> trainInstances = DTrain.getInstanceSet();
		
		// Read in the test data set Dtest
		ProcessInputData DTest = new ProcessInputData(testDataFile);
		ArrayList<Instance> testInstances = DTest.getInstanceSet();
		
		// pre-process DTrain and DTest data into a 2d array
		trainData = preProcessData(attributesAndResult, trainInstances);
		testData = preProcessData(attributes, testInstances);
		
	}
	
	/*
	 * use this constructor for cross validation
	 */
	public KnnPreProcess(String trainDataFile) throws IOException {
		// Read in the training data set Dtrain
		ProcessInputData DTrain = new ProcessInputData(trainDataFile);
		ArrayList<Attribute> attributes = DTrain.getAttributeSet();
		attributesAndResult = new ArrayList<Attribute>(attributes);
		attributesAndResult.add(DTrain.getTargetAttribute());
		ArrayList<Instance> trainInstances = DTrain.getInstanceSet();
		
		// pre-process DTrain and DTest data into a 2d array
		trainData = preProcessData(attributesAndResult, trainInstances);
	}
	
	public ArrayList<ArrayList<Double>> getTrainData() {
		return trainData;
	}
	
	public ArrayList<ArrayList<Double>> getTestData() {
		return testData;
	}

	public ArrayList<Attribute> getAttributesAndResult() {
		return attributesAndResult;
	}
	
	private static ArrayList<ArrayList<Double>> preProcessData(ArrayList<Attribute> attributes, 
			ArrayList<Instance> trainInstances) {
		ArrayList<ArrayList<Double>> trainData= new ArrayList<ArrayList<Double>>(trainInstances.size());
		for (int i = 0; i < trainInstances.size(); i++) {
			trainData.add(new ArrayList<Double>(attributes.size()));
			Instance inst = trainInstances.get(i);
			for (int j = 0; j < attributes.size(); j++) {
				Attribute attr = attributes.get(j);
				String attrName = attr.getName();
				String attrType = attr.getType();
				String data = inst.getAttributeValuePairs().get(attrName);
				if (attrType.equals("continuous")) {
					trainData.get(i).add(Double.parseDouble(data));
				} else {
					ArrayList<String> values = attr.getValues();
					for (int k = 0; k < values.size(); k++) {
						if (data.equals(values.get(k))) {
							double valueIndex = k;
							trainData.get(i).add(valueIndex);
							break;
						}
					}
				}
			}
		}
		return trainData;
	}

}
