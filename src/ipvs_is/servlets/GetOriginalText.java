package ipvs_is.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ipvs_is.database.DatabaseConnectionHandler;

/**
 * GetOriginalText
 * Obtains the original text stored in the database. This is required in the Result Analysis page.
 */

@WebServlet("/GetOriginalText")
public class GetOriginalText extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetOriginalText() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		String originalText = databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id));

		PrintWriter out = null;

		String[] lines = originalText.split("\r\n|\r|\n");
		String responseString = "";

		for (String line : lines) {
			System.out.println("line: " + line);
			responseString += line + "<br/>";
		}

		try {
			response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");
			out = response.getWriter();
			out.println(responseString);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
