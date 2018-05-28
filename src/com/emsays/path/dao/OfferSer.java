package com.emsays.path.dao;

import com.emsays.path.dto.OfferDTO;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

public class OfferSer
{

	Session session;
	OfferDAO offerDAO;

	public OfferSer(Session session)
	{
		this.session = session;
		offerDAO = new OfferDAO(session);
	}

	public boolean create(OfferDTO offer, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidCreate(offer, errorMsg))
		{
			return false;
		}

		return offerDAO.createOrUpdate(offer);
	}

	public boolean update(OfferDTO offer, StringBuilder errorMsg)
	{
		errorMsg = errorMsg == null ? new StringBuilder() : errorMsg;

		if (!checkIsValidUpdate(offer, errorMsg))
		{
			return false;
		}

		return offerDAO.createOrUpdate(offer);
	}

	public List<OfferDTO> retrieve()
	{
		return offerDAO.retrieve(null);
	}

	public List<OfferDTO> retrieve(String name)
	{
		return offerDAO.retrieve(name);
	}

	public OfferDTO retrieveByName(String name)
	{
		return offerDAO.retrieveByName(name);
	}

	public boolean delete(OfferDTO offer)
	{
		return offerDAO.delete(offer);
	}

	private boolean checkIsValidCreate(OfferDTO offer, StringBuilder errorMsg)
	{
		if (!checkIsValid(offer, errorMsg))
		{
			return false;
		}

		OfferDTO offerFound = offerDAO.retrieveByName(offer.getName());
		if (offerFound != null)
		{
			JOptionPane.showMessageDialog(null, "Name already exists", "Message", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		return true;
	}

	private boolean checkIsValidUpdate(OfferDTO offer, StringBuilder errorMsg)
	{
		if (!checkIsValid(offer, errorMsg))
		{
			return false;
		}

		OfferDTO offerFound = offerDAO.retrieveByName(offer.getName());
		if (offerFound != null && offer.getId() != offerFound.getId())
		{
			errorMsg.append("Name already exists");
			return false;
		}
		return true;
	}

	private boolean checkIsValid(OfferDTO offer, StringBuilder errorMsg)
	{
		if (offer.getName() == null || offer.getName().equals(""))
		{
			errorMsg.append("Name is required");
			return false;
		}

		return true;
	}
}
