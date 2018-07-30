package setexpansion.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class PrepairSeedsList
{

	private static final String SEEDFILEPATH = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_japanesePrefecturesConcept\\Seeds\\";
	private static final String CurrentPATH = "D:\\CodePath\\dataset\\SampledWebList\\LittleSeedsSet\\SampledWebList_japanesePrefecturesConcept\\";

	private static final String ConceptSetPath = "D:\\CodePath\\dataset\\SampledWebList\\Concept Set\\";

	public static void main(String[] args)
	{
		PrepairSeedsList robot = new PrepairSeedsList();

		int[] a = { 1, 2, 3, 4 };
		int n = 2;
		robot.combine(a, n);

	}

	public static void PrepairSeeds(String inputdataPath, String outputPath, int startline, int endline)
	{
		try
		{
			File filename = new File(inputdataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // ����һ������������reader
			BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������

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

				line = br.readLine(); // һ�ζ���һ������
				linenumber++;
			}
			br.close();

			writer.close();

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void combine(int[] a, int n)
	{

		if (null == a || a.length == 0 || n <= 0 || n > a.length)
			return;

		int[] b = new int[n];// �����ռ䣬�������������
		getCombination(a, n, 0, b, 0);
	}

	private void getCombination(int[] a, int n, int begin, int[] b, int index)
	{

		if (n == 0)
		{// �����n�����ˣ����b����
			for (int i = 0; i < index; i++)
			{
				System.out.print(b[i] + " ");
			}
			System.out.println();
			return;
		}

		for (int i = begin; i < a.length; i++)
		{

			b[index] = a[i];
			getCombination(a, n - 1, i + 1, b, index + 1);
		}

	}

}
