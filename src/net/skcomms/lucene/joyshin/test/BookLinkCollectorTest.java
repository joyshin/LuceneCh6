package net.skcomms.lucene.joyshin.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.skcomms.lucene.joyshin.BookLinkCollector;
import net.skcomms.lucene.joyshin.TestUtil;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.junit.Test;

public class BookLinkCollectorTest {

  @Test
  public void testCollecting() throws Exception {
    TermQuery query = new TermQuery(new Term("contents", "junit"));
    IndexSearcher searcher = new IndexSearcher(TestUtil.getBookIndexDirectory());

    BookLinkCollector collector = new BookLinkCollector(searcher);
    searcher.search(query, collector);

    Map linkMap = collector.getLinks();
    assertEquals("java development with ant", linkMap.get("http://www.manning.com/antboot"));
    searcher.close();
  }

}
