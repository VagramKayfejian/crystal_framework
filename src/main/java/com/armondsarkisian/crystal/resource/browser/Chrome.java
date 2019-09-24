//////////////////////////////////////////////////////////////////////////////////////////////
// file: Chrome.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.browser;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.ArrayList;

import java.net.URL;
import java.net.MalformedURLException;

import java.io.File;

import org.testng.Assert;

import com.armondsarkisian.crystal.CFramework;
import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.browser.Browser;
import com.armondsarkisian.crystal.resource.proxy.BrowserMob;
import com.armondsarkisian.crystal.resource.reporting.Reporting;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public final class Chrome extends Browser {

    //////////////////////////////////////////////////////////////////////////////////////////////
    // attribute(s) -- static

    private static ChromeOptions cap = new ChromeOptions();

    //////////////////////////////////////////////////////////////////////////////////////////////
    // method(s) -- static

    public static ChromeOptions getChromeCapabilities() {

        // is testing ui?
        if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

        return Chrome.cap;
    }

    public static void setChromeOptions(ChromeOptions c) {

        // is testing ui?
        if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

        Chrome.cap = c;

        return;
    }

    public static boolean instantiateWebDriver() {

        // is testing ui?
        if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

        boolean isAction = false;

        if(Table.incoming_isRemote) {

			URL remote_url = null;

			// attempt to construct the URL
			try {
				remote_url = new URL(String.format("http://%s:%s/wd/hub", Table.incoming_remoteIp, Table.incoming_remotePort));

			} catch (MalformedURLException e) {

				Reporting.error(String.format("MalformedURLException: %s", e.getMessage()));

				// is print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				}
			}

			Reporting.text(String.format("Selenium: Running on %s", remote_url.toString()));

			// try to create the remote chrome webdriver
			try {

				// create remote driver
				setDriver(new RemoteWebDriver(remote_url, new ChromeOptions()));
				TimeUnit.SECONDS.sleep(5);

				Reporting.text("Selenium: Opened Remote Chrome WebDriver");

				isAction = true;
			}
			catch(Exception e){

				Reporting.error(String.format("Exception: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				}
			}

        } else {

			try {

				// the driverpath will get stored here depending on the os
				File file;

				StringBuilder driverName = new StringBuilder();

				driverName.append("chromedriver");

				// is it windows?
				if(System.getProperty("os.name").toString().toLowerCase().contains("windows")) {

					driverName.append(".exe");

					// path to the webdriver for windows
					file = new File(String.format("%s/%s", Table.incoming_driverPath_win, driverName.toString()));

				}
				else {

					// path to the webdriver for mac/linux
					file = new File(String.format("%s/%s", Table.incoming_driverPath_other, driverName.toString()));
				}

				System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

				Reporting.text("Chromedriver - Table: Setting Chrome Webdriver Path");

				// create local driver
				setDriver(new ChromeDriver(Chrome.getChromeCapabilities()));
				TimeUnit.SECONDS.sleep(5);

				Reporting.text("Selenium: Opened Local Chrome WebDriver");

				isAction = true;

			}
			catch(Exception e) {

				Reporting.error(String.format("Exception: %s", e.getMessage()));

				// print out stack trace?
				if(Table.is_displayStackTrace) {

					// print stack trace
					e.printStackTrace();
				}

				Assert.fail("Exception: " + e.getMessage().toString());
			}

		}

        return isAction;
    }

    public static void setRemoteOptions() {

        // is testing ui?
        if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

        return;
    }

    public static void setOptions() {

        // is testing ui?
        if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

        // is set any experimental options?
        if(Table.browser_chrome_experimental_isSetExperimental) {

            Map<String, Object> prefs = new HashMap<String, Object>();

            // is set experimental credentials service?
            if(Table.browser_chrome_experimental_isSetCredentialEnabled) {
                prefs.put("credentials_enable_service", false);
                Reporting.text("Chrome - Table [Experimental Options - Credentials Enable Service]: false");
            }

            // is set experimental password manager?
            if(Table.browser_chrome_experimental_isSetPasswordManager) {

                prefs.put("profile.password_manager_enabled", false);
                Reporting.text("Chrome - Table [Experimental Options - Password Manager Enabled]: false");
            }

            // is set experimental automation enabled?
            if(Table.browser_chrome_experimental_isSetAutomation) {

                prefs.put("excludeswitches", Table.browser_chrome_experimental_setAutomation);
                Reporting.text(String.format("Chrome - Table [Experimental Options - Exclude Switches]: %s", Table.browser_chrome_experimental_setAutomation));
            }

            // is set experimental content settings popups?
            if(Table.browser_chrome_experimental_isSetContentSettingsPopups) {

                prefs.put("profile.default_content_settings.popups", Table.browser_chrome_experimental_setContentSettingsPopups);
                Reporting.text(String.format("Chrome - Table [Experimental Options - profile.default_content_settings.popups]: %d", Table.browser_chrome_experimental_setContentSettingsPopups));
            }

            // is set experimental content settings notifications?
            if(Table.browser_chrome_experimental_isSetContentSettingsNotifications) {

                prefs.put("profile.default_content_setting_values.notifications", Table.browser_chrome_experimental_setContentSettingsNotifications);
                Reporting.text(String.format("Chrome - Table [Experimental Options - profile.default_content_setting_values.notifications]: %d", Table.browser_chrome_experimental_setContentSettingsNotifications));
            }

            // is finalize and apply experimental options?
            if(Table.browser_chrome_capability_isApplyExperimental) {

                Chrome.cap.setExperimentalOption("prefs", prefs);
                Reporting.text("Chrome - [Experimental Options] - Finalizing Options");
            }

        }

        // is set any arguments?
        if(Table.browser_chrome_argument_isSetArguments) {

            // set enable automation?
            if(Table.browser_chrome_argument_isEnableAutomation) {

                Chrome.cap.addArguments("enable-automation");
                Reporting.text("Chrome - Table [Argument]: Is Enable Automation");
            }

            // is set no sandbox?
            if(Table.browser_chrome_argument_isNoSandbox) {

                Chrome.cap.addArguments("--no-sandbox");
                Reporting.text("Chrome - Table [Argument]: Is No Sandbox");
            }

            // is disable synch passwords?
            if(Table.browser_chrome_argument_isDisableSyncPasswords) {

                Chrome.cap.addArguments("--disable-sync-passwords");
                Reporting.text("Chrome - Table [Argument]: Is Disable Sync Passwords");
            }

            // is set headless?
            if(Table.incoming_isHeadless) {

                // set headless
                Chrome.cap.setHeadless(true);
                Reporting.text("Chrome - Table [Set Headless]: True");

                Chrome.cap.addArguments("--headless");
                Reporting.text("Chrome - Table [Argument]: Headless");
            }
            else {

                Chrome.cap.setHeadless(false);
                Reporting.text("Chrome - Table [Set Headless]: False");
            }

            // set window size?
            if(Table.browser_chrome_argument_windowSize) {

                Chrome.cap.addArguments("--window-size=1920,1080");
                Reporting.text("Chrome - Table [Argument]: Window Size Set - 1920, 1080");
            }

            // is set window maximized?
            if(Table.browser_chrome_argument_isMaximized) {

                Chrome.cap.addArguments("start-maximized");
                Reporting.text("Chrome - Table [Arguments - Start Maximized]: True");
            }
            else {

                Reporting.text("Chrome - Table [Arguments - Start Maximized]: False");
            }

            // is set ignore certificate errors?
            if(Table.browser_chrome_argument_isIgnoreCertificateErrors) {

                Chrome.cap.addArguments("--ignore-certificate-errors");
                Reporting.text("Chrome - Table [Argument]: Ignore Certificate Errors");
            }

            // is disable extensions?
            if(Table.browser_chrome_argument_isDisableExtensions) {

                Chrome.cap.addArguments("--disable-extensions");
                Reporting.text("Chrome - Table [Argument]: Disable Extensions");
            }

            // is disable info bars?
            if(Table.browser_chrome_argument_isDisableInfoBars) {

                Chrome.cap.addArguments("--disable-infobars");
                Reporting.text("Chrome - Table [Argument]: Disable Infobars");
            }

            // is disable GPU?
            if(Table.browser_chrome_argument_isDisableGPU) {

                Chrome.cap.addArguments("--disable-gpu");
                Reporting.text("Chrome - Table [Argument]: Set Disable GPU");
            }

            // is disable dev shm usage?
            if(Table.browser_chrome_argument_isDisableDevShmUsage) {

                Chrome.cap.addArguments("--disable-dev-shm-usage");
                Reporting.text("Chrome - Table [Argument]: Disable DEV SHM Usage");
            }

            // set DNS prefetch disable option?
            if(Table.browser_chrome_argument_isDNSPrefetchDisable) {

                Chrome.cap.addArguments("--dns-prefetch-disable");
                Reporting.text("Chrome - Table [Argument]: Set DNS PrefetchDisable ");
            }

            // is always allow and authorize plugins?
            if(Table.browser_chrome_capability_isAllowPlugins) {

                Chrome.cap.addArguments("--always-authorize-plugins");

                Reporting.text("Chrome - Table [Capability]: Always Authorize Plugins");
            }

            // is set user directory?
            if(Table.browser_chrome_argument_isSetUserDataDir) {

                Chrome.cap.addArguments(Table.browser_chrome_argument_setUserDataDir);

                Reporting.text(String.format("Chrome - Table [Argument - User Data Dir]: %s", Table.browser_chrome_argument_setUserDataDir));
            }

            // is set test type?
            if(Table.browser_chrome_argument_isSetTestType) {

                // set test-type
                Chrome.cap.addArguments("test-type");
            }

            // is disable popups?
            if(Table.browser_chrome_argument_isDisablePopups) {

                // disable pop-up blocking
                Chrome.cap.addArguments("disable-popup-blocking");
            }

            // is set cache options?
            if(Table.browser_chrome_argument_isSetCache) {

                Chrome.cap.addArguments("--aggressive-cache-discard");
                Chrome.cap.addArguments("--disable-cache");
                Chrome.cap.addArguments("--disable-application-cache");
                Chrome.cap.addArguments("--disable-offline-load-stale-cache");
                Chrome.cap.addArguments("--disk-cache-size=0");

                Reporting.text("Chrome - Table [Capability]: Set Cache Options");
            }

            // is set incognito mode?
            if(Table.browser_chrome_argument_isIncognito) {

                // set it incognito mode
                Chrome.cap.addArguments("--incognito");

                Reporting.text("Chrome - Table [Capability]: Set Incognito");
            }

            // is proxy not enabled?
            if(!Table.incoming_is_proxy) {

                Chrome.cap.addArguments("--no-proxy-server");
                Chrome.cap.setProxy(null);

                Reporting.text("Chrome - Table [Capability]: Proxy Is Disabled");
            }
            else {

                Reporting.text("Chrome - Table [Capability]: Proxy Is Enabled");
            }

            // is disable side navigation?
            if(Table.browser_chrome_argument_isDisableSideNavigation) {

                Chrome.cap.addArguments("--disable-browser-side-navigation");

                Reporting.text("Chrome - Table [Capability]: Disable Browser Side Navigation");
            }

            // is silence chrome?
            if(Table.browser_chrome_argument_isSilence) {

                Chrome.cap.addArguments("--silent");
                System.setProperty("webdriver.chrome.silentOutput", "true");

                Reporting.text("Chrome - Table [Capability]: Chrome Silenced");
            }

            Reporting.text("Chrome - [Arguments] - Finalizing Arguments");
        }

        // is set any capabilities?
        if(Table.browser_chrome_capability_isSetCapabilities) {

            // proceed with this step only if the proxy has been enabled
            if(Table.incoming_is_proxy) {

                // is set capability chrome selenium proxy?
                if(Table.browser_chrome_capability_isSetProxy) {

                    // apply the selenium proxy to chrome capabilities
                    Chrome.cap.setCapability(CapabilityType.PROXY, BrowserMob.seleniumProxy);
                    Reporting.text("Chrome - Table [Capability - Proxy]: Selenium Proxy");
                }
            }

            // is set capability for proxy server address?
            if(Table.browser_chrome_capability_isSetProxyServerAddress && Table.incoming_is_proxy) {

                ArrayList<String> switches = new ArrayList<String>();
                switches.add("--proxy-server=localhost:8080");
                Chrome.cap.setCapability("chrome.switches", switches);

                Reporting.text("Chrome - Table [Capability - Proxy Web Server Address]: localhost:8080");
            }

            // is set capability version to latest?
            if(Table.browser_chrome_capability_isSetLatestVersion) {

                Chrome.cap.setCapability("version", "latest");
                Reporting.text("Chrome - Table [Capability - Version]: Latest");
            }

            // is set capability browser name?
            if(Table.browser_chrome_capability_isSetBrowserName) {

                Chrome.cap.setCapability("browserName", "chrome");
                Reporting.text("Chrome - Table [Capability - Browser Name]: Chrome");
            }

            // is set capability accept ssl certificates?
            if(Table.browser_chrome_capability_isAcceptSSLCerts) {

                Chrome.cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                Reporting.text("Chrome - Table [Capability - Accept SSL Certs]: True");
            }

            // enable this capability only if running a web proxy server
            if(Table.incoming_is_proxy) {

                // is set capability wait for quiescence?
                if(Table.browser_chrome_capability_isWaitForQuiescence) {

                    Chrome.cap.setCapability("waitForQuiescence", true);
                    Reporting.text("Chrome - Table [Capability - Wait For Quiescence]: True");
                }
            }

            // is set logging preferences?
            if(Table.browser_chrome_capability_isSetLoggingPreferences) {

                LoggingPreferences logPrefs = new LoggingPreferences();

                logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
                cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);

                Reporting.text("Chrome - Table [Capability]: Logging Preferences = Level.ALL");
            }

            // specify to chrome to strategize page loads
            if(Table.browser_chrome_capability_isStategizePageLoad) {

                Chrome.cap.setPageLoadStrategy(PageLoadStrategy.NONE);

                Reporting.text("Chrome - Table [Capability]: Is Stategize Page Loads - PageLoadStategy.NONE");
            }

            // is finalize and apply capabilities?
            if(Table.browser_chrome_capability_isApplyCapabilities) {

                Chrome.cap.setCapability(ChromeOptions.CAPABILITY, cap);
                Reporting.text("Chrome - Table [Capability]: Finalize & Set Capabilities");

                Reporting.text("Chrome - [Capabilities] - Finalizing Capabilities");
            }
        }

        // all chrome options/settings/capabilities are set at this point
        return;
    }
}

// file: Chrome.java
//////////////////////////////////////////////////////////////////////////////////////////////
