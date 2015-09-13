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
 * Servlet implementation class GetOriginalText
 */
@WebServlet("/GetOriginalText")
public class GetOriginalText extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetOriginalText() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		String originalText = databaseConnectionHandler.getDataSourceContent(Integer.parseInt(id));
		
		PrintWriter out = null;
		try {
			/*response.setContentType("application/x-www-form-urlencoded;charset=UTF-8");*/
			out = response.getWriter();
			out.println(originalText);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
