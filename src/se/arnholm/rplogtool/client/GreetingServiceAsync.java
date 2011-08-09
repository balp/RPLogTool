package se.arnholm.rplogtool.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, String template, Boolean expandMe, Boolean removeCCS, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
	