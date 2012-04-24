package net.skcomms.lucene.ch6.test;

import java.util.Locale;

import junit.framework.TestCase;
import net.skcomms.lucene.ch6.NumericDateRangeQueryParser;
import net.skcomms.lucene.ch6.NumericRangeQueryParser;
import net.skcomms.lucene.ch6.TestUtil;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

public class NumericQueryParserTest extends TestCase {
    private Analyzer      analyzer;

    private IndexSearcher searcher;

    private Directory     dir;

    @Override
    protected void setUp() throws Exception {
        this.analyzer = new WhitespaceAnalyzer();
        this.dir = TestUtil.getBookIndexDirectory();
        this.searcher = new IndexSearcher(this.dir, true);
    }

    /*
     * #A Get super()'s default TermRangeQuery #B Create matching
     * NumericRangeQuery #C Return default TermRangeQuery
     */

    @Override
    protected void tearDown() throws Exception {
        this.searcher.close();
        this.dir.close();
    }

    public void testDateRangeQuery() throws Exception {
        String expression = "pubmonth:[01/01/2010 TO 06/01/2010]";

        QueryParser parser = new NumericDateRangeQueryParser(Version.LUCENE_30, "subject", this.analyzer);

        parser.setDateResolution("pubmonth", DateTools.Resolution.MONTH); // 1
        parser.setLocale(Locale.US);

        Query query = parser.parse(expression);
        System.out.println(expression + " parsed to " + query);

        TopDocs matches = this.searcher.search(query, 10);
        assertTrue("expecting at least one result !", matches.totalHits > 0);
    }

    /*
     * 1 Tell QueryParser date resolution
     */

    public void testDefaultDateRangeQuery() throws Exception {
        QueryParser parser = new QueryParser(Version.LUCENE_30, "subject", this.analyzer);
        Query query = parser.parse("pubmonth:[1/1/04 TO 12/31/04]");
        System.out.println("default date parsing: " + query);
    }

    public void testNumericRangeQuery() throws Exception {
        String expression = "price:[10 TO 20]";

        QueryParser parser = new NumericRangeQueryParser(Version.LUCENE_30, "subject", this.analyzer);

        Query query = parser.parse(expression);
        System.out.println(expression + " parsed to " + query);
    }
}