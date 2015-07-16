package ipvs_is.trial;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.*;
import static org.apache.uima.fit.factory.CollectionReaderFactory.*;
import static org.apache.uima.fit.pipeline.SimplePipeline.*;
import ipvs_is.database.DatabaseConnectionHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;*/
import java.io.InputStream;



import javax.servlet.*;

import org.apache.commons.jxpath.ri.model.beans.LangAttributePointer;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;
import org.knallgrau.utils.textcat.TextCategorizer;

import de.tudarmstadt.ukp.dkpro.core.io.text.StringReader;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xml.XmlWriterInline;
import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
public class Pipeline 
{
	//public static String filePath;
	
	/*public Pipeline(String path)
	{
		filePath=path;
	}*/
	public void RunPipelineForFileUpload(String filePath )throws Exception
	{

		String fileText;
			StringBuilder sb;
			
			BufferedReader br = new BufferedReader(new FileReader(filePath));
		    try {
		        sb = new StringBuilder();
		        String line = br.readLine();

	        while (line != null) {
		            sb.append(line);
		            sb.append(System.lineSeparator());
		            line = br.readLine();
		        }
		        fileText = sb.toString();
		    } finally {
		        br.close();
		    }
		    
		    TextCategorizer guesser = new TextCategorizer();
		    String language = guesser.categorize(fileText);
		    
			System.out.println(sb.toString());
			DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
			databaseConnectionHandler.insertDataSourceContent(sb.toString(), "file");
		System.out.println(language + ": " + language.substring(0, 2).toLowerCase());
		
		
			CollectionReaderDescription cr = createReaderDescription(
					TextReader.class, TextReader.PARAM_SOURCE_LOCATION,
					filePath, TextReader.PARAM_LANGUAGE, language.substring(0, 2).toLowerCase());
		/*CollectionReaderDescription cr = createReaderDescription(
				StringReader.class, StringReader.PARAM_DOCUMENT_TEXT,
				"", StringReader.PARAM_LANGUAGE, language.substring(0, 2).toLowerCase());*/
		
			AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);
			AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
			AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
			AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
			AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
			AnalysisEngineDescription tagger4 = createEngineDescription(
					JazzyChecker.class, JazzyChecker.PARAM_MODEL_LOCATION,
					new File("resources/words.utf-8.txt"));

			AnalysisEngineDescription cc = createEngineDescription(
					CasDumpWriter.class, CasDumpWriter.PARAM_OUTPUT_FILE,
					"target/Trial.txt");
			
//			AnalysisEngineDescription cc = createEngineDescription(
//					XmlWriterInline.class, XmlWriterInline.PARAM_TARGET_LOCATION, "target/xmlTrial.txt");
	//
		runPipeline(cr,seg, tagger/*, tagger1, tagger2, tagger3, tagger4*/, cc);
		System.out.println("Completed");
	//	
	//*/
//			/*
//			 * TextCategorizer guesser = new TextCategorizer();
//			 * System.out.println(guesser.categorize(cr));
//			 */
		System.out.println("File write complete! Saved to: "+new File("target/Trial.txt").getAbsolutePath());
	}
	

	public void RunPipelineForTextInput(String Text )throws Exception
	{

		
		    
		    TextCategorizer guesser = new TextCategorizer();
		    String language = guesser.categorize(Text);
		    
			System.out.println(Text.toString());
		System.out.println(language + ": " + language.substring(0, 2).toLowerCase());
		
		
		CollectionReaderDescription cr = createReaderDescription(
				StringReader.class, StringReader.PARAM_DOCUMENT_TEXT,
				Text, TextReader.PARAM_LANGUAGE, language.substring(0, 2).toLowerCase());


		/*CollectionReaderDescription cr = createReaderDescription(
				StringReader.class, StringReader.PARAM_DOCUMENT_TEXT,
				"", StringReader.PARAM_LANGUAGE, language.substring(0, 2).toLowerCase());*/
		
			AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);
			AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
			AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
			AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
			AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
			AnalysisEngineDescription tagger4 = createEngineDescription(
					JazzyChecker.class, JazzyChecker.PARAM_MODEL_LOCATION,
					new File("resources/words.utf-8.txt"));

			AnalysisEngineDescription cc = createEngineDescription(
					CasDumpWriter.class, CasDumpWriter.PARAM_OUTPUT_FILE,
					"target/Trial.txt");
			
//			AnalysisEngineDescription cc = createEngineDescription(
//					XmlWriterInline.class, XmlWriterInline.PARAM_TARGET_LOCATION, "target/xmlTrial.txt");
	//
		runPipeline(cr,seg, tagger, tagger1, tagger2, tagger3, tagger4, cc);
		System.out.println("Completed");
	//	
	//*/
//			/*
//			 * TextCategorizer guesser = new TextCategorizer();
//			 * System.out.println(guesser.categorize(cr));
//			 */
		System.out.println("File write complete! Saved to: "+new File("target/Trial.txt").getAbsolutePath());
	}
	

	
			

		
}
