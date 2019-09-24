//////////////////////////////////////////////////////////////////////////////////////////////
// file: Reporting.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.reporting;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.net.UnknownHostException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

import java.time.LocalDateTime;

import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.proxy.BrowserMob;

import net.lightbody.bmp.core.har.Har;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public final class Reporting {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s) -- static

	public static String folder_results              = "results";
	public static String folder_log                  = "log";
	public static String folder_har                  = "har";

	private static Har har                            = null;

	private static File harFile                       = null;

	private static String log_filename                = null;

	private static FileWriter fw_log                  = null;

	// supporting text
	public static final String constant_testSkip      = "Testing was disabled for this test";
	public static final String constant_testUISkip    = "UI " + Reporting.constant_testSkip;
	public static final String constant_testAPISkip   = "API " + Reporting.constant_testSkip;

	public static enum TestResultState
	{
		RETRY("Retry"),
		PENDING("Pending"),
		PASSED("Passed"),
		FAILED("Failed"),
		SKIPPED("Skipped"),
		WARNING("Warning");

		private String testResultState;

		TestResultState(String trs) {

			this.testResultState = trs;

		}
		 
		public String getTestResultState() {

			return this.testResultState;
		}
	}
	
	public static enum TestProgressState
	{
		START_STEP("Starting Step"),
		END_STEP("Ending Step"),

		START_TEST("Starting Test"),
		END_TEST("Ending Test");

		private String testProgressState;

		TestProgressState(String tps) {

			this.testProgressState = tps;

		}

		public String getTestProgressState() {

			return this.testProgressState;
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static	
	
	public static void systemInformation() {
		
		// display system information?
		if(Table.is_displaySystemInformation) {

			Reporting.text("\n\n\n+++++++ System Information +++++++");
			Reporting.text(String.format("Current Date/Time:\t\t %s", LocalDateTime.now().toString()));

			// attempt to display system information
			try {

				// display system information
				Reporting.text(String.format("Host Name:\t\t\t\t %s", java.net.InetAddress.getLocalHost().getHostName().toString()));
				Reporting.text(String.format("IP Address:\t\t\t\t %s", java.net.InetAddress.getLocalHost().toString()));

			} catch (UnknownHostException e) {

			    Reporting.error(String.format("IOException: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				};
			}

			// attempt to display system information
			try {

				// display system information
				Reporting.text(String.format("OS Name:\t\t\t\t %s", System.getProperty("os.name").toString()));
				Reporting.text(String.format("OS Version:\t\t\t\t %s", System.getProperty("os.version").toString()));
				Reporting.text(String.format("OS Architecture:\t\t %s", System.getProperty("os.arch").toString()));
				Reporting.text(String.format("Java Version:\t\t\t %s", System.getProperty("java.version").toString()));
				Reporting.text(String.format("Java Home Dir:\t\t\t %s", System.getProperty("java.home").toString()));
				Reporting.text(String.format("Java Lib Dir:\t\t\t %s", System.getProperty("java.library.path").toString()));
				Reporting.text(String.format("JVM Impl Version:\t\t %s", System.getProperty("java.vm.version").toString()));
				Reporting.text(String.format("JVM Impl Name:\t\t\t %s", System.getProperty("java.vm.name").toString()));
				Reporting.text(String.format("Operator:\t\t\t\t %s", System.getProperty("user.name").toString()));
				Reporting.text(String.format("Operator Dir:\t\t\t %s", System.getProperty("user.dir").toString()));
				Reporting.text(String.format("Path Separator:\t\t\t %s", System.getProperty("path.separator").toString()));
				Reporting.text(String.format("File Separator:\t\t\t %s", System.getProperty("file.separator").toString()));

			} catch (Exception e) {
				
			    Reporting.error(String.format("Exception: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				};
			}

			Reporting.text("");
		}
		
	    return;
	}
	
	public static void testModuleInformation() {
		
    	// is display test module information?
		if(Table.is_displayTestModuleInformation) {

			// attempt display test module information
			try {

				// display test module information
				Reporting.text("\n\n+++++ Test Information +++++");
				Reporting.text(String.format("Suite Name:\t\t\t\t %s", Table.incoming_suiteName));
				Reporting.text(String.format("Test Name:\t\t\t\t %s", Table.incoming_testName));
				Reporting.text(String.format("Class Name:\t\t\t\t %s", Table.incoming_className));
				Reporting.text(String.format("Method Name:\t\t\t %s", Table.incoming_methodName));

				// is it windows?
				if(System.getProperty("os.name").toLowerCase().contains("windows")) {
					
					Reporting.text(String.format("Webdriver Dir:\t\t\t %s", Table.incoming_driverPath_win));
				}
				else {

					Reporting.text(String.format("Webdriver Dir:\t\t\t %s", Table.incoming_driverPath_other));
				}

				Reporting.text(String.format("Browser:\t\t\t\t %s", Table.incoming_browser));
				Reporting.text(String.format("Headless:\t\t\t\t %s", Table.incoming_isHeadless));
				Reporting.text(String.format("Start URL:\t\t\t\t %s", Table.incoming_loadUrl));
				Reporting.text(String.format("Running Remote:\t\t\t %s", Table.incoming_isRemote));

				// output remote hostname
				if(Table.incoming_isRemote) {
					Reporting.text(String.format("IP Address:\t\t\t %s", Table.incoming_remoteIp));
				}

				Reporting.text(String.format("Running Proxy Server:\t %s", Table.incoming_is_proxy));
				Reporting.text(String.format("Running UI:\t\t\t\t %s", Table.incoming_is_uiTest));
				Reporting.text(String.format("Running API:\t\t\t %s", Table.incoming_is_apiTest));
				Reporting.text(String.format("Execute Test:\t\t\t %s", Table.incoming_isRunMethod));
				Reporting.text("");
			}
			catch(Exception e) {
				
			    Reporting.error(String.format("Exception: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				};
			}
		}

		return;
	}
	
	public static void text(String message) {
	
		// send message to log file?
		if(Table.is_writeToLogFile) {
			
			// send message to file
			Reporting.outputStream_logFile(message);
		}
		
		// send message to stdout?
		if(Table.is_displayOutput) {

			// send to stdout
			System.out.println(message);
		}
		
		return;
	}
	
	public static void error(String message) {
	
		// send message to log file?
		if(Table.is_writeToLogFile) {
			
			// send message to file
			Reporting.outputStream_logFile(message);
		}
		
		// send message to stderr?
		if(Table.is_displayOutput) {

			// send to stderr
			System.err.println("Exception: " + message);
		}
		
		return;
	}

	public static void display_testCaseProgress(Reporting.TestProgressState progress, String functionName) {

		Reporting.text(String.format(" --> %s: %s", progress.getTestProgressState(), functionName));

		return;
	}

	public static void display_testStepProgress(Reporting.TestProgressState progress, String functionName) {

		Reporting.text(String.format(" ----> %s: %s", progress.getTestProgressState(), functionName));

		return;
	}

	public static void statusMessage(TestResultState state, String message) {

		Reporting.text(String.format(" -----> %s: %s", state, message.trim()));
		
		return;
	}
	
	public static boolean initializeStream_logFile() {
		
		boolean isAction = false;
		
		String folder_logFullPath = String.format("%s/%s",  Reporting.folder_results, Reporting.folder_log);
		
		Path path_filename = null;
		
		// create the full file path
		Reporting.log_filename = String.format("%s/log.dat", folder_logFullPath);
		
		// attempt to delete and create the new log file
		try {
			
			path_filename = Paths.get(log_filename);

			// attempt to delete the old log file
			try {

			//	// delete the old log file
				Files.delete(path_filename);

			} catch (NoSuchFileException e) {

				// do noting if a log does not already exist
			}

			// create the filewriter for the new log
			Reporting.fw_log = new FileWriter(Reporting.log_filename, true);
			
			isAction = true;

		} catch (IOException e) {

		    Reporting.error(String.format("IOException: %s", e.getMessage()));

			// print out stack trace?
			if(Table.is_displayStackTrace) {

				// print stack trace
				e.printStackTrace();
			}
		}

		return isAction;
	}

	public static void closeStream_logFile() {
		
		try {

			// close the log file
			Reporting.fw_log.close();

		} catch (IOException e) {

		    Reporting.error(String.format("IOException: %s", e.getMessage()));

			// print out stack trace?
			if(Table.is_displayStackTrace) {

				// print stack trace
				e.printStackTrace();
			};
		}

		return;
	}

	public static void outputStream_logFile(String message) {

		try
		{
		    // write to disk
		    Reporting.fw_log.write(message + '\n');
		    
		}
		catch(IOException e) {

			// print out stack trace?
			if(Table.is_displayStackTrace) {

				// print stack trace
				e.printStackTrace();
			}

		    Reporting.error(String.format("IOException: %s", e.getMessage()));
		}	
		
		return;
	}

	public static void outputStream_harFile(String moduleName) {

		// proceed with outputting the HAR file only if proxy server was enabled and HAR settings were configured
		if(Table.incoming_is_proxy && Table.browser_proxy_isCreateSeleniumProxy && Table.browser_proxy_isSetHarCaptureTypes) {

			String folder_harFullPath = String.format("%s/%s",  Reporting.folder_results, Reporting.folder_har);
			
			// get the HAR data
			har = BrowserMob.proxy.getHar();
			
			// generate HAR file name based off the "page name"
			harFile = new File(String.format("%s/%s.har", folder_harFullPath, moduleName.toString()));

			// attempt to create new HAR file
			try {

				// create new HAR file
				harFile.createNewFile();

			} catch (IOException e) {

				Reporting.error(String.format("IOException: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				}
			}

			// attempt to write HAR data to file
			try {

				// write to HAR file
				har.writeTo(harFile);

			} catch (IOException e) {

				Reporting.error(String.format("IOException: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				}
			}
		}
		
		return;
	}
}

// file: Reporting.java
//////////////////////////////////////////////////////////////////////////////////////////////
