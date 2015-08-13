package ipvs_is.servlets;

import ipvs_is.database.DatabaseConnectionHandler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getResultForArchive")
public class ResultForArchiveServlet extends HttpServlet {
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

		String POSResult = databaseConnectionHandler.getResultData("POSResult", Integer.parseInt(id));

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(POSResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getResultForNER(ServletResponse response) {
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		
		String NERResult = databaseConnectionHandler.getResultData("NERResult", Integer.parseInt(id));

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.println(NERResult);
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
