package ipvs_is.trial;

import java.util.Iterator;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.CasConsumer_ImplBase;
import org.apache.uima.resource.ResourceInitializationException;

import ipvs_is.database.DatabaseConnectionHandler;
import ipvs_is.database.ParsingOutputDatabase;

public class CustomWriter extends CasConsumer_ImplBase {

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
		DatabaseConnectionHandler databaseConnectionHandler1 = new DatabaseConnectionHandler();
		databaseConnectionHandler1.deleteTableContents("POS_DATA");
//		databaseConnectionHandler1.deleteTableContents("NAMED_ENTITY_DATA");
		ParsingOutputDatabase databaseConnectionHandler = new ParsingOutputDatabase();
		FSIterator<AnnotationFS> annotationIterator = aCAS.getAnnotationIndex().iterator();
		while (annotationIterator.hasNext()) {
			AnnotationFS annotation = annotationIterator.next();
			
			String type = annotation.getType().getName();
			if (type.contains(".pos")) {
				if (type.split("\\.")[type.split("\\.").length - 1].startsWith("N")) {
					System.out.println("noun for: " + annotation.getCoveredText());
					databaseConnectionHandler.insertPOS(annotation.getCoveredText(), annotation.getBegin(),
							annotation.getEnd(), "Noun");
				} else if (type.split("\\.")[type.split("\\.").length - 1].startsWith("V")) {
					
					databaseConnectionHandler.insertPOS(annotation.getCoveredText(), annotation.getBegin(),
							annotation.getEnd(), "Verb");
				} else if (type.split("\\.")[type.split("\\.").length - 1].equals("ADJ")) {
					
					databaseConnectionHandler.insertPOS(annotation.getCoveredText(), annotation.getBegin(),
							annotation.getEnd(), "Adjective");
				} 
			}
			
			/*if (type.contains(".ner")) {
				if (type.split("\\.")[type.split("\\.").length - 1].equals("Location")) {
				
					databaseConnectionHandler.insertPOS(annotation.getCoveredText(), annotation.getBegin(),
							annotation.getEnd(), "Noun");
				} else if (type.split("\\.")[type.split("\\.").length - 1].equals("Organization")) {
					
					databaseConnectionHandler.insertPOS(annotation.getCoveredText(), annotation.getBegin(),
							annotation.getEnd(), "Verb");
				} else if (type.split("\\.")[type.split("\\.").length - 1].startsWith("ADJ")) {
					
					databaseConnectionHandler.insertPOS(annotation.getCoveredText(), annotation.getBegin(),
							annotation.getEnd(), "Adjective");
				} 
			}*/
		}
	}
}
