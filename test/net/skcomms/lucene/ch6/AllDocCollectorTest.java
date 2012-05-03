package net.skcomms.lucene.ch6;

import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.junit.Test;

public class AllDocCollectorTest {

    @Test
    public void testAllCollectingLucene() throws Exception {
        Directory dir = TestUtil.getBookIndexDirectory();
        TermQuery query = new TermQuery(new Term("contents", "action"));
        IndexSearcher searcher = new IndexSearcher(dir);

        AllDocCollector allDocCollector = new AllDocCollector();
        searcher.search(query, allDocCollector);

        List<ScoreDoc> scoreDocs = allDocCollector.getHits();

        for (ScoreDoc scoreDoc : scoreDocs) {
            System.out.println(scoreDoc.toString());
        }

        System.out.println("\n");
        searcher.close();
    }

}
