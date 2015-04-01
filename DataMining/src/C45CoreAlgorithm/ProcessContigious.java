/*
 * Author: Charlotte Lin
 * Date: 2015/04/01
 * 
 */
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import DataDefination.*;
import ProcessInput.ProcessInputData;

public class ProcessContigious {
	private double threshold;
	private double infoGain;
	
	public ProcessContigious(Attribute attribute, Attribute target, 
			ArrayList<Instance> instances) throws IOException {
		String attributeName = attribute.getName();
		
		Comparator<Instance> comparator = new Comparator<Instance>() {
			@Override
			public int compare(Instance x, Instance y) {
				HashMap<String, String> xPair = x.getAttributeValuePairs();
				String xValue = xPair.get(attributeName);
				
				HashMap<String, String> yPair = y.getAttributeValuePairs();
				String yValue = yPair.get(attributeName);
				if (Double.parseDouble(xValue) - Double.parseDouble(yValue) > 0) return 1;
				else if (Double.parseDouble(xValue) - Double.parseDouble(yValue) < 0) return -1;
				else return 0;
			}
		};
		instances.sort(comparator);
		
		int thresholdPos = -1;
		double infoGain = 0;
		for (int i = 0; i < instances.size() - 1; i++) {
			HashMap<String, String> instancePair = instances.get(i).getAttributeValuePairs();
			String instanceValue = instancePair.get(attributeName);
			HashMap<String, String> instancePair2 = instances.get(i + 1).getAttributeValuePairs();
			String instanceValue2 = instancePair2.get(attributeName);
			
			// not sure accuracy
			if (!instanceValue.equals(instanceValue2)) {
				double itemInfoGain = InformationGain.calculateConti(attribute,
						target, instances, i);
				if (itemInfoGain > infoGain) {
					infoGain = itemInfoGain;
					thresholdPos = i;
				}
			}
		}	
		HashMap<String, String> a = instances.get(thresholdPos).getAttributeValuePairs();
		String aValue = a.get(attributeName);
		HashMap<String, String> b = instances.get(thresholdPos).getAttributeValuePairs();
		String bValue = b.get(attributeName);
		
		this.threshold = (Double.parseDouble(aValue) + Double.parseDouble(bValue)) / 2;	
		this.infoGain = infoGain;
	}
	
	public double getThreshold() {
		return threshold;
	}
	
	public double getInfoGain() {
		return infoGain;
	}
	
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("trainProdSelection.txt");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		for (Attribute item : attributes) {
			System.out.println(item);
		}		
		
		ProcessContigious test2 = new ProcessContigious(attributes.get(5), 
				attributes.get(attributes.size() - 1), instances);
		double res = test2.getInfoGain();
		System.out.println(res);
		double res2 = test2.getThreshold();
		System.out.println(res2);
	}
	
}
