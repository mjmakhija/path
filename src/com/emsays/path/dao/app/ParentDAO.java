package com.emsays.path.dao.app;

import com.emsays.path.Database;
import org.hibernate.Session;

public class ParentDAO
{

	protected Session vSession;

	protected void mOpenSession()
	{
		this.vSession = Database.getSessionApp();
	}

	protected void mCloseSession()
	{
		vSession.close();
	}
}
