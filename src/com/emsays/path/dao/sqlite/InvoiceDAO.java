package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.InvoiceModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

public class InvoiceDAO
{

	public static final String tableName = "invoice";
	private String columns = "`id`, "
			+ "`customer_id`, "
			+ "`year`, "
			+ "`month`, "
			+ "`sr_no`, "
			+ "`amount`, "
			+ "`note`, "
			+ "`created_at`, "
			+ "`sms_sent` ";
	private String sqlInsert = "INSERT INTO `%s`(" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public InvoiceDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(InvoiceModel invoice, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, invoice.getId());
			ps.setInt(columnIndex++, invoice.getCustomerId());
			ps.setInt(columnIndex++, invoice.getYear());
			ps.setInt(columnIndex++, invoice.getMonth());
			ps.setInt(columnIndex++, invoice.getSrNo());
			ps.setInt(columnIndex++, invoice.getAmount());
			if (invoice.getNote() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, invoice.getNote());
			}
			ps.setLong(columnIndex++, invoice.getCreatedAt().getTime());
			ps.setInt(columnIndex++, invoice.isSmsSent() ? 1 : 0);

			ps.execute();
			
				ResultSet rs = ps.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next())
				{
					generatedKey = rs.getInt(1);
				}
				if (generatedKey > 0)
				{
					invoice.setId(generatedKey);
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

	public void create(List<InvoiceModel> invoices)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (InvoiceModel invoice
					: invoices)
			{
				insert(invoice, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
