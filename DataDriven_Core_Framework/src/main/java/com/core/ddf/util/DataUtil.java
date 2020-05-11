package com.core.ddf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;

import com.aventstack.extentreports.Status;
import com.core.ddf.base.BaseTest;


public class DataUtil extends BaseTest {
	
	public static XSSFWorkbook book = null;

	// (1) Function to read Excel file and get data from Excel file 
	public static Object[][] readData_Excel(String sheetName) throws IOException {
		
		Object[][] data = null;
		Hashtable<String, String> table = null;
		
		loadExcelFile();
		
		Sheet sheet = book.getSheet(sheetName);
		
		int no_row = sheet.getLastRowNum() - sheet.getFirstRowNum();
		System.out.println("total row: " + no_row );
		int no_column = 0;
		for(int i=0; i<=no_row; i++) {
			Row row = sheet.getRow(i);
			no_column= row.getLastCellNum();
		    }
		System.out.println("total column: " + no_column);
		data = new Object[no_row][1];
		
		for(int i=1; i<=no_row; i++) {
			table = new Hashtable<String, String>();
			Row row = sheet.getRow(i);
			for(int k=0; k<1; k++) {
				Row r = sheet.getRow(k);
				for(int j=0; j<no_column; j++) {
					String key =  r.getCell(j).getStringCellValue();	
					String value = row.getCell(j).getStringCellValue();
					table.put(key, value);
					//System.out.println(data[i-1][j]);
					}
				} 
			data[i-1][0] = table;
		}
		return data;
	}

	
	// (2) check runmode of testcase
	public static boolean checkRunMode(String testcase) throws IOException {
		Object[][] TCID = null;
		loadExcelFile();
		Sheet sheet = book.getSheet("TestCases"); 
		int totalrow = sheet.getLastRowNum() - sheet.getFirstRowNum();
		//System.out.println("Total row in testcases: " + totalrow);
		TCID = new Object[totalrow][2];
		for(int i=1; i<=totalrow; i++) {
			Row row = sheet.getRow(i); 
			for(int j=0; j< 1; j++ ) {
				TCID[i-1][j] = row.getCell(j).getStringCellValue();
				TCID[i-1][j+1] = row.getCell(j+1).getStringCellValue();
				//System.out.println(TCID[i-1][j] + "****" + TCID[i-1][j+1]);
				if(TCID[i-1][j].equals(testcase) && TCID[i-1][j+1].equals("Y")) {
					return true;
					} 
				}
			}
			return false;
		}

	
	// (3) read data from Excel file and load in workbook
	
	public static void loadExcelFile() throws IOException {
		
		File file = new File(CONFIG.getProperty("Excel_File"));
		FileInputStream fs1 = new FileInputStream(file);
		book = new XSSFWorkbook(fs1);
	}
	
	public static void checkTestRunmode(String testName) {
		try {
			if(!DataUtil.checkRunMode(testName)) {
				test.log(Status.SKIP, "PortFolio Test skipped as RunMode is N");
				throw new SkipException("PortFolio Test skipped as RunMode is N");
			}else {
				test.log(Status.INFO, "PortFolio Test started as RunMode is Y");
			}
		} catch (IOException e) {
		
			e.printStackTrace();
		}
	}
	
	public static void checkTestCasesRunmode(String runmodestatus) {
		if(runmodestatus.equals("N")) {
			test.log(Status.SKIP, "TestCase Skipped as RunMode is N");
			throw new SkipException("TestCase Skipped as RunMode is N");
		} else {
			test.log(Status.INFO, "TestCase started as RunMode is Y");
		}
	}

}



