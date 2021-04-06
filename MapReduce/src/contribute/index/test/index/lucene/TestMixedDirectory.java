package org.apache.hadoop.contrib.index.lucene;

import java.io.IOException;

import junit.framework.TestCase;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.IndexOutput;
import org.apache.lucene.store.RAMDirectory;

public class TestMixedDirectory extends TestCase {
  private int numDocsPerUpdate = 10;
  private int maxBufferedDocs = 2;

  public void testMixedDirectoryAndPolicy() throws IOException {
    Directory readDir = new RAMDirectory();
    updateIndex(readDir, 0, numDocsPerUpdate,
        new KeepOnlyLastCommitDeletionPolicy());

    verify(readDir, numDocsPerUpdate);

    IndexOutput out =
        readDir.createOutput("_" + (numDocsPerUpdate / maxBufferedDocs + 2)
            + ".cfs");
    out.writeInt(0);
    out.close();

    Directory writeDir = new RAMDirectory();
    Directory mixedDir = new MixedDirectory(readDir, writeDir);
    updateIndex(mixedDir, numDocsPerUpdate, numDocsPerUpdate,
        new MixedDeletionPolicy());

    verify(readDir, numDocsPerUpdate);
    verify(mixedDir, 2 * numDocsPerUpdate);
  }

  public void updateIndex(Directory dir, int base, int numDocs,
      IndexDeletionPolicy policy) throws IOException {
    IndexWriter writer =
        new IndexWriter(dir, false, new StandardAnalyzer(), policy);
    writer.setMaxBufferedDocs(maxBufferedDocs);
    writer.setMergeFactor(1000);
    for (int i = 0; i < numDocs; i++) {
      addDoc(writer, base + i);
    }
    writer.close();
  }

  private void addDoc(IndexWriter writer, int id) throws IOException {
    Document doc = new Document();
    doc.add(new Field("id", String.valueOf(id), Field.Store.YES,
        Field.Index.UN_TOKENIZED));
    doc.add(new Field("content", "apache", Field.Store.NO,
        Field.Index.TOKENIZED));
    writer.addDocument(doc);
  }

  private void verify(Directory dir, int expectedHits) throws IOException {
    IndexSearcher searcher = new IndexSearcher(dir);
    Hits hits = searcher.search(new TermQuery(new Term("content", "apache")));
    int numHits = hits.length();

    assertEquals(expectedHits, numHits);

    int[] docs = new int[numHits];
    for (int i = 0; i < numHits; i++) {
      Document hit = hits.doc(i);
      docs[Integer.parseInt(hit.get("id"))]++;
    }
    for (int i = 0; i < numHits; i++) {
      assertEquals(1, docs[i]);
    }

    searcher.close();
  }

}
