package setexpansion.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import setexpansion.algorithm.SumOfPairs;
import setexpansion.algorithm.SumOfTerms;
import setexpansion.util.DataReader;
import setexpansion.util.Tuple;

public class SEISA
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	public static void sEISA_withSeedNumber(String dataFile, String workPath, String seedsPath, String SeedNumber)
			throws IOException, ParseException
	{
		String filename = dataFile.substring(dataFile.lastIndexOf("\\") + 1);
		// String[] filenameparts = filename.split("\\.");
		filename = SyntheticData.getFileNameNoEx(filename) + SeedNumber;

		SyntheticData.createDir(workPath + filename);
		String outputPath = workPath + filename + "\\";

		BufferedWriter writer_log = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "log.txt"), "UTF-8"));

		System.out.println("***********SEISA Starting************************");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		writer_log.write("The SEISA program begin at: " + df.format(new Date()) + "\r\n");

		DataReader dr = new DataReader();

		dr.generatedSortedFile_Path(dataFile, outputPath);

		writer_log.write("The program of generated Sort end at: " + df.format(new Date()) + "\r\n");

		Tuple.sort(outputPath + "smallterm.txt", outputPath + "smallTermSorted.txt");
		writer_log.write("The program of smallterm Sort end at: " + df.format(new Date()) + "\r\n");

		SumOfTerms.sumOfTerms(outputPath + "smallTermSorted.txt", outputPath + "smalltermCount.txt");
		writer_log.write("The program of sumOfTerms end at: " + df.format(new Date()) + "\r\n");

		Tuple.sort(outputPath + "smalltermPair.txt", outputPath + "smallTermPairSorted.txt");
		writer_log.write("The program of smalltermPair Sort end at: " + df.format(new Date()) + "\r\n");

		SumOfPairs.sumOfPairs(outputPath + "smallTermPairSorted.txt", outputPath + "smallTermPairCount.txt");
		writer_log.write("The program of sumOfPairs end at: " + df.format(new Date()) + "\r\n");

		String seedsfile[] = getTempFiles_total(seedsPath);

		for (int i = 0; i < seedsfile.length; i++)
		{
			PrintWriter outFile2 = new PrintWriter(new FileWriter(outputPath + "result.txt", true));

			outFile2.println("One first seed  expansion begin at: " + df.format(new Date()));

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

			// String partFilename = seedsfile[i].substring(seedsfile[i].lastIndexOf("\\") + 1);

			BufferedWriter writer_resultlist = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "resultlist.txt"), "UTF-8"));

			BufferedWriter writer_runningtime = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "runningtime.txt"), "UTF-8"));

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

				Set<String> expandedSet = SEISA_StaticThreshold.getExpandedSet(dataFile, outputPath, seeds, 0.5, 20);

				/*
				 * Set<String> expandedSet = StaticThreshold_modify.getExpandedSet(seeds, 0.5); int stage = 1;
				 * 
				 * if (stage == 1) { return; }
				 */

				Iterator iter1 = expandedSet.iterator();

				outFile2.println("Result retrieved is: ");

				writer_resultlist.write("***" + "\r\n");

				while (iter1.hasNext())
				{
					String line = iter1.next().toString();
					outFile2.println(line);
					writer_resultlist.write(line + "\r\n");
				}

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;

				writer_runningtime.write(between + "\r\n");

				outFile2.println("The running time of this roll is  " + second + "." + Msecond + "  millsecond");

				outFile2.println("One seed  expansion end at: " + df.format(new Date()));

				outFile2.flush();

				writer_log
						.write("The running time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");

				System.out.println("One seed  expansion end at: " + df.format(new Date()));// new

			}

			writer_resultlist.flush();
			writer_resultlist.close();

			writer_runningtime.flush();
			writer_runningtime.close();
		}

		writer_log.flush();
		writer_log.close();

		System.out.println("The program end at: " + df.format(new Date()));// new
		System.out.println("###############Ending for file: ################");

	}

	public static void sEISA(String dataFile, String workPath, String seedsPath) throws IOException, ParseException
	{
		String filename = dataFile.substring(dataFile.lastIndexOf("\\") + 1);
		// String[] filenameparts = filename.split("\\.");
		filename = SyntheticData.getFileNameNoEx(filename);

		SyntheticData.createDir(workPath + filename);
		String outputPath = workPath + filename + "\\";

		BufferedWriter writer_log = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "log.txt"), "UTF-8"));

		System.out.println("***********SEISA Starting************************");

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		writer_log.write("The SEISA program begin at: " + df.format(new Date()) + "\r\n");

		DataReader dr = new DataReader();

		dr.generatedSortedFile_Path(dataFile, outputPath);

		writer_log.write("The program of generated Sort end at: " + df.format(new Date()) + "\r\n");

		Tuple.sort(outputPath + "smallterm.txt", outputPath + "smallTermSorted.txt");
		writer_log.write("The program of smallterm Sort end at: " + df.format(new Date()) + "\r\n");

		SumOfTerms.sumOfTerms(outputPath + "smallTermSorted.txt", outputPath + "smalltermCount.txt");
		writer_log.write("The program of sumOfTerms end at: " + df.format(new Date()) + "\r\n");

		Tuple.sort(outputPath + "smalltermPair.txt", outputPath + "smallTermPairSorted.txt");
		writer_log.write("The program of smalltermPair Sort end at: " + df.format(new Date()) + "\r\n");

		SumOfPairs.sumOfPairs(outputPath + "smallTermPairSorted.txt", outputPath + "smallTermPairCount.txt");
		writer_log.write("The program of sumOfPairs end at: " + df.format(new Date()) + "\r\n");

		String seedsfile[] = getTempFiles_total(seedsPath);

		for (int i = 0; i < seedsfile.length; i++)
		{
			PrintWriter outFile2 = new PrintWriter(new FileWriter(outputPath + "result.txt", true));

			outFile2.println("One first seed  expansion begin at: " + df.format(new Date()));

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

			// String partFilename = seedsfile[i].substring(seedsfile[i].lastIndexOf("\\") + 1);

			BufferedWriter writer_resultlist = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "resultlist.txt"), "UTF-8"));

			BufferedWriter writer_runningtime = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "runningtime.txt"), "UTF-8"));

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

				Set<String> expandedSet = SEISA_StaticThreshold.getExpandedSet(dataFile, outputPath, seeds, 0.5, 20);

				/*
				 * Set<String> expandedSet = StaticThreshold_modify.getExpandedSet(seeds, 0.5); int stage = 1;
				 * 
				 * if (stage == 1) { return; }
				 */

				Iterator iter1 = expandedSet.iterator();

				outFile2.println("Result retrieved is: ");

				writer_resultlist.write("***" + "\r\n");

				while (iter1.hasNext())
				{
					String line = iter1.next().toString();
					outFile2.println(line);
					writer_resultlist.write(line + "\r\n");
				}

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;

				writer_runningtime.write(between + "\r\n");

				outFile2.println("The running time of this roll is  " + second + "." + Msecond + "  millsecond");

				outFile2.println("One seed  expansion end at: " + df.format(new Date()));

				outFile2.flush();

				writer_log
						.write("The running time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");

				System.out.println("One seed  expansion end at: " + df.format(new Date()));// new

			}

			writer_resultlist.flush();
			writer_resultlist.close();

			writer_runningtime.flush();
			writer_runningtime.close();
		}

		writer_log.flush();
		writer_log.close();

		System.out.println("The program end at: " + df.format(new Date()));// new
		System.out.println("###############Ending for file: ################");

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
}