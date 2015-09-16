package ipvs_is.trial;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.*;
import static org.apache.uima.fit.factory.CollectionReaderFactory.*;
import static org.apache.uima.fit.pipeline.SimplePipeline.*;

import java.io.File;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.knallgrau.utils.textcat.TextCategorizer;

import de.tudarmstadt.ukp.dkpro.core.io.text.StringReader;
import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;
import de.tudarmstadt.ukp.dkpro.core.tokit.GermanSeparatedParticleAnnotator;

/**
 * Pipeline
 * Handles the main analysis of the input provided by the user. The language is detected automatically. Upon this features
 * engines are run in a pipeline to obtain the Cas file which is read into by FeatureExtractor class.  
 */

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
		else
			languageCode = "unsupported";
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
	
	public String RunPipelineForTwitter(String Text, String languageCode) throws Exception {
		
		CollectionReaderDescription cr = createReaderDescription(StringReader.class, StringReader.PARAM_DOCUMENT_TEXT,
				Text, TextReader.PARAM_LANGUAGE, languageCode);

		AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);
		AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
		AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
		AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
		AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
		AnalysisEngineDescription tagger4 = createEngineDescription(JazzyChecker.class,
				JazzyChecker.PARAM_MODEL_LOCATION, new File(this.getClass().getResource("/words.utf-8.txt").toURI()));

		AnalysisEngineDescription cc = createEngineDescription(FeatureExtractor.class);
 
		runPipeline(cr, seg, tagger, tagger1, tagger2, tagger3, tagger4, cc);
		
		return languageCode;
	}
}
