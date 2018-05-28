package com.emsays.path.dao;

import com.emsays.path.dto.CustomerDTO_;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceDTO_;
import com.emsays.path.dto.MonthYearDTO;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class InvoiceDAO
{

	Session session;

	public InvoiceDAO(Session session)
	{
		this.session = session;
	}

	public List<InvoiceDTO> retrieve()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		criteria.select(root);
		List<InvoiceDTO> listObjects = session.createQuery(criteria).getResultList();

		return listObjects;
	}

	public List<MonthYearDTO> getMonthYear()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<MonthYearDTO> criteria = builder.createQuery(MonthYearDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);

		criteria.multiselect(root.get(InvoiceDTO_.month), root.get(InvoiceDTO_.year));
		criteria.distinct(true);

		List<MonthYearDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return vObjInvoice;

	}

	public List<InvoiceDTO> mFindByCustomerId(int customerId)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get(InvoiceDTO_.customer).get(CustomerDTO_.id), customerId));
		List<InvoiceDTO> vObjReceipt = session.createQuery(criteria).getResultList();

		return vObjReceipt;
	}

	public Integer getNextInvoiceNo()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<Integer> criteria = builder.createQuery(Integer.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		criteria.select(builder.max(root.get(InvoiceDTO_.srNo)));
		Integer maxSrNo = session.createQuery(criteria).getResultList().get(0);

		return maxSrNo + 1;
	}

	public boolean exists(Integer customerId, Integer month, Integer year)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(builder.equal(root.get(InvoiceDTO_.customer).get(CustomerDTO_.id), customerId));
		predicates.add(builder.equal(root.get(InvoiceDTO_.month), month));
		predicates.add(builder.equal(root.get(InvoiceDTO_.year), year));

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));
		List<Order> orders = new ArrayList<>();
		orders.add(builder.asc(root.get(InvoiceDTO_.year)));
		orders.add(builder.asc(root.get(InvoiceDTO_.month)));
		criteria.orderBy(orders);
		List<InvoiceDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return (vObjInvoice.size() > 0);
	}

	public List<InvoiceDTO> mSearch(String vName, String vNote, Integer vMonth, Integer year)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		List<Predicate> predicates = new ArrayList<>();

		if (vName != null && !vName.equals(""))
		{
			//predicates.add(builder.like(root.get(InvoiceDTO_.customerName), "%" + vName + "%"));
		}

		if (vNote != null && !vNote.equals(""))
		{
			predicates.add(builder.like(root.get(InvoiceDTO_.note), "%" + vNote + "%"));
		}

		if (vMonth != null && vMonth > 0)
		{
			predicates.add(builder.equal(root.get(InvoiceDTO_.month), vMonth));
		}

		if (vMonth != null && vMonth > 0)
		{
			predicates.add(builder.equal(root.get(InvoiceDTO_.month), vMonth));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<InvoiceDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return vObjInvoice;
	}

	public boolean delete(InvoiceDTO objInvoice)
	{

		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(objInvoice);
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
}
