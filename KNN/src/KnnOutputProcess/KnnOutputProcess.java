package KnnOutputProcess;

import java.util.ArrayList;

import DataDefination.Attribute;

public class KnnOutputProcess {
	
	public static String getResult(ArrayList<Attribute> attributesAndResult, ArrayList<Double> e, double kvote) {
		StringBuilder sb = new StringBuilder();
		sb.append("test data: [");
		for (int i = 0; i < attributesAndResult.size() - 1; i++) {
			Attribute attr = attributesAndResult.get(i);
			String attrName = attr.getName();
			sb.append(attrName + " = ");
			String attrType = attr.getType();
			if (attrType.equals("continuous")) {
				sb.append(e.get(i));
			} else {
				sb.append(attr.getValues().get(e.get(i).intValue()));
			}
			sb.append(", ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]; label: ");
		
		Attribute result = attributesAndResult.get(attributesAndResult.size() - 1);
		if (result.getType().equals("continuous")) {
			sb.append(kvote);
		} else {
			int index = (int) kvote;
			sb.append(result.getValues().get(index));
		}
		
		return sb.toString();
	}

}
