package com.emsays.path.dao;

import com.emsays.path.dto.PackageDTO;
import com.emsays.path.dto.PackageDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class PackageDAO
{

	Session session;

	public PackageDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(PackageDTO package_)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.saveOrUpdate(package_);
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

	public List<PackageDTO> retrieve(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<PackageDTO> criteria = builder.createQuery(PackageDTO.class);
		Root<PackageDTO> root = criteria.from(PackageDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (name != null && !name.equals(""))
		{
			predicates.add(builder.like(root.get(PackageDTO_.name), "%" + name + "%"));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<PackageDTO> vObjPackage = session.createQuery(criteria).getResultList();

		return vObjPackage;
	}

	public PackageDTO retrieveByName(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<PackageDTO> criteria = builder.createQuery(PackageDTO.class);
		Root<PackageDTO> root = criteria.from(PackageDTO.class);
		criteria.where(builder.equal(root.get(PackageDTO_.name), name));
		List<PackageDTO> package_s = session.createQuery(criteria).getResultList();

		if (package_s == null || package_s.size() == 0)
		{
			return null;
		}

		return package_s.get(0);

	}

	public boolean delete(PackageDTO package_)
	{
		boolean result;

		try
		{
			session.beginTransaction();
			session.delete(package_);
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
