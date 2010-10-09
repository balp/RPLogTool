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
		setName(name);
		this.action = action;
		this.ccsLine = ccsLine;
		this.ccsText = "";
	}

	/**
	 * @param name
	 */
	private void setName(String name) {
		
		if(name.endsWith(" shouts")) {
			this.name = name.substring(0, name.lastIndexOf(" shouts"));
		} else if(name.endsWith(" whispers")) {
				this.name = name.substring(0, name.lastIndexOf(" whispers"));
		} else {
			this.name = name;
		}
		

	}

	public RpLogLine(String time, String name, String action, String ccsText) {
		this.time = time;
		setName(name);
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
