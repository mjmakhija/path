package com.emsays.path.dao;

import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.CustomerDTOWrapper;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.PackageDTO_;
import com.emsays.path.dto.PaymentTypeDTO_;
import com.emsays.path.dto.ReceiptDTO;
import com.emsays.path.dto.ReceiptDTO_;
import com.emsays.path.dto.CustomerDTO_;
import com.emsays.path.dto.InvoiceDTO_;
import java.util.*;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

class CustomerDAO
{

	Session session;

	public CustomerDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(CustomerDTO customer)
	{
		boolean result;
		Transaction tx = null;

		try
		{
			tx = session.beginTransaction();
			session.saveOrUpdate(customer);
			session.flush();
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

	public CustomerDTO retrieve(int id)
	{
		CustomerDTO customer = null;
		customer = (CustomerDTO) session.get(CustomerDTO.class, id);
		return customer;
	}

	public List<CustomerDTO> retrieve(
		String accountNo,
		String customerName,
		String locality1,
		String mobile,
		Integer paymentType,
		Integer package_,
		Boolean amt,
		Boolean home,
		Boolean repairing,
		Boolean dc,
		Boolean suspended,
		AreaDTO area)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<CustomerDTO> criteria = builder.createQuery(CustomerDTO.class);
		Root<CustomerDTO> root = criteria.from(CustomerDTO.class);
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(builder.isNull(root.get(CustomerDTO_.parent)));

		if (accountNo != null)
		{
			predicates.add(builder.like(root.get(CustomerDTO_.accountNo).as(String.class), "%" + accountNo + "%"));
		}

		if (customerName != null)
		{
			predicates.add(builder.like(root.get(CustomerDTO_.customerName), "%" + customerName + "%"));
		}

		if (mobile != null)
		{
			predicates.add(builder.like(root.get(CustomerDTO_.mobile), "%" + mobile + "%"));
		}

		if (paymentType != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.paymentType).get(PaymentTypeDTO_.id), paymentType));
		}

		if (package_ != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.package_).get(PackageDTO_.id), package_));
		}

		if (amt != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.amp), amt));
		}

		if (home != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.home), home));
		}

		if (repairing != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.repair), repairing));
		}

		if (dc != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.dc), dc));
		}

		if (suspended != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.suspended), suspended));
		}

		if (area != null)
		{
			predicates.add(builder.equal(root.get(CustomerDTO_.area), area));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<CustomerDTO> vObjCustomer = session.createQuery(criteria).getResultList();

		return vObjCustomer;

	}

	public boolean delete(CustomerDTO customer)
	{

		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(customer);
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

	public CustomerDTO resetObject(CustomerDTO customer)
	{
		session.evict(customer);
		customer = retrieve(customer.getId());
		return customer;
	}

	public List<CustomerDTOWrapper> _getDue()
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CustomerDTOWrapper> query = builder.createQuery(CustomerDTOWrapper.class);
		Root<CustomerDTO> root = query.from(CustomerDTO.class);
		List<Predicate> predicates = new ArrayList<>();

		predicates.add(builder.isNull(root.get(CustomerDTO_.parent)));

		query.where(predicates.toArray(new Predicate[]
		{
		}));

		// Total Receivable
		Subquery<Integer> querySub = query.subquery(Integer.class);
		Root<InvoiceDTO> rootSub = querySub.from(InvoiceDTO.class);
		querySub.select((Expression<Integer>) builder.sum(rootSub.get(InvoiceDTO_.amount)).alias("amt_gt_receivable"));
		querySub.where(builder.equal(rootSub.get(InvoiceDTO_.customer), root));

		// Total Received
		Subquery<Integer> querySub2 = query.subquery(Integer.class);
		Root<ReceiptDTO> rootSub2 = querySub2.from(ReceiptDTO.class);
		querySub2.select((Expression<Integer>) builder.sum(rootSub2.get(ReceiptDTO_.amount)).alias("amt_gt_received"));
		querySub2.where(builder.equal(rootSub2.get(ReceiptDTO_.customer), root));

		query.multiselect(root, querySub.getSelection(), querySub2.getSelection());

		TypedQuery<CustomerDTOWrapper> typedQuery = session.createQuery(query);

		List<CustomerDTOWrapper> listObjects = typedQuery.getResultList();

		return listObjects;
	}

	public CustomerDTO _mLoadAllDetails(CustomerDTO customer)
	{

		customer = session.load(CustomerDTO.class, customer.getId());

		Hibernate.initialize(customer.getChilds());
		Hibernate.initialize(customer.getCollectFrom());
		Hibernate.initialize(customer.getOffer());
		Hibernate.initialize(customer.getPackage());
		Hibernate.initialize(customer.getPackage());
		Hibernate.initialize(customer.getParent());
		Hibernate.initialize(customer.getPaymentType());

		Hibernate.initialize(customer.getPaymentType());

		return customer;

	}

	public CustomerDTO _mFindByName(String vName)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<CustomerDTO> criteria = builder.createQuery(CustomerDTO.class);
		Root<CustomerDTO> root = criteria.from(CustomerDTO.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get(CustomerDTO_.customerName), vName));

		List<CustomerDTO> vObjCustomer = session.createQuery(criteria).getResultList();

		return vObjCustomer.isEmpty() ? null : vObjCustomer.get(0);
	}

}
