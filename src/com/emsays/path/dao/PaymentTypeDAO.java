package com.emsays.path.dao;

import com.emsays.path.dto.PaymentTypeDTO;
import com.emsays.path.dto.PaymentTypeDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class PaymentTypeDAO 
{ 

	Session session;

	public PaymentTypeDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(PaymentTypeDTO paymentType)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.saveOrUpdate(paymentType);
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
	
	public List<PaymentTypeDTO> retrieve(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<PaymentTypeDTO> criteria = builder.createQuery(PaymentTypeDTO.class);
		Root<PaymentTypeDTO> root = criteria.from(PaymentTypeDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (name != null && !name.equals(""))
		{
			predicates.add(builder.like(root.get(PaymentTypeDTO_.name), "%" + name + "%"));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<PaymentTypeDTO> vObjPaymentType = session.createQuery(criteria).getResultList();

		return vObjPaymentType;
	}

	public PaymentTypeDTO retrieveByName(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<PaymentTypeDTO> criteria = builder.createQuery(PaymentTypeDTO.class);
		Root<PaymentTypeDTO> root = criteria.from(PaymentTypeDTO.class);
		criteria.where(builder.equal(root.get(PaymentTypeDTO_.name), name));
		List<PaymentTypeDTO> paymentTypes = session.createQuery(criteria).getResultList();

		if (paymentTypes == null || paymentTypes.size() == 0)
		{
			return null;
		}

		return paymentTypes.get(0);

	}

	public boolean delete(PaymentTypeDTO paymentType)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(paymentType);
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
}
