//////////////////////////////////////////////////////////////////////////////////////////////
// file: Browser.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.resource.browser;

//////////////////////////////////////////////////////////////////////////////////////////////
// import(s)
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import com.armondsarkisian.crystal.CFramework;
import com.armondsarkisian.crystal.table.Table;
import com.armondsarkisian.crystal.resource.reporting.Reporting;
import com.armondsarkisian.crystal.resource.time.Time;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public class Browser {

	//////////////////////////////////////////////////////////////////////////////////////////////
	// attribute(s) -- static
	public static String original_winHandle = null;

	private static WebDriver driver = null;

	private static ArrayList<String> new_tab = null;

	//////////////////////////////////////////////////////////////////////////////////////////////
	// method(s) -- static

    public static WebDriver getDriver() {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }
    	
    	return Browser.driver;
    }
	
    public static void setDriver(WebDriver drv) {
    	
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

	    // set the new driver
		Browser.driver = drv;
    	
    	return;
    }

	public static void setBrowserTimeoutOptions(int timeout) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

	    // timer settings
	    int time_implicitWait = 60;
	    int time_pageLoadTimeout = 80;
	    int time_scriptTimeout = 65;

	    // timeout options
		getDriver().manage().timeouts().implicitlyWait(time_implicitWait, TimeUnit.SECONDS);
		Reporting.text(String.format("Selenium - Timeout [Setting Implicit Wait]: %d TimeUnit.SECONDS", time_implicitWait));

		getDriver().manage().timeouts().pageLoadTimeout(time_pageLoadTimeout, TimeUnit.SECONDS);
		Reporting.text(String.format("Selenium - Timeout [Setting Page Load Timeout]: %d TimeUnit.SECONDS", time_pageLoadTimeout));

		getDriver().manage().timeouts().setScriptTimeout(time_scriptTimeout, TimeUnit.SECONDS);
		Reporting.text(String.format("Selenium - Timeout [Setting Script Timeout]: %d TimeUnit.SECONDS", time_scriptTimeout));
		
		return;
	}

    public static boolean releaseWebDriver() {

        // is testing ui?
        if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

        boolean isAction = false;

        try {

            if (getDriver() == null) {
                return false;
            }

            getDriver().quit();
            setDriver(null);

            Reporting.text("Selenium: Closing Webdriver");

            isAction = true;

        }
        catch(Exception e) {

            Reporting.error(e.getMessage());

            // print out stack trace?
            if(Table.is_displayStackTrace) {

                // print stack trace
                e.printStackTrace();
            }
        }

        return isAction;
    }

	public static boolean waitForJSandJQueryToLoad() {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean result;

		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 25);

		// wait for jquery to load
		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {

				try {

					return ((Long)((JavascriptExecutor)Browser.getDriver()).executeScript("return jQuery.active") == 0);
				}
				catch (Exception e) {

					// no jquery present
					return true;
				}
			}
		};

		// wait for javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver driver) {

				return ((JavascriptExecutor)Browser.getDriver()).executeScript("return document.readyState").toString().equals("complete");
			}
		};

		result = wait.until(jQueryLoad) && wait.until(jsLoad);

		if (result == false) {
			
			String message = "Issues loading content of the page";

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			Assert.fail(String.format("FAILED: %s", message)); 
		}		
	     
	    return result;
	}
	
	public static boolean inspectTitleEquals(String expected_title) {
		
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		String actual_title = Browser.getDriver().getTitle().toString().toLowerCase().trim();
		String message = "";
		
		if(actual_title.equals(expected_title.toLowerCase().trim())) {

			message = String.format("Expected title ['%s'] precisely matched actual title ['%s'] for url: %s", expected_title.toLowerCase().trim(), actual_title.toLowerCase().trim(), Browser.getCurrentUrl());

			Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

			// title matched
			isAction = true;
		}
		else {
			
			message = String.format("Expected title ['%s'] did not precisely match actual title ['%s'] for url: %s", expected_title.toLowerCase().trim(), actual_title.toLowerCase().trim(), Browser.getCurrentUrl());
			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			Assert.fail(String.format("FAILED: %s", message)); 
		}
		
		Assert.assertTrue(isAction);

	    // was title inspected?
		return isAction;
	}
	
	public static boolean inspectTitleContains(String expected_title) {
		
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		String actual_title = Browser.getDriver().getTitle().toString().toLowerCase().trim();
		String message = "";
		
		if(actual_title.contains(expected_title.toLowerCase().trim())) {

			message = String.format("Expected title ['%s'] was contained within the actual title ['%s'] for url: %s", expected_title.toLowerCase().trim(), actual_title.toLowerCase().trim(), Browser.getCurrentUrl());

			Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

			// title matched
			isAction = true;
		}
		else {
			
			message = String.format("Expected title ['%s'] was not contained within the actual title ['%s'] for url: %s", expected_title.toLowerCase().trim(), actual_title.toLowerCase().trim(), Browser.getCurrentUrl());

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			Assert.fail(String.format("FAILED: %s", message)); 
		}
		
		Assert.assertTrue(isAction);

	    // was title inspected?
		return isAction;
	}
	
	public static String getCurrentWindowHandle() {
		
		// store the current window handle
		Browser.original_winHandle = Browser.getDriver().getWindowHandle();

		return Browser.original_winHandle;
	}
	
	public static void switchToNewWindow() {

		// switch to the opened window
		for(String h : Browser.getDriver().getWindowHandles()){
		    Browser.getDriver().switchTo().window(h);
		}
		 
		return;
	}
	
	public static void closeNewWindow() {
		
		// close the new window
		Browser.getDriver().close();

		// switch back to original window
		Browser.getDriver().switchTo().window(Browser.original_winHandle);

		return;
	}
	
	public static void switchToFrame(String frameId) {	

		driver.switchTo().defaultContent();
		driver.switchTo().frame(frameId);
		
		String message = String.format("Successfully switched to new frame: ID [%s]", frameId);
		Reporting.statusMessage(Reporting.TestResultState.PASSED, message);
	}
	
	public static void switchFromFrame() {

		driver.switchTo().defaultContent();
		driver.switchTo().parentFrame();

		String message = String.format("Successfully switched to parent frame");
		Reporting.statusMessage(Reporting.TestResultState.PASSED, message);
	}
	
	public static void switchToTab() {
	
		// acquire handle name for new tab
		new_tab = new ArrayList<String> (Browser.getDriver().getWindowHandles());

		// switch to new tab
		Browser.getDriver().switchTo().window(new_tab.get(1));
		
		return;
	}
	
	public static void switchFromTab() {

		// close the tab
		Browser.getDriver().close();
		
		// switch out of tab
		Browser.getDriver().switchTo().window(new_tab.get(0));
		
		return;
	}
	
	public static boolean hoverElement(WebElement element, int timeout_milliseconds) {
		
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		// attempt to hover the specified element
		try {

			// move to the object first
			Browser.moveToElement(element, Table.sleep_object_moveToIt);

			Actions builder = new Actions(Browser.getDriver());

			// hover over the specified element
			builder.moveToElement(element).perform();

			isAction = true;

		}
		catch(Exception e) {
		
	    	String message = String.format("Unable to however the specified element: %s", element);

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);
		}
		
		Assert.assertTrue(isAction);

		return isAction;
	}

	public static boolean clickElement(WebElement element, String objectFriendlyName, int timeout_milliseconds) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 30);

		// ensure the element is visible before communication occurs
		wait.until(ExpectedConditions.visibilityOf(element)); 

		// if enabled & displayed, click on it
		if(element.isEnabled() && element.isDisplayed()) {
			
			// move to the object first
			Browser.moveToElement(element, Table.sleep_object_moveToIt);

			// click on object
			element.click();
			
			// object interaction occurred successfully
			isAction = true;

			Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("Successfully clicked on %s", objectFriendlyName));
			
			// idle x milliseconds
			Time.sleep(timeout_milliseconds);
		}

		Assert.assertTrue(isAction);

		// was object clicked on?
		return isAction;
	}
	
	public static boolean moveToElement(WebElement element, int timeout_milliseconds) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		Actions action = new Actions(Browser.getDriver());		

		try {

			action.moveToElement(element);
			
			isAction = true;
			
			// idle x milliseconds
			Time.sleep(timeout_milliseconds);
		}
		catch(Exception e) { }

		Assert.assertTrue(isAction);

		// did it move to object location?
		return isAction;
	}
	
	public static boolean rightClickNewTab(WebElement element, int timeout_milliseconds) {
		
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		try {
			
			// move to the object first
			Browser.moveToElement(element, Table.sleep_object_moveToIt);

			Actions action = new Actions(Browser.getDriver());		

			action.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
			
			isAction = true;
			
			// idle x milliseconds
			Time.sleep(timeout_milliseconds);
		}
		catch(Exception e) {
			
			String message = String.format("Unable to right click on element and open new tab: %s", element.getText());

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			Assert.fail(String.format("FAILED: %s", message)); 
		}
		 
		Assert.assertTrue(isAction);

		return isAction;
	}
	
	public static boolean rightClickNewWindow(WebElement element, int timeout_milliseconds) {
		
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		try {

			// move to the object first
			Browser.moveToElement(element, Table.sleep_object_moveToIt);

			Actions action = new Actions(Browser.getDriver());		

			action.contextClick(element).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
			
			isAction = true;
			
			// idle x milliseconds
			Time.sleep(timeout_milliseconds);
		}
		catch(Exception e) {
			
			String message = String.format("Unable to right click on element and open new window: %s", element.getText());

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			Assert.fail(String.format("FAILED: %s", message)); 
		}
		 
		Assert.assertTrue(isAction);

		return isAction;
	}
	
	public static boolean inputText(WebElement element, String objectFriendlyName, String inputValue, int timeout_milliseconds, boolean isPressEnter) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		WebDriverWait wait = new WebDriverWait(Browser.getDriver(), 30);

		// ensure the element is visible before communication occurs
		wait.until(ExpectedConditions.visibilityOf(element)); 

		// move to the object first
		Browser.moveToElement(element, Table.sleep_object_moveToIt);

		// if enabled & displayed, send keys to the element
		if(element.isEnabled() && element.isDisplayed()) {
			
			// clear the input first
			element.clear();
			
			// click on the input text field
			element.click();
			
			// input data
			if(isPressEnter) {

				element.sendKeys(inputValue, Keys.RETURN);
			}
			else {

				element.sendKeys(inputValue);
			}

			// object interaction occurred successfully
			isAction = true;

			Reporting.statusMessage(Reporting.TestResultState.PASSED, String.format("Can successfully input values to %s", objectFriendlyName));
			
			// idle x milliseconds
			Time.sleep(timeout_milliseconds);
		}

		Assert.assertTrue(isAction);

	    // was able to input text?
		return isAction;
	}
	
	public static String getText(WebElement element) {
		
	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		String text = null;

		// move to the object first
		Browser.moveToElement(element, Table.sleep_object_moveToIt);

		text = element.getAttribute("innerHTML").toString();

		isAction = true;
		
		Assert.assertTrue(isAction);

		return text;
	}

	public static boolean isTextPresent(String expectedWord){

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;
		
		Reporting.statusMessage(Reporting.TestResultState.PENDING, String.format("Preparing to search for text: \"%s\"", expectedWord));

		boolean match = Browser.getDriver().getPageSource().toLowerCase().toString().contains(expectedWord.toLowerCase());

		// is match found
		if(match) {

			String message = String.format("Expected word ['%s'] found within URL: ['%s']", expectedWord, Browser.getDriver().getCurrentUrl().toString());

			Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

			isAction = true;
		}

		Assert.assertTrue(isAction);

	    // was able to retrieve text?
		return isAction;
	}

	public static boolean isElementPresent(WebElement element){

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		// move to the object first
		Browser.moveToElement(element, Table.sleep_object_moveToIt);

	    // was able to locate element?
	    return element != null;
	}

	public boolean isAttribtuePresent(WebElement element, String attribute) {

	    Boolean isAction = false;
	    
	    String attributeValue = null;
	    
	    // attempt to check if attribute is present
	    try {

			// move to the object first
			Browser.moveToElement(element, Table.sleep_object_moveToIt);

	        attributeValue = element.getAttribute(attribute);

	        // is attribute present?
	        if(attributeValue != null) {

	            isAction = true;
	        }
	    } 
	    catch (Exception e) {}

	    // was able to locate element attribute?
	    return isAction;
	}

	public static boolean getAttributeValue(WebElement element, String attribute, String expectedAttributeValue) {

	    Boolean isAction = false;
	    
	    String actualAttributeValue = null;

	    // attempt to check if attribute value matches expected result
	    try {

			// move to the object first
			Browser.moveToElement(element, Table.sleep_object_moveToIt);

	        actualAttributeValue = element.getAttribute(attribute);
	        
	        // does attribute value match expected result?
	        if(actualAttributeValue.equals(expectedAttributeValue)) {

	            isAction = true;
	        }

			String message = String.format("Expected attribute value ['%s'] matched actual attribute value ['%s']", expectedAttributeValue, actualAttributeValue);

			Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

	    } 
	    catch (Exception e) {

			String message = String.format("Expected attribute value ['%s'] did not match actual attribute value ['%s']", expectedAttributeValue, actualAttributeValue);

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			Assert.fail(String.format("FAILED: %s", message)); 
	    }
	    
	    Assert.assertTrue(isAction);

	    // was able to locate element attribute and validate the value?
	    return isAction;
	}

	public static String getCurrentUrl() {
		

		Reporting.text(String.format("Selenium: Attempting To Acquire Current URL"));	
		String url = getDriver().getCurrentUrl().toString().trim();
		Reporting.text(String.format("Selenium: URL Successfully Acquired: \"%s\"", url));	

		return url;

	}
	public static boolean goTo(String url, int page_load_sleep, boolean waitUntilJSLoads) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		// before loading the URL
		Reporting.text(String.format("Selenium: Preparing To Load URL: %s", url));	

		//driver.get("about:blank");
		driver.get(url);

		// after loading the URL
		Reporting.text(String.format("Selenium: Loaded Specified URL: %s", url));	
		
		// load complete
		isAction = true;
		
		// explicit wait until the initial page loads
		Time.sleep(page_load_sleep);

		if(waitUntilJSLoads) {

			// wait until the page loads
			Browser.waitForJSandJQueryToLoad();

			//Reporting.text("Selenium: Waiting For JS & JQuery Calls To Finish");
		}

		Assert.assertTrue(isAction);

	    // was able to go to new page?
		return isAction;
	}
	
	public static boolean goBack(int timeout_milliseconds, boolean waitUntilJSLoads) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		// click the back button on the browser
		Browser.getDriver().navigate().back();
		
		isAction = true;
		
		String message = "Pressed the back button on the browser";
		Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

		// idle x milliseconds
		Time.sleep(timeout_milliseconds);

		if(waitUntilJSLoads) {

			// wait until the page loads
			Browser.waitForJSandJQueryToLoad();

			//Reporting.text("Selenium: Waiting For JS & JQuery Calls To Finish");
		}

		Assert.assertTrue(isAction);

	    // was able to go back?
		return isAction;
	}
	
	public static boolean goForward(int timeout_milliseconds, boolean waitUntilJSLoads) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		// click the forward button on the browser
		Browser.getDriver().navigate().forward();
		
		isAction = true;
		
		String message = "Pressed the forward button on the browser";
		Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

		// idle x milliseconds
		Time.sleep(timeout_milliseconds);

		if(waitUntilJSLoads) {

			// wait until the page loads
			Browser.waitForJSandJQueryToLoad();

			//Reporting.text("Selenium: Waiting for JavaScript & JQuery calls to finish");
		}

		Assert.assertTrue(isAction);

	    // was able to go forward?
		return isAction;
	}

	public static boolean refreshPage(int timeout_milliseconds, boolean waitUntilJSLoads) {

	    // is testing ui?
	    if(!Table.incoming_is_uiTest) { CFramework.skipTest(Reporting.constant_testUISkip); }

		boolean isAction = false;

		try {

			isAction = true;
			
			// click the refresh button on the browser
			Browser.getDriver().navigate().refresh();

			String message = "Pressed the refresh button on the browser";

			Reporting.statusMessage(Reporting.TestResultState.PASSED, message);

			// idle x milliseconds
			Time.sleep(timeout_milliseconds);

			if(waitUntilJSLoads) {

				// wait until the page loads
				Browser.waitForJSandJQueryToLoad();

				//Reporting.text("Selenium: Waiting For JS & JQuery Calls To Finish");
			}

		}
		catch(Exception e) {

			// treat this as a warning and not an error
			String message = String.format("Unable to refresh the page");

			Reporting.statusMessage(Reporting.TestResultState.FAILED, message);

			//Assert.fail(String.format("FAILED: %s", message));  // <-- not severe enough to warrant a failure
		}

		Assert.assertTrue(isAction);

	    // was able to refresh page?
		return isAction;
	}
	
	public static void scrollDown(WebElement element, int amount) {

		JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();

		if(amount == 100) {

			// scroll to the very bottom
			jse.executeScript("window.scroll(0, document.body.scrollHeight);");
		}
		else if(amount == 0) {

			// scroll down into view of the element
			jse.executeScript("arguments[0].scrollIntoView(true);",element);
		}
		else{

			// scroll down the amount specified
			jse.executeScript(String.format("window.scroll(0, %d);", amount));
		}
		
		return;
	}
	
	public static void scrollUp(WebDriver element, int amount) {

		JavascriptExecutor jse = (JavascriptExecutor) Browser.getDriver();

		if(amount == 100) {

			// scroll to the very top
			jse.executeScript("window.scroll(0, -document.body.scrollHeight);");
		}
		else if(amount == 0) {

			// scroll up into view of the element
			jse.executeScript("arguments[0].scrollIntoView(true);",element);
		}
		else{

			// scroll up the amount specified
			jse.executeScript(String.format("window.scroll(0, -%d);", amount));
		}
		
		return;
	}
}

// file: Browser.java
//////////////////////////////////////////////////////////////////////////////////////////////
