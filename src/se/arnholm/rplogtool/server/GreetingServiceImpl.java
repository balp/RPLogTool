package se.arnholm.rplogtool.server;

import java.util.Set;

import se.arnholm.rplogtool.client.GreetingService;
import se.arnholm.rplogtool.shared.FieldVerifier;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		
		

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		String log = escapeHtml(input);
		userAgent = escapeHtml(userAgent);
		LogCleaner cleaner = new LogCleaner(log);
		log = cleaner.getClean();
		log = log.replaceAll("\n", "<br>\n");
		
		String result = "Roleplay Log:<br>";
		result += "----------------------------------------------------------------<br>\n";
		Set<String> players = cleaner.getPartisipants();
		for(String player: players) {
			PlayerInfo info = cleaner.getPlayerInfo(player);
//			result += player + ": " + LogCleaner.formatTime(info.getDuration()) +"<br>\n"; 
			result += player + ": " +"<br>\n"; 
		}
		result += "----------------------------------------------------------------<br>\n";
		result += log; 
		result += "----------------------------------------------------------------<br>\n";
		result += "<strong>RPLogTool ©2010 Balp Allen</strong><br>" + serverInfo + ".<br>";
		return result;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
