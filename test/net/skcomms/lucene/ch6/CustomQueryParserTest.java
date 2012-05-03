package net.skcomms.lucene.ch6;

import static org.junit.Assert.assertTrue;
import net.skcomms.lucene.ch6.CustomQueryParser;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.junit.Test;

public class CustomQueryParserTest {

    @Test
    public void testCustomQueryParser() {
        QueryParser parser = new CustomQueryParser(Version.LUCENE_35, "field",
                new StandardAnalyzer(Version.LUCENE_35));

        try {
            parser.parse("a?t");// 내부적으로 제공하는 QueryParser를 이용한다.
            System.out.println("Wildcard queries should not be allowed");
        } catch (ParseException expected) {
            // expected
            assertTrue(true);
            System.out.println("parser.parse(\"a?t\")");
        }

        try {
            parser.parse("xunit~");
            System.out.println("Fuzzy queries should not be allowed");
        } catch (ParseException expected) {
            // expected
            assertTrue(true);
            System.out.println("parser.parse(\"xunit~\")");
        }
    }
}
