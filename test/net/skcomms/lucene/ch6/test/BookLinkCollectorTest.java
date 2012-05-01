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
        Directory dir = TestUtil.getBookIndexDirectory();
        TermQuery query = new TermQuery(new Term("contents", "junit"));
        IndexSearcher searcher = new IndexSearcher(dir);

        BookLinkCollector collector = new BookLinkCollector();
        searcher.search(query, collector);

        Map<String, String> linkMap = collector.getLinks();
        assertEquals("ant in action",
                linkMap.get("http://www.manning.com/loughran"));
        searcher.close();
    }
}
