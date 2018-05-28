package com.emsays.path.dao;

import com.emsays.path.dto.CustomerChangeLogDTO;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

class CustomerChangeLogDAO
{

	Session session;

	public CustomerChangeLogDAO(Session session)
	{
		this.session = session;
	}

	public List<CustomerChangeLogDTO> retrieve()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CustomerChangeLogDTO> criteria = builder.createQuery(CustomerChangeLogDTO.class);
		Root<CustomerChangeLogDTO> root = criteria.from(CustomerChangeLogDTO.class);
		criteria.select(root);
		List<CustomerChangeLogDTO> listObjects = session.createQuery(criteria).getResultList();

		return listObjects;
	}

}
