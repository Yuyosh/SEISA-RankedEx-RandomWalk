package setexpansion.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import setexpansion.algorithm.StaticThreshold;
import setexpansion.algorithm.StaticThresholdRedis;
import setexpansion.algorithm.SumOfPairs;
import setexpansion.algorithm.SumOfTerms;
import setexpansion.util.DataReader;
import setexpansion.util.Tuple;

public class StaticSeisaRedis
{

	// private static final String ORIGINALPATH = "D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\smalltermPair.txt";
	private static final String ORIGINALPATH = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\smallterm_list.txt";
	private static final int BIGDATALENGTH = 0;
	private static final int TEMPFILELENGTH = 8500000;
	private static final String TEMPFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\smallterm_list\\Source\\";
	private static final String SORTEDFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\smallterm_list\\Sorted\\";
	// private static final String SEEDFILEPATH = "D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\Seeds\\";
	private static final String SEEDFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\Seeds\\";
	private static int NUM_OF_TEST_PER_FILE = 10;
	private static int TERM_LENGTH_TO_INCLUDE = 6;
	private static double[] alphaVal = new double[10];
	public static ArrayList<String> fileNames;

	public static void main(String[] args) throws IOException, ParseException
	{
		prepairData();
	}

	public static void prepairData() throws IOException, ParseException
	{

		System.out.println("***********Starting for file: 29-40 ************************");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		System.out.println("The program of generated Sort begin at: " + df.format(new Date()));// new

		int flag = 1124;
		String DataPath = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\";

		if (flag == 1)
		{
			DataReader dr = new DataReader();

			dr.generatedSortedFile_Path(DataPath + "SampledWebList_Formated - Copy.txt", DataPath);

			System.out.println("The program of generated Sort end at: " + df.format(new Date()));// new

			Tuple.sort(DataPath + "smallterm.txt", DataPath + "smallTermSorted.txt");
			System.out.println("The program of smallterm Sort end at: " + df.format(new Date()));// new

			SumOfTerms.sumOfTerms(DataPath + "smallTermSorted.txt", DataPath + "smalltermCount.txt");
			System.out.println("The program of sumOfTerms end at: " + df.format(new Date()));// new

			return;
		}

		if (flag == 2)
		{
			Tuple.sort(DataPath + "smalltermPair.txt", DataPath + "smallTermPairSorted.txt");
			System.out.println("The program of smalltermPair Sort end at: " + df.format(new Date()));// new

			return;
		}

		if (flag == 24)
		{
			Tuple.sort(DataPath + "smalltermPair.txt", DataPath + "smallTermPairSorted.txt");
			System.out.println("The program of smalltermPair Sort end at: " + df.format(new Date()));// new

			SumOfTerms.sumOfTerms(DataPath + "smallTermSorted.txt", DataPath + "smalltermCount.txt");
			System.out.println("The program of sumOfTerms end at: " + df.format(new Date()));// new

			SumOfPairs.sumOfPairs(DataPath + "smallTermPairSorted.txt", DataPath + "smallTermPairCount.txt");
			System.out.println("The program of sumOfPairs end at: " + df.format(new Date()));// new

			return;
		}

		if (flag == 1124)
		{

			SumOfTerms.sumOfTerms_list(DataPath + "smallterm_list_sorted.txt",
					DataPath + "smallterm_list_sorted_Count.txt");
			System.out.println("The program of sumOfTerms end at: " + df.format(new Date()));// new

			return;
		}

		if (flag == 3)
		{

			splitData(ORIGINALPATH);
			System.out.println("The program of split end at: " + df.format(new Date()));// new

			multiFileSort();
			System.out.println("The program of sort end at: " + df.format(new Date()));// new

			String files[] = getTempFiles_total(SORTEDFILEPATH);
			multiWaysMergeSort(files, 1, SORTEDFILEPATH);

			System.out.println("The program of smalltermPair Sort end at: " + df.format(new Date()));// new

			return;
		}

		if (flag == 4)
		{
			SumOfPairs.sumOfPairs(DataPath + "smallTermPairSorted.txt", DataPath + "smallTermPairCount.txt");
			System.out.println("The program of sumOfPairs end at: " + df.format(new Date()));// new

			return;
		}

		if (flag == 5)
		{
			DataReader dr = new DataReader();

			dr.generatedSortedFile_Path(DataPath + "country-5000-30-0.8.txt", DataPath);

			System.out.println("The program of generated Sort end at: " + df.format(new Date()));// new

			Tuple.sort(DataPath + "smallterm.txt", DataPath + "smallTermSorted.txt");
			System.out.println("The program of smallterm Sort end at: " + df.format(new Date()));// new

			Tuple.sort(DataPath + "smalltermPair.txt", DataPath + "smallTermPairSorted.txt");
			System.out.println("The program of smalltermPair Sort end at: " + df.format(new Date()));// new

			SumOfTerms.sumOfTerms(DataPath + "smallTermSorted.txt", DataPath + "smalltermCount.txt");
			System.out.println("The program of sumOfTerms end at: " + df.format(new Date()));// new

			SumOfPairs.sumOfPairs(DataPath + "smallTermPairSorted.txt", DataPath + "smallTermPairCount.txt");
			System.out.println("The program of sumOfPairs end at: " + df.format(new Date()));// new
		}

		String seedsfile[] = getTempFiles_total(SEEDFILEPATH);

		for (int i = 0; i < seedsfile.length; i++)
		{
			PrintWriter outFile2 = new PrintWriter(new FileWriter(DataPath + "result.txt", true));

			outFile2.println("One first seed  expansion begin at: " + df.format(new Date()));

			// File filename_seed = new File("D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\AnimalSeeds.txt");
			File filename_seed = new File(seedsfile[i]);

			outFile2.println("A seeds groups  begin at: " + df.format(new Date()));
			outFile2.println("The seeds groups  path is: " + seedsfile[i]);

			System.out.println("A seeds groups  begin at: " + df.format(new Date()));
			System.out.println("The seeds groups  path is: " + seedsfile[i]);

			outFile2.println("One first seed  expansion begin at: " + df.format(new Date()));

			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed)); // 建立一个输入流对象reader
			BufferedReader br_seed = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_seed = "";
			int lineNumber = 0;
			while ((line_seed = br_seed.readLine()) != null)
			{
				SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				java.util.Date begin = dfs.parse(dfs.format(new Date()));

				Set<String> seeds = new HashSet();

				String seednumber[] = line_seed.split("\t");
				for (String seeditem : seednumber)
				{
					seeds.add(seeditem);
				}

				outFile2.println("the seeds is: ");
				for (String seed : seeds)
				{
					outFile2.println(seed);
				}

				Set<String> expandedSet = StaticThresholdRedis.getExpandedSet(seeds, 0.5);

				/*
				 * Set<String> expandedSet = StaticThreshold_modify.getExpandedSet(seeds, 0.5); int stage = 1;
				 * 
				 * if (stage == 1) { return; }
				 */

				Iterator iter1 = expandedSet.iterator();

				outFile2.println("Result retrieved is: ");

				while (iter1.hasNext())
				{
					outFile2.println(iter1.next());
				}

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;
				outFile2.println("The runling time of this roll is  " + second + "." + Msecond + "  millsecond");

				outFile2.println("One seed  expansion end at: " + df.format(new Date()));

				outFile2.flush();

				System.out.println("One seed  expansion end at: " + df.format(new Date()));// new

			}

		}

		System.out.println("The program end at: " + df.format(new Date()));// new
		System.out.println("###############Ending for file: ################");
	}

	public static void SanMethod(String[] args) throws IOException
	{
		double alpha = 0.1;
		for (int i = 0; i < 9; i++)
		{
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
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].getName().startsWith("data_"))
			{
				fileNames.add(listOfFiles[i].getName());
			}
		}
		String algoType = "static-";
		String splitdataset = algoType + "splitdataset";
		String result = algoType + "result";
		String avgResult = algoType + "avgResult";
		File avgResultFolder = new File(avgResult);
		File datasetFolder = new File(splitdataset);
		File resultFolder = new File(result);
		if (datasetFolder.exists())
		{
			datasetFolder.delete();
		}
		if (resultFolder.exists())
		{
			resultFolder.delete();
		}
		if (avgResultFolder.exists())
		{
			avgResultFolder.delete();
		}
		datasetFolder.mkdir();
		resultFolder.mkdir();
		avgResultFolder.mkdir();

		for (int i = 0; i < fileNames.size(); i++)
		{
			// Map<String, ArrayList<String>> totaldata = new HashMap();
			System.out.println("***********Starting for file:" + fileNames.get(i) + " ************************");
			Map<String, ArrayList<String>> removedData = new HashMap();
			File file = new File(splitFolder + "/" + fileNames.get(i));
			LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

			String outFileName = "./" + splitdataset + "/output-" + fileNames.get(i) + ".txt";
			File file1 = new File(outFileName);
			if (file1.exists())
			{
				file1.delete();
			}
			PrintWriter outFile1 = new PrintWriter(new FileWriter(outFileName, true));
			int lineFoundToRemove = 0;
			while (iterator.hasNext())
			{
				String line = iterator.nextLine();
				String[] listTermPair = line.split("\t\t");
				// System.out.println(listTermPair[0]);
				// if(listTermPair.length == 1){
				// System.out.println("hi");
				// }
				String[] terms = listTermPair[1].split("\t");
				if (terms.length >= TERM_LENGTH_TO_INCLUDE)
				{
					if (lineFoundToRemove != NUM_OF_TEST_PER_FILE)
					{
						ArrayList<String> termList = new ArrayList();
						for (int k = 0; k < terms.length; k++)
						{
							termList.add(terms[k]);
						}
						removedData.put(listTermPair[0], termList);
						lineFoundToRemove++;
					} else
					{
						outFile1.println(line);
						outFile1.flush();
					}
				} else
				{
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

			for (int seedIndex = 0; seedIndex < seedList.size(); seedIndex++)
			{
				int SEED_SET_SIZE = seedList.get(seedIndex);
				System.out.println("Calculating for seed=" + SEED_SET_SIZE);

				String outputAvgAlhpaPrecisionFile = "./" + avgResult + "/avg-precison-" + fileNames.get(i) + "-seed="
						+ SEED_SET_SIZE + ".txt";
				String outputAvgAlhpaRecallFile = "./" + avgResult + "/avg-recall-" + fileNames.get(i) + "-seed="
						+ SEED_SET_SIZE + ".txt";
				file1 = new File(outputAvgAlhpaPrecisionFile);
				if (file1.exists())
				{
					file1.delete();
				}
				file1 = new File(outputAvgAlhpaRecallFile);
				if (file1.exists())
				{
					file1.delete();
				}
				PrintWriter outFile4 = new PrintWriter(new FileWriter(outputAvgAlhpaPrecisionFile, true));
				PrintWriter outFile5 = new PrintWriter(new FileWriter(outputAvgAlhpaRecallFile, true));

				for (int index = 0; index < 9; index++)
				{
					System.out.println("Calculating for alhpa=" + alphaVal[index]);
					Iterator iter = removedData.entrySet().iterator();
					outFileName = "./" + result + "/result-" + fileNames.get(i) + "-seed=" + SEED_SET_SIZE + "-alpha="
							+ alphaVal[index] + ".txt";
					file1 = new File(outFileName);
					if (file1.exists())
					{
						file1.delete();
					}
					// PrintWriter outFile2 = new PrintWriter(new
					// FileWriter(outFileName, true));
					String precisionFile = outFileName + "-precision";
					String recallFile = outFileName + "-recall";
					file1 = new File(precisionFile);
					if (file1.exists())
					{
						file1.delete();
					}
					file1 = new File(recallFile);
					if (file1.exists())
					{
						file1.delete();
					}
					PrintWriter outFile2 = new PrintWriter(new FileWriter(precisionFile, true));
					PrintWriter outFile3 = new PrintWriter(new FileWriter(recallFile, true));

					int queryNo = 0;
					double avgPrecision = 0.0;
					double avgRecall = 0.0;
					while (iter.hasNext())
					{
						queryNo++;
						System.out.println("*****QUERY:" + queryNo + " STARTED**********");
						Map.Entry entry = (Map.Entry) iter.next();
						ArrayList<String> groundTruth = (ArrayList<String>) entry.getValue();
						String listId = (String) entry.getKey();
						Set<String> seeds = new HashSet();
						for (int ss = 0; ss < SEED_SET_SIZE; ss++)
						{
							seeds.add(groundTruth.get(ss));
							// seeds.add(groundTruth.get(1));
						}
						// seeds.add(groundTruth.get(0));
						// .add(groundTruth.get(1));
						System.out.println("Seeds are:" + groundTruth.get(0) + " " + groundTruth.get(1));
						Set<String> expandedSet = StaticThreshold.getExpandedSet(seeds, alphaVal[index], 10);
						// outFile2.println("For Listid: "+ listId + " ,Seeds
						// are:"+
						// groundTruth.get(0) + " " + groundTruth.get(1));
						// outFile2.flush();
						String truth = "";
						for (int j = 0; j < groundTruth.size(); j++)
						{
							truth = truth + " " + groundTruth.get(j);
						}
						// outFile2.println("GroundTruth is: " + truth);
						// outFile2.flush();
						Iterator iter1 = expandedSet.iterator();
						String output = "";
						while (iter1.hasNext())
						{
							output = output + iter1.next() + " ";
						}
						// outFile2.println("Result retrieved is: "+output);
						// outFile2.flush();
						double precision = getPrecision(groundTruth, expandedSet);
						double recall = getRecall(groundTruth, expandedSet);

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
					avgPrecision = avgPrecision / NUM_OF_TEST_PER_FILE;
					avgRecall = avgRecall / NUM_OF_TEST_PER_FILE;
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
				System.out.println("###############Done for seed:" + SEED_SET_SIZE + " ################");
			}
			System.out.println("###############Ending for file:" + fileNames.get(i) + " ################");
		}

	}

	private static double getRecall(ArrayList<String> groundTruth, Set<String> expandedSet)
	{
		if (expandedSet == null || expandedSet.size() == 0)
		{
			return 0;
		}
		int truePositivesCount = 0;
		Iterator iter = expandedSet.iterator();
		while (iter.hasNext())
		{
			if (groundTruth.contains(iter.next()))
			{
				truePositivesCount++;
			}
		}
		double recall = (double) truePositivesCount / (double) groundTruth.size();
		return recall;
	}

	private static double getPrecision(ArrayList<String> groundTruth, Set<String> expandedSet)
	{
		if (expandedSet == null || expandedSet.size() == 0)
		{
			return 0;
		}
		int truePositivesCount = 0;
		Iterator iter = expandedSet.iterator();
		while (iter.hasNext())
		{
			if (groundTruth.contains(iter.next()))
			{
				truePositivesCount++;
			}
		}
		double precision = (double) truePositivesCount / (double) expandedSet.size();
		return precision;
	}

	/**
	 * 将原始数据分成几块 并排序 再保存到临时文件
	 * 
	 * @throws IOException
	 */
	public static void splitData(String originalData) throws IOException
	{
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(ORIGINALPATH));

		String line = "";
		int lineNumber = 0;
		int fileNumber = 0;
		while ((line = br.readLine()) != null)
		{
			lineNumber++;
		}

		File[] tempFiles = new File[lineNumber / TEMPFILELENGTH + 1];// 将会产生的临时文件列表

		for (int i = 0; i < tempFiles.length; i++)
		{
			tempFiles[i] = new File(TEMPFILEPATH + "TempFile" + i + ".txt");

			String outPath = TEMPFILEPATH + "TempFile" + i + ".txt";
			int starline = 0;
			int endline = 0;

			if (i == 0)
			{
				starline = 1;
				endline = TEMPFILELENGTH;
			} else
			{
				starline = i * TEMPFILELENGTH + 1;
				endline = (i + 1) * TEMPFILELENGTH;
			}

			copySubdataset(ORIGINALPATH, outPath, starline, endline);
		}
	}

	public static void multiFileSort() throws IOException
	{

		String splitFolder = TEMPFILEPATH;
		File folder = new File(splitFolder);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++)
		{
			String SourceFileName = TEMPFILEPATH + listOfFiles[i].getName();
			String SortedFileName = SORTEDFILEPATH + listOfFiles[i].getName() + "_Sorted" + ".txt";
			File outputFile = new File(SortedFileName);
			Tuple.sort(SourceFileName, SortedFileName);
		}
	}

	/**
	 * 多路归并排序
	 * 
	 * @param files
	 * @throws IOException
	 */
	public static void multiWaysMergeSort(String[] files, int mergeLoop, String outPath) throws IOException
	{

		String path = outPath;
		int mergeSortCount = mergeLoop;

		System.out.println("归并文件-----第 " + mergeSortCount + " 次-----");
		// 当最后只有一个文件的时候 数据已经排序成功 直接复制保存到结果文件

		if (files.length == 1)
		{
			// String copylastFilePath = lastFilePath + lastFileName;
			// copyFile(files[0], copylastFilePath, false);
			// deleteFile(files[0]);
			System.out.println("finish merge!");
			return;
		}

		for (int i = 0; i < files.length; i += 2)
		{
			// 开始合并两个相邻的文件 所以一次跳两个
			if (i == files.length - 1)
			{
				// 这时候已经只剩下最后一个文件了 不需要合并 本趟归并结束
				String newFileName = path + "last_" + mergeSortCount + "_" + i + ".txt";
				File file1 = new File(files[i]);
				file1.renameTo(new File(newFileName));
				break;
			}

			// 将br1 和 br2 写入到Write
			BufferedReader br1 = new BufferedReader(new FileReader(files[i]));
			BufferedReader br2 = new BufferedReader(new FileReader(files[i + 1]));
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(path + "last_" + mergeSortCount + "_" + i + ".txt"));
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
						writer.write(s2);
						writer.write("\r\n");
						// writer.write(System.getProperty("line.separator"));
						// System.out.println("write time : " + writeTime++);
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
			// deleteFile(files[i]);
			// deleteFile(files[i + 1]);
			writer.close();
		}
		mergeSortCount++;
		String nextFiles = "last_" + (mergeSortCount - 1) + "_";
		multiWaysMergeSort(getTempFiles(nextFiles, path), mergeSortCount, path);
	}

	public static String[] getTempFiles(String fileHead, String Path)
	{

		// fileNames = new ArrayList();
		String splitFolder = Path;
		File folder = new File(splitFolder);
		File[] listOfFiles = folder.listFiles();
		int fileNumber = 0;
		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].getName().startsWith(fileHead))
			{
				fileNumber++;
			}
		}

		String[] fileNames = new String[fileNumber];

		int filestar = 0;

		for (int i = 0; i < listOfFiles.length; i++)
		{
			if (listOfFiles[i].getName().startsWith(fileHead))
			{
				fileNames[filestar] = Path + listOfFiles[i].getName();
				filestar++;
			}
		}

		return fileNames;
	}

	public static String[] getTempFiles_total(String Path)
	{

		// fileNames = new ArrayList();
		String splitFolder = Path;
		File folder = new File(splitFolder);
		File[] listOfFiles = folder.listFiles();

		String[] fileNames = new String[listOfFiles.length];

		int filestar = 0;

		for (int i = 0; i < listOfFiles.length; i++)
		{
			fileNames[i] = Path + listOfFiles[i].getName();
		}

		return fileNames;
	}

	public static void copySubdataset(String inputdataPath, String outputPath, int startline, int endline)
	{
		try
		{
			File filename = new File(inputdataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			FileWriter writer = new FileWriter(outputPath, true);

			line = br.readLine();
			int linenumber = 1;

			while (line != null)
			{

				if ((linenumber >= startline) && (linenumber <= endline))
				{
					writer.write(line + "\r\n");
				}

				line = br.readLine(); // 一次读入一行数据
				linenumber++;
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private static void getBetweenTime(String start, String type) throws ParseException
	{
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		java.util.Date begin = dfs.parse(start);
		java.util.Date end = dfs.parse(dfs.format(new Date()));
		long between = (end.getTime() - begin.getTime());
		// long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		// long day1 = between / (24 * 3600);
		// long hour1 = between % (24 * 3600) / 3600;
		// long minute1 = between % 3600 / 60;
		long second = between / 1000;
		long Msecond = between % 1000;
		System.out.println("The runling time of this roll is " + second + " second and " + Msecond + "millsecond");
	}

}
