package com.emsays.path.dao;

import com.emsays.path.dto.AccountDTO;
import java.util.List;
import org.hibernate.Session;

public class AccountSer
{

	Session session;

	public enum enmKeys
	{
		NA("na"),
		WRITE_OFF("write_off");

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

	List<AccountDTO> accounts;
	AccountDAO accountDAO;

	public AccountSer(Session session)
	{
		this.session = session;
		accountDAO = new AccountDAO(session);
		accounts = accountDAO.retrieve(null);
	}

	public AccountDTO get(enmKeys key)
	{
		if (key == null)
		{
			return null;
		}

		for (AccountDTO account : accounts)
		{
			if (account.getName().equals(key.toString()))
			{
				return account;
			}
		}
		return null;
	}

	public List<AccountDTO> get()
	{
		return accounts;
	}

}
