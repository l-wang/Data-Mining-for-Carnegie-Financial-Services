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
	
	public TreeNode constructTree(Attribute target, ArrayList<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		if (height == 5) {
			String leafLabel = getMajorityLabel(target, instances);
			TreeNode leaf = new TreeNode(leafLabel);
			return leaf;
		}
		
		ChooseAttribute choose = new ChooseAttribute(target, attributes, instances)
		Attribute rootAttr = choose.getChosen();
		attributes.remove(rootAttr);
		TreeNode root = new TreeNode(rootAttr);
		HashMap<String, ArrayList<Instance>> valueSubsets = choose.getSubset();
		for (String valueName : valueSubsets.keySet()) {
			ArrayList<Instance> subset = valueSubsets.get(valueName);
			TreeNode child = constructTree(target, attributes, subset);
			root.addChild(valueName, child);
		}
		return root;
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
