package setexpansion.test;

import static setexpansion.util.MapUtility.DESC;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import setexpansion.util.MapUtility_RandomJump;
import setexpansion.util.Tuple;

public class UtilityForRandomJump
{
	public static void oneTime_randomWalk(String DictFile, String[] Seeds, String MatrixA1TFile, String MatrixA2File,
			double d, String outputFile, String logFile)
	{
		try
		{
			BufferedWriter writer_log = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(logFile), "UTF-8"));

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			writer_log.write("The program begin at: " + df.format(new Date()) + "\r\n");

			String line = "";
			int[] seedlocation = new int[Seeds.length];

			for (int i = 0; i < seedlocation.length; i++)
			{
				seedlocation[i] = Integer.parseInt(Seeds[i]);
			}

			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			double[] preValue = new double[DictSize];
			double[] result = new double[DictSize];

			for (int i = 0; i < preValue.length; i++)
			{
				preValue[i] = (1 - d) / DictSize;
			}

			double charge = 1.0;
			double itemCharge = 0.0;

			int iterationTimes = 0;

			while (iterationTimes < 10)
			{
				double[] tempL = MatrixA2MutipleVector(MatrixA1TFile, MatrixA2File, preValue);
				result = MatrixA1TMutipleVector(MatrixA1TFile, MatrixA2File, tempL);

				for (int i = 0; i < seedlocation.length; i++)
				{
					result[i] = (1 - d) * result[i];
				}

				for (int i = 0; i < seedlocation.length; i++)
				{
					result[seedlocation[i]] = result[seedlocation[i]] + d / seedlocation.length;
				}

				charge = 0.0;
				itemCharge = 0.0;
				for (int i = 0; i < preValue.length; i++)
				{
					if ((preValue[i] - result[i]) > 0)
					{
						charge = charge + (preValue[i] - result[i]);
						if (itemCharge < (preValue[i] - result[i]))
						{
							itemCharge = preValue[i] - result[i];
						}
					} else
					{
						charge = charge - (preValue[i] - result[i]);
						if (itemCharge < (result[i] - preValue[i]))
						{
							itemCharge = result[i] - preValue[i];
						}
					}

				}

				for (int i = 0; i < preValue.length; i++)
				{
					preValue[i] = result[i];
				}

				iterationTimes++;

				writer_log.write("The total gap is " + charge + "   and the max gap is  " + itemCharge + "\r\n");
			}

			// System.out.println("The iteration times is " + iterationTimes);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile, false), "UTF-8"));

			for (double i : result)
			{
				writer.write(i + "\r\n");
			}

			writer.flush();
			writer.close();

			writer_log.write("The program end at: " + df.format(new Date()) + "\r\n");
			writer_log.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void oneTime(int seedsNumber, String DictFile, String SeedsLocationFile, String MatrixA1TFile,
			String MatrixA2File, double d, double error, String outputFile, String logFile)
	{
		try
		{
			BufferedWriter writer_log = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(logFile), "UTF-8"));

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			writer_log.write("The program begin at: " + df.format(new Date()) + "\r\n");

