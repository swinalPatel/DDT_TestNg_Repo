package com.core.ddf.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.core.ddf.base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class PortFolioTest extends BaseTest {
	
	@Test(priority = 1)
	public void CreatePortfolioTest()  {
		test = rep.startTest("PortFolioTest");
		test.log(LogStatus.INFO, "staring of creating portfolio Test");
		
	}
	
	@Test(priority = 2, dependsOnMethods = {"CreatePortfolioTest"})
	public void DeletePortfolioTest() {
		test.log(LogStatus.INFO, "staring of deleting portfolio Test" );
		
	}
	
	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();
	}

}
