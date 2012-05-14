package org.weeels.simulator;
import org.opentripplanner.routing.graph.*;
import java.util.*;

public class WorldSimulator {
	private Graph graph;
	private Timeline timeline;
	private CabPoolSimulator cabPool;
	

	public WorldSimulator(Graph graph, Timeline timeline, CabPoolSimulator cabPool) {
		this.graph = graph;
		this.timeline = timeline;
		this.cabPool = cabPool;
	}
	
	public Timeline getTimeline() {
		return timeline;
	}
	
	public CabPoolSimulator getCabs() {
		return cabPool;
	}
	
	public Graph getGraph() {
		return graph;
	}
}
