package org.weeels.simulator;

import java.util.*;

public class DispatchServer {
	HashSet<PassengerServer> passengerServers;
	CabPool cabPool;
	
	HashSet<CabServer> cabServers;
	//HashSet<Ride> rides;
	
	public DispatchServer(CabPool cabPool) {
		passengerServers = new HashSet<PassengerServer>();
		this.cabPool = cabPool;
	}
	
	public PassengerServer makePassengerServer() {
		PassengerServer server = new PassengerServer(this);
		passengerServers.add(server);
		return server;
	}
	
	public void rideRequested(RideRequest request) {
		CabServer cab = cabPool.getFreeCab();
		cab.handleRequest(request);
	}
}
