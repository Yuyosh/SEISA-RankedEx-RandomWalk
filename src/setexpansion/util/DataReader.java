package setexpansion.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * This is data preprocessing class. Commented code refers to different approach tried for pre-processing the data.
 * 
 * @author sanket
 *
 */
public class DataReader
{

	// public static Map generatePairWiseMatrix(Map<String, Set<String>> uniqueTermsMap)
	// throws IOException {
	//
	// Set<String> uniqueTerms = uniqueTermsMap.keySet();
	// int uniqueTermsSize = uniqueTermsMap.keySet().size();
	// System.out.println("Number of unique terms:" + uniqueTermsSize);
	// Iterator<String> iter1 = uniqueTerms.iterator();
	// Map<String, List<TermInfo>> termSimMap = new HashMap();
	// String terms[] = new String[uniqueTermsSize]; int index = 0;
	// while(iter1.hasNext()){
	// terms[index] = iter1.next();
	// index++;
	// }
	// for(int i =0; i < terms.length ;i++){
	// for(int j = i+1;j < terms.length ; j++ ){
	// Set<String> setI = uniqueTermsMap.get(terms[i]);
	// Set<String> setJ = uniqueTermsMap.get(terms[j]);
	// double score = Util.getSimilarity(setI, setJ);
	// if(score != 0.0){
	// if(termSimMap.containsKey(terms[i])){
	// List<TermInfo> termLists = termSimMap.get(terms[i]);
	// TermInfo termInfo = new TermInfo();
	// termInfo.setSimScore(score);
	// termInfo.setTerm(terms[j]);
	// termLists.add(termInfo);
	// }else{
	// List<TermInfo> termList = new ArrayList<TermInfo>();
	// TermInfo termInfo = new TermInfo();
	// termInfo.setSimScore(score);
	// termInfo.setTerm(terms[j]);
	// termList.add(termInfo);
	// termSimMap.put(terms[i], termList);
	// }
	// }
	// }
	// }
	// try
	// {
	// FileOutputStream fos =
	// new FileOutputStream("termScore.ser");
	// ObjectOutputStream oos = new ObjectOutputStream(fos);
	// oos.writeObject(termSimMap);
	// oos.close();
	// fos.close();
	// System.out.printf("Serialized HashMap data is saved in termScore.ser");
	// }catch(IOException ioe)
	// {
	// ioe.printStackTrace();
	// }
	//
	// System.out.println("Finished created HashMap..");
	//
	// return termSimMap;
	// }

	// public static void generateListTermPairFile(String fileName) throws
	// IOException {
	// File file = new File(fileName);
	// //1707913
	// LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	// PrintWriter outFile = new PrintWriter(new FileWriter("fileId-termid.txt",
	// true));
	// long lineNo = 0;
	//// Map<String , Set<String>> uniqueMap = new HashMap<String ,
	// Set<String>>();
	// try {
	// while (it.hasNext()) {
	// String line = it.nextLine();
	// String value[] = line.split("\t",-1);
	// String listId = value[0];
	// for(int i = 2 ; i < value.length ; i++){
	// String ltrim = value[i].replaceAll("^\\s+","");
	// String listIdTermId = listId + "\t" + ltrim ;
	//// if(uniqueMap.containsKey(ltrim)){
	//// Set listvals = uniqueMap.get(ltrim);
	//// listvals.add(listId);
	//// }else{
	//// Set<String> listvals = new HashSet<String>();
	//// listvals.add(ltrim);
	//// uniqueMap.put(ltrim, listvals);
	//// }
	// outFile.println(listIdTermId);
	// outFile.flush();
	// }
	// lineNo = lineNo + 1;
	// System.out.println("Done line number:"+ lineNo);
	//
	// }
	// System.out.println("Finished converting file.");
	// outFile.close();
	//
	// } catch (Exception e){
	// e.printStackTrace();
	// }
	// finally {
	//
	// LineIterator.closeQuietly(it);
	// }
	// //return uniqueMap;
	// }
	//

