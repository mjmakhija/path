package com.emsays.path.dao;

import com.emsays.path.dto.MonthYearDTO;
import com.emsays.path.dto.ReceiptDTO;
import com.emsays.path.model.sqlite.ReceiptModel;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;

public class ReceiptSer
{

	Session session;
	ReceiptDAO receiptDAO;

	public ReceiptSer(Session session)
	{
		this.session = session;
		receiptDAO = new ReceiptDAO(session);
	}

	public boolean createOrUpdate(ReceiptDTO receipt)
	{
		return receiptDAO.createOrUpdate(receipt);
	}

	public boolean create(List<ReceiptModel> receipts)
	{
		return receiptDAO.create(receipts);
	}

	public List<ReceiptDTO> retrieve()
	{
		return receiptDAO.retrieve(null, null, null, null, null, null, null, null);
	}

	public List<ReceiptDTO> retrieveByCustomerId(int customerId)
	{
		return receiptDAO.retrieve(null, null, customerId, null, null, null, null, null);
	}

	public List<ReceiptDTO> retrieve(Integer month, Integer year, Integer customerId, String vNote, Date dateFrom, Date dateTo, Integer amount, AccountSer.enmKeys accountKey)
	{
		return receiptDAO.retrieve(month, year, customerId, vNote, dateFrom, dateTo, amount, accountKey);
	}

	public boolean delete(ReceiptDTO receipt)
	{
		return receiptDAO.delete(receipt);
	}

	public List<MonthYearDTO> getMonthYear()
	{
		return receiptDAO.getMonthYear();
	}
}
