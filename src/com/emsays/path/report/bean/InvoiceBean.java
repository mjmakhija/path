package com.emsays.path.report.bean;

import com.emsays.path.GV;
import com.emsays.path.Util;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.util.List;

public class InvoiceBean
{

	private int id;

	private CustomerDTO customer;

	private int year;
	private int month;

	private int srNo;

	private int prevDueAmt;
	private int currentMonthAmt;
	private int totalAmt;

	private String logoImagePath;
	private String rupeeImagePath;
	private String tncImagePath;

	public InvoiceBean(InvoiceDTO invoice)
	{
		this.customer = invoice.getCustomer();
		this.year = invoice.getYear();
		this.month = invoice.getMonth();
		this.srNo = invoice.getSrNo();

		int sumInvoices = 0;
		List<InvoiceDTO> invoices = customer.getInvoices();
		for (InvoiceDTO invoiceTemp : invoices)
		{
			sumInvoices += invoiceTemp.getAmount();
		}

		int sumReceipt = 0;
		List<ReceiptDTO> receipts = customer.getReceipts();
		for (ReceiptDTO receiptTemp : receipts)
		{
			sumReceipt += receiptTemp.getAmount();
		}

		this.prevDueAmt = sumInvoices - sumReceipt - invoice.getAmount();
		this.currentMonthAmt = invoice.getAmount();
		this.totalAmt = this.prevDueAmt + this.currentMonthAmt;

		this.logoImagePath = GV.IMAGE_DIR + "/logo.png";
		this.rupeeImagePath = GV.IMAGE_DIR + "/rupee.png";
		this.tncImagePath = GV.IMAGE_DIR + "/tnc.png";
	}

	public String getInvoiceNo()
	{
		return String.valueOf(year) + String.format("%02d", month) + "-" + String.valueOf(srNo);
	}

	public String getMonth()
	{
		//return String.format("%02d", month);
		return Util.getMonthName(month);
	}

	public String getAccountNo()
	{
		return String.valueOf(customer.getAccountNo());
	}

	public String getMobile()
	{
		return String.valueOf(customer.getMobile());
	}

	public String getName()
	{
		return String.valueOf(customer.getCustomerName());
	}

	public String getAddress()
	{
		return customer.getHouseNo1()
				+ (customer.getHouseNo2() == null || customer.getHouseNo2().equals("") ? "" : "-" + customer.getHouseNo2())
				+ (customer.getHouseNo3() == null || customer.getHouseNo3().equals("") ? "" : "-" + customer.getHouseNo3())
				+ " "
				+ (customer.getArea().getName());
	}

	public String getNoOfTvs()
	{
		int count = 1;

		for (CustomerDTO customerDTO : customer.getChilds())
		{
			if (!customerDTO.isSuspended() && !customerDTO.isDc())
			{
				count++;
			}
		}

		return String.valueOf(count);
	}

	public String getPackage()
	{
		return this.customer.getPackage().getName();
	}

	public String getPrevDueAmt()
	{
		return String.valueOf(prevDueAmt);
	}

	public String getCurrentMonthAmt()
	{
		return String.valueOf(currentMonthAmt);
	}

	public String getTotalAmt()
	{
		return String.valueOf(totalAmt);
	}

	public String getLogoImagePath()
	{
		return logoImagePath;
	}

	public String getRupeeImagePath()
	{
		return rupeeImagePath;
	}

	public String getTncImagePath()
	{
		return tncImagePath;
	}

	public String getStar()
	{
		return prevDueAmt > 0 ? GV.IMAGE_DIR + "/star.png" : GV.IMAGE_DIR + "/noimage.png";
	}

}