	//
	// public static Map<String, Set<String>> generateListTermPairFile(String fileName) throws IOException {
	// File file = new File(fileName);
	// LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	// long lineNo = 0;
	// Map<String, Set<String>> uniqueMap = new HashMap<String, Set<String>>();
	// try {
	// while (it.hasNext()) {
	// String line = it.nextLine();
	// String value[] = line.split("\t");
	// String listId = value[0].trim();
	// String term = value[1].trim();
	// if (uniqueMap.containsKey(term)) {
	// Set<String> listSet = uniqueMap.get(term);
	// listSet.add(listId);
	// } else {
	//
	// Set<String> listvals = new HashSet<String>();
	// listvals.add(listId);
	// uniqueMap.put(term, listvals);
	// }
	// lineNo = lineNo + 1;
	// System.out.println("Done line number:" + lineNo);
	//
	// }
	// try
	// {
	// FileOutputStream fos =
	// new FileOutputStream("termListPair.ser");
	// ObjectOutputStream oos = new ObjectOutputStream(fos);
	// oos.writeObject(uniqueMap);
	// oos.close();
	// fos.close();
	// System.out.printf("Serialized HashMap data is saved in termListPair.ser");
	// }catch(IOException ioe)
	// {
	// ioe.printStackTrace();
	// }
	//
	// System.out.println("Finished created HashMap..");
	// } catch (Exception e) {
	// e.printStackTrace();
	// } finally {
	//
	// LineIterator.closeQuietly(it);
	// }
	// return uniqueMap;
	// }
	//
	//
	// public static void generateTermListsFile(String fileName) throws IOException{
	// File file = new File(fileName);
	// LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	// int i = 0;
	// String lineToWrite = ""; String theFirstTerm = "";
	// PrintWriter outFile = new PrintWriter(new FileWriter("termid-listids.txt", true));
	// while(it.hasNext()){
	// String line = it.nextLine();
	// String value[] = line.split("\t");
	// String term = value[1];
	// String listId = value[0];
	// if(i==0){
	// lineToWrite = term + "\t" + listId;
	// theFirstTerm = value[1];
	// i++;
	// }else{
	// if(theFirstTerm.equals(term)){
	// lineToWrite = lineToWrite+ "\t"+ listId;
	// }else{
	// //i=0;
	// outFile.println(lineToWrite);
	// outFile.flush();
	// lineToWrite = term + "\t" + listId;
	// theFirstTerm = value[1];
	// }
	// }
	// }
	// }

	// public static BipartiteGraph readDataFileAndBuildGraph(String fileName)
	// throws IOException{
	//
	// File file = new File(fileName);
	// BipartiteGraph graph = null;
	// LineIterator it = FileUtils.lineIterator(file, "UTF-8");
	// try {
	// graph = new BipartiteGraph();
	// List<ConceptNode> conceptNodes = new ArrayList<ConceptNode>();
	// while (it.hasNext()) {
	// String line = it.nextLine();
	// System.out.println(line);
	// String value[] = line.split("\t",-1);
	// ConceptNode node = new ConceptNode();
	// node.setConcept(value[0]);
	// List<String> terms = Arrays.asList(value);
	// terms.remove(0);
	// terms.remove(1);
	// node.setTerms(terms);
	// conceptNodes.add(node);
	// }
	// graph.setConcepts(conceptNodes);
	//
	// } catch (Exception e){
	// System.out.println(e);
	// }
	// finally {
	//
	// LineIterator.closeQuietly(it);
	// }
	//
	// return graph;
	// }

	// public static Map generatePairWiseSimScore(String fileName) throws IOException{
	// File file = new File(fileName);
	//
	// LineIterator it1 = FileUtils.lineIterator(file, "UTF-8");
	//
	// Map<String, List<TermInfo>> termSimMap = new HashMap();
	// int lineNo = 0;
	// while(it1.hasNext()){
	// String line = it1.nextLine();
	// lineNo++;
	// String values[] = line.split("\t");
	// Set<String> setVal1 = new HashSet();
	// for(int i=1;i<values.length;i++){
	// setVal1.add(values[i]);
	// }
	// int j = 0 ;
	// LineIterator it2 = FileUtils.lineIterator(file, "UTF-8");
	// while(j<lineNo){
	// it2.next();
	// j++;
	// }
	//
	// //it2.next();
	// String iTerm = values[0];
	// while(it2.hasNext()){
	// String nextline = it2.nextLine();
	// String nextvalue[] = nextline.split("\t");
	// Set<String> setVal2 = new HashSet();
	// for(int i=1;i<nextvalue.length;i++){
	// setVal2.add(nextvalue[i]);
	// }
	// String jTerm = nextvalue[0];
	// double score = Util.getSimilarity(setVal1, setVal2);
	// if(score != 0.0){
	// if(termSimMap.containsKey(iTerm)){
	// List<TermInfo> termLists = termSimMap.get(iTerm);
	// TermInfo termInfo = new TermInfo();
	// termInfo.setSimScore(score);
	// termInfo.setTerm(jTerm);
	// termLists.add(termInfo);
	// }else{
	// List<TermInfo> termList = new ArrayList<TermInfo>();
	// TermInfo termInfo = new TermInfo();
	// termInfo.setSimScore(score);
	// termInfo.setTerm(jTerm);
	// termList.add(termInfo);
	// termSimMap.put(iTerm, termList);
	// }
	// }
	// }
	// System.out.println("Done line no:" + lineNo);
	// }
	//
	// try
	// {
	// FileOutputStream fos =
	// new FileOutputStream("termScore.ser");
	// ObjectOutputStream oos = new ObjectOutputStream(fos);
	// oos.writeObject(termSimMap);
	// oos.close();
	// fos.close();
	// System.out.printf("Serialized HashMap data is saved in termScore.ser");
	// }catch(IOException ioe)
	// {
	// ioe.printStackTrace();
	// }
	//
	// System.out.println("Finished created HashMap..");
	//
	// return termSimMap;
	//
	// }

