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
import de.tudarmstadt.ukp.dkpro.core.tokit.GermanSeparatedParticleAnnotator;

public class Pipeline {
	public String RunPipeline(String Text) throws Exception {

		TextCategorizer guesser = new TextCategorizer();
		String language = guesser.categorize(Text);
		System.out.println("language: " + language);
		String languageCode = null;
		if (language.equals("german"))
			languageCode = "de";
		else if (language.equals("english"))
			languageCode = "en";
		System.out.println("language: " + languageCode);
		CollectionReaderDescription cr = createReaderDescription(StringReader.class, StringReader.PARAM_DOCUMENT_TEXT,
				Text, TextReader.PARAM_LANGUAGE, languageCode);

		AnalysisEngineDescription germanAnnotator = createEngineDescription(GermanSeparatedParticleAnnotator.class);

		AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);
		AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
		AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
		AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
		AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
		AnalysisEngineDescription tagger4 = createEngineDescription(JazzyChecker.class,
				JazzyChecker.PARAM_MODEL_LOCATION, new File(this.getClass().getResource("/words.utf-8.txt").toURI()));

		AnalysisEngineDescription cc = createEngineDescription(FeatureExtractor.class);

		if (languageCode.equals("en")) 
			runPipeline(cr, seg, tagger, tagger1, tagger2, tagger3, tagger4, cc);
		else if(languageCode.equals("de"))
			runPipeline(cr, seg, tagger, tagger1, germanAnnotator, cc);
		return languageCode;

	}
}
