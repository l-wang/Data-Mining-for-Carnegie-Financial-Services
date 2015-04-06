/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/06
 *********************************/
package Client;

import java.io.IOException;
import java.util.ArrayList;

import CV.CrossValidation;

public class Main {
	public static void main(String[] args) throws IOException {
		CrossValidation cv = new CrossValidation("trainProdIntro.binary.arff");
		ArrayList<Double> final_score = cv.validate();
		double r = 0;
		for(int i = 0; i < final_score.size(); i++) {
			r += final_score.get(i);
		}
		System.out.println(r / 10);
	}
}
