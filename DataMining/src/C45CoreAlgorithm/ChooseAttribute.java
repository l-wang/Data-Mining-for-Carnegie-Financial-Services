/*
 * Author: Charlotte Lin
 * Date: 2015/04/01
 * 
 */
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class ChooseAttribute {
	
	// Field chosen: the attribute with largest information gain
	// Field subset: the value subsets of the chosen attribute
	// Field infoGain: the information gain of the chosen attribute
	private Attribute chosen;
	private HashMap<String, ArrayList<Instance>> subset;
	private double infoGain;
	private double threshold;
	
	public ChooseAttribute(Attribute target, ArrayList<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		
		// Initialize variables
		chosen = null;
		infoGain = -1;
		subset = null;
		
		// Iterate to find the attribute with the largest information gain
		for (Attribute currAttribute : attributes) {
			double currInfoGain = 0;
			HashMap<String, ArrayList<Instance>> currSubset = null;
			
			if (currAttribute.getType().equals("continuous")) {
				InfoGainContinuous contigous = new InfoGainContinuous(currAttribute, target, instances);
				currInfoGain = contigous.getInfoGain();
				currSubset = contigous.getSubset();
				threshold = contigous.getThreshold();
				//System.out.println(contigous);
			} else {
				InfoGainDiscrete discrete = new InfoGainDiscrete(target, currAttribute, instances);
				currInfoGain = discrete.getInfoGain();
				currSubset = discrete.getSubset();
			}
			if (currInfoGain > infoGain) {
				infoGain = currInfoGain;
				chosen = currAttribute;
				subset = currSubset;
			}
		}
	}
	public Attribute getChosen() {
		return chosen;
	}
	public double getInfoGain() {
		return infoGain;
	}
	public HashMap<String, ArrayList<Instance>> getSubset() {
		return subset;
	}
	public double getThreshold() {
		return threshold;
	}
	
	public String toString() {
		return "Chosen attribute: " + chosen + "\n" + "InfoGain: " + infoGain + "\n"
				+ "Subset: " + subset;
	}
	// Unit test
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("rain.txt");
		HashSet<Attribute> attributesHash = test.getAttributeHashSet();
		ArrayList<Attribute> attributes = test.getAttributeSet();
		Attribute target = test.getTargetAttribute();
		ArrayList<Instance> instances = test.getInstanceSet();
		for (Attribute item : attributes) {
			System.out.println(item);
		}		
		for (Instance item : instances) {
			System.out.println(item);
		}		
		ChooseAttribute test2 = new ChooseAttribute(target, attributes, instances);
		System.out.println(test2);
	}
}
