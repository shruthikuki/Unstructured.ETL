package ipvs_is.trial;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.*;
import static org.apache.uima.fit.factory.CollectionReaderFactory.*;
import static org.apache.uima.fit.pipeline.SimplePipeline.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.jxpath.ri.model.beans.LangAttributePointer;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;

import org.knallgrau.utils.textcat.TextCategorizer;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xml.XmlWriterInline;
import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class Trial {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://bd6c79c61fba4f:9bcd28a7@us-cdbr-iron-east-02.cleardb.net:3306/ad_97053fa1b7b08e0";

	// Database credentials
	static final String USER = "bd6c79c61fba4f";
	static final String PASS = "9bcd28a7";


	public static void main(String[] args) throws Exception {

		
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT id, name FROM Trial";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int id = rs.getInt("id");
				String first = rs.getString("name");

				// Display values
				System.out.print("ID: " + id);

				System.out.print(", First: " + first);

			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}// end finally try
		}// end try

	

		
//		String fileText;
//		StringBuilder sb;
//		
//		BufferedReader br = new BufferedReader(new FileReader("resources/sample.txt"));
//	    try {
//	        sb = new StringBuilder();
//	        String line = br.readLine();
//
//	        while (line != null) {
//	            sb.append(line);
//	            sb.append(System.lineSeparator());
//	            line = br.readLine();
//	        }
//	        fileText = sb.toString();
//	    } finally {
//	        br.close();
//	    }
//	    
//	    TextCategorizer guesser = new TextCategorizer();
//	    String language = guesser.categorize(fileText);
//	    
//		System.out.println(sb.toString());
//		System.out.println(language + ": " + language.substring(0, 2).toLowerCase());
//		
//		
//		CollectionReaderDescription cr = createReaderDescription(
//				TextReader.class, TextReader.PARAM_SOURCE_LOCATION,
//				"resources/sample.txt", TextReader.PARAM_LANGUAGE, language.substring(0, 2).toLowerCase());
//
//		AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);
//
//		AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
//		AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
//		AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
//		AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
//		AnalysisEngineDescription tagger4 = createEngineDescription(
//				JazzyChecker.class, JazzyChecker.PARAM_MODEL_LOCATION,
//				"resources/words.utf-8.txt");
//
////		AnalysisEngineDescription cc = createEngineDescription(
////				CasDumpWriter.class, CasDumpWriter.PARAM_OUTPUT_FILE,
////				"target/Trial.txt");
//		
//		AnalysisEngineDescription cc = createEngineDescription(
//				XmlWriterInline.class, XmlWriterInline.PARAM_TARGET_LOCATION, "target/xmlTrial.txt");
//
//		runPipeline(cr,seg, tagger, tagger1, tagger2, tagger3, tagger4, cc);
//	
//
//		/*
//		 * TextCategorizer guesser = new TextCategorizer();
//		 * System.out.println(guesser.categorize(cr));
//		 */
		

	}
}
