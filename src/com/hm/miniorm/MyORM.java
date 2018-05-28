package com.hm.miniorm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyORM
{

	private Connection conn;

	public MyORM(Connection conn)
	{
		this.conn = conn;
	}

	private int update(Object object)
	{
		/*
		Table table = object.getClass().getAnnotation(Table.class);
		
		Field[] fields = object.getClass().getDeclaredFields();
		
		String[] setStrings = new String[fields.length - 1];
		for (int i = 0; i < fields.length; i++)
		{
			Field field = fields[i];

			field.setAccessible(true);
			try
			{
				Object value = field.get(object);
				Column column = field.getAnnotation(Column.class);

				if (column.name().equals("id"))
				{
					continue;
				}

				if (value == null)
				{
					setStrings[i] = column.name() + " = NULL";
				}
				else
				{
					Class<?> clazz = field.getType();

					if (clazz.equals(Integer.TYPE))
					{
						setStrings[i] = column.name() + " = " + String.valueOf(value);
					}
					else if (clazz.equals(Integer.class))
					{
						setStrings[i] = column.name() + " = " + String.valueOf(value);
					}
					else if (clazz.equals(String.class))
					{
						setStrings[i] = column.name() + " = '" + String.valueOf(value) + "'";
					}
					else if (clazz.equals(Boolean.TYPE))
					{
						int intValue = (boolean) value ? 1 : 0;
						setStrings[i] = column.name() + " = " + String.valueOf(intValue);
					}
					else if (clazz.equals(Date.class))
					{
						setStrings[i] = column.name() + " = " + String.valueOf(((Date) value).getTime());
					}
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}

		for (int i = 0; i < setStrings.length; i++)
		{
			if (i == 0)
			{
				sql += " " + setStrings[i];
			}
			else
			{
				sql += ", " + setStrings[i];
			}
		}

		try
		{

			Field field = object.getClass().getField("id");
			field.setAccessible(true);

			sql += " WHERE id = " + String.valueOf(field.get(object));

		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}

		return count;
		 */
		return 0;
	}

	private long insert(Object object)
	{
		/*
		ContentValues contentValues = new ContentValues();
		putContentValues(object, contentValues, true);
		Table table = object.getClass().getAnnotation(Table.class);
		return db.insert(table.name(), null, contentValues);
		 */
		return 0;
	}

	private void putContentValues(Object object, boolean modeAdd)
	{
		/*
		Field[] fields = object.getClass().getDeclaredFields();

		Table table = object.getClass().getAnnotation(Table.class);
		for (Field field : fields)
		{
			field.setAccessible(true);
			try
			{
				Object value = field.get(object);
				Column column = field.getAnnotation(Column.class);

				if (column.name().equals("id"))
				{

					if (modeAdd)
					{
						if ((int) value == 0)
						{
							continue;
						}
					}
					else
					{
						continue;
					}
				}

				if (value == null)
				{
					contentValues.putNull(column.name());
				}
				else
				{
					Class<?> clazz = field.getType();

					if (clazz.equals(Integer.TYPE))
					{
						contentValues.put(column.name(), (int) value);
					}
					else if (clazz.equals(Integer.class))
					{
						contentValues.put(column.name(), (Integer) value);
					}
					else if (clazz.equals(String.class))
					{
						contentValues.put(column.name(), (String) value);
					}
					else if (clazz.equals(Boolean.TYPE))
					{
						contentValues.put(column.name(), (boolean) value);
					}
					else if (clazz.equals(Date.class))
					{
						contentValues.put(column.name(), ((Date) value).getTime());
					}
				}
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		 */
	}

	public <E> List<E> get(Class<E> classOfE, String sql)
	{

		List<E> result = new ArrayList<>();
		Field[] fields = classOfE.getDeclaredFields();
		try
		{

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{

				// New object to add in array list
				E obj = classOfE.newInstance();

				for (Field field : fields)
				{

					field.setAccessible(true);

					// Get annotation of field
					Column column = field.getAnnotation(Column.class);

					Class<?> fieldDataType = field.getType();

					Object value = null;

					if (fieldDataType.equals(Integer.TYPE))
					{
						value = rs.getInt(column.name());
					}
					else if (fieldDataType.equals(Integer.class))
					{
						value = rs.getInt(column.name());
					}
					else if (fieldDataType.equals(String.class))
					{
						value = rs.getString(column.name());
					}
					else if (fieldDataType.equals(Boolean.TYPE))
					{
						value = rs.getInt(column.name()) > 0;
					}
					else if (fieldDataType.equals(Boolean.class))
					{
						value = rs.getInt(column.name()) > 0;
					}
					else if (fieldDataType.equals(Date.class))
					{
						value = new Date(rs.getLong(column.name()));
					}

					field.set(obj, value);

				}

				result.add(obj);

			}

			rs.close();
			stmt.close();

		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (SQLException ex)
		{
			Logger.getLogger(MyORM.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally
		{

		}
		return result;

	}

}
