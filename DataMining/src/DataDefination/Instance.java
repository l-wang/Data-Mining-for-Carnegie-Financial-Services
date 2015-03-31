/*
 * Author: Charlotte Lin
 * Date: 2015/3/31
 * 
 */
package DataDefination;

import java.util.HashMap;

public class Instance {
	private static int instanceCount = 0;
	private int instanceIndex;
	private HashMap<String, String> attributeValuePairs;
	public Instance() {
		instanceIndex = instanceCount;
		attributeValuePairs = new HashMap<String, String>();
		instanceCount++;
	}
	public void addAttribute(String name, String value) {
		attributeValuePairs.put(name, value);
	}
	public int getInstanceIndex() {
		return instanceIndex;
	}
	public String toString() {
		
	}
}
