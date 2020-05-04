package com.core.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class BaseTest {
	public Properties CONFIG;
	public WebDriver driver;
	public ExtentReports rep = ExtentManager.getReport();     // initiating new object (rep) of ExtentReports class by given function (geInstance) of ExtentManager class
	public ExtentTest test;            // creating test object of ExtentTest class 
	
	
	// (1) (Function for opening the browser and initialised the properties file)
	public void openBrowser(String browser) {
		
		// initialised the properties file CONFIG
		if(CONFIG==null) {
			CONFIG = new Properties();
			try {
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "//scr//test//resources//CONFIG.properties");
				CONFIG.load(fs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// now initialised the browser
		if(browser.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver", CONFIG.getProperty("Mozilla_Driver"));
			driver = new FirefoxDriver();
		}else if(browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", CONFIG.getProperty("Chrome_Driver"));
			driver = new ChromeDriver();
		}else if(browser.equals("IE")) {
			System.setProperty("webdriver.ie.driver", CONFIG.getProperty("IE_Driver"));
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	
	// (2) (Function for navigating the URL
	public void navigate(String urlkey) {
		driver.get(CONFIG.getProperty(urlkey));
		
		
	}
	
	public void click(String locatorKey) throws IOException {
		getElement(locatorKey).click();
		
	}
	
	public void type(String locatorKey, String data) throws IOException {
		getElement(locatorKey).sendKeys(data);
		
	}
	
	
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


	//*****************Validation functions************//
	
	public boolean verifyTitle() {
		return false;
		
	}
	
	public boolean isElementPresent() {
		return false;
	}

	public boolean verifyText() {
		return false;
	}
	
	//*****************Reporting functions************//
	
	public void reportPass(String msg) {
		test.log(LogStatus.PASS, msg);
		
	}
	
	public void reportFailure(String msg) throws IOException {
		test.log(LogStatus.FAIL, msg);
		takeScreenShot();
		Assert.fail();
		}
	
	// store screenshots in jpeg file
		public  void takeScreenShot() throws IOException {
			// Convert web driver object to TakeScreenshot
			//TakesScreenshot scrShot =((TakesScreenshot)dr);
			
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			
			// Call getScreenshotAs method to create image file
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			try {
			// Move image file to new destination
			File DestFile=new File(System.getProperty("user.dir") + "\\screenshots\\" + filename()+".jpeg");
			
			// Copy file at destination
			FileHandler.copy(SrcFile, DestFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// put screenshot file in the report
			test.log(LogStatus.INFO, "screenshot--->" + test.addScreenCapture(System.getProperty("user.dir") + "\\screenshots\\" + filename()+".jpeg"));
			}
		
		public static String filename() {
		    return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
		}
}
