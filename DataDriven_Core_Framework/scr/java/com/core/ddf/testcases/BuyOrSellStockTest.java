package com.core.ddf.testcases;

import static org.testng.Assert.fail;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.core.ddf.base.BaseTest;
import com.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BuyOrSellStockTest extends BaseTest {
	
	
	@Test (priority = 1)
	public void BuyStockTest() {
		test = rep.startTest("BuyOrSellStockTest");
		test.log(LogStatus.INFO, "staring of buying stock test");
		
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"BuyStockTest"})
	public void SellStockTest() {
		test.log(LogStatus.INFO, "staring of selling stock Test");
		
	}
	
		
	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
	}

}
