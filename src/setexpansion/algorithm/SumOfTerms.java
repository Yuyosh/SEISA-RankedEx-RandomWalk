/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package setexpansion.algorithm;

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
import java.io.PrintWriter;

/**
 * This class is used for preprocessing the data
 * 
 * @author nawshad and sanket
 */
public class SumOfTerms
{
	public static void sumOfTerms(String inputFile, String outputFile) throws IOException
	{
		File file = new File(outputFile);
		if (file.exists())
		{
			file.delete();
		}
		FileWriter fos = new FileWriter(outputFile);
		PrintWriter dos = new PrintWriter(fos);
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		try
		{
			String line = "";
			String prevt1 = "";
			int curVal = 0;
			int lineno = 0;
			boolean first = true;
			// boolean endAtSameTerm = false;
			while ((line = br.readLine()) != null)
			{
				String[] split = line.split("\t");
				if (split.length != 2)
				{
					continue;
				}
				if (first)
				{
					curVal += Integer.parseInt(split[1]);
					prevt1 = split[0];
					first = false;
					continue;
				}
				if (!split[0].equals(prevt1))
				{
					dos.println(prevt1 + "\t" + curVal);
					dos.flush();
					curVal = Integer.parseInt(split[1]);
					prevt1 = split[0];
					// endAtSameTerm = false;
				} else
				{
					curVal += Integer.parseInt(split[1]);
					prevt1 = split[0];
					// endAtSameTerm = true;
				}
				// lineno = lineno + 1;
				// if(lineno%10000 == 0)
				// System.out.println("Done line no:" + lineno);
			}
			// if(endAtSameTerm){
			dos.println(prevt1 + "\t" + curVal);
			dos.flush();
			System.out.println("Done last line");
			// }

		} finally
		{
			br.close();
		}
		dos.close();
		fos.close();
		System.out.println("Finished..");
	}

	public static void sumOfTerms_list(String inputFile, String outputFile) throws IOException
	{
		File file = new File(outputFile);
		if (file.exists())
		{
			file.delete();
		}
		BufferedWriter dos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));

		// FileWriter fos = new FileWriter(outputFile);
		// PrintWriter dos = new PrintWriter(fos);

		File filename = new File(inputFile);
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename), "UTF-8"); // 建立一个输入流对象reader
		BufferedReader br = new BufferedReader(reader);

		// BufferedReader br = new BufferedReader(new FileReader(inputFile));
		try
		{
			String line = "";
			String prevt1 = "";

			String templist = "";
			int curVal = 0;
			int lineno = 0;
			boolean first = true;
			// boolean endAtSameTerm = false;
			while ((line = br.readLine()) != null)
			{
				String[] split = line.split("\t\t");
				if (split.length != 2)
				{
					continue;
				}
				if (first)
				{
					templist = split[1];
					curVal++;

					// curVal += Integer.parseInt(split[1]);
					prevt1 = split[0];
					first = false;
					continue;
				}
				if (!split[0].equals(prevt1))
				{
					dos.write(prevt1 + "\t\t" + curVal + "\t" + templist + "\r\n");
					dos.flush();
					templist = split[1];
					curVal = 1;
					prevt1 = split[0];
					// endAtSameTerm = false;
				} else
				{
					templist = templist + "\t" + split[1];
					curVal++;
					// curVal += Integer.parseInt(split[1]);
					prevt1 = split[0];
					// endAtSameTerm = true;
				}
				// lineno = lineno + 1;
				// if(lineno%10000 == 0)
				// System.out.println("Done line no:" + lineno);
			}
			// if(endAtSameTerm){
			dos.write(prevt1 + "\t\t" + curVal + "\t" + templist + "\r\n");
			dos.flush();
			System.out.println("Done last line");
			// }

		} finally
		{
			br.close();
		}
		dos.close();
		System.out.println("Finished..");
	}
}
