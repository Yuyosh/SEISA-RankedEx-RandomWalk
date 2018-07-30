package setexpansion.algorithm;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * This class is used by Lucene to perform search.
 * 
 * @author sanket
 *
 */
public class SearchEngine
{
	private IndexSearcher searcher = null;
	private QueryParser parser = null;

	/** Creates a new instance of SearchEngine */
	public SearchEngine(String fileName) throws IOException
	{
		Path path = FileSystems.getDefault().getPath(null, fileName);
		searcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(path)));
		parser = new QueryParser("term", new StandardAnalyzer());
		// 2016/8/22 parser = new QueryParser(Version.LUCENE_41, "term", new StandardAnalyzer(Version.LUCENE_41));
	}

	public TopDocs performSearch(String queryString, int n) throws IOException, ParseException
	{
		Query query = parser.parse(queryString);
		return searcher.search(query, n);
	}

	public Document getDocument(int docId) throws IOException
	{
		return searcher.doc(docId);
	}
}
