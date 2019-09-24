//////////////////////////////////////////////////////////////////////////////////////////////
// file: Time.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.time;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import java.time.Duration;
import java.time.Instant;

import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.reporting.Reporting;

//////////////////////////////////////////////////////////////////////////////////////////////
// class

// --------------------------------------------------------------------------------
/**
 * This class is responsible for timing a particular action and reporting the elapsed time,
 * acquiring date and time information as well as putting the automation into idle mode.
 */
public final class Time {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s)

	// step timer
	private static Instant timer_stepStart;
	private static Instant timer_stepEnd;
	private static Duration timer_stepInterval;

	// test timer
	private static Instant timer_methodStart;
	private static Instant timer_methodEnd;
	private static Duration timer_methodInterval;

	// suite timer
	private static Instant timer_suiteStart;
	private static Instant timer_suiteEnd;
	private static Duration timer_suiteInterval;

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static
	public static void engageStepTimer(boolean isStart, String functionName) {

		// is recording step timer enabled in config?
		if(Table.timer_isRecordStepTime) {

			if(isStart) {

				Reporting.text(String.format("\n ---> Starting Step Timer: %s", functionName));

				// capture start time
				Time.timer_stepStart = Instant.now();
			}
			else {
				
				// capture end time
				Time.timer_stepEnd = Instant.now();

				timer_stepInterval = Duration.between(timer_stepStart, timer_stepEnd);

				if(timer_stepInterval.getSeconds() >= 60.0) {
					
					Reporting.text(String.format(" ---> Stopping Step Timer: %s - %s Minute(s)", functionName, timer_stepInterval.getSeconds() / 60.0));
				}
				else {
					
					Reporting.text(String.format(" ---> Stopping Step Timer: %s - %s Second(s)", functionName, timer_stepInterval.getSeconds()));
				}
				
				Time.timer_stepStart = null;
				Time.timer_stepEnd = null;
				Time.timer_stepInterval = null;
			}
		}
		
		return;
	}
	
	public static void engageMethodTimer(boolean isStart, String functionName) {

		// is recording test timer enabled in config?
		if(Table.timer_isRecordMethodTime) {

			if(isStart) {

				Reporting.text(String.format(" -> Starting Test Timer: %s", functionName));

				// capture start time
				Time.timer_methodStart = Instant.now();
			}
			else {
				
				// attempt to capture the end time and determine the elapsed time
				try {

					// capture end time
					Time.timer_methodEnd = Instant.now();

					timer_methodInterval = Duration.between(timer_methodStart, timer_methodEnd);

					// if the seconds is greater than 60, display in minute format
					if(timer_methodInterval.getSeconds() >= 60.0) {
						
						Reporting.text(String.format(" -> Stopping Test Timer: %s - %s Minute(s)", functionName, timer_methodInterval.getSeconds() / 60.0));
					}
					else {
						
						Reporting.text(String.format(" -> Stopping Test Timer: %s - %s Second(s)", functionName, timer_methodInterval.getSeconds()));
					}
				}
				catch(Exception e) {
					
					Reporting.error("Error: Unable to determine elapsed time - " + e.getMessage());
				}

				// reset the time values
				Time.timer_methodStart = null;
				Time.timer_methodEnd = null;
				Time.timer_methodInterval = null;
			}
		}
		
		return;
	}
	
	public static void engageSuiteTimer(boolean isStart, String functionName) {

		// is recording suite timer enabled in config?
		if(Table.timer_isRecordSuiteTime) {

			if(isStart) {

				Reporting.text(String.format("\n --> Starting Suite Timer: %s", functionName));

				// capture start time
				Time.timer_suiteStart = Instant.now();
			}
			else {
				
				// capture end time
				Time.timer_suiteEnd = Instant.now();

				timer_suiteInterval = Duration.between(timer_suiteStart, timer_suiteEnd);

				if(timer_suiteInterval.getSeconds() >= 60.0) {
					
					Reporting.text(String.format(" --> Stopping Suite Timer: %s - %s Minute(s)", functionName, timer_suiteInterval.getSeconds() / 60.0));
				}
				else {
					
					Reporting.text(String.format(" --> Stopping Suite Timer: %s - %s Second(s)", functionName, timer_suiteInterval.getSeconds()));
				}

				Time.timer_suiteStart = null;
				Time.timer_suiteEnd = null;
				Time.timer_suiteInterval = null;
			}
		}
		
		return;
	}
	
	public static void sleep(int milliseconds) {

		try {

			// idle for specified x milliseconds
			Thread.sleep(milliseconds);

		} catch (InterruptedException e) {

			// print out stack trace?
			if(Table.is_displayStackTrace) {

				// print stack trace
				e.printStackTrace();
			}

		    Reporting.error(String.format("InterruptedException: %s", e.getMessage()));
		}
		
		return;
	}
}

// file: Time.java
//////////////////////////////////////////////////////////////////////////////////////////////
