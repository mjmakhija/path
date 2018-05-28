package com.emsays.path;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.swing.table.TableModel;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHandler
{

	public static void createExcel(javax.swing.JTable table, String path) throws FileNotFoundException, java.io.IOException
	{

		XSSFWorkbook wb = new XSSFWorkbook(); //Excell workbook
		XSSFSheet sheet = wb.createSheet(); //WorkSheet
		XSSFRow sheetRow; //Row created at line 3
		TableModel model = table.getModel(); //Table model

		sheetRow = createRow(sheet); //Create row at line 0
		for (int headings = 0; headings < model.getColumnCount(); headings++)
		{ //For each column
			createCell(sheetRow).setCellValue(model.getColumnName(headings));//Write column name
		}

		for (int tableRow = 0; tableRow < model.getRowCount(); tableRow++)
		{ //For each table row
			sheetRow = createRow(sheet);

			for (int tableRowCol = 0; tableRowCol < table.getColumnCount(); tableRowCol++)
			{ //For each table column

				String value;
				if (model.getValueAt(tableRow, tableRowCol) == null)
				{
					value = "";
				}
				else
				{
					value = String.valueOf(model.getValueAt(tableRow, tableRowCol));
				}

				createCell(sheetRow).setCellValue(value); //Write value
			}

		}
		wb.write(new FileOutputStream(path));//Save the file     
	}

	public static void createExcel(String data[][]) throws FileNotFoundException, java.io.IOException
	{

		XSSFWorkbook wb = new XSSFWorkbook(); //Excell workbook
		XSSFSheet sheet = wb.createSheet(); //WorkSheet
		XSSFRow sheetRow; //Row created at line 3

		sheetRow = createRow(sheet); //Create row at line 0

		for (int tableRow = 0; tableRow < data.length; tableRow++)
		{ //For each table row
			sheetRow = createRow(sheet);

			for (int tableRowCol = 0; tableRowCol < data[tableRow].length; tableRowCol++)
			{ //For each table column

				String value;
				if (data[tableRow][tableRowCol] == null)
				{
					value = "";
				}
				else
				{
					value = String.valueOf(data[tableRow][tableRowCol]);
				}

				createCell(sheetRow).setCellValue(value); //Write value
			}

		}
		wb.write(new FileOutputStream("D:/message_log.xlsx"));//Save the file     
	}

	protected static XSSFRow createRow(XSSFSheet sheet)
	{
		if (sheet.getRow(sheet.getLastRowNum()) == null)
		{
			return sheet.createRow(sheet.getLastRowNum());
		}
		else
		{
			return sheet.createRow(sheet.getLastRowNum() + 1);
		}
	}

	protected static XSSFCell createCell(XSSFRow row)
	{
		return row.createCell(row.getLastCellNum() == -1 ? 0 : row.getLastCellNum());
	}
}
