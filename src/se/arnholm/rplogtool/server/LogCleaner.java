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

import org.joda.time.Duration;

public class LogCleaner {
	private String text;
	private String cleanString;
	private Vector<String> lines;
	private Map<String,PlayerInfo> players;
	private static final Pattern LINE_SPLIT = Pattern.compile("^\\s*\\[(.+)\\]\\s+(\\w+\\s+\\w+)([\\s+':].+)$");
	private static final Pattern CCS_LINE_SPLIT = Pattern.compile("^\\s*\\[(.+)\\]\\s+CCS - MTR - 1.0.2:\\s+(\\w+\\s+\\w+)([\\s+':].+)$");
	private static final Pattern TIME_SECONDS = Pattern.compile("(\\d+):(\\d+):(\\d+)");
	private static final Pattern TIME_MINUTES = Pattern.compile("(\\d+):(\\d+)");
	
	
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
		Pattern online = Pattern.compile("\\[[\\d-\\s:]+\\]\\s+\\S+\\s+\\S+ is Online");
		Pattern offline = Pattern.compile("\\[[\\d-\\s:]+\\]\\s+\\S+\\s+\\S+ is Offline");
		
		try {
			while((str = reader.readLine()) != null) {
//				System.out.println("In: " + str);
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
//				System.out.println("Adding:" + who + ":" + str);
				lines.add(str);
				result += str + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getPlayerName(String str) {
		Vector<String> values = splitLine(str);
		if(null != values) {
			return values.get(1);
		}
		return null;
	}

	public String getClean() {
		
		return cleanString;
	}

	public Duration getDuration() {
		long start = getTime(lines.firstElement());
		long end = getTime(lines.lastElement());
	 	
		System.out.println("Duration between: " +start +":"+ lines.firstElement());
		System.out.println("             and: " + end +":"+ lines.lastElement());

		return new Duration(start, end);
	}

	public static long getTime(String logLine) {
		Vector<String> values = splitLine(logLine);
		if(null != values) {
			String time = values.get(0);
			Matcher matchSeconds = TIME_SECONDS.matcher(time);
			if(matchSeconds.find()) {
				long hour = Long.parseLong(matchSeconds.group(1));
				long minutes = Long.parseLong(matchSeconds.group(2));
				long seconds = Long.parseLong(matchSeconds.group(3));
				return (((hour * 60 * 60) + (minutes*60) + seconds) * 1000);
			}
			Matcher matchMinutes = TIME_MINUTES.matcher(time);
			if(matchMinutes.find()) {
				long hour = Long.parseLong(matchMinutes.group(1));
				long minutes = Long.parseLong(matchMinutes.group(2));
				return (((hour * 60 * 60) + (minutes*60) + 0) * 1000);
			}
		}

		return 0;
	}

	public Set<String> getPartisipants() {
		return players.keySet();
	}

	public PlayerInfo getPlayerInfo(String name) {
		return players.get(name);
	}

	public static String formatTime(org.joda.time.Duration duration) {
		return String.format("%d:%02d", duration.toPeriod().getHours(), duration.toPeriod().getMinutes());
	}

	public static Vector<String> splitLine(String line) {
		Matcher ccs = CCS_LINE_SPLIT.matcher(line);
		if(ccs.find()) {
			Vector<String> res = new Vector<String>();
			res.add(ccs.group(1));
			res.add(ccs.group(2));
			res.add(ccs.group(3));
			return res;
			
		}
		Matcher matcher = LINE_SPLIT.matcher(line);
		if(matcher.find()) {
			Vector<String> res = new Vector<String>();
			res.add(matcher.group(1));
			res.add(matcher.group(2));
			res.add(matcher.group(3));
			return res;
		}
		
		return null;
	}

}
