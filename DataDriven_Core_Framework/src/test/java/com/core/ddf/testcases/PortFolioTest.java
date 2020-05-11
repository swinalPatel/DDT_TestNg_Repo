package com.core.ddf.testcases;

import java.io.IOException;
import java.util.Hashtable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;
import com.core.ddf.util.DataUtil;


public class PortFolioTest extends BaseTest {
	//ExtentReports rep = ExtentManager.getReport(System.getProperty("user.dir") + "//report//");
	//public static ExtentTest test;
	String testname = "PortFolioTest";
	SoftAssert softAssert;
	
	@Test(dataProvider = "getdata")
	public void CreatePortfolioTest(Hashtable<String, String> data) throws IOException  {
		test = rep.createTest("CreatePortFolioTest");
		test.log(Status.INFO, "starting of creating portfolio Test");
		
		// checking the runmode of test ....
		DataUtil.checkTestRunmode(testname);
		test.log(Status.INFO, "Now Checking data ...." + data.toString());
		// checking the runmode of testcases individually.....
		DataUtil.checkTestCasesRunmode(data.get("RunMode"));	
		openBrowser(data.get("Browser"));
		
		navigate("App_URL");
		click("Money_xpath");
		
		
		if(!isLoggedIn("title_xpath" , "feedback_xpath")) {
			try {
				doLogin(CONFIG.getProperty("default_username") , CONFIG.getProperty("dafault_password"));
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		} else {
			test.log(Status.INFO, "Already logged in");
			click("Myportfolio_xpath");
		}
		
		try {
			clickAndWait("CreatePortfolio_id" , "createPortfolioButton_id" , 10);
		} catch (Exception e) {
			e.printStackTrace();
			test.log(Status.INFO, e.getMessage());
		}
		getElement("create_id").clear();
		type("create_id", data.get("PortFolioName"));
		click("createPortfolioButton_id");
		
		wait(4);
		
		WebElement e = getElement("portfolio_id");
		Select s = new Select(e);   //for dopdown menu selection
		String displayed_name = s.getFirstSelectedOption().getText();
		if(displayed_name.equals(data.get("PortFolioName"))) {
			test.log(Status.INFO, data.get("PortFolioName") + " PortFolio created successfully");
			takeScreenShot();
		} else {
			String mess = getText("AddPortFolioError_xpath");
			reportFailure(data.get("PortFolioName") + " PortFolio failed to create as " + mess);
		}   
		
		
	}


	@DataProvider
	public Object[][] getdata() throws IOException{
		inIt();
		Object[][] data = DataUtil.readData_Excel("CreatePortFolioTest");
		return data;
	}
	
	
	@Test(priority = 2)
	public void DeletePortfolioTest() {
		test = rep.createTest("DeletPortFolioTest");
		test.log(Status.INFO, "starting of deleting portfolio Test" );
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
