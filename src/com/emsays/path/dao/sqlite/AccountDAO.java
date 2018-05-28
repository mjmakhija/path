package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.AccountModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class AccountDAO
{

	private static final String tableName = "account";
	private String sqlInsert = "INSERT INTO `%s`(`id`, `name`, `note`) VALUES (?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public AccountDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(AccountModel account, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, account.getId());
			ps.setString(columnIndex++, account.getName());
			ps.setString(columnIndex++, account.getNote());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next())
			{
				generatedKey = rs.getInt(1);
			}
			if (generatedKey > 0)
			{
				account.setId(generatedKey);
				return true;
			}
			return false;
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}

	}

	public void create(List<AccountModel> accounts)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (AccountModel account : accounts)
			{
				insert(account, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
