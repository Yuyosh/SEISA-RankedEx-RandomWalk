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

	public void combine(int[] a, int n)
	{

		if (null == a || a.length == 0 || n <= 0 || n > a.length)
			return;

		int[] b = new int[n];// 辅助空间，保存待输出组合数
		getCombination(a, n, 0, b, 0);
	}

	private void getCombination(int[] a, int n, int begin, int[] b, int index)
	{

		if (n == 0)
		{// 如果够n个数了，输出b数组
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
