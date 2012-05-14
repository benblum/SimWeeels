package org.weeels.simulator;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;

public class TestTPEPParser {

	@Test
	public void testFirstRide() {
		TPEPParser parser = new TPEPParser("/Users/bblum/weeels/R/sunday-8am-5min.txt");
		Calendar calendar = Calendar.getInstance();
		TPEPRide ride = parser.getNextRide();
		assertTrue(Math.abs(ride.getStartPoint().x + 73.977633) < 1e-5 );
		calendar.setTime(ride.pickupTime());
		assertTrue(calendar.get(Calendar.YEAR) == 2010);
	}
	
	@Test
	public void testAllRides() {
		TPEPParser parser = new TPEPParser("/Users/bblum/weeels/R/sunday-8am-5min.txt");
		Calendar calendar = Calendar.getInstance();
		TPEPRide ride = parser.getNextRide();
		int total = 0;
		while(ride != null) {
			System.out.println(ride.getStartPoint().x);
			ride = parser.getNextRide();
			total++;
		}
		System.out.println(total);
	}
}
