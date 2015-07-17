package ipvs_is.servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ipvs_is.trial.Pipeline;
//import org.apache.tomcat.jni.File;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "E:\\upload";
	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	String fileText;
	StringBuilder sb;

	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}

		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		//factory.setRepository(new File(("E:\\Study")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path to store upload file
		String uploadPath = UPLOAD_DIRECTORY;
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// parses the request's content to extract file data
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();

			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					
					String fileName = new File(item.getName()).getName();
					String filePath = uploadPath + File.separator + fileName;
					//String filePath = "E:\\Study";
					File storeFile = new File(filePath);
					// saves the file on disk
					item.write(storeFile);
					
					Pipeline pipeline = new Pipeline();
					pipeline.RunPipelineForFileUpload(filePath);
					/*BufferedReader br = new BufferedReader(new FileReader(filePath));
				    try {
				        sb = new StringBuilder();
				        String line = br.readLine();

				        while (line != null) {
				            sb.append(line);
				            sb.append(System.lineSeparator());
				            line = br.readLine();
				        }
				        fileText = sb.toString();
				    } finally {
				        br.close();
				    }*/
					
					
				}
			}
			//request.setAttribute("message", "Upload has been done successfully!");
			//request.setAttribute("message", uploadPath.toString());
//			System.out.println( "Upload has been done successfully!");
//			System.out.println(sb.toString());
			response.setContentType("text/html");

			// New location to be redirected
			String site = new String("Bootstrap/css_bootstrap/triya_textbox.html");

			response.setStatus(response.SC_MOVED_TEMPORARILY);
			response.setHeader("Location", site);
			
		} catch (Exception ex) {
			//request.setAttribute("message", "There was an error: " + ex.getMessage());
			System.out.println("There was an error: " + ex.getMessage());
		}
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
	    doPost(req, res);
	}
}
