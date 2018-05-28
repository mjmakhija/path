package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import com.emsays.path.DBHelperSqlite;
import com.emsays.path.Database;
import com.emsays.path.ExcelHandler;
import com.emsays.path.GV;
import com.emsays.path.SeleniumHelper;
import com.emsays.path.Util;
import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.ReceiptSer;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.MonthYearDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class pnlListReceipt extends javax.swing.JPanel implements MyPanelBack
{

	private class AmountDTO
	{

		int amtUsed;
		int amtUnused;
	}

	private class MyTableModel2 extends AbstractTableModel
	{

		private List<ReceiptDTO> receiptDTOs = new ArrayList<>();
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

		public MyTableModel2(String[] columnNames, List<ReceiptDTO> receipts, List<AmountDTO> amountDTOs)
		{
			this.receiptDTOs = receipts;
			this.amountDTOs = amountDTOs;
			this.columnNames = columnNames;
			checked = new boolean[receipts.size()];
		}

		public void updateData(List<ReceiptDTO> receipts, List<AmountDTO> amountDTOs)
		{
			this.receiptDTOs = receipts;
			this.amountDTOs = amountDTOs;
			checked = new boolean[receipts.size()];
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
			return receiptDTOs.size();
		}

		@Override
		public int getColumnCount()
		{
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{

			ReceiptDTO receipt = receiptDTOs.get(rowIndex);
			AmountDTO amountDTO = amountDTOs.get(rowIndex);
			switch (columnIndex)
			{
				case 0:
					return checked[rowIndex];
				case 1:
					return rowIndex + 1;
				case 2:
					return receipt.getId();
				case 3:
					return receipt.getCustomer().getId();
				case 4:
					return receipt.getCustomer().getAccountNo();
				case 5:
					return receipt.getCustomer().getCustomerName();
				case 6:
					return receipt.getCustomer().getHouseNo1()
							+ (receipt.getCustomer().getHouseNo2().equals("") ? "" : "-" + receipt.getCustomer().getHouseNo2())
							+ (receipt.getCustomer().getHouseNo3().equals("") ? "" : "-" + receipt.getCustomer().getHouseNo3())
							+ " " + receipt.getCustomer().getArea().getName();
				case 7:
					return Util.mGetLocalDateTime(receipt.getDate());
				case 8:
					return receipt.getAmount();
				case 9:
					return receipt.getNote();
				case 10:
					return amountDTO.amtUsed;
				case 11:
					return amountDTO.amtUnused;
				default:
					return null;
			}

		}

		public ReceiptDTO getReceiptDTOAt(int i)
		{
			return receiptDTOs.get(i);
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

	List<ReceiptDTO> receiptDTOs = new ArrayList<>();
	List<AmountDTO> amountDTOs = new ArrayList<>();
	List<ReceiptDTO> receiptDTOsInTable = new ArrayList<>();
	List<AmountDTO> amountDTOsInTable = new ArrayList<>();

	ReceiptSer receiptSer;
	String[] vColumnNamesParent =
	{
		"",
		"#",
		"Id",
		"Customer Id",
		"Account No",
		"Customer Name",
		"Address",
		"Date",
		"Amount",
		"Note",
		"Amt Used",
		"Amt Unused",
	};

	List<MonthYearDTO> monthYears;
	int checkColForColorIndex = vColumnNamesParent.length - 1;

	private SeleniumHelper seleniumHelper = null;
	private WebDriver driver = null;

	private MyTableModel2 myTableModel;

	public pnlListReceipt(MyFormWrapper vParent)
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

		receiptSer = new ReceiptSer(Database.getSessionCompanyYear());

		monthYears = receiptSer.getMonthYear();

		for (MonthYearDTO monthYear : monthYears)
		{
			cmbMonthYear.addItem(monthYear.getMonth() + " - " + monthYear.getYear());
		}

		this.vParent = vParent;
	}

	private void mLoadDataParent(List<ReceiptDTO> vLstObjReceipt)
	{
		this.amountDTOs.clear();
		this.receiptDTOs = vLstObjReceipt;

		for (ReceiptDTO vObjReceipt : vLstObjReceipt)
		{
			AmountDTO amountDTO = new AmountDTO();
			for (InvoiceReceiptDTO invoice : vObjReceipt.getInvoices())
			{
				amountDTO.amtUsed += invoice.getAmount();
			}
			amountDTO.amtUnused = vObjReceipt.getAmount() - amountDTO.amtUsed;
			amountDTOs.add(amountDTO);
		}

		this.receiptDTOsInTable.clear();
		this.amountDTOsInTable.clear();

		receiptDTOsInTable.addAll(receiptDTOs);
		amountDTOsInTable.addAll(amountDTOs);

		myTableModel.updateData(receiptDTOsInTable, amountDTOsInTable);

		Util.resizeColumnWidth(tblParent);

		fillCountBar();

	}

	private void clearBoxes()
	{
		txtDateFrom.setText(null);
		txtDateTo.setText(null);
	}

	private void handleClickBtnSearch()
	{

		MonthYearDTO selectedMonthYear = monthYears.get(cmbMonthYear.getSelectedIndex());
		Integer month = selectedMonthYear.getMonth();
		Integer year = selectedMonthYear.getYear();

		Date dateFrom = null;
		Date dateTo = null;

		if (txtDateFrom.getText() != null
				&& !txtDateFrom.getText().equals("")
				&& Util.isDateValid(txtDateFrom.getText()))
		{
			dateFrom = Util.mConvertStringToDate2(txtDateFrom.getText());

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dateFrom.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			dateFrom = new Date(cal.getTimeInMillis());
		}

		if (txtDateTo.getText() != null
				&& !txtDateTo.getText().equals("")
				&& Util.isDateValid(txtDateTo.getText()))
		{
			dateTo = Util.mConvertStringToDate2(txtDateTo.getText());

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(dateTo.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			dateTo = new Date(cal.getTimeInMillis());

		}

		this.mLoadDataParent(receiptSer.retrieve(month, year, null, null, dateFrom, dateTo, null, AccountSer.enmKeys.NA));
	}

	private void handleClickBtnAll()
	{
		clearBoxes();

		handleClickBtnSearch();
	}

	private void mBtnMenuClickHandler()
	{
		//vParent.addPanel(new pnlMenu(vParent));
	}

	private void handleMIEdtiReceiptClick()
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

		ReceiptDTO receipt = receiptDTOs.get(tblParent.getSelectedRow());

		this.vParent.addPanel(new pnlAddReceipt(vParent, this, receipt));
	}

	private void mBtnImportClickHandler()
	{

		/*
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SQlite Database File", "db");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION)
		{
			File file = new File(fileChooser.getSelectedFile().getPath());

			if (!file.exists())
			{

				JOptionPane.showMessageDialog(null, "Invalid file", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				List<ReceiptDTOPhone> receipts = new DBHelperSqlite(file.getAbsolutePath()).getReceipts();

				if (receiptSer.create(receipts))
				{
					JOptionPane.showMessageDialog(null, "Data imported successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Some error occured when importing data", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		}
		 */
	}

	private void mBtnExportClickHandler()
	{
		/*
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SQlite Database File", "db");
		fileChooser.setFileFilter(filter);

		if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION)
		{
			File file = new File(fileChooser.getSelectedFile().getPath());

			if (!file.exists())
			{

				JOptionPane.showMessageDialog(null, "Invalid file", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{

				if (new DBHelperSqlite(file.getAbsolutePath()).generateDB(new InvoiceSer(Database.getSessionCompanyYear()).mSearch(null, null, Integer.parseInt(cmbPaymentType.getSelectedItem().toString()), null), receiptSer.mSearch(null, null, null, null, null, null, null)))
				{
					JOptionPane.showMessageDialog(null, "Data imported successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Some error occured when importing data", "Message", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		}
		 */
	}

	private void handleMIDeleteReceiptClick()
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
				int[] vSelectedIndices = tblParent.getSelectedRows();
				for (int i : vSelectedIndices)
				{
					receiptSer.delete(receiptDTOs.get(i));
				}
				//mLoadData();
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				handleClickBtnSearch();
			}
		}

	}

	private void handleMISelectCustomerClick()
	{

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			pnlListCustomer abc = new pnlListCustomer(vParent);
			abc.selectCustomer(receiptDTOs.get(tblParent.getSelectedRow()).getCustomer());
			this.vParent.addPanel(abc);
		}

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

	private void handleClickMIC2E()
	{
		CommonUIActions.convertToExcelEventHandler(tblParent);
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

	private void sendMessage(List<Integer> checkedIndices, String messageTemplate)
	{

		String abc[][] = new String[checkedIndices.size()][6];
		int rowIndex = 0;

		for (int i : checkedIndices)
		{
			ReceiptDTO invoiceSelected = myTableModel.getReceiptDTOAt(i);

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
			ReceiptDTO receiptSelected = myTableModel.getReceiptDTOAt(i);

			CustomerDTO customerDTO = receiptSelected.getCustomer();

			if (customerDTO.getMobile() == null
					|| customerDTO.getMobile().length() != 10
					|| !Util.isNumeric(customerDTO.getMobile()))
			{
				continue;
			}

			Map map = new HashMap();
			map.put("customer_name", customerDTO.getCustomerName());
			map.put("amount", String.valueOf(receiptSelected.getAmount()));
			map.put("date", Util.mGetLocalDateTime(receiptSelected.getDate()));

			String messageTemp = Util.formatString(messageTemplate, map);

			smsDTOs.add(new SMSDTOPhone(receiptSelected.getCustomer().getMobile(), messageTemp));

		}
		GV.getServer().sendSMS(smsDTOs);

		JOptionPane.showMessageDialog(null, "Done", "Message", JOptionPane.INFORMATION_MESSAGE);
		 */
	}

	private boolean sendMessage(ReceiptDTO receiptDTO, String messageTemplate)
	{

		CustomerDTO customerDTO = receiptDTO.getCustomer();

		if (customerDTO.getMobile() == null || customerDTO.getMobile().length() != 10 || !Util.isNumeric(customerDTO.getMobile()))
		{
			return false;
		}

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
		map.put("customer_name", customerDTO.getCustomerName());
		map.put("amount", String.valueOf(receiptDTO.getAmount()));
		map.put("date", Util.mGetLocalDateTime(receiptDTO.getDate()));

		messageTemplate = Util.formatString(messageTemplate, map);

		messageTemplate = messageTemplate.replace("\n", Keys.chord(Keys.SHIFT, Keys.ENTER));
		element.sendKeys(messageTemplate + "\n");
		seleniumHelper.wait(1);

		//element = seleniumHelper.findElement(By.cssSelector("#main > footer > div._3oju3 > button"));
		//element.click();
		//seleniumHelper.wait(1);
		return true;

	}

	private boolean confirm()
	{
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		return dialogResult == JOptionPane.YES_OPTION;
	}

	private void fillCountBar()
	{
		lblCount.setText("");

		int sumReceiptAmount = 0;
		int sumAmountUsed = 0;
		int sumAmountUnused = 0;

		int countReceipt = receiptDTOs.size();
		int countAmountUsed = 0;
		int countAmountUnused = 0;

		for (ReceiptDTO receipt : receiptDTOs)
		{

			int tempAmountUsed = 0;
			int tempAmountUnused = 0;

			for (InvoiceReceiptDTO invoice : receipt.getInvoices())
			{
				tempAmountUsed += invoice.getAmount();
			}

			tempAmountUnused = receipt.getAmount() - tempAmountUsed;

			sumReceiptAmount += receipt.getAmount();
			sumAmountUsed += tempAmountUsed;
			sumAmountUnused += tempAmountUnused;

			if (tempAmountUsed > 0)
			{
				countAmountUsed++;
			}

			if (tempAmountUnused > 0)
			{
				countAmountUnused++;
			}

		}

		lblCount.setText(lblCount.getText() + "" + "Total | ");
		lblCount.setText(lblCount.getText() + "" + "Amount : " + sumReceiptAmount);
		lblCount.setText(lblCount.getText() + "        " + "Used : " + sumAmountUsed);
		lblCount.setText(lblCount.getText() + "        " + "Unused : " + sumAmountUnused);

		lblCount.setText(lblCount.getText() + "        " + "Count | ");
		lblCount.setText(lblCount.getText() + "" + "Receipt : " + countReceipt);
		lblCount.setText(lblCount.getText() + "        " + "Used : " + countAmountUsed);
		lblCount.setText(lblCount.getText() + "        " + "Unused : " + countAmountUnused);
	}

	private void handleClickBtnBrowser()
	{
		seleniumHelper = GV.getSeleniumHelper();
		driver = seleniumHelper.getDriver();

		driver.get("https://web.whatsapp.com/");

	}

	@Override
	public void refreshData()
	{
		handleClickBtnSearch();
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
        pmTblReceipt = new javax.swing.JPopupMenu();
        miAddReceipt = new javax.swing.JMenuItem();
        miEditReceipt = new javax.swing.JMenuItem();
        miDeleteReceipt = new javax.swing.JMenuItem();
        miC2EReceipt = new javax.swing.JMenuItem();
        miSelectCustomer = new javax.swing.JMenuItem();
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
        cmbMonthYear = new javax.swing.JComboBox<>();
        txtDateFrom = new javax.swing.JTextField();
        txtDateTo = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        btnAll = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblParent = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnImport = new javax.swing.JButton();
        btnImport1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblCount = new javax.swing.JLabel();
        btnBrowser = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        fileChooser.setAcceptAllFileFilterUsed(false);

        miAddReceipt.setText("Add");
        miAddReceipt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miAddReceiptActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miAddReceipt);

        miEditReceipt.setText("Edit");
        miEditReceipt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miEditReceiptActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miEditReceipt);

        miDeleteReceipt.setText("Delete");
        miDeleteReceipt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miDeleteReceiptActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miDeleteReceipt);

        miC2EReceipt.setText("Convert to Excel");
        miC2EReceipt.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miC2EReceiptActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miC2EReceipt);

        miSelectCustomer.setText("Select Customer");
        miSelectCustomer.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSelectCustomerActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miSelectCustomer);
        pmTblReceipt.add(jSeparator3);

        miCheckAll.setText("Tick All");
        miCheckAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miCheckAllActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miCheckAll);

        miUnCheckAll.setText("UnTick All");
        miUnCheckAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miUnCheckAllActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miUnCheckAll);
        pmTblReceipt.add(jSeparator2);

        miCheckSelected.setText("Tick Selected");
        miCheckSelected.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miCheckSelectedActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miCheckSelected);

        miUncheckSelected.setText("UnTick Selected");
        miUncheckSelected.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miUncheckSelectedActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miUncheckSelected);
        pmTblReceipt.add(jSeparator1);

        miMessageSelected.setText("Message Selected");
        miMessageSelected.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miMessageSelectedActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miMessageSelected);

        miMessageChecked.setText("Message Ticked");
        miMessageChecked.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miMessageCheckedActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miMessageChecked);
        pmTblReceipt.add(jSeparator6);

        miSMSSelected.setText("SMS Selected");
        miSMSSelected.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSMSSelectedActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miSMSSelected);

        miSMSChecked.setText("SMS Ticked");
        miSMSChecked.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSMSCheckedActionPerformed(evt);
            }
        });
        pmTblReceipt.add(miSMSChecked);

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

        jScrollPane1.setComponentPopupMenu(pmTblReceipt);

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
        tblParent.setComponentPopupMenu(pmTblReceipt);
        tblParent.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblParent);

        jLabel1.setText("Date From");

        jLabel7.setText("Date To");

        btnImport.setText("Import");
        btnImport.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnImportActionPerformed(evt);
            }
        });

        btnImport1.setText("Export");
        btnImport1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnImport1ActionPerformed(evt);
            }
        });

        lblCount.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
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

        jLabel2.setText("Month");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnBrowser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnImport1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnImport)
                        .addComponent(btnImport1)
                        .addComponent(btnBrowser))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel7)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSearch)
                            .addComponent(btnAll)
                            .addComponent(txtDateTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbMonthYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
                .addGap(36, 36, 36))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0)))
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

        private void btnImportActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnImportActionPerformed
        {//GEN-HEADEREND:event_btnImportActionPerformed
			// TODO add your handling code here:
			mBtnImportClickHandler();
        }//GEN-LAST:event_btnImportActionPerformed

        private void btnImport1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnImport1ActionPerformed
        {//GEN-HEADEREND:event_btnImport1ActionPerformed
			// TODO add your handling code here:
			mBtnExportClickHandler();
        }//GEN-LAST:event_btnImport1ActionPerformed

        private void miAddReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddReceiptActionPerformed
        {//GEN-HEADEREND:event_miAddReceiptActionPerformed
			// TODO add your handling code here:
        }//GEN-LAST:event_miAddReceiptActionPerformed

        private void miEditReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miEditReceiptActionPerformed
        {//GEN-HEADEREND:event_miEditReceiptActionPerformed
			// TODO add your handling code here:
			handleMIEdtiReceiptClick();
        }//GEN-LAST:event_miEditReceiptActionPerformed

        private void miDeleteReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDeleteReceiptActionPerformed
        {//GEN-HEADEREND:event_miDeleteReceiptActionPerformed
			// TODO add your handling code here:
			handleMIDeleteReceiptClick();
        }//GEN-LAST:event_miDeleteReceiptActionPerformed

        private void miC2EReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miC2EReceiptActionPerformed
        {//GEN-HEADEREND:event_miC2EReceiptActionPerformed
			// TODO add your handling code here:
			handleClickMIC2E();
        }//GEN-LAST:event_miC2EReceiptActionPerformed

        private void miSelectCustomerActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSelectCustomerActionPerformed
        {//GEN-HEADEREND:event_miSelectCustomerActionPerformed
			// TODO add your handling code here:
			handleMISelectCustomerClick();
        }//GEN-LAST:event_miSelectCustomerActionPerformed

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

        private void miSMSSelectedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSMSSelectedActionPerformed
        {//GEN-HEADEREND:event_miSMSSelectedActionPerformed
			handleClickMiSMSSelected();
        }//GEN-LAST:event_miSMSSelectedActionPerformed

        private void miSMSCheckedActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSMSCheckedActionPerformed
        {//GEN-HEADEREND:event_miSMSCheckedActionPerformed
			handleClickMiSMSChecked();
        }//GEN-LAST:event_miSMSCheckedActionPerformed

        private void btnBrowserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBrowserActionPerformed
        {//GEN-HEADEREND:event_btnBrowserActionPerformed
			handleClickBtnBrowser();
        }//GEN-LAST:event_btnBrowserActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnBrowser;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnImport1;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbMonthYear;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JLabel lblCount;
    private javax.swing.JMenuItem miAddReceipt;
    private javax.swing.JMenuItem miC2EReceipt;
    private javax.swing.JMenuItem miCheckAll;
    private javax.swing.JMenuItem miCheckSelected;
    private javax.swing.JMenuItem miDeleteReceipt;
    private javax.swing.JMenuItem miEditReceipt;
    private javax.swing.JMenuItem miMessageChecked;
    private javax.swing.JMenuItem miMessageSelected;
    private javax.swing.JMenuItem miSMSChecked;
    private javax.swing.JMenuItem miSMSSelected;
    private javax.swing.JMenuItem miSelectCustomer;
    private javax.swing.JMenuItem miUnCheckAll;
    private javax.swing.JMenuItem miUncheckSelected;
    private javax.swing.JPopupMenu pmTblReceipt;
    private javax.swing.JTable tblParent;
    private javax.swing.JTextField txtDateFrom;
    private javax.swing.JTextField txtDateTo;
    // End of variables declaration//GEN-END:variables
}
