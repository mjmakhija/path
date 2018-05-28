package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.PackageModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class PackageDAO
{

	public static final String tableName = "package";
	private String sqlInsert = "INSERT INTO `%s`(`id`, `name`, `note`, `created_at`) VALUES (?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public PackageDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(PackageModel package_, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, package_.getId());
			ps.setString(columnIndex++, package_.getName());
			ps.setString(columnIndex++, package_.getNote());
			ps.setLong(columnIndex++, package_.getCreatedAt().getTime());

			ps.execute();
			
				ResultSet rs = ps.getGeneratedKeys();
				int generatedKey = 0;
				if (rs.next())
				{
					generatedKey = rs.getInt(1);
				}
				if (generatedKey > 0)
				{
					package_.setId(generatedKey);
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

	public void create(List<PackageModel> package_s)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (PackageModel package_ : package_s)
			{
				insert(package_, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}
}
