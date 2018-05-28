package com.emsays.path.dao;

import com.emsays.path.dto.CollectFromDTO;
import java.util.List;
import org.hibernate.Session;

public class CollectFromSer
{

	Session session;
	CollectFromDAO collectFromDAO;

	public CollectFromSer(Session session)
	{
		this.session = session;
		collectFromDAO = new CollectFromDAO(session);
	}

	public boolean create(CollectFromDTO collectFrom, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(collectFrom, errorMsg))
		{
			return false;
		}

		return collectFromDAO.createOrUpdate(collectFrom);
	}

	public boolean update(CollectFromDTO collectFrom, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(collectFrom, errorMsg))
		{
			return false;
		}

		return collectFromDAO.createOrUpdate(collectFrom);
	}

	public List<CollectFromDTO> retrieve()
	{
		return collectFromDAO.retrieve(null);
	}

	public List<CollectFromDTO> retrieve(String name)
	{
		return collectFromDAO.retrieve(name);
	}

	public CollectFromDTO retrieveByName(String name)
	{
		return collectFromDAO.retrieveByName(name);
	}

	public boolean delete(CollectFromDTO collectFrom)
	{
		return collectFromDAO.delete(collectFrom);
	}

	private boolean checkIsValidCreate(CollectFromDTO collectFrom, StringBuilder errorMsg)
	{
		if (!checkIsValid(collectFrom, errorMsg))
		{
			return false;
		}

		CollectFromDTO collectFromFound = collectFromDAO.retrieveByName(collectFrom.getName());
		if (collectFromFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		return true;
	}

	private boolean checkIsValidUpdate(CollectFromDTO collectFrom, StringBuilder errorMsg)
	{
		if (!checkIsValid(collectFrom, errorMsg))
		{
			return false;
		}

		CollectFromDTO collectFromFound = collectFromDAO.retrieveByName(collectFrom.getName());
		if (collectFromFound != null && collectFrom.getId() != collectFromFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	private boolean checkIsValid(CollectFromDTO collectFrom, StringBuilder errorMsg)
	{
		if (collectFrom == null)
		{
			errorMsg.append("Collect from object is null");
			return false;
		}

		if (collectFrom.getName() == null || collectFrom.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
