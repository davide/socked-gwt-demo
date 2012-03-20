package org.socked.example.client;

public class Debugger {

	public native static void log(String message) /*-{
		if (typeof console != 'undefined') {
			console.log(message);
		}
	}-*/;
}
