package ipvs_is.servlets;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ResultGenerationServlet extends GenericServlet {

	@Override
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String featureCode = request.getParameter("featureCode");
		if (featureCode.equals("NER")) {
			getResultForNER(response);
		}
	}

	private void getResultForNER(ServletResponse response) {
		String originalText;
		// get all rows from NER table
		// for each row
		// using position of each token, in originalText, append approproaite divs
	}

}
