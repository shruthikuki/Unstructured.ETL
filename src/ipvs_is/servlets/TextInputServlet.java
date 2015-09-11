package ipvs_is.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ipvs_is.database.DatabaseConnectionHandler;
import ipvs_is.trial.Pipeline;

@WebServlet("/TextInputServlet")
public class TextInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TextInputServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String languageCode = null;
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		String Text = request.getParameter("input_text");

		int id;
		Pipeline pipeline = new Pipeline();
		try {
			String site;
			languageCode = pipeline.RunPipeline(Text);
			if (languageCode.equals("unsupported")) {
				site = new String("html/Error.html");
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);
				response.setHeader("sample", "sampleValue");
			} else {
				if (Text.length() >= 24)
					id = databaseConnectionHandler.insertDataSourceContent(Text.replaceAll("'", "''"), "text",
							Text.substring(0, 24).replaceAll("'", "''") + "...", languageCode);
				else
					id = databaseConnectionHandler.insertDataSourceContent(Text.replaceAll("'", "''"), "text",
							Text.replaceAll("'", "''"), languageCode);
				databaseConnectionHandler.writeResultData(id);
				response.setContentType("text/html");

				// New location to be redirected
				site = new String("html/ResultDisplay.html?id=" + id + "&languageCode=" + languageCode);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);
				response.setHeader("sample", "sampleValue");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
