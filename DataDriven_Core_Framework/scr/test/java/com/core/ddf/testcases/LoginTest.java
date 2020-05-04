package com.core.ddf.testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class LoginTest extends BaseTest {
	
		
	@Test
	public void loginTest() throws InterruptedException, IOException {
		
		test = rep.createTest("LoginTest");
		test.log(Status.INFO, "Starting the test LoginTest");
		openBrowser("Chrome");
		test.log(Status.INFO, "Opening the browser");
		navigate("App_URL");
		click("Money_xpath");
		click("signin_xpath");
		type("user_name", "swinal01_10@yahoo.co.in");
		click("email_continue_id");
		takeScreenShot();
	}
	
	@AfterMethod
	public void quit() {
		
		rep.flush();
		
	}
	
	

}
