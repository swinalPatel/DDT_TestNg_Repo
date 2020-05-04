package com.core.ddf.testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.core.ddf.base.BaseTest;
import com.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTest extends BaseTest {
	
		
	@Test
	public void loginTest() throws InterruptedException, IOException {
		
		test = report.startTest("LoginTest");
		test.log(LogStatus.INFO, "Starting the test LoginTest");
		openBrowser("Chrome");
		test.log(LogStatus.INFO, "Opening the browser");
		navigate("App_URL");
		click("Money_xpath");
		click("signin_xpath");
		type("user_name", "swinal01_10@yahoo.co.in");
		click("email_continue_id");
		takeScreenShot();
	}
	
	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
		
	}
	
	

}
