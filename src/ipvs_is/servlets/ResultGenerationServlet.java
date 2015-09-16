package ipvs_is.servlets;

import ipvs_is.database.DatabaseConnectionHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ResultGenerationServlet
 * Obtains the analysis result stored in the database (got from pipeline processing) and constructs the result appropriately
 * for visualization for each feature
 */

public class ResultGenerationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String dataSourceId, id;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String featureCode = request.getParameter("featureCode");
		id = request.getParameter("id");

		if (featureCode.equals("NER")) {
			getResultForNER(response);
		} 
		else if (featureCode.equals("POS")) {
			getResultForPOS(response);
		} 
		else if (featureCode.equals("SC")) {
			getResultForSC(response);
		} 
		else if (featureCode.equals("CR")) {
			getResultForCR(response);
		}
	}

	private void getResultForPOS(ServletResponse response) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		StringBuilder originalText = new StringBuilder(
				databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id)));
		String databaseText;
		HashMap<String, String> posColorMap = new HashMap<String, String>();
		posColorMap.put("noun", "#F79898");
		posColorMap.put("verb", "#98F7C4");
		posColorMap.put("adjective", "#4A7FF0");
		ArrayList<String> posDataList = databaseConnectionHandler.getAllPOSData();
		for (int i = 0; i < posDataList.size(); i++) {
			int begin = Integer.parseInt(posDataList.get(i));
			int end = Integer.parseInt(posDataList.get(i + 1));
			String posType = posDataList.get(i + 2);
			i = i + 2;
			originalText.insert(end, "</font>");
			originalText.insert(begin,
					" <font style = \"background-color:" + posColorMap.get(posType.toLowerCase()) + "\">");
		}
		databaseText = originalText.toString().replaceAll("'", "''");
		databaseConnectionHandler.updatePOSResultData(databaseText, Integer.parseInt(id));
		PrintWriter out = null;

		String[] lines = originalText.toString().split("\r\n|\r|\n");

		String responseString = "";

		for (String line : lines) {
			System.out.println("line: " + line);
			responseString += line + "<br/>";
		}

		try {
			response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
			out = response.getWriter();
			out.println(responseString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getResultForNER(ServletResponse response) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		StringBuilder originalText = new StringBuilder(
				databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id)));
		String databaseText;
		HashMap<String, String> nerColorMap = new HashMap<String, String>();
		nerColorMap.put("person", "#F79898");
		nerColorMap.put("location", "#98F7C4");
		nerColorMap.put("organization", "#4A7FF0");

		ArrayList<String> nerDataList = databaseConnectionHandler.getAllNERData();

		for (int i = 0; i < nerDataList.size(); i++) {
			int begin = Integer.parseInt(nerDataList.get(i));
			int end = Integer.parseInt(nerDataList.get(i + 1));
			String nerType = nerDataList.get(i + 2);
			i = i + 2;
			originalText.insert(end, "</font>");
			originalText.insert(begin,
					" <font style = \"background-color:" + nerColorMap.get(nerType.toLowerCase()) + "\">");

		}
		databaseText = originalText.toString().replaceAll("'", "''");
		databaseConnectionHandler.updateNERResultData(databaseText, Integer.parseInt(id));
		PrintWriter out = null;

		String[] lines = originalText.toString().split("\r\n|\r|\n");

		String responseString = "";

		for (String line : lines) {
			System.out.println("line: " + line);
			responseString += line + "<br/>";
		}

		try {
			response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
			out = response.getWriter();
			out.println(responseString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getResultForSC(ServletResponse response) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		StringBuilder originalText = new StringBuilder(
				databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id)));
		String databaseText;

		ArrayList<String> scDataList = databaseConnectionHandler.getAllSCData();

		for (int i = 0; i < scDataList.size(); i++) {
			int begin = Integer.parseInt(scDataList.get(i));
			int end = Integer.parseInt(scDataList.get(i + 1));
			i = i + 1;
			originalText.insert(end, "</font>");
			originalText.insert(begin, " <font style = \"background-color:" + "red" + "\">");
		}
		databaseText = originalText.toString().replaceAll("'", "''");
		databaseConnectionHandler.updateSCResultData(databaseText, Integer.parseInt(id));

		PrintWriter out = null;

		String[] lines = originalText.toString().split("\r\n|\r|\n");

		String responseString = "";

		for (String line : lines) {
			System.out.println("line: " + line);
			responseString += line + "<br/>";
		}

		try {
			response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
			out = response.getWriter();
			out.println(responseString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getResultForCR(ServletResponse response) {

		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		ArrayList<String> colorList = new ArrayList<String>();

		colorList.add("#FF6040");
		colorList.add("#80C000");
		colorList.add("#E09870");
		colorList.add("#FF8000");
		colorList.add("#C0FF00");
		colorList.add("#80C0FF");
		colorList.add("#FF6040");
		colorList.add("#80FF80");
		colorList.add("#FFA000");
		colorList.add("#FF80FF");
		colorList.add("#40FF40");
		colorList.add("grey");
		StringBuilder originalText = new StringBuilder(
				databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id)));
		String databaseText;
		ArrayList<String> idsList = databaseConnectionHandler.getCoRefIds();

		HashMap<Integer, String> idColorMap = new HashMap<Integer, String>();
		int j = 0;
		for (String idList : idsList) {
			for (int i = 0; i < idList.split(",").length; i++) {
				String id = idList.split(",")[i];
				idColorMap.put(Integer.parseInt(id), colorList.get(j));
			}
			j++;
		}

		ArrayList<String> crDataList = databaseConnectionHandler.getCoRefInfo();
		PrintWriter out = null;
		String overlapText = "";
		String entire = "";
		Map<Integer, String> overlap = new TreeMap<Integer, String>(Collections.reverseOrder());
		ArrayList<String> overlapDataList = new ArrayList<String>();
		String color, overlapColor;

		for (int i = 0; i < crDataList.size(); i++) {
			int begin = Integer.parseInt(crDataList.get(i));
			int end = Integer.parseInt(crDataList.get(i + 1));

			if (i < crDataList.size() - 3 && begin == Integer.parseInt(crDataList.get(i + 3))) {
				j = i;
				for (j = i; j + 3 < crDataList.size()
						&& Integer.parseInt(crDataList.get(j)) == Integer.parseInt(crDataList.get(j + 3)); j = j + 3) {
					entire = crDataList.get(j) + "," + crDataList.get(j + 1) + "," + crDataList.get(j + 2);
					overlap.put(Integer.parseInt(crDataList.get(j + 1)), entire);
					entire = "";
				}
				entire = crDataList.get(j) + "," + crDataList.get(j + 1) + "," + crDataList.get(j + 2);
				overlap.put(Integer.parseInt(crDataList.get(j + 1)), entire);
				entire = "";

				for (Integer key : overlap.keySet()) {
					entire = overlap.get(key);
					for (String entireItem : entire.split(","))
						overlapDataList.add(entireItem);
					originalText.insert(Integer.parseInt(overlapDataList.get(1)), "</font>");
					overlapColor = idColorMap.get(Integer.parseInt(overlapDataList.get(2)));
					overlapText =  overlapText + " <font style = \"background-color:" + overlapColor + "\">";
					overlapDataList.clear();
				}
				originalText.insert(begin, overlapText);
				i = j + 2;
				overlap.clear();
				overlapText = "";
			}

			else {
				color = idColorMap.get(Integer.parseInt(crDataList.get(i + 2)));
				i = i + 2;
				originalText.insert(end, "</font>");
				originalText.insert(begin, " <font style = \"background-color:" + color + "\">");
			}
		}

		databaseText = originalText.toString().replaceAll("'", "''");
		databaseConnectionHandler.updateCRResultData(databaseText, Integer.parseInt(id));

		String[] lines = originalText.toString().split("\r\n|\r|\n");

		String responseString = "";

		for (String line : lines) {
			System.out.println("line: " + line);
			responseString += line + "<br/>";
		}

		try {
			response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
			out = response.getWriter();
			out.println(responseString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
