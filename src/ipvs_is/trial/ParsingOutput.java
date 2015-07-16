package ipvs_is.trial;

import java.io.BufferedReader;
import java.io.FileReader;

import ipvs_is.database.DatabaseConnectionHandler;
import ipvs_is.database.ParsingOutputDatabase;

public class ParsingOutput {

	public static void main(String[] args) throws Exception {
		// DatabaseConnectionHandler databaseConnectionHandler = new
		// DatabaseConnectionHandler();
		// databaseConnectionHandler.insert();
		// System.out.println ("\nParts Of Speech");
		// POS();
		// System.out.println ("\nNamed Entity");
		// namedEntity();
		// System.out.println ("\nCoreference");
		// coreference();
	}

	public void POS() throws Exception {
		System.out.println("deleting POS");
		DatabaseConnectionHandler databaseConnectionHandler1 = new DatabaseConnectionHandler();
		databaseConnectionHandler1.deleteTableContents("POS_DATA");

		String outputText, buffer, begin, end, posValue;
		int length;

		BufferedReader br = new BufferedReader(new FileReader("resources/output.txt"));
		String previousLine = br.readLine();

		ParsingOutputDatabase databaseConnectionHandler = new ParsingOutputDatabase();

		while ((outputText = br.readLine()) != null) {
			if (outputText.startsWith("N")) {
				buffer = br.readLine();
				begin = br.readLine();
				end = br.readLine();
				posValue = br.readLine();
				if (posValue.contains("NN") || posValue.contains("NNS") || posValue.contains("NNP")
						|| posValue.contains("NNPS")) {
					System.out.println("Noun" + previousLine);
					length = previousLine.length();
					previousLine = previousLine.substring(1, length - 1);
					begin = begin.replace("begin: ", " ");
					end = end.replace("end: ", "");
					databaseConnectionHandler.insertPOS(previousLine, Integer.parseInt(begin.trim()),
							Integer.parseInt(end.trim()), "Noun");
				}
				previousLine = posValue;
			} else {
				if (outputText.equals("V")) {
					buffer = br.readLine();
					begin = br.readLine();
					end = br.readLine();
					posValue = br.readLine();
					if (posValue.contains("VB") || posValue.contains("VBD") || posValue.contains("VBG")
							|| posValue.contains("VBN") || posValue.contains("VBP") || posValue.contains("VBZ")) {
						System.out.println("Verb" + previousLine);
						length = previousLine.length();
						previousLine = previousLine.substring(1, length - 1);
						begin = begin.replace("begin: ", " ");
						end = end.replace("end: ", "");
						databaseConnectionHandler.insertPOS(previousLine, Integer.parseInt(begin.trim()),
								Integer.parseInt(end.trim()), "Verb");
					}
					previousLine = posValue;
				}

				else {
					if (outputText.equals("ADJ")) {
						buffer = br.readLine();
						begin = br.readLine();
						end = br.readLine();
						posValue = br.readLine();
						if (posValue.contains("JJ") || posValue.contains("JJR") || posValue.contains("JJZ")) {
							System.out.println("Adjective" + previousLine);
							length = previousLine.length();
							previousLine = previousLine.substring(1, length - 1);
							begin = begin.replace("begin: ", " ");
							end = end.replace("end: ", "");
							databaseConnectionHandler.insertPOS(previousLine, Integer.parseInt(begin.trim()),
									Integer.parseInt(end.trim()), "Adjective");
						}
						previousLine = posValue;
					}

					else
						previousLine = outputText;
				}
			}
		}
	}

	public void namedEntity() throws Exception {

		String outputText, buffer, begin, end;
		int length;

		BufferedReader br = new BufferedReader(new FileReader("resources/output.txt"));
		String previousLine = br.readLine();

		while ((outputText = br.readLine()) != null) {
			if (outputText.equals("Person")) {
				buffer = br.readLine();
				begin = br.readLine();
				end = br.readLine();
				System.out.println("Person" + previousLine);

				length = previousLine.length();
				previousLine = previousLine.substring(1, length - 1);
				begin = begin.replace("begin: ", " ");
				end = end.replace("end: ", "");
				// databaseConnectionHandler.insertNamedEntity(previousLine,
				// Integer.parseInt(begin.trim()), Integer.parseInt(end.trim()),
				// "Person");

				previousLine = end;
			} else {
				if (outputText.equals("Location")) {
					buffer = br.readLine();
					begin = br.readLine();
					end = br.readLine();
					System.out.println("Location" + previousLine);

					length = previousLine.length();
					previousLine = previousLine.substring(1, length - 1);
					begin = begin.replace("begin: ", " ");
					end = end.replace("end: ", "");
					// databaseConnectionHandler.insertNamedEntity(previousLine,
					// Integer.parseInt(begin.trim()),
					// Integer.parseInt(end.trim()), "Location");

					previousLine = end;
				}

				else if (outputText.equals("Organization")) {
					buffer = br.readLine();
					begin = br.readLine();
					end = br.readLine();
					System.out.println("Organization" + previousLine);

					length = previousLine.length();
					previousLine = previousLine.substring(1, length - 1);
					begin = begin.replace("begin: ", " ");
					end = end.replace("end: ", "");
					// databaseConnectionHandler.insertNamedEntity(previousLine,
					// Integer.parseInt(begin.trim()),
					// Integer.parseInt(end.trim()), "Organization");

					previousLine = end;
				} else
					previousLine = outputText;
			}
		}
	}

