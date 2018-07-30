package setexpansion.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class CombinationSeed
{

	public static void main(String[] args)
	{
		String stage = "createSeedsList";

		int seedListSize = 3;

		if (stage == "createSeedsList")
		{
			String seedFile = "D:\\CodePath\\dataset\\SyntheticData\\SourceData\\concept\\ConceptSet_200_81_100_0.1_\\C000.txt";

			String outputListFile = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\ConceptSet_200_81_100_0.1_list_3.txt";

			String outputSeedsListFile = "D:\\CodePath\\dataset\\SyntheticData\\Seeds\\ConceptSet_200_81_100_0.1_seedsList_3.txt";

			UtiForCreateDataset Util = new UtiForCreateDataset();

			int seedSize = Util.getDataLineSize(seedFile);

			int[] num = new int[seedSize];

			for (int i = 0; i < seedSize; i++)
			{
				num[i] = i;
			}

			String str = "";

			countList(0, str, num, seedListSize, outputListFile);

			createSeedsList(outputListFile, seedFile, outputSeedsListFile);

			return;
		}

		int[] num = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
		String str = "";

		String outputListFile = "D:\\CodePath\\dataset\\SampledWebList\\CreatedDataset\\CountrySet\\CombinationSeed\\list.txt";

		countList(0, str, num, 3, outputListFile);

		// 求3个数的组合个数
		// count(0, str, num, 3);
		// 求1-n个数的组合个数
		// countAll(0, str, num);

	}

	public static void createSeedsList(String listFile, String seedFile, String outputFile)
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

			while ((line_seed = br_seed.readLine()) != null)
			{
				seeds[Seednumber] = line_seed;
				Seednumber++;
			}
			br_seed.close();

			File filename = new File(listFile);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
			BufferedReader br = new BufferedReader(reader);

			String line = "";

			FileWriter writer = new FileWriter(outputFile, true);

			while ((line = br.readLine()) != null)
			{
				String[] sep = line.split(",");

				String lineString = seeds[Integer.parseInt(sep[0])];

				for (int i = 1; i < sep.length; i++)
				{
					lineString = lineString + "\t" + seeds[Integer.parseInt(sep[i])];
				}

				writer.write(lineString + "\r\n");

			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void countList(int i, String str, int[] num, int n, String outputListFile)
	{
		if (n == 0)
		{
			try
			{
				FileWriter writer = new FileWriter(outputListFile, true);

				writer.write(str.substring(0, str.length() - 1) + "\r\n");

				writer.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			System.out.println(str);
			return;
		}
		if (i == num.length)
		{
			return;
		}
		countList(i + 1, str + num[i] + ",", num, n - 1, outputListFile);
		countList(i + 1, str, num, n, outputListFile);
	}

	public static void count(int i, String str, int[] num, int n)
	{
		if (n == 0)
		{
			System.out.println(str);
			return;
		}
		if (i == num.length)
		{
			return;
		}
		count(i + 1, str + num[i] + ",", num, n - 1);
		count(i + 1, str, num, n);
	}

	public static void countAll(int i, String str, int[] num)
	{
		if (i == num.length)
		{
			System.out.println(str);
			return;
		}
		countAll(i + 1, str, num);
		countAll(i + 1, str + num[i] + ",", num);
	}

}
