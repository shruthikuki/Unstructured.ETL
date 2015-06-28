package ipvs_is.trial;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.*;
import static org.apache.uima.fit.factory.CollectionReaderFactory.*;
import static org.apache.uima.fit.pipeline.SimplePipeline.*;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.jxpath.ri.model.beans.LangAttributePointer;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.component.CasDumpWriter;

import org.knallgrau.utils.textcat.TextCategorizer;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;

//import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import de.tudarmstadt.ukp.dkpro.core.io.xml.XmlWriterInline;

import de.tudarmstadt.ukp.dkpro.core.jazzy.JazzyChecker;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordCoreferenceResolver;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordNamedEntityRecognizer;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordParser;
import de.tudarmstadt.ukp.dkpro.core.stanfordnlp.StanfordPosTagger;
import de.tudarmstadt.ukp.dkpro.core.tokit.BreakIteratorSegmenter;

public class Trial {

	public static void main(String[] args) throws Exception {

		String fileText;
		StringBuilder sb;

		BufferedReader br = new BufferedReader(new FileReader(
				"resources/sample.txt"));
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
		System.out.println(language + ": "
				+ language.substring(0, 2).toLowerCase());

		CollectionReaderDescription cr = createReaderDescription(
				TextReader.class, TextReader.PARAM_SOURCE_LOCATION,
				"resources/sample.txt", TextReader.PARAM_LANGUAGE, language
						.substring(0, 2).toLowerCase());

		AnalysisEngineDescription seg = createEngineDescription(BreakIteratorSegmenter.class);

		AnalysisEngineDescription tagger = createEngineDescription(StanfordPosTagger.class);
		AnalysisEngineDescription tagger1 = createEngineDescription(StanfordNamedEntityRecognizer.class);
		AnalysisEngineDescription tagger2 = createEngineDescription(StanfordParser.class);
		AnalysisEngineDescription tagger3 = createEngineDescription(StanfordCoreferenceResolver.class);
		/*
		 * AnalysisEngineDescription tagger4 = createEngineDescription(
		 * JazzyChecker.class, JazzyChecker.PARAM_MODEL_LOCATION,
		 * "resources/words.utf-8.txt");
		 */

		AnalysisEngineDescription cc = createEngineDescription(
				CasDumpWriter.class, CasDumpWriter.PARAM_OUTPUT_FILE,
				"target/Trial.txt");

		/*
		 * AnalysisEngineDescription cc = createEngineDescription(
		 * XmlWriterInline.class, XmlWriterInline.PARAM_TARGET_LOCATION,
		 * "target/trial.txt");
		 */

		runPipeline(cr, seg, tagger, tagger1, tagger2, tagger3, cc);

		/*
		 * TextCategorizer guesser = new TextCategorizer();
		 * System.out.println(guesser.categorize(cr));
		 */

	}
}
