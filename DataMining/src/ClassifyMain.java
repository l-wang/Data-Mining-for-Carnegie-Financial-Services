/*********************************
 * Author: Xue (Charlotte) Lin
 * Date: 2015/04/06
 *********************************/
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import CV.CrossValidationWithPruning;
import DataDefination.Instance;
import MineData.C45MineData;
import ProcessInput.ProcessInputData;
import ProcessOutput.PrintTree;

public class ClassifyMain {
	public static void main(String[] args) throws IOException {
		
		System.out.println("Hello! Please input the file name of training data: ");
		Scanner in = new Scanner(System.in);
		String fileTrain = in.nextLine();
		System.out.println("Please input the file name of data to be classified: ");
		String fileTest = in.nextLine();
		ProcessInputData inputTest = new ProcessInputData(fileTest);
		ArrayList<Instance> instanceTest = inputTest.getInstanceSet();
		ArrayList<Instance> result = new ArrayList<Instance>();
		C45MineData mine = new C45MineData(fileTrain, fileTest);
		PrintTree print = new PrintTree();
		CrossValidationWithPruning cvP = new CrossValidationWithPruning(fileTrain);
		cvP.validate(10);
		ArrayList<Instance> resultBefore = mine.getResult();
		result = cvP.mine(instanceTest, resultBefore);
		System.out.println("**********************");
		System.out.println("Classify result, using tree before pruning: \n"
				+ "(Test*** is classified label.)");
		
		for (Instance i : resultBefore) {
			System.out.println(i);
		}
		System.out.println("**********************");
		System.out.println("Do you want to see the tree before pruning? (y/n)");
		String printTreeBefore = in.nextLine();
		if (printTreeBefore.equals("y")) {
			System.out.println("Tree before Pruning: \n" + print.printDFS(mine.getRoot()));
		}
		System.out.println("**********************");
		System.out.println("Do you want to see the classify result using the tree after pruning? (y/n)");
		String printResultAfter = in.nextLine();
		if (printResultAfter.equals("y")) {
			System.out.println("Classify result, using tree after pruning: \n"
					+ "(Test*** is classified label.)");
		}
		for (Instance i : result) {
			System.out.println(i);
		}
		System.out.println("**********************");
		System.out.println("Do you want to see the tree after pruning? (y/n)");
		String printTreeAfter = in.nextLine();
		if (printTreeAfter.equals("y")) {
			System.out.println("Tree after Pruning: \n" + print.printDFS(cvP.getRoot()));
		}
		System.out.println("**********************");
		System.out.println("(: The end. Thank you! :)");
		System.out.println("=======================@CMU Team Supernova@====================");
		in.close();
	}
}
