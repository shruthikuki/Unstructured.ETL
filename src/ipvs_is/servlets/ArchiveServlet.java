package ipvs_is.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ipvs_is.database.DatabaseConnectionHandler;

/**
 * Servlet implementation class FileArchiveServlet
 */
@WebServlet("/ArchiveServlet")
public class ArchiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArchiveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int j = 1;
		String type = request.getParameter("type");
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		ArrayList<String> datalist = new ArrayList<String>();
		datalist = databaseConnectionHandler.getDataSource(type);
		PrintWriter out = response.getWriter();
		out.println("<table>");
		out.println("<th>SNo</th>");
		out.println("<th class='title-header'>Title</th>");
		for (int i = 0; i < datalist.size(); i = i + 2) {
			
			out.println("<tr>");
			out.println("<td>" + j + "</td>");
			out.println("<td onclick='javascript:showResultForArchive(" + datalist.get(i) + ")' class='title'>" + datalist.get(i + 1) + "</td>");
			out.println("</tr>");
			j++;
			
		}
		out.println("</table>");
	}

}
