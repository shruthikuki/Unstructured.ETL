package ipvs_is.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@WebServlet("/TwitterInputServlet")
public class TwitterInputServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String queryString = request.getParameter("query");

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("IOA47SmcjlVK5Fm9nhRPSyz8P")
				.setOAuthConsumerSecret("SLjtMd8rAxMjwsIDAIwN9tuYPFNaMfn4nF7BYnYV1OX54qMier")
				.setOAuthAccessToken("38613900-oxvKmvKxSbXnWjSwLyMCghoLYGzntJLjUjmWqpcbL")
				.setOAuthAccessTokenSecret("qIoPeSg9on3Xf6WJq4aGkXOXq30sOUR5SAsutElhsFnCU");
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {
			Query query = new Query(queryString);
			int index = 0;
			QueryResult result;
			StringBuilder tweetText = new StringBuilder();
			do {

				result = twitter.search(query);

				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					index++;
					if (index <= 10)
						if (tweet.getLang().equals("en")) {
							System.out.println("tweet: " + index + " : " + tweet.getText());
							tweetText.append(tweet.getText());
							tweetText.append("\n");
						}
				}
			} while (((query = result.nextQuery()) != null));
			System.out.println("tweetText: " + tweetText);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}

	}

}
