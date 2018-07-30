/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setexpansion.algorithm;

import static setexpansion.util.MapUtility.DESC;
import static setexpansion.util.MapUtility.sortByComparator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;

import setexpansion.util.MapUtility;

/**
 * This class provide the implementation of Static Threshold algorithm.
 * 
 * @author nawshad and sanket
 */
public class StaticThreshold
{
	public static String currentPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-30-0.8\\";

	// public static int findItemNumber = 30;
	public static int resultItemNumber_old = 20;

	public static Set<String> getExpandedSet(Set<String> seeds, double alpha, int number) throws IOException
	{

		int resultItemNumber = number;

		MapUtility mUtil = new MapUtility();
		Set<String> dictionary = mUtil.termCountMap.keySet();
		Map<String, Double> relScoreMap = mUtil.findRelScore(dictionary, seeds);

		Map<String, Double> sortedMapDesc = sortByComparator(relScoreMap, DESC);

		int thresHoldIndex = mUtil.OtsusThreshold(mUtil.getValuesFromMap(sortedMapDesc));

		/*
		 * if (thresHoldIndex > 20) { thresHoldIndex = 20; }
		 */

		double thresHold = mUtil.getValuesFromMap(sortedMapDesc).get(thresHoldIndex);
		// System.out.println("Threshold:" + thresHold);
		// thresHold = 0.5416;

		// 2016/08/25 add topK
		// Map<String, Double> topKRelScores = mUtil.findTopK(sortedMapDesc, thresHold);
		Map<String, Double> topKRelScores = mUtil.findTopK_number(sortedMapDesc, thresHold, resultItemNumber);

		// System.out.println("Sorted Map: " + sortedMapDesc);
		// mUtil.printMap(topKRelScores);
		// System.out.println("Size of topkRelscore:" + topKRelScores.size());
		int topKSetSize = topKRelScores.size();

		// topKSetSize = findItemNumber;
		Set<String> prevRelTerms = mUtil.findTopKSet(sortedMapDesc, topKSetSize).keySet();

		System.out.println("The topKSetSize is:" + topKSetSize);// new

		PrintWriter outFile2 = new PrintWriter(new FileWriter(currentPath + "result.txt", true));
		outFile2.println("The topKSetSize is: " + topKSetSize);
		outFile2.flush();

		// double alpha = 0.5;
		int iter = 1;

		// Set<String> prevRelTerms = topKRelScores.keySet();

		Set<String> resultSet = topKRelScores.keySet();

		String prevIterA = "";
		String prevIterR = "";
		int i = 0;
		int rolltimes = 1;
		while (true)
		{

			i = i + 1;
			Map<String, Double> simScoreMap = mUtil.findRelScore(dictionary, prevRelTerms);

			Map<String, Double> gScoreMap = mUtil.findGScore(relScoreMap, simScoreMap, alpha);

			Map<String, Double> sortedGScoreMap = sortByComparator(gScoreMap, DESC);
			// System.out.println(sortedGScoreMap);
			Set<String> nextRelTerms = mUtil.findTopKSet(sortedGScoreMap, topKSetSize).keySet();
			/*
			 * if (i % 100 == 0) { System.out.print("prevRelTerms: "); Iterator it = prevRelTerms.iterator(); while (it.hasNext()) { System.out.print(it.next() + " "); } System.out.println();
			 * System.out.print("nextRelTerms: "); it = nextRelTerms.iterator(); while (it.hasNext()) { System.out.print(it.next() + " "); } System.out.println("\n"); }
			 */
			// if sets are different
			if (MapUtility.isSetDifferent(nextRelTerms, prevRelTerms))
			{
				rolltimes++;
				Set diffSet = MapUtility.getDifferenceInIter(nextRelTerms, prevRelTerms);
				/*
				 * if (diffSet.size() == 0) { resultSet = mUtil.findTopKSet(sortedGScoreMap, resultItemNumber).keySet(); break; }
				 */
				String a = (String) diffSet.iterator().next();
				double scoreA = sortedGScoreMap.get(a);
				String r = (String) prevRelTerms.toArray()[prevRelTerms.size() - 1];
				double scoreR = topKRelScores.get(r);

				// if (prevIterA.equalsIgnoreCase(r) && prevIterR.equalsIgnoreCase(a)) {
				if (scoreR >= scoreA)
				{
					nextRelTerms = prevRelTerms;
					resultSet = mUtil.findTopKSet(sortedGScoreMap, resultItemNumber).keySet();
					break;
				}
				// }
				// prevRelTerms.remove(r);
				topKRelScores.remove(r);
				// Set<String> tempRelTerms = new HashSet<String>();
				// tempRelTerms.addAll(prevRelTerms);
				// tempRelTerms.add(a);
				// prevRelTerms = tempRelTerms;
				topKRelScores.put(a, scoreA);
				Map<String, Double> sortedprevRelScoreMap = sortByComparator(topKRelScores, DESC);
				prevRelTerms = sortedprevRelScoreMap.keySet();
				prevIterA = a;
				prevIterR = r;
			} else
			{
				resultSet = mUtil.findTopKSet(sortedGScoreMap, resultItemNumber).keySet();
				break;
			}

			iter++;

		}

		System.out.println("Result rolltimes is: " + rolltimes);
		outFile2.println("Result rolltimes is: " + rolltimes);
		outFile2.flush();

		PrintWriter outFile_rollingTimes = new PrintWriter(new FileWriter(currentPath + "rollingTimes.txt", true));
		outFile_rollingTimes.println(rolltimes);
		outFile_rollingTimes.flush();

		// System.out.println("\n\n");
		// System.out.println("Term:");
		// Iterator it = prevRelTerms.iterator();
		// while (it.hasNext()) {
		// System.out.println(it.next());
		// }

		return resultSet;
		// return prevRelTerms;
	}
}
