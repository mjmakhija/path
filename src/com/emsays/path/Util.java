package com.emsays.path;

import com.emsays.path.dto.InvoiceDTO;
import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class Util
{

	final static String APP_DATE_FORMAT = "dd-MM-yyyy";
	final static String DB_DATE_FORMAT = "yyyy-MM-dd";

	public static boolean isDateValid(String date)
	{
		try
		{
			DateFormat df = new SimpleDateFormat(APP_DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public static Date mConvertStringToDate(String vDate)
	{
		try
		{
			Date objDate = new SimpleDateFormat(APP_DATE_FORMAT).parse(vDate);
			return objDate;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static Date mConvertStringToDate2(String vDate)
	{
		try
		{
			Date objDate = new SimpleDateFormat("d-M-y").parse(vDate);
			return objDate;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static Date mTemp(String date)
	{
		try
		{
			Date objDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").parse(date);
			Date abc = new Date();
			return objDate;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	public static String mGetLocalDateFormat(Date vDate)
	{
		return new SimpleDateFormat("dd-MM-yyyy").format(vDate);
	}

	public static String mGetLocalDateTime(Date vDate)
	{
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(vDate);
	}

	public static String getMonthName(int month)
	{
		String[] monthNames = new String[]
		{
			"Jan",
			"Feb",
			"Mar",
			"Apr",
			"May",
			"Jun",
			"Jul",
			"Aug",
			"Sep",
			"Oct",
			"Nov",
			"Dec",
		};

		if (month < 0 || month > 12)
		{
			return "";
		}

		return monthNames[month - 1];

	}

	public static boolean mIsNetConnected(String vUrl)
	{
		try
		{
			try
			{
				URL url = new URL(vUrl);
				System.out.println(url.getHost());
				HttpURLConnection con = (HttpURLConnection) url
					.openConnection();
				con.connect();
				if (con.getResponseCode() == 200)
				{
					System.out.println("Connection established!!");
					return true;
				}

				return false;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.out.println("No Connection");
				return false;
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}

	}

	public static boolean mIsNetConnected()
	{
		return Util.mIsNetConnected("https://www.google.com");
	}

	public static boolean isNumeric(String s)
	{
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

	public static void resizeColumnWidth(JTable table)
	{
		int cumulativeActual = 0;
		int padding = 15;
		for (int columnIndex = 0; columnIndex < table.getColumnCount(); columnIndex++)
		{
			int columnExpectedWidth = 0; // Min width
			TableColumn column = table.getColumnModel().getColumn(columnIndex);

			//Column Header Size check --------------
			Object value = column.getHeaderValue();
			TableCellRenderer renderer = column.getHeaderRenderer();

			if (renderer == null)
			{
				renderer = table.getTableHeader().getDefaultRenderer();
			}

			Component c = renderer.getTableCellRendererComponent(table, value, false, false, -1, columnIndex);
			columnExpectedWidth = c.getPreferredSize().width + padding;
			//Column Header Size check --------------

			for (int row = 0; row < table.getRowCount(); row++)
			{
				renderer = table.getCellRenderer(row, columnIndex);
				Component comp = table.prepareRenderer(renderer, row, columnIndex);
				columnExpectedWidth = Math.max(comp.getPreferredSize().width + padding, columnExpectedWidth);
			}
			if (columnIndex < table.getColumnCount())
			{
				column.setPreferredWidth(columnExpectedWidth);
				cumulativeActual += column.getWidth();
			}

		}
	}

	public static void setGreenColor(JTable table, int rowIndex)
	{

		for (int i = 0; i < table.getRowCount(); i++)
		{
			for (int j = 0; j < table.getColumnCount(); j++)
			{
				Component c = table.getCellRenderer(i, j).getTableCellRendererComponent(table, null, false, false, i, j);
				if (i == rowIndex - 1)
				{
					c.setBackground(Color.BLUE);
				}
				else
				{
					c.setBackground(null);
				}
			}
		}
	}

	public static InvoiceDTO unproxy(InvoiceDTO proxied)
	{
		InvoiceDTO entity = proxied;
		if (entity instanceof HibernateProxy)
		{
			Hibernate.initialize(entity);
			entity = (InvoiceDTO) ((HibernateProxy) entity)
				.getHibernateLazyInitializer()
				.getImplementation();
		}
		return entity;
	}

	public static String readFile(String filePath) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

	public static String formatString(String pattern, Map arguments)
	{
		Map<String, Object> entries = arguments;
		for (Map.Entry<String, Object> entry : entries.entrySet())
		{
			pattern = pattern.replace("{" + entry.getKey() + "}", String.valueOf(entry.getValue()));
		}

		return pattern;
	}

}
