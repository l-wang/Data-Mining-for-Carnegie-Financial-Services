package knn;

public class DataEntry {
	private String[] symbolicAttributes;
	private double[] NumericAttributes;
	private String label;
	
	public DataEntry(String[] attributes, String label) throws Exception {
		if (attributes.length != 6) {
			throw new Exception("invalid input attributes");
		}
		symbolicAttributes = new String[]{attributes[0], attributes[1]};
		NumericAttributes = new double[4];
		for (int i = 0; i < 4; i++) {
			NumericAttributes[i] = Double.parseDouble(attributes[i + 2]);
		}
		this.label = label;
	}
	
	public String[] getSymbolicAttributes() {
		return symbolicAttributes;
	}
	
	public double[] getNumericAttributes() {
		return NumericAttributes;
	}
	
	public String getLabel() {
		return label;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < symbolicAttributes.length; i++) {
			sb.append(symbolicAttributes[i] + ",");
		}
		for (int i = 0; i < NumericAttributes.length; i++) {
			sb.append(NumericAttributes[i] + ",");
		}
		sb.append(label);
		sb.append("]\n");
		
		return sb.toString();
	}
}
