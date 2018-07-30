package setexpansion.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import setexpansion.experiment.SyntheticData;

public class CreateDataset
{

	public static void main(String[] args)
	{
		String ProcessStage = "findConceptAppearTimes";

		if (ProcessStage == "randomSeedsList")
		{

			String conceptFile = "D:\\CodePath\\dataset\\SampledWebList\\Seeds_FourConcept\\ConceptItemAppearTimes\\Country_ConceptItemAppearTimes.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\Seeds_FourConcept\\ConceptItemAppearTimes\\CountrySeed_4_10.txt";

			SyntheticData.randomSeedsList_forGeneralData(4, 9, conceptFile, outputFile);
			return;
		}

		// 2017-03-23 create seisa item-lists data

		UtiForCreateDataset Util = new UtiForCreateDataset();

		if (ProcessStage == "averageRollingTimes")
		{
			String rollingTimesFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-30-0.8\\rollingTimes_9.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-30-0.8\\rollingTimes_9_feature.txt";

			Util.averageRollingTimes(rollingTimesFile, outputFile);

			return;
		}

		if (ProcessStage == "averageNPAndLP")
		{
			String NPAndLPPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-50-0.8\\LPAndNp\\";

			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-50-0.8\\LPAndNp_Feature\\";

			String NPAndLPfile[] = StaticSeisa.getTempFiles_total(NPAndLPPath);

			Util.averageNPAndLP(NPAndLPfile, outputPath);

			return;
		}

		if (ProcessStage == "findLPAndNp_bat")
		{
			String seedsPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\Seeds\\";

			String outputPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-50-0.8\\LPAndNp\\";

			String seedsfile[] = StaticSeisa.getTempFiles_total(seedsPath);

			String DataPath = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\country-5000-50-0.8\\country-5000-50-0.8.txt";

			try
			{
				Util.findLPAndNp_bat(seedsfile, DataPath, outputPath);
			} catch (IOException e)
			{
				e.printStackTrace();
			}

			return;
		}

		if (ProcessStage == "pickSeedsList")
		{
			String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\CombinationSeed\\seedsList_3.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\CombinationSeed\\seedsList_3_500.txt";

			int listSize = 500;

			Util.pickSeedsList(DataFile, outputFile, listSize);

			return;
		}

		if (ProcessStage == "findConceptAppearTimes")
		{
			// 2017-6-25
			String ConceptFile = "D:\\CodePath\\dataset\\SampledWebList\\Seeds_FourConcept\\SEISA_camera_10.txt";

			String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\tenPercents\\SampledWebList_Formated_tenPercents.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\Seeds_FourConcept\\SEISA_camera_10_ItemAppearTimes.txt";

			Util.findConceptAppearTimes(ConceptFile, DataFile, outputFile);

			/*
			 * String ConceptFile = "D:\\CodePath\\dataset\\SampledWebList\\ConceptSet\\countriesConcept.txt";
			 * 
			 * String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";
			 * 
			 * String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\ConceptItemAppearTimes.txt";
			 * 
			 * Util.findConceptAppearTimes(ConceptFile, DataFile, outputFile);
			 */
			return;
		}

		if (ProcessStage == "pickSubdataset_withSeeds")
		{
			String ConceptFile = "D:\\CodePath\\dataset\\SampledWebList\\ConceptSet\\countriesConcept.txt";

			String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\SubDataset_country.txt";

			Util.pickSubdataset_withSeeds(DataFile, ConceptFile, outputFile);

			return;
		}

		if (ProcessStage == "sortRankedItem")
		{
			String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\smalltermCount.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\ItemSorted.txt";

			Util.sortRankedItem(DataFile, outputFile);

			return;
		}

		if (ProcessStage == "removeSeeds")
		{
			String ConceptFile = "D:\\CodePath\\dataset\\SampledWebList\\ConceptSet\\countriesConcept.txt";

			String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\ItemSorted.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\noiseItemSorted.txt";

			Util.removeSeeds(DataFile, ConceptFile, outputFile);

			return;
		}

		if (ProcessStage == "pickNoiseList")
		{
			String DataFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\noiseItemSorted.txt";

			String outputFile_Item = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\noiseItem_1000.txt";

			String outputFile_ItemAndTimes = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\noiseItemAndTimes_1000.txt";

			int noiseItemSize = 1000;

			Util.pickNoiseList(DataFile, outputFile_Item, outputFile_ItemAndTimes, noiseItemSize);

			return;
		}

		if (ProcessStage == "createSubDataset")
		{
			String conceptItemAppearTimesFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\ConceptItemAppearTimes.txt";

			String noiseItemAndTimesFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\noiseItemAndTimes_10000.txt";

			String outputFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\result.txt";

			int listNumber = 5000;

			int listSize = 30;

			double errorRate = 0.8;

			createSubDataset(conceptItemAppearTimesFile, noiseItemAndTimesFile, outputFile, listNumber, listSize,
					errorRate);

			return;
		}

	}

