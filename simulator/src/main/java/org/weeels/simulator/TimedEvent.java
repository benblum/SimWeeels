package org.weeels.simulator;
import java.util.Date;

public class TimedEvent implements Comparable<TimedEvent> {
  private Date date;
  
  public TimedEvent(long rawdate) {
	  date = new Date(rawdate);
  }
  
  public TimedEvent(Date date) {
	  this.date = date;
  }
  
  public int compareTo(TimedEvent other) {
	  return this.date.compareTo(other.date);
  }
  
  public Date getDate() {
	  return date;
  }
  
  public void run() {
  }
}
