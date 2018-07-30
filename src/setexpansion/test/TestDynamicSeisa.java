package setexpansion.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import setexpansion.algorithm.DynamicSeisa;
import setexpansion.algorithm.SumOfPairs;
import setexpansion.algorithm.SumOfTerms;
import setexpansion.util.DataReader;
import setexpansion.util.Tuple;

/**
 * This is test class for dynamic seisa algorithm.
 * 
 * @author sanket
 *
 */
public class TestDynamicSeisa {

	private static int NUM_OF_TEST_PER_FILE = 10;
	private static int TERM_LENGTH_TO_INCLUDE = 6;
	//private static int SEED_SET_SIZE = 2;
	private static double[] alphaVal = new double[10];
	public static ArrayList<String> fileNames;

	public static void main(String[] args) throws IOException {
		double alpha = 0.1;
		for (int i = 0; i < 9; i++) {
			alphaVal[i] = alpha;
			alpha = alpha + 0.1;
		}
		List<Integer> seedList = new ArrayList();
		seedList.add(2);
		seedList.add(3);
		seedList.add(4);
		seedList.add(6);
		fileNames = new ArrayList();
		String splitFolder = "./SplitSeisa";
		File folder = new File(splitFolder);
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].getName().startsWith("data_")) {
				fileNames.add(listOfFiles[i].getName());
			}
		}
		String algoType = "dynamic-";
		String splitdataset =algoType+ "splitdataset";
		String result =algoType+ "result";
		String avgResult = algoType+ "avgResult";
		File avgResultFolder = new File(avgResult);
		File datasetFolder = new File(splitdataset);
		File resultFolder = new File(result);
		if (datasetFolder.exists()) {
			datasetFolder.delete();
		}
		if (resultFolder.exists()) {
			resultFolder.delete();
		}
		if (avgResultFolder.exists()) {
			avgResultFolder.delete();
		}
		datasetFolder.mkdir();
		resultFolder.mkdir();
		avgResultFolder.mkdir();
		// fileNames.add("data_aa");
		// fileNames.add("data_ab");
		for (int i = 0; i < fileNames.size(); i++) {
			// Map<String, ArrayList<String>> totaldata = new HashMap();
			System.out.println("***********Starting for file:" + fileNames.get(i) + " ************************");
			Map<String, ArrayList<String>> removedData = new HashMap();
			File file = new File(splitFolder + "/" + fileNames.get(i));
			LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

			String outFileName = "./" + splitdataset + "/output-" + fileNames.get(i) + ".txt";
			File file1 = new File(outFileName);
			if (file1.exists()) {
				file1.delete();
			}
			PrintWriter outFile1 = new PrintWriter(new FileWriter(outFileName, true));
			int lineFoundToRemove = 0;
			while (iterator.hasNext()) {
				String line = iterator.nextLine();
				String[] listTermPair = line.split("\t\t");
				// System.out.println(listTermPair[0]);
				// if(listTermPair.length == 1){
				// System.out.println("hi");
				// }
				String[] terms = listTermPair[1].split("\t");
				if (terms.length >= TERM_LENGTH_TO_INCLUDE) {
					if (lineFoundToRemove != NUM_OF_TEST_PER_FILE) {
						ArrayList<String> termList = new ArrayList();
						for (int k = 0; k < terms.length; k++) {
							termList.add(terms[k]);
						}
						removedData.put(listTermPair[0], termList);
						lineFoundToRemove++;
					} else {
						outFile1.println(line);
						outFile1.flush();
					}
				} else {
					outFile1.println(line);
					outFile1.flush();
				}
			}
			outFile1.close();
			DataReader dr = new DataReader();
			dr.generatedSortedFile(outFileName);
			Tuple.sort("smallterm.txt", "smallTermSorted.txt");
			Tuple.sort("smalltermPair.txt", "smallTermPairSorted.txt");
			SumOfTerms.sumOfTerms("smallTermSorted.txt", "smalltermCount.txt");
			SumOfPairs.sumOfPairs("smallTermPairSorted.txt", "smallTermPairCount.txt");

			
			for (int seedIndex = 0; seedIndex < 4; seedIndex++) {
				int SEED_SET_SIZE = seedList.get(seedIndex);
				System.out.println("Calculating for seed=" + SEED_SET_SIZE);
				
				String outputAvgAlhpaPrecisionFile = "./" + avgResult + "/avg-precison-" + fileNames.get(i) +"-seed="+SEED_SET_SIZE +".txt";
				String outputAvgAlhpaRecallFile = "./" + avgResult + "/avg-recall-" + fileNames.get(i) +"-seed="+SEED_SET_SIZE+ ".txt";
				file1 = new File(outputAvgAlhpaPrecisionFile);
				if (file1.exists()) {
					file1.delete();
				}
				file1 = new File(outputAvgAlhpaRecallFile);
				if (file1.exists()) {
					file1.delete();
				}
				PrintWriter outFile4 = new PrintWriter(new FileWriter(outputAvgAlhpaPrecisionFile, true));
				PrintWriter outFile5 = new PrintWriter(new FileWriter(outputAvgAlhpaRecallFile, true));
				
				for (int index = 0; index < 9; index++) {
					System.out.println("Calculating for alhpa=" + alphaVal[index]);
					Iterator iter = (Iterator) removedData.entrySet().iterator();
					outFileName = "./" + result + "/result-" + fileNames.get(i) + "-seed="+SEED_SET_SIZE+"-alpha=" + alphaVal[index] + ".txt";
					file1 = new File(outFileName);
					if (file1.exists()) {
						file1.delete();
					}
					// PrintWriter outFile2 = new PrintWriter(new
					// FileWriter(outFileName, true));
					String precisionFile = outFileName + "-precision";
					String recallFile = outFileName + "-recall";
					file1 = new File(precisionFile);
					if (file1.exists()) {
						file1.delete();
					}
					file1 = new File(recallFile);
					if (file1.exists()) {
						file1.delete();
					}
					PrintWriter outFile2 = new PrintWriter(new FileWriter(precisionFile, true));
					PrintWriter outFile3 = new PrintWriter(new FileWriter( recallFile, true));
					
					int queryNo = 0;
					double avgPrecision = 0.0;
					double avgRecall = 0.0;
					while (iter.hasNext()) {
						queryNo++;
						System.out.println("*****QUERY:" + queryNo + " STARTED**********");
						Map.Entry entry = (Map.Entry) iter.next();
						ArrayList<String> groundTruth = (ArrayList<String>) entry.getValue();
						String listId = (String) entry.getKey();
						Set<String> seeds = new HashSet();
						for (int ss = 0; ss < SEED_SET_SIZE; ss++) {
							seeds.add(groundTruth.get(ss));
							// seeds.add(groundTruth.get(1));
						}
						// seeds.add(groundTruth.get(0));
						// .add(groundTruth.get(1));
						Set<String> expandedSet = DynamicSeisa.getExpandedSet(seeds, alphaVal[index]);
						// outFile2.println("For Listid: "+ listId + " ,Seeds
						// are:"+
						// groundTruth.get(0) + " " + groundTruth.get(1));
						// outFile2.flush();
						String truth = "";
						for (int j = 0; j < groundTruth.size(); j++) {
							truth = truth + " " + groundTruth.get(j);
						}
						// outFile2.println("GroundTruth is: " + truth);
						// outFile2.flush();
						Iterator iter1 = expandedSet.iterator();
						String output = "";
						while (iter1.hasNext()) {
							output = output + iter1.next() + " ";
						}
						// outFile2.println("Result retrieved is: "+output);
						// outFile2.flush();
						double precision = getPrecision(groundTruth, expandedSet);
						double recall = getRecall(groundTruth, expandedSet);
						System.out.println("Seeds are:" + groundTruth.get(0) + " " + groundTruth.get(1));
						System.out.println("Precision:" + precision + " Recall:" + recall);
						// outFile2.println("Precision:" + precision + "
						// Recall:" +
						// recall);
						outFile2.println(precision);
						outFile2.flush();
						outFile3.println(recall);
						outFile3.flush();
						avgPrecision = avgPrecision + precision;
						avgRecall = avgRecall + recall;
						System.out.println("#####QUERY:" + queryNo + " DONE########");
					}
					avgPrecision = avgPrecision / (double) NUM_OF_TEST_PER_FILE;
					avgRecall = avgRecall / (double) NUM_OF_TEST_PER_FILE;
					outFile4.println(avgPrecision);
					outFile4.flush();
					outFile5.println(avgRecall);
					outFile5.flush();
					outFile2.close();
					// outFile1.close();
					outFile3.close();
					System.out.println("###############Done for Alpha:" + alphaVal[index] + " ################");
				}
				outFile4.close();
				outFile5.close();
				System.out.println("###############Done for seed:" + seedList.get(seedIndex) + " ################");
			}
			System.out.println("###############Ending for file:" + fileNames.get(i) + " ################");
		}

	}

	private static double getRecall(ArrayList<String> groundTruth, Set<String> expandedSet) {
		if (expandedSet == null || expandedSet.size() == 0) {
			return 0;
		}
		int truePositivesCount = 0;
		Iterator iter = expandedSet.iterator();
		while (iter.hasNext()) {
			if (groundTruth.contains((String) iter.next())) {
				truePositivesCount++;
			}
		}
		double recall = (double) truePositivesCount / (double) groundTruth.size();
		return recall;
	}

	private static double getPrecision(ArrayList<String> groundTruth, Set<String> expandedSet) {
		if (expandedSet == null || expandedSet.size() == 0) {
			return 0;
		}
		int truePositivesCount = 0;
		Iterator iter = expandedSet.iterator();
		while (iter.hasNext()) {
			if (groundTruth.contains((String) iter.next())) {
				truePositivesCount++;
			}
		}
		double precision = (double) truePositivesCount / (double) expandedSet.size();
		return precision;
	}

}
