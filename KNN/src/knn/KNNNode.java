package knn;
public class KNNNode implements Comparable<KNNNode> {
	private int index; // index in trainData
	private double simScore; // distance from test data
	private double label; // class label
	private String labelType; // class label type
	
	public KNNNode(int index, double simScore, double label, String labelType) {
		this.index = index;
		this.simScore = simScore;
		this.label = label;
		this.labelType = labelType;
	}	
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public double getSimScore() {
		return simScore;
	}
	
	public void setDistance(double simScore) {
		this.simScore = simScore;
	}
	
	public double getLabel() {
		return label;
	}
	
	public void setLabel(double label) {
		this.label = label;
	}
	
	public String getLabelType() {
		return labelType;
	}
	
	public void seetLabelType(String labelType) {
		this.labelType = labelType;
	}
	
	@Override
	public int compareTo(KNNNode other) {
		if (this.getSimScore() < other.getSimScore()) {
			// lower simScore -> higher priority
			return 1;
		} else if (this.getSimScore() == other.getSimScore() && !this.getLabelType().equals("continuous")) {
			// for symbolic label: bigger label string -> higher priority
			return (int) (this.getLabel() - other.getLabel());
		} else {
			return -1;
		}
	}
	
	public String toString() {
		return "[" + index + " " + simScore + " " + label + "]";
	}
}
