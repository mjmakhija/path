package com.emsays.path;

import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.model.sqlite.ReceiptModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelperSqlite
{

	Connection c = null;
	String dbPath;

	public DBHelperSqlite(String dbPath)
	{
		this.dbPath = dbPath;
	}

	public void createConnection()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			//c.setAutoCommit(false);
			System.out.println("Opened database successfully");

		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void closeConnection()
	{
		try
		{
			c.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}

	public Connection getConnection()
	{
		return c;
	}

	public List<ReceiptModel> getReceipts()
	{
		/*
		List<ReceiptDTOPhone> receipts = new ArrayList<>();

		try
		{
			createConnection();
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, customer_id, amount, substr(date,1,10) * 1000 as date, `comment` FROM receipt;");

			while (rs.next())
			{
				ReceiptDTOPhone receipt = new ReceiptDTOPhone();
				receipt.set(rs.getInt("id"));
				receipt.setCustomerId(rs.getInt("customer_id"));
				receipt.setAmount(rs.getInt("amount"));
				receipt.setDate(rs.getTimestamp("date"));
				receipt.setNote(rs.getString("comment"));

				receipts.add(receipt);
			}
			rs.close();
			stmt.close();

			closeConnection();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(DBHelperSqlite.class.getName()).log(Level.SEVERE, null, ex);
		}

		 */
		return null;
	}

	public boolean generateDB(List<com.emsays.path.dto.InvoiceDTO> invoices, List<com.emsays.path.dto.ReceiptDTO> receipts)
	{
		String deleteOrderQuery = "DELETE FROM `order` WHERE 1";
		String deleteReceiptQuery = "DELETE FROM `receipt` WHERE 1";

		String inserOrderQuery = "INSERT INTO `order` ( id, customer_id, sr_no, customer_name, house_no1, house_no2, house_no3, locality1, amount, done, mobile ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String inserReceiptQuery = "INSERT INTO `receipt` ( customer_id, amount, date, comment ) VALUES ( ?, ?, ?, ?)";

		try
		{
			createConnection();

			Statement stmt = c.createStatement();
			stmt.executeUpdate(deleteOrderQuery);
			stmt.executeUpdate(deleteReceiptQuery);

			PreparedStatement preparedStmt = c.prepareStatement(inserOrderQuery);

			int i = 1;
			int columnIndex;
			for (InvoiceDTO invoice : invoices)
			{
				columnIndex = 1;

				preparedStmt.setInt(columnIndex++, i++);
				preparedStmt.setInt(columnIndex++, invoice.getCustomer().getId());
				preparedStmt.setInt(columnIndex++, invoice.getSrNo());
				preparedStmt.setString(columnIndex++, invoice.getCustomer().getCustomerName());
				preparedStmt.setString(columnIndex++, invoice.getCustomer().getHouseNo1());
				preparedStmt.setString(columnIndex++, invoice.getCustomer().getHouseNo2());
				preparedStmt.setString(columnIndex++, invoice.getCustomer().getHouseNo3());
				preparedStmt.setString(columnIndex++, invoice.getCustomer().getArea().getName());
				preparedStmt.setInt(columnIndex++, invoice.getAmount());
				preparedStmt.setBoolean(columnIndex++, false);
				preparedStmt.setString(columnIndex++, invoice.getCustomer().getMobile());

				preparedStmt.execute();

			}

			preparedStmt = c.prepareStatement(inserReceiptQuery);

			for (com.emsays.path.dto.ReceiptDTO receipt : receipts)
			{
				columnIndex = 1;

				preparedStmt.setInt(columnIndex++, receipt.getCustomer().getId());
				preparedStmt.setInt(columnIndex++, receipt.getAmount());
				preparedStmt.setTimestamp(columnIndex++, (Timestamp) receipt.getDate());
				preparedStmt.setString(columnIndex++, receipt.getNote());

				preparedStmt.execute();

			}

			preparedStmt.close();

			closeConnection();

			return true;

		}
		catch (SQLException ex)
		{
			Logger.getLogger(DBHelperSqlite.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}

	}

}
