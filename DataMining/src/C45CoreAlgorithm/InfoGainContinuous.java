/*
 * Author: Charlotte Lin
 * Date: 2015/04/01
 * 
 */
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import DataDefination.*;
import ProcessInput.ProcessInputData;

public class InfoGainContinuous {
	
	// Field attribute: the attribute that we are calculating
	// Field threshold: the threshold of the continuous attribute values
	// Field infoGain: the infoGain of current attribute
	// Field subset: the value subset of current attribute
	private Attribute attribute;
	private double threshold;
	private double infoGain = -1;
	private HashMap<String, ArrayList<Instance>> subset;
	
	public InfoGainContinuous(Attribute attribute, Attribute target, 
			ArrayList<Instance> instances) throws IOException {
		
		// Initialize attribute
		this.attribute = attribute;
		
		// Initialize threshold and infoGain
		// (1) Get the name of the attribute to be calculated
		String attributeName = attribute.getName();
				
		// (2) Sort instances according to the attribute
		Comparator<Instance> comparator = new Comparator<Instance>() {
			@Override
			public int compare(Instance x, Instance y) {
				HashMap<String, String> xPair = x.getAttributeValuePairs();
				String xValue = xPair.get(attributeName);
						
				HashMap<String, String> yPair = y.getAttributeValuePairs();
				String yValue = yPair.get(attributeName);
				if (Double.parseDouble(xValue) - Double.parseDouble(yValue) > 0) return 1;
				else if (Double.parseDouble(xValue) - Double.parseDouble(yValue) < 0) return -1;
				else return 0;
			}
		};
		instances.sort(comparator);
				
		// (3) Get each position that target value change,
		// 	   then calculate information gain of each position
		//     find the maximum position value to be the threshold
		int thresholdPos = 0;
		for (int i = 0; i < instances.size() - 1; i++) {
			HashMap<String, String> instancePair = instances.get(i).getAttributeValuePairs();
			String instanceValue = instancePair.get(attributeName);
			HashMap<String, String> instancePair2 = instances.get(i + 1).getAttributeValuePairs();
			String instanceValue2 = instancePair2.get(attributeName);
					
			// not sure accuracy
			if (!instanceValue.equals(instanceValue2)) {
				double currInfoGain = calculateConti(attribute, target, instances, i);
				//System.out.println(currInfoGain);
				//System.out.println(infoGain);
				if (currInfoGain - infoGain > 0) {
					infoGain = currInfoGain;
					thresholdPos = i;
				}
			}
		}	
		// (4) Calculate threshold
		HashMap<String, String> a = instances.get(thresholdPos).getAttributeValuePairs();
		String aValue = a.get(attributeName);
		HashMap<String, String> b = instances.get(thresholdPos).getAttributeValuePairs();
		String bValue = b.get(attributeName);			
		threshold = (Double.parseDouble(aValue) + Double.parseDouble(bValue)) / 2;	
		
		// Initialize subset
		subset = new HashMap<String, ArrayList<Instance>>();
		ArrayList<Instance> left = new ArrayList<Instance>();
		ArrayList<Instance> right = new ArrayList<Instance>();
		for (int i = 0; i < thresholdPos; i++) {
			left.add(instances.get(i));
		}
		for (int i = thresholdPos + 1; i < instances.size(); i++) {
			right.add(instances.get(i));
		}
		String leftName = "less" + threshold;
		String rightName = "more" + threshold;
		subset.put(leftName, left);
		subset.put(rightName, right);
	}
	
	public static double calculateConti(Attribute attribute, Attribute target, 
			ArrayList<Instance> instances, int index) throws IOException {
		
		int totalN = instances.size();
		double infoGain = Entropy.calculate(target, instances);
		int subL = index + 1;
		int subR = instances.size() - index - 1;
		double subResL = ((double) subL) / ((double) totalN) * 
				Entropy.calculateConti(target, instances, 0, index);
		double subResR = ((double) subR) / ((double) totalN) * 
				Entropy.calculateConti(target, instances, index + 1, totalN - 1);
		infoGain -= (subResL + subResR);
		return infoGain;
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public double getInfoGain() {
		return infoGain;
	}
	
	public HashMap<String, ArrayList<Instance>> getSubset() {
		return subset;
	}
	
	public String toString() {
		return "Attribute: " + attribute.getName() + "\n" + "Threshold: " + threshold + "\n" 
				+ "InfoGain: " + infoGain + "\n" + "Subset: " + subset;
	}
	
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("trainProdSelection.txt");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		Attribute target = test.getTargetAttribute();
		for (Attribute item : attributes) {
			System.out.println(item);
		}				
		InfoGainContinuous test2 = new InfoGainContinuous(attributes.get(4), target, instances);
		System.out.println(test2);
	}
	
}
