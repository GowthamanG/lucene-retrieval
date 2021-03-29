import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import offline.IndexDocument;
import online.SearchDocument;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class Main {

    public static void createIndexMusicFile(Analyzer analyzer, Directory directory) throws IOException {
        IndexDocument indexDocument = new IndexDocument(analyzer, directory);
        Document doc = null;

        try {
            File file = new File("src/main/resources/6000Songs.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));

            br.readLine(); // get rid of first line

            String data;
            while ((data = br.readLine()) != null) {
                String[] song_details = data.split("\\s*,(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)\\s*");

                doc = new Document();

                indexDocument.addField("ID", song_details[0], true, doc);
                indexDocument.addField("Artist", song_details[1], true, doc);
                indexDocument.addField("Song", song_details[2], true, doc);
                indexDocument.addField("Year", song_details[3], true, doc);
                indexDocument.addDocumentToIndex(doc);
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        indexDocument.closeIndexWriter();
    }

    public static void createIndexEnglishDict(Analyzer analyzer, Directory directory) throws IOException {
        IndexDocument indexDocument = new IndexDocument(analyzer, directory);
        Document doc = null;

        try {
            File myObj = new File("src/main/resources/fulldictionary00.txt");
            Scanner myReader = new Scanner(myObj);
            doc = new Document();

            String data = myReader.next().replaceAll("\"", "");
            String[] words = data.split(",");

            for(String s: words) {
                indexDocument.addField("Word", s, true, doc);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        indexDocument.closeIndexWriter();
    }

    public static void search(Analyzer analyzer, Directory directory) throws IOException, ParseException {
        SearchDocument searchDocument = new SearchDocument(analyzer, directory);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Type a song title you want to search for: ");
            String userInput = scanner.nextLine();

            if (userInput.equals("STOP")) {
                searchDocument.closerReader();
                scanner.close();
                System.exit(0);
            } else {
                searchDocument.search(userInput);
            }
        }
    }

    public static void main(String[] args) throws IOException, ParseException {

        Analyzer analyzer = new StandardAnalyzer();

        Path path1 = Paths.get("src/main/resources/index");
        Directory directory = FSDirectory.open(path1);

        //createIndexMusicFile(analyzer, directory);
        search(analyzer, directory);

    }
}
