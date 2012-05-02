package net.skcomms.lucene.ch6.test;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import net.skcomms.lucene.ch6.BookLinkCollector;
import net.skcomms.lucene.ch6.TestUtil;

import org.apache.lucene.index.IndexReader;
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
        Directory directory = TestUtil.getBookIndexDirectory();

        IndexReader reader = IndexReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        BookLinkCollector collector = new BookLinkCollector();
        searcher.search(query, collector);

        Map linkMap = collector.getLinks();
        assertEquals("Lucene in Action", linkMap.get("http://www.manning.com/hatcher2/"));
        searcher.close();
    }
}
