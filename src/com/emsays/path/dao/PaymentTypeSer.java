package com.emsays.path.dao;

import com.emsays.path.dto.PaymentTypeDTO;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

public class PaymentTypeSer
{

	Session session;
	PaymentTypeDAO paymentTypeDAO;

	public PaymentTypeSer(Session session)
	{
		this.session = session;
		paymentTypeDAO = new PaymentTypeDAO(session);
	}

	public boolean create(PaymentTypeDTO paymentType, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(paymentType, errorMsg))
		{
			return false;
		}

		return paymentTypeDAO.createOrUpdate(paymentType);
	}

	public boolean update(PaymentTypeDTO paymentType, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(paymentType, errorMsg))
		{
			return false;
		}

		return paymentTypeDAO.createOrUpdate(paymentType);
	}

	public List<PaymentTypeDTO> retrieve()
	{
		return paymentTypeDAO.retrieve(null);
	}

	public List<PaymentTypeDTO> retrieve(String name)
	{
		return paymentTypeDAO.retrieve(name);
	}

	public PaymentTypeDTO retrieveByName(String name)
	{
		return paymentTypeDAO.retrieveByName(name);
	}

	public boolean delete(PaymentTypeDTO paymentType)
	{
		return paymentTypeDAO.delete(paymentType);
	}

	private boolean checkIsValidCreate(PaymentTypeDTO paymentType, StringBuilder errorMsg)
	{
		if (!checkIsValid(paymentType, errorMsg))
		{
			return false;
		}

		PaymentTypeDTO paymentTypeFound = paymentTypeDAO.retrieveByName(paymentType.getName());
		if (paymentTypeFound != null)
		{
			JOptionPane.showMessageDialog(null, "Name already exists", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean checkIsValidUpdate(PaymentTypeDTO paymentType, StringBuilder errorMsg)
	{
		if (!checkIsValid(paymentType, errorMsg))
		{
			return false;
		}

		PaymentTypeDTO paymentTypeFound = paymentTypeDAO.retrieveByName(paymentType.getName());
		if (paymentTypeFound != null && paymentType.getId() != paymentTypeFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	private boolean checkIsValid(PaymentTypeDTO paymentType, StringBuilder errorMsg)
	{
		if (paymentType.getName() == null || paymentType.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
