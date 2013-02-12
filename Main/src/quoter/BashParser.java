package quoter;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import web.SingleWebClient;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BashParser {

	private static String BASE_URL_TO_QUOTE = "http://bash.im/quote/";
	private static String XPATH_TO_QUOTE = "//*[@id=\"body\"]/div[3]/div[2]";
	private static Logger log = LoggerFactory.getLogger(BashParser.class);

	public static String parseQuote(int num) {
		String result = null;
		WebClient client = SingleWebClient.getInstance();
		String url = BASE_URL_TO_QUOTE + num;
		try {
			HtmlPage page = client.getPage(url);
			List<?> quoteList = page.getByXPath(XPATH_TO_QUOTE);
			if (quoteIsExist(quoteList)) {
				HtmlElement element = (HtmlElement) quoteList.get(0);
				result = element.asText();
			}
		} catch (FailingHttpStatusCodeException e) {
			log.error("HTTP Fail error: {} error code: {}", e.getMessage(),
					e.getStatusCode());
		} catch (MalformedURLException e) {
			log.error("Invalid URL: {}", url);
		} catch (IOException e) {
			log.error("I/O error: {}", e.getMessage());
		}
		return result;
	}

	private static boolean quoteIsExist(List<?> quoteList) {
		return !quoteList.isEmpty();
	}
}
