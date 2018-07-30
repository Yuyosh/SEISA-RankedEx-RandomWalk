/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setexpansion.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is used in preprocessing steps.
 * 
 * @author nawshad
 */
public class Tuple
{
	String c1;
	String c2;

	public Tuple(String c1, String c2)
	{
		this.c1 = c1;
		this.c2 = c2;
	}

	public static void sort(String inputFile, String outputFile) throws IOException
	{

		// Sorting smallterm
		Path inputPath = Paths.get(inputFile);
		List<Tuple> tuple = new ArrayList<Tuple>();

		/*
		 * File file = new File(inputFile); LineIterator iterator = FileUtils.lineIterator(file, "UTF-8"); List<String> lines = new ArrayList(); while (iterator.hasNext()) { String line =
		 * iterator.nextLine(); lines.add(line); }
		 */
		List<String> lines = new ArrayList();

		File filename = new File(inputFile);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); //

		String readline = "";
		readline = br.readLine();

		while (readline != null)
		{
			lines.add(readline);
			readline = br.readLine(); // 一次读入一行数据
		}
		br.close();

		// List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
		String splitter;
		if (inputFile.indexOf("smallterm.txt") > 0)
		{
			splitter = "\t";
		} else
		{
			splitter = "\t\t";
		}
		for (String line : lines)
		{
			String[] array = line.split(splitter);
			tuple.add(new Tuple(array[0], array[1]));
		}

		Collections.sort(tuple, new Comparator<Tuple>()
		{
			@Override
			public int compare(Tuple o1, Tuple o2)
			{
				return o1.c1.compareTo(o2.c1);
			}
		});

		File file1 = new File(outputFile);
		if (file1.exists())
		{
			file1.delete();
		}
		Path outputPath = Paths.get(outputFile);
		BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8);
		PrintWriter outSmallTermSorted = new PrintWriter(writer);

		for (Tuple tp : tuple)
		{
			if (inputFile.indexOf("smallterm.txt") > 0)
			{
				outSmallTermSorted.println(tp.c1 + "\t" + tp.c2);
			} else
			{
				outSmallTermSorted.println(tp.c1 + "\t\t" + tp.c2);
			}

			outSmallTermSorted.flush();
		}
	}

	public static void sort_withSplitString(String inputFile, String outputFile, String SplitString) throws IOException
	{

		// Sorting smallterm
		Path inputPath = Paths.get(inputFile);
		List<Tuple> tuple = new ArrayList<Tuple>();

		/*
		 * File file = new File(inputFile); LineIterator iterator = FileUtils.lineIterator(file, "UTF-8"); List<String> lines = new ArrayList(); while (iterator.hasNext()) { String line =
		 * iterator.nextLine(); lines.add(line); }
		 */
		List<String> lines = new ArrayList();

		File filename = new File(inputFile);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader); //

		String readline = "";
		readline = br.readLine();

		while (readline != null)
		{
			lines.add(readline);
			readline = br.readLine(); // 一次读入一行数据
		}
		br.close();

		// List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
		String splitter = SplitString;

		for (String line : lines)
		{
			String[] array = line.split(splitter);
			tuple.add(new Tuple(array[0], array[1]));
		}

		Collections.sort(tuple, new Comparator<Tuple>()
		{
			@Override
			public int compare(Tuple o1, Tuple o2)
			{
				return o1.c1.compareTo(o2.c1);
			}
		});

		File file1 = new File(outputFile);
		if (file1.exists())
		{
			file1.delete();
		}
		Path outputPath = Paths.get(outputFile);
		BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8);
		PrintWriter outSmallTermSorted = new PrintWriter(writer);

		for (Tuple tp : tuple)
		{
			outSmallTermSorted.println(tp.c1 + SplitString + tp.c2);
			outSmallTermSorted.flush();
		}
	}

	public static void main(String[] args) throws IOException
	{
		Tuple.sort("smallterm.txt", "smallTermSorted.txt");
		Tuple.sort("smalltermPair.txt", "smallTermPairSorted.txt");
	}

}
