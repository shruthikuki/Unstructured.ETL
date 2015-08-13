package ipvs_is.servlets;

import ipvs_is.database.DatabaseConnectionHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultGenerationServlet extends HttpServlet {
	String dataSourceId, id;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String featureCode = request.getParameter("featureCode");
		id = request.getParameter("id");
		if (featureCode.equals("NER")) {
			getResultForNER(response);
		} else if (featureCode.equals("POS")) {
			getResultForPOS(response);
		} else if (featureCode.equals("SC")) {
			getResultForSC(response);
		} else if (featureCode.equals("CR")) {
			getResultForCR(response);
		}
	};

	private void getResultForPOS(ServletResponse response) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		/*
		 * StringBuilder originalText = new StringBuilder(
		 * databaseConnectionHandler.getDataSourceContent(dataSourceId));
		 */
		StringBuilder originalText = new StringBuilder(databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id)));
		HashMap<String, String> posColorMap = new HashMap<String, String>();
		posColorMap.put("noun", "#F79898");
		posColorMap.put("verb", "#98F7C4");
		posColorMap.put("adjective", "#4A7FF0");
		// get all rows from POS table
		// for each row
		// using position of each token, in originalText, append appropriate
		// divs

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
		databaseConnectionHandler.updatePOSResultData(originalText.toString(), Integer.parseInt(id));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(originalText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getResultForNER(ServletResponse response) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		/*
		 * StringBuilder originalText = new StringBuilder(
		 * databaseConnectionHandler.getDataSourceContent(dataSourceId));
		 */
		StringBuilder originalText = new StringBuilder(databaseConnectionHandler.getDataSourceContent());
		HashMap<String, String> nerColorMap = new HashMap<String, String>();
		nerColorMap.put("person", "#F79898");
		nerColorMap.put("location", "#98F7C4");
		nerColorMap.put("organization", "#4A7FF0");
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append appropriate
		// divs

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

		databaseConnectionHandler.updateNERResultData(originalText.toString(), Integer.parseInt(id));
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(originalText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getResultForSC(ServletResponse response) {
		String originalText;
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append appropriate
		// divs

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.println("<div><b>Response for SC!!!</b></div>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getResultForCR(ServletResponse response) {
		String originalText;
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append approproaite
		// divs

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.println("<div><b>Response for CR!!!</b></div>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
