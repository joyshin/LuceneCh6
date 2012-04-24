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
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

// From chapter 6
public class NumericRangeQueryParser extends QueryParser {

    public NumericRangeQueryParser(Version matchVersion, String field, Analyzer a) {
        super(matchVersion, field, a);
    }

    @Override
    public Query getRangeQuery(String field, String part1, String part2, boolean inclusive) throws ParseException {
        TermRangeQuery query = (TermRangeQuery) // A
        super.getRangeQuery(field, part1, part2, // A
                        inclusive); // A
        if ("price".equals(field)) {
            return NumericRangeQuery.newDoubleRange( // B
                            "price", // B
                            Double.parseDouble( // B
                            query.getLowerTerm()), // B
                            Double.parseDouble( // B
                            query.getUpperTerm()), // B
                            query.includesLower(), // B
                            query.includesUpper()); // B
        } else {
            return query; // C
        }
    }

}
