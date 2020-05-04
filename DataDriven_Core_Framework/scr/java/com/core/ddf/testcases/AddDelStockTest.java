package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.core.ddf.base.BaseTest;
import com.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class AddDelStockTest extends BaseTest{
	
	
	@Test (priority = 1)
	public void AddStockTest() {
		test = rep.startTest("AddDelStockTest");
		test.log(LogStatus.INFO, "starting of stock adding test");
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"AddStockTest"})
	public void DeleteStockTest()  {
		test.log(LogStatus.INFO, "starting of stock deleting test");
		
	}
	
	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
	}
}
