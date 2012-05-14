package org.weeels.simulator;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

public class PassengerConnectionSimulator implements PassengerServer.PassengerServerToClientInterface, PassengerClientToServerInterface {
	private final PassengerClient client;
	private final PassengerServer server;
	private final Timeline timeline;
	private final static long LATENCY = 10; // Network latency in milliseconds
	
	public PassengerConnectionSimulator(PassengerClientSimulator client, PassengerServer server) {
		this.client = client;
		this.server = server;
		this.timeline = client.getTimeline();
	}
	
	@Override
	public void sendRequest(final RideRequest request) {
		timeline.addEvent(new TimedEvent(timeline.now().getTime()+LATENCY) {
			public void run() {
				server.receiveRideRequest(request);
			}
		});
	}

}
