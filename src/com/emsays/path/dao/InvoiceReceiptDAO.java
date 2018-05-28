package com.emsays.path.dao;

import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.InvoiceReceiptDTO_;
import com.emsays.path.dto.ReceiptDTO;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

class InvoiceReceiptDAO
{

	Session session;

	public InvoiceReceiptDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(InvoiceReceiptDTO invoiceReceipt)
	{

		boolean result;
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			session.saveOrUpdate(invoiceReceipt);
			tx.commit();
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			tx.rollback();
			result = false;
		}

		return result;

	}
	
	public List<InvoiceReceiptDTO> retrieve()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<InvoiceReceiptDTO> criteria = builder.createQuery(InvoiceReceiptDTO.class);
		Root<InvoiceReceiptDTO> root = criteria.from(InvoiceReceiptDTO.class);
		criteria.select(root);
		List<InvoiceReceiptDTO> listObjects = session.createQuery(criteria).getResultList();

		return listObjects;
	}

	public InvoiceReceiptDTO find(InvoiceDTO invoice, ReceiptDTO receipt)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceReceiptDTO> criteria = builder.createQuery(InvoiceReceiptDTO.class);
		Root<InvoiceReceiptDTO> root = criteria.from(InvoiceReceiptDTO.class);

		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(builder.equal(root.get(InvoiceReceiptDTO_.invoice), invoice));
		predicates.add(builder.equal(root.get(InvoiceReceiptDTO_.receipt), receipt));

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<InvoiceReceiptDTO> vObjReceipt = session.createQuery(criteria).getResultList();

		if (vObjReceipt.size() > 0)
		{
			return vObjReceipt.get(0);
		}
		return null;
	}

	public boolean delete(InvoiceReceiptDTO invoiceReceipt)
	{

		boolean result;
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			session.delete(invoiceReceipt);
			tx.commit();
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			tx.rollback();
			result = false;
		}

		return result;

	}
}
