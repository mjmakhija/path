package com.emsays.path.dao;

import com.emsays.path.dto.PackageDTO;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

public class PackageSer
{

	Session session;
	PackageDAO package_DAO;

	public PackageSer(Session session)
	{
		this.session = session;
		package_DAO = new PackageDAO(session);
	}

	public boolean create(PackageDTO package_, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(package_, errorMsg))
		{
			return false;
		}

		return package_DAO.createOrUpdate(package_);
	}

	public boolean update(PackageDTO package_, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(package_, errorMsg))
		{
			return false;
		}

		return package_DAO.createOrUpdate(package_);
	}

	public List<PackageDTO> retrieve()
	{
		return package_DAO.retrieve(null);
	}

	public List<PackageDTO> retrieve(String name)
	{
		return package_DAO.retrieve(name);
	}

	public PackageDTO retrieveByName(String name)
	{
		return package_DAO.retrieveByName(name);
	}

	public boolean delete(PackageDTO package_)
	{
		return package_DAO.delete(package_);
	}

	private boolean checkIsValidCreate(PackageDTO package_, StringBuilder errorMsg)
	{
		if (!checkIsValid(package_, errorMsg))
		{
			return false;
		}

		PackageDTO package_Found = package_DAO.retrieveByName(package_.getName());
		if (package_Found != null)
		{
			JOptionPane.showMessageDialog(null, "Name already exists", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean checkIsValidUpdate(PackageDTO package_, StringBuilder errorMsg)
	{
		if (!checkIsValid(package_, errorMsg))
		{
			return false;
		}

		PackageDTO package_Found = package_DAO.retrieveByName(package_.getName());
		if (package_Found != null && package_.getId() != package_Found.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	private boolean checkIsValid(PackageDTO package_, StringBuilder errorMsg)
	{
		if (package_.getName() == null || package_.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
