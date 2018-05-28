package com.emsays.path.dao;

import com.emsays.path.dto.CompanyYearInfoDTO;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;

class CompanyYearInfoDAO
{

	Session session;

	List<CompanyYearInfoDTO> companyYearInfoDTOs;

	public CompanyYearInfoDAO(Session session)
	{
		this.session = session;
	}

	public boolean update(CompanyYearInfoDTO objUnit)
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

	public List<CompanyYearInfoDTO> retrieve()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<CompanyYearInfoDTO> criteria = builder.createQuery(CompanyYearInfoDTO.class);
		Root<CompanyYearInfoDTO> root = criteria.from(CompanyYearInfoDTO.class);
		criteria.select(root);

		return session.createQuery(criteria).getResultList();

	}
}
