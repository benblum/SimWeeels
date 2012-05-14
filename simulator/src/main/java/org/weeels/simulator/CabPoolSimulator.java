package org.weeels.simulator;

import java.util.*;

public class CabPoolSimulator extends CabPool implements Iterable<CabClientSimulator> {
	private Timeline timeline;
	private HashSet<CabClientSimulator> clientSet;
	
	public CabPoolSimulator(Timeline timeline) {
		this.timeline = timeline;
		clientSet = new HashSet<CabClientSimulator>();
	}
	
	@Override
	public synchronized CabServer getFreeCab() {
		CabServer server = new CabServer();
		CabClientSimulator client = new CabClientSimulator(timeline);
		clientSet.add(client);
		CabConnectionSimulator connection = new CabConnectionSimulator(server, client, timeline);
		server.openConnection(connection);
		client.openConnection(connection);
		return server;
	}
	
	@Override
	public Iterator<CabClientSimulator> iterator() {
		return clientSet.iterator();
	}
}