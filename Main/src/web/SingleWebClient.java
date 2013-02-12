package web;

import com.gargoylesoftware.htmlunit.WebClient;

public class SingleWebClient {

	private static WebClient client;

	public static WebClient getInstance() {
		if (client == null) {
			client = WebClientFactory.createDefaultWebClient();
		}
		return client;
	}

}
