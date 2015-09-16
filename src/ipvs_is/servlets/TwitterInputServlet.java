package ipvs_is.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ipvs_is.database.DatabaseConnectionHandler;
import ipvs_is.trial.Pipeline;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * TwitterInputServlet
 * Obtains the twitter hashtag provided by the user. Invoke Twitter4J framework based on the hashtag to obtain the tweets,
 * concatenate the tweets into a string and call the pipeline to run the analysis
 */

@WebServlet("/TwitterInputServlet")
public class TwitterInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryString = request.getParameter("query");
		String languageCode = null;
		DatabaseConnectionHandler databaseConnectionHandler = new DatabaseConnectionHandler();
		Pipeline pipeline = new Pipeline();
		int id;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("IOA47SmcjlVK5Fm9nhRPSyz8P")
				.setOAuthConsumerSecret("SLjtMd8rAxMjwsIDAIwN9tuYPFNaMfn4nF7BYnYV1OX54qMier")
				.setOAuthAccessToken("38613900-oxvKmvKxSbXnWjSwLyMCghoLYGzntJLjUjmWqpcbL")
				.setOAuthAccessTokenSecret("qIoPeSg9on3Xf6WJq4aGkXOXq30sOUR5SAsutElhsFnCU");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {
			String site;
			Query query = new Query(queryString);
			query.setCount(5);
			int index = 0;
			QueryResult result;
			StringBuilder tweetText = new StringBuilder();

			result = twitter.search(query);

			List<Status> tweets = result.getTweets();
			for (Status tweet : tweets) {
				if (tweet.getLang().equals("en")) {
					System.out.println("tweet: " + index + " : " + tweet.getText());
					tweetText.append(tweet.getText());
					tweetText.append("\n");
				}
			}
			
			languageCode = pipeline.RunPipelineForTwitter(tweetText.toString(), "en");
			
			if (languageCode.equals("unsupported")) {
				site = new String("html/Error.html");
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);
				response.setHeader("sample", "sampleValue");
			} else {
				id = databaseConnectionHandler.insertDataSourceContent(tweetText.toString().replaceAll("'", "''"),
						"tweet", "#" + queryString, languageCode);
				databaseConnectionHandler.writeResultData(id);
				site = new String("html/ResultDisplay.html?id=" + id + "&languageCode=" + languageCode);
				response.setStatus(response.SC_MOVED_TEMPORARILY);
				response.setHeader("Location", site);
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
