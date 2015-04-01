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

public class CalculateInformationGain {
	
	public static double calculate(Attribute target, Attribute attribute, ArrayList<Instance> instances) 
			throws IOException {
		ArrayList<String> valuesOfAttribute = attribute.getValues();
		String attributeName = attribute.getName();
		HashMap<String, ArrayList<Instance>> instanceSubsets = 
				new HashMap<String, ArrayList<Instance>>();
		for (String s : valuesOfAttribute) {
			instanceSubsets.put(s, new ArrayList<Instance>());
		}
		for (Instance instance : instances) {
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtAttribute = attributeValuePairsOfInstance.get(attributeName);
			if (!instanceSubsets.containsKey(valueOfInstanceAtAttribute)) 
				throw new IOException("Invalid input data");
			instanceSubsets.get(valueOfInstanceAtAttribute).add(instance);
		}
		
		int totalN = instances.size();
		double informationGain = CalculateEntropy.calculate(target, instances);
		
		for (String s : instanceSubsets.keySet()) {
			ArrayList<Instance> subset = instanceSubsets.get(s);
			//System.out.println(s + "\n" + subset);
			int subN = subset.size();
			double subRes = ((double) subN) / ((double) totalN) * 
					CalculateEntropy.calculate(target, subset);
			informationGain -= subRes;
		}
		return informationGain;
	}
	
	public static double calculateConti(Attribute attribute, Attribute target, 
			ArrayList<Instance> instances, int index) throws IOException {
		int totalN = instances.size();
		double infoGain = CalculateEntropy.calculate(target, instances);
		int subL = index + 1;
		int subR = instances.size() - index - 1;
		double subResL = ((double) subL) / ((double) totalN) * 
				CalculateEntropy.calculateConti(target, instances, 0, index);
		double subResR = ((double) subR) / ((double) totalN) * 
				CalculateEntropy.calculateConti(target, instances, index + 1, totalN - 1);
		infoGain -= (subResL + subResR);
		return infoGain;
	}
	
	// Unit test
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("trainProdSelection.txt");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		for (Attribute item : attributes) {
			System.out.println(item);
		}		
		for (Instance item : instances) {
			System.out.println(item);
		}		
				
		double res = CalculateInformationGain.calculateConti(attributes.get(3),
				attributes.get(attributes.size() - 1), instances, 72);
		System.out.println(res);
	}
}
