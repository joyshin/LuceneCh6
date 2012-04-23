package net.skcomms.lucene.joyshin;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class SearchServlet extends HttpServlet {
  // import javax.servlet.http.*;
  // import javax.servlet.*;

  private IndexSearcher searcher;

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

    QueryParser parser = new NumericDateRangeQueryParser(Version.LUCENE_35,
        "contents",
        new StandardAnalyzer(Version.LUCENE_35));

    parser.setLocale(request.getLocale());
    parser.setDateResolution(DateTools.Resolution.DAY);

    Query query = null;
    try {
      query = parser.parse(request.getParameter("q"));
    } catch (ParseException e) {
      e.printStackTrace(System.err); // 1
    }

    TopDocs docs = this.searcher.search(query, 10); // 2
  }
  /*
   * 1 Handle exception 2 Perfom search and render results
   */
}
