package setexpansion.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import setexpansion.util.Tuple;

public class AdjustDataset
{
	/*
	 * For a new concept set, do the following steps: 1.pick subset use the pickSubdataset_withSeeds function; 2.remove the items which length larger than 100; 3. merge the set and the added set.
	 */

	public static void main(String[] args) throws IOException
	{
		String stage = "pickSubdataset_withRandom";

		if (stage.equals("pickSubdataset_withRandom"))
		{
			String dataPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated_tenPercents.txt";
			pickSubdataset_withRandom(dataPath, outputPath, 0.1);
			return;
		}

		if (stage.equals("twoWaysMergeSort"))
		{
			String dataPath = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_canadaUniversitiesConcept\\SampledWebList_Formated_10_15.txt";
			String addPath = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_canadaUniversitiesConcept\\SampledWebList_canadaUniversitiesConcept.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_canadaUniversitiesConcept\\SampledWebList_Formated_10_15_canadaUniversitiesConcept.txt";

			twoWaysMergeSort(dataPath, addPath, outputPath);
			return;
		}

		if (stage.equals("addSubdataset"))
		{
			String dataPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_moneySeeds\\SampledWebList_20_28.txt";
			String addPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_moneySeeds\\SampledWebList_moneySeeds.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_moneySeeds\\SampledWebList_20_28_added.txt";
			addSubdataset(dataPath, addPath, outputPath);
			return;
		}

		if (stage.equals("pickSubdataset_withSeeds"))
		{
			String dataPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";
			String seedPath = "D:\\CodePath\\dataset\\SampledWebList\\Concept Set\\videoGameConsolesConcept.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_videoGameConsolesConcept.txt";
			int seedsSize = 1000;

			pickSubdataset_withSeeds(dataPath, seedPath, outputPath, seedsSize);
			return;
		}

		if (stage.equals("pickItem"))
		{
			String inputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_20_28\\smalltermCount.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_20_28\\smalltermCount_classof.txt";
			String startWord = "class of";

			pickItem(inputPath, outputPath, startWord);
			return;
		}

		if (stage.equals("ChangeItemOrder_forWordDistribution_Sort"))
		{
			String inputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_29_40\\smalltermCount_wordDistribution.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_29_40\\smalltermCount_wordDistribution_sorted.txt";
			String SplitString = "\t";

			Tuple.sort_withSplitString(inputPath, outputPath, SplitString);
			return;
		}

		if (stage.equals("ChangeItemOrder_forWordDistribution"))
		{
			String inputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_29_40\\smalltermCount.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_29_40\\smalltermCount_wordDistribution.txt";
			String SplitString = "\t";

			ChangeItemOrder_forWordDistribution(inputPath, outputPath, SplitString);
			return;
		}

		if (stage.equals("ChangeItemOrder"))
		{
			String inputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_ChangeItemOrder.txt";

			ChangeItemOrder(inputPath, outputPath);
			return;
		}

		if (stage.equals("MergeItem"))
		{
			String inputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_ChangeItemOrder_Sorted.txt";
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";

			MergeItem(inputPath, outputPath);
			return;
		}

		if (stage.equals("pickSubdataset"))
		{
			String dataPath = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_canadaUniversitiesConcept\\SampledWebList_canadaUniversitiesConcept.txt";
			String featurePath = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_canadaUniversitiesConcept\\SampledWebList_canadaUniversitiesConcept_100.txt";
			pickSubdataset(dataPath, featurePath, 1, 200);
			return;
		}

		if (stage.equals("getMonthSeeds"))
		{
			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\Concept Set\\monthYearSeeds.txt";

			getMonthSeeds(outputPath);
			return;
		}
	}

