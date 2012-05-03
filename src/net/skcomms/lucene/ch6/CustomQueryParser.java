package net.skcomms.lucene.ch6;

/**
 * Copyright Manning Publications Co.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific lan      
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.util.Version;

// From chapter 6
public class CustomQueryParser extends QueryParser {
    public CustomQueryParser(Version matchVersion, String field, Analyzer analyzer) {
        super(matchVersion, field, analyzer);
    }

    /**
     * Replace PhraseQuery with SpanNearQuery to force in-order phrase matching
     * rather than reverse.
     */
    @Override
    protected Query getFieldQuery(String field, String queryText, int slop) throws ParseException {
        // �˻��� �м� ��� ���� ���¸� �����ϴ� �۾��� QueryParser�� ������ ���� �״�� ���
        Query orig = super.getFieldQuery(field, queryText, slop); // #1

        // PhraseQuery��� �ٸ� ���Ƿ� �����ϰ�, �ƴ϶�� �ش� ���Ǹ� ��� ��ȯ
        if (!(orig instanceof PhraseQuery)) { // #2
            return orig; // #2
        } // #2

        PhraseQuery pq = (PhraseQuery) orig;
        Term[] terms = pq.getTerms(); // #3 // ������ PhraseQuery���� ������� �����´�
        SpanTermQuery[] clauses = new SpanTermQuery[terms.length];
        for (int i = 0; i < terms.length; i++) {
            clauses[i] = new SpanTermQuery(terms[i]);
        }

        // ���������� ������ PhraseQuery���� ������ ��� ���� ���ڷ� SpannearQuery�� �����Ѵ�
        SpanNearQuery query = new SpanNearQuery(clauses, slop, true); // #4

        return query;
    }

    /*
     * #1 Delegate to QueryParser's implementation #2 Only override PhraseQuery
     * #3 Pull all terms #4 Create SpanNearQuery
     */

    @Override
    protected Query getFuzzyQuery(String field, String term, float minSimilarity) throws ParseException {
        throw new ParseException("Fuzzy queries not allowed");
    }

    @Override
    protected final Query getWildcardQuery(String field, String termStr) throws ParseException {
        throw new ParseException("Wildcard not allowed");
    }

}
