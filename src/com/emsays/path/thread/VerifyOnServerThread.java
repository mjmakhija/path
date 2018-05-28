package com.emsays.path.thread;

import com.emsays.path.GV;
import com.emsays.path.RegistrationHelper;
import org.hibernate.Session;

public class VerifyOnServerThread extends Thread
{

	RegistrationHelper registrationHelper;

	public VerifyOnServerThread(Session session)
	{
		registrationHelper = new RegistrationHelper(session);
	}

	@Override
	public void run()
	{

		registrationHelper.checkRegistration();

		boolean run = true;

		while (run)
		{

			registrationHelper.verifyOnServer();

			if (GV.getAppStatus() == GV.enmAppStatus.REGISTERED && GV.isVerifiedOnline)
			{
				run = false;
			}
			else if (GV.getAppStatus() == GV.enmAppStatus.UNREGISTERED)
			{
				run = false;
			}
			else
			{
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException ex)
				{
				}
			}
		}

	}

}
