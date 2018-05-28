package com.emsays.path.dao;

import com.emsays.path.dto.OfferDTO;
import com.emsays.path.dto.OfferDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class OfferDAO
{ 

	Session session;

	public OfferDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(OfferDTO offer)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.saveOrUpdate(offer);
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

	public List<OfferDTO> retrieve(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<OfferDTO> criteria = builder.createQuery(OfferDTO.class);
		Root<OfferDTO> root = criteria.from(OfferDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (name != null && !name.equals(""))
		{
			predicates.add(builder.like(root.get(OfferDTO_.name), "%" + name + "%"));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<OfferDTO> vObjOffer = session.createQuery(criteria).getResultList();

		return vObjOffer;
	}

	public OfferDTO retrieveByName(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<OfferDTO> criteria = builder.createQuery(OfferDTO.class);
		Root<OfferDTO> root = criteria.from(OfferDTO.class);
		criteria.where(builder.equal(root.get(OfferDTO_.name), name));
		List<OfferDTO> offers = session.createQuery(criteria).getResultList();

		if (offers == null || offers.size() == 0)
		{
			return null;
		}

		return offers.get(0);

	}

	public boolean delete(OfferDTO offer)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(offer);
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
