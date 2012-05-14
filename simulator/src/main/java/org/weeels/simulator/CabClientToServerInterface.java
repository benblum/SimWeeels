package org.weeels.simulator;

import com.vividsolutions.jts.geom.Coordinate;

public interface CabClientToServerInterface {
	public void sendPosition(final Coordinate position);
}
