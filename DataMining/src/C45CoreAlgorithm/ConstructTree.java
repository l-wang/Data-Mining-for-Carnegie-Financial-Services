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

import ProcessInput.ProcessInputData;
import TreeDefination.TreeNode;
import DataDefination.Instance;
import DataDefination.Attribute;

public class ConstructTree {
	private ArrayList<Attribute> attributes;
	private ArrayList<Instance> instances;
	
	private int height = 0;
	
	public ConstructTree(String fileName) throws IOException {
		ProcessInputData input = new ProcessInputData(fileName);
		attributes = input.getAttributeSet();
		instances = input.getInstanceSet();
	}
	
	public TreeNode constructTree(Attribute target, HashSet<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		if (height == 5) {
			String leafLabel = getMajorityLabel(target, instances);
			TreeNode leaf = new TreeNode(leafLabel);
			return leaf;
		}
		
		Attribute rootAttr = ChooseAttribute.choose(target, attributes, instances);
		attributes.remove(rootAttr);
		TreeNode root = new TreeNode(rootAttr);
		HashMap<String, ArrayList<Instance>> valueSubsets = getValueSubset(rootAttr);
		for (String valueName : valueSubsets.keySet()) {
			ArrayList<Instance> subset = valueSubsets.get(valueName);
			TreeNode child = constructTree(target, attributes, subset);
			root.addChild(valueName, child);
		}
		return root;
	}
	
	private HashMap<String, ArrayList<Instance>> getValueSubset(Attribute attribute) 
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
		return instanceSubsets;
	}
	
	public String getMajorityLabel(Attribute target, ArrayList<Instance> instances) throws IOException {
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
		String maxLabel = "";
		int maxCount = 0;
		for (String s : countValueOfTarget.keySet()) {
			int currCount = countValueOfTarget.get(s);
			if (currCount > maxCount) {
				maxCount = currCount;
				maxLabel = s;
			}
		}
		return maxLabel;
	}
	
	// Unit test
	public static void main(String[] args) {
		
	}
}
