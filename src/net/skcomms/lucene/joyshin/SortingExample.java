package net.skcomms.lucene.joyshin;

import java.io.IOException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;

public class SortingExample {

	private Directory directory;
	
	public SortingExample(Directory directory) {
		this.directory = directory;
	}
	
	public void displayResults (Query query, Sort sort) throws IOException{
		IndexSearcher searcher = new IndexSearcher(this.directory);
		searcher.set
	}

}
