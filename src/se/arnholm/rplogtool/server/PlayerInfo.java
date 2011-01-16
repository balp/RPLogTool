package se.arnholm.rplogtool.server;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.Duration;

public class PlayerInfo implements Comparable<PlayerInfo> {
	

	private List<String> lines;
	private long first;
	private long last;
	private String player;

	public PlayerInfo(String who) {
		lines = new LinkedList<String>();
		last = Long.MIN_VALUE;
		first = Long.MIN_VALUE;
		player = who;
	}

	public void addLine(String line) {
		lines.add(line);
		long time = LogCleaner.getTime(line);
		if(first == Long.MIN_VALUE) {
//			System.out.println("addLine("+ line +"): first = " + time);
			first = time;
		}
		if( time < first) {
			time += 24*60*60*1000;
		}
		if(time > last) {
//			System.out.println("addLine("+ line +"): last = " + time);
			last = time;
		}
		
	}
	public String getPlayerName() {
		return player;
	}

	public long getFirstTime() {
		return first;
	}

	public long getLastTime() {
		return last;
	}

	public Duration getDuration() {
		long firstTime = getFirstTime();
		long lastTime = getLastTime();
		if(lastTime < firstTime) {
			lastTime += 24*60*60*1000;
		}
		return new Duration(firstTime, lastTime);
	}

	public int getNumberOfLines() {
		return lines.size();
	}

	public List<String> getLines() {
		return lines;
	}

	@Override
	public int compareTo(PlayerInfo o) {
		return getDuration().compareTo(o.getDuration());
	}

}
