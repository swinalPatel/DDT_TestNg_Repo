package com.core.ddf.testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class LoginTest extends BaseTest {
	SoftAssert softAssert = new SoftAssert();
		
	@Test
	public void loginTest() throws InterruptedException, IOException {
		
		test = rep.createTest("LoginTest");
		test.log(Status.INFO, "Starting the test LoginTest");
		openBrowser("Chrome");
		test.log(Status.INFO, "Opening the browser");
		navigate("App_URL");
		click("Money_xpath");
		
		 // minor failure
		if(!verifyText("title_xpath", "exp_text"))
			softAssert.assertTrue(false, "Err 1 wrong title text");
		
		
		softAssert.assertTrue(false, "Err 2");
		
		click("signin_xpath");
		
		if(!isElementPresent("user_name"))
			reportFailure("Email field not present");    // critical failure
		type("user_name", "swinal01_10@yahoo.co.in");
		
		softAssert.assertTrue(true, "Err 3");
		
		click("email_continue_id");
		
		if(!isElementPresent("password_name"))
			reportFailure("Password field not present");    // critical failure
		type("password_name", "Patel888!");
		test.log(Status.INFO, "entering username and password");
		
		click("continue_id");
		
		
		test.log(Status.INFO, "login successfull");
		takeScreenShot();
	}
	
	@AfterMethod
	public void quit() {
		try {
		softAssert.assertAll();
		} catch(Error e) {
			test.log(Status.FAIL, e.getMessage());
		}
		rep.flush();
		
	}
	
	

}
