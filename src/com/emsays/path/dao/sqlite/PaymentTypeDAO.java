package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.PaymentTypeModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class PaymentTypeDAO
{

	public static final String tableName = "payment_type";
	private String sqlInsert = "INSERT INTO `%s`(`id`, `name`, `note`, `created_at`) VALUES (?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public PaymentTypeDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(PaymentTypeModel paymentType, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, paymentType.getId());
			ps.setString(columnIndex++, paymentType.getName());
			ps.setString(columnIndex++, paymentType.getNote());
			ps.setLong(columnIndex++, paymentType.getCreatedAt().getTime());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next())
			{
				generatedKey = rs.getInt(1);
			}
			if (generatedKey > 0)
			{
				paymentType.setId(generatedKey);
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

	public void create(List<PaymentTypeModel> paymentTypes)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (PaymentTypeModel paymentType : paymentTypes)
			{
				insert(paymentType, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
