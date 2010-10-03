/**
 * 
 */
package se.arnholm.rplogtool.server;

/**
 * @author balp
 *
 */
public class RpLogLine {

	private String name;
	private String time;
	private boolean ccsLine;
	private String action;
	private String ccsText;

	public RpLogLine(String time, String name, String action, boolean ccsLine) {
		this.time = time;
		this.name = name;
		this.action = action;
		this.ccsLine = ccsLine;
		this.ccsText = "";
	}

	public RpLogLine(String time, String name, String action, String ccsText) {
		this.time = time;
		this.name = name;
		this.action = action;
		this.ccsLine = true;
		this.ccsText = ccsText;
	}

	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}
	
	public boolean isCCS() {
		return ccsLine;
	}

	public String getAction() {
		return action;
	}

	public String getCCSText() {
		return ccsText;
	}


}
