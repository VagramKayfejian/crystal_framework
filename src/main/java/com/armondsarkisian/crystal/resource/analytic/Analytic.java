//////////////////////////////////////////////////////////////////////////////////////////////
// file: Analytic.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.analytic;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import java.util.List;

import org.testng.Assert;

import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.proxy.BrowserMob;
import com.armondsarkisian.crystal.resource.reporting.Reporting;

import net.lightbody.bmp.core.har.HarEntry;

/*

//How to check analytics:

// validate analytics
String callUrl = "CHECK_FOR_PORTION_OF_URL";
System.out.println(String.format("Validating Rule:\t[Call Url] = [%s]", callUrl));

String stringKeyMatch = "CHECK_FOR_KEY";
System.out.println(String.format("Validating Rule:\t[String Key Match] = [%s]", stringKeyMatch));

String contains = "CHECK_FOR_VALUE";
System.out.println(String.format("Validating Rule:\t[Contains] = [%s]", contains));

// perform validation
if(Analytic.is_detectedResponse(callUrl, stringKeyMatch, contains) == false) { }

 */

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public final class Analytic {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static

	public static boolean is_detectedResponse(String call, String key, String expected) {

		// is web proxy enabled?
		if(!Table.incoming_is_proxy) { return true; } // skip

		// retrieve HAR entries
		List<HarEntry> entries = BrowserMob.proxy.getHar().getLog().getEntries();
		
		// form the key/valuie pair string which we will search in the url
		String matchText = String.format("%s=%s", key, expected);
		
		// validate analytic response
		boolean isPass = false;
		String url = "";

		// go through all the HAR entries
		for(HarEntry entry :entries){

			// acquire the current url
			url = entry.getRequest().getUrl();

			if(url.contains(call)) {

				Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("Inspecting URL: [%s]", url));

				if(url.contains(matchText.toString())) {

					// value matched
					isPass = true;

					// if match found, exit the loop
					break;
				}

			}
		}

		if (isPass == false) {

			System.err.println(String.format("Validating Failed:\tNo match for [%s=%s] for call %s", key, expected, call));
			Assert.fail(String.format("Validating Failed:\tNo match for [%s=%s] for call %s", key, expected, call));

		}
		
		return isPass;
	}
}

// file: Analytic.java
//////////////////////////////////////////////////////////////////////////////////////////////
