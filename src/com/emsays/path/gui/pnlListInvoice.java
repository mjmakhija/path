package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import com.emsays.path.Database;
import com.emsays.path.ExcelHandler;
import com.emsays.path.GV;
import com.emsays.path.SeleniumHelper;
import com.emsays.path.Util;
import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.InvoiceSer;
import com.emsays.path.dao.CompanyYearInfoSer;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.MonthYearDTO;
import com.emsays.path.dto.ReceiptDTO;
import com.emsays.path.report.bean.InvoiceBean;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class pnlListInvoice extends javax.swing.JPanel implements MyPanelBack
{

	private class AmountDTO
	{

		int amtRcvdCash;
		int amtRcvdWriteOff;
		int amtDue;
	}

	private class MyTableModel2 extends AbstractTableModel
	{

		private List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
		private List<AmountDTO> amountDTOs = new ArrayList<>();
		String[] columnNames;
		boolean[] checked;

		public MyTableModel2()
		{
		}

		public MyTableModel2(String[] columnNames)
		{
			this.columnNames = columnNames;
		}

		public MyTableModel2(String[] columnNames, List<InvoiceDTO> invoices, List<AmountDTO> amountDTOs)
		{
			this.invoiceDTOs = invoices;
			this.amountDTOs = amountDTOs;
			this.columnNames = columnNames;
			checked = new boolean[invoices.size()];
		}

		public void updateData(List<InvoiceDTO> invoices, List<AmountDTO> amountDTOs)
		{
			this.invoiceDTOs = invoices;
			this.amountDTOs = amountDTOs;
			checked = new boolean[invoices.size()];
			fireTableDataChanged();
		}

		@Override
		public String getColumnName(int column)
		{
			return columnNames[column];
		}

		@Override
		public Class<?> getColumnClass(int columnIndex)
		{
			Class clazz = String.class;
			switch (columnIndex)
			{
				case 0:
					clazz = Boolean.class;
					break;
			}
			return clazz;
		}

		@Override
		public boolean isCellEditable(int row, int column)
		{
			return column == 0;
		}

		@Override
		public int getRowCount()
		{
			return invoiceDTOs.size();
		}

		@Override
		public int getColumnCount()
		{
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{

			InvoiceDTO invoice = invoiceDTOs.get(rowIndex);
			AmountDTO amountDTO = amountDTOs.get(rowIndex);
			switch (columnIndex)
			{
				case 0:
					return checked[rowIndex];
				case 1:
					return rowIndex + 1;
				case 2:
					return invoice.getId();
				case 3:
					return invoice.getCustomer().getId();
				case 4:
					return invoice.getSrNo();
				case 5:
					return invoice.getCustomer().getAccountNo();
				case 6:
					return invoice.getCustomer().getCustomerName();
				case 7:
					return invoice.getCustomer().getHouseNo1()
							+ (invoice.getCustomer().getHouseNo2() == null || invoice.getCustomer().getHouseNo2().equals("") ? "" : "-" + invoice.getCustomer().getHouseNo2())
							+ (invoice.getCustomer().getHouseNo3() == null || invoice.getCustomer().getHouseNo3().equals("") ? "" : "-" + invoice.getCustomer().getHouseNo3())
							+ (invoice.getCustomer().getArea().getName() == null || invoice.getCustomer().getArea().getName().equals("") ? "" : " " + invoice.getCustomer().getArea().getName());
				case 8:
					return invoice.getCustomer().getMobile();
				case 9:
					return invoice.getCustomer().getPaymentType() == null ? null : invoice.getCustomer().getPaymentType().getName();
				case 10:
					return invoice.getAmount();
				case 11:
					return invoice.getNote();
				case 12:
					return amountDTO.amtRcvdCash;
				case 13:
					return amountDTO.amtRcvdWriteOff;
				case 14:
					return amountDTO.amtDue;
				default:
					return null;
			}
		}

		public InvoiceDTO getInvoiceDTOAt(int i)
		{
			return invoiceDTOs.get(i);
		}

		public AmountDTO getAmountDTOAt(int i)
		{
			return amountDTOs.get(i);
		}

		public void checkAll()
		{
			for (int i = 0; i < getRowCount(); i++)
			{
				setValueAt(true, i, 0);
			}

		}

		public void check(int rowIndex)
		{
			setValueAt(true, rowIndex, 0);
		}

		public void uncheckAll()
		{
			for (int i = 0; i < getRowCount(); i++)
			{
				setValueAt(false, i, 0);
			}
		}

		public void uncheck(int rowIndex)
		{
			setValueAt(false, rowIndex, 0);
		}

		public boolean isChecked(int rowIndex)
		{
			return checked[rowIndex];
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex)
		{
			super.setValueAt(aValue, rowIndex, columnIndex);

			if (columnIndex == 0)
			{
				checked[rowIndex] = (boolean) aValue;
				fireTableCellUpdated(rowIndex, columnIndex);
			}
		}

	}

	MyFormWrapper vParent;
	List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
	List<AmountDTO> amountDTOs = new ArrayList<>();
	List<InvoiceDTO> invoiceDTOsInTable = new ArrayList<>();
	List<AmountDTO> amountDTOsInTable = new ArrayList<>();
	String[] vColumnNamesParent =
	{
		"",
		"#",
		"Id",
		"Customer Id",
		"Invoice No",
		"Account No",
		"Customer Name",
		"Address",
		"Mobile",
		"Payment Type",
		"Amount",
		"Note",
		"Amt Rcvd Cash",
		"Amt Rcvd Write Off",
		"Amt Due"
	};

	List<MonthYearDTO> monthYears;
	int checkColForColorIndex = vColumnNamesParent.length - 1;

	private SeleniumHelper seleniumHelper = null;
	private WebDriver driver = null;

	private MyTableModel2 myTableModel;

	enum invoiceTypes
	{
		ALL,
		PAID,
		UNPAID,
		OVERPAID
	}

	/**
	 * Creates new form pnlLogin
	 */
	public pnlListInvoice(MyFormWrapper vParent)
	{
		initComponents();

		tblParent.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
			{
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected)
				{
					int amt = (int) table.getModel().getValueAt(row, checkColForColorIndex);
					if (amt > 0)
					{
						c.setBackground(new Color(178, 223, 219));
					}
					else if (amt < 0)
					{
						c.setBackground(new Color(255, 224, 178));
					}
					else
					{
						c.setBackground(Color.WHITE);
					}
				}
				return c;
			}
		});

		myTableModel = new MyTableModel2(vColumnNamesParent);
		tblParent.setModel(myTableModel);

		monthYears = new InvoiceSer(Database.getSessionCompanyYear()).mGetMonthYear();

		for (MonthYearDTO monthYear : monthYears)
		{
			cmbMonthYear.addItem(monthYear.getMonth() + " - " + monthYear.getYear());
		}

		cmbInvoiceType.addItem("ALL");
		cmbInvoiceType.addItem("PAID");
		cmbInvoiceType.addItem("UNPAID");
		cmbInvoiceType.addItem("OVERPAID");

		cmbInvoiceType.setSelectedIndex(0);

		this.vParent = vParent;
	}

	private void mLoadDataParent(List<InvoiceDTO> vLstObjInvoice, invoiceTypes invoiceType)
	{
		this.amountDTOs.clear();
		this.invoiceDTOs = vLstObjInvoice;

		for (InvoiceDTO vObjInvoice : vLstObjInvoice)
		{
			AmountDTO amountDTO = new AmountDTO();
			for (InvoiceReceiptDTO receipt : vObjInvoice.getReceiptes())
			{
				if (receipt.getReceipt().getAccount().getName().equals(AccountSer.enmKeys.WRITE_OFF.toString()))
				{
					amountDTO.amtRcvdWriteOff += receipt.getAmount();
				}
				else
				{
					amountDTO.amtRcvdCash += receipt.getAmount();
				}
			}
			amountDTO.amtDue = vObjInvoice.getAmount() - amountDTO.amtRcvdCash - amountDTO.amtRcvdWriteOff;

			amountDTOs.add(amountDTO);
		}

		this.invoiceDTOsInTable.clear();
		this.amountDTOsInTable.clear();

		if (invoiceType == invoiceTypes.OVERPAID)
		{
			for (int i = 0; i < invoiceDTOs.size(); i++)
			{
				if (amountDTOs.get(i).amtDue < 0)
				{
					invoiceDTOsInTable.add(invoiceDTOs.get(i));
					amountDTOsInTable.add(amountDTOs.get(i));
				}
			}
		}
		else if (invoiceType == invoiceTypes.PAID)
		{
			for (int i = 0; i < invoiceDTOs.size(); i++)
			{
				if (amountDTOs.get(i).amtDue == 0)
				{
					invoiceDTOsInTable.add(invoiceDTOs.get(i));
					amountDTOsInTable.add(amountDTOs.get(i));
				}
			}
		}
		else if (invoiceType == invoiceTypes.UNPAID)
		{
			for (int i = 0; i < invoiceDTOs.size(); i++)
			{
				if (amountDTOs.get(i).amtDue > 0)
				{
					invoiceDTOsInTable.add(invoiceDTOs.get(i));
					amountDTOsInTable.add(amountDTOs.get(i));
				}
			}
		}
		else
		{
			invoiceDTOsInTable.addAll(invoiceDTOs);
			amountDTOsInTable.addAll(amountDTOs);
		}

		myTableModel.updateData(invoiceDTOsInTable, amountDTOsInTable);

		Util.resizeColumnWidth(tblParent);

		fillCountBar();

	}

	private void clearBoxes()
	{
		//txtName.setText(null);
		txtNote.setText(null);
	}

	private void handleClickBtnSearch()
	{
		MonthYearDTO selectedMonthYear = monthYears.get(cmbMonthYear.getSelectedIndex());

		this.mLoadDataParent(new InvoiceSer(Database.getSessionCompanyYear()).mSearch("", txtNote.getText(), selectedMonthYear.getMonth(), selectedMonthYear.getYear()),
				getInvoiceType());
	}

	private void handleClickBtnAll()
	{
		clearBoxes();

		handleClickBtnSearch();
	}

	private void handleClickBtnEdit()
	{
		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblParent.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		InvoiceDTO invoice = myTableModel.getInvoiceDTOAt(tblParent.getSelectedRow());
		CustomerDTO parent = invoice.getCustomer();

		this.vParent.addPanel(new pnlAddInvoice(vParent, this, parent, invoice));

	}

	private void handleClickBtnDelete()
	{

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
			if (dialogResult == JOptionPane.YES_OPTION)
			{
				InvoiceSer objInvoiceSer = new InvoiceSer(Database.getSessionCompanyYear());

				int[] vSelectedIndices = tblParent.getSelectedRows();
				for (int i : vSelectedIndices)
				{
					objInvoiceSer.delete(myTableModel.getInvoiceDTOAt(i));
				}
				//mLoadData();
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				handleClickBtnSearch();
			}
		}

	}

	private void handleClickBtnConvert()
	{
		CommonUIActions.convertToExcelEventHandler(tblParent);
	}

	private void handleClickMiCheckAll()
	{
		myTableModel.checkAll();
	}

	private void handleClickMiUnCheckAll()
	{
		myTableModel.uncheckAll();
	}

	private void handleClickMiCheckSelected()
	{
		for (int i : tblParent.getSelectedRows())
		{
			myTableModel.check(i);
		}
	}

	private void handleClickMiUnCheckSelected()
	{
		for (int i : tblParent.getSelectedRows())
		{
			myTableModel.uncheck(i);
		}
	}

	private void handleClickBtnGenerate()
	{
		vParent.addPanel(new pnlGenerateInvoice(vParent));
	}

	private void handleClickBtnMapAuto()
	{

		InvoiceSer invoiceSer = new InvoiceSer(Database.getSessionCompanyYear());
		invoiceSer.mapReceipts(invoiceDTOsInTable);

	}

	private void handleClickBtnGeneratePDF()
	{

		String fileName;
		File file;
		int i = 0;
		boolean run = true;

		do
		{
			fileName = GV.TEMP_DIR + "/file" + String.valueOf(i) + ".pdf";
			file = new File(fileName);

			if (file.exists())
			{
				if (file.delete())
				{
					run = false;
				}
				else
				{
					i++;
				}

			}
			else
			{
				run = false;
			}

		}
		while (run);

		List<InvoiceBean> invoiceBeans = new ArrayList<>();
		for (int j = 0; j < myTableModel.getRowCount(); j++)
		{
			if (myTableModel.isChecked(j))
			{
				invoiceBeans.add(new InvoiceBean(myTableModel.getInvoiceDTOAt(j)));
			}
		}

		try
		{
			CompanyYearInfoSer companyYearInfoSer = new CompanyYearInfoSer(Database.getSessionCompanyYear());
			HashMap map = new HashMap<>();
			map.put(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName.toString(),
					companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName));

			map.put(CompanyYearInfoSer.CompanyYearInfoKey.Address.toString(),
					companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.Address));

			map.put(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo.toString(),
					companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo));

			String jasperPrint = JasperFillManager.fillReportToFile(GV.REPORT_DIR + "/Receipt.jasper", map, new JRBeanCollectionDataSource(invoiceBeans));
			JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		}
		catch (JRException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	private void handleClickBtnGeneratePDF8()
	{

		String fileName;
		File file;
		int i = 0;
		boolean run = true;

		do
		{
			fileName = GV.TEMP_DIR + "/file" + String.valueOf(i) + ".pdf";
			file = new File(fileName);

			if (file.exists())
			{
				if (file.delete())
				{
					run = false;
				}
				else
				{
					i++;
				}

			}
			else
			{
				run = false;
			}

		}
		while (run);

		List<InvoiceDTO> invoicesSelectedForPrint = new ArrayList<>();
		for (int j = 0; j < myTableModel.getRowCount(); j++)
		{
			if (myTableModel.isChecked(j))
			{
				invoicesSelectedForPrint.add(myTableModel.getInvoiceDTOAt(j));
			}
		}

		invoicesSelectedForPrint = InvoiceSer.sort(invoicesSelectedForPrint);

		List<InvoiceBean> invoiceBeans = new ArrayList<>();
		for (InvoiceDTO invoiceDTOTemp : invoicesSelectedForPrint)
		{
			invoiceBeans.add(new InvoiceBean(invoiceDTOTemp));
		}

		try
		{
			CompanyYearInfoSer companyYearInfoSer = new CompanyYearInfoSer(Database.getSessionCompanyYear());
			HashMap map = new HashMap<>();
			map.put(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName.toString(),
					companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.CompanyName));

			map.put(CompanyYearInfoSer.CompanyYearInfoKey.Address.toString(),
					companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.Address));

			map.put(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo.toString(),
					companyYearInfoSer.get(CompanyYearInfoSer.CompanyYearInfoKey.ContactNo));

			String jasperPrint = JasperFillManager.fillReportToFile(GV.REPORT_DIR + "/Receipt8.jasper", map, new JRBeanCollectionDataSource(invoiceBeans));

			JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);

		}
		catch (JRException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

	}

	private void miWriteOffInvoiceClickHandler()
	{
		StringBuilder errorMsg;

		errorMsg = new StringBuilder();

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		InvoiceSer invoiceSer = new InvoiceSer(Database.getSessionCompanyYear());
		if (invoiceSer.writeOff(myTableModel.getInvoiceDTOAt(tblParent.getSelectedRow()),
				errorMsg
		))
		{
			CommonUIActions.showMessageBox("Data saved successfully");
			mLoadDataParent(invoiceDTOs, getInvoiceType());
		}
		else
		{
			CommonUIActions.showMessageBox(errorMsg.toString());
			return;
		}

	}

	private void fillCountBar()
	{
		lblCount.setText("");

		int sumInvoiceAmount = 0;
		int sumReceivedAmountCash = 0;
		int sumReceivedAmountWriteOff = 0;
		int sumDueAmount = 0;
		int countInvoice = invoiceDTOsInTable.size();
		int countReceivedAmountCash = 0;
		int countReceivedAmountWriteOff = 0;
		int countDueAmount = 0;

		for (int i = 0; i < amountDTOsInTable.size(); i++)
		{
			sumInvoiceAmount += invoiceDTOsInTable.get(i).getAmount();
			sumReceivedAmountCash += amountDTOsInTable.get(i).amtRcvdCash;
			sumReceivedAmountWriteOff += amountDTOsInTable.get(i).amtRcvdWriteOff;
			sumDueAmount += amountDTOsInTable.get(i).amtDue;

			if (amountDTOsInTable.get(i).amtRcvdCash > 0)
			{
				countReceivedAmountCash++;
			}

			if (amountDTOsInTable.get(i).amtRcvdWriteOff > 0)
			{
				countReceivedAmountWriteOff++;
			}

			if (amountDTOsInTable.get(i).amtDue > 0)
			{
				countDueAmount++;
			}
		}

		lblCount.setText(lblCount.getText() + "" + "Total | ");
		lblCount.setText(lblCount.getText() + "" + "Amount : " + sumInvoiceAmount);
		lblCount.setText(lblCount.getText() + "        " + "Cash: " + sumReceivedAmountCash);
		lblCount.setText(lblCount.getText() + "        " + "Write Off: " + sumReceivedAmountWriteOff);
		lblCount.setText(lblCount.getText() + "        " + "Due : " + sumDueAmount);

		lblCount.setText(lblCount.getText() + "        " + "Count | ");
		lblCount.setText(lblCount.getText() + "" + "Invoice : " + countInvoice);
		lblCount.setText(lblCount.getText() + "        " + "Cash: " + countReceivedAmountCash);
		lblCount.setText(lblCount.getText() + "        " + "Write Off: " + countReceivedAmountWriteOff);
		lblCount.setText(lblCount.getText() + "        " + "Due : " + countDueAmount);
	}

	@Override
	public void refreshData()
	{
		handleClickBtnSearch();
	}

	private void handleClickBtnBrowser()
	{
		seleniumHelper = GV.getSeleniumHelper();
		driver = seleniumHelper.getDriver();

		driver.get("https://web.whatsapp.com/");

	}

	private void handleClickMiMessageSelected()
	{
		SMSTemplateDialog dlg = new SMSTemplateDialog(null, true);

		if (!dlg.run())
		{
			return;
		}

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!confirm())
		{
			return;
		}

		List<Integer> selectedIndices = new ArrayList<>();
		for (int i : tblParent.getSelectedRows())
		{
			selectedIndices.add(i);
		}

		sendMessage(selectedIndices, dlg.getMessage());
	}

	private void handleClickMiMessageChecked()
	{
		SMSTemplateDialog dlg = new SMSTemplateDialog(null, true);

		if (!dlg.run())
		{
			return;
		}

		List<Integer> checkedIndices = new ArrayList<>();
		for (int i = 0; i < myTableModel.getRowCount(); i++)
		{
			if (myTableModel.isChecked(i))
			{
				checkedIndices.add(i);
			}
		}

		if (checkedIndices.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "No checked rows", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (!confirm())
		{
			return;
		}

		sendMessage(checkedIndices, dlg.getMessage());
	}

	private void handleClickMiSMSSelected()
	{

		SMSTemplateDialog dlg = new SMSTemplateDialog(null, true);

		if (!dlg.run())
		{
			return;
		}

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!confirm())
		{
			return;
		}

		List<Integer> selectedIndices = new ArrayList<>();
		for (int i : tblParent.getSelectedRows())
		{
			selectedIndices.add(i);
		}

		sendSMS(selectedIndices, dlg.getMessage());
	}

	private void handleClickMiSMSChecked()
	{
		SMSTemplateDialog dlg = new SMSTemplateDialog(null, true);

		if (!dlg.run())
		{
			return;
		}

		List<Integer> checkedIndices = new ArrayList<>();
		for (int i = 0; i < myTableModel.getRowCount(); i++)
		{
			if (myTableModel.isChecked(i))
			{
				checkedIndices.add(i);
			}
		}

		if (checkedIndices.size() == 0)
		{
			JOptionPane.showMessageDialog(null, "No checked rows", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (!confirm())
		{
			return;
		}

		sendSMS(checkedIndices, dlg.getMessage());
	}

	private void handleClickMiSelectCustomer()
	{
		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			pnlListCustomer abc = new pnlListCustomer(vParent);
			abc.selectCustomer(myTableModel.getInvoiceDTOAt(tblParent.getSelectedRow()).getCustomer());
			this.vParent.addPanel(abc);
		}
	}

	private boolean confirm()
	{
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}

	private void sendMessage(List<Integer> checkedIndices, String messageTemplate)
	{

		String abc[][] = new String[checkedIndices.size()][6];
		int rowIndex = 0;

		for (int i : checkedIndices)
		{
			InvoiceDTO invoiceSelected = myTableModel.getInvoiceDTOAt(i);

			boolean sent = sendMessage(invoiceSelected, messageTemplate);

			int colIndex = 0;
			abc[rowIndex][colIndex] = String.valueOf(invoiceSelected.getCustomer().getId());
			abc[rowIndex][colIndex++] = String.valueOf(invoiceSelected.getCustomer().getId());
			abc[rowIndex][colIndex++] = String.valueOf(invoiceSelected.getCustomer().getCustomerName());
			abc[rowIndex][colIndex++] = String.valueOf(invoiceSelected.getCustomer().getHouseNo1())
					+ " " + String.valueOf(invoiceSelected.getCustomer().getHouseNo2())
					+ " " + String.valueOf(invoiceSelected.getCustomer().getHouseNo3())
					+ " " + String.valueOf(invoiceSelected.getCustomer().getArea().getName());
			abc[rowIndex][colIndex++] = String.valueOf(invoiceSelected.getCustomer().getMobile());
			abc[rowIndex][colIndex++] = sent ? "sent" : "not sent";

			rowIndex++;

		}

		try
		{
			ExcelHandler.createExcel(abc);
		}
		catch (Exception e)
		{
		}

		//mLoadData();
		JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

	}

	private void sendSMS(List<Integer> checkedIndices, String messageTemplate)
	{
		/*
		List<SMSDTOPhone> smsDTOs = new ArrayList<>();

		for (int i : checkedIndices)
		{
			InvoiceDTO invoiceSelected = myTableModel.getInvoiceDTOAt(i);

			CustomerDTO customerDTO = invoiceSelected.getCustomer();
			List<InvoiceDTO> invoiceDTOs = customerDTO.getInvoices();
			List<ReceiptDTO> receiptDTOs = customerDTO.getReceipts();

			if (customerDTO.getMobile() == null
					|| customerDTO.getMobile().length() != 10
					|| !Util.isNumeric(customerDTO.getMobile()))
			{
				continue;
			}

			int sumPrevInvoices = 0;
			int sumPrevReceipts = 0;
			int prevDue = 0;

			for (InvoiceDTO invoiceDTOtemp : invoiceDTOs)
			{
				if (invoiceDTOtemp.getId() != invoiceSelected.getId())
				{
					sumPrevInvoices += invoiceDTOtemp.getAmount();
				}
			}

			for (ReceiptDTO receiptDTOtemp : receiptDTOs)
			{
				sumPrevReceipts += receiptDTOtemp.getAmount();
			}

			prevDue = sumPrevInvoices - sumPrevReceipts;

			Map map = new HashMap();
			map.put("customer_name", customerDTO.getCustomerName());
			map.put("prev_due", String.valueOf(prevDue));
			map.put("current_month_charges", String.valueOf(invoiceSelected.getAmount()));
			map.put("total_amt", String.valueOf(invoiceSelected.getAmount() + prevDue));
			map.put("currentMonthName", String.valueOf(Util.getMonthName(
					Calendar.getInstance().get(Calendar.MONTH) + 1
			)));

			String messageTemp = Util.formatString(messageTemplate, map);

			smsDTOs.add(new SMSDTOPhone(invoiceSelected.getCustomer().getMobile(), messageTemp));

		}
		GV.getServer().sendSMS(smsDTOs);

		JOptionPane.showMessageDialog(null, "Done", "Message", JOptionPane.INFORMATION_MESSAGE);
		*/
	}

	private boolean sendMessage(InvoiceDTO invoiceDTO, String messageTemplate)
	{

		CustomerDTO customerDTO = invoiceDTO.getCustomer();
		List<InvoiceDTO> invoiceDTOs = customerDTO.getInvoices();
		List<ReceiptDTO> receiptDTOs = customerDTO.getReceipts();

		if (customerDTO.getMobile() == null || customerDTO.getMobile().length() != 10 || !Util.isNumeric(customerDTO.getMobile()))
		{
			return false;
		}

		int sumPrevInvoices = 0;
		int sumPrevReceipts = 0;
		int prevDue = 0;
		int prevAdvancePayment = 0;

		for (InvoiceDTO invoiceDTOtemp : invoiceDTOs)
		{
			if (invoiceDTOtemp.getId() != invoiceDTO.getId())
			{
				sumPrevInvoices += invoiceDTOtemp.getAmount();
			}
		}

		for (ReceiptDTO receiptDTOtemp : receiptDTOs)
		{
			sumPrevReceipts += receiptDTOtemp.getAmount();
		}

		prevDue = sumPrevInvoices - sumPrevReceipts;

		WebElement element;

		element = seleniumHelper.findElement(By.cssSelector("#side > header > div._20NlL > div > span > div:nth-child(2) > div"));
		element.click();
		seleniumHelper.wait(1);

		element = seleniumHelper.findElement(By.cssSelector("#app > div > div > div.MZIyP > div._3q4NP.k1feT > span > div > span > div > div:nth-child(2) > div > label > input"));

		//element.click();
		//seleniumHelper.wait(1);
		element.sendKeys(String.valueOf(customerDTO.getMobile()));
		seleniumHelper.wait(1);

		element = seleniumHelper.findElement(By.cssSelector("#app > div > div > div.MZIyP > div._3q4NP.k1feT > span > div > span > div > div._2sNbV > div > div > div > div"), 1);

		if (element == null)
		{

			element = seleniumHelper.findElement(By.cssSelector("#app > div > div > div.MZIyP > div._3q4NP.k1feT > span > div > span > div > header > div > div.SFEHG > button"));
			element.click();
			seleniumHelper.wait(1);

			return false;
		}

		element.click();
		seleniumHelper.wait(1);

		element = seleniumHelper.findElement(By.cssSelector("#main > footer > div._3oju3 > div._2bXVy > div > div._2S1VP.copyable-text.selectable-text"));

		Map map = new HashMap();
		map.put("customerName", customerDTO.getCustomerName());
		map.put("prevDue", String.valueOf(prevDue));
		map.put("currentMonthCharges", String.valueOf(invoiceDTO.getAmount()));
		map.put("totalAmt", String.valueOf(invoiceDTO.getAmount() + prevDue));

		messageTemplate = Util.formatString(messageTemplate, map);

		messageTemplate = messageTemplate.replace("\n", Keys.chord(Keys.SHIFT, Keys.ENTER));
		element.sendKeys(messageTemplate + "\n");
		seleniumHelper.wait(1);

		//element = seleniumHelper.findElement(By.cssSelector("#main > footer > div._3oju3 > button"));
		//element.click();
		//seleniumHelper.wait(1);
		return true;

	}

	private invoiceTypes getInvoiceType()
	{

		if (cmbInvoiceType.getSelectedItem().equals("PAID"))
		{
			return invoiceTypes.PAID;
		}
		else if (cmbInvoiceType.getSelectedItem().equals("UNPAID"))
		{
			return invoiceTypes.UNPAID;
		}
		else if (cmbInvoiceType.getSelectedItem().equals("OVERPAID"))
		{
			return invoiceTypes.OVERPAID;
		}

		return invoiceTypes.ALL;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
        private void initComponents()
        {

                fileChooser = new javax.swing.JFileChooser();
                pmTblInvoice = new javax.swing.JPopupMenu();
                miAdd = new javax.swing.JMenuItem();
                miEdit = new javax.swing.JMenuItem();
                miDelete = new javax.swing.JMenuItem();
                jSeparator5 = new javax.swing.JPopupMenu.Separator();
                miWriteOff = new javax.swing.JMenuItem();
                jSeparator4 = new javax.swing.JPopupMenu.Separator();
                miSelectCustomer = new javax.swing.JMenuItem();
                miC2E = new javax.swing.JMenuItem();
                jSeparator3 = new javax.swing.JPopupMenu.Separator();
                miCheckAll = new javax.swing.JMenuItem();
                miUnCheckAll = new javax.swing.JMenuItem();
                jSeparator2 = new javax.swing.JPopupMenu.Separator();
                miCheckSelected = new javax.swing.JMenuItem();
                miUncheckSelected = new javax.swing.JMenuItem();
                jSeparator1 = new javax.swing.JPopupMenu.Separator();
                miMessageSelected = new javax.swing.JMenuItem();
                miMessageChecked = new javax.swing.JMenuItem();
                jSeparator6 = new javax.swing.JPopupMenu.Separator();
                miSMSSelected = new javax.swing.JMenuItem();
                miSMSChecked = new javax.swing.JMenuItem();
                btnSearch = new javax.swing.JButton();
                btnAll = new javax.swing.JButton();
                jLabel1 = new javax.swing.JLabel();
                txtNote = new javax.swing.JTextField();
                jLabel7 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                tblParent = new javax.swing.JTable();
                cmbMonthYear = new javax.swing.JComboBox<>();
                btnAll1 = new javax.swing.JButton();
                btnMapAuto = new javax.swing.JButton();
                btnGeneratePDF = new javax.swing.JButton();
                btnGeneratePDF1 = new javax.swing.JButton();
                jLabel8 = new javax.swing.JLabel();
                jPanel3 = new javax.swing.JPanel();
                lblCount = new javax.swing.JLabel();
                btnBrowser = new javax.swing.JButton();
                cmbInvoiceType = new javax.swing.JComboBox<>();

                fileChooser.setAcceptAllFileFilterUsed(false);

                miAdd.setText("Add");
                miAdd.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miAddActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miAdd);

                miEdit.setText("Edit");
                miEdit.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miEditActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miEdit);

                miDelete.setText("Delete Selected");
                miDelete.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miDeleteActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miDelete);
                pmTblInvoice.add(jSeparator5);

                miWriteOff.setText("Write Off");
                miWriteOff.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miWriteOffActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miWriteOff);
                pmTblInvoice.add(jSeparator4);

                miSelectCustomer.setText("Select Customer");
                miSelectCustomer.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miSelectCustomerActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miSelectCustomer);

                miC2E.setText("Convert To Excel");
                miC2E.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miC2EActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miC2E);
                pmTblInvoice.add(jSeparator3);

                miCheckAll.setText("Tick All");
                miCheckAll.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miCheckAllActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miCheckAll);

                miUnCheckAll.setText("UnTick All");
                miUnCheckAll.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miUnCheckAllActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miUnCheckAll);
                pmTblInvoice.add(jSeparator2);

                miCheckSelected.setText("Tick Selected");
                miCheckSelected.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miCheckSelectedActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miCheckSelected);

                miUncheckSelected.setText("UnTick Selected");
                miUncheckSelected.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miUncheckSelectedActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miUncheckSelected);
                pmTblInvoice.add(jSeparator1);

                miMessageSelected.setText("Message Selected");
                miMessageSelected.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miMessageSelectedActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miMessageSelected);

                miMessageChecked.setText("Message Ticked");
                miMessageChecked.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miMessageCheckedActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miMessageChecked);
                pmTblInvoice.add(jSeparator6);

                miSMSSelected.setText("SMS Selected");
                miSMSSelected.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miSMSSelectedActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miSMSSelected);

                miSMSChecked.setText("SMS Ticked");
                miSMSChecked.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                miSMSCheckedActionPerformed(evt);
                        }
                });
                pmTblInvoice.add(miSMSChecked);

                btnSearch.setText("Search");
                btnSearch.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnSearchActionPerformed(evt);
                        }
                });

                btnAll.setText("All");
                btnAll.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnAllActionPerformed(evt);
                        }
                });

                jLabel1.setText("Month");

                jLabel7.setText("Note");

                jScrollPane1.setComponentPopupMenu(pmTblInvoice);

                tblParent.setModel(new javax.swing.table.DefaultTableModel(
                        new Object [][]
                        {
                                {},
                                {},
                                {},
                                {}
                        },
                        new String []
                        {

                        }
                ));
                tblParent.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                tblParent.setComponentPopupMenu(pmTblInvoice);
                jScrollPane1.setViewportView(tblParent);

                btnAll1.setText("Generate");
                btnAll1.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnAll1ActionPerformed(evt);
                        }
                });

                btnMapAuto.setText("Map Automatically");
                btnMapAuto.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnMapAutoActionPerformed(evt);
                        }
                });

                btnGeneratePDF.setText("Generate PDF");
                btnGeneratePDF.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnGeneratePDFActionPerformed(evt);
                        }
                });

                btnGeneratePDF1.setText("Generate PDF 8");
                btnGeneratePDF1.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnGeneratePDF1ActionPerformed(evt);
                        }
                });

                jLabel8.setText("Amt Received");

                lblCount.setText("jLabel2");

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblCount)
                                .addGap(0, 0, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblCount)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                btnBrowser.setText("Browser");
                btnBrowser.addActionListener(new java.awt.event.ActionListener()
                {
                        public void actionPerformed(java.awt.event.ActionEvent evt)
                        {
                                btnBrowserActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(cmbMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addComponent(jLabel1)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel8)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(cmbInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(btnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                                .addComponent(btnBrowser)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnMapAuto)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnGeneratePDF)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnGeneratePDF1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAll1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane1)))
                                .addContainerGap())
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel1)
                                                        .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnSearch)
                                                        .addComponent(btnAll)
                                                        .addComponent(cmbMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cmbInvoiceType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(btnAll1)
                                                .addComponent(btnMapAuto)
                                                .addComponent(btnGeneratePDF)
                                                .addComponent(btnGeneratePDF1)
                                                .addComponent(btnBrowser))
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0))
                );
        }// </editor-fold>//GEN-END:initComponents

        private void btnSearchActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSearchActionPerformed
        {//GEN-HEADEREND:event_btnSearchActionPerformed
			// TODO add your handling code here:
			handleClickBtnSearch();
        }//GEN-LAST:event_btnSearchActionPerformed

        private void btnAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAllActionPerformed
        {//GEN-HEADEREND:event_btnAllActionPerformed
			// TODO add your handling code here:
			handleClickBtnAll();
        }//GEN-LAST:event_btnAllActionPerformed

        private void btnAll1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAll1ActionPerformed
        {//GEN-HEADEREND:event_btnAll1ActionPerformed
			// TODO add your handling code here:
			handleClickBtnGenerate();
        }//GEN-LAST:event_btnAll1ActionPerformed

        private void btnMapAutoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnMapAutoActionPerformed
        {//GEN-HEADEREND:event_btnMapAutoActionPerformed
			// TODO add your handling code here:
			handleClickBtnMapAuto();
        }//GEN-LAST:event_btnMapAutoActionPerformed

        private void btnGeneratePDFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnGeneratePDFActionPerformed
        {//GEN-HEADEREND:event_btnGeneratePDFActionPerformed
			// TODO add your handling code here:
			handleClickBtnGeneratePDF();
        }//GEN-LAST:event_btnGeneratePDFActionPerformed

        private void btnGeneratePDF1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnGeneratePDF1ActionPerformed
        {//GEN-HEADEREND:event_btnGeneratePDF1ActionPerformed
			// TODO add your handling code here:
			handleClickBtnGeneratePDF8();
        }//GEN-LAST:event_btnGeneratePDF1ActionPerformed

        private void miAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddActionPerformed
        {//GEN-HEADEREND:event_miAddActionPerformed
			// TODO add your handling code here:
        }//GEN-LAST:event_miAddActionPerformed

        private void miEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miEditActionPerformed
        {//GEN-HEADEREND:event_miEditActionPerformed
			// TODO add your handling code here:
			handleClickBtnEdit();
        }//GEN-LAST:event_miEditActionPerformed

        private void miDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDeleteActionPerformed
        {//GEN-HEADEREND:event_miDeleteActionPerformed
			// TODO add your handling code here:
			handleClickBtnDelete();
        }//GEN-LAST:event_miDeleteActionPerformed

        private void miWriteOffActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miWriteOffActionPerformed
        {//GEN-HEADEREND:event_miWriteOffActionPerformed
			// TODO add your handling code here:
			miWriteOffInvoiceClickHandler();
        }//GEN-LAST:event_miWriteOffActionPerformed

        private void miC2EActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miC2EActionPerformed
        {//GEN-HEADEREND:event_miC2EActionPerformed
			// TODO add your handling code here:
			handleClickBtnConvert();
        }//GEN-LAST:event_miC2EActionPerformed

        private void btnBrowserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBrowserActionPerformed
        {//GEN-HEADEREND:event_btnBrowserActionPerformed
			handleClickBtnBrowser();
        }//GEN-LAST:event_btnBrowserActionPerformed

        private void miCheckAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miCheckAllActionPerformed
        {//GEN-HEADEREND:event_miCheckAllActionPerformed
			// TODO add your handling code here:
			handleClickMiCheckAll();
        }//GEN-LAST:event_miCheckAllActionPerformed

        private void miUnCheckAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miUnCheckAllActionPerformed
        {//GEN-HEADEREND:event_miUnCheckAllActionPerformed
			// TODO add your handling code here:
			handleClickMiUnCheckAll();
        }//GEN-LAST:event_miUnCheckAllActionPerformed

        private void miCheckSelectedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miCheckSelectedActionPerformed
        {//GEN-HEADEREND:event_miCheckSelectedActionPerformed
			// TODO add your handling code here:
			handleClickMiCheckSelected();
        }//GEN-LAST:event_miCheckSelectedActionPerformed

        private void miUncheckSelectedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miUncheckSelectedActionPerformed
        {//GEN-HEADEREND:event_miUncheckSelectedActionPerformed
			// TODO add your handling code here:
			handleClickMiUnCheckSelected();
        }//GEN-LAST:event_miUncheckSelectedActionPerformed

        private void miMessageSelectedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miMessageSelectedActionPerformed
        {//GEN-HEADEREND:event_miMessageSelectedActionPerformed
			// TODO add your handling code here:
			handleClickMiMessageSelected();
        }//GEN-LAST:event_miMessageSelectedActionPerformed

        private void miMessageCheckedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miMessageCheckedActionPerformed
        {//GEN-HEADEREND:event_miMessageCheckedActionPerformed
			// TODO add your handling code here:
			handleClickMiMessageChecked();
        }//GEN-LAST:event_miMessageCheckedActionPerformed

        private void miSelectCustomerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSelectCustomerActionPerformed
        {//GEN-HEADEREND:event_miSelectCustomerActionPerformed
			// TODO add your handling code here:
			handleClickMiSelectCustomer();
        }//GEN-LAST:event_miSelectCustomerActionPerformed

        private void miSMSSelectedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSMSSelectedActionPerformed
        {//GEN-HEADEREND:event_miSMSSelectedActionPerformed
			handleClickMiSMSSelected();
        }//GEN-LAST:event_miSMSSelectedActionPerformed

        private void miSMSCheckedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSMSCheckedActionPerformed
        {//GEN-HEADEREND:event_miSMSCheckedActionPerformed
			handleClickMiSMSChecked();
        }//GEN-LAST:event_miSMSCheckedActionPerformed


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnAll;
        private javax.swing.JButton btnAll1;
        private javax.swing.JButton btnBrowser;
        private javax.swing.JButton btnGeneratePDF;
        private javax.swing.JButton btnGeneratePDF1;
        private javax.swing.JButton btnMapAuto;
        private javax.swing.JButton btnSearch;
        private javax.swing.JComboBox<String> cmbInvoiceType;
        private javax.swing.JComboBox<String> cmbMonthYear;
        private javax.swing.JFileChooser fileChooser;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JPopupMenu.Separator jSeparator1;
        private javax.swing.JPopupMenu.Separator jSeparator2;
        private javax.swing.JPopupMenu.Separator jSeparator3;
        private javax.swing.JPopupMenu.Separator jSeparator4;
        private javax.swing.JPopupMenu.Separator jSeparator5;
        private javax.swing.JPopupMenu.Separator jSeparator6;
        private javax.swing.JLabel lblCount;
        private javax.swing.JMenuItem miAdd;
        private javax.swing.JMenuItem miC2E;
        private javax.swing.JMenuItem miCheckAll;
        private javax.swing.JMenuItem miCheckSelected;
        private javax.swing.JMenuItem miDelete;
        private javax.swing.JMenuItem miEdit;
        private javax.swing.JMenuItem miMessageChecked;
        private javax.swing.JMenuItem miMessageSelected;
        private javax.swing.JMenuItem miSMSChecked;
        private javax.swing.JMenuItem miSMSSelected;
        private javax.swing.JMenuItem miSelectCustomer;
        private javax.swing.JMenuItem miUnCheckAll;
        private javax.swing.JMenuItem miUncheckSelected;
        private javax.swing.JMenuItem miWriteOff;
        private javax.swing.JPopupMenu pmTblInvoice;
        private javax.swing.JTable tblParent;
        private javax.swing.JTextField txtNote;
        // End of variables declaration//GEN-END:variables
}
