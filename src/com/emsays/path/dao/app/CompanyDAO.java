package com.emsays.path.dao.app;

import com.emsays.path.dto.app.CompanyDTO;
import java.util.*;
import javax.persistence.criteria.*;

public class CompanyDAO extends ParentDAO
{

	public List<CompanyDTO> mRetrieve()
	{

		this.mOpenSession();
		CriteriaBuilder builder = vSession.getCriteriaBuilder();

		CriteriaQuery<CompanyDTO> criteria = builder.createQuery(CompanyDTO.class);
		Root<CompanyDTO> root = criteria.from(CompanyDTO.class);
		criteria.select(root);
		List<CompanyDTO> listCompanys = vSession.createQuery(criteria).getResultList();
		this.mCloseSession();
		return listCompanys;
	}

	public boolean mSave(CompanyDTO objCompany)
	{
		this.mOpenSession();
		boolean result;

		try
		{
			vSession.beginTransaction();
			vSession.saveOrUpdate(objCompany);
			vSession.getTransaction().commit();
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}
		finally
		{
			this.mCloseSession();
		}

		return result;

	}

	public boolean mDelete(CompanyDTO objCompany)
	{
		this.mOpenSession();

		boolean result;

		try
		{
			vSession.beginTransaction();
			vSession.delete(objCompany);
			vSession.getTransaction().commit();
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}
		finally
		{
			this.mCloseSession();
		}

		return result;

	}

}
