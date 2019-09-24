//////////////////////////////////////////////////////////////////////////////////////////////
// file: BrowserMob.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.proxy;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

import java.net.Inet4Address;

import org.openqa.selenium.Proxy;

import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.reporting.Reporting;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public final class BrowserMob {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s) -- static

	public static BrowserMobProxy proxy = null;

	public static Proxy seleniumProxy = null;

	public static Har har = null;

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static

	public static void createProxy() {
		
        // attempt to start the web proxy server
        try {

            Reporting.text("Proxy: Creating Web Proxy - PENDING");

            // create an instance of a web proxy server
            BrowserMob.proxy = new BrowserMobProxyServer();

            Reporting.text("Proxy: Created Web Proxy - COMPLETED");
        }
        catch(Exception e) {
            
            Reporting.error(e.getMessage());

            // print out stack trace?
            if(Table.is_displayStackTrace) {

                // print stack trace
                e.printStackTrace();
            }
        }
		
		return;
	}

	public static void startProxy() {
		
        // is start the web proxy server?
        if(Table.browser_proxy_isStartProxy) { 

            Reporting.text(String.format("Proxy: Starting Web Proxy"));

            // engage the proxy server
            BrowserMob.proxy.start(0);

            Reporting.text(String.format("Proxy: Started Web Proxy on port: %d", BrowserMob.proxy.getPort()));
        }
		
		return;
	} 
	
	public static void stopProxy() {
		
        // attempt to stop the web proxy server
        try {
            Reporting.text("Proxy: Shutting Down Web Proxy - PENDING ");

            BrowserMob.proxy.stop();

            Reporting.text("Proxy: Shutdown Web Proxy Successfully - COMPLETED");
        }
        catch(Exception e) {
                
            Reporting.error(e.getMessage());

            // print out stack trace?
            if(Table.is_displayStackTrace) {

                // print stack trace
                e.printStackTrace();
            }
        }
		
		return;
	}
	public static void setProxyOptions() {
		
        // is set web proxy server options?
        if(Table.browser_proxy_isSetProxyOptions) {

            // trust all servers
            BrowserMob.proxy.setTrustAllServers(true);

            Reporting.text("Proxy - Table [Trust All Servers]: True");
        }
		
		return;
	}
	
	public static void createSeleniumProxy() {

        if(Table.browser_proxy_isCreateSeleniumProxy) {

            // set selenium proxy http ip/ports
            try {

                Reporting.text("Selenium Proxy: Creating Selenium Proxy Bindings - PENDING");	

                // get the selenium web proxy object
                BrowserMob.seleniumProxy = ClientUtil.createSeleniumProxy(BrowserMob.proxy);

                Reporting.text("Selenium Proxy: Created Selenium Proxy Bindings - COMPLETED");	

                String hostIp = Inet4Address.getLocalHost().getHostAddress();

                seleniumProxy.setHttpProxy(hostIp + ":" + proxy.getPort());
                seleniumProxy.setSslProxy(hostIp + ":" + proxy.getPort());

            }
            catch(Exception e) {
                    
                Reporting.error(e.getMessage());

                // print out stack trace?
                if(Table.is_displayStackTrace) {

                    // print stack trace
                    e.printStackTrace();
                }
            }
        }

		return;
	}
	
	public static void setHarCaptureTypes(String whatToCapture) {
		
        // is set har capture types?
        if(Table.browser_proxy_isSetHarCaptureTypes) {
            
            // enable HAR capture types
            if(whatToCapture.toLowerCase().trim().equals("All")) {
        
                BrowserMob.proxy.enableHarCaptureTypes(CaptureType.getAllContentCaptureTypes());

                Reporting.text("HAR Logger: Specified Capture Types: All Content");
            }
            else {

                BrowserMob.proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

                Reporting.text("HAR Logger: Specified Capture Types: Request Content, Response Content");
            }

            Reporting.text("HAR Logger: Specify Capture Types Option Set Successfully");
        }

		return;
	}

	public static void createHar() {
		
        // is create new har?
        if(Table.browser_proxy_isCreateNewHar) {

            Reporting.text("HAR Logger: Creating HAR Logger Stream - PENDING");

            // create a new HAR
            BrowserMob.proxy.newHar();

            Reporting.text("HAR Logger: Created HAR Logger Stream - COMPLETED");
        }
		
		return;
	}
}

// file: BrowserMob.java
//////////////////////////////////////////////////////////////////////////////////////////////
