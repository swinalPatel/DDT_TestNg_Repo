package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class PortFolioTest extends BaseTest {
	
	@Test(priority = 1)
	public void CreatePortfolioTest()  {
		test = rep.createTest("PortFolioTest");
		test.log(Status.INFO, "staring of creating portfolio Test");
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"CreatePortfolioTest"})
	public void DeletePortfolioTest() {
		test.log(Status.INFO, "staring of deleting portfolio Test" );
		
	}
	
	@AfterMethod
	public void quit() {
		
		rep.flush();
	}

}
