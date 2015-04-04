/*
 * first construct tree
 * input data
 * view result
 */

package MineData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ProcessInput.ProcessInputData;
import ProcessOutput.PrintTree;
import TreeDefination.TreeNode;
import C45CoreAlgorithm.ConstructTree;
import DataDefination.Attribute;
import DataDefination.Instance;

public class C45MineData {
	private ArrayList<Attribute> attributes;
	private ArrayList<Instance> testInstances;
	private Attribute target;
	private TreeNode root;
	private ArrayList<Instance> result;
	
	public C45MineData(String trainData, String testData) throws IOException {
		result = new ArrayList<Instance>();
		ProcessInputData input = new ProcessInputData(testData);
			
		ConstructTree tree = new ConstructTree(trainData);
		attributes = input.getAttributeSet();
		target = input.getTargetAttribute();
		testInstances = input.getInstanceSet();

		root = tree.construct();
		
		result.addAll(testInstances);
	}
	
	private void mine() {
		for (int i = 0; i < testInstances.size(); i++) {
			TreeNode node = root;
			Instance currInstance = testInstances.get(i);
			Instance resInstance = result.get(i);
			while (!node.getType().equals("leaf")) {
				String attributeName = node.getAttribute().getName();
				String attributeType = node.getAttribute().getType();
				HashMap<String, String> attributeValuePairs = currInstance.getAttributeValuePairs();
				String value = attributeValuePairs.get(attributeName);
				if (attributeType.equals("continuous")) {
					HashMap<String, TreeNode> children = node.getChildren();
					String tmp = "";
					for (String s : children.keySet()) {
						String threshold = s.substring(4);
						if (Double.parseDouble(value) < Double.parseDouble(threshold)) {
							tmp = "less";
						} else {
							tmp = "more";
						}
						String curLabel = s.substring(0, 4);
						if (tmp.equals(curLabel)) node = children.get(s);
					}
				} else {
					HashMap<String, TreeNode> children = node.getChildren();
					node = children.get(value);
				}
			}
			HashMap<String, String> pairs = resInstance.getAttributeValuePairs();
			pairs.put("Test" + target.getName(), node.getTargetLabel());
		}
	}
	
	public ArrayList<Instance> getResult() {
		mine();
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		ConstructTree test = new ConstructTree("trainProdIntro.binary.arff");
		PrintTree test2 = new PrintTree();
		String res1 = test2.printBFS(test.construct());
		ArrayList<String> res2 = test2.printDFS(test.construct());
//		System.out.println("********************");
		System.out.println(res2);
				
		C45MineData test3 = new C45MineData("trainProdIntro.binary.arff", "testProdIntro.binary.arff");
		ArrayList<Instance> res = test3.getResult();
//		for (Instance item : res) {
//			System.out.println(item);		
//		}
	}
}