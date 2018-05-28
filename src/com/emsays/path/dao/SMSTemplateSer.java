package com.emsays.path.dao;

import com.emsays.path.dto.SMSTemplateDTO;
import java.util.List;
import org.hibernate.Session;

public class SMSTemplateSer
{

	Session session;
	SMSTemplateDAO smsTemplateDAO;

	public SMSTemplateSer(Session session)
	{
		this.session = session;
		smsTemplateDAO = new SMSTemplateDAO(session);
	}

	public boolean create(SMSTemplateDTO smsTemplate, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(smsTemplate, errorMsg))
		{
			return false;
		}

		session.beginTransaction();

		boolean result = smsTemplateDAO.createOrUpdate(smsTemplate);

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

	public boolean update(SMSTemplateDTO smsTemplate, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(smsTemplate, errorMsg))
		{
			return false;
		}

		session.beginTransaction();

		boolean result = smsTemplateDAO.createOrUpdate(smsTemplate);

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

	public List<SMSTemplateDTO> retrieve()
	{
		return smsTemplateDAO.retrieve(null);
	}

	public List<SMSTemplateDTO> retrieve(String name)
	{
		return smsTemplateDAO.retrieve(name);
	}

	public SMSTemplateDTO retrieveByName(String name)
	{
		return smsTemplateDAO.retrieveByName(name);
	}

	public boolean delete(SMSTemplateDTO smsTemplate)
	{
		return smsTemplateDAO.delete(smsTemplate);
	}

	private boolean checkIsValidCreate(SMSTemplateDTO smsTemplate, StringBuilder errorMsg)
	{
		if (!checkIsValid(smsTemplate, errorMsg))
		{
			return false;
		}

		SMSTemplateDTO smsTemplateFound = smsTemplateDAO.retrieveByName(smsTemplate.getName());
		if (smsTemplateFound != null)
		{
			errorMsg.append("Name already exists");
			return false;
		}

		return true;
	}

	private boolean checkIsValidUpdate(SMSTemplateDTO smsTemplate, StringBuilder errorMsg)
	{
		if (!checkIsValid(smsTemplate, errorMsg))
		{
			return false;
		}

		SMSTemplateDTO smsTemplateFound = smsTemplateDAO.retrieveByName(smsTemplate.getName());
		if (smsTemplateFound != null && smsTemplate.getId() != smsTemplateFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	private boolean checkIsValid(SMSTemplateDTO smsTemplate, StringBuilder errorMsg)
	{

		if (smsTemplate == null)
		{
			errorMsg.append("SMSTemplate dto is null");
			return false;
		}

		if (smsTemplate.getName() == null || smsTemplate.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
