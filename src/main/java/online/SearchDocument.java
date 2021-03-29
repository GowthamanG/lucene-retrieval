package online;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.apache.lucene.store.Directory;

import java.io.File;
import java.io.IOException;

public class SearchDocument {
    private Directory directory;
    private QueryParser parser;
    private IndexReader reader;
    private IndexSearcher searcher;

    private int NUM_RESULTS = 5; // Top 5 results, changable

    public SearchDocument(Analyzer analyzer, Directory directory) throws IOException {
        this.directory = directory;
        parser = new QueryParser("Song", analyzer); // Song => Title
        reader = DirectoryReader.open(this.directory);
        searcher = new IndexSearcher(reader);
    }

    public void search(String queryStringFromUser) throws ParseException, IOException {
        Query query = parser.parse(queryStringFromUser);
        TopDocs hits = searcher.search(query, NUM_RESULTS);


        // Present result
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            Document doc = searcher.doc(hits.scoreDocs[i].doc);
            System.out.printf(" %4d %1.3f %s %s %s %s\n",
                    i+1,
                    hits.scoreDocs[i].score,
                    doc.get("ID"),
                    doc.get("Artist"),
                    doc.get("Song"),
                    doc.get("Year"));

        }
    }

    public void spellChecker(Directory dir, String queryStringFromUser) throws IOException {

    }

    public void closerReader() throws IOException {
        reader.close();
    }
}
