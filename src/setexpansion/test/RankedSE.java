package setexpansion.test;

import static setexpansion.util.MapUtility.DESC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import setexpansion.experiment.SEISA;
import setexpansion.experiment.SyntheticData;

public class RankedSE
{
	public static void rankedSE_withSeedNumber(String dataFile, String workPath, String seedsPath, int topKSetSize,
			String SeedNumber) throws ParseException, IOException, FileNotFoundException
	{

		UtilityForRankedSE Util = new UtilityForRankedSE();

		String filename = dataFile.substring(dataFile.lastIndexOf("\\") + 1);
		filename = SyntheticData.getFileNameNoEx(filename) + SeedNumber;

		SyntheticData.createDir(workPath + filename);

		String outputPath = workPath + filename + "\\";

		BufferedWriter writer_log = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "log.txt"), "UTF-8"));

		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		writer_log.write("The program begin at: " + dfs.format(new Date()) + "\r\n");
		System.out.println("The program begin at: " + dfs.format(new Date()));

		String seedsfile[] = SEISA.getTempFiles_total(seedsPath);

		for (int i = 0; i < seedsfile.length; i++)
		{

			// String partFilename = seedsfile[i].substring(seedsfile[i].lastIndexOf("\\") + 1);

			BufferedWriter writer_resultlist = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "resultlist.txt"), "UTF-8"));

			BufferedWriter writer_runningtime = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "runningtime.txt"), "UTF-8"));

			prepairSubdataset_withSeeds(dataFile, seedsfile[i], outputPath);

			int seedNumber = UtiForCreateDataset.getDataLineSize(seedsfile[i]);

			for (int fileNumber = 0; fileNumber < seedNumber; fileNumber++)
			{

				java.util.Date begin = dfs.parse(dfs.format(new Date()));

				String sub_dataFile = outputPath + "subData\\subData" + fileNumber + ".txt";
				String seedAppearTimesFile = outputPath + "appearTimes\\appearTimes" + fileNumber + ".txt";

				String outputFile = outputPath + "result.txt";

				String Dataset[][];
				Dataset = readDataset(sub_dataFile);

				String SeedAppearTimes[][];
				SeedAppearTimes = readSeedTimes(seedAppearTimesFile);

				double rankedList[];
				rankedList = rankList(Dataset, SeedAppearTimes);

				String outputFile_rankedList = outputPath + "result_rankedList.txt";
				sortRankedList(sub_dataFile, rankedList, outputFile_rankedList);

				Map<String, Double> rankedItem = rankItem(Dataset, SeedAppearTimes, rankedList);

				Map<String, Double> sortedMapDesc = Util.sortByComparator(rankedItem, DESC);

				Set<String> result = Util.findTopKSet(sortedMapDesc, topKSetSize).keySet();

				Util.writeSet(result, outputFile);

				Util.writeSet(result, outputPath + "resultlist.txt");

				System.out.println("One seed  expansion end at: " + dfs.format(new Date()));// new

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;
				writer_log
						.write("The running time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");

				writer_runningtime.write(between + "\r\n");
			}
			writer_resultlist.flush();
			writer_resultlist.close();

			writer_runningtime.flush();
			writer_runningtime.close();
		}
		writer_log.flush();
		writer_log.close();

		System.out.println("stop");

	}

	public static void rankedSE(String dataFile, String workPath, String seedsPath, int topKSetSize)
			throws ParseException, IOException, FileNotFoundException
	{

		UtilityForRankedSE Util = new UtilityForRankedSE();

		String filename = dataFile.substring(dataFile.lastIndexOf("\\") + 1);
		filename = SyntheticData.getFileNameNoEx(filename);

		SyntheticData.createDir(workPath + filename);

		String outputPath = workPath + filename + "\\";

		BufferedWriter writer_log = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "log.txt"), "UTF-8"));

		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		writer_log.write("The program begin at: " + dfs.format(new Date()) + "\r\n");
		System.out.println("The program begin at: " + dfs.format(new Date()));

		String seedsfile[] = SEISA.getTempFiles_total(seedsPath);

		for (int i = 0; i < seedsfile.length; i++)
		{

			// String partFilename = seedsfile[i].substring(seedsfile[i].lastIndexOf("\\") + 1);

			BufferedWriter writer_resultlist = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "resultlist.txt"), "UTF-8"));

			BufferedWriter writer_runningtime = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "runningtime.txt"), "UTF-8"));

			prepairSubdataset_withSeeds(dataFile, seedsfile[i], outputPath);

			int seedNumber = UtiForCreateDataset.getDataLineSize(seedsfile[i]);

			for (int fileNumber = 0; fileNumber < seedNumber; fileNumber++)
			{

				java.util.Date begin = dfs.parse(dfs.format(new Date()));

				String sub_dataFile = outputPath + "subData\\subData" + fileNumber + ".txt";
				String seedAppearTimesFile = outputPath + "appearTimes\\appearTimes" + fileNumber + ".txt";

				String outputFile = outputPath + "result.txt";

				String Dataset[][];
				Dataset = readDataset(sub_dataFile);

				String SeedAppearTimes[][];
				SeedAppearTimes = readSeedTimes(seedAppearTimesFile);

				double rankedList[];
				rankedList = rankList(Dataset, SeedAppearTimes);

				String outputFile_rankedList = outputPath + "result_rankedList.txt";
				sortRankedList(sub_dataFile, rankedList, outputFile_rankedList);

				Map<String, Double> rankedItem = rankItem(Dataset, SeedAppearTimes, rankedList);

				Map<String, Double> sortedMapDesc = Util.sortByComparator(rankedItem, DESC);

				Set<String> result = Util.findTopKSet(sortedMapDesc, topKSetSize).keySet();

				Util.writeSet(result, outputFile);

				Util.writeSet(result, outputPath + "resultlist.txt");

				System.out.println("One seed  expansion end at: " + dfs.format(new Date()));// new

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;
				writer_log
						.write("The running time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");

				writer_runningtime.write(between + "\r\n");
			}
			writer_resultlist.flush();
			writer_resultlist.close();

			writer_runningtime.flush();
			writer_runningtime.close();
		}
		writer_log.flush();
		writer_log.close();

		System.out.println("stop");

	}

	public static void main(String[] args) throws ParseException
	{

		String processPart = "dircetily";

		if (processPart == "dircetily")
		{
			String dataFile = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";
			String filename = dataFile.substring(dataFile.lastIndexOf("\\") + 1);
			System.out.println(filename);
			String[] filenameparts = filename.split("\\.");
			filename = filenameparts[0];
			System.out.println(filename);

			String workPath = "D:\\CodePath\\dataset\\SampledWebList\\RandedExpansion\\SEISASeeds\\";
			String outputPath = workPath + filename + "\\";
			System.out.println(outputPath);

			return;
		}

		UtilityForRankedSE Util = new UtilityForRankedSE();
		int topKSetSize = 20;

		String workPath = "D:\\CodePath\\dataset\\SampledWebList\\RandedExpansion\\SEISASeeds\\";

		if (processPart == "prepairData")
		{
			String dataFile = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";

			String seedFile = "D:\\CodePath\\dataset\\SampledWebList\\Seeds_Rank\\SEISASeeds.txt";

			String outputPath = workPath;

			prepairSubdataset_withSeeds(dataFile, seedFile, outputPath);
		}

		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println("The program begin at: " + dfs.format(new Date()));// new

		for (int fileNumber = 0; fileNumber < 4; fileNumber++)
		{
			String dataFile = workPath + "subData\\subData" + fileNumber + ".txt";
			String seedAppearTimesFile = workPath + "appearTimes\\appearTimes" + fileNumber + ".txt";

			String outputFile = workPath + "result.txt";

			String Dataset[][];
			Dataset = readDataset(dataFile);

			String SeedAppearTimes[][];
			SeedAppearTimes = readSeedTimes(seedAppearTimesFile);

			double rankedList[];
			rankedList = rankList(Dataset, SeedAppearTimes);

			String outputFile_rankedList = workPath + "result_rankedList.txt";
			sortRankedList(dataFile, rankedList, outputFile_rankedList);

			Map<String, Double> rankedItem = rankItem(Dataset, SeedAppearTimes, rankedList);

			Map<String, Double> sortedMapDesc = Util.sortByComparator(rankedItem, DESC);

			Set<String> result = Util.findTopKSet(sortedMapDesc, topKSetSize).keySet();

			Util.writeSet(result, outputFile);

			System.out.println("One seed  expansion end at: " + dfs.format(new Date()));// new
		}

		System.out.println("stop");

	}

	public static Map<String, Double> rankItem(String[][] Dataset, String[][] SeedAppearTimes, double[] RankedList)
	{

		Map<String, Double> rankedItemMap = new LinkedHashMap<String, Double>();
		Set<String> Dict = new HashSet();

		for (int list = 0; list < Dataset.length; list++)
		{
			for (String oneString : Dataset[list])
			{
				Dict.add(oneString);
			}
		}

		for (Iterator it = Dict.iterator(); it.hasNext();)
		{
			double result = 0.0;

			String item = it.next().toString();

			for (int list = 0; list < Dataset.length; list++)
			{
				int appearFlag = 0;

				for (int listItem = 0; listItem < Dataset[list].length; listItem++)
				{
					if (item.equals(Dataset[list][listItem]))
					{
						appearFlag = 1;
						break;
					}
				}

				if (appearFlag == 1)
				{
					result = result + RankedList[list] / (Dataset[list].length);
				}
			}

			rankedItemMap.put(item, result);
		}

		return rankedItemMap;
	}

	public static double[] rankList(String[][] Dataset, String[][] SeedAppearTimes)
	{

		double RankedList[] = new double[Dataset.length];

		for (double i : RankedList)
		{
			i = 0.0;
		}

		for (int list = 0; list < Dataset.length; list++)
		{
			double tempResult = 1.0;

			for (int seed = 0; seed < SeedAppearTimes.length; seed++)
			{
				int findFlag = 0;

				for (int listItem = 0; listItem < Dataset[list].length; listItem++)
				{
					if (SeedAppearTimes[seed][0].equals(Dataset[list][listItem]))
					{
						findFlag = 1;
						break;
					}
				}
				if (findFlag == 1)
				{
					tempResult = tempResult / (Integer.parseInt(SeedAppearTimes[seed][1]));
				} else
				{
					tempResult = tempResult * (1 - (1 / (Integer.parseInt(SeedAppearTimes[seed][1]))));
				}
			}
			RankedList[list] = tempResult;
		}

		return RankedList;
	}

	public static void prepairSubdataset_withSeeds(String dataFile, String seedFile, String outputPath)
	{
		try
		{
			File filename_seed = new File(seedFile);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed));
			BufferedReader br_seed = new BufferedReader(reader_seed);

			String line_seed = "";
			int subNumber = 0;

			while ((line_seed = br_seed.readLine()) != null)
			{
				String[] seeds = line_seed.split("\t");

				/*
				 * File file1 = new File(outputPath + "subSeed"); if (file1.exists()) { deleteDir(file1); }
				 * 
				 * File file2 = new File(outputPath + "subData"); if (file2.exists()) { deleteDir(file2); }
				 */

				SyntheticData.createDir(outputPath + "subSeed");

				String outputFile_seed = outputPath + "subSeed\\subSeed" + subNumber + ".txt";

				FileWriter writer_seed = new FileWriter(outputFile_seed, true);

				writer_seed.write(line_seed);
				writer_seed.close();

				File filename = new File(dataFile);
				InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
				BufferedReader br = new BufferedReader(reader);

				String line = "";

				SyntheticData.createDir(outputPath + "subData");
				String outputFile_subdata = outputPath + "subData\\subData" + subNumber + ".txt";

				FileWriter writer = new FileWriter(outputFile_subdata, true);

				while ((line = br.readLine()) != null)
				{
					String[] total = line.split("\t\t");
					String[] sep = total[1].split("\t");

					int flag = 0;
					for (String s : sep)
					{
						for (int i = 0; i < seeds.length; i++)
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

				/*
				 * File file3 = new File(outputPath + "appearTimes"); if (file3.exists()) { deleteDir(file3); }
				 */

				SyntheticData.createDir(outputPath + "appearTimes");
				String outputFile_appearTimes = outputPath + "appearTimes\\appearTimes" + subNumber + ".txt";
				SeedsAppearTimes(outputFile_seed, outputFile_subdata, outputFile_appearTimes);

				subNumber++;
			}
			br_seed.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 删除空目录
	 * 
	 * @param dir 将要删除的目录路径
	 */
	public static void doDeleteEmptyDir(String dir)
	{
		boolean success = (new File(dir)).delete();
		if (success)
		{
			System.out.println("Successfully deleted empty directory: " + dir);
		} else
		{
			System.out.println("Failed to delete empty directory: " + dir);
		}
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir 将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a deletion fails, the method stops attempting to delete and returns "false".
	 */
	public static boolean deleteDir(File dir)
	{
		if (dir.isDirectory())
		{
			String[] children = dir.list();

			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public static void SeedsAppearTimes(String seedsFile, String subDataFile, String outputPath)
	{
		try
		{
			File filename_seed = new File(seedsFile);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed)); // 建立一个输入流对象reader
			BufferedReader br_seed = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			int linenumber = 0;
			String line_seed = "";
			String line_term = "";

			FileWriter writer = new FileWriter(outputPath, true);

			while ((line_seed = br_seed.readLine()) != null)
			{
				String[] sep_seed = line_seed.split("\t");

				int[] seed_number = new int[sep_seed.length];
				for (int i = 0; i < seed_number.length; i++)
				{
					seed_number[i] = 0;
				}

				File file_subData = new File(subDataFile);
				InputStreamReader reader_subData = new InputStreamReader(new FileInputStream(file_subData)); // 建立一个输入流对象reader
				BufferedReader br_subData = new BufferedReader(reader_subData); // 建立一个对象，它把文件内容转成计算机能读懂的语言

				int findFlag = 0;
				while ((line_term = br_subData.readLine()) != null)
				{
					String[] total = line_term.split("\t\t");
					String[] sep = total[1].split("\t");

					for (int i = 0; i < sep_seed.length; i++)
					{
						for (int j = 0; j < sep.length; j++)
						{
							if (sep_seed[i].equals(sep[j]))
							{
								seed_number[i]++;
							}
						}
					}
				}

				for (int i = 0; i < sep_seed.length; i++)
				{
					writer.write(sep_seed[i] + "\t" + seed_number[i] + "\r\n");
				}
				br_subData.close();
			}

			br_seed.close();

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
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

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

	public static String[][] readSeedTimes(String seedAppearTimesFile)
	{
		int seedLineNumber = getDataLineSize(seedAppearTimesFile);

		String seed[][] = new String[seedLineNumber][];

		try
		{
			File filename = new File(seedAppearTimesFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			int linenumber = 0;
			int itemnumber = 0;

			while ((line = br.readLine()) != null)
			{
				String[] sep = line.split("\t");

				seed[linenumber] = new String[sep.length];
				itemnumber = 0;

				for (int i = 0; i < sep.length; i++)
				{
					seed[linenumber][itemnumber] = sep[i];
					itemnumber++;
				}

				linenumber++;
				sep = null;
			}
			br.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return seed;
	}

	public static int getDataLineSize(String dataPath)
	{
		int LineNumbers = 0;
		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
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

	public static void sortRankedList(String dataPath, double[] RankedList, String outputFile)
	{
		UtilityForRankedSE Util = new UtilityForRankedSE();

		int lineNumber = getDataLineSize(dataPath);

		int items = 0;

		String ListNO[] = new String[lineNumber];

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
				String[] temp = line.split("\t\t");

				ListNO[linenumber] = temp[0];

				linenumber++;

				temp = null;
			}
			br.close();

			Map<String, Double> rankedList = new LinkedHashMap<String, Double>();
			for (int i = 0; i < lineNumber; i++)
			{
				rankedList.put(ListNO[i], RankedList[i]);
			}

			Map<String, Double> sortedMapDesc = Util.sortByComparator(rankedList, DESC);

			FileWriter writer = new FileWriter(outputFile, true);

			writer.write("**************************************************\r\n");

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

}
