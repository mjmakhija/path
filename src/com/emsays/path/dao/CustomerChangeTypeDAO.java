package com.emsays.path.dao;

import com.emsays.path.dto.CustomerChangeTypeDTO;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

class CustomerChangeTypeDAO
{

	Session session;

	public CustomerChangeTypeDAO(Session session)
	{
		this.session = session;
	}

	public List<CustomerChangeTypeDTO> retrieve(String name)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<CustomerChangeTypeDTO> criteria = builder.createQuery(CustomerChangeTypeDTO.class);
		Root<CustomerChangeTypeDTO> root = criteria.from(CustomerChangeTypeDTO.class);
		criteria.select(root);
		return session.createQuery(criteria).getResultList();

	}

}
