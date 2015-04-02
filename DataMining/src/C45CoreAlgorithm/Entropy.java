/*
 * Author: Charlotte Lin
 * Date: 2015/04/01
 * 
 */
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class Entropy {
	
	public static double calculate(Attribute target, ArrayList<Instance> instances) throws IOException {
		ArrayList<String> valuesOfTarget = target.getValues();
		String targetName = target.getName();
		HashMap<String, Integer> countValueOfTarget = new HashMap<String, Integer>();
		for (String s : valuesOfTarget) {
			countValueOfTarget.put(s, 0);
		}
		for (Instance instance : instances) {
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtTarget = attributeValuePairsOfInstance.get(targetName);
			if (!countValueOfTarget.containsKey(valueOfInstanceAtTarget)) 
				throw new IOException("Invalid input data");
			countValueOfTarget.put(valueOfInstanceAtTarget, 
					countValueOfTarget.get(valueOfInstanceAtTarget) + 1);
		}
		int totalN = instances.size();
		double entropy = 0;
		
		//System.out.println(countValueOfTarget);
		
		for (String s : valuesOfTarget) {
			int countSingleValue = countValueOfTarget.get(s);
			if (countSingleValue == 0) continue;
			if (countSingleValue == totalN) return 0;
			double pValue = ((double) countSingleValue) / ((double)totalN);
			double itemRes = -pValue * (Math.log(pValue));
			entropy += itemRes;
		}
		return entropy;
	}
	
	public static double calculateConti(Attribute target, ArrayList<Instance> instances, 
			int start, int end) throws IOException {
		ArrayList<String> valuesOfTarget = target.getValues();
		String targetName = target.getName();
		HashMap<String, Integer> countValueOfTarget = new HashMap<String, Integer>();
		for (String s : valuesOfTarget) {
			countValueOfTarget.put(s, 0);
		}
		for (int i = start; i <= end; i++) {
			Instance instance = instances.get(i);
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtTarget = attributeValuePairsOfInstance.get(targetName);
			if (!countValueOfTarget.containsKey(valueOfInstanceAtTarget)) 
				throw new IOException("Invalid input data");
			countValueOfTarget.put(valueOfInstanceAtTarget, 
					countValueOfTarget.get(valueOfInstanceAtTarget) + 1);
		}
		int totalN = instances.size();
		double entropy = 0;
		
		//System.out.println(countValueOfTarget);
		
		for (String s : valuesOfTarget) {
			int countSingleValue = countValueOfTarget.get(s);
			if (countSingleValue == 0) continue;
			if (countSingleValue == totalN) return 0;
			double pValue = ((double) countSingleValue) / ((double)totalN);
			double itemRes = -pValue * (Math.log(pValue));
			entropy += itemRes;
		}
		return entropy;
	}
	
	// Unit test
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("rain.txt");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		Attribute target = test.getTargetAttribute();
		for (Attribute item : attributes) {
			System.out.println(item);
		}		
		for (Instance item : instances) {
			System.out.println(item);
		}		
		double res = Entropy.calculate(target, instances);
		System.out.println(res);
	}
}
