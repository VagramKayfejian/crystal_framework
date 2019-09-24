//////////////////////////////////////////////////////////////////////////////////////////////
// file: RequestGetOperation.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.request.get.operation;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import org.openqa.selenium.WebDriver;

import com.armondsarkisian.crystal.CFramework;
import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.reporting.Reporting;
import com.armondsarkisian.crystal.resource.request.get.RequestGet;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public final class RequestGetOperation extends RequestGet {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static
	public static void validateCurrentUrlStatus(WebDriver driver, int expectedStatus) {
	
	    // is testing api?
	    if(!Table.incoming_is_apiTest) { CFramework.skipTest(Reporting.constant_testAPISkip); }

		// validate the current url
		String currentUrl = driver.getCurrentUrl();
		
		// call getRequest to perform the get operation
		if (RequestGet.process(currentUrl, expectedStatus)) {

			Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("%s returned the expected status of \"%d\"", currentUrl, expectedStatus));
		}
		
		return;
	}	

	public static void validateSpecifiedUrlStatus(String url, int expectedStatus) {
	
	    // is testing api?
	    if(!Table.incoming_is_apiTest) { CFramework.skipTest(Reporting.constant_testAPISkip); }
	    
	    if(!url.contains("http")) {
	    	
			Reporting.statusMessage(Reporting.TestResultState.SKIPPED, String.format("Unable to acquire status of url: \"%s\"", url));

			return;
	    }
	    
	    // do not process if it is an ftp url
	    if(url.toLowerCase().contains("ftp")) {
	    	
			Reporting.statusMessage(Reporting.TestResultState.SKIPPED, String.format("Unable to process ftp url: \"%s\"", url));

			return;
	    }
	    else {

			// call getRequest to perform the get operation
			if (RequestGet.process(url, expectedStatus)) {

				Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("%s returned the expected status of \"%d\"", url, expectedStatus));
			}
	    }
		
		return;
	}	
}

// file: RequestGetOperation.java
//////////////////////////////////////////////////////////////////////////////////////////////
