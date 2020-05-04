package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class AddDelStockTest extends BaseTest{
	
	
	@Test (priority = 1)
	public void AddStockTest() {
		test = rep.createTest("AddDelStockTest");
		test.log(Status.INFO, "starting of stock adding test");
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"AddStockTest"})
	public void DeleteStockTest()  {
		test.log(Status.INFO, "starting of stock deleting test");
		
	}
	
	@AfterMethod
	public void quit() {
		
		rep.flush();
	}
}
