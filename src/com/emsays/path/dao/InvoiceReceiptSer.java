package com.emsays.path.dao;

import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.util.List;
import org.hibernate.Session;

public class InvoiceReceiptSer
{

	Session session;
	InvoiceReceiptDAO invoiceReceiptDAO;

	public InvoiceReceiptSer(Session session)
	{
		this.session = session;
		invoiceReceiptDAO = new InvoiceReceiptDAO(session);
	}

	public boolean createOrUpdate(InvoiceReceiptDTO invoiceReceipt)
	{
		return invoiceReceiptDAO.createOrUpdate(invoiceReceipt);
	}

	public List<InvoiceReceiptDTO> retrieve()
	{

		return invoiceReceiptDAO.retrieve();
	}
	
	public InvoiceReceiptDTO find(InvoiceDTO invoice, ReceiptDTO receipt)
	{

		return invoiceReceiptDAO.find(invoice, receipt);
	}

	public boolean delete(InvoiceReceiptDTO invoiceReceipt)
	{

		return invoiceReceiptDAO.delete(invoiceReceipt);

	}
}
