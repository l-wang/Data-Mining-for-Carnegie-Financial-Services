/*
 * Author: Charlotte Lin
 * Date: 2015/04/01
 * 
 */
package C45CoreAlgorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class ChooseAttribute {
	
	public static Attribute choose(Attribute target, HashSet<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		Attribute chosen = null;
		double maxInfoGain = 0;
		for (Attribute attr : attributes) {
			double attrInfoGain = 0;
			if (attr.getType().equals("continuous")) {
				ContigiousToDiscrete contigous = new ContigiousToDiscrete(attr, target, instances);
				attrInfoGain = contigous.getInfoGain();
			}
			else attrInfoGain = CalculateInformationGain.calculate(target, attr, instances);
			if (attrInfoGain < maxInfoGain) {
				maxInfoGain = attrInfoGain;
				chosen = attr;
			}
		}
		return chosen;	
	}
	
	// Unit test
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("trainProdSelection.txt");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		for (Attribute item : attributes) {
			System.out.println(item);
		}		
		for (Instance item : instances) {
			System.out.println(item);
		}		
//		Attribute res = ChooseAttribute.choose(attributes.get(attributes.size() - 1), 
//				attributes, instances);		
//		System.out.println(res);
	}
}
