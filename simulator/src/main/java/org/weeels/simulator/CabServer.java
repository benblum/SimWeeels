package org.weeels.simulator;
import java.util.Date;

import org.opentripplanner.routing.graph.*;
import com.vividsolutions.jts.geom.Coordinate;

public class CabServer {
	public interface CabServerToClientInterface {
		public void sendRoute(final RideRequest request);
	}
	
	private CabServerToClientInterface clientConnection;
	
	public CabServer() {
	}

	public void openConnection(CabServerToClientInterface connection) {
		clientConnection = connection;
	}
	
	public void receiveClientPosition(Coordinate position) {
		
	}
	
	public void handleRequest(RideRequest request) {
		clientConnection.sendRoute(request);
	}
}
