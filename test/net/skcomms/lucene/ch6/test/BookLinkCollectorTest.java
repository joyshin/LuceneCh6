package net.skcomms.lucene.ch6.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.skcomms.lucene.ch6.BookLinkCollector;
import net.skcomms.lucene.ch6.TestUtil;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.junit.Test;

public class BookLinkCollectorTest {
  public static final String INDEX_DIRECTORY = "indexDirectory";

  @Test
  public void testCollecting() throws Exception {
    TermQuery query = new TermQuery(new Term("contents", "junit"));
    // IndexSearcher searcher = new
    // IndexSearcher(TestUtil.getBookIndexDirectory());
    Directory directory = TestUtil.getBookIndexDirectory();
    IndexSearcher searcher = new IndexSearcher(directory);

    BookLinkCollector collector = new BookLinkCollector();
    searcher.search(query, collector);

    Map linkMap = collector.getLinks();
    assertEquals("java development with ant", linkMap.get("http://www.manning.com/antbook"));
    searcher.close();
  }
}
