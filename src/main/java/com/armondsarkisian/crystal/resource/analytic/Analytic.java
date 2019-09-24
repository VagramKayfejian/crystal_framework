//////////////////////////////////////////////////////////////////////////////////////////////
// file: Analytic.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.analytic;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import java.util.List;

import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.proxy.BrowserMob;
import com.armondsarkisian.crystal.resource.reporting.Reporting;

import net.lightbody.bmp.core.har.HarEntry;

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

		// go through all the HAR entries
		for(HarEntry entry :entries){

			// acquire the current url
			String url = entry.getRequest().getUrl();

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

		return isPass;
	}
}

// file: Analytic.java
//////////////////////////////////////////////////////////////////////////////////////////////
