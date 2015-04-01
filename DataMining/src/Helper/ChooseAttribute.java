package Helper;

import java.io.IOException;
import java.util.ArrayList;

import DataDefination.Attribute;
import DataDefination.Instance;
import ProcessInput.ProcessInputData;

public class ChooseAttribute {
	
	public static Attribute choose(Attribute target, ArrayList<Attribute> attributes, 
			ArrayList<Instance> instances) throws IOException {
		Attribute chosen = attributes.get(0);
		double maxInfoGain = CalculateInformationGain.calculate(target, chosen, instances);
		for (int i = 1; i < attributes.size(); i++) {
			Attribute attr = attributes.get(i);
			double attrInfoGain = CalculateInformationGain.calculate(target, attr, instances);
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
		Attribute res = ChooseAttribute.choose(attributes.get(attributes.size() - 1), 
				attributes, instances);		
		System.out.println(res);
	}
}
