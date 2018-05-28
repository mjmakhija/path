package com.emsays.path;

import com.emsays.path.dao.app.AppInfoSer;
import com.emsays.path.thread.StartupThread;
import com.emsays.path.gui.SplashScreen;
import com.emsays.path.gui.frmWrapper;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import javax.swing.*;
//import org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel;

public class Main
{

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{

		SplashScreen abc = new SplashScreen();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		abc.setLocation(dim.width / 2 - abc.getSize().width / 2, dim.height / 2 - abc.getSize().height / 2);

		abc.setVisible(true);

		Thread t = new StartupThread(abc.getLblStatus());

		t.start();
		try
		{
			t.join();
			abc.getLblStatus().setText("All set");
		}
		catch (InterruptedException ex)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, "main", "main", ex.getMessage());
			CommonUIActions.showMessageBox("Error starting the app");
			GV.closeApp();
		}

		if (new AppInfoSer(Database.getSessionApp()).get(AppInfoSer.AppInfoKey.UpdateStatus) == "2")
		{
			GV.startUpdateManager();
			return;
		}

		//t = new VerifyOnServerThread(Database.getSessionApp());
		//t.start();
		abc.dispose();

		//Thread t2 = new UpdateThread(Database.getSessionApp());
		//t2.start();
		// 2. Set app status
		//AppInfo.setAppStatus();
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				new frmWrapper();
			}
		});

	}

}
