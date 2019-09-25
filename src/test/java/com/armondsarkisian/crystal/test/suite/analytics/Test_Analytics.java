//////////////////////////////////////////////////////////////////////////////////////////////
// file: Test_Analytics.java

//////////////////////////////////////////////////////////////////////////////////////////////
//package
package com.armondsarkisian.crystal.test.suite.analytics;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import org.openqa.selenium.support.PageFactory;

import org.testng.annotations.Test;

import com.armondsarkisian.crystal.CFramework;
import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.browser.Browser;
import com.armondsarkisian.crystal.resource.reporting.Reporting;
import com.armondsarkisian.crystal.test.TestBase;
import com.armondsarkisian.crystal.test.table.TestTable;

//////////////////////////////////////////////////////////////////////////////////////////////
//class
public class Test_Analytics extends TestBase {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// constructor(s)
    public Test_Analytics() { }

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s)	-- static

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- individual test case(s)

	// (Invoke exception as expected result: expectedExceptions=RuntimeException.class)
	@Test (enabled=TestTable.IS_RUN_TEST_RIGHT_RAIL, invocationCount=1, priority=1, alwaysRun=true, successPercentage=100, timeOut=1000*60*5, singleThreaded=true, description=TestTable.DESCRIPTION_TEST_RIGHT_RAIL)
	public void test_analytics () throws InterruptedException {
		
		// is this test case going to run?
		if(!Table.incoming_isRunMethod) {
			
			// disable this test case
		    CFramework.skipTest("Warning: Test case has been disabled!");
		}
		
		// instantiate news object
		Analytics analytics = PageFactory.initElements(Browser.getDriver(), Analytics.class);

		Reporting.display_testCaseProgress(Reporting.TestProgressState.START_TEST, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		// test analytics functionality
		analytics.verify_analytics(); 

		Reporting.display_testCaseProgress(Reporting.TestProgressState.END_TEST, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		return;
	}
}

//file: Test_Analytics.java
//////////////////////////////////////////////////////////////////////////////////////////////