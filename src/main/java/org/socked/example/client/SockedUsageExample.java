package org.socked.example.client;

import java.util.Set;

import org.socked.gwt.client.Socked;
import org.socked.gwt.client.Socked.SockedReady;
import org.socked.gwt.client.model.Channel;
import org.socked.gwt.client.model.ChannelRef;
import org.socked.gwt.client.model.Connection;
import org.socked.gwt.client.model.Options;
import org.socked.gwt.client.model.Options.Role;
import org.socked.gwt.client.model.callbacks.IConnectCallback;
import org.socked.gwt.client.model.callbacks.IDisconnectCallback;
import org.socked.gwt.client.model.callbacks.IMessageCallback;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Timer;

public class SockedUsageExample implements EntryPoint {

	public SockedUsageExample() {
	}

	public void onModuleLoad() {
		Socked.ready(new SockedReady() {
			public void onReady() {
				subscribe();
			}
		});
	}

	private void subscribe() {
		String server = "localhost:8080";
		String appKey = "myKey";
		final String connectionId = "s1";
		final String channelName = "mouselive";
		Connection connection = Socked.createConnection(connectionId, server,
				appKey, new String[] { "mouselive" });
		if (connection == null) {
			Debugger.log("SockedExample - CONNECTION FAILED!");
			return;
		}
		Debugger.log("SockedExample - CONNECTION: " + connection);

		final Channel channel = Socked.getChannel(connectionId, channelName);
		if (channel == null) {
			Debugger.log("SockedExample - CHANNEL NOT FOUND: " + channelName);
			return;
		}

		Debugger.log("SockedExample - #conn-" + connectionId + ": subscribing "
				+ channelName);

		Set<String> interests = null;
		// Set<String> interests = new HashSet<String>();
		// interests.add("batata");
		Options options = new Options(Role.BOTH, interests);
		final ChannelRef ref = channel.subscribe(options);

		ref.onConnect(new IConnectCallback() {
			public void onConnect() {
				Debugger.log("SockedExample - #conn-" + connectionId
						+ ": connected to " + channelName);
				ref.send("hello " + channelName + "! #conn-" + connectionId
						+ " here!");
			}
		});

		ref.onMessage(new IMessageCallback() {
			public void onMessage(String message) {
				Debugger.log("<" + channelName + "> " + message);
			}
		});

		ref.onDisconnect(new IDisconnectCallback() {
			public void onDisconnect() {
				Debugger.log("SockedExample - #conn-" + connectionId
						+ ": disconnected from " + channelName);
			}
		});

		Timer unsubscribeTimer = new Timer() {
			public void run() {
				ref.unsubscribe();
			}
		};
		unsubscribeTimer.schedule(5000);
	}
}
