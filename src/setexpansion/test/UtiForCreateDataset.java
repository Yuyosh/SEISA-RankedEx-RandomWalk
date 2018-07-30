package setexpansion.test;

import static setexpansion.util.MapUtility.DESC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class UtiForCreateDataset
{
	public static void averageNPAndLP(String NPAndLPFile[], String outputPath)
	{
		try
		{
			for (int fileNumber = 0; fileNumber < NPAndLPFile.length; fileNumber++)
			{
				File filename_NPAndLP = new File(NPAndLPFile[fileNumber]);

				InputStreamReader reader_NPAndLP = new InputStreamReader(new FileInputStream(filename_NPAndLP));
				BufferedReader br_NPAndLP = new BufferedReader(reader_NPAndLP);

				FileWriter outFile_NPAndLP = new FileWriter(outputPath + "Feature_" + filename_NPAndLP.getName(), true);

				String line = "";
				int total = 0;

				int lineNumber = 0;
				while ((line = br_NPAndLP.readLine()) != null)
				{
					total = total + Integer.parseInt(line.trim());

					lineNumber++;
				}
				br_NPAndLP.close();

				outFile_NPAndLP.write("The total list is " + lineNumber + "\r\n");
				outFile_NPAndLP.write("The total NP or LP is " + total + "\r\n");
				outFile_NPAndLP.write("The average NP or LP is " + total / lineNumber + "\r\n");

				br_NPAndLP.close();
				outFile_NPAndLP.close();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void findLPAndNp_bat(String seedsfile[], String DataPath, String outputPath) throws IOException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int fileNumber = 0; fileNumber < seedsfile.length; fileNumber++)
		{
			File filename_seed = new File(seedsfile[fileNumber]);

			int groupcount = getDataLineSize(filename_seed.toString());

			System.out.println("A seeds groups  begin at: " + df.format(new Date()));
			System.out.println("The seeds groups  path is: " + seedsfile[fileNumber]);

			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed));
			BufferedReader br_seed = new BufferedReader(reader_seed);

			File filename_data = new File(DataPath);
			InputStreamReader reader_data = new InputStreamReader(new FileInputStream(filename_data));
			BufferedReader br_data = new BufferedReader(reader_data);

			PrintWriter outFile_LP = new PrintWriter(
					new FileWriter(outputPath + "LP_" + filename_seed.getName(), true));
			PrintWriter outFile_NP = new PrintWriter(
					new FileWriter(outputPath + "NP_" + filename_seed.getName(), true));

			int groupCount = groupcount;

			String seedsGroup[][] = new String[groupCount][];
			String line_seed = "";
			int lineNumber = 0;
			while ((line_seed = br_seed.readLine()) != null)
			{
				seedsGroup[lineNumber] = line_seed.split("\t");
				lineNumber++;
			}

			int count[][] = new int[groupCount][];
			for (int i = 0; i < groupCount; i++)
			{
				count[i] = new int[2];

				count[i][0] = 0;
				count[i][1] = 0;

			}

			String line_data = "";

			while ((line_data = br_data.readLine()) != null)
			{
				String values[] = line_data.split("\t\t");

				String terms = values[1];

				String[] sep = terms.split("\t");

				for (int i = 0; i < groupCount; i++)
				{

					int LP = 0;
					for (int j = 0; j < seedsGroup[i].length; j++)
					{
						for (int k = 0; k < sep.length; k++)
						{
							if (seedsGroup[i][j].equals(sep[k]))
							{
								LP++;
							}
						}

					}

					if (LP > 0)
					{
						count[i][1]++;
					}
					if (LP == seedsGroup[i].length)
					{
						count[i][0]++;
					}

				}
			}

			for (int i = 0; i < groupCount; i++)
			{
				outFile_LP.write(count[i][1] + "\r\n");

				outFile_NP.write(count[i][0] + "\r\n");
			}

			br_data.close();
			br_seed.close();

			outFile_LP.close();
			outFile_NP.close();
		}
	}

	public static void averageRollingTimes(String rollingTimesFile, String outputFile)
	{
		try
		{
			File filename = new File(rollingTimesFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";
			int total = 0;

			int lineNumber = 0;
			while ((line = br.readLine()) != null)
			{
				total = total + Integer.parseInt(line.trim());

				lineNumber++;
			}
			br.close();

			FileWriter writer = new FileWriter(outputFile, true);

			writer.write("The total list is " + lineNumber + "\r\n");
			writer.write("The total rolling times is " + total + "\r\n");
			writer.write("The average rolling times is " + total / lineNumber + "\r\n");

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void pickSeedsList(String seedsFile, String outputFile, int listSize)
	{
		int listNumber = getDataLineSize(seedsFile);
		String lists[] = new String[listNumber];
		int Times[] = new int[listNumber];

		for (int i = 0; i < listNumber; i++)
		{
			Times[i] = i;
		}

		try
		{
			File filename = new File(seedsFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			int lineNumber = 0;
			while ((line = br.readLine()) != null)
			{
				lists[lineNumber] = line;

				lineNumber++;
			}
			br.close();

			FileWriter writer = new FileWriter(outputFile, true);

			int number[] = randomList_Times(Times, listSize);

			for (int i = 0; i < number.length; i++)
			{
				writer.write(lists[number[i]] + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static int[] randomList_Times(int[] Times, int size)
	{
		int[] Item = new int[size];

		for (int i : Item)
		{
			i = -1;
		}

		Item[0] = random_Times(Times);
		for (int i = 1; i < size;)
		{
			int thistime = random_Times(Times);
			int duplicationFlag = 0;

			for (int j = 0; j < i; j++)
			{
				if (Item[j] == thistime)
				{
					duplicationFlag = 1;
				}
			}

			if (duplicationFlag == 0)
			{
				Item[i] = thistime;
				i++;
			}
		}

		return Item;
	}

	public static int[] randomList_accumulativeTimes(int[] accumulativeTimes, int size)
	{
		int[] Item = new int[size];

		for (int i = 0; i < size; i++)
		{
			Item[i] = -1;
		}

		Item[0] = random_accumulativeTimes(accumulativeTimes);
		for (int i = 1; i < size;)
		{
			int thistime = random_accumulativeTimes(accumulativeTimes);
			int duplicationFlag = 0;

			for (int j = 0; j < i; j++)
			{
				if (Item[j] == thistime)
				{
					duplicationFlag = 1;
				}
			}

			if (duplicationFlag == 0)
			{
				Item[i] = thistime;
				i++;
			}
		}

		return Item;
	}

	public static String[] readItem(String dataFile, int type)
	{
		int ItemNumber = getDataLineSize(dataFile);

		String[] Item = new String[ItemNumber];
		try
		{
			File filename = new File(dataFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			int lineNumber = 0;
			while ((line = br.readLine()) != null)
			{
				if (type == 1)
				{
					String[] sep = line.split("\t");

					Item[lineNumber] = sep[0];
				} else if (type == 2)
				{
					Item[lineNumber] = line.trim();
				}
				lineNumber++;
			}
			br.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return Item;
	}

	public static int[] readTimes(String dataFile)
	{
		int ItemNumber = getDataLineSize(dataFile);

		int[] Item = new int[ItemNumber];
		try
		{
			File filename = new File(dataFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			int lineNumber = 0;
			while ((line = br.readLine()) != null)
			{
				String[] sep = line.split("\t");

				Item[lineNumber] = Integer.parseInt(sep[1]);

				lineNumber++;
			}
			br.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return Item;
	}

	public static int[] accumulativeTimes(int[] times)
	{
		int[] Item = new int[times.length];

		try
		{
			int accumulative = 0;
			for (int i = 0; i < times.length; i++)
			{
				accumulative = accumulative + times[i];
				Item[i] = accumulative;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return Item;
	}

	public static int random_accumulativeTimes(int[] accumulativeTimes)
	{
		int max = accumulativeTimes[accumulativeTimes.length - 1];

		int endExclusive = max;
		int startInclusive = 1;

		org.apache.commons.lang3.RandomUtils it = new org.apache.commons.lang3.RandomUtils();

		int select = it.nextInt(startInclusive, endExclusive);

		if (select <= accumulativeTimes[0])
			return 0;

		for (int i = 1; i < accumulativeTimes.length; i++)
		{
			if ((accumulativeTimes[i - 1] < select) && (select <= accumulativeTimes[i]))
			{
				return i;
			}
		}

		return -1;
	}

	public static int random_Times(int[] accumulativeTimes)
	{

		int endExclusive = accumulativeTimes.length;
		int startInclusive = 0;

		org.apache.commons.lang3.RandomUtils it = new org.apache.commons.lang3.RandomUtils();

		int select = it.nextInt(startInclusive, endExclusive);

		if ((0 <= select) && (select < accumulativeTimes.length))
		{
			return select;
		}

		return -1;
	}

	public static void pickNoiseList(String dataFile, String outputFile_Item, String outputFile_ItemAndTimes,
			int listSize)
	{
		try
		{
			File filename = new File(dataFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			FileWriter writer_Item = new FileWriter(outputFile_Item, true);
			FileWriter writer_ItemAndTimes = new FileWriter(outputFile_ItemAndTimes, true);

			int lineNumber = 0;
			while (((line = br.readLine()) != null) && (lineNumber < listSize))
			{

				String[] sep = line.split("\t");

				writer_Item.write(sep[0] + "\r\n");

				int times = (int) Double.parseDouble(sep[1]);

				writer_ItemAndTimes.write(sep[0] + "\t" + times + "\r\n");

				lineNumber++;

			}
			br.close();

			writer_Item.close();

			writer_ItemAndTimes.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static int getDataLineSize(String dataPath)
	{
		int LineNumbers = 0;
		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			while ((line = br.readLine()) != null)
			{
				LineNumbers++;
			}
			br.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return LineNumbers;
	}

	public static void findConceptAppearTimes(String conceptFile, String dataFile, String outputPath)
	{
		int ItemNumber = getDataLineSize(conceptFile);
		try
		{
			File filename_conceptItem = new File(conceptFile);
			InputStreamReader reader_conceptItem = new InputStreamReader(new FileInputStream(filename_conceptItem),
					"UTF-8");
			BufferedReader br_conceptItem = new BufferedReader(reader_conceptItem);

			String[] Item = new String[ItemNumber];
			int[] Item_Number = new int[ItemNumber];

			int linenumber = 0;
			String line_seed = "";

			while ((line_seed = br_conceptItem.readLine()) != null)
			{
				Item[linenumber] = line_seed;
				Item_Number[linenumber] = 0;
				linenumber++;
			}
			br_conceptItem.close();

			File file_data = new File(dataFile);
			InputStreamReader reader_data = new InputStreamReader(new FileInputStream(file_data), "UTF-8");
			BufferedReader br_data = new BufferedReader(reader_data);

			String line_term = "";

			while ((line_term = br_data.readLine()) != null)
			{
				String[] total = line_term.split("\t\t");
				String[] sep = total[1].split("\t");

				for (int i = 0; i < Item.length; i++)
				{
					for (int j = 0; j < sep.length; j++)
					{
						if (Item[i].equals(sep[j]))
						{
							Item_Number[i]++;
						}
					}
				}
			}
			br_data.close();

			FileWriter writer = new FileWriter(outputPath, true);

			for (int i = 0; i < Item.length; i++)
			{
				writer.write(Item[i] + "\t" + Item_Number[i] + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void removeSeeds(String dataFile, String seedFile, String outputFile)
	{
		int seedSize = getDataLineSize(seedFile);
		try
		{
			File filename_seed = new File(seedFile);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed), "UTF-8");
			BufferedReader br_seed = new BufferedReader(reader_seed);

			String[] seeds = new String[seedSize];
			String line_seed = "";
			int Seednumber = 0;

			while ((line_seed = br_seed.readLine()) != null)
			{
				seeds[Seednumber] = line_seed;
				Seednumber++;
			}
			br_seed.close();

			File filename = new File(dataFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			FileWriter writer = new FileWriter(outputFile, true);

			while ((line = br.readLine()) != null)
			{
				String[] sep = line.split("\t");

				int flag = 0;

				for (int i = 0; i < Seednumber; i++)
				{
					if (sep[0].equals(seeds[i]))
					{
						flag = 1;
						break;
					}
				}

				if (flag == 0)
				{
					writer.write(line + "\r\n");
				}
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void pickSubdataset_withSeeds(String dataFile, String seedFile, String outputFile)
	{
		int seedSize = getDataLineSize(seedFile);
		try
		{
			File filename_seed = new File(seedFile);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed), "UTF-8");
			BufferedReader br_seed = new BufferedReader(reader_seed);

			String[] seeds = new String[seedSize];
			String line_seed = "";
			int Seednumber = 0;

			while ((line_seed = br_seed.readLine()) != null)
			{
				seeds[Seednumber] = line_seed;
				Seednumber++;
			}
			br_seed.close();

			File filename = new File(dataFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			FileWriter writer = new FileWriter(outputFile, true);

			while ((line = br.readLine()) != null)
			{
				String[] total = line.split("\t\t");
				String[] sep = total[1].split("\t");

				int flag = 0;
				for (String s : sep)
				{
					for (int i = 0; i < Seednumber; i++)
					{
						if (s.equals(seeds[i]))
						{
							flag = 1;
							break;
						}
					}
					if (flag == 1)
						break;
				}

				if (flag == 1)
				{
					writer.write(line + "\r\n");
				}
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void sortRankedItem(String dataPath, String outputFile)
	{
		UtilityForRankedSE Util = new UtilityForRankedSE();

		int lineNumber = getDataLineSize(dataPath);

		int items = 0;

		String ItemNO[] = new String[lineNumber];
		double ItemFre[] = new double[lineNumber];

		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			int linenumber = 0;
			int itemnumber = 0;

			while ((line = br.readLine()) != null)
			{
				String[] temp = line.split("\t");

				ItemNO[linenumber] = temp[0];
				ItemFre[linenumber] = Double.parseDouble(temp[1]);

				linenumber++;

				temp = null;
			}
			br.close();

			Map<String, Double> rankedList = new LinkedHashMap<String, Double>();
			for (int i = 0; i < lineNumber; i++)
			{
				rankedList.put(ItemNO[i], ItemFre[i]);
			}

			Map<String, Double> sortedMapDesc = Util.sortByComparator(rankedList, DESC);

			FileWriter writer = new FileWriter(outputFile, true);

			for (Iterator iter = sortedMapDesc.entrySet().iterator(); iter.hasNext();)
			{
				Map.Entry element = (Map.Entry) iter.next();
				Object strKey = element.getKey();
				Object strObj = element.getValue();

				writer.write(strKey + "\t" + strObj + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String[][] readDataset(String dataPath)
	{
		int lineNumber = getDataLineSize(dataPath);

		int items = 0;

		String whole[][] = new String[lineNumber][];

		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			BufferedReader br = new BufferedReader(reader);
			String line = "";

			int linenumber = 0;
			int itemnumber = 0;

			while ((line = br.readLine()) != null)
			{
				String[] temp = line.split("\t\t");
				String[] sep = temp[1].split("\t");

				whole[linenumber] = new String[sep.length];
				itemnumber = 0;

				items = items + sep.length;

				for (int i = 0; i < sep.length; i++)
				{
					whole[linenumber][itemnumber] = sep[i];
					itemnumber++;
				}

				linenumber++;
				temp = null;
				sep = null;
			}
			br.close();

			// System.out.println("List number is " + lineNumber);
			// System.out.println("Item number is " + items);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return whole;
	}
}
