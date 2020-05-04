package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.core.ddf.base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class CheckTransactionHistoryTest extends BaseTest{
	
	@Test(priority = 3)
	public void checkTransactionHistoryTest() {
		test = rep.startTest("CheckTransactionHistoryTest");
		test.log(LogStatus.INFO, "staring of checking transation history of stock Test");
		
	}
	
	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
	}
	

}
