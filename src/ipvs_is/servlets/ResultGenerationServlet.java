package ipvs_is.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResultGenerationServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String featureCode = request.getParameter("featureCode");
		response.getWriter().println(featureCode);
		System.out.println("featureCode:  " + featureCode);
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

	private void getResultForNER(ServletResponse response) {
		String originalText;
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append appropriate
		// divs

		PrintWriter out = null;

		try {
			out = response.getWriter();
			out.println("<div><b>Response for NER!!!</b></div>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getResultForPOS(ServletResponse response) {
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
