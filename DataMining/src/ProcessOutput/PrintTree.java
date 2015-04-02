package ProcessOutput;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import C45CoreAlgorithm.ConstructTree;
import TreeDefination.TreeNode;

public class PrintTree {
	private int height;
	private int nodeN;
	private String pic;
	
	public PrintTree(TreeNode root) {
		StringBuilder sb = new StringBuilder();
		
		// Level order traversal
		LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
		queue.offer(root);
		int level = 0;
		int currN = 1;
		int nextN = 0;
		while (!queue.isEmpty()) {
			TreeNode node = queue.poll();
//			System.out.println(node.getAttribute());
			currN--;
			if (node.getAttribute() != null) sb.append(node.getAttribute().getName());
			sb.append("||");
			HashMap<String, TreeNode> children = node.getChildren();
			if (children != null) {
				for (String valueName : children.keySet()) {
					sb.append(valueName);
					queue.offer(children.get(valueName));
					nextN++;
				}
			}
			if (currN == 0) {
				currN = nextN;
				nextN = 0;
				level++;
				sb.append("\n");
			}
		}
		pic = sb.toString();
	}
	
	public String toSting() {
		return pic;
	}
	public static void main(String[] args) throws IOException {
		ConstructTree test = new ConstructTree("trainProdSelection.txt");
		
		PrintTree test2 = new PrintTree(test.construct());
		System.out.println("********************");
		System.out.println(test2.pic);
	}
}
