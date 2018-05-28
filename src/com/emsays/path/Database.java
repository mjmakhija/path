package com.emsays.path;

import com.emsays.path.dto.AccountDTO;
import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.CollectFromDTO;
import com.emsays.path.dto.CompanyYearInfoDTO;
import com.emsays.path.dto.CustomerChangeLogDTO;
import com.emsays.path.dto.CustomerChangeTypeDTO;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.OfferDTO;
import com.emsays.path.dto.PackageDTO;
import com.emsays.path.dto.PaymentTypeDTO;
import com.emsays.path.dto.ReceiptDTO;
import com.emsays.path.dto.SMSTemplateDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Database
{

	private static Process p;

	public static SessionFactory vSessionFactoryApp;
	public static SessionFactory vSessionFactoryCompanyYear;
	public static int vSelectedYear;

	protected static Session vSessionCompanyYear;
	protected static Session vSessionApp;

	private static DbHelper dbHelper;

	private static boolean startSessionFactoryApp()
	{
		try
		{
			vSessionFactoryApp = new Configuration()
					.configure()
					.buildSessionFactory();

			return true;
		}
		catch (Exception ex)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, ex.getMessage());
			return false;
		}
	}

	public static void startSessionFactoryCompanyYear(String vDBName)
	{
		vDBName = addDBPrefix(vDBName);

		if (vSessionFactoryCompanyYear != null && vSessionFactoryCompanyYear.isOpen())
		{
			vSessionFactoryCompanyYear.close();
		}

		vSessionFactoryCompanyYear = new Configuration()
				.configure()
				.setProperty("hibernate.connection.url", GV.DB_URL + vDBName)
				.addAnnotatedClass(AccountDTO.class)
				.addAnnotatedClass(AreaDTO.class)
				.addAnnotatedClass(CollectFromDTO.class)
				.addAnnotatedClass(CompanyYearInfoDTO.class)
				.addAnnotatedClass(CustomerChangeLogDTO.class)
				.addAnnotatedClass(CustomerChangeTypeDTO.class)
				.addAnnotatedClass(CustomerDTO.class)
				.addAnnotatedClass(InvoiceDTO.class)
				.addAnnotatedClass(InvoiceReceiptDTO.class)
				.addAnnotatedClass(OfferDTO.class)
				.addAnnotatedClass(PackageDTO.class)
				.addAnnotatedClass(PaymentTypeDTO.class)
				.addAnnotatedClass(ReceiptDTO.class)
				.addAnnotatedClass(SMSTemplateDTO.class)
				.buildSessionFactory();

		dbHelper.close();
	}

	public static boolean startDBServer()
	{
		LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), "startDBServer", "Starting");
		dbHelper = new DbHelper(GV.DB_URL, GV.DB_USERNAME, GV.DB_PASSWORD);

		if (!isMysqlRunning())
		{

			try
			{
				startMysql();
			}
			catch (Exception ex)
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, ex.getMessage());
				return false;
			}

			if (!dbHelper.connect())
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, "Cant connect to mysql after starting mysql");
				return false;
			}
		}

		String dbName = addDBPrefix(GV.DB_APP);

		if (!dbHelper.isDbExists(dbName))
		{
			if (!dbHelper.createDb(dbName))
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, "Cant create app db");
				return false;
			}

			String stringAppSql = null;
			try
			{
				stringAppSql = getScriptApp();
			}
			catch (IOException ex)
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, ex.getMessage());
				return false;
			}

			if (!dbHelper.executeSql(dbName, getColonSeperatedStrings(stringAppSql)))
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, "Cant setup app db");
				return false;
			}
		}

		return startSessionFactoryApp();

	}

	private static void startMysql() throws IOException, InterruptedException
	{
		p = Runtime.getRuntime().exec(GV.MYSQL_DIR + "/bin/mysqld.exe");
		Thread.sleep(5000);
	}

	public static Session getSessionApp()
	{
		if (vSessionApp == null || !vSessionApp.isOpen())
		{
			vSessionApp = vSessionFactoryApp.openSession();
		}
		return vSessionApp;
	}

	public static Session getSessionCompanyYear()
	{
		if (vSessionCompanyYear == null)
		{
			vSessionCompanyYear = vSessionFactoryCompanyYear.openSession();
		}
		return vSessionCompanyYear;
	}

	public static void clearCompanyYearSession()
	{
		vSessionCompanyYear.clear();
	}

	public static boolean closeDBServer()
	{
		try
		{
			if (vSessionFactoryApp != null && vSessionFactoryApp.isOpen())
			{
				vSessionFactoryApp.close();
			}
			if (vSessionFactoryCompanyYear != null && vSessionFactoryCompanyYear.isOpen())
			{
				vSessionFactoryCompanyYear.close();
			}
			if (dbHelper != null)
			{
				dbHelper.close();
			}
			if (p != null)
			{
				p.destroy();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return true;
	}

	private static boolean _mBlankToSql()
	{
		String dbName = addDBPrefix(GV.DB_BLANK);

		try
		{
			String[] vExecuteCmd = new String[]
			{
				"cmd.exe",
				"/c",
				GV.MYSQL_DIR + "/bin/mysqldump.exe -u " + GV.DB_USERNAME + " -p" + GV.DB_PASSWORD + " " + dbName + " > " + GV.MYSQL_DIR + "/bin/backup.sql"
			};

			Process runtimeProcess = Runtime.getRuntime().exec(vExecuteCmd);

			InputStream is;
			if (runtimeProcess.waitFor() == 0)
			{
				//normally terminated, a way to read the output
				is = runtimeProcess.getInputStream();
				System.out.println("blank to sql success");
			}
			else
			{
				// abnormally terminated, there was some problem
				//a way to read the error during the execution of the command
				is = runtimeProcess.getErrorStream();
				System.out.println("blank to sql error");

			}
			byte[] buffer = new byte[is.available()];
			is.read(buffer);

			String str = new String(buffer);
			System.out.println(str);

		}
		catch (Exception ex)
		{
			System.out.println("Error at Backuprestore" + ex.getMessage());
			return false;
		}

		return true;
	}

	private static void mSqlToDB(String vDBName)
	{
		try
		{
			String[] executeCmd = new String[]
			{
				"cmd.exe",
				"/c",
				GV.MYSQL_DIR + "/bin/mysql.exe", vDBName, "-u" + GV.DB_USERNAME, "-p" + GV.DB_PASSWORD, "-e", " source " + GV.OTHER_DIR + "/" + GV.DB_BLANK
			};

			/*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
			Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			int processComplete = runtimeProcess.waitFor();

			//Wait for the command to complete, and check if the exit value was 0 (success)
			InputStream is;
			if (processComplete == 0)
			{
				//normally terminated, a way to read the output
				is = runtimeProcess.getInputStream();
				System.out.println("sql to db success");
			}
			else
			{
				// abnormally terminated, there was some problem
				//a way to read the error during the execution of the command
				is = runtimeProcess.getErrorStream();
				System.out.println("sql to db error");

			}
			byte[] buffer = new byte[is.available()];
			is.read(buffer);

			String str = new String(buffer);
			System.out.println(str);

		}
		catch (Exception ex)
		{
			System.out.println("Error at Restoredbfromsql" + ex.getMessage());
		}
	}

	public static void createDB(String dbId)
	{
		String dbName = addDBPrefix(dbId);

		dbHelper.createDb(dbName);
		try
		{
			dbHelper.executeSql(dbName, getColonSeperatedStrings(getScriptBlank()));
		}
		catch (IOException ex)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, Database.class.getName(), null, ex.getMessage());
		}
	}

	public static void deleteDB(String dbId)
	{
		String dbName = addDBPrefix(dbId);

		dbHelper.deleteDb(dbName);

	}

	private static String addDBPrefix(String dbName)
	{
		return GV.DB_PREFIX + dbName;
	}

	private static boolean isMysqlRunning()
	{
		return dbHelper.connect();
	}

	private static String[] getColonSeperatedStrings(String sqls)
	{
		return sqls.split(";");
	}

	private static String getScriptApp() throws IOException
	{
		String res = "";
		res = Util.readFile(GV.OTHER_DIR + "/" + GV.DB_APP);
		return res;
	}

	private static String getScriptBlank() throws IOException
	{
		String res = "";
		res = Util.readFile(GV.OTHER_DIR + "/" + GV.DB_BLANK);
		return res;
	}

}
