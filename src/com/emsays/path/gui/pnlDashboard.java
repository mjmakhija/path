package com.emsays.path.gui;

import com.emsays.path.Database;
import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.CustomerSer;
import com.emsays.path.dao.InvoiceSer;
import com.emsays.path.dao.ReceiptSer;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class pnlDashboard extends javax.swing.JPanel
{

	MyFormWrapper vParent;

	public pnlDashboard(MyFormWrapper vParent)
	{
		initComponents();

		this.vParent = vParent;
		loadDataCustomer();
		loadDataInvoices();
		loadDataReceipts();
	}

	private void loadDataCustomer()
	{
		List<CustomerDTO> customerDTOs = new CustomerSer(Database.getSessionCompanyYear()).retrieve();
		int countTotalCustomers = customerDTOs.size();
		int countYearlyCustomers = 0;
		int countMonthlyCustomers = 0;
		int countInactiveCustomers = 0;

		int countTotalPackage = 0;
		int countFTA = 0;
		int countPower = 0;
		int countRoyal = 0;
		int countRoyalHD = 0;

		for (CustomerDTO customerDTO : customerDTOs)
		{
			if (customerDTO.isSuspended())
			{
				countInactiveCustomers++;

			}
			else
			{
				if (customerDTO.getPaymentType().getName().toUpperCase().equals("YEARLY"))
				{
					countYearlyCustomers++;
				}
				else if (customerDTO.getPaymentType().getName().toUpperCase().equals("MONTHLY"))
				{
					countMonthlyCustomers++;
				}

				countTotalPackage++;
				if (customerDTO.getPackage().getName().toLowerCase().equals("fta+addon"))
				{
					countFTA++;
				}
				else if (customerDTO.getPackage().getName().toLowerCase().equals("power"))
				{
					countPower++;
				}
				else if (customerDTO.getPackage().getName().toLowerCase().equals("royal"))
				{
					countRoyal++;
				}
				else if (customerDTO.getPackage().getName().toLowerCase().equals("royalhd"))
				{
					countRoyalHD++;
				}

			}

		}

		lblTotalCustomers.setText(String.valueOf(countTotalCustomers));
		lblYearlyCustomers.setText(String.valueOf(countYearlyCustomers));
		lblMonthlyCustomers.setText(String.valueOf(countMonthlyCustomers));
		lblInactiveCustomers.setText(String.valueOf(countInactiveCustomers));

		lblTotalPackage.setText(String.valueOf(countTotalPackage));
		lblFTAADDONPackage.setText(String.valueOf(countFTA));
		lblPowerPackage.setText(String.valueOf(countPower));
		lblRoyalPackage.setText(String.valueOf(countRoyal));
		lblRoyalHDPackage.setText(String.valueOf(countRoyalHD));

	}

	private void loadDataInvoices()
	{
		Calendar cal = Calendar.getInstance();

		List<InvoiceDTO> invoiceDTOs = new InvoiceSer(Database.getSessionCompanyYear()).mSearch(null, null, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.YEAR));
		int countTotalInvoices = invoiceDTOs.size();
		int countPaidInvoices = 0;
		int countUnPaidInvoices = 0;
		int countOverPaidInvoices = 0;

		int amountTotalInvoices = 0;
		int amountPaidInvoices = 0;
		int amountUnPaidInvoices = 0;
		int amountOverPaidInvoices = 0;

		for (InvoiceDTO invoice : invoiceDTOs)
		{

			amountTotalInvoices += invoice.getAmount();

			if (invoice.getAmountDue() > 0)
			{
				countUnPaidInvoices++;
				amountUnPaidInvoices += invoice.getAmount();
			}
			else if (invoice.getAmountDue() == 0)
			{
				countPaidInvoices++;
				amountPaidInvoices += invoice.getAmount();
			}
			else if (invoice.getAmountDue() < 0)
			{
				countUnPaidInvoices++;
				amountUnPaidInvoices += (-1 * invoice.getAmount());
			}

		}

		lblTotalCurrentMonthInvoices.setText(String.valueOf(countTotalInvoices));
		lblPaidCurrentMonthInvoices.setText(String.valueOf(countPaidInvoices));
		lblUnPaidCurrentMonthInvoices.setText(String.valueOf(countUnPaidInvoices));
		lblOverPaidCurrentMonthInvoices.setText(String.valueOf(countOverPaidInvoices));

		lblTotalCurrentMonthInvoicesAmt.setText(String.valueOf(amountTotalInvoices));
		lblPaidCurrentMonthInvoicesAmt.setText(String.valueOf(amountPaidInvoices));
		lblUnPaidCurrentMonthInvoicesAmt.setText(String.valueOf(amountUnPaidInvoices));
		lblOverPaidCurrentMonthInvoicesAmt.setText(String.valueOf(amountOverPaidInvoices));

	}

	private void loadDataReceipts()
	{
		AccountSer accountSer = new AccountSer(Database.getSessionCompanyYear());
		ReceiptSer receiptSer = new ReceiptSer(Database.getSessionCompanyYear());

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		cal2.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal2.set(Calendar.HOUR_OF_DAY, 23);
		cal2.set(Calendar.MINUTE, 59);
		cal2.set(Calendar.SECOND, 59);
		cal2.set(Calendar.MILLISECOND, 999);

		List<ReceiptDTO> receipts = receiptSer.retrieve(null, null, null, null, cal.getTime(), cal2.getTime(), null, null);

		int countReceipts = receipts.size();
		int countCashReceipts = 0;
		int countWriteOffReceipts = 0;
		int amtReceipts = 0;
		int amtCashReceipts = 0;
		int amtWriteOffReceipts = 0;

		for (ReceiptDTO receipt : receipts)
		{
			amtReceipts += receipt.getAmount();

			if (accountSer.get(AccountSer.enmKeys.NA) == receipt.getAccount())
			{
				amtCashReceipts += receipt.getAmount();
				countCashReceipts++;
			}
			else if (accountSer.get(AccountSer.enmKeys.WRITE_OFF) == receipt.getAccount())
			{
				amtWriteOffReceipts += receipt.getAmount();
				countWriteOffReceipts++;
			}
		}

		lblTotalCurrentMonthReceipts.setText(String.valueOf(countReceipts));
		lblCashCurrentMonthReceipts.setText(String.valueOf(countCashReceipts));
		lblWriteOffCurrentMonthReceipts.setText(String.valueOf(countWriteOffReceipts));

		lblTotalCurrentMonthReceiptsAmt.setText(String.valueOf(amtReceipts));
		lblCashCurrentMonthReceiptsAmt.setText(String.valueOf(amtCashReceipts));
		lblWriteOffCurrentMonthReceiptsAmt.setText(String.valueOf(amtWriteOffReceipts));

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
                jPanel3 = new javax.swing.JPanel();
                lblTotalCustomers = new javax.swing.JLabel();
                jLabel22 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel25 = new javax.swing.JLabel();
                jLabel26 = new javax.swing.JLabel();
                lblYearlyCustomers = new javax.swing.JLabel();
                lblMonthlyCustomers = new javax.swing.JLabel();
                lblInactiveCustomers = new javax.swing.JLabel();
                jPanel4 = new javax.swing.JPanel();
                lblTotalCurrentMonthInvoices = new javax.swing.JLabel();
                jLabel24 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel29 = new javax.swing.JLabel();
                jLabel30 = new javax.swing.JLabel();
                lblPaidCurrentMonthInvoices = new javax.swing.JLabel();
                lblUnPaidCurrentMonthInvoices = new javax.swing.JLabel();
                lblOverPaidCurrentMonthInvoices = new javax.swing.JLabel();
                jPanel5 = new javax.swing.JPanel();
                lblTotalCurrentMonthReceipts = new javax.swing.JLabel();
                jLabel34 = new javax.swing.JLabel();
                jLabel10 = new javax.swing.JLabel();
                jLabel35 = new javax.swing.JLabel();
                lblCashCurrentMonthReceipts = new javax.swing.JLabel();
                lblWriteOffCurrentMonthReceipts = new javax.swing.JLabel();
                jPanel6 = new javax.swing.JPanel();
                lblTotalPackage = new javax.swing.JLabel();
                jLabel40 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();
                jLabel41 = new javax.swing.JLabel();
                jLabel42 = new javax.swing.JLabel();
                lblFTAADDONPackage = new javax.swing.JLabel();
                lblPowerPackage = new javax.swing.JLabel();
                lblRoyalPackage = new javax.swing.JLabel();
                jLabel46 = new javax.swing.JLabel();
                lblRoyalHDPackage = new javax.swing.JLabel();
                jPanel7 = new javax.swing.JPanel();
                lblTotalCurrentMonthInvoicesAmt = new javax.swing.JLabel();
                jLabel27 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jLabel31 = new javax.swing.JLabel();
                jLabel32 = new javax.swing.JLabel();
                lblPaidCurrentMonthInvoicesAmt = new javax.swing.JLabel();
                lblUnPaidCurrentMonthInvoicesAmt = new javax.swing.JLabel();
                lblOverPaidCurrentMonthInvoicesAmt = new javax.swing.JLabel();
                jPanel8 = new javax.swing.JPanel();
                lblTotalCurrentMonthReceiptsAmt = new javax.swing.JLabel();
                jLabel36 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel37 = new javax.swing.JLabel();
                lblCashCurrentMonthReceiptsAmt = new javax.swing.JLabel();
                lblWriteOffCurrentMonthReceiptsAmt = new javax.swing.JLabel();

                fileChooser.setAcceptAllFileFilterUsed(false);

                jPanel3.setPreferredSize(new java.awt.Dimension(250, 250));

                lblTotalCustomers.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
                lblTotalCustomers.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblTotalCustomers.setText("300");

                jLabel22.setText("Yearly");

                jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel5.setText("Customers");

                jLabel25.setText("Monthly");

                jLabel26.setText("Inactive");

                lblYearlyCustomers.setText("0");

                lblMonthlyCustomers.setText("0");

                lblInactiveCustomers.setText("0");

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblInactiveCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblMonthlyCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblYearlyCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(66, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel5)
                                .addGap(25, 25, 25)
                                .addComponent(lblTotalCustomers)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel25)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel26))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(lblYearlyCustomers)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblMonthlyCustomers)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblInactiveCustomers)))
                                .addContainerGap(31, Short.MAX_VALUE))
                );

                jPanel4.setPreferredSize(new java.awt.Dimension(250, 250));

                lblTotalCurrentMonthInvoices.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
                lblTotalCurrentMonthInvoices.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblTotalCurrentMonthInvoices.setText("300");

                jLabel24.setText("Paid");

                jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel8.setText("Current Month Invoices");

                jLabel29.setText("Un Paid");

                jLabel30.setText("Over Paid");

                lblPaidCurrentMonthInvoices.setText("0");

                lblUnPaidCurrentMonthInvoices.setText("0");

                lblOverPaidCurrentMonthInvoices.setText("0");

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalCurrentMonthInvoices, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblOverPaidCurrentMonthInvoices, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUnPaidCurrentMonthInvoices, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPaidCurrentMonthInvoices, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(66, Short.MAX_VALUE))
                );
                jPanel4Layout.setVerticalGroup(
                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel8)
                                .addGap(25, 25, 25)
                                .addComponent(lblTotalCurrentMonthInvoices)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel24)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel29)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel30))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(lblPaidCurrentMonthInvoices)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblUnPaidCurrentMonthInvoices)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblOverPaidCurrentMonthInvoices)))
                                .addContainerGap(31, Short.MAX_VALUE))
                );

                jPanel5.setPreferredSize(new java.awt.Dimension(250, 250));

                lblTotalCurrentMonthReceipts.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
                lblTotalCurrentMonthReceipts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblTotalCurrentMonthReceipts.setText("300");

                jLabel34.setText("Cash");

                jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel10.setText("Current Month Receipts");

                jLabel35.setText("Write Off");

                lblCashCurrentMonthReceipts.setText("0");

                lblWriteOffCurrentMonthReceipts.setText("0");

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalCurrentMonthReceipts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblWriteOffCurrentMonthReceipts, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCashCurrentMonthReceipts, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(66, Short.MAX_VALUE))
                );
                jPanel5Layout.setVerticalGroup(
                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel10)
                                .addGap(25, 25, 25)
                                .addComponent(lblTotalCurrentMonthReceipts)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(jLabel34)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel35))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addComponent(lblCashCurrentMonthReceipts)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblWriteOffCurrentMonthReceipts)))
                                .addContainerGap(53, Short.MAX_VALUE))
                );

                jPanel6.setPreferredSize(new java.awt.Dimension(250, 250));

                lblTotalPackage.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
                lblTotalPackage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblTotalPackage.setText("300");

                jLabel40.setText("FTA ADDON");

                jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel12.setText("Package");

                jLabel41.setText("POWER");

                jLabel42.setText("ROYAL");

                lblFTAADDONPackage.setText("0");

                lblPowerPackage.setText("0");

                lblRoyalPackage.setText("0");

                jLabel46.setText("ROYAL HD");

                lblRoyalHDPackage.setText("0");

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalPackage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblRoyalHDPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblRoyalPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblPowerPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(lblFTAADDONPackage, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(66, Short.MAX_VALUE))
                );
                jPanel6Layout.setVerticalGroup(
                        jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel12)
                                .addGap(25, 25, 25)
                                .addComponent(lblTotalPackage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(jLabel40)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel41)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel42))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(lblFTAADDONPackage)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblPowerPackage)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblRoyalPackage)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel46)
                                        .addComponent(lblRoyalHDPackage))
                                .addContainerGap(9, Short.MAX_VALUE))
                );

                jPanel7.setPreferredSize(new java.awt.Dimension(250, 250));

                lblTotalCurrentMonthInvoicesAmt.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
                lblTotalCurrentMonthInvoicesAmt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblTotalCurrentMonthInvoicesAmt.setText("300");

                jLabel27.setText("Paid");

                jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel9.setText("Current Month Invoices Amount");

                jLabel31.setText("Un Paid");

                jLabel32.setText("Over Paid");

                lblPaidCurrentMonthInvoicesAmt.setText("0");

                lblUnPaidCurrentMonthInvoicesAmt.setText("0");

                lblOverPaidCurrentMonthInvoicesAmt.setText("0");

                javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                jPanel7.setLayout(jPanel7Layout);
                jPanel7Layout.setHorizontalGroup(
                        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalCurrentMonthInvoicesAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblOverPaidCurrentMonthInvoicesAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblUnPaidCurrentMonthInvoicesAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblPaidCurrentMonthInvoicesAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(66, Short.MAX_VALUE))
                );
                jPanel7Layout.setVerticalGroup(
                        jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel9)
                                .addGap(25, 25, 25)
                                .addComponent(lblTotalCurrentMonthInvoicesAmt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(jLabel27)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel31)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel32))
                                        .addGroup(jPanel7Layout.createSequentialGroup()
                                                .addComponent(lblPaidCurrentMonthInvoicesAmt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblUnPaidCurrentMonthInvoicesAmt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblOverPaidCurrentMonthInvoicesAmt)))
                                .addContainerGap(31, Short.MAX_VALUE))
                );

                jPanel8.setPreferredSize(new java.awt.Dimension(250, 250));

                lblTotalCurrentMonthReceiptsAmt.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
                lblTotalCurrentMonthReceiptsAmt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                lblTotalCurrentMonthReceiptsAmt.setText("300");

                jLabel36.setText("Cash");

                jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel11.setText("Current Month Receipts Amount");

                jLabel37.setText("Write Off");

                lblCashCurrentMonthReceiptsAmt.setText("0");

                lblWriteOffCurrentMonthReceiptsAmt.setText("0");

                javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
                jPanel8.setLayout(jPanel8Layout);
                jPanel8Layout.setHorizontalGroup(
                        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTotalCurrentMonthReceiptsAmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblWriteOffCurrentMonthReceiptsAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCashCurrentMonthReceiptsAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(66, Short.MAX_VALUE))
                );
                jPanel8Layout.setVerticalGroup(
                        jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel11)
                                .addGap(25, 25, 25)
                                .addComponent(lblTotalCurrentMonthReceiptsAmt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(jLabel36)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel37))
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                                .addComponent(lblCashCurrentMonthReceiptsAmt)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblWriteOffCurrentMonthReceiptsAmt)))
                                .addContainerGap(53, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, 0)
                                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                );
                layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, 0)))
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                );
        }// </editor-fold>//GEN-END:initComponents


        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JFileChooser fileChooser;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel22;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel25;
        private javax.swing.JLabel jLabel26;
        private javax.swing.JLabel jLabel27;
        private javax.swing.JLabel jLabel29;
        private javax.swing.JLabel jLabel30;
        private javax.swing.JLabel jLabel31;
        private javax.swing.JLabel jLabel32;
        private javax.swing.JLabel jLabel34;
        private javax.swing.JLabel jLabel35;
        private javax.swing.JLabel jLabel36;
        private javax.swing.JLabel jLabel37;
        private javax.swing.JLabel jLabel40;
        private javax.swing.JLabel jLabel41;
        private javax.swing.JLabel jLabel42;
        private javax.swing.JLabel jLabel46;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JLabel lblCashCurrentMonthReceipts;
        private javax.swing.JLabel lblCashCurrentMonthReceiptsAmt;
        private javax.swing.JLabel lblFTAADDONPackage;
        private javax.swing.JLabel lblInactiveCustomers;
        private javax.swing.JLabel lblMonthlyCustomers;
        private javax.swing.JLabel lblOverPaidCurrentMonthInvoices;
        private javax.swing.JLabel lblOverPaidCurrentMonthInvoicesAmt;
        private javax.swing.JLabel lblPaidCurrentMonthInvoices;
        private javax.swing.JLabel lblPaidCurrentMonthInvoicesAmt;
        private javax.swing.JLabel lblPowerPackage;
        private javax.swing.JLabel lblRoyalHDPackage;
        private javax.swing.JLabel lblRoyalPackage;
        private javax.swing.JLabel lblTotalCurrentMonthInvoices;
        private javax.swing.JLabel lblTotalCurrentMonthInvoicesAmt;
        private javax.swing.JLabel lblTotalCurrentMonthReceipts;
        private javax.swing.JLabel lblTotalCurrentMonthReceiptsAmt;
        private javax.swing.JLabel lblTotalCustomers;
        private javax.swing.JLabel lblTotalPackage;
        private javax.swing.JLabel lblUnPaidCurrentMonthInvoices;
        private javax.swing.JLabel lblUnPaidCurrentMonthInvoicesAmt;
        private javax.swing.JLabel lblWriteOffCurrentMonthReceipts;
        private javax.swing.JLabel lblWriteOffCurrentMonthReceiptsAmt;
        private javax.swing.JLabel lblYearlyCustomers;
        // End of variables declaration//GEN-END:variables
}
