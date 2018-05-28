package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.ReceiptModel;
import com.hm.miniorm.MyORM;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

public class ReceiptDAO
{

	private static final String tableName = "receipt";
	private String columns = "`id`, "
			+ "`customer_id`, "
			+ "`account_id`, "
			+ "`amount`, "
			+ "`date`, "
			+ "`note`, "
			+ "`syncd` ";
	private String sqlInsert = "INSERT INTO `%s`(" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";
	private String sqlMarkAllSyncd = "UPDATE `%s` SET syncd = 1;";
	private String sqlGetNotSyncd = "SELECT * FROM `%s` WHERE `syncd` = 0;";

	Connection conn;
	MyORM orm;

	public ReceiptDAO(Connection c)
	{
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
		sqlMarkAllSyncd = String.format(sqlMarkAllSyncd, tableName);
		sqlGetNotSyncd = String.format(sqlGetNotSyncd, tableName);
		this.conn = c;
		orm = new MyORM(c);
	}

	public List<ReceiptModel> getNotSyncd()
	{

		String sql = String.format(sqlGetNotSyncd, tableName);
		return orm.get(ReceiptModel.class, sql);

	}

	public boolean insert(ReceiptModel receipt, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = conn.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, receipt.getId());
			ps.setInt(columnIndex++, receipt.getCustomerId());
			ps.setInt(columnIndex++, receipt.getAccountId());
			ps.setInt(columnIndex++, receipt.getAmount());
			ps.setLong(columnIndex++, receipt.getDate().getTime());
			if (receipt.getNote() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, receipt.getNote());
			}
			ps.setInt(columnIndex++, receipt.isSyncd() ? 1 : 0);

			ps.execute();
			
				ResultSet rs = ps.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next())
				{
					generatedKey = rs.getInt(1);
				}
				if (generatedKey > 0)
				{
					receipt.setId(generatedKey);
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

	public void markAllSyncd()
	{

		try
		{
			Statement stmt = conn.createStatement();
			stmt.execute(sqlMarkAllSyncd);
			stmt.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{

		}
	}

	public void create(List<ReceiptModel> receipts)
	{
		try
		{
			conn.createStatement().execute(sqlDelete);

			PreparedStatement ps = conn.prepareStatement(sqlInsert);

			for (ReceiptModel receipt
					: receipts)
			{
				insert(receipt, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
