package com.emsays.path.dao.app;

import com.emsays.path.dto.app.UpdateLogDTO;
import com.emsays.path.dto.app.UpdateLogDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class UpdateLogDAO
{

	Session session;

	public UpdateLogDAO(Session session)
	{
		this.session = session;
	}
	
	

	public boolean createOrUpdate(UpdateLogDTO vObj)
	{
		boolean result;

		try
		{
			session.saveOrUpdate(vObj);
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}

		return result;

	}

	public List<UpdateLogDTO> retrieve()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<UpdateLogDTO> criteria = builder.createQuery(UpdateLogDTO.class);
		Root<UpdateLogDTO> root = criteria.from(UpdateLogDTO.class);
		criteria.select(root);
		List<UpdateLogDTO> listObjects = session.createQuery(criteria).getResultList();

		return listObjects;
	}

	public UpdateLogDTO retrieveLatestInstalledVersion()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<UpdateLogDTO> criteria = builder.createQuery(UpdateLogDTO.class);
		Root<UpdateLogDTO> root = criteria.from(UpdateLogDTO.class);

		criteria.orderBy(builder.desc(root.get(UpdateLogDTO_.id)));
		List<UpdateLogDTO> listUpdateLogDTOs = session.createQuery(criteria).getResultList();

		return listUpdateLogDTOs.get(0);
	}

}
