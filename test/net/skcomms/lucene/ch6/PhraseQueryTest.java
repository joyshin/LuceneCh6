package net.skcomms.lucene.ch6;

import static org.junit.Assert.assertTrue;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class PhraseQueryTest {

    @Test
    public void testPhraseQuery() throws ParseException {
        CustomQueryParser parser = new CustomQueryParser(Version.LUCENE_35, "field", new StandardAnalyzer(
                        Version.LUCENE_35));

        Query tQuery = parser.parse("singleTerm");
        assertTrue("TermQuery", tQuery instanceof TermQuery);

        // Query snQuery = parser.parse("\"phrase Query\"");
        Query snQuery = parser.parse("\"a Query\"");
        assertTrue("SpanNearQuery", snQuery instanceof SpanNearQuery);

    }
}
