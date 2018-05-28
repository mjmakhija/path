package com.emsays.path.dao;

import com.emsays.path.dto.CollectFromDTO;
import com.emsays.path.dto.CollectFromDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class CollectFromDAO
{

	Session session;

	public CollectFromDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(CollectFromDTO collectFrom)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.saveOrUpdate(collectFrom);
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

	public List<CollectFromDTO> retrieve(String vName)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<CollectFromDTO> criteria = builder.createQuery(CollectFromDTO.class);
		Root<CollectFromDTO> root = criteria.from(CollectFromDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (vName != null && !vName.equals(""))
		{
			predicates.add(builder.like(root.get(CollectFromDTO_.name), "%" + vName + "%"));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<CollectFromDTO> vObjCollectFrom = session.createQuery(criteria).getResultList();

		return vObjCollectFrom;
	}

	public CollectFromDTO retrieveByName(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<CollectFromDTO> criteria = builder.createQuery(CollectFromDTO.class);
		Root<CollectFromDTO> root = criteria.from(CollectFromDTO.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get(CollectFromDTO_.name), name));

		List<CollectFromDTO> vObjCollectFrom = session.createQuery(criteria).getResultList();

		return vObjCollectFrom.isEmpty() ? null : vObjCollectFrom.get(0);
	}

	public boolean delete(CollectFromDTO collectFrom)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(collectFrom);
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
