/*
 * Author: Charlotte Lin
 * Date: 2015/3/31
 * 
 */
package ProcessInput;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import DataDefination.Attribute;
import DataDefination.Instance;

public class ProcessInputData {
	
	// field attributeSet stores all attributes of input data
	// field instanceSet stores all instances of input data
	ArrayList<Attribute> attributeSet;
	ArrayList<Instance> instanceSet;
	HashSet<Attribute> attributeHashSet;
	Attribute targetAttribute;
	
	/**
	 * Constructor for the
	 * @param fileName: file name of input data file
	 * @throws IOException 
	 */
	public ProcessInputData(String fileName) throws IOException {
		attributeHashSet = new HashSet<Attribute>();
		attributeSet = new ArrayList<Attribute>();
		instanceSet = new ArrayList<Instance>();
				
		Scanner in = new Scanner(new File(fileName));
		
		// Pass the first two line of input data.
		if (!in.hasNextLine()) throw new IOException("Invalid input format");
		in.nextLine();
		if (!in.hasNextLine()) throw new IOException("Invalid input format");
		in.nextLine();
		
		String line = in.nextLine();
		// Put all attributes into attributeSet
		while (!line.equals("")) {
						
			// lineArr should have three elements. 
			// lineArr[1] is attribute name; lineArr[2] is attribute value
			String[] lineArr = line.split("\\s+");
			
			if (lineArr.length != 3) throw new IOException("Invalid input format");
			Attribute attr = new Attribute(lineArr[1], lineArr[2]);
			attributeSet.add(attr);
			attributeHashSet.add(attr);
			line = in.nextLine();
		}
		targetAttribute = attributeSet.get(attributeSet.size() - 1);
		
		// Pass the next two line
		if (!in.hasNextLine()) throw new IOException("Invalid input format");
		line = in.nextLine();
		
		// Put all instances into instanceSet
		while (in.hasNextLine()) {
			line = in.nextLine();
			String[] lineArr = line.split(",");
			Instance item = new Instance();
			for (int i = 0; i < lineArr.length; i++) {
				item.addAttribute(attributeSet.get(i).getName(), lineArr[i]);
			}		
			instanceSet.add(item);			
		}
	}
	
	public ArrayList<Attribute> getAttributeSet() {
		attributeSet.remove(attributeSet.size() - 1);
		return attributeSet;
	}
	public ArrayList<Instance> getInstanceSet(){
		return instanceSet;
	}
	public HashSet<Attribute> getAttributeHashSet() {
		return attributeHashSet;
	}
	public Attribute getTargetAttribute() {
		return targetAttribute;
	}
	// unit test
	public static void main(String[] args) throws IOException {
		ProcessInputData test = new ProcessInputData("trainProdSelection.arff");
		ArrayList<Attribute> attributes = test.getAttributeSet();
		ArrayList<Instance> instances = test.getInstanceSet();
		for (Attribute item : attributes) {
			System.out.println(item);
		}		
		for (Instance item : instances) {
			System.out.println(item);
		}		
		System.out.println(test.targetAttribute);
	}
}
