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
	String dataSourceId;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String featureCode = request.getParameter("featureCode");
		dataSourceId = request.getParameter("dataSourceId");
		response.getWriter().println(featureCode);
		System.out.println("featureCode:  " + featureCode);
		System.out.println("dataSourceId: " + dataSourceId);
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
		/*StringBuilder originalText = new StringBuilder(
				databaseConnectionHandler.getDataSourceContent(dataSourceId));*/
		StringBuilder originalText = new StringBuilder(
				databaseConnectionHandler.getDataSourceContent());
		HashMap<String, String> posColorMap = new HashMap<String, String>();
		posColorMap.put("noun", "red");
		posColorMap.put("verb", "green");
		posColorMap.put("adjective", "blue");
		// get all rows from POS table
		// for each row
		// using position of each token, in originalText, append appropriate
		// divs

		ArrayList<String> posDataList = databaseConnectionHandler
				.getAllPOSData();

		for (int i = 0; i < posDataList.size(); i++) {
			int begin = Integer.parseInt(posDataList.get(i));
			int end = Integer.parseInt(posDataList.get(i + 1));
			String posType = posDataList.get(i + 2);
			i = i + 2;
			originalText.insert(end, "</font>");
			originalText.insert(begin, " <font style = 'background-color:"
					+ posColorMap.get(posType.toLowerCase()) + "'>");

		}
		System.out.println("highlighted text: " + originalText);

		/*
		 * ArrayList<Integer> beginIndexList = new
		 * ArrayList<Integer>(indexMap.keySet()); ArrayList<Integer>
		 * descendingBeginIndexList = Collections.sort(beginIndexList,
		 * Collections.reverseOrder());
		 */
		/*
		 * TreeMap<Integer, Integer> descendingIndexMap = new TreeMap<Integer,
		 * Integer>( Collections.reverseOrder());
		 * descendingIndexMap.putAll(indexMap); for (int begin :
		 * descendingIndexMap.keySet()) { int end =
		 * descendingIndexMap.get(begin);
		 * 
		 * }
		 */
		/*databaseConnectionHandler.writeResultData(originalText.toString(),
				dataSourceId);*/
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
		String originalText;
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append approproaite
		// divs

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.println("<div><b>Response for POS!!!</b></div>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getResultForSC(ServletResponse response) {
		String originalText;
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append approproaite
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
