package ProcessOutput;

import java.util.HashMap;
import java.util.LinkedList;

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
			currN--;
			sb.append(node.getAttribute());
			sb.append("||");
			HashMap<String, TreeNode> children = node.getChildren();
			for (String valueName : children.keySet()) {
				sb.append(valueName);
				queue.offer(children.get(valueName));
				nextN++;
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
	public static void main(String[] args) {
		
	}
}
