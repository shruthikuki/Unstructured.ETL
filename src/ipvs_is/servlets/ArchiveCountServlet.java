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
 * ArchiveCountServlet
 * Obtains the count of file, twitter feed and text input stored in the database 
 */

@WebServlet("/ArchiveCountServlet")
public class ArchiveCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ArchiveCountServlet() {
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		int count = databaseConnectionHandler.getArchiveCount(type);
		PrintWriter out = response.getWriter();
		out.println(count);
	}
}
