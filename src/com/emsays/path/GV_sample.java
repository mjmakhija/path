package com.emsays.path;

import com.emsays.path.gui.pnlGateway;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GV_sample
{

	public enum enmAppStatus
	{
		REGISTERED, UNREGISTERED
	}

	private static enmAppStatus vAppStatus;

	public static boolean isVerifiedOnline = false;

	public static final String APP_TYPE_ID = "";

	public static final String INSTALLATION_DIR = System.getProperty("user.dir");
	//public static final String MYSQL_DIR = ""; //DEBUG
	public static final String MYSQL_DIR = new File(INSTALLATION_DIR).getParent() + "/db"; //RUN
	public static final String TEMP_DIR = INSTALLATION_DIR + "/temp";
	public static final String REPORT_DIR = INSTALLATION_DIR + "/report";
	public static final String IMAGE_DIR = INSTALLATION_DIR + "/image";
	public static final String OTHER_DIR = INSTALLATION_DIR + "/other";

	//public static final String CHROME_DRIVER_PATH = INSTALLATION_DIR; //DEBUG
	public static final String CHROME_DRIVER_PATH = new File(INSTALLATION_DIR).getParent(); //RUN

	public static final String APP_DATA_DIR = System.getenv("appdata");

	public static final String SQLITE_PATH = TEMP_DIR + "/sqlite.db";

	public static final String DB_URL = "jdbc:mysql://localhost/";
	public static final String DB_PREFIX = "";
	public static final String DB_USERNAME = "";
	public static final String DB_PASSWORD = "";
	public static final String DB_TEST = "";
	public static final String DB_APP = "";
	public static final String DB_BLANK = "";

	public static final String API_BASE_URL = "";

	public static final String APP_NAME = "Path";

	private static SeleniumHelper seleniumHelper = null;
	private static Server server;

	public static void setAppStatus(enmAppStatus vAppStatus)
	{
		GV_sample.vAppStatus = vAppStatus;
	}

	public static enmAppStatus getAppStatus()
	{
		return vAppStatus;
	}

	public static SeleniumHelper getSeleniumHelper()
	{
		if (seleniumHelper == null)
		{
			seleniumHelper = new SeleniumHelper();
		}
		return seleniumHelper;
	}

	public static Server getServer()
	{
		if (server == null)
		{
			server = new Server();
		}
		return server;
	}

	public static void disposeServer()
	{
		if (server != null)
		{
			server.stopServer();
		}
	}

	public static void closeApp()
	{
		Database.closeDBServer();
		disposeServer();
		LoggerWrapper.close();
		System.exit(0);
	}

	public static void startUpdateManager()
	{
		try
		{
			String[] vExecuteCmd = new String[]
			{
				"cmd.exe",
				"/c",
				GV.INSTALLATION_DIR + "/um.exe"
			};

			Runtime.getRuntime().exec(vExecuteCmd);

			closeApp();
		}
		catch (IOException ex)
		{
			Logger.getLogger(pnlGateway.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
