package org.weeels.simulator;
import java.util.*;

/**
 * Global timeline. Events are added as anonymous subclasses of TimedEvent which override the run()
 * method to perform the desired action. 
 *
 */
public class Timeline 
{
	private PriorityQueue<TimedEvent> eventQueue;
	private Date currentTime;
	private ArrayList<TickListener> tickListeners;
	private double scale;
	private long realStartTime;
	private long simulationStartTime;
	private long tickInterval;
	private long lastTick;
	
	public Timeline(double scale) {
		eventQueue = new PriorityQueue<TimedEvent>();
		tickListeners = new ArrayList<TickListener>();
		this.scale = scale;
		tickInterval = 0;
		lastTick = 0;
	}
	
	private void initializeTimes() {
		realStartTime = System.currentTimeMillis();
		simulationStartTime = eventQueue.peek().getDate().getTime();
		lastTick = simulationStartTime;
	}
	
	public void run() {
		initializeTimes();
		while(!eventQueue.isEmpty())
			runOneEvent();
	}
	
	private synchronized void runOneEvent() {
		long realElapsed, simulationElapsed, gap;
		TimedEvent event = eventQueue.poll();
		simulationElapsed = event.getDate().getTime() - simulationStartTime;
		realElapsed = System.currentTimeMillis() - realStartTime;
		gap = (long)((double)simulationElapsed - scale * (double)realElapsed);
		while(gap > 0) {
			try {
				wait(gap);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			realElapsed = System.currentTimeMillis() - realStartTime;
			gap = (long)((double)simulationElapsed - scale * (double)realElapsed);
		}
		currentTime = event.getDate();
		if(tickInterval == 0) // 
			notifyTickListeners();
		event.run();
	}
	
	public void runUntil(Date endTime) {
		initializeTimes();
		while(!eventQueue.isEmpty() && !eventQueue.peek().getDate().after(endTime))
			runOneEvent();
	}
	
	public void addEvent(TimedEvent event) {
		assert(!currentTime.after(event.getDate()));
		if(tickInterval > 0) { // Add empty clock events
			while(event.getDate().getTime() > lastTick) {
				eventQueue.add(new TimedEvent(lastTick + tickInterval) {
					public void run() {
						notifyTickListeners();
					}
				});
				lastTick += tickInterval;
			}
		}
		eventQueue.add(event);
	}
	
	public Date now() {
		return currentTime;
	}
	
	public void addTickListener(TickListener listener) {
		tickListeners.add(listener);
		listener.tick(currentTime);
	}
	
	private void notifyTickListeners() {
		for(TickListener listener : tickListeners) {
			listener.tick(currentTime);
		}
	}
	
	public void setTickInterval(long interval) {
		tickInterval = interval;
	}
}
