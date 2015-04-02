package TreeDefination;

import java.util.ArrayList;
import java.util.HashMap;

import C45CoreAlgorithm.ConstructTree;
import DataDefination.Attribute;

public class TreeNode {
	private String type;
	private Attribute attribute;
	private String branchValue;
	private HashMap<String, TreeNode> children;
	private String targetLabel;
	
	public TreeNode(Attribute attribute) {
		type = "root";
		this.attribute = attribute;
		children = new HashMap<String, TreeNode>();
	}
	
	public TreeNode(String targetLabel) {
		type = "leaf";
		this.targetLabel = targetLabel;
	}
	public Attribute getAttribute() {
		return attribute;
	}
	
	public String getBranchValue() {
		return branchValue;
	}
	
	public void setBranchValue(String value) {
		branchValue = value;
	}
	
	public void addChild(String valueName, TreeNode child) {
		children.put(valueName, child);
	}
	public HashMap<String, TreeNode> getChildren() {
		return children;
	}
	
	public String getTargetLabel() {
		return targetLabel;
	}
	
	public String getType() {
		return type;
	}
	
	// toString
	public String toString() {
		if (type.equals("root")) return "Root attribute: " + attribute.getName() + "; Children: " + children;
		else return "Leaf label: " + targetLabel;
	}
	
	public static void main(String[] args) {
		
	}
}
