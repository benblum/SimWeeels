package org.weeels.simulator;

import java.util.*;

public class PassengerServer {
	public interface PassengerServerToClientInterface {
		
	}
	
	private PassengerServerToClientInterface clientConnection;
	private DispatchServer dispatchServer;
	
	public PassengerServer(DispatchServer dispatchServer) {
		this.dispatchServer = dispatchServer;
	}
	
	public void openConnection(PassengerServerToClientInterface clientConnection) {
		this.clientConnection = clientConnection;
	}
	
	public void receiveRideRequest(RideRequest request) {
		dispatchServer.rideRequested(request);
	}
}
