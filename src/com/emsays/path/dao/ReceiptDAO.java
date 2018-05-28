package com.emsays.path.dao;

import com.emsays.path.dto.AccountDTO;
import com.emsays.path.dto.AccountDTO_;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.CustomerDTO_;
import com.emsays.path.dto.MonthYearDTO;
import com.emsays.path.dto.ReceiptDTO;
import com.emsays.path.dto.ReceiptDTO_;
import com.emsays.path.model.sqlite.ReceiptModel;
import java.util.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

class ReceiptDAO
{

	Session session;

	public ReceiptDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(ReceiptDTO receipt)
	{

		boolean result;
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			session.saveOrUpdate(receipt);
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

	public boolean create(List<ReceiptModel> receipts)
	{

		boolean result;

		try
		{
			session.beginTransaction();

			AccountDTO account = new AccountSer(session).get(AccountSer.enmKeys.NA);

			receipts.forEach((receipt) ->
			{

				CriteriaBuilder builder = session.getCriteriaBuilder();

				CriteriaQuery<ReceiptDTO> criteria = builder.createQuery(ReceiptDTO.class);
				Root<ReceiptDTO> root = criteria.from(ReceiptDTO.class);
				criteria.select(root);
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(builder.equal(root.get(ReceiptDTO_.amount), receipt.getAmount()));
				predicates.add(builder.equal(root.get(ReceiptDTO_.customer).get(CustomerDTO_.id), receipt.getCustomerId()));
				predicates.add(builder.equal(root.get(ReceiptDTO_.date), receipt.getDate()));
				predicates.add(builder.equal(root.get(ReceiptDTO_.note), receipt.getNote()));
				/*
				try
				{
					predicates.add(builder.equal(root.get(ReceiptDTO_.date), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2018-01-04 11:55:49.000")));
				}
				catch (ParseException ex)
				{
					Logger.getLogger(ReceiptDAO.class.getName()).log(Level.SEVERE, null, ex);
				}
				 */
				criteria.select(root);
				criteria.where(predicates.toArray(new Predicate[]
				{
				}));
				List<ReceiptDTO> checkReceipts = session.createQuery(criteria).getResultList();

				if (checkReceipts.size() == 0)
				{
					CustomerDTO customer = session.load(CustomerDTO.class, receipt.getCustomerId());
					ReceiptDTO receiptDest = new ReceiptDTO();
					receiptDest.setAmount(receipt.getAmount());
					receiptDest.setCustomer(customer);
					receiptDest.setDate(receipt.getDate());
					receiptDest.setNote(receipt.getNote());
					receiptDest.setAccount(account);
					session.saveOrUpdate(receiptDest);
				}

			});

			session.getTransaction().commit();
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}
		finally
		{

		}

		return result;

	}

	public List<ReceiptDTO> retrieve(Integer month, Integer year, Integer customerId, String vNote, Date dateFrom, Date dateTo, Integer amount, AccountSer.enmKeys accountKey)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<ReceiptDTO> criteria = builder.createQuery(ReceiptDTO.class);
		Root<ReceiptDTO> root = criteria.from(ReceiptDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (month != null && year != null)
		{

			Calendar c1 = Calendar.getInstance();
			c1.set(Calendar.MONTH, month - 1);
			c1.set(Calendar.YEAR, year);
			c1.set(Calendar.DAY_OF_MONTH, 1);
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);

			Calendar c2 = Calendar.getInstance();
			c2.set(Calendar.MONTH, month - 1);
			c2.set(Calendar.YEAR, year);
			c2.set(Calendar.DAY_OF_MONTH, c2.getActualMaximum(Calendar.DAY_OF_MONTH));
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);

			Path path = root.get(ReceiptDTO_.date);
			predicates.add(builder.greaterThanOrEqualTo(path, c1.getTime()));
			predicates.add(builder.lessThanOrEqualTo(path, c2.getTime()));
		}

		if (vNote != null && !vNote.equals(""))
		{
			predicates.add(builder.like(root.get(ReceiptDTO_.note), "%" + vNote + "%"));
		}

		if (dateFrom != null)
		{
			Path path = root.get(ReceiptDTO_.date);
			predicates.add(builder.greaterThanOrEqualTo(path, dateFrom));
		}

		if (dateTo != null)
		{
			Path path = root.get(ReceiptDTO_.date);
			predicates.add(builder.lessThanOrEqualTo(path, dateTo));
		}

		if (amount != null && amount > 0)
		{
			predicates.add(builder.equal(root.get(ReceiptDTO_.amount), amount));
		}

		if (customerId != null && customerId > 0)
		{
			predicates.add(builder.equal(root.get(ReceiptDTO_.customer).get(CustomerDTO_.id), customerId));
		}

		if (accountKey != null)
		{
			predicates.add(builder.equal(root.get(ReceiptDTO_.account).get(AccountDTO_.name), accountKey.toString()));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));
		criteria.orderBy(builder.asc(root.get(ReceiptDTO_.date)));

		List<ReceiptDTO> vObjReceipt = session.createQuery(criteria).getResultList();

		return vObjReceipt;
	}

	public boolean delete(ReceiptDTO receipt)
	{

		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(receipt);
			session.getTransaction().commit();
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		}

		return result;

	}

	public List<MonthYearDTO> getMonthYear()
	{

		List<MonthYearDTO> vObjInvoice = session
				.createNativeQuery("SELECT DISTINCT MONTH(date) as month, YEAR(date) as year FROM receipt order by year, month")
				.setResultTransformer(Transformers.aliasToBean(MonthYearDTO.class))
				.list();
		return vObjInvoice;
	}

}
