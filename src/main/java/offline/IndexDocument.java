package offline;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Field.Store;

import java.io.IOException;

public class IndexDocument {
    private IndexWriterConfig config;
    private IndexWriter writer;

    public IndexDocument(Analyzer analyzer, Directory directory) throws IOException {
        config = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(directory, config);
    }


    public void addField(String name, String value, Boolean store, Document doc) {
        Store fieldStore = store ? Store.YES: Store.NO;
        doc.add(new TextField(name, value, fieldStore));
    }

    public void addDocumentToIndex(Document doc) throws IOException {
        writer.addDocument(doc);
    }

    public void closeIndexWriter() throws IOException {
        writer.commit();
        writer.close();
    }
}
