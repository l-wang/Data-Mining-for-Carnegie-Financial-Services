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

public class InfoGainDiscrete {
	
	// Field attribute: the attribute that we are calculating
	// Field infoGain: the infoGain of current attribute
	// Field subset: the value subset of current attribute
	private Attribute attribute;
	private double infoGain;
	private HashMap<String, ArrayList<Instance>> subset;
	
	
	public InfoGainDiscrete(Attribute target, Attribute attribute, ArrayList<Instance> instances) 
			throws IOException {
		
		// Initialize attribute
		this.attribute = attribute;
		
		ArrayList<String> valuesOfAttribute = attribute.getValues();
		
		String attributeName = attribute.getName();
		subset = new HashMap<String, ArrayList<Instance>>();
		for (String s : valuesOfAttribute) {
			subset.put(s, new ArrayList<Instance>());
		}
		for (Instance instance : instances) {
			HashMap<String, String> attributeValuePairsOfInstance = instance.getAttributeValuePairs();
			String valueOfInstanceAtAttribute = attributeValuePairsOfInstance.get(attributeName);
			if (!subset.containsKey(valueOfInstanceAtAttribute)) 
				throw new IOException("Invalid input data");
			subset.get(valueOfInstanceAtAttribute).add(instance);
		}
		
		int totalN = instances.size();
		infoGain = Entropy.calculate(target, instances);
		
		
		for (String s : subset.keySet()) {
			ArrayList<Instance> currSubset = subset.get(s);
			//System.out.println(s + "\n" + subset);
			int subN = currSubset.size();
			double subRes = ((double) subN) / ((double) totalN) * 
					Entropy.calculate(target, currSubset);
			infoGain -= subRes;
		}
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public double getInfoGain() {
		return infoGain;
	}
	
	public HashMap<String, ArrayList<Instance>> getSubset() {
		return subset;
	}
	
	public String toString() {
		return "Attribute: " + attribute + "\n"  
				+ "InfoGain: " + infoGain + "\n" + "Subset: " + subset;
	}
	
	// Unit test
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("rain.txt");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		Attribute target = test.getTargetAttribute();	
		System.out.println(target);
				
		InfoGainDiscrete test2 = new InfoGainDiscrete(target, attributes.get(0), instances);
		System.out.println(test2);
	}
}
