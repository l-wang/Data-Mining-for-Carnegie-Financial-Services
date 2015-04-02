package ProcessOutput;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import C45CoreAlgorithm.ConstructTree;
import TreeDefination.TreeNode;

public class PrintTree {
	private int height;
	private int nodeN;
	private String pic;
	
	public ArrayList<String> printDFS(TreeNode root) {
		ArrayList<String> res = new ArrayList<String>();
		printDFS(root, new StringBuilder(), res);
		return res;
	}
	
	private void printDFS(TreeNode root, StringBuilder sb, ArrayList<String> res) {
		if (root.getType().equals("leaf")) {
			StringBuilder curr = new StringBuilder(sb);
			curr.append(root.getTargetLabel());
			curr.append("\n");
			res.add(curr.toString());
		} else {
			sb.append(root.getAttribute().getName());
			HashMap<String, TreeNode> children = root.getChildren();
			for (String valueName : children.keySet()) {
				StringBuilder curr = new StringBuilder(sb);
				curr.append("(");
				curr.append(valueName);
				curr.append(")");
				printDFS(children.get(valueName), curr, res);
			}
		}	
	}
	
	public String printBFS(TreeNode root) {
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
			if (node.getType().equals("root")) sb.append(node.getAttribute().getName());
			else sb.append(node.getTargetLabel());
			HashMap<String, TreeNode> children = node.getChildren();
			if (children != null) {
				//sb.append("\n");
				sb.append("(");
				for (String valueName : children.keySet()) {
					sb.append(valueName);
					queue.offer(children.get(valueName));
					nextN++;
				}
				sb.append(")");
			}
			if (currN == 0) {
				currN = nextN;
				nextN = 0;
				level++;
				sb.append("\n");
			}
		}
		return sb.toString();
	}
	
	public String toSting() {
		return pic;
	}
	public static void main(String[] args) throws IOException {
		ConstructTree test = new ConstructTree("rain.txt");
		PrintTree test2 = new PrintTree();
		//String res1 = test2.printBFS(test.construct());
		ArrayList<String> res2 = test2.printDFS(test.construct());
		System.out.println("********************");
		System.out.println(res2);
	}
}
