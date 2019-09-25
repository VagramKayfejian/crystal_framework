//////////////////////////////////////////////////////////////////////////////////////////////
// file: CFramework.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.io.FileUtils;

import org.testng.TestNG;
import org.testng.SkipException;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.browser.Browser;
import com.armondsarkisian.crystal.resource.browser.Chrome;
import com.armondsarkisian.crystal.resource.proxy.BrowserMob;
import com.armondsarkisian.crystal.resource.reporting.Reporting;
import com.armondsarkisian.crystal.resource.time.Time;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

//////////////////////////////////////////////////////////////////////////////////////////////
// class

// --------------------------------------------------------------------------------
/**
 * This class is the base class representing the parent module. All other modules must
 * inherit from this class. CFramework is responsible for processing incoming values, initializing
 * the automation framework and tearing it down.
 */
public class CFramework {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s) -- static
	
	private static int total_suites_count                      = 0;
	private static int total_classes_count                     = 0;
	private static int total_testSections_count                = 0;
	private static int total_groups_count                      = 0;
	private static int total_methods_count                     = 0;

	private static int total_methods_skipped                   = 0;
	private static ArrayList<String> skipped_methods           = new ArrayList<String>();

	private static final boolean isRunBeforeSuite              = true;
	private static final boolean isRunAfterSuite               = true;
	private static final boolean isRunBeforeClass              = true;
	private static final boolean isRunAfterClass               = true;
	private static final boolean isRunAfterTest                = true;
	private static final boolean isRunBeforeTest               = true;
	private static final boolean isRunBeforeGroups             = true;
	private static final boolean isRunAfterGroups              = true;
	private static final boolean isRunBeforeMethod             = true;
	private static final boolean isRunAfterMethod              = true;

    public static TestNG myTestNG                              = new TestNG();
    
	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will skip a specified test
	 *
	 * @param  message  the message to display when skipping a specified test
	 * @return null
	 */
	public static void skipTest(String message) {

		// record the skipped method name
		CFramework.skipped_methods.add(Table.incoming_methodName);
		
		// keep track of how many skipped tests there are
		CFramework.total_methods_skipped += 1;

		message += " - " + Table.incoming_methodName;

		Reporting.statusMessage(Reporting.TestResultState.SKIPPED, message);

		throw new SkipException(String.format("Skipping - %s", message));
	}
	
	

	
	// --------------------------------------------------------------------------------
	/**
	 * This method will execute when a test method passes
	 *
	 * @param  message  the message to display when passing a specified test
	 * @return null
	 */
	public static void onPassTest(String message) {

		message += " - " + Table.incoming_methodName;

		Reporting.statusMessage(Reporting.TestResultState.PASSED, message);
		
		return;
	}
	
	
	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will execute when a test method fails
	 *
	 * @param  message  the message to display when failing a specified test
	 * @return null
	 */
	public static void onFailTest(String message) {

		message += " - " + Table.incoming_methodName;

		Reporting.statusMessage(Reporting.TestResultState.FAILED, message);
	}
	

	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will set suite level configuration
	 *
	 * @return void
	 */
	private static void setSuiteLevelConfiguration() {
		
		// is set to allow unrecognized names?
		if(Table.ssl_handshake_allowUnrecognizedNames) {

			// set to allow unrecognized names
			System.setProperty("jsse.enableSNIExtension", "false");
		}

		return;
	}
	// --------------------------------------------------------------------------------
	
	

	
	// --------------------------------------------------------------------------------
	/**
	 * This method will create the necessary folders required for this project
	 *
	 * @return boolean
	 */
	private static void initializeProjectFolders() {
		
		boolean isFolder;
		
		try {
			
			// recursively deltree
			File results = new File("results");

			FileUtils.deleteDirectory(results);

		}
		catch(IllegalArgumentException e) {
			
			// do nothing
		}
		catch(IOException e) {
			
			// do nothing
		}
		
		// attempt to create the results folder
		try {

			String path = String.format("%s", Reporting.folder_results);

			// does results folder not exist?
			if(!new File(path).exists()) {

				// create the results folder
				isFolder = (new File(path)).mkdirs();
				
				if(!isFolder) {
					
					Reporting.error("Error: Unable to create \'results\' folder!");
				}
			}
		}
		catch(Exception e) {
			
			Reporting.error("Exception: Unable to create results folder");
			e.printStackTrace();
		}
		
		// attempt to create the results/log folder
		try {
			
			String path = String.format("%s/%s", Reporting.folder_results, Reporting.folder_log);

			// does results folder not exist?
			if(!new File(path).exists()) {

				// create the results/log folder
				isFolder = (new File(path)).mkdirs();

				if(!isFolder) {
					
					Reporting.error("Error: Unable to create \'results\\log\\\' folder!");
				}
			}
		}
		catch(Exception e) {
			
			Reporting.error("Exception: Unable to create results/log folder");

			e.printStackTrace();
		}

		// attempt to create the results/log/har folder
		try {

			String path = String.format("%s/%s/%s", Reporting.folder_results, Reporting.folder_log, Reporting.folder_har);

			// does results folder not exist?
			if(!new File(path).exists()) {

				// create the results/har folder
				isFolder = (new File(path)).mkdirs();

				if(!isFolder) {
					
					Reporting.error("Error: Unable to create \'results\\log\\har\\\' folder!");
				}
			}
		}
		catch(Exception e) {
			
			Reporting.error("Exception: Unable to create results/log/har folder");

			e.printStackTrace();
		}

		return;
	}
	// --------------------------------------------------------------------------------
	



	// --------------------------------------------------------------------------------
	/**
	 * This method will capture all the incoming data and store it in
	 * the appropriate attribute within the Table class. These values
	 * are provided externally and can be modified outside the program.
	 *
	 * @param  suite_name  the name of the suite_name provided externally
	 * @param  test_name  the name of the test_name provided externally
	 * @param  class_name  the name of the class_name provided externally
	 * @param  is_run_method  the option to execute the current test method
	 * @param  method_name  the name of the method_name provided externally
	 * @param  is_remote  the option to run the automation remotely
	 * @param  remote_ip  the ip address which you will use to access the selenium grid
	 * @param  remote_port  the port which you will use to access the selenium grid
	 * @param  is_proxy  the option to run the automation utilizing a web proxy
	 * @param  is_ui_test  the option to run the automation with ui tests enabled
	 * @param  browser  the browser which will be used to validate ui
	 * @param  is_headless  the option to run in headless mode
	 * @param  is_api_test  the option to run the automation with api tests enabled
	 * @param  load_url  the initial url which will get loaded in the specified browser
	 * @param  driver_path_other the path to where all the drivers reside on mac/linux
	 * @param  driver_path_win the path to where all the drivers reside on windows
	 * @return null
	 */
	public static void processIncomingValues(String suite_name, String test_name, String class_name, String is_run_method, String method_name, String is_remote, String remote_ip, String remote_port, String is_proxy, String is_ui_test, String browser, String is_headless, String is_api_test, String load_url, String driver_path_other, String driver_path_win) {
		
		// attempt to add these external incoming values
		try {

			// add incoming values to Table options
			Table.incoming_suiteName = suite_name.toLowerCase().trim();
			Table.incoming_testName = test_name.toLowerCase().trim();
			Table.incoming_className = class_name.toLowerCase().trim();
			Table.incoming_isRunMethod = Boolean.parseBoolean(is_run_method);
			Table.incoming_methodName = method_name.toLowerCase().trim();
			Table.incoming_isRemote = Boolean.parseBoolean(is_remote);
			Table.incoming_remoteIp = remote_ip.trim();
			Table.incoming_remotePort = remote_port.trim();
			Table.incoming_is_proxy = Boolean.parseBoolean(is_proxy);
			Table.incoming_is_uiTest = Boolean.parseBoolean(is_ui_test);
			Table.incoming_browser = browser.toLowerCase().trim();
			Table.incoming_isHeadless = Boolean.parseBoolean(is_headless);
			Table.incoming_is_apiTest = Boolean.parseBoolean(is_api_test);
			Table.incoming_loadUrl = load_url.toLowerCase().trim();
			Table.incoming_driverPath_other = driver_path_other.trim();
			Table.incoming_driverPath_win = driver_path_win.trim();
		}
		catch(Exception e) {
			
			// print exception
			System.err.println("Exception: " + e.getMessage());
			
			// print stack trace
			e.printStackTrace();
		}
		
		return;
	}
	// --------------------------------------------------------------------------------

	

	
	// --------------------------------------------------------------------------------
	/**
	 * This method will setup the automation and initialize all of its components. The
	 * components include setting up the log file for writing, determining which browser
	 * to use and which options/capabilities to use. Initiating the web proxy server, 
	 * creating the HAR file and determining whether the browser will run remotely or locally.
	 *
	 * @return null
	 */
	public static void setupAutomation() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  

	    // overwrite the location for log4j.properties file?
		//String log4jConfPath = "/new/path/to/log4j.properties";
		//PropertyConfigurator.configure(log4jConfPath);
	    
		Reporting.text("Automation: Initialization Started");
		Reporting.text("\n\nDate/Time: " + dtf.format(now).toString());
	    Reporting.text("----------------------------------");

	    // is testing ui?
	    if(Table.incoming_is_uiTest) {

            // is setup proxy?
            if(Table.incoming_is_proxy) {

                //TODO: setup support for remote proxy, temporarily disallowing proxy for remote
                // create proxy
                BrowserMob.createProxy();
                
                // set proxy options
                BrowserMob.setProxyOptions();
                
                // start the proxy
                BrowserMob.startProxy();
                
                // create the selenium binding
                BrowserMob.createSeleniumProxy();
                
            }

            // is browser chrome?
            if(Table.incoming_browser.toLowerCase().contains("chrome")) {
                
                Reporting.text("Browser: Preparing To Load Chrome/Chromium Driver");

                // set chrome  config
                Chrome.setOptions();

                Reporting.text("Chrome - Table [Options]: Finalize & Set Options");

                // instantiate chrome webdriver
                Chrome.instantiateWebDriver();

                // timeout config
                Chrome.setBrowserTimeoutOptions(Table.explicit_wait);
			}

            // is set har config?
            if(Table.incoming_is_proxy) {

                // set har capture types
                BrowserMob.setHarCaptureTypes(Table.browser_proxy_setCaptureType);
                
                // create HAR logger stream
                BrowserMob.createHar();
            }

			// navigate to specified start page
			Browser.goTo(Table.incoming_loadUrl, Table.sleep_page_load, false);
			
			// capture & store current window handle
			Browser.getCurrentWindowHandle();
		
			Reporting.text("------------------------------------");
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			now = LocalDateTime.now();  
			Reporting.text("\n\nDate/Time: " + dtf.format(now).toString());
			Reporting.text(String.format("Automation: Initialization Completed - %s", Table.incoming_browser));

			Reporting.text(String.format("\n\n\nAutomation: Execution Started - %s", Table.incoming_browser));
			dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			now = LocalDateTime.now();  
			Reporting.text("\n\nDate/Time: " + dtf.format(now).toString());
			Reporting.text("-----------------------------");
	    }
	    
		return;
	}
	// --------------------------------------------------------------------------------
		
	
	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method is responsible for tearing down the automation. It will close the
	 * specified browser, stop the web proxy server and also stop the file output stream
	 *
	 * @return null
	 */
	public static void teardownAutomation() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  

		Reporting.text("-------------------------------");
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		now = LocalDateTime.now();  
		Reporting.text("\n\nDate/Time: " + dtf.format(now).toString());
		Reporting.text("Automation: Execution Completed");
		
		Reporting.text("\n\n\nAutomation: Teardown Started");
	    Reporting.text("----------------------------");

	    // is testing ui?
	    if(Table.incoming_is_uiTest) {

			// write to HAR file (if enabled)
			Reporting.outputStream_harFile(Table.incoming_methodName);

            Browser.releaseWebDriver();
			
            Reporting.text("Browser: Chrome/Chromium Driver Shutdown Successfully");
	    }

		// only stop the proxy if it was enabled previously
		if (Table.incoming_is_proxy) {

			// shutdown the web proxy
			BrowserMob.stopProxy();
		}

	    Reporting.text("------------------------------");
		dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		now = LocalDateTime.now();  
		Reporting.text("\n\nDate/Time: " + dtf.format(now).toString());
		Reporting.text(String.format("Automation: Teardown Completed - %s", Table.incoming_browser));
		
		Reporting.text("\n\n\n======================================================================================================\n");

		return;
	}
	// --------------------------------------------------------------------------------
	
	
	

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- non-static

	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute prior to every method event. This will setup the necessary
	 * components to ensure the automation executes without any issues.
	 *
	 * @param  suite_name  the name of the suite_name provided externally
	 * @param  test_name  the name of the test_name provided externally
	 * @param  class_name  the name of the class_name provided externally
	 * @param  method_name  the name of the method_name provided externally
	 * @param  is_remote  the option to run the automation remotely
	 * @param  remote_ip  the ip address which you will use to access the selenium grid
	 * @param  remote_port  the port which you will use to access the selenium grid
	 * @param  is_proxy  the option to run the automation utilizing a web proxy
	 * @param  is_ui_test  the option to run the automation with ui tests enabled
	 * @param  browser  the browser which will be used to validate ui
	 * @param  is_headless  the option to run in headless browser mode
	 * @param  is_api_test  the option to run the automation with api tests enabled
	 * @param  load_url  the initial url which will get loaded in the specified browser
	 * @param  driver_path_other  the path to where all the drivers reside on your mac/linux
	 * @param  driver_path_win  the path to where all the drivers reside on your windows
	 * @return null
	 */
	@Parameters({"suite_name", "test_name", "class_name", "is_run_method", "method_name", "is_remote", "remote_ip", "remote_port", "is_proxy", "is_ui_test", "browser", "is_headless", "is_api_test", "load_url", "driver_path_other", "driver_path_win"})
	@BeforeMethod(enabled=CFramework.isRunBeforeMethod, alwaysRun=true)
	public void beforeMethod(String suite_name, String test_name, String class_name, String is_run_method, String method_name, String is_remote, String remote_ip, String remote_port, String is_proxy, String is_ui_test, String browser, String is_headless, String is_api_test, String load_url, String driver_path_other, String driver_path_win) throws Exception {

		// increase method test count
		total_methods_count += 1;

		// process external values
		CFramework.processIncomingValues(suite_name, test_name, class_name, is_run_method, method_name, is_remote, remote_ip, remote_port, is_proxy, is_ui_test, browser, is_headless, is_api_test, load_url, driver_path_other, driver_path_win);
		
	    // display test module information
	    Reporting.testModuleInformation();
		
		// initialize automation framework
		CFramework.setupAutomation();

		// engage test timer to start (if enabled)
		Time.engageMethodTimer(true, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		return;
	}
	// --------------------------------------------------------------------------------
	
	
	

	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute after every method event. This will teardown the necessary
	 * components to ensure the automation exits gracefully
	 *
	 * @return null
	 */
	@AfterMethod(enabled=CFramework.isRunAfterMethod, alwaysRun=true)
	public void afterMethod() {

		// engage test timer to stop (if enabled)
		Time.engageMethodTimer(false, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		// de-initialize automation framework
		CFramework.teardownAutomation();
		
		return;
	}
	// --------------------------------------------------------------------------------

	
	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute prior to every class event. This will setup the necessary
	 * components to ensure the automation executes without any issues.
	 *
	 * @return null
	 */
	@BeforeClass(enabled=CFramework.isRunBeforeClass, alwaysRun=true)
	public void beforeClass() throws Exception {

		// increase class test count
		total_classes_count += 1;

		return;
	}
	// --------------------------------------------------------------------------------

	
	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute after every class event. This will teardown the necessary
	 * components to ensure the automation exits gracefully
	 *
	 * @return null
	 */
	@AfterClass(enabled=CFramework.isRunAfterClass, alwaysRun=true)
	public void afterClass() throws Exception {

		return;
	}
	// --------------------------------------------------------------------------------


	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute prior to every group event. This will setup the necessary
	 * components to ensure the test group executes without any issues.
	 *
	 * @return null
	 */
	@BeforeGroups(enabled=CFramework.isRunBeforeGroups, alwaysRun=true)
	public void beforeGroup() throws Exception {

		// increase class test count
		total_groups_count += 1;
		
		return;
	}
	// --------------------------------------------------------------------------------
	
	
	

	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute after every group event. This will teardown the necessary
	 * components to ensure the automation exits gracefully
	 *
	 * @return null
	 */
	@AfterGroups(enabled=CFramework.isRunAfterGroups, alwaysRun=true)
	public void afterGroup() throws Exception {

		return;
	}
	// --------------------------------------------------------------------------------

	
	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute prior to every test event. This will setup the necessary
	 * components to ensure the test group executes without any issues.
	 *
	 * @return null
	 */
	@BeforeTest(enabled=CFramework.isRunBeforeTest, alwaysRun=true)
	public void beforeTest() throws Exception {
		
		// increase class test count
		total_testSections_count += 1;
		
		return;
	}
	// --------------------------------------------------------------------------------
	
	
	

	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute after every test event. This will teardown the necessary
	 * components to ensure the automation exits gracefully
	 *
	 * @return null
	 */
	@AfterTest(enabled=CFramework.isRunAfterTest, alwaysRun=true)
	public void afterTest() throws Exception {
		
		return;
	}
	// --------------------------------------------------------------------------------
	
	
	

	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute prior to every suite event. This will setup the necessary
	 * components to ensure the test group executes without any issues.
	 *
	 * @return null
	 */
	@BeforeSuite(enabled=CFramework.isRunBeforeSuite, alwaysRun=true)
	public void beforeSuite() throws Exception {

        myTestNG.setUseDefaultListeners(false);

		// increase class test count
		total_suites_count += 1;
		
		// set suite-level configuration
		CFramework.setSuiteLevelConfiguration();
		
		// initialize folder structure
		CFramework.initializeProjectFolders();
		
		// initialize log file outputter
		Reporting.initializeStream_logFile();
		
		Reporting.text("\n\n\n======================================================================================================");

	    // display system information
	    Reporting.systemInformation();
	    
		// engage test timer to start (if enabled)
		Time.engageSuiteTimer(true, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		return;
	}
	// --------------------------------------------------------------------------------

	
	
	
	// --------------------------------------------------------------------------------
	/**
	 * This method will always execute after every suite event. This will teardown the necessary
	 * components to ensure the automation exits gracefully
	 *
	 * @return null
	 */
	@AfterSuite(enabled=CFramework.isRunAfterSuite, alwaysRun=true)
	public void afterSuite() throws Exception {


		// engage test timer to stop (if enabled)
		Time.engageSuiteTimer(false, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		Reporting.text("");

		// if suite is enabled, display how many test cases executed within it
		if(CFramework.isRunBeforeSuite && CFramework.isRunAfterSuite) {

			Reporting.text(String.format("Total Test Suites:\t %d", CFramework.total_suites_count));
		}

		// if test is enabled, display how many test cases executed within it
		if(CFramework.isRunBeforeTest && CFramework.isRunAfterTest) {

			Reporting.text(String.format("Total Test CFrameworks:\t %d", CFramework.total_testSections_count));
		}
		
		// if class is enabled, display how many test cases executed within it
		if(CFramework.isRunBeforeClass && CFramework.isRunAfterClass) {

			Reporting.text(String.format("Total Test Classes:\t %d", CFramework.total_classes_count));
		}

		// if groups is enabled, display how many test cases executed within it
		if(CFramework.isRunBeforeGroups && CFramework.isRunAfterGroups) { 

			Reporting.text(String.format("Total Test Groups:\t %d", CFramework.total_groups_count));
		}

		// if method is enabled, display how many test cases executed within it
		if(CFramework.isRunBeforeMethod && CFramework.isRunAfterMethod) { 

			Reporting.text(String.format("Total Test Cases:\t %d", CFramework.total_methods_count));
		}
		
		Reporting.text(String.format("\nTotal Skipped Tests:\t %d", CFramework.total_methods_skipped));
		
		Reporting.text("");
		
		// were there any skipped tests?
		if(CFramework.total_methods_skipped > 0) {
			
			int i = 1;
			
			// display all the skipped tests
			for(String skippedTest : CFramework.skipped_methods) {
				
				Reporting.text(String.format("Skipped Test # %s:\t %s", i, skippedTest));
				
				i = i + 1;
			}
		}

		// close the log file writer stream
		Reporting.closeStream_logFile();

		return;
	}
	// --------------------------------------------------------------------------------

}

// file: CFramework.java
//////////////////////////////////////////////////////////////////////////////////////////////
