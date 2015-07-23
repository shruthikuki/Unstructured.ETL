package ipvs_is.trial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class IteratorReader {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Initializes the root component of the Java StAX API
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		try {
			// initializes the iterator which goes through the document one
			// event at a time. The input file is specified using the Input
			// Stream
			XMLEventReader xmlEventReader = xmlInputFactory
					.createXMLEventReader(new FileInputStream(new File(
							"scientists_dtd.xml")));
			while (xmlEventReader.hasNext()) {
				// Get the current event
				XMLEvent xmlEvent = xmlEventReader.nextEvent();
				// Checks the type of the event and compares with one among the
				// many possibilities
				switch (xmlEvent.getEventType()) {
				case XMLStreamConstants.START_ELEMENT:
					StartElement startElement = xmlEvent.asStartElement();
					System.out.println("Start element event.\nName: "
							+ startElement.getName().getLocalPart());
					Iterator attributesIterator = startElement.getAttributes();
					// If this element has any attributes, then iterate over
					// them and display details
					if (attributesIterator.hasNext()) {
						System.out.println("Attributes: ");
						while (attributesIterator.hasNext()) {
							Attribute attribute = (Attribute) attributesIterator
									.next();
							System.out.println("Attribute name: "
									+ attribute.getName().getLocalPart());
							System.out.println("Attribute data: "
									+ attribute.getValue());
						}
					} else {
						System.out.println("No attributes.");
					}
					System.out
							.println("-------------------------------------------");
					break;

				case XMLStreamConstants.COMMENT:
					System.out.println("Comment event. ");
					System.out
							.println("-------------------------------------------");
					break;
				case XMLStreamConstants.CHARACTERS:
					if (!xmlEvent.asCharacters().isWhiteSpace())
						System.out.println("Characters event. Data: "
								+ xmlEvent.asCharacters().getData());
					System.out
							.println("-------------------------------------------");
					break;
				case XMLStreamConstants.END_ELEMENT:
					EndElement endElement = xmlEvent.asEndElement();
					System.out.println("End element event. Name: "
							+ endElement.getName().getLocalPart());
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
