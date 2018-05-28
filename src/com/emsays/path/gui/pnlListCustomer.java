package com.emsays.path.gui;

import com.emsays.path.CommonUIActions;
import com.emsays.path.Database;
import com.emsays.path.ObjectForCombobox;
import com.emsays.path.Util;
import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.AreaSer;
import com.emsays.path.dao.CustomerSer;
import com.emsays.path.dao.InvoiceSer;
import com.emsays.path.dao.ReceiptSer;
import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.CustomerChangeLogDTO;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.PopupMenuEvent;
import javax.swing.table.DefaultTableModel;

public class pnlListCustomer extends javax.swing.JPanel implements MyPanelBack
{

	MyFormWrapper vParent;
	List<CustomerDTO> vLstObjCustomer;
	String[] vColumnNamesParent =
	{
		"#",
		"Id",
		"Account No",
		"Customer Name",
		"Address",
		"Mobile",
		"Transferred",
		"Children",
	};
	String[] vColumnNamesChild =
	{
		"#",
		"Id",
		"Account No",
		"Child Name",
		"Customer Name",
		"Mobile",
		"Transferred",
	};
	String[] vColumnNamesInvoice =
	{
		"#",
		"Id",
		"Invoice No",
		"For",
		"Amount",
		"Note",
		"Created At",
		"Amount Received",
	};
	String[] vColumnNamesReceipt =
	{
		"#",
		"Id",
		"Date",
		"Amount",
		"Account",
	};
	String[] vColumnNamesLog =
	{
		"#",
		"Id",
		"Date",
		"Change In",
		"Old Value",
	};
	List<AreaDTO> areas;
	CustomerSer customerSer;
	//List<PaymentTypeDTO> paymentTypes;
	//List<PackageDTO> packages;

	/**
	 * Creates new form pnlLogin
	 */
	public pnlListCustomer(MyFormWrapper vParent)
	{
		initComponents();

		customerSer = new CustomerSer(Database.getSessionCompanyYear());
		myInit();

		this.vParent = vParent;
		handleClickBtnSearch();
	}

	private void myInit()
	{

		areas = new AreaSer(Database.getSessionCompanyYear()).retrieve();
		CommonUIActions.fillComboBox(cmbArea, new ArrayList<ObjectForCombobox>(areas));

		cmbSuspended.addItem("[SELECT]");
		cmbSuspended.addItem("YES");
		cmbSuspended.addItem("NO");
		cmbSuspended.setSelectedIndex(0);

		DefaultTableModel vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(vColumnNamesParent);
		tblParent.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(new String[]
		{
			"", ""
		});
		tblDetails.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(new String[]
		{
			"", ""
		});
		tblChildrenDetails.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(vColumnNamesChild);
		tblChildren.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(vColumnNamesInvoice);
		tblInvoice.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(vColumnNamesReceipt);
		tblReceipt.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(vColumnNamesLog);
		tblLog.setModel(vDTM);

		vDTM = getNewTableModel();
		vDTM.setColumnIdentifiers(vColumnNamesLog);
		tblChildLog.setModel(vDTM);

		tblParent.getSelectionModel().addListSelectionListener((ListSelectionEvent e) ->
		{
			if (!e.getValueIsAdjusting())
			{
				mHandleParentSelectionChange();
			}
		});

		tblChildren.getSelectionModel().addListSelectionListener((ListSelectionEvent e) ->
		{
			if (!e.getValueIsAdjusting())
			{
				mHandleChildSelectionChange();
			}
		});

	}

	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
	{
		JComboBox box = (JComboBox) e.getSource();
		Object comp = box.getUI().getAccessibleChild(box, 0);
		if (!(comp instanceof JPopupMenu))
		{
			return;
		}
		JComponent scrollPane = (JComponent) ((JPopupMenu) comp).getComponent(0);
		Dimension size = new Dimension();
		size.width = box.getPreferredSize().width;
		size.height = scrollPane.getPreferredSize().height;
		scrollPane.setPreferredSize(size);
		//  following line for Tiger
		// scrollPane.setMaximumSize(size);
	}

	private DefaultTableModel getNewTableModel()
	{
		return new DefaultTableModel()
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
	}

	private void loadCustomers(List<CustomerDTO> vLstObjCustomer)
	{
		this.vLstObjCustomer = vLstObjCustomer;

		DefaultTableModel vDTM = (DefaultTableModel) tblParent.getModel();
		vDTM.setRowCount(0);

		int i = 1;
		for (CustomerDTO vObjCustomer : vLstObjCustomer)
		{
			Object[] o = new Object[]
			{
				i++,
				vObjCustomer.getId(),
				vObjCustomer.getAccountNo(),
				vObjCustomer.getCustomerName(),
				vObjCustomer.getHouseNo1()
				+ (vObjCustomer.getHouseNo2() == null || vObjCustomer.getHouseNo2().equals("") ? "" : "-" + vObjCustomer.getHouseNo2())
				+ (vObjCustomer.getHouseNo3() == null || vObjCustomer.getHouseNo3().equals("") ? "" : "-" + vObjCustomer.getHouseNo3())
				+ " " + (vObjCustomer.getArea().getName()),
				//vObjCustomer.getLocality2(),
				//vObjCustomer.getArea(),
				vObjCustomer.getMobile(),
				//vObjCustomer.getPaymentType() == null ? null : vObjCustomer.getPaymentType().getName(),
				//vObjCustomer.getPackage().getName(),
				//vObjCustomer.isAmp() ? "AMP" : null,
				//vObjCustomer.isHome() ? "HOME" : null,
				//vObjCustomer.isRepair() ? "Repairing" : null,
				//vObjCustomer.isDc() ? "Suspend" : null,
				//vObjCustomer.getAmount(),
				//vObjCustomer.getOffer() == null ? null : vObjCustomer.getOffer().getName(),
				//vObjCustomer.getCollectFrom() == null ? null : vObjCustomer.getCollectFrom().getName(),
				//vObjCustomer.getNote(),
				//vObjCustomer.getChilds().size(),
				vObjCustomer.isSuspended() ? "Transferred" : null,
				vObjCustomer.getChilds().size()
			};
			vDTM.addRow(o);

		}

		Util.resizeColumnWidth(tblParent);

		fillCountBar();

	}

