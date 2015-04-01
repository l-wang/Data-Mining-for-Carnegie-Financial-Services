package knn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class KNN {
	
	public void classify(ArrayList<DataEntry> DTrain, DataEntry de, int k, double[] weights) {
		// find k nearest neighbours and their classes
		PriorityQueue<KNNNode> queue = new PriorityQueue<KNNNode>(k, comparitor);
		for (int i = 0; i < k; i++) {
			DataEntry curr = DTrain.get(i);
			queue.add(new KNNNode(i, calDistance(de, curr, weights), curr.getLabel()));
		}
		
		for (int i = k; i < DTrain.size(); i++) {
			DataEntry curr = DTrain.get(i);
			double distance = calDistance(de, curr, weights);
			if (distance < queue.peek().getDistance()) {
				queue.poll();
				queue.add(new KNNNode(i, distance, curr.getLabel()));
			}
		}
		//System.out.println(queue);
		
		// count number of labels in KNN
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (KNNNode node: queue) {
			String label = node.getC();
			if (map.containsKey(label)) {
				map.put(label, map.get(label) + 1);
			} else {
				map.put(label, 1);
			}
		}
		// find max count
		int maxCount = 0;
		for (String label: map.keySet()) {
			int count = map.get(label);
			if (count > maxCount) {
				maxCount = count;
			}
		}
		
		// collects max count(s)
		ArrayList<String> maxLabels = new ArrayList<String>();
		for (String label: map.keySet()) {
			int count = map.get(label);
			if (count == maxCount) {
				maxLabels.add(label);
			}
		}
		
		// assign class label
		String kvote;
		if (maxLabels.size() == 1) {
			kvote = maxLabels.get(0);
		} else {
			if (maxLabels.contains("C1")) {
				kvote = "C1";
			} else if (maxLabels.contains("C2")) {
				kvote = "C2";
			} else if (maxLabels.contains("C3")) {
				kvote = "C3";
			} else if (maxLabels.contains("C4")) {
				kvote = "C4";
			} else {
				kvote = "C5";
			}
		}
		
		System.out.println("Test Data: " + de + "\tLabel: " + kvote);
	}
	
	private Comparator<KNNNode> comparitor = new Comparator<KNNNode>() {
		public int compare(KNNNode n1, KNNNode n2) {
			if (n1.getDistance() > n2.getDistance()) {
				// bigger distance -> higher priority
				return 1;
			} else if (n1.getDistance() == n2.getDistance()) {
				// bigger label string -> highter priority
				return n1.getC().compareTo(n2.getC());
			} else {
				return 0;
			}
		}
	};
	
	private double calDistance(DataEntry d1, DataEntry d2, double[] weights) {
		// add weight later
		double distance = 0;
		for (int i = 0; i < 2; i++) {
			if (!d1.getSymbolicAttributes()[i].equals(d2.getSymbolicAttributes()[i])) {
				distance += 1 * weights[i];
			}
		}
		for (int i = 0; i < 4; i++) {
			double diff = d1.getNumericAttributes()[i] - d2.getNumericAttributes()[i];
			distance += Math.pow(diff, 2) * weights[i];
		}
		distance = Math.sqrt(distance);
		return distance;
	}	
}


	

