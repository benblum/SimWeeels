package org.weeels.simulator;

import java.util.Date;
import com.vividsolutions.jts.geom.Coordinate;

public class RideRequest {
	Coordinate startPoint, endPoint;
	Date pickupTime, dropoffTime;
	
	public RideRequest(Coordinate startPoint, Coordinate endPoint, Date pickupTime, Date dropoffTime) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.pickupTime = pickupTime;
		this.dropoffTime = dropoffTime;
	}
}
