package TreeDefination;

import java.util.ArrayList;
import java.util.HashMap;

import DataDefination.Attribute;

public class TreeNode {
	private Attribute attribute;
	private String branchValue;
	private HashMap<String, TreeNode> children;
	private String targetLabel;
	
	public TreeNode() {
		children = new HashMap<String, TreeNode>();
	}
	
	public TreeNode(Attribute attribute) {
		children = new HashMap<String, TreeNode>();
	}
	
	public TreeNode(String targetLabel) {
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
	
	// toString
	public String toString() {
		return "Attribute: " + attribute + "\n";
	}
	
	public static void main(String[] args) {
		
	}
}
