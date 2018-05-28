package com.emsays.path.dao.app;

import com.emsays.path.dto.app.AppInfoDTO;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

class AppInfoDAO
{

	Session session;

	List<AppInfoDTO> appInfoDTOs;

	public AppInfoDAO(Session session)
	{
		this.session = session;
	}

	public boolean update(AppInfoDTO objUnit)
	{
		boolean result;

		try
		{
			session.saveOrUpdate(objUnit);
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}

		return result;

	}

	public List<AppInfoDTO> retrieve()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<AppInfoDTO> criteria = builder.createQuery(AppInfoDTO.class);
		Root<AppInfoDTO> root = criteria.from(AppInfoDTO.class);
		criteria.select(root);

		return session.createQuery(criteria).getResultList();

	}
}