	public static void createSEISAData(String DictFile, String inputdataPath, String outputPath)
	{

		try
		{
			int DictSize = UtiForCreateDataset.getDataLineSize(DictFile);
			String[] dict = new String[DictSize];
			String[] dictList = new String[DictSize];

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
			int listNumber = 0;

			while (line != null)
			{

				String[] list = line.split("\t\t");
				String[] sep = list[1].split("\t");

				String newlist = list[0] + "\t\t" + sep.length;

				for (int i = 0; i < sep.length; i++)
				{
					int location = UtilityForRandomJump.halfSearch(dict, sep[i]);

					/*
					 * for (int j = 0; j < dict.length; j++) { if (sep[i] == dict[j]) { newlist = newlist + "\t" + j; break; } }
					 */
					if (location == -1)
					{
						System.out.println("-1");
					}

					if (dictList[location] == null || dictList[location].length() <= 0)
					{
						dictList[location] = "" + location;
					} else
					{
						dictList[location] = dictList[location] + "\t" + location;
					}
					// newlist = newlist + "\t" + location;
				}
				// writer.write(newlist + "\r\n");

				line = br.readLine(); // 一次读入一行数据
				listNumber++;
			}
			br.close();

			for (int i = 0; i < DictSize; i++)
			{
				writer.write(dict[i] + "\t\t" + dictList[i] + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void createSubDataset(String conceptItemAppearTimesFile, String noiseItemAndTimesFile,
			String outputPath, int listNumber, int listSize, double errorRate)
	{
		UtiForCreateDataset Util_C = new UtiForCreateDataset();
		try
		{
			String ConceptItme[] = Util_C.readItem(conceptItemAppearTimesFile, 1);

			int ConceptItmeAppearTimes[] = Util_C.readTimes(conceptItemAppearTimesFile);

			int ConceptItemAccumulativeTimes[] = Util_C.accumulativeTimes(ConceptItmeAppearTimes);

			String NoiseItme[] = Util_C.readItem(noiseItemAndTimesFile, 1);

			int noiseItemAppearTimes[] = Util_C.readTimes(noiseItemAndTimesFile);

			int noiseItemAccumulativeTimes[] = Util_C.accumulativeTimes(noiseItemAppearTimes);

			/*
			 * int NoiseList[] = new int[NoiseItme.length]; for (int i = 0; i < NoiseList.length; i++) { NoiseList[i] = i; }
			 */

			int errorSize = (int) (listSize * errorRate);
			int rightSize = listSize - errorSize;

			int[][] result_right = new int[listNumber][];
			int[][] result_error = new int[listNumber][];

			FileWriter writer = new FileWriter(outputPath, true);

			for (int listN = 0; listN < listNumber; listN++)
			{
				result_right[listN] = Util_C.randomList_accumulativeTimes(ConceptItemAccumulativeTimes, rightSize);

				result_error[listN] = Util_C.randomList_accumulativeTimes(noiseItemAccumulativeTimes, errorSize);

				String thisString = "";

				for (int i : result_right[listN])
				{
					if (thisString == "")
					{
						thisString = ConceptItme[i];
					} else
					{
						thisString = thisString + "\t" + ConceptItme[i];
					}
				}

				for (int i : result_error[listN])
				{
					thisString = thisString + "\t" + NoiseItme[i];
				}

				writer.write(listN + "\t\t" + thisString + "\r\n");
			}

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
