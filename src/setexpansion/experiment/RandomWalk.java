package setexpansion.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import setexpansion.algorithm.SumOfTerms;
import setexpansion.test.UtilityForRandomJump;
import setexpansion.util.Tuple;

public class RandomWalk
{
	public static void randomJump_withSeedNumber(String dataFile, String workPath, String seedsPath, int topKSetSize,
			String SeedNumber) throws Exception
	{
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

		UtilityForRandomJump RJ = new UtilityForRandomJump();

		// String stage = "pickTop";

		RJ.generatDictFile(dataFile, outputPath);

		Tuple.sort(outputPath + "smallterm_list.txt", outputPath + "smallterm_list_sorted.txt");

		SumOfTerms.sumOfTerms_list(outputPath + "smallterm_list_sorted.txt", outputPath + "MatrixA1T.txt");

		RJ.pickDict(outputPath + "MatrixA1T.txt", outputPath + "Dict.txt");

		RJ.createMatrixA1(outputPath + "Dict.txt", dataFile, outputPath + "MatrixA2.txt");

		for (int i = 0; i < seedsfile.length; i++)
		{

			BufferedWriter writer_runningtime = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "runningtime.txt"), "UTF-8"));

			RJ.findSeedLocation_RandomWalk(outputPath + "Dict.txt", seedsfile[i], outputPath + "SeedsLocation.txt");

			InputStreamReader reader_seed = new InputStreamReader(
					new FileInputStream(outputPath + "SeedsLocation.txt")); // 建立一个输入流对象reader
			BufferedReader br_seed = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_seed = "";
			int lineNumber = 0;

			while ((line_seed = br_seed.readLine()) != null)
			{

				java.util.Date begin = dfs.parse(dfs.format(new Date()));

				String[] sep = line_seed.split("\t\t");
				String[] seeds = sep[1].split("\t");

				RJ.oneTime_randomWalk(outputPath + "Dict.txt", seeds, outputPath + "MatrixA1T.txt",
						outputPath + "MatrixA2.txt", 0.8, outputPath + "result.txt", outputPath + "log.txt");

				// 2017-6-9
				// RJ.pickTop_smallMemory_randomWalk(outputPath + "Dict.txt", outputPath + "result.txt", 20,
				// outputPath + "final_result.txt", outputPath + "resultlist.txt");
				RJ.pickTop_smallMemory_randomWalk(outputPath + "Dict.txt", outputPath + "result.txt", topKSetSize,
						outputPath + "final_result.txt", outputPath + "resultlist.txt");

				lineNumber++;

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;
				writer_log
						.write("The running time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");
				writer_runningtime.write(between + "\r\n");
			}

			writer_runningtime.flush();
			writer_runningtime.close();
		}

		writer_log.flush();
		writer_log.close();
	}

	public static void randomJump(String dataFile, String workPath, String seedsPath, int topKSetSize) throws Exception
	{
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

		UtilityForRandomJump RJ = new UtilityForRandomJump();

		// String stage = "pickTop";

		RJ.generatDictFile(dataFile, outputPath);

		Tuple.sort(outputPath + "smallterm_list.txt", outputPath + "smallterm_list_sorted.txt");

		SumOfTerms.sumOfTerms_list(outputPath + "smallterm_list_sorted.txt", outputPath + "MatrixA1T.txt");

		RJ.pickDict(outputPath + "MatrixA1T.txt", outputPath + "Dict.txt");

		RJ.createMatrixA1(outputPath + "Dict.txt", dataFile, outputPath + "MatrixA2.txt");

		for (int i = 0; i < seedsfile.length; i++)
		{

			BufferedWriter writer_runningtime = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath + "runningtime.txt"), "UTF-8"));

			RJ.findSeedLocation_RandomWalk(outputPath + "Dict.txt", seedsfile[i], outputPath + "SeedsLocation.txt");

			InputStreamReader reader_seed = new InputStreamReader(
					new FileInputStream(outputPath + "SeedsLocation.txt")); // 建立一个输入流对象reader
			BufferedReader br_seed = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_seed = "";
			int lineNumber = 0;

			while ((line_seed = br_seed.readLine()) != null)
			{

				java.util.Date begin = dfs.parse(dfs.format(new Date()));

				String[] sep = line_seed.split("\t\t");
				String[] seeds = sep[1].split("\t");

				RJ.oneTime_randomWalk(outputPath + "Dict.txt", seeds, outputPath + "MatrixA1T.txt",
						outputPath + "MatrixA2.txt", 0.8, outputPath + "result.txt", outputPath + "log.txt");

				// 2017-6-9
				// RJ.pickTop_smallMemory_randomWalk(outputPath + "Dict.txt", outputPath + "result.txt", 20,
				// outputPath + "final_result.txt", outputPath + "resultlist.txt");
				RJ.pickTop_smallMemory_randomWalk(outputPath + "Dict.txt", outputPath + "result.txt", topKSetSize,
						outputPath + "final_result.txt", outputPath + "resultlist.txt");

				lineNumber++;

				java.util.Date end = dfs.parse(dfs.format(new Date()));
				long between = (end.getTime() - begin.getTime());
				long second = between / 1000;
				long Msecond = between % 1000;
				writer_log
						.write("The running time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");
				writer_runningtime.write(between + "\r\n");
			}

			writer_runningtime.flush();
			writer_runningtime.close();
		}

		writer_log.flush();
		writer_log.close();
	}

	public static void main(String[] args) throws Exception
	{
		String DataPath = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\";

		UtilityForRandomJump RJ = new UtilityForRandomJump();

		String stage = "pickTop";

		if (stage == "pickTop")
		{
			RJ.pickTop_smallMemory(DataPath + "Dict.txt", DataPath + "SeisaSeeds_matters_result_0.1_10.txt", 20,
					DataPath + "SeisaSeeds_matters_final_result_0.1_10.txt");

			return;
		}

		if (stage == "rewirteDataset")
		{
			RJ.rewirteDataset(DataPath + "SampledWebList_Formated - Copy.txt",
					DataPath + "SampledWebList_Formated_reWirted.txt");

			return;
		}

		if (stage == "oneTime")
		{
			RJ.oneTime(5, DataPath + "Dict.txt", DataPath + "SeisaSeeds_matters_Location.txt",
					DataPath + "MatrixA1T.txt", DataPath + "MatrixA2.txt", 0.1, 0.0000001,
					DataPath + "SeisaSeeds_matters_result_0.1_10.txt", DataPath + "SeisaSeeds_matters_log_0.1_10.txt");

			return;
		}

		if (stage == "findSeedsLocation")
		{
			RJ.createMatrixA1(DataPath + "Dict.txt", DataPath + "SeisaSeeds_matters.txt",
					DataPath + "SeisaSeeds_matters_Location.txt");

			return;
		}

		if (stage == "createMatrixA1")
		{
			RJ.createMatrixA1(DataPath + "Dict.txt", DataPath + "SampledWebList_Formated_reWirted.txt",
					DataPath + "MatrixA1.txt");

			return;
		}

		if (stage == "generatDictFile")
		{
			RJ.generatDictFile(DataPath + "SampledWebList_Formated_reWirted.txt", DataPath);

			return;
		}

		if (stage == "pickDict")
		{

			RJ.pickDict(DataPath + "MatrixA2T.txt", DataPath + "Dict.txt");

			return;
		}

		if (stage == "teststring")
		{
			String[] a = new String[2];

			String[] b = new String[2];

			for (int i = 0; i < a.length; i++)
			{
				a[i] = "000";
			}
			for (int i = 0; i < b.length; i++)
			{
				b[i] = "111";
			}

			for (int i = 0; i < 2; i++)
			{
				a[i] = b[i];
			}

			for (int i = 0; i < b.length; i++)
			{
				b[i] = "222";
			}

			for (int i = 0; i < 2; i++)
			{
				System.out.print("a: " + a[i] + "\t");
				System.out.print("b: " + b[i] + "\t");
			}

			return;
		}

		if (stage == "multiWaysMergeSort")
		{
			String ORIGINALPATH = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\smallterm_list.txt";
			// int BIGDATALENGTH = 0;
			int TEMPFILELENGTH = 8500000;
			String TEMPFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\smallterm_list\\Source\\";
			String SORTEDFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\RandomJump\\smallterm_list\\Sorted\\";

			RJ.splitData(ORIGINALPATH, TEMPFILELENGTH, TEMPFILEPATH);

			RJ.multiFileSort(TEMPFILEPATH, SORTEDFILEPATH);

			String files[] = RJ.getTempFiles_total(SORTEDFILEPATH);
			RJ.multiWaysMergeSort(files, 1, SORTEDFILEPATH);

			return;
		}

	}

}
