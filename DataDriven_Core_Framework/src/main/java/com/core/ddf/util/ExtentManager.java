package com.core.ddf.util;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	// creating object (extent) of ExtentReports class
	 public static ExtentReports report;
	 public static String screenshotpath;
	 
	
	// this geInstance function returns the object (extent) of ExtentReports class
	// one object of this class represent one report
	// if the first time function geInstance called by other class ...it will initiate the object (extent) and load the file by given path
	// when 2nd time function geInstance called it just return the object so all logs are stored under one report
	public static ExtentReports getReport(String basepath) {
		if(report==null) {
			// report folder generate
			Date d = new Date();
			String reportFolder = d.toString().replace(":",  "_");
			screenshotpath = basepath + reportFolder  + "//screenshots//";
			File file = new File(screenshotpath);
			file.mkdirs();
			
			// initialising report config
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(basepath + reportFolder );
			sparkReporter.config().setReportName("Production regressing testing");
			sparkReporter.config().setDocumentTitle("Automation Extent Report");
			sparkReporter.config().setTheme(Theme.STANDARD);
			report = new ExtentReports();
			report.attachReporter(sparkReporter);
			}
		return report;
		}

}
