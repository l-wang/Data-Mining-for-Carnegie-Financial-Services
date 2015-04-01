package knn;
public class KNNNode {
	private int index; // index in DTrain
	private double distance; // distance from test data
	private String c; // class label
	public KNNNode(int index, double distance, String c) {
		this.index = index;
		this.distance = distance;
		this.c = c;
	}	
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public double getDistance() {
		return distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public String getC() {
		return c;
	}
	
	public void setC(String c) {
		this.c = c;
	}
	
	public String toString() {
		return "[" + index + " " + distance + " " + c + "]";
	}
}
