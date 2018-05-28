package com.emsays.path;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class DbHelper
{

	private Connection conn = null;
	private Statement stmt = null;

	private String url;
	private String username;
	private String password;

	public DbHelper(String url, String username, String password)
	{
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public boolean connect()
	{
		boolean res = false;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, username, password);
			res = true;
		}
		catch (Exception e)
		{
		}
		return res;
	}

	public boolean createDb(String dbName)
	{
		boolean res = false;
		try
		{
			stmt = conn.createStatement();
			String sql = "CREATE DATABASE " + dbName;
			stmt.executeUpdate(sql);
			res = true;
		}
		catch (SQLException ex)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, ex.getMessage());
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			catch (SQLException se2)
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, se2.getMessage());
			}
		}
		return res;
	}

	public boolean deleteDb(String dbName)
	{
		boolean res = false;
		try
		{
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP DATABASE `" + dbName + "`");
			res = true;
		}
		catch (Exception ex)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, ex.getMessage());
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			catch (Exception ex)
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, ex.getMessage());
			}
		}
		return res;
	}

	public boolean isDbExists(String dbName)
	{
		ResultSet rs = null;
		boolean res = false;

		try
		{

			rs = conn.getMetaData().getCatalogs();

			while (rs.next())
			{
				String catalogs = rs.getString(1);

				if (dbName.equals(catalogs))
				{
					res = true;
					break;
				}
			}

		}
		catch (Exception ex)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, ex.getMessage());
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException ex)
				{
					LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, ex.getMessage());
				}
			}
		}

		return res;
	}

	public boolean executeSql(String dbName, String[] sqls)
	{
		boolean res = false;
		try
		{

			conn.setCatalog(dbName);
			stmt = conn.createStatement();

			for (String sql1 : sqls)
			{
				stmt.executeUpdate(sql1);
			}

			res = true;
		}
		catch (Exception e)
		{
			LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, e.getMessage());
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			catch (SQLException se2)
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, se2.getMessage());
			}
		}

		return res;
	}

	public boolean close()
	{
		boolean res = false;
		if (conn != null)
		{
			try
			{
				conn.close();
				res = true;
			}
			catch (Exception ignore)
			{
				LoggerWrapper.getInstance().logp(Level.SEVERE, DbHelper.class.getName(), null, ignore.getMessage());
			}
		}
		return res;
	}

}