	// public static void getTermsValue(String fileName) throws IOException{
	// File file = new File(fileName);
	//
	// LineIterator it1 = FileUtils.lineIterator(file, "UTF-8");
	// Map<String, Map<String,Integer>> dataMap = new HashMap();
	// int lineNo = 0;
	// while(it1.hasNext()){
	// String line = it1.next();
	// String values[] = line.split("\t\t");
	// String listId = values[0];
	// String terms = values[1];
	// String termVal[] = terms.split("\t");
	// for(int i=0;i<termVal.length;i++){
	// String termToWhichToAdd = termVal[i];
	// if(!dataMap.containsKey(termToWhichToAdd)){
	// Map<String, Integer> map = new HashMap();
	// dataMap.put(termToWhichToAdd, map);
	// }
	// for(int j=0; j<termVal.length;j++){
	// String termToAdd = termVal[j];
	// Map map = dataMap.get(termToWhichToAdd);
	// if(map.containsKey(termToAdd)){
	// int count = (Integer) map.get(termToAdd);
	// map.put(termToAdd, count+1);
	// }else{
	// map.put(termToAdd, 1);
	// }
	//
	// }
	// }
	//
	// System.out.println("Processed " + listId);
	// }
	//
	// PrintWriter outFile = new PrintWriter(new FileWriter("output.txt", true));
	// Iterator it = dataMap.entrySet().iterator();
	// while(it.hasNext()){
	// Map.Entry entry = (Map.Entry) it.next();
	// String term1 = (String) entry.getKey();
	// Map map = (Map) entry.getValue();
	// Iterator iter = map.entrySet().iterator();
	// String lineToWrite = term1 ;
	//
	// while(iter.hasNext()){
	// Map.Entry otherEntry = (Map.Entry) iter.next();
	// String term2 = (String) otherEntry.getKey();
	// int intersectionCount = (Integer) otherEntry.getValue();
	// if(term1.equals(term2)){
	// lineToWrite = lineToWrite +"\t"+ "(" + term2 + ","+ "1.0" +")";
	//
	// }else{
	//
	// Integer totalCountOfTerm1 = (Integer) dataMap.get(term1).get(term1);
	// Integer totalCountOfTerm2 = (Integer) dataMap.get(term2).get(term2);
	//
	//
	// int unionCount = totalCountOfTerm1+totalCountOfTerm2-intersectionCount;
	// double sim = (double)intersectionCount/(double)unionCount;
	// lineToWrite = lineToWrite +"\t"+ "(" + term2 + ","+ sim +")";
	//
	// }
	// }
	// outFile.println(lineToWrite);
	// outFile.flush();
	// System.out.println("Printed: " + term1 + " to file");
	// lineToWrite = "";
	// }
	// outFile.close();
	//
	// }
	//

