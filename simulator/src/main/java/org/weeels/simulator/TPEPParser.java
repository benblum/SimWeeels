package org.weeels.simulator;
import java.util.*;
import java.text.*;
import java.io.*;

public class TPEPParser {
	
	private BufferedReader input;
	private ArrayList<String> fields;
	
	public TPEPParser( String fileName ) {
		if ( fileName == null ) {
			throw new IllegalArgumentException("File name cannot be null.");
		}
		try {
			this.input = new BufferedReader(new FileReader(fileName));
			fields = splitLine(input.readLine());
			TPEPRide.setFields(fields);
		} catch(FileNotFoundException e) {
			System.out.println("File not found!");
		} catch(IOException e) {
			System.out.println("Error reading file.");
		}
	}

	public TPEPRide getNextRide() {
		while(true) {
			try {
				String line = input.readLine();
				if(line == null)
					return null;
				ArrayList<String> values = splitLine(line);
				if(values.size() == fields.size())
					return new TPEPRide(values);
				else
					continue;
			} catch(ParseException e) {
				continue;
			} catch(IOException e) {
				return null;
			}
		}
	}
	
	private ArrayList<String> splitLine(String line) {
		ArrayList<String> result = new ArrayList<String>();
		String currentDelims = fCOMMAS_AND_QUOTES;
		StringTokenizer parser = new StringTokenizer(line, currentDelims, true);
		while ( parser.hasMoreTokens() ) {
			String token = parser.nextToken(currentDelims);
			if ( !isDoubleQuote(token) ){
				result.add(token);
			}
			else {
				currentDelims = flipDelimiters(currentDelims);
			}
		}
		return result;
	}

	// PRIVATE //
	private static final String fDOUBLE_QUOTE = "\"";

	//the parser flips between these two sets of delimiters
	private static final String fCOMMAS_AND_QUOTES = ",\"";
	private static final String fQUOTES_ONLY ="\"";

	private boolean isDoubleQuote( String aToken ){
		return aToken.equals(fDOUBLE_QUOTE);
	}

	private String flipDelimiters( String aCurrentDelims ) {
		String result = null;
		if ( aCurrentDelims.equals(fCOMMAS_AND_QUOTES) ) {
			result = fQUOTES_ONLY;
		}
		else {
			result = fCOMMAS_AND_QUOTES;
		}
		return result;
	}
}
