/*
 * Author: Charlotte Lin
 * Date: 2015/3/31
 * 
 */
package Input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DataDefination.Attribute;
import DataDefination.Instance;

public class DealWithInputData {
	
	// field attributeSet stores all attributes of input data
	ArrayList<Attribute> attributeSet;
	
	// field instanceSet stores all instances of input data
	ArrayList<Instance> instanceSet;
	
	/**
	 * Constructor for the
	 * @param fileName: file name of input data file
	 */
	public DealWithInputData(String fileName) {
		attributeSet = new ArrayList<Attribute>();
		instanceSet = new ArrayList<Instance>();
		
		Scanner in = new Scanner(fileName);
		
		// Pass the first two line of input data.
		if (!in.hasNextLine()) throw new IOException("Invalid input format");
		in.nextLine();
		if (!in.hasNextLine()) throw new IOException("Invalid input format");
		in.nextLine();
		
		// Put all attributes into attributeSet
		while (!in.nextLine().equals("\n")) {
			String line = in.nextLine();
			
			// lineArr should have three elements. 
			// lineArr[1] is attribute name; lineArr[2] is attribute value
			String[] lineArr = line.split(" ");
			if (lineArr.length != 3) throw new IOException("Invalid input format");
			Attribute attr = new Attribute(lineArr[1], lineArr[2]);
			attributeSet.add(attr);
		}
		
		// Pass the next line
		if (!in.hasNextLine()) throw new IOException("Invalid input format");
		in.nextLine();
		
		// Put all instances into instanceSet
		while (in.hasNextLine() && !in.nextLine().equals("\n")) {
			String line = in.nextLine();
			String[] lineArr = line.split(",");
			for (int i = 0; i < lineArr.length; i++) {
				Instance inst = new Instance(attributeSet.get(i).getName(), lineArr[i]);
				instanceSet.add(inst);
			}			
		}
	}
	
	public ArrayList<Attribute> getAttributeSet() {
		return attributeSet;
	}
	public ArrayList<Instance> getInstanceSet(){
		return instanceSet;
	}
	
	public static void main(String[] args) {
		
	}
}
