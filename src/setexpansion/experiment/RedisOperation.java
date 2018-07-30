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
		 * // 连接本地的 Redis 服务 Jedis jedis = new Jedis("localhost"); System.out.println("Connection to server sucessfully"); // 查看服务是否运行 System.out.println("Server is running: " + jedis.ping());
		 */

		String dataFile = "D:\\CodePath\\dataset\\SampledWebList\\SampledWebList_Formated.txt";

		setKeyValue(dataFile);
	}

	public static void setKeyValue(String dataPath)
	{
		// 连接本地的 Redis 服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");

		try
		{
			File filename = new File(dataPath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

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
