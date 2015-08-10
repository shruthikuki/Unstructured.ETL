package ipvs_is.servlets;

import java.io.IOException;
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
@WebServlet("/FileArchiveServlet")
public class FileArchiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileArchiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String page = "FileArchive.html";
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		/*
		 * StringBuilder originalText = new StringBuilder(
		 * databaseConnectionHandler.getDataSourceContent(dataSourceId));
		 */
		
		ArrayList<String> datalist = new ArrayList<String>();
		datalist=databaseConnectionHandler.getFileDataSource();
		request.setAttribute("data",datalist);
	    RequestDispatcher dispatcher = request.getRequestDispatcher(page);
	    if (dispatcher != null){
	        dispatcher.forward(request, response);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
