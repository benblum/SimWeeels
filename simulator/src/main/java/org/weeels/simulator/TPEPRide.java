package org.weeels.simulator;

import java.util.*;
import java.text.*;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

import org.opentripplanner.routing.location.StreetLocation;

public class TPEPRide {
  private static final String[] stringFieldNames = {"Medallion",};
  private static final String[] dateFieldNames = {"Trip_Pickup_DateTime","Trip_Dropoff_DateTime"};
  private static final String[] doubleFieldNames = {"Start_Lon","Start_Lat","End_Lon","End_Lat",
	  "Fare_Amt","Tolls_Amt","Tip_Amt","surcharge","Total_Amt", "distanceBetweenService"};
  private HashMap<String, String> stringFields;
  private HashMap<String, Date> dateFields;
  private HashMap<String, Double> doubleFields;
  private Coordinate startPoint, endPoint;
  
  private static int[] stringFieldIndex, dateFieldIndex, doubleFieldIndex;
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static void setFields(ArrayList<String> fields) {
	  stringFieldIndex = new int[stringFieldNames.length];
	  dateFieldIndex = new int[dateFieldNames.length];
	  doubleFieldIndex = new int[doubleFieldNames.length];
	  
	  //dateFormat.setTimeZone(TimeZone.getTimeZone("EST"));
	  
	  for(int i=0; i < stringFieldNames.length; i++)
		  stringFieldIndex[i] = fields.indexOf(stringFieldNames[i]);
	  for(int i=0; i < dateFieldNames.length; i++)
		  dateFieldIndex[i] = fields.indexOf(dateFieldNames[i]);
	  for(int i=0; i < doubleFieldNames.length; i++)
		  doubleFieldIndex[i] = fields.indexOf(doubleFieldNames[i]);
  }
  
  public TPEPRide(ArrayList<String> values) throws ParseException {
	  stringFields = new HashMap<String, String>();
	  dateFields = new HashMap<String, Date>();
	  doubleFields = new HashMap<String, Double>();
	  
	  for(int i=0; i < stringFieldIndex.length; i++)
		  if(stringFieldIndex[i] != -1)
			  stringFields.put(stringFieldNames[i], values.get(stringFieldIndex[i]));
	  for(int i=0; i < dateFieldIndex.length; i++)
		  if(dateFieldIndex[i] != -1)
			  dateFields.put(dateFieldNames[i], dateFormat.parse(values.get(dateFieldIndex[i])));
	  for(int i=0; i < doubleFieldIndex.length; i++)
		  if(doubleFieldIndex[i] != -1)
			  doubleFields.put(doubleFieldNames[i], Double.parseDouble(values.get(doubleFieldIndex[i])));
	  
	  if(doubleFields.containsKey("Start_Lon") && doubleFields.containsKey("Start_Lat"))
		  startPoint = new Coordinate(doubleFields.get("Start_Lon"),doubleFields.get("Start_Lat"));
	  if(doubleFields.containsKey("End_Lon") && doubleFields.containsKey("End_Lat"))
		  endPoint = new Coordinate(doubleFields.get("End_Lon"),doubleFields.get("End_Lat"));
  }
  
  public Date pickupTime() {
	  return dateFields.get(dateFieldNames[0]);
  }
  
  public Date dropoffTime() {
	  return dateFields.get(dateFieldNames[1]);
  }
  
  public Coordinate getStartPoint() {
	  return startPoint;
  }
  
  public Coordinate getEndPoint() {
	  return endPoint;
  }
}