	public void coreference() throws Exception {

		String outputText, buffer, begin, end, posValue, nextBegin, nextEnd;
		int length;

		BufferedReader br = new BufferedReader(new FileReader("resources/output.txt"));
		String previousLine = br.readLine();

		while ((outputText = br.readLine()) != null) {
			if (outputText.equals("CoreferenceLink")) {
				buffer = br.readLine();
				begin = br.readLine();
				end = br.readLine();
				posValue = br.readLine();
				if (posValue.contains("next: CoreferenceLink")) {
					System.out.println("Coreference " + previousLine + " " + begin + " " + end);
					buffer = br.readLine();
					nextBegin = br.readLine();
					nextEnd = br.readLine();
					System.out.println(" " + nextBegin + " " + nextEnd);

					length = previousLine.length();
					previousLine = previousLine.substring(1, length - 1);
					begin = begin.replace("begin: ", " ");
					end = end.replace("end: ", "");
					nextBegin = nextBegin.replace("begin: ", " ");
					nextEnd = nextEnd.replace("end: ", "");
					// databaseConnectionHandler.insertCoreference(previousLine,
					// Integer.parseInt(begin.trim()),
					// Integer.parseInt(end.trim()),
					// Integer.parseInt(nextBegin.trim()),
					// Integer.parseInt(nextEnd.trim()));

					previousLine = nextEnd;
				} else
					previousLine = posValue;
			} else {
				if (outputText.equals("CoreferenceLink")) {
					buffer = br.readLine();
					begin = br.readLine();
					end = br.readLine();
					posValue = br.readLine();
					if (posValue.contains("next: CoreferenceLink")) {
						System.out.println("Coreference " + previousLine + " " + begin + " " + end);
						buffer = br.readLine();
						nextBegin = br.readLine();
						nextEnd = br.readLine();
						System.out.println(" " + nextBegin + " " + nextEnd);

						length = previousLine.length();
						previousLine = previousLine.substring(1, length - 1);
						begin = begin.replace("begin: ", " ");
						end = end.replace("end: ", "");
						nextBegin = nextBegin.replace("begin: ", " ");
						nextEnd = nextEnd.replace("end: ", "");
						// databaseConnectionHandler.insertCoreference(previousLine,
						// Integer.parseInt(begin.trim()),
						// Integer.parseInt(end.trim()),
						// Integer.parseInt(nextBegin.trim()),
						// Integer.parseInt(nextEnd.trim()));

						previousLine = nextEnd;
					} else
						previousLine = posValue;
				} else {
					if (outputText.equals("CoreferenceLink")) {
						buffer = br.readLine();
						begin = br.readLine();
						end = br.readLine();
						posValue = br.readLine();
						if (posValue.contains("next: CoreferenceLink")) {
							System.out.println("Coreference " + previousLine + " " + begin + " " + end);
							buffer = br.readLine();
							nextBegin = br.readLine();
							nextEnd = br.readLine();
							System.out.println(" " + nextBegin + " " + nextEnd);

							length = previousLine.length();
							previousLine = previousLine.substring(1, length - 1);
							begin = begin.replace("begin: ", " ");
							end = end.replace("end: ", "");
							nextBegin = nextBegin.replace("begin: ", " ");
							nextEnd = nextEnd.replace("end: ", "");
							// databaseConnectionHandler.insertCoreference(previousLine,
							// Integer.parseInt(begin.trim()),
							// Integer.parseInt(end.trim()),
							// Integer.parseInt(nextBegin.trim()),
							// Integer.parseInt(nextEnd.trim()));

							previousLine = nextEnd;
						} else
							previousLine = posValue;
					} else
						previousLine = outputText;
				}
			}
		}
	}

}