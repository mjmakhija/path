package com.emsays.path.dao.sqlite;

import com.emsays.path.model.sqlite.CustomerModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.List;

public class CustomerDAO
{

	public static final String tableName = "customer";
	private String columns = "`id`, "
			+ "`parent_id`, "
			+ "`account_no`, "
			+ "`customer_name`, "
			+ "`house_no1`, "
			+ "`house_no2`, "
			+ "`house_no3`, "
			+ "`area_id`, "
			+ "`official_address`, "
			+ "`mobile`, "
			+ "`payment_type_id`, "
			+ "`package_id`, "
			+ "`amp`, "
			+ "`home`, "
			+ "`repair`, "
			+ "`dc`, "
			+ "`amount`, "
			+ "`offer_id`, "
			+ "`collect_from_id`, "
			+ "`suspended`, "
			+ "`note`, "
			+ "`created_at` ";
	private String sqlInsert = "INSERT INTO `%s`(" + columns + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private String sqlDelete = "DELETE FROM `%s`;";

	Connection c;

	public CustomerDAO(Connection c)
	{
		this.c = c;
		sqlInsert = String.format(sqlInsert, tableName);
		sqlDelete = String.format(sqlDelete, tableName);
	}

	public boolean insert(CustomerModel customer, PreparedStatement ps)
	{

		try
		{
			if (ps == null)
			{
				ps = c.prepareStatement(sqlInsert);
			}

			int columnIndex = 1;
			ps.setInt(columnIndex++, customer.getId());
			if (customer.getParentId() == null)
			{
				ps.setNull(columnIndex++, Types.INTEGER);
			}
			else
			{
				ps.setInt(columnIndex++, customer.getParentId());
			}
			ps.setInt(columnIndex++, customer.getAccountNo());
			ps.setString(columnIndex++, customer.getCustomerName());
			if (customer.getHouseNo1() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, customer.getHouseNo1());
			}
			if (customer.getHouseNo2() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, customer.getHouseNo2());
			}
			if (customer.getHouseNo3() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, customer.getHouseNo3());
			}

			ps.setInt(columnIndex++, customer.getAreaId());

			if (customer.getOfficialAddress() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, customer.getOfficialAddress());
			}

			if (customer.getMobile() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, customer.getMobile());
			}

			if (customer.getPaymentTypeId() == null)
			{
				ps.setNull(columnIndex++, Types.INTEGER);
			}
			else
			{
				ps.setInt(columnIndex++, customer.getPaymentTypeId());
			}

			if (customer.getPackageId() == null)
			{
				ps.setNull(columnIndex++, Types.INTEGER);
			}
			else
			{
				ps.setInt(columnIndex++, customer.getPackageId());
			}

			ps.setInt(columnIndex++, customer.isAmp() ? 1 : 0);
			ps.setInt(columnIndex++, customer.isHome() ? 1 : 0);
			ps.setInt(columnIndex++, customer.isRepair() ? 1 : 0);
			ps.setInt(columnIndex++, customer.isDc() ? 1 : 0);

			if (customer.getAmount() == null)
			{
				ps.setNull(columnIndex++, Types.INTEGER);
			}
			else
			{
				ps.setInt(columnIndex++, customer.getAmount());
			}

			if (customer.getOfferId() == null)
			{
				ps.setNull(columnIndex++, Types.INTEGER);
			}
			else
			{
				ps.setInt(columnIndex++, customer.getOfferId());
			}

			if (customer.getCollectFromId() == null)
			{
				ps.setNull(columnIndex++, Types.INTEGER);
			}
			else
			{
				ps.setInt(columnIndex++, customer.getCollectFromId());
			}

			ps.setInt(columnIndex++, customer.isSuspended() ? 1 : 0);
			if (customer.getNote() == null)
			{
				ps.setNull(columnIndex++, Types.VARCHAR);
			}
			else
			{
				ps.setString(columnIndex++, customer.getNote());
			}
			ps.setLong(columnIndex++, customer.getCreatedAt().getTime());

			ps.execute();

			ResultSet rs = ps.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next())
			{
				generatedKey = rs.getInt(1);
			}
			if (generatedKey > 0)
			{
				customer.setId(generatedKey);
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

	public void create(List<CustomerModel> customers)
	{
		try
		{
			c.createStatement().execute(sqlDelete);

			PreparedStatement ps = c.prepareStatement(sqlInsert);

			for (CustomerModel customer
					: customers)
			{
				insert(customer, ps);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
