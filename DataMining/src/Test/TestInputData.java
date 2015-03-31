package Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import DataDefination.Attribute;
import DataDefination.Instance;
import Input.DealWithInputData;

public class TestInputData {
	public static void main(String[] args) throws IOException {
		ArrayList<String> res = new ArrayList<String>();
		Scanner in = new Scanner(new File("testProdIntro.binary.txt"));
		while(in.hasNext()) {
			String s = in.next();
			res.add(s);
		}
		System.out.println(res);
		
	}
}
