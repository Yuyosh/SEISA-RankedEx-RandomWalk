package setexpansion.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import setexpansion.test.RankedSE;
import setexpansion.test.StaticSeisa;

public class ExtractData
{

	public static void main(String[] args) throws Exception
	{

		String stage = "PrecisionRecallPoints_groups";

		String dataFile = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\SampledWebList_Formated_tenPercents_format.txt";

		String seedsPath = "D:\\CodePath\\dataset\\SampledWebList\\Seeds_Rank\\mattress\\";

		int[] topk_size = { 10, 20, 30, 40, 50, 60 };

		int k = topk_size.length;

		if (stage == "CheckDuplication")
		{
			String path = "E:\\余\\主目录\\paper-set expansion\\8-20\\Data\\result\\test\\SEISA_color_6\\";

			String outpath = "E:\\余\\主目录\\paper-set expansion\\8-20\\Data\\result\\test\\";
			;

			CheckDuplication(path, outpath);
			return;
		}

		if (stage == "formatData")
		{
			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\SampledWebList_Formated_tenPercents_format.txt";

			formatFile(dataFile, outputFile);
			return;
		}

		String fileName_concept = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\PR\\cameraBrandConcept.txt";
		String fileName_rankedResult = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\PR\\camera_order\\SEISA_camera_100_.txt";
		String fileName_topk = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\PR\\point.txt";

		String fileName_outputPath = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\PR\\camera_order\\";

		if (stage == "PrecisionRecallPoints_groups")
		{

			PrecisionRecallPoints_groups(fileName_concept, fileName_rankedResult, 100, fileName_outputPath);
			return;
		}

		if (stage == "PrecisionRecallPoints")
		{

			PrecisionRecallPoints(fileName_concept, fileName_rankedResult, fileName_topk, fileName_outputPath);
			return;
		}

		if (stage == "combineFile")
		{
			String Path = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\SEISA\\camera\\camera_50\\";

			combineFile(Path);
			return;
		}

		// ******************stage five: get random seeds **********************

		if (stage == "two")
		{
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\";

			RankedSE.rankedSE(dataFile, workPath, seedsPath, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\";

			RandomWalk.randomJump(dataFile, workPath, seedsPath, 30);
			return;
		}

		// ******************others **********************

		if (stage == "SEISA")
		{
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\";

			SEISA.sEISA(dataFile, workPath, seedsPath);
			return;
		}

		if (stage == "RankedSE")
		{
			String workPath = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\RankedSE\\";

			RankedSE.rankedSE(dataFile, workPath, seedsPath, 100);
			return;
		}

		if (stage == "RandomJump")
		{
			String workPath = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\RandomJump\\";

			RandomWalk.randomJump(dataFile, workPath, seedsPath, 100);
			return;
		}
	}

	public static void CheckDuplication(String path, String fileName_outputPath) throws IOException
	{

		BufferedWriter outFile_term = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName_outputPath + "CheckResult.txt"), "UTF-8"));

		String seedsfile[] = StaticSeisa.getTempFiles_total(path);

		for (int i = 0; i < seedsfile.length; i++)
		{
			String files = seedsfile[i].substring(seedsfile[i].lastIndexOf("\\") + 1);
			files = SyntheticData.getFileNameNoEx(files);

			if (!files.startsWith("PR_"))
			{
				outFile_term.write(seedsfile[i] + "\r\n");

				int Size_rankedResult = getDataLineSize(seedsfile[i]);
				String[] rankedResult = new String[Size_rankedResult];

				File file_rankedResult = new File(seedsfile[i]);
				LineIterator iterator_rankedResult = FileUtils.lineIterator(file_rankedResult, "UTF-8");

				int lineNumber = 0;
				while (iterator_rankedResult.hasNext())
				{
					String line = iterator_rankedResult.nextLine();

					rankedResult[lineNumber] = line;
					lineNumber++;
				}

				int startflag = 0;

				int onegroup = 0;
				Set<String> onegroupall = new HashSet();

				for (int j = 0; j < Size_rankedResult; j++)
				{

					if (rankedResult[j].startsWith("***"))
					{
						if (startflag == 0)
						{
							startflag = 1;
							continue;
						} else
						{
							if (onegroupall.size() == onegroup)
							{
								outFile_term.write("onegroupall.size() == onegroup = " + onegroup + "\r\n");

								outFile_term.write("No duplication\r\n");
							} else
							{
								outFile_term.write("onegroupall.size() == onegroup =" + onegroup + "\r\n");

								outFile_term.write("Have duplication&&&&&&&&&&&&&&&&&&&&&&&&&&\r\n");
							}

							onegroup = 0;
							onegroupall.clear();
						}
					} else
					{
						onegroupall.add(rankedResult[j]);
						onegroup++;

						if (j == (Size_rankedResult - 1))
						{
							outFile_term.write("j=Size_rankedResult=" + j + "\r\n");
							if (onegroupall.size() == onegroup)
							{
								outFile_term.write("onegroupall.size() == onegroup - 1 =" + onegroup + "\r\n");

								outFile_term.write("No duplication\r\n");
							} else
							{
								outFile_term.write("onegroupall.size() == onegroup =" + onegroup + "\r\n");
								outFile_term.write("Have duplication&&&&&&&&&&&&&&&&&&&&&&&&&&\r\n");
							}

							onegroup = 0;
							onegroupall.clear();
						}
					}

				}

			}

		}

		outFile_term.write("***************************************************************\r\n");
		outFile_term.flush();

		outFile_term.close();

	}

	public static void combineFile(String path) throws IOException
	{
		BufferedWriter outFile_term = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(path + "SEISA_country_70.txt"), "UTF-8"));

		String seedsfile[] = StaticSeisa.getTempFiles_total(path);

		for (int i = 0; i < seedsfile.length; i++)
		{
			String filename = seedsfile[i].substring(seedsfile[i].lastIndexOf("\\") + 1);
			if (filename.startsWith("top"))
			{
				outFile_term.write("***********\r\n");

				File filename_seed = new File(seedsfile[i]);

				InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed));
				BufferedReader br_seed = new BufferedReader(reader_seed);

				String line_seed = "";

				while ((line_seed = br_seed.readLine()) != null)
				{
					outFile_term.write(line_seed + "\r\n");
				}
			}

		}

		outFile_term.flush();
		outFile_term.close();
	}

	public static void PrecisionRecallPoints_groups(String fileName_concept, String fileName_rankedResult, int topk,
			String fileName_outputPath) throws IOException
	{

		int Size_rankedResult = getDataLineSize(fileName_rankedResult);
		String[] rankedResult = new String[Size_rankedResult];

		File file_rankedResult = new File(fileName_rankedResult);
		LineIterator iterator_rankedResult = FileUtils.lineIterator(file_rankedResult, "UTF-8");

		int lineNumber = 0;
		while (iterator_rankedResult.hasNext())
		{
			String line = iterator_rankedResult.nextLine();

			rankedResult[lineNumber] = line;
			lineNumber++;
		}

		int Size_concept = getDataLineSize(fileName_concept);
		String[] concept = new String[Size_concept];

		File file_concept = new File(fileName_concept);
		LineIterator iterator_concept = FileUtils.lineIterator(file_concept, "UTF-8");

		lineNumber = 0;
		while (iterator_concept.hasNext())
		{
			String line = iterator_concept.nextLine();

			concept[lineNumber] = line;
			lineNumber++;
		}

		String filename = fileName_rankedResult.substring(fileName_rankedResult.lastIndexOf("\\") + 1);
		filename = SyntheticData.getFileNameNoEx(filename);

		BufferedWriter outFile_term = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName_outputPath + "PR_" + filename + ".txt"), "UTF-8"));

		lineNumber = 0;

		String[] oneGroup = new String[topk];

		int oneItme = 0;
		int find = 0;
		int total = concept.length;
		int onegroup = 0;

		double total_P = 0.0;
		double total_R = 0.0;

		for (int i = 1; i < Size_rankedResult; i++)
		{

			if (rankedResult[i].startsWith("***"))
			{
				continue;
			}

			if (oneItme == topk - 1)
			{
				oneGroup[oneItme] = rankedResult[i];
				oneItme++;

				for (int j = 0; j < topk; j++)
				{
					for (int k = 0; k < concept.length; k++)
					{
						if (oneGroup[j].equals(concept[k]))
						{
							find++;
							break;
						}
					}
				}

				double recall = (double) find / (double) total;
				double precision = find / (double) topk;

				total_R = total_R + recall;
				total_P = total_P + precision;

				outFile_term.write(recall + "\t\t" + precision + "\r\n");

				oneItme = 0;
				find = 0;

			} else
			{
				oneGroup[oneItme] = rankedResult[i];
				oneItme++;
			}
		}

		int groupNumber = Size_rankedResult / (topk + 1);

		outFile_term.write("\r\n");

		outFile_term.write(total_R + "\t\t" + total_P + "\r\n");

		outFile_term.write(total_R / groupNumber + "\t\t" + total_P / groupNumber + "\r\n");

		outFile_term.flush();

		outFile_term.close();

	}

	public static void PrecisionRecallPoints(String fileName_concept, String fileName_rankedResult,
			String fileName_topk, String fileName_outputPath) throws IOException
	{

		int Size_rankedResult = getDataLineSize(fileName_rankedResult);
		String[] rankedResult = new String[Size_rankedResult];

		File file_rankedResult = new File(fileName_rankedResult);
		LineIterator iterator_rankedResult = FileUtils.lineIterator(file_rankedResult, "UTF-8");

		int lineNumber = 0;
		while (iterator_rankedResult.hasNext())
		{
			String line = iterator_rankedResult.nextLine();

			rankedResult[lineNumber] = line;
			lineNumber++;
		}

		int Size_concept = getDataLineSize(fileName_concept);
		String[] concept = new String[Size_concept];

		File file_concept = new File(fileName_concept);
		LineIterator iterator_concept = FileUtils.lineIterator(file_concept, "UTF-8");

		lineNumber = 0;
		while (iterator_concept.hasNext())
		{
			String line = iterator_concept.nextLine();

			concept[lineNumber] = line;
			lineNumber++;
		}

		int Size_topk = getDataLineSize(fileName_topk);
		int[] topk = new int[Size_topk];

		File file_topk = new File(fileName_topk);
		LineIterator iterator_topk = FileUtils.lineIterator(file_topk, "UTF-8");

		lineNumber = 0;
		while (iterator_topk.hasNext())
		{
			String line = iterator_topk.nextLine();

			topk[lineNumber] = Integer.parseInt(line);
			lineNumber++;
		}

		String filename = fileName_rankedResult.substring(fileName_rankedResult.lastIndexOf("\\") + 1);
		filename = SyntheticData.getFileNameNoEx(filename);

		BufferedWriter outFile_term = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName_outputPath + "PR_" + filename + ".txt"), "UTF-8"));

		lineNumber = 0;

		for (int i = 0; i < topk.length; i++)
		{
			int find = 0;
			int total = concept.length;

			for (int j = 0; j < topk[i]; j++)
			{
				for (int k = 0; k < concept.length; k++)
				{
					if (rankedResult[j].equals(concept[k]))
					{
						find++;
						break;
					}
				}
			}

			double recall = (double) find / (double) total;
			double precision = (double) find / (double) topk[i];

			outFile_term.write(recall + "\t\t" + precision + "\r\n");
			outFile_term.flush();
		}
	}

	public static int getDataLineSize(String dataPath)
	{

		int LineNumbers = 0;
		try
		{
			File file = new File(dataPath);
			LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

			while (iterator.hasNext())
			{
				String line = iterator.nextLine();
				LineNumbers++;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return LineNumbers;
	}

	public static void formatFile(String fileName, String outputPath) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

		BufferedWriter outFile_term = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "formatData_tenpercent.txt"), "UTF-8"));

		int lineNumber = 0;

		while (iterator.hasNext())
		{
			String line = iterator.nextLine();
			String values[] = line.split("\t\t");

			String listId = lineNumber + "";
			lineNumber++;

			String terms = values[1];

			outFile_term.write(listId + "\t\t" + terms + "\r\n");
			outFile_term.flush();

			// System.out.println("Finished line no:" + k);
		}
	}

	public static boolean createDir(String destDirName)
	{
		File dir = new File(destDirName);
		if (dir.exists())
		{
			// System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
			return false;
		}
		if (!destDirName.endsWith(File.separator))
		{
			destDirName = destDirName + File.separator;
		}
		// 创建目录
		if (dir.mkdirs())
		{
			// System.out.println("创建目录" + destDirName + "成功！");
			return true;
		} else
		{
			// System.out.println("创建目录" + destDirName + "失败！");
			return false;
		}
	}

	/*
	 * Java文件操作 获取文件扩展名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getExtensionName(String filename)
	{
		if ((filename != null) && (filename.length() > 0))
		{
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1)))
			{
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/*
	 * Java文件操作 获取不带扩展名的文件名
	 * 
	 * Created on: 2011-8-2 Author: blueeagle
	 */
	public static String getFileNameNoEx(String filename)
	{
		if ((filename != null) && (filename.length() > 0))
		{
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length())))
			{
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
}
