package ipvs_is.trial;

import java.io.File;
import java.io.FileReader;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.transform.Source;

public class CursorReader {
	public static void main(String[] args) throws Exception {
		
		XMLInputFactory factory = XMLInputFactory.newInstance();

		factory.setProperty("javax.xml.stream.isNamespaceAware", false);

		XMLStreamReader reader = factory.createXMLStreamReader(new FileReader(
				new File("scientists_dtd.xml")));

		while (reader.hasNext()) {
			int event = reader.getEventType();
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				System.out.println("start element" + " "
						+ reader.getLocalName());

				if (reader.getLocalName().equals("person")) {
					System.out.println("attribute" + " "
							+ reader.getAttributeName(0)
							+ reader.getAttributeValue(0));
					System.out.println("attribute" + " "
							+ reader.getAttributeName(1)
							+ reader.getAttributeValue(1));
				}
				break;

			case XMLStreamConstants.COMMENT:
				System.out.println("comment" + " " + reader.getText());

				break;

			case XMLStreamConstants.CHARACTERS:
				System.out.println("text" + reader.getText());

				break;
			case XMLStreamConstants.END_ELEMENT:
				System.out.println("end element" + " " + reader.getLocalName());

				break;

			default:
				System.out.println("----------------------------");
				break;

			}
			reader.next();
		}
		reader.close();
	}
}