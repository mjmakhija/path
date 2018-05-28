package com.emsays.path.dao.app;

import com.emsays.path.dto.app.UpdateLogDTO;
import java.util.*;
import org.hibernate.Session;

public class UpdateLogSer
{

	UpdateLogDAO updateLogDAO;
	Session session;

	public UpdateLogSer(Session session)
	{
		this.session = session;
		updateLogDAO = new UpdateLogDAO(session);
	}

	public UpdateLogDTO getLatestInstalledVersion()
	{
		return updateLogDAO.retrieveLatestInstalledVersion();
	}

	public boolean createOrUpdate(UpdateLogDTO vObj)
	{
		boolean result;

		session.beginTransaction();

		result = updateLogDAO.createOrUpdate(vObj);

		if (result)
		{
			session.getTransaction().commit();
		}
		else
		{
			session.getTransaction().rollback();
		}

		return result;

	}

	public List<UpdateLogDTO> retrieve()
	{
		return updateLogDAO.retrieve();
	}

}
