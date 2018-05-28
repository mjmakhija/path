package com.emsays.path.dao;

import com.emsays.path.dto.CustomerChangeLogDTO;
import java.util.List;
import org.hibernate.Session;

public class CustomerChangeLogSer
{

	Session session;
	CustomerChangeLogDAO customerChangeLogDAO;

	public CustomerChangeLogSer(Session session)
	{
		this.session = session;
		customerChangeLogDAO = new CustomerChangeLogDAO(session);
	}

	public List<CustomerChangeLogDTO> retrieve()
	{
		return customerChangeLogDAO.retrieve();
	}
}
