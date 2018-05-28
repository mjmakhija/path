package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.AreaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class AreaDAO
{

	public static final String tableName = "area";
	private String sqlInsert = "INSERT INTO `%s`(`id`, `name`, `note`, `created_at`) VALUES (?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public AreaDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(AreaModel area, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, area.getId());
			ps.setString(columnIndex++, area.getName());
			ps.setString(columnIndex++, area.getNote());
			ps.setLong(columnIndex++, area.getCreatedAt().getTime());

			ps.execute();
			
				ResultSet rs = ps.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next())
				{
					generatedKey = rs.getInt(1);
				}
				if (generatedKey > 0)
				{
					area.setId(generatedKey);
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

	public void create(List<AreaModel> areas)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (AreaModel area : areas)
			{
				insert(area, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
