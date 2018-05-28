package com.emsays.path.dao;

import com.emsays.path.dto.AccountDTO;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

class AccountDAO
{

	Session session;

	public AccountDAO(Session session)
	{
		this.session = session;
	}

	public List<AccountDTO> retrieve(String name)
	{

		
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<AccountDTO> criteria = builder.createQuery(AccountDTO.class);
		Root<AccountDTO> root = criteria.from(AccountDTO.class);
		criteria.select(root);
		
		return session.createQuery(criteria).getResultList();

	}

}