	public static void generateListTermFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");
		PrintWriter outFile1 = new PrintWriter(new FileWriter("data.txt", true));
		String lineToWrite = "";
		int i = 0;
		int lineno = 0;
		String prevListId = "";
		while (iterator.hasNext())
		{
			lineno = lineno + 1;
			String line = iterator.nextLine();
			String values[] = line.split("\t");
			if (i == 0)
			{
				lineToWrite = values[1] + "\t\t" + values[0];
				i = i + 1;
				prevListId = values[1];
			} else
			{
				if (prevListId.equals(values[1]))
				{
					lineToWrite = lineToWrite + "\t" + values[0];
				} else
				{
					outFile1.println(lineToWrite);
					outFile1.flush();
					lineToWrite = values[1] + "\t\t" + values[0];
					prevListId = values[1];
				}
			}
			if (lineno % 10000000 == 0)
			{
				System.out.println("Completed:" + lineno);
			}
		}
		outFile1.println(lineToWrite);
		outFile1.flush();
		outFile1.close();
		System.out.println("Finished..");
	}

	public static void generatedSortedFile_Path(String fileName, String outputPath) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

		// 2016/8/23
		// File file1 = new File("smalltermPair.txt");
		// file1 = new File("smallterm.txt");

		File file1 = new File(outputPath + "smalltermPair.txt");
		if (file1.exists())
		{
			file1.delete();
		}
		file1 = new File(outputPath + "smallterm.txt");
		if (file1.exists())
		{
			file1.delete();
		}
		PrintWriter outFile1 = new PrintWriter(new FileWriter(outputPath + "smalltermPair.txt", true));
		PrintWriter outFile2 = new PrintWriter(new FileWriter(outputPath + "smallterm.txt", true));
		int k = 0;
		while (iterator.hasNext())
		{
			String line = iterator.nextLine();
			String values[] = line.split("\t\t");
			String listId = values[0];
			String terms = values[1];
			String termVal[] = terms.split("\t");
			for (int i = 0; i < termVal.length; i++)
			{
				for (int j = i + 1; j < termVal.length; j++)
				{
					String termPairToWrite = termVal[i] + "\t" + termVal[j] + "\t\t" + 1;
					outFile1.println(termPairToWrite);
					outFile1.flush();
				}
				String termToWrite = termVal[i] + "\t" + 1;
				outFile2.println(termToWrite);
				outFile2.flush();
			}
			k = k + 1;
			// System.out.println("Finished line no:" + k);
		}

	}

	public static void generatedSortedFile_Path_SEISA(String fileName, String outputPath) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

		// 2016/8/23
		// File file1 = new File("smalltermPair.txt");
		// file1 = new File("smallterm.txt");

		File file1 = new File(outputPath + file.getName() + "_smalltermPair.txt");
		if (file1.exists())
		{
			file1.delete();
		}
		file1 = new File(outputPath + file.getName() + "_smallterm.txt");
		if (file1.exists())
		{
			file1.delete();
		}
		PrintWriter outFile1 = new PrintWriter(
				new FileWriter(outputPath + file.getName() + "_smalltermPair.txt", true));
		PrintWriter outFile2 = new PrintWriter(new FileWriter(outputPath + file.getName() + "_smallterm.txt", true));
		int k = 0;
		while (iterator.hasNext())
		{
			String line = iterator.nextLine();
			String values[] = line.split("\t\t");
			String listId = values[0];
			String terms = values[1];
			String termVal[] = terms.split("\t");
			for (int i = 0; i < termVal.length; i++)
			{
				for (int j = i + 1; j < termVal.length; j++)
				{
					String termPairToWrite = termVal[i] + "\t" + termVal[j] + "\t\t" + 1;
					outFile1.println(termPairToWrite);
					outFile1.flush();
				}
				String termToWrite = termVal[i] + "\t" + 1;
				outFile2.println(termToWrite);
				outFile2.flush();
			}
			k = k + 1;
			// System.out.println("Finished line no:" + k);
		}

	}

	public static void generatedSortedFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");

		// 2016/8/23
		// File file1 = new File("smalltermPair.txt");
		// file1 = new File("smallterm.txt");

		File file1 = new File("D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\smalltermPair.txt");
		if (file1.exists())
		{
			file1.delete();
		}
		file1 = new File("D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\smallterm.txt");
		if (file1.exists())
		{
			file1.delete();
		}
		PrintWriter outFile1 = new PrintWriter(
				new FileWriter("D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\smalltermPair.txt", true));
		PrintWriter outFile2 = new PrintWriter(
				new FileWriter("D:\\CodePath\\dataset\\full_list_clean\\tenToFifty\\smallterm.txt", true));
		int k = 0;
		while (iterator.hasNext())
		{
			String line = iterator.nextLine();
			String values[] = line.split("\t\t");
			String listId = values[0];
			String terms = values[1];
			String termVal[] = terms.split("\t");
			for (int i = 0; i < termVal.length; i++)
			{
				for (int j = i + 1; j < termVal.length; j++)
				{
					String termPairToWrite = termVal[i] + "\t" + termVal[j] + "\t\t" + 1;
					outFile1.println(termPairToWrite);
					outFile1.flush();
				}
				String termToWrite = termVal[i] + "\t" + 1;
				outFile2.println(termToWrite);
				outFile2.flush();
			}
			k = k + 1;
			// System.out.println("Finished line no:" + k);
		}

	}

	public static void main(String[] args)
	{
		// String fileName = "/Users/sanket/Desktop/SEISA_Project/termid-listids.txt";
		String fileName = "data_aa";
		try
		{
			// //readDataFileAndBuildGraph(fileName);
			// // generateListTermPairFile(fileName);
			// Map map = generateListTermPairFile(fileName);
			// generatePairWiseMatrix(map);

			// generatePairWiseSimScore(fileName);
			// generateListTermFile("sortedSeisaFile.txt");
			generatedSortedFile(fileName);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 6312442
		System.out.println("Done..");
	}

}
