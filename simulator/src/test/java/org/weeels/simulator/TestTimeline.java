package org.weeels.simulator;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.Test;

public class TestTimeline {

	
	public void testRealTime() {
		Timeline timeline = new Timeline(3);
		TPEPParser parser = new TPEPParser("/Users/bblum/weeels/R/sunday-8am-5min.txt");
 		TPEPRide ride = parser.getNextRide();
 		while(ride != null) {
 			final Date d = ride.pickupTime();
 			timeline.addEvent(new TimedEvent(d) {
 				public void run() {
 					System.out.println(d.toString());
 				}
 			});
 			ride = parser.getNextRide();
 		}
    	timeline.run();
	}
	
	@Test
	public void testRequests() {
		Timeline timeline = new Timeline(1000);
		CabPoolSimulator cabPool = new CabPoolSimulator(timeline);
		DispatchServer dispatcher = new DispatchServer(cabPool);
		TPEPParser parser = new TPEPParser("/Users/bblum/weeels/R/sunday-8am-5min.txt");
 		TPEPRide ride = parser.getNextRide();
 		while(ride != null) {
 			PassengerClientSimulator passenger = new PassengerClientSimulator(timeline);
 			PassengerServer server = dispatcher.makePassengerServer();
 			PassengerConnectionSimulator connection = new PassengerConnectionSimulator(passenger, server);
 			passenger.openConnection(connection);
 			server.openConnection(connection);
 			passenger.makeRequest(ride);
 			ride = parser.getNextRide();
 		}
 		timeline.setTickInterval(5000);
 		timeline.addTickListener(new TestTickListener());
    	timeline.run();
	}
	
	public class TestTickListener implements TickListener {
		public void tick(Date currentTime) {
			System.out.println(currentTime);
		}
	}

}
