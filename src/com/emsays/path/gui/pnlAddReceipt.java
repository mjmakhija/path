package com.emsays.path.gui;

import com.emsays.path.Database;
import com.emsays.path.Util;
import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.InvoiceReceiptSer;
import com.emsays.path.dao.ReceiptSer;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class pnlAddReceipt extends javax.swing.JPanel
{
	
	MyFormWrapper frmWrapper;
	MyPanelBack pnlBack;
	boolean vModeAdd;
	CustomerDTO customer;
	ReceiptDTO receipt;
	String[] vColumnNames =
	{
		"#",
		"Id",
		"Customer Id",
		"Invoice No",
		"Account No",
		"Customer Name",
		"Address",
		"Mobile",
		"Amount",
		"Amount Paid",
		"Amount Due",
		""
	};
	
	public pnlAddReceipt(MyFormWrapper vParent, MyPanelBack pnlBack)
	{
		initComponents();
		
		myInit();
		
		this.frmWrapper = vParent;
		this.pnlBack = pnlBack;
		this.vModeAdd = true;
		this.receipt = new ReceiptDTO();
		
		txtDate.setText(Util.mGetLocalDateFormat(new Date()));
		loadInvoices(customer.getInvoices());
		
		txtAmount.setText(String.valueOf(customer.getAmount()));
	}
	
	public pnlAddReceipt(MyFormWrapper vParent, MyPanelBack pnlBack, CustomerDTO customer)
	{
		initComponents();
		
		myInit();
		
		this.frmWrapper = vParent;
		this.pnlBack = pnlBack;
		this.vModeAdd = true;
		this.customer = customer;
		this.receipt = new ReceiptDTO();
		
		loadInvoices(customer.getInvoices());
		
		txtAmount.setText(String.valueOf(customer.getAmount()));
	}
	
	public pnlAddReceipt(MyFormWrapper vParent, MyPanelBack pnlBack, ReceiptDTO receipt)
	{
		initComponents();
		
		myInit();
		
		this.frmWrapper = vParent;
		this.pnlBack = pnlBack;
		this.vModeAdd = false;
		this.customer = receipt.getCustomer();
		this.receipt = receipt;
		
		txtDate.setText(Util.mGetLocalDateFormat(receipt.getDate()));
		txtAmount.setText(String.valueOf(receipt.getAmount()));
		txtNote.setText(receipt.getNote());
		
		loadInvoices(customer.getInvoices());
		
	}
	
	private void myInit()
	{
		DefaultTableModel dtm = new DefaultTableModel()
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return column == 11;
			}
		};
		
		dtm.setColumnIdentifiers(vColumnNames);
		dtm.addTableModelListener(new TableModelListener()
		{
			@Override
			public void tableChanged(TableModelEvent e)
			{
				if (e.getFirstRow() == e.getLastRow()
						&& e.getColumn() == 11
						&& e.getType() == e.UPDATE)
				{
					handleRecievingAmountChange();
				}
			}
		});
		
		tbl.setModel(dtm);
	}
	
	private void loadInvoices(List<InvoiceDTO> invoices)
	{
		DefaultTableModel vDTM = (DefaultTableModel) tbl.getModel();
		int i = 1;
		for (InvoiceDTO invoice : invoices)
		{
			List<InvoiceReceiptDTO> receipts = invoice.getReceiptes();
			int totalAmtPaid = 0;
			for (InvoiceReceiptDTO receipt1 : receipts)
			{
				totalAmtPaid += receipt1.getAmount();
			}
			
			int amountFromThisReceipt = 0;
			List<InvoiceReceiptDTO> invoiceReceipts = invoice.getReceiptes();
			for (InvoiceReceiptDTO invoiceReceipt : invoiceReceipts)
			{
				if (invoiceReceipt.getReceipt().equals(receipt))
				{
					amountFromThisReceipt = invoiceReceipt.getAmount();
					break;
				}
			}
			
			Object[] o = new Object[]
			{
				i++,
				invoice.getId(),
				invoice.getCustomer().getId(),
				invoice.getSrNo(),
				invoice.getCustomer().getAccountNo(),
				invoice.getCustomer().getCustomerName(),
				invoice.getCustomer().getHouseNo1()
				+ (invoice.getCustomer().getHouseNo2() == null || invoice.getCustomer().getHouseNo2().equals("") ? "" : "-" + invoice.getCustomer().getHouseNo2())
				+ (invoice.getCustomer().getHouseNo3() == null || invoice.getCustomer().getHouseNo3().equals("") ? "" : "-" + invoice.getCustomer().getHouseNo3()),
				invoice.getCustomer().getMobile(),
				invoice.getAmount(),
				totalAmtPaid,
				invoice.getAmount() - totalAmtPaid,
				amountFromThisReceipt
			};
			vDTM.addRow(o);
		}
		
		Util.resizeColumnWidth(tbl);
	}
	
	private void handleRecievingAmountChange()
	{
		int rowCount = tbl.getModel().getRowCount();
		int colIndex = 11;
		int sum = 0;
		for (int i = 0; i < rowCount; i++)
		{
			String amt = String.valueOf(tbl.getModel().getValueAt(i, colIndex));
			if (Util.isNumeric(amt))
			{
				sum += Integer.parseInt(amt);
			}
		}
		txtRemaining.setText(String.valueOf(sum));
		
	}
	
	private boolean checkIsValid()
	{
		
		if (txtDate.getText() == null || txtDate.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Date is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (!Util.isDateValid(txtDate.getText()))
		{
			JOptionPane.showMessageDialog(null, "Date is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (txtAmount.getText() == null || txtAmount.getText().equals(""))
		{
			JOptionPane.showMessageDialog(null, "Amount is required", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		if (!Util.isNumeric(txtAmount.getText()))
		{
			JOptionPane.showMessageDialog(null, "Amount is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		
		int rowCount = tbl.getModel().getRowCount();
		int colIndex = 11;
		for (int i = 0; i < rowCount; i++)
		{
			String amt = String.valueOf(tbl.getModel().getValueAt(i, colIndex));
			if (amt != null && !amt.equals("") && !Util.isNumeric(amt))
			{
				JOptionPane.showMessageDialog(null, "Amount is invalid", "Message", JOptionPane.INFORMATION_MESSAGE);
				return false;
			}
		}
		
		return true;
	}
	
	private void handleClickBtnSave()
	{
		ReceiptSer receiptSer;
		InvoiceReceiptSer invoiceReceiptSer;
		
		receiptSer = new ReceiptSer(Database.getSessionCompanyYear());
		invoiceReceiptSer = new InvoiceReceiptSer(Database.getSessionCompanyYear());
		
		if (!checkIsValid())
		{
			return;
		}
		
		if (vModeAdd)
		{
			receipt.setDate(Util.mConvertStringToDate2(txtDate.getText()));
			receipt.setAccount(new AccountSer(Database.getSessionCompanyYear()).get(AccountSer.enmKeys.NA));
		}
		
		receipt.setAmount(Integer.parseInt(txtAmount.getText()));
		receipt.setNote(txtNote.getText());
		receipt.setCustomer(customer);
		
		if (!receiptSer.createOrUpdate(receipt))
		{
			
		}

		//customer.addReceipt(receipt);
		int rowCount = tbl.getModel().getRowCount();
		int colIndex = 11;
		for (int i = 0; i < rowCount; i++)
		{
			InvoiceDTO invoice = customer.getInvoices().get(i);
			String amt = String.valueOf(tbl.getModel().getValueAt(i, colIndex));
			
			if (vModeAdd)
			{
				if (amt == null || amt.equals("") || amt.equals("0"))
				{
				}
				else
				{
					InvoiceReceiptDTO invoiceReceiptDTO
							= new InvoiceReceiptDTO(
									invoice, receipt, Integer.parseInt(amt));
					invoiceReceiptSer.createOrUpdate(invoiceReceiptDTO);
					invoice.addReceipt(invoiceReceiptDTO);
				}
			}
			else
			{
				InvoiceReceiptDTO invoiceReceiptFind;
				if (amt == null || amt.equals("") || amt.equals("0"))
				{
					invoiceReceiptFind
							= invoiceReceiptSer.find(invoice, receipt);
					if (invoiceReceiptFind == null)
					{
					}
					else
					{
						invoice.removeReceipt(invoiceReceiptFind);
						invoiceReceiptSer.delete(invoiceReceiptFind);
					}
				}
				else
				{
					invoiceReceiptFind
							= invoiceReceiptSer.find(invoice, receipt);
					if (invoiceReceiptFind == null)
					{
						InvoiceReceiptDTO invoiceReceiptDTO
								= new InvoiceReceiptDTO(
										invoice, receipt, Integer.parseInt(amt));
						invoiceReceiptSer.createOrUpdate(invoiceReceiptDTO);
						invoice.addReceipt(invoiceReceiptDTO);
					}
					else
					{
						invoiceReceiptFind.setAmount(Integer.parseInt(amt));
						invoiceReceiptSer.createOrUpdate(invoiceReceiptFind);
					}
				}
			}
		}
		
		JOptionPane.showMessageDialog(null, "Data saved successfully", "Message", JOptionPane.INFORMATION_MESSAGE);
		
		this.showListForm();
		
	}
	
	private void handleClickBtnCancel()
	{
		this.showListForm();
	}
	
	private void showListForm()
	{
		pnlBack.refreshData();
		frmWrapper.addPanel((JPanel) pnlBack);
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

        txtAmount = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        txtNote = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtRemaining = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tbl);

        btnSave.setText("Save");
        btnSave.setPreferredSize(new java.awt.Dimension(75, 32));
        btnSave.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.setPreferredSize(new java.awt.Dimension(75, 32));
        btnCancel.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnCancelActionPerformed(evt);
            }
        });

        jLabel12.setText("Amount");

        jLabel13.setText("Note");

        txtRemaining.setEditable(false);

        jLabel14.setText("Remaining");

        jLabel15.setText("Date");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(24, 24, 24)
                        .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel14))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(txtRemaining))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(515, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRemaining, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtNote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

        private void btnSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSaveActionPerformed
        {//GEN-HEADEREND:event_btnSaveActionPerformed
			// TODO add your handling code here:
			handleClickBtnSave();
        }//GEN-LAST:event_btnSaveActionPerformed

        private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnCancelActionPerformed
        {//GEN-HEADEREND:event_btnCancelActionPerformed
			// TODO add your handling code here:
			handleClickBtnCancel();
        }//GEN-LAST:event_btnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtNote;
    private javax.swing.JTextField txtRemaining;
    // End of variables declaration//GEN-END:variables
}
