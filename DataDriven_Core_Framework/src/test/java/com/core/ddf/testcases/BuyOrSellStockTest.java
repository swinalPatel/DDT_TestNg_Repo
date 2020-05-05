package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class BuyOrSellStockTest extends BaseTest {
	
	
	@Test (priority = 1)
	public void BuyStockTest() {
		test = rep.createTest("BuyOrSellStockTest");
		test.log(Status.INFO, "staring of buying stock test");
		
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"BuyStockTest"})
	public void SellStockTest() {
		test.log(Status.INFO, "staring of selling stock Test");
		
	}
	
		
	@AfterMethod
	public void quit() {
		
		rep.flush();
	}

}
