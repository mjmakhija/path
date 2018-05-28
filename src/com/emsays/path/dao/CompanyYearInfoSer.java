package com.emsays.path.dao;

import com.emsays.path.dto.CompanyYearInfoDTO;
import java.util.List;
import org.hibernate.Session;

public class CompanyYearInfoSer
{

	public enum CompanyYearInfoKey
	{
		CompanyName("company_name"),
		Address("address"),
		ContactNo("contact_no");

		private final String name;

		private CompanyYearInfoKey(String s)
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

	List<CompanyYearInfoDTO> companyYearInfoDTOs;
	CompanyYearInfoDAO companyYearInfoDAO;
	Session session;

	public CompanyYearInfoSer(Session session)
	{
		this.session = session;
		companyYearInfoDAO = new CompanyYearInfoDAO(session);
		companyYearInfoDTOs = companyYearInfoDAO.retrieve();
	}

	public String get(CompanyYearInfoKey vAppProperty)
	{
		for (CompanyYearInfoDTO companyYearInfoDTO : companyYearInfoDTOs)
		{
			if (vAppProperty.toString().equals(companyYearInfoDTO.getInfoKey()))
			{
				return String.valueOf(companyYearInfoDTO.getInfoValue());
			}
		}
		return null;
	}

	public boolean set(CompanyYearInfoKey key, String value)
	{
		for (CompanyYearInfoDTO companyYearInfoDTO : companyYearInfoDTOs)
		{
			if (key.toString().equals(companyYearInfoDTO.getInfoKey()))
			{

				companyYearInfoDTO.setInfoValue(value);
				return update(companyYearInfoDTO);

			}
		}

		return false;

	}

	public boolean update(CompanyYearInfoDTO companyYearInfoDTO)
	{

		boolean result;

		session.beginTransaction();

		result = companyYearInfoDAO.update(companyYearInfoDTO);

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
