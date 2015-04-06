/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/06
 *********************************/
package Client;

import java.io.IOException;
import java.util.ArrayList;

import CV.CrossValidation;
import CV.CrossValidationWithPruning;

public class Main {
	public static void main(String[] args) throws IOException {
				
		System.out.println("Please input the file name of training data: ");
		CrossValidation cv = new CrossValidation(args[0]);
		CrossValidationWithPruning cvP = new CrossValidationWithPruning(args[0]);
		
		System.out.println("Please input the fold number of cross validation: ");
		ArrayList<Double> final_score = cv.validate(Integer.parseInt(args[1]));
		ArrayList<Double> final_score_P = cvP.validate(Integer.parseInt(args[1]));
		
		double r = 0;
		double rP = 0;
		for(int i = 0; i < final_score.size(); i++) {
			r += final_score.get(i);
			rP += final_score_P.get(i);
		}
		System.out.println("Cross Validation before Pruning: " + r / 10);
		System.out.println("Cross Validation after Pruning: " + rP / 10);		
	}
}
