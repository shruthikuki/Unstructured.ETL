package ipvs_is.trial;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class AIMTrial {

	public static void main(String[] args) throws Exception {
		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("people.xsd"));

		DocumentBuilderFactory parserFactory = DocumentBuilderFactory
				.newInstance();
		parserFactory.setNamespaceAware(true);
		parserFactory.setValidating(false);
		parserFactory.setSchema(schema);
		parserFactory.setIgnoringElementContentWhitespace(true);

		DocumentBuilder parser = parserFactory.newDocumentBuilder();

		Document doc = parser.parse(new File("scientists.xml"));

		// add a person element
		Element newPersonElement = doc.createElement("person");
		doc.getElementsByTagName("people").item(0)
				.appendChild(newPersonElement);

		// add this person's firstname lastname and birthyear

		newPersonElement.setAttribute("born", "1992");

		Text firstnametext = doc.createTextNode("Linsong");
		Text lastnametext = doc.createTextNode("Gu");

		Element newPersonFirstnameElement = doc.createElement("firstname");
		Element newPersonLastnameElement = doc.createElement("lastname");

		newPersonFirstnameElement.appendChild(firstnametext);
		newPersonLastnameElement.appendChild(lastnametext);

		newPersonElement.appendChild(newPersonFirstnameElement);
		newPersonElement.appendChild(newPersonLastnameElement);

		// get the "person" nodelist
		NodeList list = doc.getElementsByTagName("person");
		// traverse the list and show the info of the person
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			String borndate = element.getAttribute("born");
			String deaddate = element.getAttribute("deceased");
			String firstname = element.getElementsByTagName("firstname")
					.item(0).getFirstChild().getNodeValue();
			String lastname = element.getElementsByTagName("lastname").item(0)
					.getFirstChild().getNodeValue();

			System.out.println("year of birth" + " " + borndate);
			System.out.println("year of death" + " " + deaddate);
			System.out.println("firstname" + " " + firstname);
			System.out.println("lastname" + " " + lastname);
			System.out.println("-----------------------------------");
		}
	}
}
