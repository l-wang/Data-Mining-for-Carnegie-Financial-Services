package TreeDefination;

import java.util.ArrayList;

import DataDefination.Attribute;

public class TreeNode {
	private Attribute attribute;
	private String preValue;
	private ArrayList<TreeNode> children;
	
	public TreeNode() {
		children = new ArrayList<TreeNode>();
	}
	
	public Attribute getAttribute() {
		return attribute;
	}
	
	public String getPreValue() {
		return preValue;
	}
	
	public ArrayList<TreeNode> getChildren() {
		return children;
	}
	
	// toString
}
