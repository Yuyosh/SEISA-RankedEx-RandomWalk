package setexpansion.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ProcessSet
{
	private static final String SEEDFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_japanesePrefecturesConcept\\Seeds\\";
	private static final String CurrentPATH = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_japanesePrefecturesConcept\\";

	private static final String ConceptSetPath = "D:\\CodePath\\dataset\\SampledWebList\\Concept Set\\";

	public static void main(String[] args) throws IOException
	{
		String funcationName = "FindDatasetFeature";

		/********************************* group one *********************************************/
		if (funcationName == "FindDatasetFeature")
		{
			String dataPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\SubDataset_country.txt";
			String featurePath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\SubDataset_country_Feature.txt";

			FindDatasetFeature(dataPath, featurePath);
			return;
		}

		if (funcationName == "group")
		{
			String seedsfile[] = StaticSeisa.getTempFiles_total(SEEDFILEPATH);
			String DataPath = CurrentPATH + "SampledWebList_japanesePrefecturesConcept.txt";
			String output = CurrentPATH + "CalNPAndLP_result.txt";
			int groupCount = 12;
			findLPAndNp(seedsfile, DataPath, output, groupCount);
			// return;
		}

		/********************************* group two *********************************************/
		if (funcationName == ("group"))
		{
			String seedsfile[] = StaticSeisa.getTempFiles_total(SEEDFILEPATH);
			String output = CurrentPATH + "SeedsForAppearTimes.txt";

			getSeedsList(seedsfile, output);
			// return;
		}

		if (funcationName == "group")
		{
			String seedsPath = CurrentPATH + "SeedsForAppearTimes.txt";
			// String seedsPath = CurrentPATH + "moneyConcept.txt";
			String DataPath = CurrentPATH + "smalltermCount.txt";
			String output = CurrentPATH + "SeedsForAppearTimes_result.txt";
			// String output = CurrentPATH + "ConceptForAppearTimes_result.txt";

			findSeedsAppearTimes(seedsPath, DataPath, output);
			// return;
		}

		if (funcationName == "checkExpansionResult")
		{
			String ConceptSet_Path = ConceptSetPath + "japanesePrefecturesConcept.txt";
			String ResultPath = CurrentPATH + "result_list.txt";
			String output = CurrentPATH + "result_false_list.txt";

			checkExpansionResult(ConceptSet_Path, ResultPath, output);
			return;
		}

		/********************************* group three *********************************************/
		if (funcationName == ("group"))
		{
			String seedsfile[] = StaticSeisa.getTempFiles_total(SEEDFILEPATH);
			String KeyInput = CurrentPATH + "SeedsForAppearTimes_result.txt";
			String output = CurrentPATH + "SeedsValue_PrepareData.txt";
			int keyNumber = 300;
			getdata_group_forseeds(seedsfile, KeyInput, output, keyNumber);

			return;
		}

		/********************************* group four *********************************************/
		if (funcationName == "conceptAppearTimes")
		{
			String conceptPath = ConceptSetPath + "japanesePrefecturesConcept.txt";
			String DataPath = CurrentPATH + "smalltermCount.txt";
			String output = CurrentPATH + "conceptAppearTimes_result.txt";
			int length = 300;

			conceptAppearTimes(conceptPath, DataPath, output, length);
			return;
		}
	}

	public static void checkExpansionResult(String ConceptSetPath, String ResultPath, String outputPath)
	{
		try
		{
			File filename_ConceptSet = new File(ConceptSetPath);
			InputStreamReader reader_ConceptSet = new InputStreamReader(new FileInputStream(filename_ConceptSet));
			BufferedReader br_ConceptSet = new BufferedReader(reader_ConceptSet);

			String[] seeds = new String[10000];

			String line_seed = "";
			int addnumber = 0;

			while ((line_seed = br_ConceptSet.readLine()) != null)
			{
				seeds[addnumber] = line_seed;

				addnumber++;
			}
			br_ConceptSet.close();

			File filename_result = new File(ResultPath);
			InputStreamReader reader_result = new InputStreamReader(new FileInputStream(filename_result)); // 建立一个输入流对象reader
			BufferedReader br_result = new BufferedReader(reader_result); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			FileWriter writer = new FileWriter(outputPath, true);

			String line = "";

			while ((line = br_result.readLine()) != null)
			{

				if (line.startsWith("*"))
				{
					writer.write(line + "\r\n");
				} else
				{
					int flag = 0;
					for (int i = 0; i < addnumber; i++)
					{
						if (seeds[i].equals(line))
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
			}
			br_result.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void getSeedsList(String seedsfile[], String output)
	{
		try
		{

			FileWriter writer = new FileWriter(output, true);

			for (int fileNumber = 0; fileNumber < seedsfile.length; fileNumber++)
			{

				File filename_seed = new File(seedsfile[fileNumber]);

				InputStreamReader reader_SeedsInput = new InputStreamReader(new FileInputStream(filename_seed)); // 建立一个输入流对象reader
				BufferedReader br_SeedsInput = new BufferedReader(reader_SeedsInput); // 建立一个对象，它把文件内容转成计算机能读懂的语言

				String line_seed = "";

				Set<String> seeds = new HashSet();

				while ((line_seed = br_SeedsInput.readLine()) != null)
				{

					String[] sep_seed = line_seed.split("\t");

					for (int i = 0; i < sep_seed.length; i++)
					{
						seeds.add(sep_seed[i]);
					}
				}
				br_SeedsInput.close();

				String seedList = "";
				int start = 0;
				Iterator it = seeds.iterator();

				while (it.hasNext())
				{
					if (start == 0)
					{
						seedList = it.next().toString();
						start = 1;
					} else
					{
						seedList = seedList + "\t" + it.next().toString();
					}
				}
				writer.write(seedList + "\r\n");
			}

			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void getdata_group_forseeds(String seedsfile[], String KeyInput, String output, int keySize)
	{
		try
		{
			File filename_KeyInput = new File(KeyInput);
			InputStreamReader reader_KeyInput = new InputStreamReader(new FileInputStream(filename_KeyInput));
			BufferedReader br_KeyInput = new BufferedReader(reader_KeyInput);

			String key[][] = new String[keySize][2];
			String keyline = "";
			int keyi = 0;

			FileWriter writer = new FileWriter(output, true);

			while ((keyline = br_KeyInput.readLine()) != null)
			{
				if (!keyline.startsWith("*******"))
				{
					String[] sep_seed = keyline.split("\t");

					key[keyi][0] = sep_seed[0];
					key[keyi][1] = sep_seed[1];
					keyi++;
				}
			}

			for (int fileNumber = 0; fileNumber < seedsfile.length; fileNumber++)
			{

				// writer.write(seedsfile[fileNumber] + "\r\n");

				File filename_seed = new File(seedsfile[fileNumber]);

				InputStreamReader reader_SeedsInput = new InputStreamReader(new FileInputStream(filename_seed)); // 建立一个输入流对象reader
				BufferedReader br_SeedsInput = new BufferedReader(reader_SeedsInput); // 建立一个对象，它把文件内容转成计算机能读懂的语言

				int linenumber = 0;
				String line_seed = "";
				String line_term = "";

				while ((line_seed = br_SeedsInput.readLine()) != null)
				{

					String[] sep_seed = line_seed.split("\t");

					String seed_cal = "";

					for (int i = 0; i < sep_seed.length; i++)
					{
						for (int j = 0; j < keySize; j++)
						{
							if (sep_seed[i].equals(key[j][0]))
							{
								if (i == 0)
								{
									seed_cal = key[j][1];
								} else
								{
									seed_cal = seed_cal + "\t" + key[j][1];
								}

							}
						}
					}

					writer.write(seed_cal + "\r\n");
				}
			}

			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void getdata_group(String SeedsInput, String KeyInput, String output, int keyNumber)
	{
		try
		{
			File filename_KeyInput = new File(KeyInput);
			InputStreamReader reader_KeyInput = new InputStreamReader(new FileInputStream(filename_KeyInput)); // 建立一个输入流对象reader
			BufferedReader br_KeyInput = new BufferedReader(reader_KeyInput); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			String key[][] = new String[keyNumber][2];
			String keyline = "";
			int keyi = 0;

			while ((keyline = br_KeyInput.readLine()) != null)
			{
				String[] sep_seed = keyline.split("\t");

				key[keyi][0] = sep_seed[0];
				key[keyi][1] = sep_seed[1];
				keyi++;
			}

			File filename_SeedsInput = new File(SeedsInput);
			InputStreamReader reader_SeedsInput = new InputStreamReader(new FileInputStream(filename_SeedsInput)); // 建立一个输入流对象reader
			BufferedReader br_SeedsInput = new BufferedReader(reader_SeedsInput); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			int linenumber = 0;
			String line_seed = "";
			String line_term = "";

			FileWriter writer = new FileWriter(output, true);

			while ((line_seed = br_SeedsInput.readLine()) != null)
			{

				String[] sep_seed = line_seed.split("\t");

				String seed_cal = "";

				for (int i = 0; i < sep_seed.length; i++)
				{
					for (int j = 0; j < keyNumber; j++)
					{
						if (sep_seed[i].equals(key[j][0]))
						{
							if (i == 0)
							{
								seed_cal = key[j][1];
							} else
							{
								seed_cal = seed_cal + "\t" + key[j][1];
							}

						}
					}
				}

				writer.write(seed_cal + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static void findLPAndNp(String seedsfile[], String DataPath, String outputPath, int groupcount)
			throws IOException
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int fileNumber = 0; fileNumber < seedsfile.length; fileNumber++)
		{
			PrintWriter outFile = new PrintWriter(new FileWriter(outputPath, true));

			outFile.println("One seeds group process begin at: " + df.format(new Date()));

			// File filename_seed = new File("D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\AnimalSeeds.txt");
			File filename_seed = new File(seedsfile[fileNumber]);

			outFile.println("A seeds groups  begin at: " + df.format(new Date()));
			outFile.println("The seeds groups  path is: " + seedsfile[fileNumber]);

			System.out.println("A seeds groups  begin at: " + df.format(new Date()));
			System.out.println("The seeds groups  path is: " + seedsfile[fileNumber]);

			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed)); // 建立一个输入流对象reader
			BufferedReader br_seed = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			File filename_data = new File(DataPath);
			InputStreamReader reader_data = new InputStreamReader(new FileInputStream(filename_data)); // 建立一个输入流对象reader
			BufferedReader br_data = new BufferedReader(reader_data); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			/*
			 * File filename_seed_count = new File(seedsfile[fileNumber]); InputStreamReader reader_seed_count = new InputStreamReader(new FileInputStream(filename_seed_count)); // 建立一个输入流对象reader
			 * BufferedReader br_seed_count = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言 int groupCount = 0;
			 * 
			 * while ((br_seed_count.readLine()) != null) { groupCount++; }
			 */
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
				outFile.write("The groups " + i + " seeds is" + "\r\n");
				String seeds = "";
				for (int j = 0; j < seedsGroup[i].length; j++)
				{
					seeds = seeds + "\t" + seedsGroup[i][j];
				}
				outFile.write(seeds + "\r\n");

				outFile.write("The LP is " + count[i][1] + " , and the NP is " + count[i][0] + "\r\n");
			}

			br_data.close();
			br_seed.close();

			outFile.close();
		}
	}

	public static void findSeedsAppearTimes(String seedsPath, String termCountDataPath, String outputPath)
	{
		try
		{
			File filename_seed = new File(seedsPath);
			InputStreamReader reader_seed = new InputStreamReader(new FileInputStream(filename_seed)); // 建立一个输入流对象reader
			BufferedReader br_seed = new BufferedReader(reader_seed); // 建立一个对象，它把文件内容转成计算机能读懂的语言

			int linenumber = 0;
			String line_seed = "";
			String line_term = "";

			FileWriter writer = new FileWriter(outputPath, true);

			while ((line_seed = br_seed.readLine()) != null)
			{
				writer.write("*********************************************************************   " + "\r\n");

				String[] sep_seed = line_seed.split("\t");

				for (int i = 0; i < sep_seed.length; i++)
				{
					File filename_term = new File(termCountDataPath);
					InputStreamReader reader_term = new InputStreamReader(new FileInputStream(filename_term)); // 建立一个输入流对象reader
					BufferedReader br_term = new BufferedReader(reader_term); // 建立一个对象，它把文件内容转成计算机能读懂的语言

					int findFlag = 0;
					while ((line_term = br_term.readLine()) != null)
					{
						String[] sep = line_term.split("\t");

						if (sep_seed[i].equals(sep[0]))
						{
							writer.write(sep_seed[i] + "\t" + sep[1] + "\r\n");
							findFlag = 1;
						}
					}

					if (findFlag == 0)
					{
						writer.write(sep_seed[i] + "\t" + 0 + "\r\n");
						findFlag = 1;
					}
					br_term.close();
				}
			}

			br_seed.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void conceptAppearTimes(String conceptPath, String termCountDataPath, String outputPath, int length)
	{
		try
		{
			File filename_cocept = new File(conceptPath);
			InputStreamReader reader_cocept = new InputStreamReader(new FileInputStream(filename_cocept));
			BufferedReader br_cocept = new BufferedReader(reader_cocept);

			int linenumber = 0;
			String line_cocept = "";
			String line_term = "";

			String conceptItem[] = new String[length];
			String conceptItemNumber[] = new String[length];

			while ((line_cocept = br_cocept.readLine()) != null)
			{
				conceptItem[linenumber] = line_cocept;
				conceptItemNumber[linenumber] = "0";

				linenumber++;
			}

			br_cocept.close();

			FileWriter writer = new FileWriter(outputPath, true);

			for (int i = 0; i < linenumber; i++)
			{
				File filename_term = new File(termCountDataPath);
				InputStreamReader reader_term = new InputStreamReader(new FileInputStream(filename_term));
				BufferedReader br_term = new BufferedReader(reader_term);

				int findFlag = 0;
				while ((line_term = br_term.readLine()) != null)
				{
					String[] sep = line_term.split("\t");

					if (conceptItem[i].equals(sep[0]))
					{
						conceptItemNumber[i] = sep[1];
					}
				}

				br_term.close();

				writer.write(conceptItem[i] + "\t" + conceptItemNumber[i] + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
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
				String[] sep = line.split("\t");

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

	public static void FindDatasetFeature(String dataPath, String featurePath)
	{
		int ItemNumbers = 0;

		int SetNumbers = 0;

		int maxSetSize = 0;
		int maxWordLength = 0;
		int maxNumberLength = 0;

		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";
			line = br.readLine();
			while (line != null)
			{
				SetNumbers++;

				String[] sep = line.split("\t");

				if (sep[0].length() > maxNumberLength)
				{
					maxNumberLength = sep[0].length();
				}

				if ((sep.length - 2) > maxSetSize)
				{
					maxSetSize = sep.length - 2;
				}

				for (int i = 2; i < sep.length; i++)
				{
					ItemNumbers++;

					if (sep[i].length() > maxWordLength)
					{
						maxWordLength = sep[i].length();
					}
				}

				line = br.readLine(); // 一次读入一行数据
			}
			br.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{

			FileWriter writer = new FileWriter(featurePath, true);

			writer.write("Total items number is:" + ItemNumbers + "\r\n");
			writer.write("Total set number is:" + SetNumbers + "\r\n");
			writer.write("Total maxSetSize number is:" + maxSetSize + "\r\n");
			writer.write("Total maxWordLength number is:" + maxWordLength + "\r\n");
			writer.write("Total maxNumberLength number is:" + maxNumberLength + "\r\n");
			writer.write("The dictionary number is:  \r\n");
			writer.write("The edges number is:  \r\n");
			writer.write("\r\n");
			writer.write("The average size of lists is: " + ItemNumbers / SetNumbers + "\r\n");
			writer.write("The concept set size is:  \r\n");

			// writer.write("\n\t" + time);
			writer.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
