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

import junit.framework.TestCase;
import net.skcomms.lucene.ch6.DistanceComparatorSource;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FieldDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

// From chapter 6
public class DistanceSortingTest extends TestCase {
    private RAMDirectory  directory;
    private IndexSearcher searcher;
    private Query         query;

    private void addPoint(final IndexWriter writer, final String name, final String type, final int x, final int y)
                    throws IOException {
        final Document doc = new Document();
        doc.add(new Field("name", name, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("type", type, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("x", Integer.toString(x), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field("y", Integer.toString(y), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
        writer.addDocument(doc);
    }

    private void dumpDocs(final Sort sort, final TopFieldDocs docs) throws IOException {
        System.out.println("Sorted by: " + sort);
        final ScoreDoc[] scoreDocs = docs.scoreDocs;
        for (int i = 0; i < scoreDocs.length; i++) {
            final FieldDoc fieldDoc = (FieldDoc) scoreDocs[i];
            final Float distance = (Float) fieldDoc.fields[0];
            final Document doc = this.searcher.doc(fieldDoc.doc);
            System.out.println("   " + doc.get("name") + " @ (" + doc.get("x") + "," + doc.get("y") + ") -> "
                            + distance);
        }
    }

    @Override
    protected void setUp() throws Exception {
        this.directory = new RAMDirectory();
        final IndexWriter writer = new IndexWriter(this.directory, new WhitespaceAnalyzer(),
                        IndexWriter.MaxFieldLength.UNLIMITED);
        this.addPoint(writer, "El Charro", "restaurant", 1, 2);
        this.addPoint(writer, "Cafe Poca Cosa", "restaurant", 5, 9);
        this.addPoint(writer, "Los Betos", "restaurant", 9, 6);
        this.addPoint(writer, "Nico's Taco Shop", "restaurant", 3, 8);

        writer.close();

        this.searcher = new IndexSearcher(this.directory);

        this.query = new TermQuery(new Term("type", "restaurant"));
    }

    /*
     * #1 Specify maximum hits returned #2 Total number of hits #3 Return total
     * number of documents #4 Get sorting values #5 Give value of first
     * computation #6 Get Document
     */
    @Test
    public void testNeareastRestaurantToWork() throws Exception {
        System.out.println("testNeareastRestaurantToWork()");

        final Sort sort = new Sort(new SortField("unused", new DistanceComparatorSource(10, 10)));

        final TopFieldDocs docs = this.searcher.search(this.query, null, 3, sort); // #1

        assertEquals(4, docs.totalHits); // #2
        assertEquals(3, docs.scoreDocs.length); // #3

        final FieldDoc fieldDoc = (FieldDoc) docs.scoreDocs[0]; // #4

        assertEquals("(10,10) -> (9,6) = sqrt(17)", new Float(Math.sqrt(17)), fieldDoc.fields[0]); // #5
        System.out.println("(10,10) -> (9,6) = sqrt(17) ||" + " Math.sqrt(17) : " + new Float(Math.sqrt(17)).toString()
                        + " || fieldDoc.fields[0] : " + fieldDoc.fields[0].toString());

        final Document document = this.searcher.doc(fieldDoc.doc); // #6
        assertEquals("Los Betos", document.get("name"));
        System.out.println("Los Betos || " + "document.get(\"name\") : " + document.get("name").toString() + "\n");

        // dumpDocs(sort, docs);
    }

    @Test
    public void testNearestRestaurantToHome() throws Exception {
        System.out.println("testNearestRestaurantToHome()");

        final Sort sort = new Sort(new SortField("unused", new DistanceComparatorSource(0, 0)));

        final TopDocs hits = this.searcher.search(this.query, null, 10, sort);

        assertEquals("closest", "El Charro", this.searcher.doc(hits.scoreDocs[0].doc).get("name"));
        System.out.println("Closest => " + "El Charro || " + "searcher.doc(hits.scoreDocs[0].doc).get(\"name\") : "
                        + this.searcher.doc(hits.scoreDocs[0].doc).get("name").toString());

        assertEquals("furthest", "Los Betos", this.searcher.doc(hits.scoreDocs[3].doc).get("name"));
        System.out.println("Furthest => " + "Los Betos || " + "searcher.doc(hits.scoreDocs[3].doc).get(\"name\") : "
                        + this.searcher.doc(hits.scoreDocs[3].doc).get("name").toString());
    }

}
