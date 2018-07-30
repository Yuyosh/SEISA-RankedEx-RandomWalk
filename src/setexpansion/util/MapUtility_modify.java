package setexpansion.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.google.common.collect.Sets;

public class MapUtility_modify
{
	public static boolean ASC = true;
	public static boolean DESC = false;
	public static Map<String, Integer> termCountMap;
	public static Map<String, String> termFileNameMap;
	public static Map<String, Integer> termPairCountMap;
	public static String currentPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_20_30\\";

	public static int TermCount = 2044491;
	public static int TermPairCount = 31408533;
	public static int[] termCount;
	public static int[] termPairCount;
	public static char[][] term;
	public static char[][] termPair;

	{
		term = new char[TermCount][];
		termCount = new int[TermCount];
		File file = new File(currentPath + "smalltermCount.txt");
		try
		{
			int termNumber = 0;
			LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext())
			{
				String line = iterator.nextLine();
				String value[] = line.split("\t");
				term[termNumber] = value[0].toCharArray();
				termCount[termNumber] = Integer.parseInt(value[1]);
				termNumber++;
				line = null;
				value = null;
			}
			System.out.println("Finished loading the term count");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		termPair = new char[TermPairCount][];
		termPairCount = new int[TermPairCount];
		file = new File(currentPath + "smallTermPairCount.txt");
		try
		{
			int termPairNumber = 0;
			LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext())
			{
				String line = iterator.nextLine();
				String value[] = line.split("\t\t");
				termPair[termPairNumber] = value[0].toCharArray();
				termPairCount[termPairNumber] = Integer.parseInt(value[1]);
				termPairNumber++;
				line = null;
				value = null;
			}
			System.out.println("Finished loading the term pair count");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * termCountMap = new HashMap(); File file = new File(currentPath + "smalltermCount.txt"); try { LineIterator iterator = FileUtils.lineIterator(file, "UTF-8"); while (iterator.hasNext()) {
		 * String line = iterator.nextLine(); String value[] = line.split("\t"); termCountMap.put(value[0], Integer.parseInt(value[1])); line = null; value = null; }
		 * System.out.println("Finished loading the term count"); } catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); } termPairCountMap = new HashMap(); file = new
		 * File(currentPath + "smallTermPairCount.txt"); try { LineIterator iterator = FileUtils.lineIterator(file, "UTF-8"); while (iterator.hasNext()) { String line = iterator.nextLine(); String
		 * value[] = line.split("\t\t"); termPairCountMap.put(value[0], Integer.parseInt(value[1])); line = null; value = null; } System.out.println("Finished loading the term pair count"); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		//
		// termFileNameMap = new HashMap();
		// termFileNameMap.put("9â€? 9â€?/ Ret WBPX [ edit ] West Virginia", "index_file1_aa");
		// termFileNameMap.put("WBPX [ edit ] Washington Rio de Janeiro Sioux City", "index_file1_ab");
		// termFileNameMap.put("Rio de Janeiro Sinwu N of Argonia S of Beresford", "index_file1_ac");
		// termFileNameMap.put("N of Argonia S of Bath Lester Towns Ricky Andrews", "index_file1_ad");
		// termFileNameMap.put("Lester Towns Rick Sortun Lester Towns Ricky Andrews", "index_file1_ae");
		// termFileNameMap.put("JohnYoung Leftwich Henry James T. Snowball", "index_file1_af");
		// termFileNameMap.put("Henry James T. S. Eliot Friedrich Beckh Max J. Wright", "index_file1_ag");
		// termFileNameMap.put("Friedrich Beckh Max Ibel Dundee Moose Lake", "index_file1_ah");
		// termFileNameMap.put("Dundee Moorreesburg Crisp of Bungay Holden of The Firs", "index_file1_ai");
		// termFileNameMap.put("Crisp of Bungay Holcroft of Eaton Mascott Cashel Gloucestershire Western",
		// "index_file1_aj");
		// termFileNameMap.put("Cashel Gloucestershire Eastern Blind Jumbo Smiffy", "index_file1_ak");
		// termFileNameMap.put("Blind Jumbo Smart Alice B. Beinke D. Morgan", "index_file1_al");
		// termFileNameMap.put("B. Beinke D. Brown Akkimeobsi Juryeonda Dae Jang-hwa Hong-ryeonjeon", "index_file1_am");
		// termFileNameMap.put("Akkimeobsi Juryeonda Chunmong -1250 bil. â‚? -848.3 bil. $A", "index_file1_an");
		//
	}

	public static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
	{

		List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Double>>()
		{
			@Override
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2)
			{
				if (order)
				{
					return o1.getValue().compareTo(o2.getValue());
				} else
				{
					return o2.getValue().compareTo(o1.getValue());

				}
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Entry<String, Double> entry : list)
		{
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static void printMap(Map<String, Double> map)
	{
		for (Entry<String, Double> entry : map.entrySet())
		{
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
	}

	public double pairWiseMatrixLookUp(List<String> dictionary, double[][] pairWiseMatrix, String firstTerm,
			String secondTerm)
	{
		int row = dictionary.indexOf(firstTerm);
		int col = dictionary.indexOf(secondTerm);

		return pairWiseMatrix[row][col];
	}

	public Map<String, Double> findRelScore(Set<String> dictionary, Set<String> seeds)
	{
		Map<String, Double> relScoreMap = new LinkedHashMap<String, Double>();
		long startTime = System.currentTimeMillis();
		for (String dict : dictionary)
		{
			double relScore = 0.0;
			// System.out.println("pairWiseMatrix Entity:"+entry.getKey());
			for (String seed : seeds)
			{
				// System.out.println("Seed Entity:"+s);
				// relScore += this.pairWiseMatrixLookUp(dictionary, pairWiseMatrix, dict, seed);
				relScore += this.getSimilarityScore(dict, seed);
			}

			double finalRelScore = relScore / seeds.size();
			// System.out.println("For dict:" + dict + " relscore:" + finalRelScore);
			relScoreMap.put(dict, finalRelScore);
		}
		long endTime = System.currentTimeMillis();
		// System.out.println("Time to calculate the relavance" + (endTime - startTime));
		return relScoreMap;
	}

	public Map<String, Double> findTopK(Map<String, Double> relScoreMap, double thresHold)
	{
		Map<String, Double> topKElements = new LinkedHashMap<String, Double>();

		for (Map.Entry<String, Double> entry : relScoreMap.entrySet())
		{
			if (entry.getValue() >= thresHold)
			{
				topKElements.put(entry.getKey(), entry.getValue());
			}
		}

		return topKElements;
	}

	public Map<String, Double> findTopK_number(Map<String, Double> relScoreMap, double thresHold, int number)
	{
		Map<String, Double> topKElements = new LinkedHashMap<String, Double>();

		int itemnumber = 0;

		for (Map.Entry<String, Double> entry : relScoreMap.entrySet())
		{
			if ((entry.getValue() >= thresHold) && (itemnumber < number))
			{
				topKElements.put(entry.getKey(), entry.getValue());
				itemnumber++;
			}
		}

		return topKElements;
	}

	public List<String> keyValuesFromMap(Map<String, Double> relScoreMap)
	{
		List<String> keys = new ArrayList<String>();
		for (Map.Entry<String, Double> entry : relScoreMap.entrySet())
		{
			keys.add(entry.getKey());
		}
		return keys;
	}

	public Map<String, Double> findTopKSet(Map<String, Double> relScoreMap, int topKSetSize)
	{
		Map<String, Double> topKElements = new LinkedHashMap<String, Double>();
		int i = 0;
		for (Map.Entry<String, Double> entry : relScoreMap.entrySet())
		{
			if (i < topKSetSize)
			{
				topKElements.put(entry.getKey(), entry.getValue());
			}
			i++;
		}
		return topKElements;
	}

	public static Set getDifferenceInIter(Set<String> listI, Set<String> listJ)
	{
		return Sets.difference(listI, listJ);
	}

	public static boolean isSetDifferent(Set<String> listI, Set<String> listJ)
	{
		if (Sets.difference(listI, listJ).size() == 0)
		{
			if (Sets.difference(listJ, listI).size() == 0)
			{
				return false;
			}
		}
		return true;
	}

	public Map<String, Double> findGScore(Map<String, Double> relScoreMap, Map<String, Double> simScores, double alpha)
	{
		Map<String, Double> gScoreMap = new LinkedHashMap<String, Double>();
		for (Map.Entry<String, Double> entry : relScoreMap.entrySet())
		{
			double score = alpha * entry.getValue() + (1 - alpha) * simScores.get(entry.getKey());
			gScoreMap.put(entry.getKey(), score);
		}
		return gScoreMap;
	}

	public List<Double> getValuesFromMap(Map<String, Double> relScoreMap)
	{
		List<Double> keys = new ArrayList<Double>();
		for (Map.Entry<String, Double> entry : relScoreMap.entrySet())
		{
			keys.add(entry.getValue());
		}
		return keys;
	}

	public int OtsusThreshold(List<Double> data)
	{
		// Otsu's threshold algorithm
		// C++ code by Jordan Bevik <Jordan.Bevic@qtiworld.com>
		// ported to ImageJ plugin by G.Landini
		int k;
		int kStar; // k = the current threshold; kStar = optimal threshold
		double N1, N; // N1 = # points with intensity <=k; N = total number of
						// points
		double BCV, BCVmax; // The current Between Class Variance and maximum
							// BCV
		double num, denom; // temporary bookeeping
		double Sk; // The total intensity for all histogram points <=k
		double S, L = data.size(); // The total intensity of the image

		// Initialize values:
		S = N = 0;
		for (k = 0; k < L; k++)
		{
			S += k * data.get(k); // Total histogram intensity
			N += data.get(k); // Total number of data points
		}

		Sk = 0;
		N1 = data.get(0); // The entry for zero intensity
		BCV = 0;
		BCVmax = 0;
		kStar = 0;

		// Look at each possible threshold value,
		// calculate the between-class variance, and decide if it's a max
		for (k = 1; k < L - 1; k++)
		{ // No need to check endpoints k = 0 or k =
			// L-1
			Sk += k * data.get(k);
			N1 += data.get(k);

			// The float casting here is to avoid compiler warning about loss of
			// precision and
			// will prevent overflow in the case of large saturated images
			denom = (N1) * (N - N1); // Maximum value of denom is
										// (N^2)/4 = approx. 3E10

			if (denom != 0)
			{
				// Float here is to avoid loss of precision when dividing
				num = (N1 / N) * S - Sk; // Maximum value of num =
											// 255*N = approx 8E7
				BCV = (num * num) / denom;
			} else
				BCV = 0;

			if (BCV >= BCVmax)
			{ // Assign the best threshold found so far
				BCVmax = BCV;
				kStar = k;
			}
		}
		// kStar += 1; // Use QTI convention that intensity -> 1 if intensity >=
		// k
		// (the algorithm was developed for I-> 1 if I <= k.)
		return kStar;
	}

	static int MaxEntropy(List<Double> data)
	{
		// Implements Kapur-Sahoo-Wong (Maximum Entropy) thresholding method
		// Kapur J.N., Sahoo P.K., and Wong A.K.C. (1985) "A New Method for
		// Gray-Level Picture Thresholding Using the Entropy of the Histogram"
		// Graphical Models and Image Processing, 29(3): 273-285
		// M. Emre Celebi
		// 06.15.2007
		// Ported to ImageJ plugin by G.Landini from E Celebi's fourier_0.8 routines
		int threshold = -1;
		int ih, it;
		int first_bin;
		int last_bin;
		double tot_ent; /* total entropy */
		double max_ent; /* max entropy */
		double ent_back; /* entropy of the background pixels at a given threshold */
		double ent_obj; /* entropy of the object pixels at a given threshold */
		double[] norm_histo = new double[data.size()]; /* normalized histogram */
		double[] P1 = new double[data.size()]; /* cumulative normalized histogram */
		double[] P2 = new double[data.size()];

		double total = 0;
		for (ih = 0; ih < data.size(); ih++)
			total += data.get(ih);

		for (ih = 0; ih < data.size(); ih++)
			norm_histo[ih] = data.get(ih) / total;

		P1[0] = norm_histo[0];
		P2[0] = 1.0 - P1[0];
		for (ih = 1; ih < data.size(); ih++)
		{
			P1[ih] = P1[ih - 1] + norm_histo[ih];
			P2[ih] = 1.0 - P1[ih];
		}

		/* Determine the first non-zero bin */
		first_bin = 0;
		for (ih = 0; ih < data.size(); ih++)
		{
			if (!(Math.abs(P1[ih]) < 2.220446049250313E-16))
			{
				first_bin = ih;
				break;
			}
		}

		/* Determine the last non-zero bin */
		last_bin = data.size() - 1;
		for (ih = data.size() - 1; ih >= first_bin; ih--)
		{
			if (!(Math.abs(P2[ih]) < 2.220446049250313E-16))
			{
				last_bin = ih;
				break;
			}
		}

		// Calculate the total entropy each gray-level
		// and find the threshold that maximizes it
		max_ent = Double.MIN_VALUE;

		for (it = first_bin; it <= last_bin; it++)
		{
			/* Entropy of the background pixels */
			ent_back = 0.0;
			for (ih = 0; ih <= it; ih++)
			{
				if (data.get(ih) != 0)
				{
					ent_back -= (norm_histo[ih] / P1[it]) * Math.log(norm_histo[ih] / P1[it]);
				}
			}

			/* Entropy of the object pixels */
			ent_obj = 0.0;
			for (ih = it + 1; ih < data.size(); ih++)
			{
				if (data.get(ih) != 0)
				{
					ent_obj -= (norm_histo[ih] / P2[it]) * Math.log(norm_histo[ih] / P2[it]);
				}
			}

			/* Total entropy */
			tot_ent = ent_back + ent_obj;

			// IJ.log(""+max_ent+" "+tot_ent);
			if (max_ent < tot_ent)
			{
				max_ent = tot_ent;
				threshold = it;
			}
		}
		return threshold;
	}

	public static void main(String[] args)
	{
		List<Double> arr = new ArrayList();
		// arr.add(0.75);
		// arr.add(0.75);
		// arr.add(0.7);
		// arr.add(0.7);
		// arr.add(0.65);
		// arr.add(0.6);
		arr.add(0.333);
		arr.add(0.333);
		arr.add(0.333);
		arr.add(0.333);
		arr.add(0.25);
		arr.add(0.1);
		System.out.println(MaxEntropy(arr));
	}

	public static Set getSimilarity(Set<String> listI, Set<String> listJ)
	{
		return Sets.difference(listI, listJ);
	}

	public static String getFileAddress(String term1, String term2)
	{

		String fileIndex = "";
		for (Map.Entry<String, String> entry : termFileNameMap.entrySet())
		{
			String key = entry.getKey().toString();
			String[] splits = key.split("\t\t");
			String termToCompare = term1 + "\t" + term2;
			String revTermToCompare = term2 + "\t" + term1;
			if (splits[0].compareTo(termToCompare) >= 0 && splits[1].compareTo(termToCompare) <= 0)
			{
				fileIndex = entry.getValue().toString();
				return fileIndex;
			}
			if (splits[0].compareTo(revTermToCompare) >= 0 && splits[1].compareTo(revTermToCompare) <= 0)
			{
				fileIndex = entry.getValue().toString();
				return fileIndex;
			}
		}
		return fileIndex;
	}

	public static double getSimilarityScore(String term1, String term2)
	{

		Integer listCount1 = termCountMap.get(term1);
		Integer listCount2 = termCountMap.get(term2);
		String termToSearch = term1 + "\t" + term2;
		Integer intersection = null;
		if (termPairCountMap.containsKey(termToSearch))
		{
			intersection = termPairCountMap.get(termToSearch);
		} else
		{
			termToSearch = term2 + "\t" + term1;
			intersection = termPairCountMap.get(termToSearch);
		}
		if (intersection == null)
		{
			return 0;
		}
		// System.out.println("pairt term found..");
		double union = listCount1 + listCount2 - intersection;
		return intersection / union;
		// String indexDirName = getFileAddress(term1,term2);

		// SearchEngine se;
		// try {
		// se = new SearchEngine("index_small_term_pair");
		// TopDocs topDocs = se.performSearch(term1+term2, 1);
		// ScoreDoc[] hits = topDocs.scoreDocs;
		// if(hits.length ==0){
		// topDocs = se.performSearch(term2+term1, 1);
		// hits = topDocs.scoreDocs;
		// }
		// for (int i = 0; i < hits.length; i++) {
		// Document doc = se.getDocument(hits[i].doc);
		// //System.out.println(doc.get("term") + " " + doc.get("count"));
		// int intersection = Integer.parseInt(doc.get("count"));
		// double union = listCount1 +listCount2 - intersection;
		// return intersection/union;
		// }
		// try{
		// LineIterator iterator = FileUtils.lineIterator(new File("smallTermPairCount.txt"), "UTF-8");
		// while (iterator.hasNext()) {
		// String line = iterator.nextLine();
		// String termVal[] = line.split("\t\t");
		// String termPair[] = termVal[0].split("\t");
		// if((termPair[0].equals(term1) && termPair[1].equals(term2)) ||(termPair[1].equals(term1) &&
		// termPair[0].equals(term2))){
		// int intersection = Integer.parseInt(termVal[1]);
		// double union = listCount1 +listCount2 - intersection;
		// return intersection/union;
		//
		// }
		// }

		// System.out.println("performSearch done");
		// if((termPair[0].equals(term1) && termPair[1].equals(term2)) ||(termPair[1].equals(term1) &&
		// termPair[0].equals(term2))){
		// int interesection = Integer.parseInt(value[1]);
		// double sim = listCount1 +listCount2 - interesection;
		// return sim;
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();}
		//
		// return 0;
	}

	public static void readFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");
		long startTime = System.currentTimeMillis();
		System.out.println("Starting..");
		while (iterator.hasNext())
		{
			iterator.next();
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}
}
