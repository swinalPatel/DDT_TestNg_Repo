package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class CheckTransactionHistoryTest extends BaseTest{
	
	@Test(priority = 3)
	public void checkTransactionHistoryTest() {
		test = rep.createTest("CheckTransactionHistoryTest");
		test.log(Status.INFO, "staring of checking transation history of stock Test");
		
	}
	
	@AfterMethod
	public void quit() {
		
		rep.flush();
	}
	

}
