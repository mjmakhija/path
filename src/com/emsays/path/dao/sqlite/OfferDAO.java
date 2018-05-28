package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.OfferModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class OfferDAO
{

	public static final String tableName = "offer";
	private String sqlInsert = "INSERT INTO `%s`(`id`, `name`, `note`, `created_at`) VALUES (?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public OfferDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(OfferModel offer, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, offer.getId());
			ps.setString(columnIndex++, offer.getName());
			ps.setString(columnIndex++, offer.getNote());
			ps.setLong(columnIndex++, offer.getCreatedAt().getTime());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next())
			{
				generatedKey = rs.getInt(1);
			}
			if (generatedKey > 0)
			{
				offer.setId(generatedKey);
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

	public void create(List<OfferModel> offers)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (OfferModel offer : offers)
			{
				insert(offer, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
