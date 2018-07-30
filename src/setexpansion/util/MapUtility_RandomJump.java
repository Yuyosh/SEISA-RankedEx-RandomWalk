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

public class MapUtility_RandomJump
{
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

	public static Set getSimilarity(Set<String> listI, Set<String> listJ)
	{
		return Sets.difference(listI, listJ);
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