	public static void pickSubdataset_withRandom(String dataPath, String outputPath, Double percent)
	{
		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8");
			BufferedReader br = new BufferedReader(reader);

			int linenumber = 0;
			String line = "";

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));

			// FileWriter writer = new FileWriter(outputPath, true);

			line = br.readLine();

			while ((line = br.readLine()) != null)
			{

				Double current = Math.random();
				if (current < percent)
				{
					writer.write(line + "\r\n");
					linenumber++;
				}
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void getMonthSeeds(String outputPath)
	{
		try
		{

			FileWriter writer = new FileWriter(outputPath, true);

			String month[] = { "january ", "february ", "march ", "april ", "may ", "june ", "july ", "august ",
					"september ", "october ", "november ", "december " };

			String year[] = { "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010",
					"2011" };
			for (int i = 0; i < year.length; i++)
			{
				for (int j = 0; j < month.length; j++)
				{
					writer.write(month[j] + year[i] + "\r\n");
				}
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void addSubdataset(String dataPath, String addPath, String outputPath)
	{
		try
		{
			File filename_add = new File(addPath);
			InputStreamReader reader_add = new InputStreamReader(new FileInputStream(filename_add)); // 建立一个输入流对象reader
			BufferedReader br_add = new BufferedReader(reader_add); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String[] addLine = new String[10000];
			String[] LineNumber = new String[10000];
			int[] LineFlag = new int[10000];
			String line_add = "";
			int addnumber = 0;

			while ((line_add = br_add.readLine()) != null)
			{
				addLine[addnumber] = line_add;
				String[] addTotal = line_add.split("\t\t");

				LineNumber[addnumber] = addTotal[0];
				LineFlag[addnumber] = 0;
				addnumber++;
			}
			br_add.close();

			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			while ((line = br.readLine()) != null)
			{
				String[] total = line.split("\t\t");

				for (int i = 0; i <= addnumber; i++)
				{
					if (LineNumber[i].equals(total[0]))
					{
						LineFlag[i] = 1;
						break;
					}
				}
			}
			br.close();

			FileWriter writer = new FileWriter(outputPath, true);

			for (int i = 0; i <= addnumber; i++)
			{
				if (LineFlag[i] == 0)
				{
					writer.write(addLine[i] + "\r\n");
				}
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void twoWaysMergeSort(String input1, String input2, String outPath) throws IOException
	{
		String path = outPath;

		BufferedReader br1 = new BufferedReader(new FileReader(input1));
		BufferedReader br2 = new BufferedReader(new FileReader(input2));
		BufferedWriter writer = new BufferedWriter(new FileWriter(outPath));
		String s1 = br1.readLine();
		String s2 = br2.readLine();
		while (s1 != null || s2 != null)
		{
			if (s1 != null && s2 != null)
			{
				// 都不为空 才有比较的必要
				int mergeResult = s1.compareTo(s2);
				if (mergeResult > 0)
				{// s1在s2后面
					writer.write(s2);
					writer.write("\r\n");
					// writer.write(System.getProperty("line.separator"));
					s2 = br2.readLine();
				}
				if (mergeResult == 0)
				{// s1=s2
					writer.write(s1);
					writer.write("\r\n");
					// writer.write(System.getProperty("line.separator"));
					s1 = br1.readLine();
					s2 = br2.readLine();
				}
				if (mergeResult < 0)
				{// s1在s2前面
					writer.write(s1);
					writer.write("\r\n");
					// writer.write(System.getProperty("line.separator"));
					s1 = br1.readLine();
				}
			}
			if (s1 == null && s2 != null)
			{
				writer.write(s2);
				writer.write("\r\n");
				// writer.write(System.getProperty("line.separator"));
				s2 = br2.readLine();
			}
			if (s2 == null && s1 != null)
			{
				writer.write(s1);
				writer.write("\r\n");
				// writer.write(System.getProperty("line.separator"));
				s1 = br1.readLine();
			}
		}
		br1.close();
		br2.close();
		writer.close();
	}

	public static void pickSubdataset_withSeeds(String dataPath, String seedPath, String outputPath, int seedSize)
	{
		try
		{
			File filename_seed = new File(seedPath);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed));
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

			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			FileWriter writer = new FileWriter(outputPath, true);

			while ((line = br.readLine()) != null)
			{
				String[] total = line.split("\t\t");
				String[] sep = total[1].split("\t");

				int flag = 0;
				for (String s : sep)
				{
					for (int i = 0; i <= Seednumber; i++)
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

	public static void pickItem(String dataPath, String outputPath, String startWord)
	{
		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			int linenumber = 0;
			String line = "";

			FileWriter writer = new FileWriter(outputPath, true);

			line = br.readLine();

			while (line != null)
			{
				String[] sep = line.split("\t");

				if (sep[0].startsWith(startWord))
				// if ((sep[0].startsWith(startWord)) && (line.length() == 11))
				{
					writer.write(sep[0] + "\r\n");
					linenumber++;
				}

				line = br.readLine(); // 一次读入一行数据
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void ChangeItemOrder_forWordDistribution(String inputPath, String outputPath, String SplitString)
	{
		try
		{
			File filename_Input = new File(inputPath);
			InputStreamReader reader_Input = new InputStreamReader(new FileInputStream(filename_Input)); // 建立一个输入流对象reader
			BufferedReader br_Input = new BufferedReader(reader_Input); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			FileWriter writer = new FileWriter(outputPath, true);

			String readline = "";

			while ((readline = br_Input.readLine()) != null)
			{
				String[] sep = readline.split(SplitString);

				writer.write(sep[1] + SplitString + sep[0] + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void pickSubdataset(String dataPath, String outputPath, int lowLimit, int highLimit)
	{
		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			int linenumber = 0;
			String line = "";

			FileWriter writer = new FileWriter(outputPath, true);

			line = br.readLine();

			while (line != null)
			{
				String[] temp = line.split("\t\t");
				String[] sep = temp[1].split("\t");
				temp = null;

				if ((sep.length >= lowLimit) && (sep.length <= highLimit))
				{
					writer.write(line + "\r\n");
					linenumber++;
				}

				line = br.readLine(); // 一次读入一行数据
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void MergeItem(String inputPath, String outputPath)
	{
		try
		{
			File filename_Input = new File(inputPath);
			InputStreamReader reader_Input = new InputStreamReader(new FileInputStream(filename_Input)); // 建立一个输入流对象reader
			BufferedReader br_Input = new BufferedReader(reader_Input); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			FileWriter writer = new FileWriter(outputPath, true);

			String readline = "";

			String currentListID = "";

			String WirteLine = "";

			while ((readline = br_Input.readLine()) != null)
			{
				String[] sep = readline.split("\t\t");

				if (currentListID.equals(sep[0]))
				{
					WirteLine = WirteLine + "\t" + sep[1];
				} else
				{
					if (currentListID.equals(""))
					{
						currentListID = sep[0];
						WirteLine = sep[0] + "\t\t" + sep[1];
					} else
					{
						writer.write(WirteLine + "\r\n");
						currentListID = sep[0];
						WirteLine = sep[0] + "\t\t" + sep[1];
					}
				}
			}
			writer.write(WirteLine + "\r\n");

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void ChangeItemOrder(String inputPath, String outputPath)
	{
		try
		{
			File filename_Input = new File(inputPath);
			InputStreamReader reader_Input = new InputStreamReader(new FileInputStream(filename_Input)); // 建立一个输入流对象reader
			BufferedReader br_Input = new BufferedReader(reader_Input); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			FileWriter writer = new FileWriter(outputPath, true);

			String readline = "";

			while ((readline = br_Input.readLine()) != null)
			{
				String[] sep = readline.split("\t");

				writer.write(sep[1] + "\t\t" + sep[0] + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
