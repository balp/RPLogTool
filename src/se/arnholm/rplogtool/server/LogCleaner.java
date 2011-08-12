package se.arnholm.rplogtool.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.Duration;

public class LogCleaner {
	private String text;
	private String cleanString;
	private Vector<String> lines;
	private Map<String,PlayerInfo> players;
	private Boolean expandMe;
	private Boolean removeCCS;
	private static final Pattern LINE_SPLIT = Pattern.compile("^\\s*\\[([^\\]]+)\\]\\s+(\\w+\\s+\\w+)([\\s+':].+)$");
	private static final Pattern LONGNAME_LINE_SPLIT = Pattern.compile("^\\s*\\[([^\\]]+)\\]\\s+([^:]+):\\s*(.+)$");
	private static final Pattern CCS_LINE_SPLIT = Pattern.compile("^\\s*\\[([^\\]]+)\\]\\s+CCS - MTR - 1.\\d+.\\d+:\\s+((\\w+\\s+\\w+)([\\s+':].+))$");
	private static final Pattern TIME_SECONDS = Pattern.compile("(\\d+):(\\d+):(\\d+)");
	private static final Pattern TIME_MINUTES = Pattern.compile("(\\d+):(\\d+)");
	
	
	public LogCleaner(String text, Boolean expandMe, Boolean removeCCS)
	{
		lines = new Vector<String>();
		this.text = text;
		this.expandMe = expandMe;
		this.removeCCS = removeCCS;
		players = new HashMap<String,PlayerInfo>();
		cleanString = process();
		
		
	}

	private String process() {
		String result = new String();
		BufferedReader reader = new BufferedReader(
				  new StringReader(text));
		String str;
		
		try {
			while((str = reader.readLine()) != null) {

				RpLogLine line = splitLine(str);
				if(null == line) {
					continue;
				}
				final String action = line.getAction();
//				if(line.isCCS()) {
//					System.out.println("In:\"" + str+ "\"");
//					System.out.println("action:\"" + action+ "\"");
//				}
				if(action.equals(" is Online")) {
					continue;
				}
				if(action.equals(" is Offline")) {
					continue;
				}
				if(line.isCCS()) {
					if(line.getCCSText().startsWith("CCS SYSTEM MESSAGE:")) {
						continue;
					}
					if(line.getCCSText().endsWith("to continue playing")) {
						continue;
					}
				}
				if(removeCCS && line.isCCS()) {
					continue;
				}
				if(line.getAction().startsWith("((") && line.getAction().endsWith("))")) {
					continue;
				}
				if(line.getAction().startsWith(" declined your inventory offer.")) {
					continue;
				}
				if(line.getAction().startsWith(" accepted your inventory offer.")) {
					continue;
				}
//				System.out.println("...");
				String who = line.getName();
				if(who.startsWith("(empty)")) {
					continue;
				}
				if(who.startsWith("You have")) {
					continue;
				}
				if(who.startsWith("Second Life")) {
					continue;
				}
				if(who.equals("Draw distance set to")) {
					continue;
				}
				if(who != null) {
					PlayerInfo freq = players.get(who);
					if(null == freq) {
						freq = new PlayerInfo(who);
					}
					freq.addLine(str);
					players.put(who, freq);
				}
				if(expandMe && line.getAction().startsWith("/me ")) {
					// Remove ": /me" from str
					str = str.replaceFirst(": /me", "");
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
		RpLogLine values = splitLine(str);
		if(null != values) {
			return values.getName();
		}
		return null;
	}

	public String getClean() {
		
		return cleanString;
	}

	public Duration getDuration() {
		try {
			long start = getTime(lines.firstElement());
			long end = getTime(lines.lastElement());
			if(end < start) {
				end += 24*60*60*1000;
			}
			//System.out.println("Duration between: " +start +":"+ lines.firstElement());
			//System.out.println("             and: " + end +":"+ lines.lastElement());

			return new Duration(start, end);
		} catch (NoSuchElementException e) {
			return Duration.ZERO;
		}
	}

	public static long getTime(String logLine) {
		RpLogLine values = splitLine(logLine);
		if(null != values) {
			String time = values.getTime();
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
	public List<PlayerInfo> entries() {
		List<Entry<String,PlayerInfo>> list = new LinkedList<Entry<String,PlayerInfo>>(players.entrySet());
		List<PlayerInfo> resultList = new LinkedList<PlayerInfo>();
		Collections.sort(list, new Comparator<Entry<String,PlayerInfo>>() {
			public int compare(Entry<String,PlayerInfo> o1, Entry<String,PlayerInfo> o2) {
				Comparable<PlayerInfo> comparable = o1.getValue();
				return comparable.compareTo(((Map.Entry<String,PlayerInfo>) (o2)).getValue());
			}
		});

		return resultList;

	}
	
	public PlayerInfo getPlayerInfo(String name) {
		return players.get(name);
	}

	public static String formatTime(org.joda.time.Duration duration) {
		return  String.format("%d:%02d", duration.toPeriod().getHours(), duration.toPeriod().getMinutes());
	}
	
	public static String formatTime(long time) {
		return String.format("%d:%02d", (int)time/60, (int)time%60);
	}
	
	public static RpLogLine splitLine(String line) {
		{
			Matcher matcher = CCS_LINE_SPLIT.matcher(line);
			if (matcher.find()) {
				RpLogLine res = new RpLogLine(matcher.group(1), matcher
						.group(3), matcher.group(4), matcher.group(2));
				return res;

			}
		}
		{
			Matcher matcher = LONGNAME_LINE_SPLIT.matcher(line);

			if (matcher.find()) {
//				System.out.println(":" + matcher.group(1) + ":" +  matcher.group(2) + ":" +  matcher.group(3) + ":");
				RpLogLine res = new RpLogLine(matcher.group(1), matcher
						.group(2), matcher.group(3), false);
				return res;
			}
		}
		
		{
			Matcher matcher = LINE_SPLIT.matcher(line);
			if (matcher.find()) {
				RpLogLine res = new RpLogLine(matcher.group(1), matcher
						.group(2), matcher.group(3), false);
				return res;
			}
		}

//		System.out.println("Not Matches line:" + line);
		
		
		return null;
	}

	public String getStartTime() {
		RpLogLine values;
		try {
			values = splitLine(lines.firstElement());

			if(null != values) {
				return values.getTime();
			}
		} catch (NoSuchElementException e) {
			// Will return empty string when no first line
		}
		return "";
	}

	public String getEndTime() {
		try {
			RpLogLine values = splitLine(lines.lastElement());
			if(null != values) {
				return values.getTime();
			}
		} catch (NoSuchElementException e) {
			// Will return empty string when no last line
		}
		return "";
	}



}
