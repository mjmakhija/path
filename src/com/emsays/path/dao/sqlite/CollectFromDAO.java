package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.CollectFromModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class CollectFromDAO
{

	public static final String tableName = "collect_from";
	private String sqlInsert = "INSERT INTO `%s`(`id`, `name`, `note`, `created_at`) VALUES (?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public CollectFromDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(CollectFromModel collectFrom, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, collectFrom.getId());
			ps.setString(columnIndex++, collectFrom.getName());
			ps.setString(columnIndex++, collectFrom.getNote());
			ps.setLong(columnIndex++, collectFrom.getCreatedAt().getTime());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next())
			{
				generatedKey = rs.getInt(1);
			}
			if (generatedKey > 0)
			{
				collectFrom.setId(generatedKey);
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

	public void create(List<CollectFromModel> collectFroms)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (CollectFromModel collectFrom : collectFroms)
			{
				insert(collectFrom, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
