package ipvs_is.trial;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;
import de.tudarmstadt.ukp.dkpro.core.io.xml.XmlWriterInline;
import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class Trial {

	public static void main(String[] args) throws Exception {
		CollectionReaderDescription cr = createReaderDescription(
				TextReader.class, TextReader.PARAM_SOURCE_LOCATION,
				"resources/*.txt", TextReader.PARAM_LANGUAGE, "en");

		AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);

		AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
		AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
		AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
		AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
		AnalysisEngineDescription tagger4 = createEngineDescription(
				JazzyChecker.class, JazzyChecker.PARAM_MODEL_LOCATION,
				"resources/words.utf-8.txt");

		/*AnalysisEngineDescription cc = createEngineDescription(
				CasDumpWriter.class, CasDumpWriter.PARAM_OUTPUT_FILE,
				"target/Trial.txt");*/
		
		AnalysisEngineDescription cc = createEngineDescription(
				XmlWriterInline.class, XmlWriterInline.PARAM_TARGET_LOCATION, "target/trial.txt");

		runPipeline(cr, seg, tagger, tagger1, tagger2, tagger3, cc);
		// runPipeline(cr, lang, cc);

		/*
		 * TextCategorizer guesser = new TextCategorizer();
		 * System.out.println(guesser.categorize(cr));
		 */

	}

}