			File filename = new File(SeedsLocationFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";
			int[] seedlocation = new int[seedsNumber];

			while ((line = br.readLine()) != null)
			{
				String[] list = line.split("\t\t");
				String[] sep_temp = list[1].split("\t");

				for (int i = 1; i < sep_temp.length; i++)
				{
					seedlocation[i - 1] = Integer.parseInt(sep_temp[i]);
				}
			}
			br.close();

			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			double[] preValue = new double[DictSize];
			double[] result = new double[DictSize];

			for (int i = 0; i < preValue.length; i++)
			{
				preValue[i] = (1 - d) / DictSize;
			}

			double charge = error + 1.0;
			double itemCharge = 0.0;

			int iterationTimes = 0;

			while (iterationTimes < 10)
			{
				double[] tempL = MatrixA2MutipleVector(MatrixA1TFile, MatrixA2File, preValue);
				result = MatrixA1TMutipleVector(MatrixA1TFile, MatrixA2File, tempL);

				for (int i = 0; i < seedlocation.length; i++)
				{
					result[i] = (1 - d) * result[i];
				}

				for (int i = 0; i < seedlocation.length; i++)
				{
					result[seedlocation[i]] = result[seedlocation[i]] + d / seedlocation.length;
				}

				charge = 0.0;
				itemCharge = 0.0;
				for (int i = 0; i < preValue.length; i++)
				{
					if ((preValue[i] - result[i]) > 0)
					{
						charge = charge + (preValue[i] - result[i]);
						if (itemCharge < (preValue[i] - result[i]))
						{
							itemCharge = preValue[i] - result[i];
						}
					} else
					{
						charge = charge - (preValue[i] - result[i]);
						if (itemCharge < (result[i] - preValue[i]))
						{
							itemCharge = result[i] - preValue[i];
						}
					}

				}

				for (int i = 0; i < preValue.length; i++)
				{
					preValue[i] = result[i];
				}

				iterationTimes++;

				writer_log.write("The total gap is " + charge + "   and the max gap is  " + itemCharge + "\r\n");
			}

			System.out.println("The iteration times is " + iterationTimes);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));

			for (double i : result)
			{
				writer.write(i + "\r\n");
			}

			br.close();

			writer.close();

			writer_log.write("The program end at: " + df.format(new Date()) + "\r\n");
			writer_log.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void pickTop_smallMemory_randomWalk(String DictFile, String scoreFile, int topk, String outputPath,
			String final_result)
	{
		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			int ScoreSize = UtiForCreateDataset.getDataLineSize(scoreFile);

			if (DictSize != ScoreSize)
			{
				System.out.println("Dict error!");
				return;
			}

			File filename_Dit = new File(DictFile);
			InputStreamReader reader_Dit = new InputStreamReader(new FileInputStream(filename_Dit), "UTF-8");
			BufferedReader br_Dit = new BufferedReader(reader_Dit);

			String line_Dit = "";

			File filename_Score = new File(scoreFile);
			InputStreamReader reader_Score = new InputStreamReader(new FileInputStream(filename_Score), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_Score = new BufferedReader(reader_Score); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_Score = "";

			MapUtility_RandomJump mUtil = new MapUtility_RandomJump();

			Map<String, Double> DictScoreMap = new LinkedHashMap<String, Double>();

			while ((line_Dit = br_Dit.readLine()) != null)
			{
				line_Score = br_Score.readLine();

				DictScoreMap.put(line_Dit, Double.parseDouble(line_Score));
			}
			br_Dit.close();
			br_Score.close();

			Map<String, Double> sortedDictScoreMap = mUtil.sortByComparator(DictScoreMap, DESC);

			Set<String> resultSet = mUtil.findTopKSet(sortedDictScoreMap, topk).keySet();

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath, true), "UTF-8"));

			writer.write("*****\r\n");

			BufferedWriter writer_final_result = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(final_result, true), "UTF-8"));
			// FileWriter writer = new FileWriter(outputPath, true);
			writer_final_result.write("*****\r\n");

			Iterator<Map.Entry<String, Double>> iterator = sortedDictScoreMap.entrySet().iterator();

			int index = 0;
			while ((iterator.hasNext()) && (index < topk))
			{
				Map.Entry<String, Double> entry = iterator.next();
				writer.write(entry.getValue() + "\t\t" + entry.getKey() + "\r\n");
				writer_final_result.write(entry.getKey() + "\r\n");
				writer_final_result.flush();
				index++;
			}

			/*
			 * Iterator iter1 = resultSet.iterator();
			 * 
			 * while (iter1.hasNext()) { writer.write(iter1.next().toString() + "\r\n"); }
			 */

			writer.close();
			writer_final_result.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void pickTop_smallMemory(String DictFile, String scoreFile, int k, String outputPath)
	{
		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			int ScoreSize = UtiForCreateDataset.getDataLineSize(scoreFile);

			if (DictSize != ScoreSize)
			{
				System.out.println("Dict error!");
				return;
			}

			File filename_Dit = new File(DictFile);
			InputStreamReader reader_Dit = new InputStreamReader(new FileInputStream(filename_Dit), "UTF-8");
			BufferedReader br_Dit = new BufferedReader(reader_Dit);

			String line_Dit = "";

			File filename_Score = new File(scoreFile);
			InputStreamReader reader_Score = new InputStreamReader(new FileInputStream(filename_Score), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_Score = new BufferedReader(reader_Score); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_Score = "";

			MapUtility_RandomJump mUtil = new MapUtility_RandomJump();

			Map<String, Double> DictScoreMap = new LinkedHashMap<String, Double>();

			while ((line_Dit = br_Dit.readLine()) != null)
			{
				line_Score = br_Score.readLine();

				DictScoreMap.put(line_Dit, Double.parseDouble(line_Score));
			}
			br_Dit.close();
			br_Score.close();

			Map<String, Double> sortedDictScoreMap = mUtil.sortByComparator(DictScoreMap, DESC);

			Set<String> resultSet = mUtil.findTopKSet(sortedDictScoreMap, k).keySet();

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));
			// FileWriter writer = new FileWriter(outputPath, true);

			Iterator<Map.Entry<String, Double>> iterator = sortedDictScoreMap.entrySet().iterator();
			while (iterator.hasNext())
			{
				Map.Entry<String, Double> entry = iterator.next();
				writer.write(entry.getValue() + "\t\t" + entry.getKey() + "\r\n");
			}

			/*
			 * Iterator iter1 = resultSet.iterator();
			 * 
			 * while (iter1.hasNext()) { writer.write(iter1.next().toString() + "\r\n"); }
			 */

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void pickTop(String DictFile, String scoreFile, int k, String outputPath)
	{
		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			int ScoreSize = UtiForCreateDataset.getDataLineSize(scoreFile);

			if (DictSize != ScoreSize)
			{
				System.out.println("Dict error!");
				return;
			}
			String[] dict = new String[DictSize];

			File filename_Dit = new File(DictFile);
			InputStreamReader reader_Dit = new InputStreamReader(new FileInputStream(filename_Dit), "UTF-8");
			BufferedReader br_Dit = new BufferedReader(reader_Dit);

			String line_Dit = "";

			int dictnumber = 0;

			while ((line_Dit = br_Dit.readLine()) != null)
			{
				dict[dictnumber] = line_Dit;
				dictnumber++;
			}
			br_Dit.close();

			double[] score = new double[ScoreSize];

			File filename_Score = new File(scoreFile);
			InputStreamReader reader_Score = new InputStreamReader(new FileInputStream(filename_Score), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_Score = new BufferedReader(reader_Score); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_Score = "";

			int scorenumber = 0;

			while ((line_Score = br_Score.readLine()) != null)
			{
				score[scorenumber] = Double.parseDouble(line_Score);
				scorenumber++;
			}
			br_Score.close();

			MapUtility_RandomJump mUtil = new MapUtility_RandomJump();
			Map<String, Double> DictScoreMap = new LinkedHashMap<String, Double>();
			for (int i = 0; i < DictSize; i++)
			{
				DictScoreMap.put(dict[i], score[i]);
			}

			Map<String, Double> sortedDictScoreMap = mUtil.sortByComparator(DictScoreMap, DESC);

			Set<String> resultSet = mUtil.findTopKSet(sortedDictScoreMap, k).keySet();

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));
			// FileWriter writer = new FileWriter(outputPath, true);

			Iterator iter1 = resultSet.iterator();

			while (iter1.hasNext())
			{
				writer.write(iter1.next().toString() + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static double[] MatrixA2MutipleVector(String MatrixA1TFile, String MatrixA2File, double[] vector)
	{
		double[] resultVecor = new double[vector.length];
		int[] itemAppearTime = new int[vector.length];

		try
		{
			File filename_MatrixA1T = new File(MatrixA1TFile);
			InputStreamReader reader_MatrixA1T = new InputStreamReader(new FileInputStream(filename_MatrixA1T),
					"UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_MatrixA1T = new BufferedReader(reader_MatrixA1T); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_MatrixA1T = "";

			int itemnumber = 0;

			while ((line_MatrixA1T = br_MatrixA1T.readLine()) != null)
			{
				String[] list = line_MatrixA1T.split("\t\t");
				String[] sep = list[1].split("\t");

				itemAppearTime[itemnumber] = Integer.parseInt(sep[0].trim());
				itemnumber++;
			}
			br_MatrixA1T.close();

			File filename_MatrixA2 = new File(MatrixA2File);
			InputStreamReader reader_MatrixA2 = new InputStreamReader(new FileInputStream(filename_MatrixA2), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_MatrixA2 = new BufferedReader(reader_MatrixA2); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_MatrixA2 = "";

			int linenumber = 0;

			while ((line_MatrixA2 = br_MatrixA2.readLine()) != null)
			{
				String[] list = line_MatrixA2.split("\t\t");
				String[] sep = list[1].split("\t");

				// int lineSize = Integer.parseInt(sep[0].trim());
				double itemValue = 0.0;

				for (int i = 1; i < sep.length; i++)
				{
					itemValue = itemValue
							+ vector[Integer.parseInt(sep[i].trim())] / itemAppearTime[Integer.parseInt(sep[i].trim())];
				}

				resultVecor[linenumber] = itemValue;
				linenumber++;
			}
			br_MatrixA2.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultVecor;
	}

	public static double[] MatrixA1TMutipleVector(String MatrixA1TFile, String MatrixA2File, double[] vector)
	{
		double[] resultVecor = new double[vector.length];
		int[] listSize = new int[vector.length];
		try
		{
			File filename_MatrixA2 = new File(MatrixA2File);
			InputStreamReader reader_MatrixA2 = new InputStreamReader(new FileInputStream(filename_MatrixA2), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_MatrixA2 = new BufferedReader(reader_MatrixA2); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_MatrixA2 = "";

			int listnumber = 0;

			while ((line_MatrixA2 = br_MatrixA2.readLine()) != null)
			{
				String[] list = line_MatrixA2.split("\t\t");
				String[] sep = list[1].split("\t");

				listSize[listnumber] = Integer.parseInt(sep[0].trim());
				listnumber++;
			}
			br_MatrixA2.close();

			File filename_MatrixA1T = new File(MatrixA1TFile);
			InputStreamReader reader_MatrixA1T = new InputStreamReader(new FileInputStream(filename_MatrixA1T),
					"UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_MatrixA1T = new BufferedReader(reader_MatrixA1T); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_MatrixA1T = "";

			int itemnumber = 0;

			while ((line_MatrixA1T = br_MatrixA1T.readLine()) != null)
			{
				String[] list = line_MatrixA1T.split("\t\t");
				String[] sep = list[1].split("\t");

				// int lineSize = Integer.parseInt(sep[0].trim());
				double itemValue = 0.0;

				for (int i = 1; i < sep.length; i++)
				{
					itemValue = itemValue
							+ vector[Integer.parseInt(sep[i].trim())] / listSize[Integer.parseInt(sep[i].trim())];
				}

				resultVecor[itemnumber] = itemValue;
				itemnumber++;
			}
			br_MatrixA1T.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultVecor;
	}

	public static void createMatrixA1(String DictFile, String inputdataPath, String outputPath)
	{
		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			String[] dict = new String[DictSize];

			// File file_Dit = new File(DictFile);
			// BufferedReader reader_Dit = new BufferedReader(new InputStreamReader(new FileInputStream(file_Dit), "UTF-8"));

			File filename_Dit = new File(DictFile);
			InputStreamReader reader_Dit = new InputStreamReader(new FileInputStream(filename_Dit), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_Dit = new BufferedReader(reader_Dit); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_Dit = "";

			line_Dit = br_Dit.readLine();
			int dictnumber = 0;

			while (line_Dit != null)
			{

				dict[dictnumber] = line_Dit;
				dictnumber++;
				line_Dit = br_Dit.readLine();
			}
			br_Dit.close();

			File filename = new File(inputdataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));
			// FileWriter writer = new FileWriter(outputPath, true);

			line = br.readLine();
			// int linenumber = 1;

			while (line != null)
			{

				String[] list = line.split("\t\t");
				String[] sep = list[1].split("\t");

				String newlist = list[0] + "\t\t" + sep.length;

				for (int i = 0; i < sep.length; i++)
				{
					int location = halfSearch(dict, sep[i]);

					/*
					 * for (int j = 0; j < dict.length; j++) { if (sep[i] == dict[j]) { newlist = newlist + "\t" + j; break; } }
					 */
					if (location == -1)
					{
						System.out.println("-1");
					}
					newlist = newlist + "\t" + location;
				}
				writer.write(newlist + "\r\n");

				line = br.readLine(); // 一次读入一行数据
				// linenumber++;
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void findSeedLocation_RandomWalk(String DictFile, String inputdataPath, String outputPath)
	{
		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			String[] dict = new String[DictSize];

			// File file_Dit = new File(DictFile);
			// BufferedReader reader_Dit = new BufferedReader(new InputStreamReader(new FileInputStream(file_Dit), "UTF-8"));

			File filename_Dit = new File(DictFile);
			InputStreamReader reader_Dit = new InputStreamReader(new FileInputStream(filename_Dit), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_Dit = new BufferedReader(reader_Dit); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_Dit = "";

			line_Dit = br_Dit.readLine();
			int dictnumber = 0;

			while (line_Dit != null)
			{
				dict[dictnumber] = line_Dit;
				dictnumber++;
				line_Dit = br_Dit.readLine();
			}
			br_Dit.close();

			File filename = new File(inputdataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));

			while ((line = br.readLine()) != null)
			{
				String[] sep = line.split("\t");

				String newlist = sep.length + "\t";

				for (int i = 0; i < sep.length; i++)
				{
					int location = halfSearch(dict, sep[i]);

					if (location == -1)
					{
						System.out.println("-1");
					}
					newlist = newlist + "\t" + location;
				}
				writer.write(newlist + "\r\n");
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void findSeedLocation(String DictFile, String inputdataPath, String outputPath)
	{
		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			String[] dict = new String[DictSize];

			// File file_Dit = new File(DictFile);
			// BufferedReader reader_Dit = new BufferedReader(new InputStreamReader(new FileInputStream(file_Dit), "UTF-8"));

			File filename_Dit = new File(DictFile);
			InputStreamReader reader_Dit = new InputStreamReader(new FileInputStream(filename_Dit), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_Dit = new BufferedReader(reader_Dit); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line_Dit = "";

			line_Dit = br_Dit.readLine();
			int dictnumber = 0;

			while (line_Dit != null)
			{

				dict[dictnumber] = line_Dit;
				dictnumber++;
				line_Dit = br_Dit.readLine();
			}
			br_Dit.close();

			File filename = new File(inputdataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));
			// FileWriter writer = new FileWriter(outputPath, true);

			line = br.readLine();
			// int linenumber = 1;

			while (line != null)
			{
				String[] sep = line.split("\t");

				String newlist = "000" + "\t\t" + sep.length;

				for (int i = 0; i < sep.length; i++)
				{
					int location = halfSearch(dict, sep[i]);

					/*
					 * for (int j = 0; j < dict.length; j++) { if (sep[i] == dict[j]) { newlist = newlist + "\t" + j; break; } }
					 */
					if (location == -1)
					{
						System.out.println("-1");
					}
					newlist = newlist + "\t" + location;
				}
				writer.write(newlist + "\r\n");

				line = br.readLine(); // 一次读入一行数据
				// linenumber++;
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	static int halfSearch(String a[], String key)
	{
		int low = 0;
		int high = a.length - 1;
		int mid;

		while (low <= high)
		{
			mid = (low + high) / 2;
			if (a[mid].compareTo(key) == 0)
				return mid;
			else if (a[mid].compareTo(key) > 0)
				high = mid - 1;
			else
				low = mid + 1;
		}
		return -1;
	}

	public static void pickDict(String inputdataPath, String outputPath)
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

				String[] sep = line.split("\t\t");
				writer.write(sep[0] + "\r\n");

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

	public static void generatDictFile(String fileName, String outputPath) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

		File file1 = new File(outputPath + "smallterm.txt");
		if (file1.exists())
		{
			file1.delete();
		}

		file1 = new File(outputPath + "smallterm_list.txt");
		if (file1.exists())
		{
			file1.delete();
		}

		BufferedWriter outFile_term = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "smallterm.txt"), "UTF-8"));

		BufferedWriter outFile_term_listID = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputPath + "smallterm_list.txt"), "UTF-8"));

		// PrintWriter outFile_term = new PrintWriter(new FileWriter(outputPath + "smallterm.txt", true));
		// PrintWriter outFile_term_listID = new PrintWriter(new FileWriter(outputPath + "smallterm_list.txt", true));
		int k = 0;

		while (iterator.hasNext())
		{
			String line = iterator.nextLine();
			String values[] = line.split("\t\t");
			String listId = values[0];
			String terms = values[1];
			String termVal[] = terms.split("\t");
			for (int i = 0; i < termVal.length; i++)
			{
				String termToWrite = termVal[i];
				outFile_term.write(termToWrite + "\r\n");
				outFile_term.flush();

				String termToWrite_term_listID = termVal[i] + "\t\t" + listId;
				outFile_term_listID.write(termToWrite_term_listID + "\r\n");
				outFile_term_listID.flush();
			}
			k = k + 1;
			// System.out.println("Finished line no:" + k);
		}
	}

	/**
	 * 将原始数据分成几块 并排序 再保存到临时文件
	 * 
	 * @throws IOException
	 */
	public static void splitData(String originalData, int TEMPFILELENGTH, String TEMPFILEPATH) throws IOException
	{
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(originalData));

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

			copySubdataset(originalData, outPath, starline, endline);
		}
	}

	public static void multiFileSort(String TEMPFILEPATH, String SORTEDFILEPATH) throws IOException
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

	public static void rewirteDataset(String inputdata, String outputdata)
	{
		try
		{
			File filename = new File(inputdata);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputdata), "UTF-8"));

			int listNumber = 0;
			while ((line = br.readLine()) != null)
			{
				String[] list = line.split("\t\t");
				writer.write(listNumber + "\t\t" + list[1] + "\r\n");
				listNumber++;
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
