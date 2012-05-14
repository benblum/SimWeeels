package org.weeels.simulator;
import org.opentripplanner.routing.spt.*;
import com.vividsolutions.jts.geom.Coordinate;
import java.util.Date;

public class CabClientSimulator {
	private Coordinate startPoint, endPoint, currentPosition;
	private Date pickupTime, dropoffTime;
	private final Timeline timeline;
	private static long UPDATE_INTERVAL = 1000;
	private double tripLength;
	private CabClientToServerInterface serverConnection;
	
	public CabClientSimulator(Timeline timeline) {
		this.timeline = timeline;
		this.currentPosition = new Coordinate(0,0);
	}
	
	public void openConnection(CabClientToServerInterface serverConnection) {
		this.serverConnection = serverConnection;
	}
	
	public void receiveRoute(RideRequest request) {
		startPoint = request.startPoint;
		endPoint = request.endPoint;
		pickupTime = request.pickupTime;
		dropoffTime = request.dropoffTime;
		tripLength = (double)(dropoffTime.getTime()-pickupTime.getTime());
		currentPosition = new Coordinate(startPoint);
		timeline.addEvent(new TimedEvent(pickupTime) {
			public void run() {
				updatePositionOnRoute();
			}
		});
	}
	
	public Coordinate getCurrentPosition() {
		return currentPosition;
	}
	
	private void updatePositionOnRoute() {
		serverConnection.sendPosition(currentPosition);
		currentPosition.x = currentPosition.x + ((double)UPDATE_INTERVAL) / tripLength * (endPoint.x-startPoint.x);
		currentPosition.y = currentPosition.y + ((double)UPDATE_INTERVAL) / tripLength * (endPoint.y-startPoint.y);
		if(timeline.now().before(dropoffTime)) {
			timeline.addEvent(new TimedEvent(timeline.now().getTime()+UPDATE_INTERVAL) {
				public void run() {
					updatePositionOnRoute();
				}
			});
		}
	}
}
