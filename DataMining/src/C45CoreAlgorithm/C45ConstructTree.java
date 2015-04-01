/*
 * Author: Charlotte Lin
 * Date: 2015/04/01
 * 
 */
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;

import ProcessInput.ProcessInputData;
import TreeDefination.TreeNode;
import DataDefination.Instance;
import DataDefination.Attribute;

public class C45ConstructTree {
	private TreeNode root;
	private ArrayList<Attribute> attributes;
	private ArrayList<Instance> instances;
	
	public C45ConstructTree(String fileName) throws IOException {
		ProcessInputData input = new ProcessInputData(fileName);
		attributes = input.getAttributeSet();
		instances = input.getInstanceSet();
	}
	
	public TreeNode constructTree() {
		
	}
	
}
