package knnPreProcess;

import java.util.ArrayList;

import DataDefination.Attribute;

public class ScaleData {
	private ArrayList<double[]> scalers;
	
	public ScaleData(ArrayList<Attribute> attributesAndResult, ArrayList<ArrayList<Double>> trainData) {
		ArrayList<double[]> scalers = new ArrayList<double[]>();
		for (int i = 0; i < attributesAndResult.size() - 1; i++) {
			double[] scaler = null;
			Attribute attr = attributesAndResult.get(i);
			String attrType = attr.getType();
			if (attrType.equals("continuous")) {
				double max = Double.MIN_VALUE;
				double min = Double.MAX_VALUE;
				for (int j = 0; j < trainData.size(); j++) {
					double data = trainData.get(j).get(i);
					if (data > max) {
						max = data;
					}
					if (data < min) {
						min = data;
					}
				}
				scaler = new double[]{min, max};
			}
			//System.out.println(Arrays.toString(scaler));
			scalers.add(scaler);
		}
		this.scalers = scalers;
	}

	
	public ArrayList<ArrayList<Double>> scaleData(ArrayList<ArrayList<Double>> trainData) {
		for (int i = 0; i < scalers.size(); i++) {
			if (scalers.get(i) != null) {
				double min = scalers.get(i)[0];
				double max = scalers.get(i)[1];
				for (int j = 0; j < trainData.size(); j++) {
					double oldData = trainData.get(j).get(i);
					double scaledData = (oldData - min) / (max - min);
					trainData.get(j).set(i, scaledData);
				}
			}
		}
		return trainData;
	}

}
