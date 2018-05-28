package com.emsays.path.dao;

import com.emsays.path.dto.CustomerChangeTypeDTO;
import java.util.List;
import org.hibernate.Session;

public class CustomerChangeTypeSer
{

	Session session;

	public enum enmKeys
	{
		ACCOUNT_NO("account_no"),
		CUSTOMER_NAME("customer_name"),
		ADDRESS("address"),
		AREA("area"),
		MOBILE("mobile"),
		PAYMENT_TYPE("payment_type"),
		PACKAGE("package"),
		AMP("amp"),
		HOME("home"),
		REPAIR("repair"),
		DC("dc"),
		AMOUNT("amount"),
		OFFER("offer"),
		COLLECT_FROM("collect_from"),
		SUSPENDED("suspended");

		private final String text;

		/**
		 * @param text
		 */
		private enmKeys(final String text)
		{
			this.text = text;
		}

		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString()
		{
			return text;
		}
	}

	List<CustomerChangeTypeDTO> customerChangeTypes;
	CustomerChangeTypeDAO customerChangeTypeDAO;

	public CustomerChangeTypeSer(Session session)
	{
		this.session = session;
		customerChangeTypeDAO = new CustomerChangeTypeDAO(session);
		customerChangeTypes = customerChangeTypeDAO.retrieve(null);
	}

	public CustomerChangeTypeDTO get(enmKeys key)
	{
		if (key == null)
		{
			return null;
		}

		for (CustomerChangeTypeDTO customerChangeType : customerChangeTypes)
		{
			if (customerChangeType.getName().equals(key.toString()))
			{
				return customerChangeType;
			}
		}
		return null;
	}
	
	public List<CustomerChangeTypeDTO> get()
	{
		return  customerChangeTypes;
	}
	

}
