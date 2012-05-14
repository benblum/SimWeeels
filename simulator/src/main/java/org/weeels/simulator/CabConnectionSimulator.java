package org.weeels.simulator;
import org.opentripplanner.routing.graph.*;
import org.opentripplanner.routing.spt.*;
import org.weeels.simulator.CabServer.CabServerToClientInterface;

import com.vividsolutions.jts.geom.Coordinate;
import java.util.Date;

public class CabConnectionSimulator implements CabServerToClientInterface, CabClientToServerInterface {
	private final Timeline timeline;
	private final CabClientSimulator client;
	private final CabServer server;
	private final long LATENCY = 10; // latency in milliseconds
	
	public CabConnectionSimulator(CabServer server, CabClientSimulator client, Timeline timeline) {
		this.timeline = timeline;
		this.server = server;
		this.client = client;
	}
	
	@Override
	public void sendPosition(final Coordinate position) {
		timeline.addEvent(new TimedEvent(timeline.now().getTime()+LATENCY) {
			public void run() {
				server.receiveClientPosition(position);
			}
		});
	}
	
	@Override
	public void sendRoute(final RideRequest request) {
		timeline.addEvent(new TimedEvent(timeline.now().getTime()+LATENCY) {
			public void run() {
				client.receiveRoute(request);
			}
		});
	}
}
