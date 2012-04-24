package net.skcomms.lucene.ch6;

/*public class SearchServlet extends HttpServlet {
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

 * 1 Handle exception 2 Perfom search and render results

 }*/
