package com.core.ddf.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;
import com.core.ddf.util.DataUtil;


public class LoginTest extends BaseTest {
	SoftAssert softAssert;
	String testname = "LoginTest";
		
	@Test(dataProvider = "getdata")
	public void loginTest(Hashtable<String, String> data) throws InterruptedException, IOException {
		
		test = rep.createTest("LoginTest");
		test.log(Status.INFO, "Starting the LoginTest");
		
		// checking the runmode of test ....
		DataUtil.checkTestRunmode(testname);
		test.log(Status.INFO, "Now Checking data ...." + data.toString());
		// checking the runmode of testcases individually.....
		DataUtil.checkTestCasesRunmode(data.get("RunMode"));
		
		openBrowser(data.get("Browser"));
		navigate("App_URL");
		click("Money_xpath");
		
		// minor failure
		if(!verifyText("title_xpath", "exp_text")) {
			softAssert.assertTrue(false, "Err 1 wrong title text");
		}
		
		// checking signin tab is present or not
		if(!isElementPresent("signin_xpath")) {
			reportFailure("Signin tab is not present");  // critical failure
		}
		
		boolean actualResult = doLogin(data.get("UserName") , data.get("PassWord"));
		System.out.println("actual result: " + actualResult );
		
		boolean expectedResult = false;
		if(data.get("ExpectedResult").equals("Pass"))
			expectedResult = true;
		else 
			expectedResult = false;
		System.out.println("Expected result: " + expectedResult);
		
		if(actualResult != expectedResult)
			reportFailure("Test Fail as Actual and Expected Result does not match");
		else
			reportPass("Test Pass as Actual and Expected Result match");
		
		
}
	
	@DataProvider
	public Object[][] getdata() throws IOException{
		inIt();
		Object[][] data = DataUtil.readData_Excel(testname);
		return data;
		}
	
	@BeforeMethod
	public void start() {
		softAssert = new SoftAssert();
	}
	
	@AfterMethod
	public void quit() {
		try {
		softAssert.assertAll();
		} catch(Error e) {
			test.log(Status.FAIL, e.getMessage());
		}
		if(rep!=null) {
		rep.flush();
		}
		if(driver!=null)
			driver.quit();
	}
	
	

}