	private void mHandleParentSelectionChange()
	{
		if (tblParent.getSelectedRowCount() == 1)
		{
			CustomerDTO customer = vLstObjCustomer.get(tblParent.getSelectedRow());
			loadCustomerDetails(customer);
			loadChildren(customer.getChilds());
			loadInvoices(customer.getInvoices());
			loadReceipts(customer.getReceipts());
			loadLog(tblLog, customer.getChangeLogs());
		}

	}

	private void mHandleChildSelectionChange()
	{
		if (tblChildren.getSelectedRowCount() == 1)
		{
			CustomerDTO customer = vLstObjCustomer.get(tblParent.getSelectedRow()).getChilds().get(tblChildren.getSelectedRow());
			loadChildDetails(customer);
			loadLog(tblChildLog, customer.getChangeLogs());
		}

	}

	private void loadCustomerDetails(CustomerDTO customer)
	{

		DefaultTableModel mod = (DefaultTableModel) tblDetails.getModel();
		mod.setRowCount(0);

		FillTable fillTable = new FillTable(mod);

		fillTable.addRow("Id", customer.getId());
		fillTable.addRow("Account No", customer.getAccountNo());
		fillTable.addRow("Customer Name", customer.getCustomerName());
		fillTable.addRow("Address", customer.getHouseNo1()
			+ (customer.getHouseNo2() == null || customer.getHouseNo2().equals("") ? "" : "-" + customer.getHouseNo2())
			+ (customer.getHouseNo3() == null || customer.getHouseNo3().equals("") ? "" : "-" + customer.getHouseNo3())
			+ " " + (customer.getArea().getName())
		);
		fillTable.addRow("Mobile", customer.getMobile());
		fillTable.addRow("Payment Type",
			customer.getPaymentType() == null ? null : customer.getPaymentType().getName()
		);
		fillTable.addRow("Package", customer.getPackage().getName());
		fillTable.addRow("Amplifier", customer.isAmp() ? "AMP" : null);
		fillTable.addRow("Home", customer.isHome() ? "HOME" : null);
		fillTable.addRow("Repairing", customer.isRepair() ? "Repairing" : null);
		fillTable.addRow("Suspend", customer.isDc() ? "Suspend" : null);
		fillTable.addRow("Amount", customer.getAmount());
		fillTable.addRow("Collect From", customer.getCollectFrom() == null ? null : customer.getCollectFrom().getName());
		fillTable.addRow("Note", customer.getNote());
		fillTable.addRow("Children", customer.getChilds().size());
		fillTable.addRow("Transferred", customer.isSuspended() ? "Transferred" : null);
	}

	class FillTable
	{

		DefaultTableModel dtm;

		public FillTable(DefaultTableModel dtm)
		{
			this.dtm = dtm;
		}

		public void addRow(Object value1, Object value2)
		{
			dtm.addRow(new Object[]
			{
				value1, value2,
			});
		}
	}

	private void loadChildDetails(CustomerDTO customer)
	{

		DefaultTableModel mod = (DefaultTableModel) tblChildrenDetails.getModel();
		mod.setRowCount(0);

		FillTable fillTable = new FillTable(mod);

		fillTable.addRow("Id", customer.getId());
		fillTable.addRow("Account No", customer.getAccountNo());
		fillTable.addRow("Customer Name", customer.getCustomerName());
		fillTable.addRow("Mobile", customer.getMobile());
		fillTable.addRow("Package", customer.getPackage().getName());
		fillTable.addRow("Repairing", customer.isRepair() ? "Repairing" : null);
		fillTable.addRow("Suspend", customer.isDc() ? "Suspend" : null);
		fillTable.addRow("Amount", customer.getAmount());
		fillTable.addRow("Note", customer.getNote());
		fillTable.addRow("Transferred", customer.isSuspended() ? "Transferred" : null);

	}

	private void loadChildren(List<CustomerDTO> vLstObjCustomer)
	{
		DefaultTableModel vDTM = (DefaultTableModel) tblChildren.getModel();
		vDTM.setRowCount(0);
		int i = 1;
		for (CustomerDTO vObjCustomer : vLstObjCustomer)
		{
			Object[] o = new Object[]
			{
				i++,
				vObjCustomer.getId(),
				vObjCustomer.getAccountNo(),
				null,
				vObjCustomer.getCustomerName(),
				vObjCustomer.getMobile(),
				vObjCustomer.isSuspended() ? "Transferred" : null,
			};
			vDTM.addRow(o);
		}

		Util.resizeColumnWidth(tblChildren);
	}

