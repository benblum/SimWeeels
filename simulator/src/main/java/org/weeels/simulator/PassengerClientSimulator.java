package org.weeels.simulator;


public class PassengerClientSimulator implements PassengerClient {
	private Timeline timeline;
	private PassengerClientToServerInterface serverConnection;
	
	PassengerClientSimulator(Timeline timeline) {
		this.timeline = timeline;
	}
	
	public void openConnection(PassengerClientToServerInterface serverConnection) {
		this.serverConnection = serverConnection;
	}
	
	public void makeRequest(final TPEPRide ride) {
		timeline.addEvent(new TimedEvent(ride.pickupTime()) {
			public void run() {
				serverConnection.sendRequest(new RideRequest(ride.getStartPoint(), ride.getEndPoint(), ride.pickupTime(), ride.dropoffTime()));
			}
		});
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
}
