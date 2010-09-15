package se.arnholm.rplogtool.server;

import java.util.LinkedList;
import java.util.List;

public class PlayerInfo {
	

	private List<String> lines;
	private long first;
	private long last;
	private String player;

	public PlayerInfo(String who) {
		lines = new LinkedList<String>();
		last = Long.MIN_VALUE;
		first = Long.MAX_VALUE;
		player = who;
	}

	public void addLine(String line) {
		lines.add(line);
		long time = LogCleaner.getTime(line);
		if(time < first) {
			first = time;
		}
		if(time > last) {
			last = time;
		}
		
	}
	public String getPlayerName() {
		return player;
	}

	public long getFirstTime() {
		return first;
	}

}