	private void loadInvoices(List<InvoiceDTO> invoices)
	{
		DefaultTableModel vDTM = (DefaultTableModel) tblInvoice.getModel();
		vDTM.setRowCount(0);
		int i = 1;
		for (InvoiceDTO invoice : invoices)
		{

			int amountReceived = 0;
			for (InvoiceReceiptDTO invoiceReceipt : invoice.getReceiptes())
			{
				amountReceived += invoiceReceipt.getAmount();
			}

			Object[] o = new Object[]
			{
				i++,
				invoice.getId(),
				null,
				String.valueOf(invoice.getMonth()) + "-" + String.valueOf(invoice.getYear()),
				invoice.getAmount(),
				invoice.getNote(),
				Util.mGetLocalDateTime(invoice.getCreatedAt()),
				amountReceived
			};
			vDTM.addRow(o);
		}

		Util.resizeColumnWidth(tblInvoice);
	}

	private void loadReceipts(List<ReceiptDTO> receipts)
	{
		DefaultTableModel vDTM = (DefaultTableModel) tblReceipt.getModel();
		vDTM.setRowCount(0);
		int i = 1;
		for (ReceiptDTO receipt : receipts)
		{
			Object[] o = new Object[]
			{
				i++,
				receipt.getId(),
				Util.mGetLocalDateTime(receipt.getDate()),
				receipt.getAmount(),
				receipt.getAccount().getName().equals(AccountSer.enmKeys.WRITE_OFF.toString()) ? "Write Off" : "Cash"
			};
			vDTM.addRow(o);
		}

		Util.resizeColumnWidth(tblReceipt);
	}

	private void loadLog(JTable tbl, List<CustomerChangeLogDTO> customerChangeLogs)
	{
		DefaultTableModel vDTM = (DefaultTableModel) tbl.getModel();
		vDTM.setRowCount(0);
		int i = 1;
		for (CustomerChangeLogDTO customerChangeLog : customerChangeLogs)
		{
			Object[] o = new Object[]
			{
				i++,
				customerChangeLog.getId(),
				Util.mGetLocalDateTime(customerChangeLog.getCreatedAt()),
				customerChangeLog.getCustomerChangeType().getName(),
				customerChangeLog.getOldValue()
			};
			vDTM.addRow(o);
		}

		Util.resizeColumnWidth(tbl);
	}

	private void clearBoxes()
	{
		txtAccountNo.setText(null);
		txtCustomerName.setText(null);
	}

	private void handleClickBtnSearch()
	{
		this.loadCustomers(
			customerSer.retrieve(
				txtAccountNo.getText().equals("") ? null : txtAccountNo.getText(),
				txtCustomerName.getText().equals("") ? null : txtCustomerName.getText(),
				null,
				txtMobile.getText().equals("") ? null : txtMobile.getText(),
				null,
				null,
				null,
				null,
				null,
				null,
				cmbSuspended.getSelectedIndex() == 0 ? null : (cmbSuspended.getSelectedIndex() == 1),
				cmbArea.getSelectedIndex() == 0 ? null : (areas.get(cmbArea.getSelectedIndex() - 1))
			)
		);
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

		this.vParent.addPanel(new pnlAddCustomerOnly(vParent, this, vLstObjCustomer.get(tblParent.getSelectedRow())));

	}

	private void mBtnEditChildClickHandler()
	{
		if (tblChildren.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblParent.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		CustomerDTO parent = vLstObjCustomer.get(tblParent.getSelectedRow());
		CustomerDTO child = parent.getChilds().get(tblChildren.getSelectedRow());

		this.vParent.addPanel(new pnlAddChildOnly(vParent, this, parent, child));

	}

	private void handleClickBtnDelete()
	{

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblParent.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (!vLstObjCustomer.get(tblParent.getSelectedRow()).getInvoices().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "First delete all transactions of customer to delete, or you can mark it as suspended", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			int tableSelectedIndex = tblParent.getSelectedRow();
			CustomerDTO selectedCustomer = vLstObjCustomer.get(tblParent.getSelectedRow());
			if (customerSer.delete(vLstObjCustomer.get(tblParent.getSelectedRow())))
			{
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

				vLstObjCustomer.remove(selectedCustomer);
				loadCustomers(vLstObjCustomer);
				tableSelectedIndex = (tableSelectedIndex == 1) ? 1 : tableSelectedIndex - 1;
				if (tblParent.getRowCount() >= tableSelectedIndex)
				{
					tblParent.setRowSelectionInterval(tableSelectedIndex, tableSelectedIndex);
				}
			}

		}

	}

	private void handleClickMiSuspend()
	{

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblParent.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			StringBuilder errorMsg = new StringBuilder();
			if (!customerSer.suspend(vLstObjCustomer.get(tblParent.getSelectedRow()), errorMsg))
			{
				CommonUIActions.showMessageBox(errorMsg.toString());
			}
			else
			{
				CommonUIActions.showMessageBox("Success");
				handleClickBtnSearch();
			}

		}

	}

	private void handleClickBtnConvert()
	{
		CommonUIActions.convertToExcelEventHandler(tblParent);
	}

	private void handleClickMiAddCustomer()
	{
		vParent.addPanel(new pnlAddCustomerOnly(vParent, this));
	}

	private void handleClickMiAddInvoice()
	{
		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row from parents", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		vParent.addPanel(new pnlAddInvoice(vParent, this, this.vLstObjCustomer.get(tblParent.getSelectedRow())));
	}

