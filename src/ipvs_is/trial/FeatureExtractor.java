package ipvs_is.trial;

import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.api.coref.type.CoreferenceLink;
import ipvs_is.database.DatabaseConnectionHandler;

/**
 * FeatureExtractor
 * After the pipeline has been processed, details of the feature is present in a Cas file. 
 * This is a custom defined writer that reads into the Cas file, obtains details for each feature and writes required
 * data into the database 
 */

public class FeatureExtractor extends CasConsumer_ImplBase {
	private int iCas;

	@Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
	}
	@Override
	public void process(CAS aCAS) throws AnalysisEngineProcessException {
		Iterator<CAS> viewIt = aCAS.getViewIterator();
		while (viewIt.hasNext()) {
			CAS view = viewIt.next();
			processFeatureStructures(view);
		}
		iCas++;
	}

	private void processFeatureStructures(CAS aCAS) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		databaseConnectionHandler.deleteTableContents("POS_DATA");
		databaseConnectionHandler.deleteTableContents("NAMED_ENTITY_DATA");
		databaseConnectionHandler.deleteTableContents("SC_DATA");
		databaseConnectionHandler.deleteTableContents("CR_DATA");
		FSIterator<AnnotationFS> annotationIterator = aCAS.getAnnotationIndex().iterator();
		
		while (annotationIterator.hasNext()) {
			AnnotationFS annotation = annotationIterator.next();
			String coveredText = annotation.getCoveredText().replaceAll("'", "''");
			String type = annotation.getType().getName();
			if (type.contains("Coreference")) {
				CoreferenceLink coref = (CoreferenceLink) annotation;
				if (databaseConnectionHandler.checkIfCorefExists(coref.getBegin(), coref.getEnd())) {
					int parentId = 0;
					if (coref.getNext() != null)
						parentId = databaseConnectionHandler.insertCR(coveredText,
								annotation.getBegin(), annotation.getEnd());

					String childIds = "";
					CoreferenceLink child = coref.getNext();
					while (child != null) {
						int childId = databaseConnectionHandler.insertCR(child.getCoveredText().replaceAll("'", "''"), child.getBegin(),
								child.getEnd());
						childIds += childId + ",";

						child = child.getNext();
					}
					childIds += parentId;
					databaseConnectionHandler.updateCRParent(parentId, childIds);
				}
			}
			if (type.contains(".pos")) {
				if (type.split("\\.")[type.split("\\.").length - 1].startsWith("N")) {
					databaseConnectionHandler.insertPOS(coveredText, annotation.getBegin(),
							annotation.getEnd(), "Noun");
				} else if (type.split("\\.")[type.split("\\.").length - 1].startsWith("V")) {

					databaseConnectionHandler.insertPOS(coveredText, annotation.getBegin(),
							annotation.getEnd(), "Verb");
				} else if (type.split("\\.")[type.split("\\.").length - 1].equals("ADJ")) {

					databaseConnectionHandler.insertPOS(coveredText, annotation.getBegin(),
							annotation.getEnd(), "Adjective");
				}
			}

			if (type.contains(".ner")) {
				if (type.split("\\.")[type.split("\\.").length - 1].equals("Location")) {

					databaseConnectionHandler.insertNamedEntity(coveredText, annotation.getBegin(),
							annotation.getEnd(), "Location");
				} else if (type.split("\\.")[type.split("\\.").length - 1].equals("Organization")) {

					databaseConnectionHandler.insertNamedEntity(coveredText, annotation.getBegin(),
							annotation.getEnd(), "Organization");
				} else if (type.split("\\.")[type.split("\\.").length - 1].equals("Person")) {

					databaseConnectionHandler.insertNamedEntity(coveredText, annotation.getBegin(),
							annotation.getEnd(), "Person");
				}
			}
			if (type.contains(".Spelling")) {
				databaseConnectionHandler.insertSC(coveredText, annotation.getBegin(),
						annotation.getEnd());
			}
		}
	}
}
