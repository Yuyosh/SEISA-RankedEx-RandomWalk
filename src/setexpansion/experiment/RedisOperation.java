package setexpansion.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import redis.clients.jedis.Jedis;

public class RedisOperation
{

	public static void main(String[] args)
	{

		/*
		 * // ���ӱ��ص� Redis ���� Jedis jedis = new Jedis("localhost"); System.out.println("Connection to server sucessfully"); // �鿴�����Ƿ����� System.out.println("Server is running: " + jedis.ping());
		 */

		String dataFile = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";

		setKeyValue(dataFile);
	}

	public static void setKeyValue(String dataPath)
	{
		// ���ӱ��ص� Redis ����
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");

		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // ����һ������������reader
			BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������

			String line = "";

			while ((line = br.readLine()) != null)
			{
				String[] temp = line.split("\t\t");
				jedis.set(temp[0], temp[1]);
				temp = null;
			}
			br.close();

			// System.out.println("List number is " + lineNumber);
			// System.out.println("Item number is " + items);

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Finish sucessfully");

	}
}