	private void handleClickMiAddChild()
	{
		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row from parents", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		vParent.addPanel(new pnlAddChildOnly(vParent, this, this.vLstObjCustomer.get(tblParent.getSelectedRow())));
	}

	private void handleClickMiEditInvoice()
	{
		if (tblInvoice.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblInvoice.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		CustomerDTO parent = vLstObjCustomer.get(tblParent.getSelectedRow());
		InvoiceDTO invoice = parent.getInvoices().get(tblInvoice.getSelectedRow());

		this.vParent.addPanel(new pnlAddInvoice(vParent, this, parent, invoice));
	}

	private void handleClickMiDeleteChild()
	{
		if (tblChildren.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{

			CustomerDTO selectedCustomer;
			CustomerDTO selectedChild;

			selectedCustomer = vLstObjCustomer.get(tblParent.getSelectedRow());
			selectedChild = selectedCustomer.getChilds().get(tblChildren.getSelectedRow());

			if (customerSer.deleteChild(selectedChild))
			{
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);

				selectedCustomer.getChilds().remove(selectedChild);

				int selectedRowTableChildren = tblChildren.getSelectedRow();
				loadChildren(selectedCustomer.getChilds());
				selectedRowTableChildren = selectedRowTableChildren == 1 ? 1 : selectedRowTableChildren - 1;
				if (tblChildren.getRowCount() >= selectedRowTableChildren)
				{
					tblChildren.setRowSelectionInterval(selectedRowTableChildren, selectedRowTableChildren);
				}

			}

		}
	}

	private void handleClickMiSuspendChild()
	{

		if (tblChildren.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblChildren.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			StringBuilder errorMsg = new StringBuilder();
			if (!customerSer.suspendChild(
				vLstObjCustomer
					.get(tblParent.getSelectedRow())
					.getChilds()
					.get(tblChildren.getSelectedRow()),
				errorMsg))

			{
				CommonUIActions.showMessageBox(errorMsg.toString());
			}
			else
			{
				CommonUIActions.showMessageBox("Success");
				handleClickBtnSearch();
			}

		}

	}

	private void handleClickMiDeleteInvoice()
	{
		if (tblInvoice.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{

			CustomerDTO selectedCustomer;
			InvoiceDTO selectedInvoice;

			selectedCustomer = vLstObjCustomer.get(tblParent.getSelectedRow());
			selectedInvoice = selectedCustomer.getInvoices().get(tblInvoice.getSelectedRow());

			InvoiceSer invoiceSer = new InvoiceSer(Database.getSessionCompanyYear());

			if (invoiceSer.delete(selectedInvoice))

			{
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				selectedCustomer.getInvoices().remove(selectedInvoice);
				loadInvoices(selectedCustomer.getInvoices());
			}
		}
	}

	private void miAddReceiptClickHandler()
	{
		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row from parents", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		vParent.addPanel(new pnlAddReceipt(vParent, this, this.vLstObjCustomer.get(tblParent.getSelectedRow())));
	}

	private void miEditReceiptClickHandler()
	{
		if (tblReceipt.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblReceipt.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to edit", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		CustomerDTO parent = vLstObjCustomer.get(tblParent.getSelectedRow());
		ReceiptDTO receipt = parent.getReceipts().get(tblReceipt.getSelectedRow());

		this.vParent.addPanel(new pnlAddReceipt(vParent, this, receipt));
	}

	private void handleClickMiDeleteReceipt()
	{
		if (tblReceipt.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{

			ReceiptSer receiptSer = new ReceiptSer(Database.getSessionCompanyYear());

			CustomerDTO selectedCustomer = vLstObjCustomer.get(tblParent.getSelectedRow());
			ReceiptDTO selectedReceipt = selectedCustomer.getReceipts().get(tblReceipt.getSelectedRow());

			if (receiptSer.delete(selectedReceipt))

			{
				JOptionPane.showMessageDialog(null, "Deleted successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
				selectedCustomer.getReceipts().remove(selectedReceipt);
				loadReceipts(selectedCustomer.getReceipts());
			}

		}
	}

	private void handleClickMiWriteOffInvoice()
	{
		if (tblInvoice.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to delete", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		StringBuilder errorMsg = new StringBuilder();

		InvoiceSer invoiceSer = new InvoiceSer(Database.getSessionCompanyYear());
		if (invoiceSer.writeOff(
			vLstObjCustomer
				.get(tblParent.getSelectedRow())
				.getInvoices()
				.get(tblInvoice.getSelectedRow()),
			errorMsg
		))
		{
			JOptionPane.showMessageDialog(null, "Data saved successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			CommonUIActions.showMessageBox(errorMsg.toString());
		}

	}

	private void fillCountBar()
	{
		lblCount.setText("");

		lblCount.setText("Customer Count : " + vLstObjCustomer.size());

		int totalChild = 0;
		int countChild = 0;
		int countWithoutChild = 0;

		for (CustomerDTO customer : vLstObjCustomer)
		{
			if (customer.getChilds().size() > 0)
			{
				countChild++;
				totalChild += customer.getChilds().size();
			}
			else
			{
				countWithoutChild++;
			}
		}

		lblCount.setText(lblCount.getText() + "        " + "Customers With Child Count : " + countChild);
		lblCount.setText(lblCount.getText() + "        " + "Customers Without Child Count : " + countWithoutChild);
		lblCount.setText(lblCount.getText() + "        " + "Child Total : " + totalChild);
	}

	public void selectCustomer(CustomerDTO customer)
	{
		int index = vLstObjCustomer.indexOf(customer);
		if (index >= 0)
		{
			tblParent.setRowSelectionInterval(index, index);
		}
		scrollToVisible(tblParent, index, index);
	}

	private void handleClickMiDC()
	{

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblParent.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			StringBuilder errorMsg = new StringBuilder();
			if (!customerSer.dc(vLstObjCustomer.get(tblParent.getSelectedRow()), errorMsg))
			{
				CommonUIActions.showMessageBox(errorMsg.toString());
			}
			else
			{
				CommonUIActions.showMessageBox("Success");
				handleClickBtnSearch();
			}

		}

	}

	private void handleClickMiUnDC()
	{

		if (tblParent.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblParent.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			StringBuilder errorMsg = new StringBuilder();
			if (!customerSer.unDc(vLstObjCustomer.get(tblParent.getSelectedRow()), errorMsg))
			{
				CommonUIActions.showMessageBox(errorMsg.toString());
			}
			else
			{
				CommonUIActions.showMessageBox("Success");
				handleClickBtnSearch();
			}

		}

	}

	private void handleClickMiDCChild()
	{

		if (tblChildren.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblChildren.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			StringBuilder errorMsg = new StringBuilder();
			if (!customerSer.dcChild(
				vLstObjCustomer
					.get(tblParent.getSelectedRow())
					.getChilds()
					.get(tblChildren.getSelectedRow()),
				errorMsg))

			{
				CommonUIActions.showMessageBox(errorMsg.toString());
			}
			else
			{
				CommonUIActions.showMessageBox("Success");
				handleClickBtnSearch();
			}

		}

	}

	private void handleClickMiUnDCChild()
	{

		if (tblChildren.getSelectedRowCount() == 0)
		{
			JOptionPane.showMessageDialog(null, "Select a row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (tblChildren.getSelectedRowCount() > 1)
		{
			JOptionPane.showMessageDialog(null, "Select only one row to suspend", "Message", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure?", "Message", JOptionPane.YES_NO_OPTION);
		if (dialogResult == JOptionPane.YES_OPTION)
		{
			StringBuilder errorMsg = new StringBuilder();
			if (!customerSer.unDcChild(
				vLstObjCustomer
					.get(tblParent.getSelectedRow())
					.getChilds()
					.get(tblChildren.getSelectedRow()),
				errorMsg))

			{
				CommonUIActions.showMessageBox(errorMsg.toString());
			}
			else
			{
				CommonUIActions.showMessageBox("Success");
				handleClickBtnSearch();
			}

		}

	}

	public static void scrollToVisible(JTable table, int rowIndex, int vColIndex)
	{
		if (!(table.getParent() instanceof JViewport))
		{
			return;
		}
		JViewport viewport = (JViewport) table.getParent();

		// This rectangle is relative to the table where the
		// northwest corner of cell (0,0) is always (0,0).
		Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

		// The location of the viewport relative to the table
		Point pt = viewport.getViewPosition();

		// Translate the cell location so that it is relative
		// to the view, assuming the northwest corner of the
		// view is (0,0)
		rect.setLocation(rect.x - pt.x, rect.y - pt.y);

		table.scrollRectToVisible(rect);

		// Scroll the area into view
		//viewport.scrollRectToVisible(rect);
	}

	@Override
	public void refreshData()
	{
		Database.clearCompanyYearSession();
		if (tblParent.getSelectedRow() > -1)
		{
			int selectedRow = tblParent.getSelectedRow();
			handleClickBtnSearch();
			tblParent.setRowSelectionInterval(selectedRow, selectedRow);
		}

	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        fileChooser = new javax.swing.JFileChooser();
        pmTblParent = new javax.swing.JPopupMenu();
        miAdd = new javax.swing.JMenuItem();
        miEdit = new javax.swing.JMenuItem();
        miDelete = new javax.swing.JMenuItem();
        miC2E = new javax.swing.JMenuItem();
        miDC = new javax.swing.JMenuItem();
        miUnDC = new javax.swing.JMenuItem();
        miSuspend = new javax.swing.JMenuItem();
        pmTblChild = new javax.swing.JPopupMenu();
        miAddChild = new javax.swing.JMenuItem();
        miChildEdit = new javax.swing.JMenuItem();
        miChildDelete = new javax.swing.JMenuItem();
        miChildDC = new javax.swing.JMenuItem();
        miChildUnDC = new javax.swing.JMenuItem();
        miChildSuspend = new javax.swing.JMenuItem();
        pmTblInvoice = new javax.swing.JPopupMenu();
        miAddInvoice = new javax.swing.JMenuItem();
        miEditInvoice = new javax.swing.JMenuItem();
        miDeleteInvoice = new javax.swing.JMenuItem();
        miWriteOffInvoice = new javax.swing.JMenuItem();
        pmTblReceipt = new javax.swing.JPopupMenu();
        miAddReceipt = new javax.swing.JMenuItem();
        miEditReceipt = new javax.swing.JMenuItem();
        miDeleteReceipt = new javax.swing.JMenuItem();
        btnSearch = new javax.swing.JButton();
        btnAll = new javax.swing.JButton();
        txtAccountNo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtCustomerName = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblParent = new javax.swing.JTable();
        txtMobile = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cmbArea = new javax.swing.JComboBox<>();
        cmbSuspended = new javax.swing.JComboBox<>();
        tpCustomer = new javax.swing.JTabbedPane();
        pnlCustomerDetails = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDetails = new javax.swing.JTable();
        pnlCustomerChildren = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblChildren = new javax.swing.JTable();
        tpChildrenDetails = new javax.swing.JTabbedPane();
        pnlChildDetail = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblChildrenDetails = new javax.swing.JTable();
        pnlChildLog = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChildLog = new javax.swing.JTable();
        pnlCustomerTransaction = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblInvoice = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblReceipt = new javax.swing.JTable();
        pnlCustomerLog = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLog = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblCount = new javax.swing.JLabel();

        fileChooser.setAcceptAllFileFilterUsed(false);

        miAdd.setText("Add");
        miAdd.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miAddActionPerformed(evt);
            }
        });
        pmTblParent.add(miAdd);

        miEdit.setText("Edit");
        miEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miEditActionPerformed(evt);
            }
        });
        pmTblParent.add(miEdit);

        miDelete.setText("Delete");
        miDelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miDeleteActionPerformed(evt);
            }
        });
        pmTblParent.add(miDelete);

        miC2E.setText("Convert To Excel");
        miC2E.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miC2EActionPerformed(evt);
            }
        });
        pmTblParent.add(miC2E);

        miDC.setText("Suspend");
        miDC.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miDCActionPerformed(evt);
            }
        });
        pmTblParent.add(miDC);

        miUnDC.setText("Un Suspend");
        miUnDC.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miUnDCActionPerformed(evt);
            }
        });
        pmTblParent.add(miUnDC);

        miSuspend.setText("Transferred");
        miSuspend.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miSuspendActionPerformed(evt);
            }
        });
        pmTblParent.add(miSuspend);

        miAddChild.setText("Add");
        miAddChild.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miAddChildActionPerformed(evt);
            }
        });
        pmTblChild.add(miAddChild);

        miChildEdit.setText("Edit");
        miChildEdit.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miChildEditActionPerformed(evt);
            }
        });
        pmTblChild.add(miChildEdit);

        miChildDelete.setText("Delete");
        miChildDelete.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miChildDeleteActionPerformed(evt);
            }
        });
        pmTblChild.add(miChildDelete);

        miChildDC.setText("Suspend");
        miChildDC.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miChildDCActionPerformed(evt);
            }
        });
        pmTblChild.add(miChildDC);

        miChildUnDC.setText("Un Suspend");
        miChildUnDC.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miChildUnDCActionPerformed(evt);
            }
        });
        pmTblChild.add(miChildUnDC);

        miChildSuspend.setText("Transferred");
        miChildSuspend.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miChildSuspendActionPerformed(evt);
            }
        });
        pmTblChild.add(miChildSuspend);

        miAddInvoice.setText("Add");
        miAddInvoice.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miAddInvoiceActionPerformed(evt);
            }
        });
        pmTblInvoice.add(miAddInvoice);

        miEditInvoice.setText("Edit");
        miEditInvoice.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miEditInvoiceActionPerformed(evt);
            }
        });
        pmTblInvoice.add(miEditInvoice);

        miDeleteInvoice.setText("Delete");
        miDeleteInvoice.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miDeleteInvoiceActionPerformed(evt);
            }
        });
        pmTblInvoice.add(miDeleteInvoice);

        miWriteOffInvoice.setText("Write Off");
        miWriteOffInvoice.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                miWriteOffInvoiceActionPerformed(evt);
            }
        });
        pmTblInvoice.add(miWriteOffInvoice);

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

        jLabel1.setText("Account No");

        jLabel7.setText("Cust. Name");

        jScrollPane1.setComponentPopupMenu(pmTblParent);

        tblParent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblParent.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblParent.setComponentPopupMenu(pmTblParent);
        tblParent.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblParent);

        jLabel9.setText("Mobile");

        jLabel10.setText("Area");

        jLabel16.setText("Transferred");

        cmbArea.addPopupMenuListener(new javax.swing.event.PopupMenuListener()
        {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt)
            {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt)
            {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)
            {
                cmbAreaPopupMenuWillBecomeVisible(evt);
            }
        });

        tblDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblDetails.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblDetails);

        javax.swing.GroupLayout pnlCustomerDetailsLayout = new javax.swing.GroupLayout(pnlCustomerDetails);
        pnlCustomerDetails.setLayout(pnlCustomerDetailsLayout);
        pnlCustomerDetailsLayout.setHorizontalGroup(
            pnlCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
        );
        pnlCustomerDetailsLayout.setVerticalGroup(
            pnlCustomerDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
        );

        tpCustomer.addTab("Details", pnlCustomerDetails);

        jScrollPane5.setComponentPopupMenu(pmTblChild);

        tblChildren.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblChildren.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblChildren.setComponentPopupMenu(pmTblChild);
        tblChildren.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(tblChildren);

        tblChildrenDetails.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblChildrenDetails.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane8.setViewportView(tblChildrenDetails);

        javax.swing.GroupLayout pnlChildDetailLayout = new javax.swing.GroupLayout(pnlChildDetail);
        pnlChildDetail.setLayout(pnlChildDetailLayout);
        pnlChildDetailLayout.setHorizontalGroup(
            pnlChildDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChildDetailLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlChildDetailLayout.setVerticalGroup(
            pnlChildDetailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChildDetailLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tpChildrenDetails.addTab("Details", pnlChildDetail);

        tblChildLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblChildLog.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblChildLog.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(tblChildLog);

        javax.swing.GroupLayout pnlChildLogLayout = new javax.swing.GroupLayout(pnlChildLog);
        pnlChildLog.setLayout(pnlChildLogLayout);
        pnlChildLogLayout.setHorizontalGroup(
            pnlChildLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChildLogLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 455, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlChildLogLayout.setVerticalGroup(
            pnlChildLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlChildLogLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tpChildrenDetails.addTab("Log", pnlChildLog);

        javax.swing.GroupLayout pnlCustomerChildrenLayout = new javax.swing.GroupLayout(pnlCustomerChildren);
        pnlCustomerChildren.setLayout(pnlCustomerChildrenLayout);
        pnlCustomerChildrenLayout.setHorizontalGroup(
            pnlCustomerChildrenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(tpChildrenDetails, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        pnlCustomerChildrenLayout.setVerticalGroup(
            pnlCustomerChildrenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerChildrenLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tpChildrenDetails)
                .addGap(0, 0, 0))
        );

        tpCustomer.addTab("Children", pnlCustomerChildren);

        jScrollPane6.setComponentPopupMenu(pmTblInvoice);

        tblInvoice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblInvoice.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblInvoice.setComponentPopupMenu(pmTblInvoice);
        tblInvoice.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane6.setViewportView(tblInvoice);

        jScrollPane7.setComponentPopupMenu(pmTblReceipt);

        tblReceipt.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblReceipt.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblReceipt.setComponentPopupMenu(pmTblReceipt);
        tblReceipt.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane7.setViewportView(tblReceipt);

        javax.swing.GroupLayout pnlCustomerTransactionLayout = new javax.swing.GroupLayout(pnlCustomerTransaction);
        pnlCustomerTransaction.setLayout(pnlCustomerTransactionLayout);
        pnlCustomerTransactionLayout.setHorizontalGroup(
            pnlCustomerTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerTransactionLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlCustomerTransactionLayout.setVerticalGroup(
            pnlCustomerTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCustomerTransactionLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(pnlCustomerTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        tpCustomer.addTab("Transactions", pnlCustomerTransaction);

        tblLog.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {

            },
            new String []
            {

            }
        ));
        tblLog.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblLog.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tblLog);

        javax.swing.GroupLayout pnlCustomerLogLayout = new javax.swing.GroupLayout(pnlCustomerLog);
        pnlCustomerLog.setLayout(pnlCustomerLogLayout);
        pnlCustomerLogLayout.setHorizontalGroup(
            pnlCustomerLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCustomerLogLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlCustomerLogLayout.setVerticalGroup(
            pnlCustomerLogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCustomerLogLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        tpCustomer.addTab("Log", pnlCustomerLog);

        lblCount.setText("jLabel2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCount)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cmbSuspended, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAll, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tpCustomer)
                        .addContainerGap())))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel1)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAccountNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCustomerName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbSuspended, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSearch)
                        .addComponent(btnAll)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(tpCustomer))
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        private void miAddActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddActionPerformed
        {//GEN-HEADEREND:event_miAddActionPerformed
		// TODO add your handling code here:
		handleClickMiAddCustomer();
        }//GEN-LAST:event_miAddActionPerformed

        private void miAddChildActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddChildActionPerformed
        {//GEN-HEADEREND:event_miAddChildActionPerformed
		// TODO add your handling code here:
		handleClickMiAddChild();
        }//GEN-LAST:event_miAddChildActionPerformed

        private void miEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miEditActionPerformed
        {//GEN-HEADEREND:event_miEditActionPerformed
		// TODO add your handling code here:
		handleClickBtnEdit();
        }//GEN-LAST:event_miEditActionPerformed

        private void miChildEditActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miChildEditActionPerformed
        {//GEN-HEADEREND:event_miChildEditActionPerformed
		// TODO add your handling code here:
		mBtnEditChildClickHandler();
        }//GEN-LAST:event_miChildEditActionPerformed

        private void miAddInvoiceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddInvoiceActionPerformed
        {//GEN-HEADEREND:event_miAddInvoiceActionPerformed
		// TODO add your handling code here:
		handleClickMiAddInvoice();
        }//GEN-LAST:event_miAddInvoiceActionPerformed

        private void miEditInvoiceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miEditInvoiceActionPerformed
        {//GEN-HEADEREND:event_miEditInvoiceActionPerformed
		// TODO add your handling code here:
		handleClickMiEditInvoice();
        }//GEN-LAST:event_miEditInvoiceActionPerformed

        private void miDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDeleteActionPerformed
        {//GEN-HEADEREND:event_miDeleteActionPerformed
		// TODO add your handling code here:
		handleClickBtnDelete();
        }//GEN-LAST:event_miDeleteActionPerformed

        private void miChildDeleteActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miChildDeleteActionPerformed
        {//GEN-HEADEREND:event_miChildDeleteActionPerformed
		// TODO add your handling code here:
		handleClickMiDeleteChild();
        }//GEN-LAST:event_miChildDeleteActionPerformed

        private void miDeleteInvoiceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDeleteInvoiceActionPerformed
        {//GEN-HEADEREND:event_miDeleteInvoiceActionPerformed
		// TODO add your handling code here:
		handleClickMiDeleteInvoice();
        }//GEN-LAST:event_miDeleteInvoiceActionPerformed

        private void miAddReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miAddReceiptActionPerformed
        {//GEN-HEADEREND:event_miAddReceiptActionPerformed
		// TODO add your handling code here:
		miAddReceiptClickHandler();
        }//GEN-LAST:event_miAddReceiptActionPerformed

        private void miEditReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miEditReceiptActionPerformed
        {//GEN-HEADEREND:event_miEditReceiptActionPerformed
		// TODO add your handling code here:
		miEditReceiptClickHandler();
        }//GEN-LAST:event_miEditReceiptActionPerformed

        private void miDeleteReceiptActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDeleteReceiptActionPerformed
        {//GEN-HEADEREND:event_miDeleteReceiptActionPerformed
		// TODO add your handling code here:
		handleClickMiDeleteReceipt();
        }//GEN-LAST:event_miDeleteReceiptActionPerformed

        private void miWriteOffInvoiceActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miWriteOffInvoiceActionPerformed
        {//GEN-HEADEREND:event_miWriteOffInvoiceActionPerformed
		// TODO add your handling code here:
		handleClickMiWriteOffInvoice();
        }//GEN-LAST:event_miWriteOffInvoiceActionPerformed

        private void cmbAreaPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt)//GEN-FIRST:event_cmbAreaPopupMenuWillBecomeVisible
        {//GEN-HEADEREND:event_cmbAreaPopupMenuWillBecomeVisible
		// TODO add your handling code here:
		popupMenuWillBecomeVisible(evt);
        }//GEN-LAST:event_cmbAreaPopupMenuWillBecomeVisible

        private void miC2EActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miC2EActionPerformed
        {//GEN-HEADEREND:event_miC2EActionPerformed
		// TODO add your handling code here:
		handleClickBtnConvert();
        }//GEN-LAST:event_miC2EActionPerformed

        private void miSuspendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miSuspendActionPerformed
        {//GEN-HEADEREND:event_miSuspendActionPerformed
		// TODO add your handling code here:
		handleClickMiSuspend();
        }//GEN-LAST:event_miSuspendActionPerformed

        private void miChildSuspendActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miChildSuspendActionPerformed
        {//GEN-HEADEREND:event_miChildSuspendActionPerformed
		// TODO add your handling code here:
		handleClickMiSuspendChild();
        }//GEN-LAST:event_miChildSuspendActionPerformed

        private void miDCActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miDCActionPerformed
        {//GEN-HEADEREND:event_miDCActionPerformed
		handleClickMiDC();
        }//GEN-LAST:event_miDCActionPerformed

        private void miChildDCActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miChildDCActionPerformed
        {//GEN-HEADEREND:event_miChildDCActionPerformed
		handleClickMiDCChild();
        }//GEN-LAST:event_miChildDCActionPerformed

        private void miUnDCActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miUnDCActionPerformed
        {//GEN-HEADEREND:event_miUnDCActionPerformed
		handleClickMiUnDC();
        }//GEN-LAST:event_miUnDCActionPerformed

        private void miChildUnDCActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_miChildUnDCActionPerformed
        {//GEN-HEADEREND:event_miChildUnDCActionPerformed
		handleClickMiUnDCChild();
        }//GEN-LAST:event_miChildUnDCActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAll;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbArea;
    private javax.swing.JComboBox<String> cmbSuspended;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblCount;
    private javax.swing.JMenuItem miAdd;
    private javax.swing.JMenuItem miAddChild;
    private javax.swing.JMenuItem miAddInvoice;
    private javax.swing.JMenuItem miAddReceipt;
    private javax.swing.JMenuItem miC2E;
    private javax.swing.JMenuItem miChildDC;
    private javax.swing.JMenuItem miChildDelete;
    private javax.swing.JMenuItem miChildEdit;
    private javax.swing.JMenuItem miChildSuspend;
    private javax.swing.JMenuItem miChildUnDC;
    private javax.swing.JMenuItem miDC;
    private javax.swing.JMenuItem miDelete;
    private javax.swing.JMenuItem miDeleteInvoice;
    private javax.swing.JMenuItem miDeleteReceipt;
    private javax.swing.JMenuItem miEdit;
    private javax.swing.JMenuItem miEditInvoice;
    private javax.swing.JMenuItem miEditReceipt;
    private javax.swing.JMenuItem miSuspend;
    private javax.swing.JMenuItem miUnDC;
    private javax.swing.JMenuItem miWriteOffInvoice;
    private javax.swing.JPopupMenu pmTblChild;
    private javax.swing.JPopupMenu pmTblInvoice;
    private javax.swing.JPopupMenu pmTblParent;
    private javax.swing.JPopupMenu pmTblReceipt;
    private javax.swing.JPanel pnlChildDetail;
    private javax.swing.JPanel pnlChildLog;
    private javax.swing.JPanel pnlCustomerChildren;
    private javax.swing.JPanel pnlCustomerDetails;
    private javax.swing.JPanel pnlCustomerLog;
    private javax.swing.JPanel pnlCustomerTransaction;
    private javax.swing.JTable tblChildLog;
    private javax.swing.JTable tblChildren;
    private javax.swing.JTable tblChildrenDetails;
    private javax.swing.JTable tblDetails;
    private javax.swing.JTable tblInvoice;
    private javax.swing.JTable tblLog;
    private javax.swing.JTable tblParent;
    private javax.swing.JTable tblReceipt;
    private javax.swing.JTabbedPane tpChildrenDetails;
    private javax.swing.JTabbedPane tpCustomer;
    private javax.swing.JTextField txtAccountNo;
    private javax.swing.JTextField txtCustomerName;
    private javax.swing.JTextField txtMobile;
    // End of variables declaration//GEN-END:variables
}
