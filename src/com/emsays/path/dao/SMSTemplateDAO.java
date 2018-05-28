package com.emsays.path.dao;

import com.emsays.path.dto.SMSTemplateDTO;
import com.emsays.path.dto.SMSTemplateDTO_;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

class SMSTemplateDAO
{

	Session session;

	public SMSTemplateDAO(Session session)
	{
		this.session = session;
	}

	public boolean createOrUpdate(SMSTemplateDTO smsTemplate)
	{
		boolean result;

		try
		{
			session.saveOrUpdate(smsTemplate);
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}

		return result;

	}

	public List<SMSTemplateDTO> retrieve(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<SMSTemplateDTO> criteria = builder.createQuery(SMSTemplateDTO.class);
		Root<SMSTemplateDTO> root = criteria.from(SMSTemplateDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (name != null && !name.equals(""))
		{
			predicates.add(builder.like(root.get(SMSTemplateDTO_.name), "%" + name + "%"));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<SMSTemplateDTO> vObjSMSTemplate = session.createQuery(criteria).getResultList();

		return vObjSMSTemplate;
	}

	public SMSTemplateDTO retrieveByName(String name)
	{
		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<SMSTemplateDTO> criteria = builder.createQuery(SMSTemplateDTO.class);
		Root<SMSTemplateDTO> root = criteria.from(SMSTemplateDTO.class);
		criteria.where(builder.equal(root.get(SMSTemplateDTO_.name), name));
		List<SMSTemplateDTO> smsTemplates = session.createQuery(criteria).getResultList();

		if (smsTemplates == null || smsTemplates.size() == 0)
		{
			return null;
		}

		return smsTemplates.get(0);

	}

	public boolean delete(SMSTemplateDTO smsTemplate)
	{
		boolean result;

		try
		{
			session.delete(smsTemplate);
			result = true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			result = false;
		}

		return result;

	}
}
