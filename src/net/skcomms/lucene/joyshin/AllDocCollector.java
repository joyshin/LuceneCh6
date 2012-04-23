package net.skcomms.lucene.joyshin;

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
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;

// From chapter 6

/**
 * Gathers all documents from a search.
 */

public class AllDocCollector extends Collector {
  List<ScoreDoc> docs = new ArrayList<ScoreDoc>();
  private Scorer scorer;
  private int docBase;

  @Override
  public boolean acceptsDocsOutOfOrder() {
    return true;
  }

  @Override
  public void collect(int doc) throws IOException {
    this.docs.add(
        new ScoreDoc(doc + this.docBase, // #A
            this.scorer.score())); // #B
  }

  public List<ScoreDoc> getHits() {
    return this.docs;
  }

  public void reset() {
    this.docs.clear();
  }

  @Override
  public void setNextReader(IndexReader reader, int docBase) {
    this.docBase = docBase;
  }

  @Override
  public void setScorer(Scorer scorer) {
    this.scorer = scorer;
  }
}

/*
 * #A Create absolute docID #B Record score
 */
