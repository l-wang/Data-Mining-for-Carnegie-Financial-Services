package knn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import DataDefination.Attribute;


public class KNN {
	
	public double classify(ArrayList<ArrayList<Double>> trainData, ArrayList<Double> e, int k, 
			ArrayList<Attribute> attributesAndResult, ArrayList<double[][]> matrixes, double[] weights) {
		
		String labelType = attributesAndResult.get(attributesAndResult.size() - 1).getType();
		
		// find k nearest neighbours and their classes
		PriorityQueue<KNNNode> queue = findKNearest(trainData, e, k, attributesAndResult, matrixes, weights, labelType);
		//System.out.println(queue);
		
		double kvote;
		// predict symbolic label
		if (!labelType.equals("continuous")) {
			kvote = predictSymbolicLabel(queue); // label index
		} else {
			kvote = predictNumericLabel(queue); // actual result
		}
		
		return kvote;
	}
	
	private double predictSymbolicLabel(PriorityQueue<KNNNode> queue) {
		// count number of labels in KNN
		HashMap<Double, Integer> map = new HashMap<Double, Integer>();
		for (KNNNode node: queue) {
			double label = node.getLabel();
			if (map.containsKey(label)) {
				map.put(label, map.get(label) + 1);
			} else {
				map.put(label, 1);
			}
		}

		// find max count
		int maxCount = 0;
		for (Double label: map.keySet()) {
			int count = map.get(label);
			if (count > maxCount) {
				maxCount = count;
			}
		}
		
		// collects max count(s)
		ArrayList<Double> maxLabels = new ArrayList<Double>();
		for (Double label: map.keySet()) {
			int count = map.get(label);
			if (count == maxCount) {
				maxLabels.add(label);
			}
		}
		
		// assign class label
		Collections.sort(maxLabels);
		return maxLabels.get(0);
	}
	
	private double predictNumericLabel(PriorityQueue<KNNNode> queue) {
		double numerator = 0;
		double denominator = 0;
		
		for (KNNNode node: queue) {
			double value = node.getLabel();
			double simScore = node.getSimScore();
			numerator += value * simScore;
			denominator += simScore;
		}
		
		return numerator / denominator;
	}
	
	private PriorityQueue<KNNNode> findKNearest(ArrayList<ArrayList<Double>> trainData, ArrayList<Double> e, int k, 
			ArrayList<Attribute> attributesAndResult, ArrayList<double[][]> matrixes, double[] weights, String labelType) {
		PriorityQueue<KNNNode> queue = new PriorityQueue<KNNNode>(k, comparitor);
		for (int i = 0; i < k; i++) {
			ArrayList<Double> curr = trainData.get(i);
			double simScore = calSimScore(e, curr, attributesAndResult, matrixes, weights);
			double label = curr.get(curr.size() - 1);
			queue.add(new KNNNode(i, simScore, label, labelType));
		}
		
		for (int i = k; i < trainData.size(); i++) {
			ArrayList<Double> curr = trainData.get(i);
			double simScore = calSimScore(e, curr, attributesAndResult, matrixes, weights);
			double label = curr.get(curr.size() - 1);
			KNNNode currNode = new KNNNode(i, simScore, label, labelType);
			
			if (currNode.compareTo(queue.peek()) < 0) {
				queue.poll();
				queue.add(currNode);
			}
		}
		return queue;
	}

	private Comparator<KNNNode> comparitor = new Comparator<KNNNode>() {
		public int compare(KNNNode n1, KNNNode n2) {
			if (n1.getSimScore() > n2.getSimScore()) {
				// higher simScore -> higher priority
				return 1;
			} else if (n1.getSimScore() == n2.getSimScore() && !n1.getLabelType().equals("continuous")) {
				// for symbolic label: smaller label string -> higher priority
				//System.out.println("hi");
				return (int) (n2.getLabel() - n1.getLabel());
			} else {
				return -1;
			}
		}
	};

	/*
	 * @d1 test data
	 * @d2 training data
	 * d1.size() + 1 = d2.size()
	 */
	private double calSimScore(ArrayList<Double> d1, ArrayList<Double> d2, 
			ArrayList<Attribute> attributesAndResult, ArrayList<double[][]> matrixes, double[] weights) {
		double distance = 0;
		int matrixIndex = 0;
		for (int i = 0; i < attributesAndResult.size() - 1; i++) {
			Attribute attr = attributesAndResult.get(i);
			if (attr.getType().equals("continuous")) {
				// numeric attributes
				double diff = d1.get(i) - d2.get(i);
				distance += Math.pow(diff, 2) * weights[i];
			} else {
				// symbolic attributes
				int index1 = d1.get(i).intValue();
				int index2 = d2.get(i).intValue();
				double[][] matrix = matrixes.get(matrixIndex);
				double sim = matrix[index1][index2];
				distance += (1 - sim) * weights[i];
				matrixIndex++;
			}
		}
		
		double score = 1.0 / Math.sqrt(distance);
		return score;
	}
	
}


	

