//////////////////////////////////////////////////////////////////////////////////////////////
// file: RequestGet.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.request.get;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import org.testng.Assert;

import com.armondsarkisian.crystal.CFramework;
import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.reporting.Reporting;
import com.armondsarkisian.crystal.resource.request.Request;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public class RequestGet extends Request {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static
	public static boolean process(String url, int expected) {

	    // is testing api?
	    if(!Table.incoming_is_apiTest) { CFramework.skipTest(Reporting.constant_testAPISkip); }

		boolean isGet = false;
		
		Response response = null;

		Reporting.statusMessage(Reporting.TestResultState.PENDING, String.format("Attempting to perform a [get] request for \"%s\" with expected status of \"%d\"", url.toString(), expected));

		// attempt to perform a get request
		try {

			// perform the get operation
			response = get(url);

		}
		//catch(ConnectException e) {
		//	
		//	// print out stack trace?
		//	if(Table.is_displayStackTrace) {

		//		// print stack trace
		//		e.printStackTrace();
		//	}
		//	
		//	Reporting.statusMessage(Reporting.TestResultState.FAILED, String.format("Unable to access \"%s\" due to \"%s\"", url, e.getMessage()));

		//	Assert.fail(String.format("FAILED: Unable to establish connection with - \'%s\'", url));
		//}
		catch(Exception e) {
			
			// print out stack trace?
			if(Table.is_displayStackTrace) {

				// print stack trace
				e.printStackTrace();
			}
			
			Reporting.statusMessage(Reporting.TestResultState.FAILED, String.format("Unable to initiate a GET request: \"%s\"", e.getMessage()));

			Assert.fail(String.format("FAILED: Unable to initiate a GET request - \'%s\'", url));
		}

		Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("[get] request for \"%s\" succeeded with expected status of \"%d\"", url.toString(), expected));

		// acquire status code and verify if it matches expected result
		if(response.getStatusCode() == expected) {

			// get operation successful
		    isGet = true;	
		}
		else {

			Reporting.statusMessage(Reporting.TestResultState.FAILED, String.format("%s did not return the expected status of \"%d\". Instead it returned \"%d\"", url, expected, response.getStatusCode()));

			Assert.fail(String.format("%s did not return the expected status of \"%d\". Instead it returned \"%d\"", url, expected, response.getStatusCode()));
		}
		
		return isGet;
	}
}

//file: RequestGet.java
//////////////////////////////////////////////////////////////////////////////////////////////
