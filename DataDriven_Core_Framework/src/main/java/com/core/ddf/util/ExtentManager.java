package com.core.ddf.util;

import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	// creating object (extent) of ExtentReports class
	 public static ExtentReports report;
	
	// this geInstance function returns the object (extent) of ExtentReports class
	// one object of this class represent one report
	// if the first time function geInstance called by other class ...it will initiate the object (extent) and load the file by given path
	// when 2nd time function geInstance called it just return the object so all logs are stored under one report
	public static ExtentReports getReport() {
		if(report==null) {
			Date d = new Date();
			String Filename = d.toString().replace(":", "_").replace(" ", "_")+".html";
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//report//" + Filename);
			sparkReporter.config().setReportName("Production regressing testing");
			sparkReporter.config().setDocumentTitle("Automation Extent Report");
			sparkReporter.config().setTheme(Theme.STANDARD);
			report = new ExtentReports();
			report.attachReporter(sparkReporter);
			}
		return report;
		}

}
