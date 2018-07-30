package setexpansion.test;

import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class UtilityForRankedSE
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

	public static void writeSet(Set<String> result, String outputPath)
	{
		try
		{
			FileWriter writer = new FileWriter(outputPath, true);

			writer.write("*****\r\n");

			for (Iterator it = result.iterator(); it.hasNext();)
			{
				writer.write(it.next() + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
