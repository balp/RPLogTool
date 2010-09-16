package se.arnholm.rplogtool.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.repackaged.org.joda.time.Duration;

public class LogCleaner {
	private String text;
	private String cleanString;
	private Vector<String> lines;
	private Map<String,PlayerInfo> players;

	public LogCleaner(String text)
	{
		lines = new Vector<String>();
		this.text = text;
		players = new HashMap<String,PlayerInfo>();
		cleanString = process();
		
		
	}

	private String process() {
		String result = new String();
		BufferedReader reader = new BufferedReader(
				  new StringReader(text));
		String str;
		Pattern online = Pattern.compile("\\[[\\d:]+\\]\\s+\\S+\\s+\\S+ is Online");
		Pattern offline = Pattern.compile("\\[[\\d:]+\\]\\s+\\S+\\s+\\S+ is Offline");
		
		try {
			while((str = reader.readLine()) != null) {
				System.out.println("In: " + str);
				if(online.matcher(str).find()) {
					continue;
				}
				if(offline.matcher(str).find()) {
					continue;
				}
				
				String who = getPlayerName(str);
				if(who != null) {
					PlayerInfo freq = players.get(who);
					if(null == freq) {
						freq = new PlayerInfo(who);
					}
					freq.addLine(str);
					players.put(who, freq);
				}
				System.out.println("Adding:" + who + ":" + str);
				lines.add(str);
				result += str + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getPlayerName(String str) {
		Pattern name = Pattern.compile("\\[[\\d:]+\\]\\s+(\\w+\\s+\\w+)[\\s+':]");
		Matcher matcher = name.matcher(str);
		if(matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public String getClean() {
		
		return cleanString;
	}

	public Duration getDuration() {
		long start = getTime(lines.firstElement());
		long end = getTime(lines.lastElement());
		return new Duration(start, end);
	}

	public static long getTime(String logLine) {
		Pattern timeMinutes = Pattern.compile("\\[(\\d+):(\\d+)\\]");
		Matcher matchMinutes = timeMinutes.matcher(logLine);
//		System.out.println(logLine + ":match?");
		if(matchMinutes.find()) {
			long hour = Long.parseLong(matchMinutes.group(1));
			long minutes = Long.parseLong(matchMinutes.group(2));
//			System.out.println(logLine + ":" + hour + ":" +minutes);
			return (((hour * 60 * 60) + (minutes*60) + 0) * 1000);
		}
		Pattern timeSeconds = Pattern.compile("\\[(\\d+):(\\d+):(\\d+)\\]");
		Matcher matchSeconds = timeSeconds.matcher(logLine);
		if(matchSeconds.find()) {
//			System.out.println(logLine + ":" );
			long hour = Long.parseLong(matchSeconds.group(1));
			long minutes = Long.parseLong(matchSeconds.group(2));
			long seconds = Long.parseLong(matchSeconds.group(3));
//			System.out.println(logLine + ":" + hour + ":" +minutes + ":" + seconds);
			return (((hour * 60 * 60) + (minutes*60) + seconds) * 1000);
		}
		return 0;
	}

	public Set<String> getPartisipants() {
		return players.keySet();
	}

	public PlayerInfo getPlayerInfo(String name) {
		return players.get(name);
	}

	public static String formatTime(Duration duration) {
		return String.format("%d:%02d", duration.toPeriod().getHours(), duration.toPeriod().getMinutes());
	}

}
