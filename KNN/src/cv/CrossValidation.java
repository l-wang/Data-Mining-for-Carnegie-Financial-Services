package cv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import DataDefination.Attribute;
import KnnOutputProcess.KnnOutputProcess;
import knn.*;
import knnPreProcess.KnnPreProcess;
import knnPreProcess.ScaleData;

public class CrossValidation {

	public static void main(String[] args) throws Exception {
		// =============================== set input
		// ==================================
		// set input file
		String trainDataFile1 = "trainProdSelection.arff";
		String testDataFile1 = "testProdSelection.arff";

		String trainDataFile2 = "trainProdIntro.real.arff";
		String testDataFile2 = "testProdIntro.real.arff";

		String trainDataFile3 = "trainProdIntro.binary.arff";
		String testDataFile3 = "testProdIntro.binary.arff";

		// set weights
		double[] weights1 = new double[] { 0.17, 0.17, 0.17, 0.17, 0.17, 0.17 };
		double[] weights2 = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1,
				0.1 };

		double[] multiply = new double[] { 0.2, 0.8, 1.5, 2.0, 4.0 };

		// set Similarity Matrix - part a
		double[][] matrixA = new double[][] { { 1, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 1 } };

		double[][] matrixB = new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 },
				{ 0, 0, 1, 0 }, { 0, 0, 0, 1 } };

		ArrayList<double[][]> matrixes1 = new ArrayList<double[][]>();
		matrixes1.add(matrixA);
		matrixes1.add(matrixB);

		// set Similarity Matrix - part b
		double[][] matrixC = new double[][] { { 1.0, 0.0, 0.1, 0.3, 0.2 },
				{ 0.0, 1.0, 0.0, 0.0, 0.0 }, { 0.1, 0.0, 1.0, 0.2, 0.2 },
				{ 0.3, 0.0, 0.2, 1.0, 0.1 }, { 0.2, 0.0, 0.2, 0.1, 1.0 } };

		double[][] matrixD = new double[][] { { 1.0, 0.2, 0.1, 0.2, 0.0 },
				{ 0.2, 1.0, 0.2, 0.1, 0.0 }, { 0.1, 0.2, 1.0, 0.1, 0.0 },
				{ 0.2, 0.1, 0.1, 1.0, 0.0 }, { 0.0, 0.0, 0.0, 0.0, 1.0 } };

		double[][] matrixE = new double[][] { { 1.0, 0.1, 0.0 },
				{ 0.1, 1.0, 0.1 }, { 0.0, 0.1, 1.0 } };

		double[][] matrixF = new double[][] { { 1.0, 0.8, 0.0, 0.0 },
				{ 0.8, 1.0, 0.1, 0.5 }, { 0.0, 0.1, 1.0, 0.4 },
				{ 0.0, 0.5, 0.4, 1.0 } };

		ArrayList<double[][]> matrixes2 = new ArrayList<double[][]>();
		matrixes2.add(matrixC);
		matrixes2.add(matrixD);
		matrixes2.add(matrixE);
		matrixes2.add(matrixF);

		// set k
		int k = 3;

		// =========================== start KNN ===============================
		System.out.println("======= Part A Start ======= ");
		// process input files, put input data into 2d arraylists
		KnnPreProcess kpp = new KnnPreProcess(trainDataFile1);
		ArrayList<ArrayList<Double>> trainData = kpp.getTrainData();
		ArrayList<Attribute> attributesAndResult = kpp.getAttributesAndResult();

		// calculate knn
		KNN knn = new KNN();
		int k_fold = 30;
		int count = trainData.size() / k_fold;
		double max_correct = 0.0;
		int max_m = 0, max_w = 0;
		double[] weights = new double[weights1.length];
		boolean end = false;
		while (max_correct < 0.95 && end == false) {
			end = true;
			Collections.shuffle(trainData, new Random((long) 10000));
			for (int w = 0; w < weights1.length; w++) {
				for (int m = 0; m < multiply.length; m++) {
					System.arraycopy(weights1, 0, weights, 0, weights1.length);
					weights[w] *= multiply[m];
					double correct = 0.0;
					for (int i = 0; i < k_fold; i++) {
						ArrayList<ArrayList<Double>> testData_cv = new ArrayList<ArrayList<Double>>(
								trainData.subList(count * i, count * (i + 1)));
						ArrayList<ArrayList<Double>> trainData_cv = new ArrayList<ArrayList<Double>>(
								trainData.subList(0, count * i));
						trainData_cv.addAll(trainData.subList(count * (i + 1),
								trainData.size()));

						// keep original testData for future output use
						ArrayList<ArrayList<Double>> originalTestData = new ArrayList<ArrayList<Double>>();
						for (int j = 0; j < testData_cv.size(); j++) {
							originalTestData.add(new ArrayList<Double>(
									testData_cv.get(j)));
						}

						// scale numeric attributes
						ScaleData sd = new ScaleData(attributesAndResult,
								trainData_cv);
						trainData_cv = sd.scaleData(trainData_cv);
						testData_cv = sd.scaleData(testData_cv);

						for (int j = 0; j < testData_cv.size(); j++) {
							ArrayList<Double> e = testData_cv.get(j);
							double kvote = knn.classify(trainData_cv, e, k,
									attributesAndResult, matrixes1, weights);
							// output result
							ArrayList<Double> oldE = originalTestData.get(j);
							String vote = KnnOutputProcess.getResult(
									attributesAndResult, oldE, kvote);
							// System.out.println(originalTestData.get(j).get(6)
							// + " " + vote + " " + kvote);
							if (originalTestData.get(j).get(6) - kvote == 0)
								correct++;
						}
					}
					correct /= (k_fold * count);
					if (correct > max_correct) {
						max_correct = correct;
						max_m = m;
						max_w = w;
						end = false;
					}
				}
			}
			weights1[max_w] *= multiply[max_m];
			for (double d : weights1)
				System.out.print(d + " ");
			System.out.println(max_correct);
		}
		System.out.println("============ Part A end ==============");

		System.out.println("============ Part B (real) Start ==============");
		// process input files, put input data into 2d arraylists
		kpp = new KnnPreProcess(trainDataFile2);
		trainData = kpp.getTrainData();
		attributesAndResult = kpp.getAttributesAndResult();

		// calculate knn
		count = trainData.size() / k_fold;
		double min_error = Double.MAX_VALUE;
		max_m = 0;
		max_w = 0;
		weights = new double[weights2.length];
		end = false;
		while (min_error > 1 && end == false) {
			end = true;
			Collections.shuffle(trainData, new Random((long) 10000));
			for (int w = 0; w < weights1.length; w++) {
				for (int m = 0; m < multiply.length; m++) {
					System.arraycopy(weights2, 0, weights, 0, weights2.length);
					weights[w] *= multiply[m];
					double error = 0.0;
					for (int i = 0; i < k_fold; i++) {
						ArrayList<ArrayList<Double>> testData_cv = new ArrayList<ArrayList<Double>>(
								trainData.subList(count * i, count * (i + 1)));
						ArrayList<ArrayList<Double>> trainData_cv = new ArrayList<ArrayList<Double>>(
								trainData.subList(0, count * i));
						trainData_cv.addAll(trainData.subList(count * (i + 1),
								trainData.size()));

						// keep original testData for future output use
						ArrayList<ArrayList<Double>> originalTestData = new ArrayList<ArrayList<Double>>();
						for (int j = 0; j < testData_cv.size(); j++) {
							originalTestData.add(new ArrayList<Double>(
									testData_cv.get(j)));
						}

						// scale numeric attributes
						ScaleData sd = new ScaleData(attributesAndResult,
								trainData_cv);
						trainData_cv = sd.scaleData(trainData_cv);
						testData_cv = sd.scaleData(testData_cv);

						for (int j = 0; j < testData_cv.size(); j++) {
							ArrayList<Double> e = testData_cv.get(j);
							double kvote = knn.classify(trainData_cv, e, k,
									attributesAndResult, matrixes2, weights);
							// output result
							ArrayList<Double> oldE = originalTestData.get(j);
							String vote = KnnOutputProcess.getResult(
									attributesAndResult, oldE, kvote);
							// System.out.println(originalTestData.get(j).get(8)
							// d+ " " + vote + " " + kvote);
							error += (originalTestData.get(j).get(8) - kvote)
									* (originalTestData.get(j).get(8) - kvote);
						}
					}
					error /= (k_fold * count);
					if (error < min_error) {
						min_error = error;
						max_m = m;
						max_w = w;
						end = false;
					}
				}
			}
			weights2[max_w] *= multiply[max_m];
			for (double d : weights2)
				System.out.print(d + " ");
			System.out.println(min_error);
		}
		System.out.println("============ Part B (real) End ==============");

		System.out.println("============ Part B (binary) Start ==============");
		// process input files, put input data into 2d arraylists
		kpp = new KnnPreProcess(trainDataFile3);
		trainData = kpp.getTrainData();
		attributesAndResult = kpp.getAttributesAndResult();

		// calculate knn
		count = trainData.size() / k_fold;
		max_correct = 0.0;
		max_m = 0;
		max_w = 0;
		weights2 = new double[] { 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1 };
		weights = new double[weights2.length];
		end = false;
		while (max_correct < 0.95 && end == false) {
			end = true;
			Collections.shuffle(trainData, new Random((long) 10000));
			for (int w = 0; w < weights2.length; w++) {
				for (int m = 0; m < multiply.length; m++) {
					System.arraycopy(weights1, 0, weights, 0, weights1.length);
					weights[w] *= multiply[m];
					double correct = 0.0;
					for (int i = 0; i < k_fold; i++) {
						ArrayList<ArrayList<Double>> testData_cv = new ArrayList<ArrayList<Double>>(
								trainData.subList(count * i, count * (i + 1)));
						ArrayList<ArrayList<Double>> trainData_cv = new ArrayList<ArrayList<Double>>(
								trainData.subList(0, count * i));
						trainData_cv.addAll(trainData.subList(count * (i + 1),
								trainData.size()));

						// keep original testData for future output use
						ArrayList<ArrayList<Double>> originalTestData = new ArrayList<ArrayList<Double>>();
						for (int j = 0; j < testData_cv.size(); j++) {
							originalTestData.add(new ArrayList<Double>(
									testData_cv.get(j)));
						}

						// scale numeric attributes
						ScaleData sd = new ScaleData(attributesAndResult,
								trainData_cv);
						trainData_cv = sd.scaleData(trainData_cv);
						testData_cv = sd.scaleData(testData_cv);

						for (int j = 0; j < testData_cv.size(); j++) {
							ArrayList<Double> e = testData_cv.get(j);
							double kvote = knn.classify(trainData_cv, e, k,
									attributesAndResult, matrixes2, weights);
							// output result
							ArrayList<Double> oldE = originalTestData.get(j);
							String vote = KnnOutputProcess.getResult(
									attributesAndResult, oldE, kvote);
							// System.out.println(originalTestData.get(j).get(6)
							// + " " + vote + " " + kvote);
							if (originalTestData.get(j).get(8) - kvote == 0)
								correct++;
						}
					}
					correct /= (k_fold * count);
					if (correct > max_correct) {
						max_correct = correct;
						max_m = m;
						max_w = w;
						end = false;
					}
				}
			}
			weights2[max_w] *= multiply[max_m];
			for (double d : weights2)
				System.out.print(d + " ");
			System.out.println(max_correct);
		}
		System.out.println("============ Part B (binary) End ==============");

	}
}
