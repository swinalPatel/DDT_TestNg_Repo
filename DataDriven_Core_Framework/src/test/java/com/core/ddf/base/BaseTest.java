package com.core.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.core.ddf.util.ExtentManager;

public class BaseTest {
	public static Properties CONFIG;
	public static WebDriver Mozilla = null;
	public static WebDriver Chrome = null;
	public static WebDriver IE = null;
	public  static WebDriver driver;
	public static ExtentReports rep = ExtentManager.getReport(System.getProperty("user.dir") + "//report//");     // initiating new object (rep) of ExtentReports class by given function (geInstance) of ExtentManager class
	public static ExtentTest test;            // creating test object of ExtentTest class 
	
	
	// (1) Function to initialise the properties file CONFIG
	public static void inIt() {
		if(CONFIG==null) {
			CONFIG = new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "//src//test//resources//CONFIG.properties");
				CONFIG.load(fs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// (2) Function to open the browser and checking whether browser is already loaded
	public void openBrowser(String browser) {
		test.log(Status.INFO, "Opening the browser " + browser);
		// now initialised the browser
		if(browser.equals("Mozilla") && Mozilla == null) {
			System.setProperty("webdriver.gecko.driver", CONFIG.getProperty("Mozilla_Driver"));
			driver = new FirefoxDriver();
			Mozilla = driver;
		}else if(browser.equals("Mozilla") && Mozilla != null) {
			driver= Mozilla;
		}else if(browser.equals("Chrome") && Chrome == null) {
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("Chrome_Driver"));
			driver = new ChromeDriver();
			Chrome = driver;
		}else if(browser.equals("Chrome") && Chrome != null) {
			driver= Chrome;
		}else if(browser.equals("IE") && IE == null) {
			System.setProperty("webdriver.ie.driver", CONFIG.getProperty("IE_Driver"));
			driver = new InternetExplorerDriver();
			IE = driver;
		} else if(browser.equals("IE") && IE == null) {
			driver= IE;
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		test.log(Status.INFO, browser + " Browser is now opened");
	}
	
	
	// (3) Function to navigate to the given URL
	public void navigate(String urlkey) {
			test.log(Status.INFO, "Navigating to the URL: " + CONFIG.getProperty(urlkey) );
			driver.get(CONFIG.getProperty(urlkey));
		}
		
		
	// (4) Function to find the element using either xpath or id or name
	public WebElement getElement(String locatorKey) throws IOException {
			WebElement e = null;
			try {
			if(locatorKey.endsWith("_xpath"))
				e = driver.findElement(By.xpath(CONFIG.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_id"))
				e = driver.findElement(By.id(CONFIG.getProperty(locatorKey)));
			else if(locatorKey.endsWith("_name"))
				e = driver.findElement(By.name(CONFIG.getProperty(locatorKey)));
			else {
				reportFailure("Locator not correct: " + locatorKey);
				Assert.fail("Locator not correct: " + locatorKey);
			}
			}catch(Exception ex) {
				reportFailure(ex.getMessage());
				ex.printStackTrace();
				Assert.fail("Fail the Test" + ex.getMessage());
			}
			return e;
		}
	
	// (5) Function to log in into the application
	public boolean doLogin(String username, String password) throws IOException, InterruptedException {
		click("signin_xpath"); 
		test.log(Status.INFO, "Trying to log in using username: " + username + " and password: " + password);
		type("user_name", username);
		if(!clickAndWait("email_continue_id", "password_name", 5 )){
			 
			reportFailure("login Fail as invalid username");
		}
		type("password_name", password);
		click("continue_id");
		if(isElementPresent("verifying_login_xpath")) {
			test.log(Status.INFO, "login successfull");
			return true;
		}
		else {
			String Error_Message = getElement("invalidLoginMessage_xpath").getText();
			reportFailure(Error_Message);
			return false;
		}
	}
	
	
	// (6) Function to click onto the locatorKey
	public void click(String locatorKey) throws IOException {
		test.log(Status.INFO, "Clicking on " + locatorKey);
		getElement(locatorKey).click();
		}
	
	
	// (7) Function to enter data into the given locatorKey
	public void type(String locatorKey, String data) throws IOException {
		test.log(Status.INFO, "Entering " + locatorKey + " as " + data);
		getElement(locatorKey).sendKeys(data);
		}
	
	public String getText(String locatorKey) throws IOException {
		String message = getElement(locatorKey).getText();
		System.out.println("Message: " + message);
		return message;
	}
	

	//*****************Wait functions************//
	
	// (8) Function to click on one element and wait until next element in sequence will displayed
	public boolean clickAndWait(String xpathExpTarget, String xpathExpWait, int waitime) throws InterruptedException, IOException {
			click(xpathExpTarget);
			for(int i=0; i<waitime; i++) {
				if(isElementPresent(xpathExpWait) && isElementDisplayed(xpathExpWait)) 
					return true;
				 else 
					wait(1);
				}
			return false;
		} 
		
		
	// (9) Function to wait till given time
	public void wait(int time) {
			try {
				Thread.sleep(time*1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	//*****************Validation functions************//
	

	// (10) Function to check element is displayed or not
	public boolean isElementDisplayed(String locatorKey) throws IOException {
		test.log(Status.INFO, "Checking element " + locatorKey + " is displayed?");
		if(getElement(locatorKey).isDisplayed()) {
			test.log(Status.INFO, locatorKey + " is displayed");
			return true;
		}else {
			test.log(Status.INFO, locatorKey + " element is not displayed");
			return false;
		}
	}
	
	
	// (11) Function to verify title
	public  boolean isLoggedIn(String locatorKey1, String locatorKey2) throws IOException {
		if(getElement(locatorKey1).getText().equals(CONFIG.getProperty("exp_text")))
			return false;
		else if(getElement(locatorKey2).getText().equals(CONFIG.getProperty("feedback_text")))
			return true;
		return false;
		
	}
	
	
	// (12) Function to verify whether element is present or not
	public boolean isElementPresent(String locatorKey) throws IOException {
		test.log(Status.INFO, "Checking the presence of Element " + locatorKey);
		List<WebElement> elementList = null;
		if(locatorKey.endsWith("_xpath"))
			elementList = driver.findElements(By.xpath(CONFIG.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_id"))
			elementList = driver.findElements(By.id(CONFIG.getProperty(locatorKey)));
		else if(locatorKey.endsWith("_name"))
			elementList = driver.findElements(By.name(CONFIG.getProperty(locatorKey)));
		else {
			reportFailure("Locator not correct: " + locatorKey);
			Assert.fail("Locator not correct: " + locatorKey);
		}
		if(elementList.size()==0) {
			test.log(Status.INFO, "Element is not present " + locatorKey);
			return false;
		} else {
			test.log(Status.INFO, "Element is  present " + locatorKey);
			return true;
			}
	}
	

	// (13) Function to verify the text 
	public boolean verifyText(String locatorKey, String exp_text) throws IOException {
		String actual_text = getElement(locatorKey).getText();
		String expected_text = CONFIG.getProperty(exp_text);
		if(actual_text.equals(expected_text))
			return true;
		else 
			return false;
		}
	
	
	//*****************Reporting functions************//
	
	// (14) Function to report pass 
	public void reportPass(String msg) {
		test.log(Status.PASS, msg);
		
	}
	
	// (15) Function to report failure ...stop the execution and take screenshots
	public void reportFailure(String msg) throws IOException {
		test.log(Status.FAIL, msg);
		takeScreenShot();
		Assert.fail(msg);
		}
	
	
	// (16) Function to Take screenshots and store as jpeg file into given path
	public  void takeScreenShot() throws IOException {
			// Convert web driver object to TakeScreenshot
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			// Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			try {
			// Move image file to new destination
			File DestFile=new File(ExtentManager.screenshotpath + filename()+".jpeg");
			// Copy file at destination
			FileHandler.copy(SrcFile, DestFile);
			} catch (IOException e) {
				e.printStackTrace();
				}
			// put screenshot file in the report
			test.log(Status.INFO, "screenshot--->" + test.addScreenCaptureFromPath(ExtentManager.screenshotpath  + filename()+".jpeg"));
		}
		
		
	// (17) Function to assigned new filename as recent data and time (filename for screenshots)
	public static String filename() {
		    return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		}

}
