package ipvs_is.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ipvs_is.database.DatabaseConnectionHandler;
import ipvs_is.trial.Pipeline;

/**
 * Servlet implementation class TextInputServlet
 */
@WebServlet("/TextInputServlet")
public class TextInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TextInputServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// StringBuffer sb = new
		// StringBuffer(request.getParameter("input_text"));
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		String Text = request.getParameter("input_text");
		System.out.println(Text);

		// Text="Last year we visited france";

		Pipeline pipeline = new Pipeline();
		try {
			System.out.println(Text);
			pipeline.RunPipeline(Text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int id;
		id = databaseConnectionHandler.insertDataSourceContent(Text, "text", Text.substring(0, 9) + "...");
		databaseConnectionHandler.writeResultData(id);
		response.setContentType("text/html");

		// New location to be redirected
		String site = new String("html/ResultDisplay.html?id=" +id);
		//response.setIntHeader("id", id);
		response.setStatus(response.SC_MOVED_TEMPORARILY);
		response.setHeader("Location", site);
		response.setHeader("sample","sampleValue");
		

		// doGet(request, response);
	}

}
