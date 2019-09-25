//////////////////////////////////////////////////////////////////////////////////////////////
// file: TestBase.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.test;

import com.armondsarkisian.crystal.CFramework;
import com.armondsarkisian.crystal.resource.browser.Browser;
import com.armondsarkisian.crystal.resource.reporting.Reporting;
import com.armondsarkisian.crystal.resource.request.get.operation.RequestGetOperation;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public class TestBase extends CFramework {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s) -- static
	
	// title(s)

	// page text(s)
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// constructor(s)
	public TestBase() {}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s)
	public static void performSanity(boolean isCheckStatus, int statusCode, boolean isCheckTitle, boolean isExactTitle, String title, boolean isCheckTextPresence, String textPresent) {

		if(isCheckStatus) {
			RequestGetOperation.validateCurrentUrlStatus(Browser.getDriver(), statusCode);
		}
		
		if(isCheckTitle) {

			if(isExactTitle) {

				Reporting.statusMessage(Reporting.TestResultState.PENDING, String.format("Preparing To Inspect Precise Title: [%s]", title));
				Browser.inspectTitleEquals(title);
				Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("Precise Title [%s] Found", title));

			} else {

				Reporting.statusMessage(Reporting.TestResultState.PENDING, String.format("Preparing To Inspect Contained Title: [%s]", title));
				Browser.inspectTitleContains(title);
				Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("Contained Title [%s] Found", title));

			}
		}

		if(isCheckTextPresence) {

			Reporting.statusMessage(Reporting.TestResultState.PENDING, String.format("Preparing To Inspect Page Text [%s]", textPresent));
			Browser.isTextPresent(textPresent);
			Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("Contained Page Text [%s] Found", textPresent));

		}

		return;
	}
}

// file: TestBase.java
//////////////////////////////////////////////////////////////////////////////////////////////