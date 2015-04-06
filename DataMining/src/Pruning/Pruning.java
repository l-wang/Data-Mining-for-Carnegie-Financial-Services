package Pruning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import CV.CrossValidation;
import CV.CrossValidationWithPruning;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;
import ProcessOutput.PrintTree;
import TreeDefination.TreeNode;

public class Pruning {
	private TreeNode root;
	private ArrayList<Instance> testInstances;
	private ArrayList<Instance> originalInstances;
	
	public Pruning(TreeNode root, ArrayList<Instance> testInstances, ArrayList<Instance> originalInstances) {
		this.root = root;
		this.testInstances = testInstances;
		this.originalInstances = originalInstances;
	}
	
	public TreeNode run(TreeNode r, ArrayList<Instance> testInstances) {
		if(r == null || testInstances.size() == 0) {
			return null;
		}
		
		if(r.getType().equals("leaf")) {
			return r;
		}
		
		for(String k : r.getChildren().keySet()) {
			TreeNode child = r.getChildren().get(k);
			ArrayList<Instance> curInstances = new ArrayList<Instance>();
			for(int i = 0; i < testInstances.size(); i++) {
				Instance cur = testInstances.get(i);
//				if(child.getBranchValue().equals(cur.getAttributeValuePairs().get(r.getAttribute().getName()))) {
//					curInstances.add(cur);
//				}
				String attributeType = r.getAttribute().getType();
				String attributeName = r.getAttribute().getName();
				if(attributeType.equals("continuous")) {
					double threshold = Double.parseDouble(k.substring(4));
					double testValue = Double.parseDouble(cur.getAttributeValuePairs().get(attributeName));
					String partition = k.substring(0, 4);
					if((partition.equals("less") && testValue < threshold) || (partition.equals("more") && testValue >= threshold)) {
						curInstances.add(cur);
					}
				} else {
					if(k.equals(cur.getAttributeValuePairs().get(attributeName))) {
						curInstances.add(cur);
					}
				}
			}
			
			TreeNode newChild = run(child, curInstances);
			if(newChild != null) {
				r.getChildren().put(k, newChild);
			}
		}
		
		if(r.getChildren().size() != 0) {
			HashMap<String, TreeNode> children = r.getChildren();
			for(String k : children.keySet()) {
				if(!children.get(k).getType().equals("leaf")) {
					return r;
				}
			}
		}
		
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		for(int i = 0; i < testInstances.size(); i++) {
			String label = testInstances.get(i).getAttributeValuePairs().get(ProcessInputData.
					targetAttribute.getName());
			if(result.containsKey(label)) {
				result.put(label, result.get(label) + 1);
			} else {
				result.put(label, 1);
			}
		}
		
		int max = 0;
		String targetLabel = "";
		for(String k : result.keySet()) {
			max = Math.max(max, result.get(k));
			System.out.println("***********" + k);
			targetLabel = k;
		}
		
		int preMax = 0;
		for(int i = 0; i < testInstances.size(); i++) {
			int index = testInstances.get(i).getInstanceIndex();
			for(int j = 0; j < originalInstances.size(); j++) {
				Instance curOriginalInstance = originalInstances.get(j);
				if(index == curOriginalInstance.getInstanceIndex()) {
					HashMap<String, String> attributes = curOriginalInstance.getAttributeValuePairs();
					if(attributes.get(ProcessInputData.targetAttribute.getName()).
							equals(attributes.get("Test" + ProcessInputData.targetAttribute.getName()))) {
						preMax++;
					}
				}
			}
		}
		if(preMax > max) {
			return r;
		} else {
			r.setType("leaf");
			r.getChildren().clear();
			r.setTargetLabel(targetLabel);
			System.out.println("Pruning Leaf Node: " + targetLabel);
			return r;
		}
	}

}
