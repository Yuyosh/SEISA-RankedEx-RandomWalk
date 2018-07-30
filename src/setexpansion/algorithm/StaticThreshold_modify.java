package setexpansion.algorithm;

import static setexpansion.util.MapUtility.DESC;
import static setexpansion.util.MapUtility.sortByComparator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import setexpansion.util.MapUtility;

public class StaticThreshold_modify
{
	public static String currentPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_20_30\\";

	public static Set<String> getExpandedSet(Set<String> seeds, double alpha) throws IOException
	{
		int stage = 1;

		MapUtility mUtil = new MapUtility();

		if (stage == 1)
		{
			return null;
		}

		Set<String> dictionary = mUtil.termCountMap.keySet();
		Map<String, Double> relScoreMap = mUtil.findRelScore(dictionary, seeds);

		Map<String, Double> sortedMapDesc = sortByComparator(relScoreMap, DESC);
		// System.out.println("Sorted Rel Scores:");
		// mUtil.printMap(sortedMapDesc);
		//
		int thresHoldIndex = mUtil.OtsusThreshold(mUtil.getValuesFromMap(sortedMapDesc));

		/*
		 * if (thresHoldIndex > 20) { thresHoldIndex = 20; }
		 */

		double thresHold = mUtil.getValuesFromMap(sortedMapDesc).get(thresHoldIndex);
		// System.out.println("Threshold:" + thresHold);
		// thresHold = 0.5416;

		// 2016/08/25 add topK
		// Map<String, Double> topKRelScores = mUtil.findTopK(sortedMapDesc, thresHold);
		Map<String, Double> topKRelScores = mUtil.findTopK_number(sortedMapDesc, thresHold, 20);

		// System.out.println("Sorted Map: " + sortedMapDesc);
		// mUtil.printMap(topKRelScores);
		// System.out.println("Size of topkRelscore:" + topKRelScores.size());
		int topKSetSize = topKRelScores.size();
		System.out.println("The topKSetSize is:" + topKSetSize);// new

		PrintWriter outFile2 = new PrintWriter(new FileWriter(currentPath + "result.txt", true));
		outFile2.println("The topKSetSize is: " + topKSetSize);
		outFile2.flush();

		// double alpha = 0.5;
		int iter = 1;
		Set<String> prevRelTerms = topKRelScores.keySet();
		String prevIterA = "";
		String prevIterR = "";
		int i = 0;
		int rolltimes = 1;
		while (true)
		{
			// System.out.println("\n");
			// System.out.println("Iter:" + iter);
			i = i + 1;
			Map<String, Double> simScoreMap = mUtil.findRelScore(dictionary, prevRelTerms);
			// System.out.println("Sim Scores:");
			// mUtil.printMap(simScoreMap);

			Map<String, Double> gScoreMap = mUtil.findGScore(relScoreMap, simScoreMap, alpha);
			// System.out.println("g Scores:");
			// mUtil.printMap(gScoreMap);
			Map<String, Double> sortedGScoreMap = sortByComparator(gScoreMap, DESC);
			// System.out.println(sortedGScoreMap);
			Set<String> nextRelTerms = mUtil.findTopKSet(sortedGScoreMap, topKSetSize).keySet();
			if (i % 100 == 0)
			{
				System.out.print("prevRelTerms: ");
				Iterator it = prevRelTerms.iterator();
				while (it.hasNext())
				{
					System.out.print(it.next() + " ");
				}
				System.out.println();
				System.out.print("nextRelTerms: ");
				it = nextRelTerms.iterator();
				while (it.hasNext())
				{
					System.out.print(it.next() + " ");
				}
				System.out.println("\n");
			}

			if (MapUtility.isSetDifferent(nextRelTerms, prevRelTerms)) // if
																		// sets
																		// are
																		// different
			{
				rolltimes++;
				Set diffSet = MapUtility.getDifferenceInIter(nextRelTerms, prevRelTerms);
				String a = (String) diffSet.iterator().next();
				double scoreA = sortedGScoreMap.get(a);
				String r = (String) prevRelTerms.toArray()[prevRelTerms.size() - 1];
				double scoreR = topKRelScores.get(r);

				// if (prevIterA.equalsIgnoreCase(r) && prevIterR.equalsIgnoreCase(a)) {
				if (scoreR >= scoreA)
				{
					nextRelTerms = prevRelTerms;
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
				break;
			}

			iter++;

		}

		System.out.println("Result rolltimes is: " + rolltimes);
		outFile2.println("Result rolltimes is: " + rolltimes);
		outFile2.flush();

		// System.out.println("\n\n");
		// System.out.println("Term:");
		// Iterator it = prevRelTerms.iterator();
		// while (it.hasNext()) {
		// System.out.println(it.next());
		// }
		return prevRelTerms;
	}

}
