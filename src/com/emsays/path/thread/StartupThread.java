package com.emsays.path.thread;

import com.emsays.path.CommonUIActions;
import com.emsays.path.Database;
import com.emsays.path.GV;
import com.emsays.path.LoggerWrapper;
import com.emsays.path.RegistrationHelper;
import java.io.File;
import java.util.logging.Level;
import javax.swing.JLabel;
import org.apache.commons.io.FileUtils;

public class StartupThread extends Thread
{

	JLabel label;
	RegistrationHelper registrationHelper;

	// 1. Start the app
	/**
	 *
	 */
	public StartupThread(JLabel label)
	{
		this.label = label;
	}

	@Override
	public void run()
	{

		label.setText("Cleaning temperory directory...");
		cleanTempDirectory();

		label.setText("Starting database...");
		startDatabase();

		new RegistrationHelper(Database.getSessionApp()).checkRegistration();
	}

	private void cleanTempDirectory()
	{
		try
		{
			FileUtils.cleanDirectory(new File(GV.TEMP_DIR));
		}
		catch (Exception e)
		{
			LoggerWrapper.getInstance().logp(Level.WARNING, StartupThread.class.getName(), null, e.getMessage());
		}

	}

	private void startDatabase()
	{
		if (!Database.startDBServer())
		{
			CommonUIActions.showMessageBox("cannot initialize database");
			GV.closeApp();
		}
	}

}
