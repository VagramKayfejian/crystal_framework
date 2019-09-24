//////////////////////////////////////////////////////////////////////////////////////////////
// file: Table.java

//////////////////////////////////////////////////////////////////////////////////////////////
// package
package com.armondsarkisian.crystal.table;

//////////////////////////////////////////////////////////////////////////////////////////////
// class
public final class Table {

    //////////////////////////////////////////////////////////////////////////////////////////////
    // attribute(s) -- static
    
    // external values
    public static String incoming_suiteName                             = null;
    public static String incoming_testName                              = null;
    public static String incoming_className                             = null;
    public static boolean incoming_isRunMethod                          = true;
    public static String incoming_methodName                            = null;
    public static boolean incoming_isRemote                             = false;
    public static String incoming_remoteIp                              = null;
    public static String incoming_remotePort                            = null;
    public static boolean incoming_is_proxy                             = false;
    public static boolean incoming_is_uiTest                            = false;
    public static String incoming_browser                               = null;
    public static boolean incoming_isHeadless                           = false;
    public static boolean incoming_is_apiTest                           = false;
    public static String incoming_loadUrl                               = null;
    public static String incoming_driverPath_other                      = null;
    public static String incoming_driverPath_win                        = null;
     
    // logging config
    public static final boolean is_displaySystemInformation             = true;
    public static final boolean is_displayTestModuleInformation         = true;
    public static final boolean is_displayOutput                        = true;
    public static final boolean is_displayErrorOutput                   = true;
    public static final boolean is_displayStackTrace                    = true;
    public static final boolean is_writeToLogFile                       = true;
    public static final boolean is_writeToHarLogFile                    = true;
    
    // timer config
    public static final int explicit_wait                               = 35000;
    public static final int sleep_object_click                          = 1000;
    public static final int sleep_object_moveToIt                       = 100;
    public static final int sleep_object_rightClick                     = 1000;
    public static final int sleep_object_rightClickNewTab               = 1000;
    public static final int sleep_object_rightClickNewWindow            = 1000;
    public static final int sleep_object_hover                          = 1000;
    public static final int sleep_object_textInput                      = 1000;
    public static final int sleep_page_load                             = 7500;
    public static final int sleep_page_goBackButton                     = 1500;
    
    // performance timer config
    public static final boolean timer_isRecordStepTime                  = true;
    public static final boolean timer_isRecordMethodTime                = true;
    public static final boolean timer_isRecordSuiteTime                 = true;
    
    // supporting text
    public static final String text_skipTest                            = "Test has been disabled";
    
    // ssl config
    public static final boolean ssl_handshake_allowUnrecognizedNames    = false; // setting this to true can potentially be dangerous
    
    // proxy -- config
    public static final boolean browser_proxy_isSetProxyOptions         = true;   // child
    public static final boolean browser_proxy_isStartProxy              = true;   // child
    public static final boolean browser_proxy_isCreateSeleniumProxy     = true;   // child
    public static final boolean browser_proxy_isSetHarCaptureTypes      = true;   // child
    public static final String browser_proxy_setCaptureType             = "Minimal";  // "All"|"Minimal"
    public static final boolean browser_proxy_isCreateNewHar            = true;   // child
    public static final boolean browser_chrome_capability_isSetProxyServerAddress = true; // child
    public static final boolean browser_chrome_capability_isSetProxy              = true;  // child
    
    // browser -- config

    // chrome
    public static final boolean browser_chrome_capability_isSetCapabilities       = true;  // master
    public static final boolean browser_chrome_argument_isSetArguments            = true;  // master
    public static final boolean browser_chrome_experimental_isSetExperimental     = true;  // master
    public static final boolean browser_chrome_argument_isEnableAutomation        = true;  // child
    public static final boolean browser_chrome_argument_isMaximized               = true;  // child
    public static final boolean browser_chrome_argument_isNoSandbox               = true;  // child
    public static final boolean browser_chrome_argument_isDisableSyncPasswords    = true;  // child
    public static final boolean browser_chrome_argument_isIgnoreCertificateErrors = true;  // child
    public static final boolean browser_chrome_argument_isDisableExtensions       = true;  // child
    public static final boolean browser_chrome_argument_isDisableSideNavigation   = true;  // child
    public static final boolean browser_chrome_argument_isDisableInfoBars         = true;  // child
    public static final boolean browser_chrome_argument_isDisableDevShmUsage      = true;  // child
    public static final boolean browser_chrome_argument_windowSize                = true;  // child
    public static final boolean browser_chrome_argument_isDNSPrefetchDisable      = true;  // child
    public static final boolean browser_chrome_argument_isDisableGPU              = true;  // child
    public static final boolean browser_chrome_argument_isSetUserDataDir          = true;  // child
    public static final boolean browser_chrome_argument_isSetCache                = true;  // child
    public static final boolean browser_chrome_argument_isIncognito               = true;  // child
    public static final boolean browser_chrome_argument_isSilence                 = false; // child
    public static final String browser_chrome_argument_setUserDataDir             = "--user-data-dir=cache";  // child
    public static final boolean browser_chrome_experimental_isSetCredentialEnabled= false; // child
    public static final boolean browser_chrome_experimental_isSetPasswordManager  = false; // child
    public static final boolean browser_chrome_experimental_isSetAutomation       = true;  // child
    public static final String browser_chrome_experimental_setAutomation          = "enable-automation";  // child
    public static final boolean browser_chrome_experimental_isSetContentSettingsPopups= true;  // child
    public static final int browser_chrome_experimental_setContentSettingsPopups  = 0;     // child
    public static final boolean browser_chrome_argument_isDisablePopups           = true;  // child
    public static final boolean browser_chrome_argument_isSetTestType             = true;  // child
    public static final boolean browser_chrome_experimental_isSetContentSettingsNotifications = true; // child
    public static final int browser_chrome_experimental_setContentSettingsNotifications = 1;  // child
    public static final boolean browser_chrome_capability_isApplyExperimental     = true;  // child - finalizer
    public static final boolean browser_chrome_capability_isSetLatestVersion      = true;  // child
    public static final boolean browser_chrome_capability_isSetBrowserName        = true;  // child
    public static final boolean browser_chrome_capability_isAcceptSSLCerts        = true;  // child
    public static final boolean browser_chrome_capability_isWaitForQuiescence     = true;  // child
    public static final boolean browser_chrome_capability_isSetLoggingPreferences = true;  // child
    public static final boolean browser_chrome_capability_isAllowPlugins          = false;  // child
    public static final boolean browser_chrome_capability_isStategizePageLoad     = true;  // child
    public static final boolean browser_chrome_capability_isApplyCapabilities     = true;  // child - finalizer
}

// file: Table.java
//////////////////////////////////////////////////////////////////////////////////////////////
