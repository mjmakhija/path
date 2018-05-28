package com.emsays.path.dao.app;

import com.emsays.path.dto.app.AppInfoDTO;
import java.util.List;
import org.hibernate.Session;

public class AppInfoSer
{

	public enum AppInfoKey
	{
		StatusId("status_id"),
		InstanceKey("instance_key"),
		ClientKey("client_key"),
		AgentKey("agent_key"),
		Name("name"),
		UpdaterVersion("updater_version"),
		UpdateStatus("update_status");

		private final String name;

		private AppInfoKey(String s)
		{
			name = s;
		}

		public boolean equalsName(String otherName)
		{
			// (otherName == null) check is not needed because name.equals(null) returns false 
			return name.equals(otherName);
		}

		public String toString()
		{
			return this.name;
		}
	}

	List<AppInfoDTO> appInfoDTOs;
	AppInfoDAO appInfoDAO;
	Session session;

	public AppInfoSer(Session session)
	{
		this.session = session;
		appInfoDAO = new AppInfoDAO(session);
		appInfoDTOs = appInfoDAO.retrieve();
	}

	public String get(AppInfoKey vAppProperty)
	{
		for (AppInfoDTO appInfoDTO : appInfoDTOs)
		{
			if (vAppProperty.toString().equals(appInfoDTO.getInfoKey()))
			{
				return String.valueOf(appInfoDTO.getInfoValue());
			}
		}
		return null;
	}

	public boolean set(AppInfoKey key, String value)
	{
		for (AppInfoDTO appInfoDTO : appInfoDTOs)
		{
			if (key.toString().equals(appInfoDTO.getInfoKey()))
			{

				appInfoDTO.setInfoValue(value);
				return update(appInfoDTO);

			}
		}

		return false;

	}

	public boolean update(AppInfoDTO appInfoDTO)
	{

		boolean result;

		session.beginTransaction();

		result = appInfoDAO.update(appInfoDTO);

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
}
