package com.armondsarkisian.crystal.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.armondsarkisian.crystal.CFramework;

public class TestCrystal extends CFramework {

    public TestCrystal() { }

	// (Invoke exception as expected result: expectedExceptions=RuntimeException.class)
	@Test (enabled=TableTest.IS_RUN_TEST_NEWS_RIGHT_RAIL, invocationCount=1, priority=1, alwaysRun=true, successPercentage=100, timeOut=1000*60*5, singleThreaded=true, description=TableTest.DESCRIPTION_TEST_NEWS_RIGHT_RAIL)
	public void test_01 () throws InterruptedException {
		
		// is this test case going to run?
		if(!Table.incoming_isRunMethod) {
			
			// disable this test case
		    CFramework.skipTest("Warning: Test case has been disabled!");
		}
		
		// instantiate news object
		Test test = PageFactory.initElements(Browser.getDriver(), Test.class);

		Reporting.display_testCaseProgress(Reporting.TestProgressState.START_TEST, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		// test news right rail functions
		test.test_crystal(); 

		Reporting.display_testCaseProgress(Reporting.TestProgressState.END_TEST, new Object(){}.getClass().getEnclosingMethod().getName().toString());

		return;
	}
}
