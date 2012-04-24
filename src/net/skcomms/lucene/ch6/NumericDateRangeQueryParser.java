package net.skcomms.lucene.ch6;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

public class NumericDateRangeQueryParser extends QueryParser {

  public NumericDateRangeQueryParser(Version matchVersion, String field, Analyzer a) {
    super(matchVersion, field, a);
  }

  @Override
  public Query getRangeQuery(String field, String part1, String part2, boolean inclusive)
      throws ParseException {
    TermRangeQuery query = (TermRangeQuery) super.getRangeQuery(field, part1, part2, inclusive);
    if ("pubmonth".equals(field)) {
      return NumericRangeQuery.newIntRange("pubmonth", Integer.parseInt(query.getLowerTerm()),
          Integer.parseInt(query.getUpperTerm()), query.includesLower(), query.includesUpper());
    } else {

      return query;
    }
  }
}