package com.emsays.path.dao;

import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.AreaDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class AreaDAO
{

	Session session;

	public AreaDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(AreaDTO area)
	{
		boolean result;

		try
		{
			session.saveOrUpdate(area);
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}

		return result;

	}
	
	public List<AreaDTO> retrieve(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<AreaDTO> criteria = builder.createQuery(AreaDTO.class);
		Root<AreaDTO> root = criteria.from(AreaDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (name != null && !name.equals(""))
		{
			predicates.add(builder.like(root.get(AreaDTO_.name), "%" + name + "%"));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<AreaDTO> vObjArea = session.createQuery(criteria).getResultList();

		return vObjArea;
	}

	public AreaDTO retrieveByName(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<AreaDTO> criteria = builder.createQuery(AreaDTO.class);
		Root<AreaDTO> root = criteria.from(AreaDTO.class);
		criteria.where(builder.equal(root.get(AreaDTO_.name), name));
		List<AreaDTO> areas = session.createQuery(criteria).getResultList();

		if (areas == null || areas.size() == 0)
		{
			return null;
		}

		return areas.get(0);

	}

	public boolean delete(AreaDTO area)
	{
		boolean result;

		try
		{
			session.delete(area);
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}

		return result;

	}
}
