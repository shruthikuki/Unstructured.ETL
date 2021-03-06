package ipvs_is.servlets;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ipvs_is.database.DatabaseConnectionHandler;
import ipvs_is.trial.Pipeline;

/**
 * TextInputServlet
 * Read the file content gievn by the user input, call the pipeline function to run the analysis and store the file text as 
 * original text in the database
 */

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String fileText;
	String fileName;
	StringBuilder sb;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String languageCode = null;
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			// parses the request's content to extract file data
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();
			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					fileName = new File(item.getName()).getName();
					fileText = item.getString();
					Pipeline pipeline = new Pipeline();
					languageCode = pipeline.RunPipeline(fileText);
				}
			}
			
			String site;
			if (languageCode.equals("unsupported")) {
				site = new String("html/Error.html");
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);
				response.setHeader("sample", "sampleValue");
			} 
			else {
				fileText.replaceAll("'", "''");
				DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
				int id;
				id = databaseConnectionHandler.insertDataSourceContent(fileText, "file", fileName, languageCode);
				databaseConnectionHandler.writeResultData(id);
				response.setContentType("text/html");
				// New location to be redirected
				site = new String("html/ResultDisplay.html?id=" + id + "&languageCode=" + languageCode);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);
				response.setHeader("sample", "sampleValue");
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
