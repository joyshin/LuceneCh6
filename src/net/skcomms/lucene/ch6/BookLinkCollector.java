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

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.Scorer;

// From chapter 6
public class BookLinkCollector extends Collector {
  private final Map<String, String> documents = new HashMap<String, String>();
  private Scorer scorer;
  private String[] urls;
  private String[] titles;

  @Override
  public boolean acceptsDocsOutOfOrder() {
    return true; // #A
  }

  @Override
  public void collect(int docID) {
    try {
      String url = this.urls[docID]; // #C
      String title = this.titles[docID]; // #C
      this.documents.put(url, title); // #C
      System.out.println(title + ":" + this.scorer.score());
    } catch (IOException e) {
      // ignore
    }
  }

  public Map<String, String> getLinks() {
    return Collections.unmodifiableMap(this.documents);
  }

  @Override
  public void setNextReader(IndexReader reader, int docBase) throws IOException {
    this.urls = FieldCache.DEFAULT.getStrings(reader, "url"); // #B
    this.titles = FieldCache.DEFAULT.getStrings(reader, "title2"); // #B
  }

  @Override
  public void setScorer(Scorer scorer) {
    this.scorer = scorer;
  }
}

/*
 * #A Accept docIDs out of order #B Load FieldCache values #C Store details for
 * the match
 */
