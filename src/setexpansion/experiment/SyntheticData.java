package setexpansion.experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import setexpansion.test.RankedSE;
import setexpansion.test.UtiForCreateDataset;

public class SyntheticData
{

	public static void main(String[] args) throws Exception
	{
		String stage = "ave—2017-10";

		// 2017-10-25 String dataFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\list\\list_ConceptSet_200_81_100_0.6__20_0.8_.txt";
		String dataFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\list\\SmallSizeConcept\\list_ConceptSet_200_66_75_0.2__6_0.8_.txt";

		String seedsPath = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\";

		// String seedsFile = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\seed.txt";

		// ******************stage one: create concepts**********************

		if (stage == "createRepeatingConceptSet")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			createRepeatingConceptSet(200, 81, 100, 0.2, DataPath, 0.1, 10.1, 1000, 1);
			return;
		}

		if (stage == "createConceptSet_withRepeat")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			createConceptSet_withRepeat(200, 81, 100, 0.6, DataPath);
			return;
		}

		// 2017-10-25 copy
		if (stage == "createConceptSet")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			createConceptSet(200, 81, 100, 0.2, DataPath);
			return;
		}

		// 2017-10-25
		if (stage == "createConceptSet—2017-10")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			createConceptSet(200, 66, 75, 0.2, DataPath);
			return;
		}

		// ******************stage two: create samples**********************

		if (stage == "createSamples")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			createSamples("ConceptSet_200_81_100_0.6_", 20, 0.8, DataPath);
			return;
		}

		// 2017-10-25
		if (stage == "createSamples—2017-10")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			createSamples("ConceptSet_200_66_75_0.2_", 6, 0.8, DataPath);
			return;
		}
		// ******************stage three: get dictionary **********************

		if (stage == "getDict")
		{

			// 2017-10-25 String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\dict\\";
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\dict\\SmallSizeConcept\\";

			getDict(dataFile, DataPath);
			return;
		}

		// ******************stage four: get random seeds **********************

		if (stage == "randomSeedsList")
		{

			String conceptFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\ConceptSet_200_81_100_0.6_\\C000.txt";

			String dictFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\dict\\list_ConceptSet_200_81_100_0.6__20_0.8_.txt";

			String outputFile = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\ConceptSet_200_81_100_0.6__20_0.8__seedsList_7_100.txt";

			randomSeedsList(7, 100, conceptFile, dictFile, outputFile);
			return;
		}

		if (stage == "randomSeedsList—2017-10")
		{

			String conceptFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\SmallSizeConcept\\ConceptSet_200_66_75_0.2_\\C000.txt";

			String dictFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\dict\\SmallSizeConcept\\list_ConceptSet_200_66_75_0.2__6_0.8_.txt";

			String outputFile = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\ConceptSet_200_66_75_0.2__6_0.8__seedsList_7_100.txt";

			randomSeedsList(7, 100, conceptFile, dictFile, outputFile);
			return;
		}

		// ******************stage five: get random seeds **********************

		if (stage == "three—2017-10")
		{
			String dataFile_new = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\list\\SmallSizeConcept\\list_ConceptSet_200_66_75_0.2__6_0.8_.txt";
			String seedsPath_new = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\SmallSizeConcept\\";

			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\SmallSizeConcept\\";
			String SeedNumber = "7";

			SEISA.sEISA_withSeedNumber(dataFile_new, workPath, seedsPath_new, SeedNumber);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\SmallSizeConcept\\";

			RankedSE.rankedSE_withSeedNumber(dataFile_new, workPath, seedsPath_new, 20, SeedNumber);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\SmallSizeConcept\\";

			RandomWalk.randomJump_withSeedNumber(dataFile_new, workPath, seedsPath_new, 20, SeedNumber);
			return;
		}

		if (stage == "three")
		{
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\SmallSizeConcept\\";

			SEISA.sEISA(dataFile, workPath, seedsPath);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\SmallSizeConcept\\";

			RankedSE.rankedSE(dataFile, workPath, seedsPath, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\SmallSizeConcept\\";

			RandomWalk.randomJump(dataFile, workPath, seedsPath, 20);
			return;
		}

		// ******************stage six: get random seeds **********************

		if (stage == "ave—2017-10")
		{
			String dirName = "list_ConceptSet_200_66_75_0.2__6_0.8_7" + "\\";
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\SmallSizeConcept\\" + dirName;

			String conceptFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\SmallSizeConcept\\ConceptSet_200_66_75_0.2_\\C000.txt";

			calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			calAverageMAP(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\SmallSizeConcept\\" + dirName;

			calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			calAverageMAP(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\SmallSizeConcept\\" + dirName;

			calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			calAverageMAP(workPath, conceptFile, 20);

			return;
		}

		if (stage == "newave")
		{
			String dirName = "list_ConceptSet_200_81_100_0.2__20_0.8_3" + "\\";
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\conceptOvlp_new\\" + dirName;

			String conceptFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\ConceptSet_200_81_100_0.2_\\C000.txt";

			// calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\conceptOvlp_new\\" + dirName;

			// calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\conceptOvlp_new\\" + dirName;

			// calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			return;
		}

		if (stage == "ave")
		{
			String dirName = "list_ConceptSet_200_81_100_0.6__20_0.8_7" + "\\";
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\conceptOvlp_new\\" + dirName;

			String conceptFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\ConceptSet_200_81_100_0.6_\\C000.txt";

			calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\conceptOvlp_new\\" + dirName;

			calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\conceptOvlp_new\\" + dirName;

			calAverageRunningTime(workPath);

			calAverageMRR_new(workPath, conceptFile, 20);

			return;
		}

		// ******************stage seven: get random seeds **********************
		if (stage == "MAP")// 2017-10-18
		{
			String dirName = "list_ConceptSet_200_81_100_0.2__30_0.8_11" + "\\";

			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\sampleNumber\\" + dirName;

			String conceptFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\sampleNumber\\ConceptSet_200_81_100_0.2_30\\C000.txt";

			// calAverageRunningTime(workPath);

			calAverageMAP(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\sampleNumber\\" + dirName;

			// calAverageRunningTime(workPath);

			calAverageMAP(workPath, conceptFile, 20);

			workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\sampleNumber\\" + dirName;

			// calAverageRunningTime(workPath);

			calAverageMAP(workPath, conceptFile, 20);

			return;
		}

		// ******************others **********************

		if (stage == "syntheticData")
		{
			String DataPath = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\";

			syntheticData(100, 20, 0.8, DataPath);
			return;
		}

		if (stage == "SEISA")
		{
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\SEISA\\";

			SEISA.sEISA(dataFile, workPath, seedsPath);
			return;
		}

		if (stage == "RankedSE")
		{
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\RankedSE\\";

			RankedSE.rankedSE(dataFile, workPath, seedsPath, 20);
			return;
		}

		if (stage == "RandomJump")
		{
			String workPath = "D:\\CodePath\\dataset\\SyntheticData\\RandomJump\\";

			RandomWalk.randomJump(dataFile, workPath, seedsPath, 20);
			return;
		}
	}

	public static void calAverageMAP(String workPath, String conceptFile, int topK)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "AveMAP_new.txt"), "UTF-8"));

			BufferedWriter writer_single = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "singleMAP_new.txt"), "UTF-8"));

			int conceptSize = UtiForCreateDataset.getDataLineSize(conceptFile);

			File filename_concept = new File(conceptFile);
			InputStreamReader reader_concept = new InputStreamReader(new FileInputStream(filename_concept), "UTF-8");
			BufferedReader br_concept = new BufferedReader(reader_concept);

			String line_concept = "";

			String[] concept = new String[conceptSize];

			int index = 0;

			while ((line_concept = br_concept.readLine()) != null)
			{
				concept[index] = line_concept;
				index++;
			}
			br_concept.close();

			File filename_list = new File(workPath + "resultlist.txt");
			InputStreamReader reader_list = new InputStreamReader(new FileInputStream(filename_list), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_list = new BufferedReader(reader_list); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			int loopNumber = 0;

			int singleNumber = 0;

			double totalMAP = 0;

			double singeleAP = 0;

			String[] topString = new String[topK];

			for (int i = 0; i < topK; i++)
			{
				topString[i] = "-1";
			}

			int firstFlag = 0;

			int findFlag = 0;

			int startLoop = 0;
			while ((line = br_list.readLine()) != null)
			{
				if (line.indexOf("**") > -1)
				{
					if (firstFlag == 0)
					{
						firstFlag = 1;
					} else
					{
						for (int i = 0; i < topK; i++)
						{
							if (!(topString[i].equals("-1")))
							{
								for (int con = 0; con < concept.length; con++)
								{
									if (topString[i].equals(concept[con]))
									{
										findFlag = findFlag + 1;

										double temp = findFlag * 1.0 / (i + 1.0);

										singeleAP = singeleAP + temp;

										singleNumber++;
										break;
									}
								}

							} else
							{
								break;
							}
						}

						double oneTime = 0.0;
						if (!Double.isNaN(singeleAP))
						{
							oneTime = singeleAP / findFlag;
						}

						totalMAP = totalMAP + oneTime;

						writer_single.write(oneTime + "\r\n");
						writer_single.flush();

						loopNumber++;
						singeleAP = 0;
						findFlag = 0;
						singleNumber = 0;
						startLoop = 0;

						for (int j = 0; j < topK; j++)
						{
							topString[j] = "-1";
						}

					}
				} else
				{
					topString[startLoop] = line;
					startLoop++;
				}
			}

			for (int i = 0; i < topK; i++)
			{
				if (!(topString[i].equals("-1")))
				{
					for (int con = 0; con < concept.length; con++)
					{
						if (topString[i].equals(concept[con]))
						{
							findFlag = findFlag + 1;

							double temp = findFlag * 1.0 / (i + 1.0);

							singeleAP = singeleAP + temp;

							singleNumber++;
							break;
						}
					}

				} else
				{

					double oneTime = 0.0;
					if (!Double.isNaN(singeleAP))
					{
						oneTime = singeleAP / findFlag;
						;
					}

					totalMAP = totalMAP + oneTime;
					loopNumber++;

					writer_single.write(oneTime + "\r\n");
					writer_single.flush();
				}
			}

			br_list.close();

			writer_single.close();

			writer.write(totalMAP / loopNumber + "\r\n");

			writer.flush();
			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void calAverageMRR_new(String workPath, String conceptFile, int topK)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "AveMRR_new.txt"), "UTF-8"));

			BufferedWriter writer_single = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "singleMRR_new.txt"), "UTF-8"));

			int conceptSize = UtiForCreateDataset.getDataLineSize(conceptFile);

			File filename_concept = new File(conceptFile);
			InputStreamReader reader_concept = new InputStreamReader(new FileInputStream(filename_concept), "UTF-8");
			BufferedReader br_concept = new BufferedReader(reader_concept);

			String line_concept = "";

			String[] concept = new String[conceptSize];

			int index = 0;

			while ((line_concept = br_concept.readLine()) != null)
			{
				concept[index] = line_concept;
				index++;
			}
			br_concept.close();

			File filename_list = new File(workPath + "resultlist.txt");
			InputStreamReader reader_list = new InputStreamReader(new FileInputStream(filename_list), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_list = new BufferedReader(reader_list); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			int loopNumber = 0;

			int singleNumber = 0;

			double totalMRR = 0;

			double singeleMRR = 0;

			String[] topString = new String[topK];

			for (int i = 0; i < topK; i++)
			{
				topString[i] = "-1";
			}

			int firstFlag = 0;

			int startLoop = 0;
			while ((line = br_list.readLine()) != null)
			{
				if (line.indexOf("**") > -1)
				{
					if (firstFlag == 0)
					{
						firstFlag = 1;
					} else
					{
						for (int i = 0; i < topK; i++)
						{
							if (!(topString[i].equals("-1")))
							{
								for (int con = 0; con < concept.length; con++)
								{
									if (topString[i].equals(concept[con]))
									{
										double temp = 1.0 / (i + 1.0);
										singeleMRR = singeleMRR + temp;

										singleNumber++;
										break;
									}
								}

							} else
							{
								break;
							}
						}

						double oneTime = 0.0;
						if (!Double.isNaN(singeleMRR))
						{
							oneTime = singeleMRR;
						}

						totalMRR = totalMRR + oneTime;

						writer_single.write(oneTime + "\r\n");
						writer_single.flush();

						loopNumber++;
						singeleMRR = 0;
						singleNumber = 0;
						startLoop = 0;

						for (int j = 0; j < topK; j++)
						{
							topString[j] = "-1";
						}

					}
				} else
				{
					topString[startLoop] = line;
					startLoop++;
				}
			}

			for (int i = 0; i < topK; i++)
			{
				if (!(topString[i].equals("-1")))
				{
					for (int con = 0; con < concept.length; con++)
					{
						if (topString[i].equals(concept[con]))
						{
							double temp = 1.0 / (i + 1.0);
							singeleMRR = singeleMRR + temp;
							singleNumber++;
							break;
						}
					}

				} else
				{

					double oneTime = 0.0;
					if (!Double.isNaN(singeleMRR))
					{
						oneTime = singeleMRR;
					}

					totalMRR = totalMRR + oneTime;
					loopNumber++;

					writer_single.write(oneTime + "\r\n");
					writer_single.flush();
				}
			}

			br_list.close();

			writer_single.close();

			writer.write(totalMRR / loopNumber + "\r\n");

			writer.flush();
			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void calAverageMRR(String workPath, String conceptFile, int topK)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "AveMRR.txt"), "UTF-8"));

			BufferedWriter writer_single = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "singleMRR.txt"), "UTF-8"));

			int conceptSize = UtiForCreateDataset.getDataLineSize(conceptFile);

			File filename_concept = new File(conceptFile);
			InputStreamReader reader_concept = new InputStreamReader(new FileInputStream(filename_concept), "UTF-8");
			BufferedReader br_concept = new BufferedReader(reader_concept);

			String line_concept = "";

			String[] concept = new String[conceptSize];

			int index = 0;

			while ((line_concept = br_concept.readLine()) != null)
			{
				concept[index] = line_concept;
				index++;
			}
			br_concept.close();

			File filename_list = new File(workPath + "resultlist.txt");
			InputStreamReader reader_list = new InputStreamReader(new FileInputStream(filename_list), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_list = new BufferedReader(reader_list); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			int loopNumber = 0;

			int singleNumber = 0;

			double totalMRR = 0;

			double singeleMRR = 0;

			String[] topString = new String[topK];

			for (int i = 0; i < topK; i++)
			{
				topString[i] = "-1";
			}

			int firstFlag = 0;

			int startLoop = 0;
			while ((line = br_list.readLine()) != null)
			{
				if (line.indexOf("**") > -1)
				{
					if (firstFlag == 0)
					{
						firstFlag = 1;
					} else
					{
						for (int i = 0; i < topK; i++)
						{
							if (!(topString[i].equals("-1")))
							{
								for (int con = 0; con < concept.length; con++)
								{
									if (topString[i].equals(concept[con]))
									{
										singeleMRR = singeleMRR + 1 / (i + 1);

										singleNumber++;
										break;
									}
								}

							} else
							{
								break;
							}
						}

						double oneTime = 0.0;
						if (!Double.isNaN(singeleMRR / singleNumber))
						{
							oneTime = singeleMRR / singleNumber;
						}

						totalMRR = totalMRR + oneTime;

						writer_single.write(oneTime + "\r\n");
						writer_single.flush();

						loopNumber++;
						singeleMRR = 0;
						singleNumber = 0;
						startLoop = 0;

						for (int j = 0; j < topK; j++)
						{
							topString[j] = "-1";
						}

					}
				} else
				{
					topString[startLoop] = line;
					startLoop++;
				}
			}

			for (int i = 0; i < topK; i++)
			{
				if (!(topString[i].equals("-1")))
				{
					for (int con = 0; con < concept.length; con++)
					{
						if (topString[i].equals(concept[con]))
						{
							singeleMRR = singeleMRR + 1 / (i + 1);
							singleNumber++;
							break;
						}
					}

				} else
				{
					totalMRR = totalMRR + singeleMRR / singleNumber;
					loopNumber++;
				}
			}

			br_list.close();

			writer_single.close();

			writer.write(totalMRR / loopNumber + "\r\n");

			writer.flush();
			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void calAverageRunningTime(String workPath)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(workPath + "AveRunningTime.txt"), "UTF-8"));

			File filename_list = new File(workPath + "runningtime.txt");
			InputStreamReader reader_list = new InputStreamReader(new FileInputStream(filename_list), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_list = new BufferedReader(reader_list); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			int loopNumber = 0;

			double totalTime = 0;

			while ((line = br_list.readLine()) != null)
			{
				totalTime = totalTime + Integer.parseInt(line);
				loopNumber++;
			}
			br_list.close();

			writer.write(totalTime / loopNumber + "\r\n");

			writer.flush();
			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void getDict(String sampleListFile, String DictPath)
	{
		try
		{

			String filename = sampleListFile.substring(sampleListFile.lastIndexOf("\\") + 1);

			filename = DictPath + filename;

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));

			File filename_list = new File(sampleListFile);
			InputStreamReader reader_list = new InputStreamReader(new FileInputStream(filename_list), "UTF-8"); // 建立一个输入流对象reader
			BufferedReader br_list = new BufferedReader(reader_list); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String line = "";

			Set words = new HashSet();

			while ((line = br_list.readLine()) != null)
			{
				String[] list = line.split("\t\t");
				String[] sep_temp = list[1].split("\t");

				for (int i = 0; i < sep_temp.length; i++)
				{
					words.add(sep_temp[i]);
				}
			}
			br_list.close();

			String[] setString = new String[words.size()];
			words.toArray(setString);

			for (int i = 0; i < setString.length; i++)
			{
				writer.write(setString[i] + "\r\n");
			}
			writer.flush();
			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void randomSeedsList_forGeneralData(int SeedSize, int seedsNumber, String seedFile, String outputFile)
	{
		UtiForCreateDataset Util = new UtiForCreateDataset();

		int seedSize = Util.getDataLineSize(seedFile);

		try
		{
			File filename_seed = new File(seedFile);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed), "UTF-8");
			BufferedReader br_seed = new BufferedReader(reader_seed);

			String[] seeds = new String[seedSize];
			String line_seed = "";
			int Seednumber = 0;

			Set<String> words = new HashSet<String>();

			while ((line_seed = br_seed.readLine()) != null)
			{
				seeds[Seednumber] = line_seed;
				Seednumber++;
				words.add(line_seed);
			}
			br_seed.close();

			String[] setString = new String[words.size()];
			words.toArray(setString);

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));

			for (int lineNo = 0; lineNo < seedsNumber; lineNo++)
			{
				Set randomNItem = randomN(setString.length, SeedSize);

				String[] repeatItemString = new String[randomNItem.size()];
				randomNItem.toArray(repeatItemString);

				String lineString = setString[Integer.parseInt(repeatItemString[0])];

				for (int item = 1; item < repeatItemString.length; item++)
				{
					lineString = lineString + "\t" + setString[Integer.parseInt(repeatItemString[item])];

				}
				writer.write(lineString + "\r\n");

				randomNItem = null;
				repeatItemString = null;
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void randomSeedsList(int SeedSize, int seedsNumber, String seedFile, String dictFile,
			String outputFile)
	{
		UtiForCreateDataset Util = new UtiForCreateDataset();

		int seedSize = Util.getDataLineSize(seedFile);

		int dictSize = Util.getDataLineSize(dictFile);
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

			File filename_dict = new File(dictFile);
			InputStreamReader reader_dict = new InputStreamReader(new FileInputStream(filename_dict), "UTF-8");
			BufferedReader br_dict = new BufferedReader(reader_dict);

			String[] dict = new String[dictSize];
			String line_dict = "";
			int number_dict = 0;

			while ((line_dict = br_dict.readLine()) != null)
			{
				dict[number_dict] = line_dict;
				number_dict++;
			}
			br_dict.close();

			Set<String> words = new HashSet<String>();

			// int flag = 0;

			for (int seedItem = 0; seedItem < seeds.length; seedItem++)
			{
				for (int dictItem = 0; dictItem < dict.length; dictItem++)
				{
					if (seeds[seedItem].equals(dict[dictItem]))
					{
						words.add(seeds[seedItem]);
						// flag++;
						break;
					}
				}
			}
			// System.out.println(flag);

			String[] setString = new String[words.size()];
			words.toArray(setString);

			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));

			for (int lineNo = 0; lineNo < seedsNumber; lineNo++)
			{
				Set randomNItem = randomN(setString.length, SeedSize);

				String[] repeatItemString = new String[randomNItem.size()];
				randomNItem.toArray(repeatItemString);

				String lineString = setString[Integer.parseInt(repeatItemString[0])];

				for (int item = 1; item < repeatItemString.length; item++)
				{
					lineString = lineString + "\t" + setString[Integer.parseInt(repeatItemString[item])];

				}
				writer.write(lineString + "\r\n");

				randomNItem = null;
				repeatItemString = null;
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String intToNumber(int Oint, int length)
	{
		if (Integer.toString(Oint).length() <= length)
		{
			String result = "";
			for (int i = 0; i < (length - Integer.toString(Oint).length()); i++)
			{
				result = "0" + result;
			}
			result = result + Integer.toString(Oint);
			return result;
		}
		return "lengtherror";
	}

	public static Set randomN(int length, int N)
	{
		Set result = new HashSet();

		Random random = new Random();

		while (result.size() < N)
		{
			result.add(Integer.toString(random.nextInt(length)));
		}
		return result;
	}

	public static void createConceptSet_basicSet(int SetSize, int minSize, int maxSize, double COvlp, String DataPath)
	{
		try
		{
			String suffix = "ConceptSet_" + SetSize + "_" + minSize + "_" + maxSize + "_" + COvlp + "_";

			createDir(DataPath + "concept");

			DataPath = DataPath + "concept" + "\\";

			createDir(DataPath + suffix);

			DataPath = DataPath + suffix + "\\";

			Set dict = new HashSet();

			for (int conceptFile = 0; conceptFile < SetSize; conceptFile++)
			{
				String conceptFileName = DataPath + "C" + intToNumber(conceptFile, 3) + ".txt";
				BufferedWriter writer_concept = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(conceptFileName), "UTF-8"));

				Random random = new Random();
				int conceptSize = random.nextInt(maxSize - minSize + 1) + minSize;

				String ItemSuffix = "C" + intToNumber(conceptFile, 3);
				if (conceptFile == 0)
				{
					for (int item = 0; item < conceptSize; item++)
					{
						String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
						writer_concept.write(currentItem + "\r\n");
					}
				} else
				{
					int unique = (int) (conceptSize * (1 - COvlp));
					int repeat = conceptSize - unique;

					for (int item = 0; item < unique; item++)
					{
						String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
						writer_concept.write(currentItem + "\r\n");
					}

					int[] repeatItem = new int[repeat];

					if (repeat > dict.size())
					{
						System.out.println("error number!");
						return;
					}

					String[] setString = new String[dict.size()];
					dict.toArray(setString);

					Set randomNItem = randomN(setString.length, repeat);

					String[] repeatItemString = new String[randomNItem.size()];
					randomNItem.toArray(repeatItemString);

					for (int item = 0; item < repeatItemString.length; item++)
					{
						String currentItem = setString[Integer.parseInt(repeatItemString[item])];
						writer_concept.write(currentItem + "\r\n");
					}
					setString = null;
					repeatItemString = null;
				}

				writer_concept.flush();
				writer_concept.close();

				File filename_set = new File(conceptFileName);
				InputStreamReader reader_set = new InputStreamReader(new FileInputStream(filename_set), "UTF-8");
				BufferedReader br_set = new BufferedReader(reader_set);

				String line_set = "";

				while ((line_set = br_set.readLine()) != null)
				{
					dict.add(line_set);
				}
				br_set.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void createConceptSet_distribution(int SetSize, int minSize, int maxSize, double COvlp,
			String DataPath)
	{
		try
		{
			String suffix = "ConceptSet_" + SetSize + "_" + minSize + "_" + maxSize + "_" + COvlp + "_";

			createDir(DataPath + "concept");

			DataPath = DataPath + "concept" + "\\";

			createDir(DataPath + suffix);

			DataPath = DataPath + suffix + "\\";

			Set dict = new HashSet();

			for (int conceptFile = 0; conceptFile < SetSize; conceptFile++)
			{
				String conceptFileName = DataPath + "C" + intToNumber(conceptFile, 3) + ".txt";
				BufferedWriter writer_concept = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(conceptFileName), "UTF-8"));

				Random random = new Random();
				int conceptSize = random.nextInt(maxSize - minSize + 1) + minSize;

				String ItemSuffix = "C" + intToNumber(conceptFile, 3);
				if (conceptFile == 0)
				{
					for (int item = 0; item < conceptSize; item++)
					{
						String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
						writer_concept.write(currentItem + "\r\n");
					}
				} else
				{
					int unique = (int) (conceptSize * (1 - COvlp));
					int repeat = conceptSize - unique;

					for (int item = 0; item < unique; item++)
					{
						String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
						writer_concept.write(currentItem + "\r\n");
					}

					int[] repeatItem = new int[repeat];

					if (repeat > dict.size())
					{
						System.out.println("error number!");
						return;
					}

					String[] setString = new String[dict.size()];
					dict.toArray(setString);

					Set randomNItem = randomN(setString.length, repeat);

					String[] repeatItemString = new String[randomNItem.size()];
					randomNItem.toArray(repeatItemString);

					for (int item = 0; item < repeatItemString.length; item++)
					{
						String currentItem = setString[Integer.parseInt(repeatItemString[item])];
						writer_concept.write(currentItem + "\r\n");
					}
					setString = null;
					repeatItemString = null;
				}

				writer_concept.flush();
				writer_concept.close();

				File filename_set = new File(conceptFileName);
				InputStreamReader reader_set = new InputStreamReader(new FileInputStream(filename_set), "UTF-8");
				BufferedReader br_set = new BufferedReader(reader_set);

				String line_set = "";

				while ((line_set = br_set.readLine()) != null)
				{
					dict.add(line_set);
				}
				br_set.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static double Powerfunction(int power, double input)
	{
		double tem = 1.0;

		for (int i = 0; i < power; i++)
		{
			tem = tem * input;
		}

		if (tem > 0)
		{
			return 1 / tem;
		} else
		{
			return 0;
		}

	}

	public static void createRepeatingConceptSet(int SetSize, int minSize, int maxSize, double COvlp, String DataPath,
			double min, double max, int ItemNumber, int Power)
	{
		try
		{
			String suffix = "ConceptSet_repeating_" + SetSize + "_" + minSize + "_" + maxSize + "_" + COvlp + "_";

			createDir(DataPath + "conceptRepeating");

			DataPath = DataPath + "conceptRepeating" + "\\";

			// createDir(DataPath + suffix);

			// DataPath = DataPath + suffix + "\\";

			double[] frequency = new double[ItemNumber];

			double gap = (max - min) / ItemNumber;

			double total = 0.0;

			for (int i = 0; i < ItemNumber; i++)
			{
				frequency[i] = Powerfunction(Power, min + i * gap);
				total = total + frequency[i];
			}

			for (int i = 0; i < ItemNumber; i++)
			{
				frequency[i] = frequency[i] / total;
			}

			double localTotal = 0.0;
			for (int i = 0; i < ItemNumber; i++)
			{
				localTotal = localTotal + frequency[i];
				frequency[i] = localTotal;
			}

			String conceptRFileName = DataPath + suffix + ".txt";
			BufferedWriter writer_conceptR = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(conceptRFileName), "UTF-8"));

			for (int i = 0; i < ItemNumber; i++)
			{
				String currentItem = "R" + intToNumber(i, 7);

				currentItem = currentItem + "\t\t" + frequency[i];

				writer_conceptR.write(currentItem + "\r\n");
			}

			writer_conceptR.flush();
			writer_conceptR.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String[] GetRandomN(double[] freq, int N)
	{
		Set<String> result = new HashSet();

		Random rand = new Random();

		// Random random = new Random();

		while (result.size() < N)
		{
			double num = rand.nextDouble();

			if (num < freq[0])
			{
				result.add("0");
			} else
			{
				for (int i = 1; i < freq.length; i++)
				{
					if ((num < freq[i]) && (num > freq[i - 1]))
					{
						result.add(Integer.toString(i));
					}
				}
			}
		}

		String[] res = new String[N];

		result.toArray(res);

		return res;
	}

	public static void createConceptSet_withRepeat(int SetSize, int minSize, int maxSize, double COvlp, String DataPath)
	{
		try
		{
			String DataPath_repeating = DataPath + "conceptRepeating" + "\\";

			String suffix_repeating = "ConceptSet_repeating_" + SetSize + "_" + minSize + "_" + maxSize + "_" + COvlp
					+ "_" + ".txt";

			DataPath_repeating = DataPath_repeating + suffix_repeating;

			String suffix = "ConceptSet_" + SetSize + "_" + minSize + "_" + maxSize + "_" + COvlp + "_";

			createDir(DataPath + "concept");

			DataPath = DataPath + "concept" + "\\";

			createDir(DataPath + suffix);

			DataPath = DataPath + suffix + "\\";

			UtiForCreateDataset Util = new UtiForCreateDataset();

			int repeatingItemSize = Util.getDataLineSize(DataPath_repeating);

			String repeatingItem[] = new String[repeatingItemSize];
			double repeatingFreq[] = new double[repeatingItemSize];

			File filename_repeating = new File(DataPath_repeating);
			InputStreamReader reader_repeating = new InputStreamReader(new FileInputStream(filename_repeating));
			BufferedReader br_repeating = new BufferedReader(reader_repeating);

			String line = "";

			int lineNumber = 0;

			while ((line = br_repeating.readLine()) != null)
			{

				String[] sep = line.split("\t\t");

				double times = Double.parseDouble(sep[1]);

				repeatingItem[lineNumber] = sep[0];
				repeatingFreq[lineNumber] = times;

				lineNumber++;
			}
			br_repeating.close();

			// Set dict = new HashSet();

			for (int conceptFile = 0; conceptFile < SetSize; conceptFile++)
			{
				String conceptFileName = DataPath + "C" + intToNumber(conceptFile, 3) + ".txt";
				BufferedWriter writer_concept = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(conceptFileName), "UTF-8"));

				Random random = new Random();
				int conceptSize = random.nextInt(maxSize - minSize + 1) + minSize;

				String ItemSuffix = "C" + intToNumber(conceptFile, 3);

				int unique = (int) (conceptSize * (1 - COvlp));
				int repeat = conceptSize - unique;

				for (int item = 0; item < unique; item++)
				{
					String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
					writer_concept.write(currentItem + "\r\n");
				}

				int[] repeatItem = new int[repeat];

				// String[] setString = new String[dict.size()];
				// dict.toArray(setString);

				// Set randomNItem = GetRandomN(repeatingFreq, repeat);

				// String[] repeatItemString = new String[randomNItem.size()];
				// randomNItem.toArray(repeatItemString);

				String[] repeatItemString = GetRandomN(repeatingFreq, repeat);

				for (int item = 0; item < repeatItemString.length; item++)
				{
					String currentItem = repeatingItem[Integer.parseInt(repeatItemString[item])];
					writer_concept.write(currentItem + "\r\n");
				}

				writer_concept.flush();
				writer_concept.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void createConceptSet(int SetSize, int minSize, int maxSize, double COvlp, String DataPath)
	{
		try
		{
			String suffix = "ConceptSet_" + SetSize + "_" + minSize + "_" + maxSize + "_" + COvlp + "_";

			// 2017-10-25 createDir(DataPath + "concept");

			DataPath = DataPath + "concept" + "\\SmallSizeConcept\\";

			createDir(DataPath + suffix);

			DataPath = DataPath + suffix + "\\";

			Set dict = new HashSet();

			for (int conceptFile = 0; conceptFile < SetSize; conceptFile++)
			{
				String conceptFileName = DataPath + "C" + intToNumber(conceptFile, 3) + ".txt";
				BufferedWriter writer_concept = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(conceptFileName), "UTF-8"));

				Random random = new Random();
				int conceptSize = random.nextInt(maxSize - minSize + 1) + minSize;

				String ItemSuffix = "C" + intToNumber(conceptFile, 3);
				if (conceptFile == 0)
				{
					for (int item = 0; item < conceptSize; item++)
					{
						String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
						writer_concept.write(currentItem + "\r\n");
					}
				} else
				{
					int unique = (int) (conceptSize * (1 - COvlp));
					int repeat = conceptSize - unique;

					for (int item = 0; item < unique; item++)
					{
						String currentItem = ItemSuffix + "I" + intToNumber(item, 3);
						writer_concept.write(currentItem + "\r\n");
					}

					int[] repeatItem = new int[repeat];

					if (repeat > dict.size())
					{
						System.out.println("error number!");
						return;
					}

					String[] setString = new String[dict.size()];
					dict.toArray(setString);

					Set randomNItem = randomN(setString.length, repeat);

					String[] repeatItemString = new String[randomNItem.size()];

					randomNItem.toArray(repeatItemString);

					for (int item = 0; item < repeatItemString.length; item++)
					{
						String currentItem = setString[Integer.parseInt(repeatItemString[item])];
						writer_concept.write(currentItem + "\r\n");
					}
					setString = null;
					repeatItemString = null;
				}

				writer_concept.flush();
				writer_concept.close();

				File filename_set = new File(conceptFileName);
				InputStreamReader reader_set = new InputStreamReader(new FileInputStream(filename_set), "UTF-8");
				BufferedReader br_set = new BufferedReader(reader_set);

				String line_set = "";

				while ((line_set = br_set.readLine()) != null)
				{
					dict.add(line_set);
				}
				br_set.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void createSamples(String conceptFileSuffix, int SampleNO, double SOvlp, String DataPath)
	{
		try
		{
			String suffix = "_" + conceptFileSuffix + "_" + SampleNO + "_" + SOvlp + "_.txt";

			// 2017-10-25 createDir(DataPath + "list");

			String listFile = DataPath + "list\\SmallSizeConcept\\list" + suffix;
			BufferedWriter writer_output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(listFile), "UTF-8"));

			String conceptFilePath = DataPath + "concept\\SmallSizeConcept\\" + conceptFileSuffix + "\\";

			String conceptFile[] = SEISA.getTempFiles_total(conceptFilePath);

			int totalIndex = 0;

			for (int i = 0; i < conceptFile.length; i++)
			{
				int cSize = UtiForCreateDataset.getDataLineSize(conceptFile[i]);

				File filename_concept = new File(conceptFile[i]);
				InputStreamReader reader_concept = new InputStreamReader(new FileInputStream(filename_concept),
						"UTF-8");
				BufferedReader br_concept = new BufferedReader(reader_concept);

				String line_concept = "";

				String[] item = new String[cSize];
				int itemIndex = 0;

				while ((line_concept = br_concept.readLine()) != null)
				{
					item[itemIndex] = line_concept.trim();
					itemIndex++;
				}
				br_concept.close();

				int cItemNO = cSize / SampleNO;
				int addItemNo = (int) ((cSize) * SOvlp / (SampleNO));
				int sampleSize = cItemNO + addItemNo;

				String[][] list = new String[SampleNO][sampleSize];

				int addItem = 0;
				for (int list_i = 0; list_i < SampleNO; list_i++)
				{
					for (int item_i = 0; item_i < cItemNO; item_i++)
					{
						list[list_i][item_i] = item[addItem];
						addItem++;
					}

					Random random = new Random();
					int noiseItem = 0;
					while (noiseItem < addItemNo)
					{
						int flag = 0;
						while (flag == 0)
						{
							int next = random.nextInt(cSize);

							int count = 0;

							for (int item_j = 0; item_j < cItemNO; item_j++)
							{
								if (item[next] != list[list_i][item_j])
								{
									count++;
								}
							}

							if (count == cItemNO)
							{
								flag = 1;
								list[list_i][cItemNO + noiseItem] = item[next];
								noiseItem++;
							}
						}
					}
				}

				for (int list_i = 0; list_i < SampleNO; list_i++)
				{
					int lineNumber = totalIndex + list_i;
					String line = "" + lineNumber + "\t";
					for (int item_i = 0; item_i < sampleSize; item_i++)
					{
						line = line + "\t" + list[list_i][item_i];
					}
					writer_output.write(line + "\r\n");
				}

				totalIndex = totalIndex + SampleNO;

			}

			writer_output.flush();
			writer_output.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void syntheticData(int cSize, int SampleNO, double SOvlp, String DataPath)
	{
		try
		{
			String suffix = "_" + cSize + "_" + SampleNO + "_" + SOvlp + "_.txt";
			// String logFile = DataPath + "log\\log" + suffix;
			// BufferedWriter writer_log = new BufferedWriter(
			// new OutputStreamWriter(new FileOutputStream(logFile), "UTF-8"));

			createDir(DataPath + "list");

			String listFile = DataPath + "list\\list" + suffix;
			BufferedWriter writer_output = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(listFile), "UTF-8"));

			createDir(DataPath + "concept");

			String conceptFile = DataPath + "concept\\concept" + suffix;
			BufferedWriter writer_concept = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(conceptFile), "UTF-8"));

			String[] item = new String[cSize];

			for (int i = 0; i < cSize; i++)
			{
				item[i] = "I" + i;
				writer_concept.write(item[i] + "\r\n");
			}
			writer_concept.close();

			int cItemNO = cSize / SampleNO;
			int addItemNo = (int) ((cSize) * SOvlp / (SampleNO));
			int sampleSize = cItemNO + addItemNo;

			String[][] list = new String[SampleNO][sampleSize];

			int addItem = 0;
			for (int list_i = 0; list_i < SampleNO; list_i++)
			{
				for (int item_i = 0; item_i < cItemNO; item_i++)
				{
					list[list_i][item_i] = item[addItem];
					addItem++;
				}

				Random random = new Random();
				int noiseItem = 0;
				while (noiseItem < addItemNo)
				{
					int flag = 0;
					while (flag == 0)
					{
						int next = random.nextInt(cSize);

						int count = 0;

						for (int item_j = 0; item_j < cItemNO; item_j++)
						{
							if (item[next] != list[list_i][item_j])
							{
								count++;
							}
						}

						if (count == cItemNO)
						{
							flag = 1;
							list[list_i][cItemNO + noiseItem] = item[next];
							noiseItem++;
						}
					}
				}
			}

			for (int list_i = 0; list_i < SampleNO; list_i++)
			{
				String line = "" + list_i + "\t";
				for (int item_i = 0; item_i < sampleSize; item_i++)
				{
					line = line + "\t" + list[list_i][item_i];
				}
				writer_output.write(line + "\r\n");
			}
			writer_output.close();

		} catch (Exception e)
		{
			e.printStackTrace();
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
