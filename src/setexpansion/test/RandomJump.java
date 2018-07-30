package setexpansion.test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import setexpansion.algorithm.SumOfTerms;
import setexpansion.experiment.SyntheticData;
import setexpansion.util.Tuple;

public class RandomJump
{
	public static void randomJump(String dataFile, String workPath, String seedFile, int topKSetSize) throws Exception
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

		java.util.Date begin = dfs.parse(dfs.format(new Date()));

		UtilityForRandomJump RJ = new UtilityForRandomJump();

		// String stage = "pickTop";

		RJ.generatDictFile(dataFile, outputPath);

		Tuple.sort(outputPath + "smallterm_list.txt", outputPath + "smallterm_list_sorted.txt");

		SumOfTerms.sumOfTerms_list(outputPath + "smallterm_list_sorted.txt", outputPath + "MatrixA1T.txt");

		RJ.pickDict(outputPath + "MatrixA1T.txt", outputPath + "Dict.txt");

		RJ.createMatrixA1(outputPath + "Dict.txt", dataFile, outputPath + "MatrixA2.txt");

		RJ.findSeedLocation(outputPath + "Dict.txt", seedFile, outputPath + "SeedsLocation.txt");

		RJ.oneTime(5, outputPath + "Dict.txt", outputPath + "SeedsLocation.txt", outputPath + "MatrixA1T.txt",
				outputPath + "MatrixA2.txt", 0.8, 0.0000001, outputPath + "result.txt", outputPath + "log.txt");

		RJ.pickTop_smallMemory(outputPath + "Dict.txt", outputPath + "result.txt", 20, outputPath + "final_result.txt");

		java.util.Date end = dfs.parse(dfs.format(new Date()));
		long between = (end.getTime() - begin.getTime());
		long second = between / 1000;
		long Msecond = between % 1000;
		writer_log.write("The runing time of this roll is  " + second + "." + Msecond + "  millsecond" + "\r\n");

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
