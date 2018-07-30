/*
 * Indexer.java
 *
 * Created on 6 March 2006, 13:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package setexpansion.algorithm;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import setexpansion.entity.TermInfo;

/**
 * This class is used to generate the lucene index and perform text search.
 * 
 * @author Sanket
 */
public class Indexer
{

	/** Creates a new instance of Indexer */
	public Indexer()
	{
	}

	private IndexWriter indexWriter = null;

	public IndexWriter getIndexWriter(boolean create) throws IOException
	{

		if (indexWriter == null)
		{
			Path path = FileSystems.getDefault().getPath(null, "index_small_term_pair");
			Directory indexDir = FSDirectory.open(path);
			// 2016/8/22 Directory indexDir = FSDirectory.open(new File("index_small_term_pair"));
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			// 2016/8/22 IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_41, new
			// StandardAnalyzer(Version.LUCENE_CURRENT));
			indexWriter = new IndexWriter(indexDir, config);
		}
		return indexWriter;
	}

	public void closeIndexWriter() throws IOException
	{
		if (indexWriter != null)
		{
			indexWriter.close();
		}
	}

	public void indexTerms(TermInfo term) throws IOException
	{

		IndexWriter writer = getIndexWriter(false);
		Document doc = new Document();
		doc.add(new StringField("count", term.getTermCount(), Field.Store.YES));
		doc.add(new TextField("term", term.getTerm(), Field.Store.YES));
		writer.addDocument(doc);
	}

	public void rebuildIndexes() throws IOException
	{
		//
		// Erase existing index
		//
		getIndexWriter(true);
		//
		// Index all Accommodation entries
		//
		File file = new File("smallTermPairCount.txt");
		int count = 0;
		try
		{
			LineIterator iterator = FileUtils.lineIterator(file, "UTF-8");
			while (iterator.hasNext())
			{
				String line = iterator.nextLine();
				String value[] = line.split("\t\t");
				String valTerms[] = value[0].split("\t");
				TermInfo obj = new TermInfo();
				obj.setTerm(valTerms[0] + valTerms[1]);
				obj.setTermCount(value[1]);
				// obj.setTerm("a"+count);
				// obj.setTermCount(""+count);
				indexTerms(obj);
				count = count + 1;
				if (count % 100000 == 0)
				{
					System.out.println("Done :" + count);
				}
			}

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		// Don't forget to close the index writer when done
		//
		closeIndexWriter();
	}

	public static void main(String[] args) throws IOException, ParseException
	{
		System.out.println("rebuildIndexes");
		Indexer indexer = new Indexer();
		try
		{
			indexer.rebuildIndexes();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("rebuildIndexes done");
		// SearchEngine se = new SearchEngine();
		// TopDocs topDocs = se.performSearch("9th OHAJDL9th SOJHL", 1);
		// ScoreDoc[] hits = topDocs.scoreDocs;
		// for (int i = 0; i < hits.length; i++) {
		// Document doc = se.getDocument(hits[i].doc);
		// System.out.println(doc.get("term") + " " + doc.get("count"));
		//
		// }
		// System.out.println("performSearch done");

	}

}
