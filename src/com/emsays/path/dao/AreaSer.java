package com.emsays.path.dao;

import com.emsays.path.dto.AreaDTO;
import java.util.List;
import org.hibernate.Session;

public class AreaSer
{

	Session session;
	AreaDAO areaDAO;

	public AreaSer(Session session)
	{
		this.session = session;
		areaDAO = new AreaDAO(session);
	}

	public boolean create(AreaDTO area, StringBuilder errorMsg)
	{
		boolean result;

		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(area, errorMsg))
		{
			return false;
		}

		session.beginTransaction();

		result = areaDAO.createOrUpdate(area);

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

	public boolean update(AreaDTO area, StringBuilder errorMsg)
	{
		boolean result;

		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(area, errorMsg))
		{
			return false;
		}

		session.beginTransaction();

		result = areaDAO.createOrUpdate(area);

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

	public List<AreaDTO> retrieve()
	{
		return areaDAO.retrieve(null);
	}

	public List<AreaDTO> retrieve(String name)
	{
		return areaDAO.retrieve(name);
	}

	public AreaDTO retrieveByName(String name)
	{
		return areaDAO.retrieveByName(name);
	}

	public boolean delete(AreaDTO area)
	{
		boolean result;

		session.beginTransaction();

		result = areaDAO.delete(area);

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

	private boolean checkIsValidCreate(AreaDTO area, StringBuilder errorMsg)
	{
		if (!checkIsValid(area, errorMsg))
		{
			return false;
		}

		AreaDTO areaFound = areaDAO.retrieveByName(area.getName());
		if (areaFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		return true;
	}

	private boolean checkIsValidUpdate(AreaDTO area, StringBuilder errorMsg)
	{
		if (!checkIsValid(area, errorMsg))
		{
			return false;
		}

		AreaDTO areaFound = areaDAO.retrieveByName(area.getName());
		if (areaFound != null && area.getId() != areaFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	private boolean checkIsValid(AreaDTO area, StringBuilder errorMsg)
	{

		if (area == null)
		{
			errorMsg.append("Area dto is null");
			return false;
		}

		if (area.getName() == null || area.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
