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

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;
import org.apache.lucene.search.SortField;

// From chapter 6
public class DistanceComparatorSource extends FieldComparatorSource { // #1
	private class DistanceScoreDocLookupComparator // #4
			extends FieldComparator {
		private int[] xDoc, yDoc; // #5
		private final float[] values; // #6
		private float bottom; // #7
		String fieldName;

		public DistanceScoreDocLookupComparator(final String fieldName, final int numHits) throws IOException {
			values = new float[numHits];
			this.fieldName = fieldName;
		}

		@Override
		public int compare(final int slot1, final int slot2) { // #10
			if (values[slot1] < values[slot2]) {
				return -1; // #10
			}
			if (values[slot1] > values[slot2]) {
				return 1; // #10
			}
			return 0; // #10
		}

		@Override
		public int compareBottom(final int doc) { // #12
			final float docDistance = getDistance(doc);
			if (bottom < docDistance) {
				return -1; // #12
			}
			if (bottom > docDistance) {
				return 1; // #12
			}
			return 0; // #12
		}

		@Override
		public void copy(final int slot, final int doc) { // #13
			values[slot] = getDistance(doc); // #13
		}

		private float getDistance(final int doc) { // #9
			final int deltax = xDoc[doc] - x; // #9
			final int deltay = yDoc[doc] - y; // #9
			return (float) Math.sqrt(deltax * deltax + deltay * deltay); // #9
		}

		@Override
		public void setBottom(final int slot) { // #11
			bottom = values[slot];
		}

		@Override
		public void setNextReader(final IndexReader reader, final int docBase) throws IOException {
			xDoc = FieldCache.DEFAULT.getInts(reader, "x"); // #8
			yDoc = FieldCache.DEFAULT.getInts(reader, "y"); // #8
		}

		public int sortType() {
			return SortField.CUSTOM;
		}

		@Override
		public Comparable value(final int slot) { // #14
			return new Float(values[slot]); // #14
		} // #14
	}

	private final int x;

	private final int y;

	public DistanceComparatorSource(final int x, final int y) { // #2
		this.x = x;
		this.y = y;
	}

	@Override
	public FieldComparator newComparator(final java.lang.String fieldName, // #3
			final int numHits, final int sortPos, // #3
			final boolean reversed) // #3
			throws IOException { // #3
		return new DistanceScoreDocLookupComparator(fieldName, numHits);
	}

	@Override
	public String toString() {
		return "Distance from (" + x + "," + y + ")";
	}
}

/*
 * #1 Extend FieldComparatorSource #2 Give constructor base location #3 Create
 * comparator #4 FieldComparator implementation #5 Array of x, y per document #6
 * Distances for documents in the queue #7 Worst distance in the queue #8 Get x,
 * y values from field cache #9 Compute distance for one document #10 Compare
 * two docs in the top N #11 Record worst scoring doc in the top N #12 Compare
 * new doc to worst scoring doc #13 Insert new doc into top N #14 Extract value
 * from top N
 */

