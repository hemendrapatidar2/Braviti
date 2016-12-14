package com.tm.braveti.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.tm.braveti.model.Category;
import com.tm.braveti.model.Outlet;
import com.tm.braveti.model.TransactionHistory;
import com.tm.braveti.model.User;

public class LoadStaticData {
	private Map<String, List> staticData = new HashMap<>();

	public LoadStaticData() {
		try {
			loadStaticData();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public List<User> getUsers() {
		return staticData.get("USER");
	}

	public List<Outlet> getOutlets() {
		return staticData.get("OUTLET");
	}

	@SuppressWarnings("unchecked")
	public List<TransactionHistory> getTransactionHistories() {
		return staticData.get("TRANSACTIONHISTORY");
	}

	public List<Category> getCategories() {
		return staticData.get("CATEGORY");
	}

	private void loadStaticData() throws IOException {
		/*
		 * InputStream ExcelFileToRead = new FileInputStream( "Braviti.xls");
		 */
		InputStream ExcelFileToRead = this.getClass().getClassLoader()
				.getResourceAsStream("com\\tm\\braveti\\resources\\Braviti.xls");
		/*
		 * InputStream ExcelFileToRead = new FileInputStream(
		 * "E:\\hackethon\\Braviti\\Braviti\\WebContent\\WEB-INF\\resources\\Braviti.xls"
		 * );
		 */
		HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
		List<HSSFSheet> sheets = new ArrayList<>();
		sheets.add(wb.getSheetAt(0));
		sheets.add(wb.getSheetAt(1));
		sheets.add(wb.getSheetAt(2));
		sheets.add(wb.getSheetAt(3));
		this.staticData = prepareCustomObject(sheets);
	}

	private Map<String, List> prepareCustomObject(List<HSSFSheet> sheets) {
		Map<String, List> staticData = new HashMap();
		List<User> users = new ArrayList<>();
		List<Outlet> outlets = new ArrayList<>();
		List<TransactionHistory> transactionHistorious = new ArrayList<>();
		List<Category> categaries = new ArrayList<>();
		for (HSSFSheet sheet : sheets) {
			List<List> data = readData(sheet);
			if (sheet.getSheetName().equalsIgnoreCase("user")) {
				User user;
				for (List rowData : data) {
					if (rowData.size() > 0 && null != rowData.get(0)) {
						user = new User();
						user.setId(rowData.get(0).toString());
						user.setFname(rowData.get(1).toString());
						user.setLname(rowData.get(2).toString());
						user.setGender(rowData.get(3).toString());
						user.setDob(rowData.get(4).toString());
						user.setCclimit(rowData.get(5).toString());
						user.setIncomegrp(rowData.get(6).toString());
						users.add(user);
					}
				}
			}
			if (sheet.getSheetName().equalsIgnoreCase("transhistory")) {
				TransactionHistory transactionHistory;
				for (List rowData : data) {
					transactionHistory = new TransactionHistory();
					if (rowData.size() > 0 && null != rowData.get(0)) {
						transactionHistory.setId(rowData.get(0).toString());
						transactionHistory.setUserid(rowData.get(1).toString());
						transactionHistory.setDate(rowData.get(2).toString());
						transactionHistory.setAmount(rowData.get(3).toString());
						transactionHistory.setOutletid(rowData.get(4).toString());
						transactionHistory.setDescription(rowData.get(5).toString());
						transactionHistory.setCategoryid(rowData.get(6).toString());
						transactionHistorious.add(transactionHistory);
					}
				}

			}
			if (sheet.getSheetName().equalsIgnoreCase("Outlet")) {
				Outlet outlet;
				for (List rowData : data) {
					if (rowData.size() > 0 && null != rowData.get(0)) {
						outlet = new Outlet();
						outlet.setId(rowData.get(0).toString());
						outlet.setName(rowData.get(1).toString());
						outlet.setLocation(rowData.get(2).toString());
						outlet.setCategary(rowData.get(3).toString());
						outlet.setPrice(rowData.get(4).toString());
						outlet.setOfferdesc(rowData.get(5).toString());
						outlet.setLatitude(Double.parseDouble(rowData.get(6).toString()));
						outlet.setLangitude(Double.parseDouble(rowData.get(7).toString()));
						outlets.add(outlet);
					}
				}
			}
			if (sheet.getSheetName().equalsIgnoreCase("catagary")) {
				Category categary;
				for (List rowData : data) {
					if (rowData.size() > 0 && null != rowData.get(0)) {
						categary = new Category();
						categary.setId(rowData.get(0).toString());
						categary.setName(rowData.get(1).toString());
						categaries.add(categary);
					}
				}

			}
		}

		staticData.put("USER", users);
		staticData.put("OUTLET", outlets);
		staticData.put("TRANSACTIONHISTORY", transactionHistorious);
		staticData.put("CATEGORY", categaries);
		return staticData;
	}

	private List<List> readData(HSSFSheet sheet) {
		HSSFRow row;
		HSSFCell cell;
		Iterator rows = sheet.rowIterator();
		List<String> rowData = new ArrayList<String>();
		List<List> rowDataList = new ArrayList<List>();
		boolean isFirstRow = true;
		rows.next();
		while (rows.hasNext()) {
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			rowData = new ArrayList<String>();
			while (cells.hasNext()) {
				cell = (HSSFCell) cells.next();
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
					rowData.add(cell.getStringCellValue().toString());
				} else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
					rowData.add(cell.getNumericCellValue() + "");
				}
			}
			rowDataList.add(rowData);

		}
		return rowDataList;
	}

	public static void main(String[] args) throws IOException {
		LoadStaticData staticData = new LoadStaticData();
		List<User> data = staticData.getUsers();
		List<Outlet> outlets = staticData.getOutlets();
		List<TransactionHistory> transactionHistories = staticData.getTransactionHistories();
		List<Category> categaries = staticData.getCategories();
		for (User user : data) {
			System.out.println(user.toString());
		}
		for (Outlet outlet : outlets) {
			System.out.println(outlet.toString());
		}
		for (TransactionHistory transactionHistory : transactionHistories) {
			System.out.println(transactionHistory.toString());
		}
		for (Category user : categaries) {
			System.out.println(user.toString());
		}

	}
}
