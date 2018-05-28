package com.emsays.path.dto;

import java.io.Serializable;
import javax.persistence.Column;

public class MonthYearDTO implements Serializable
{

	@Column(name = "month")
	private int month;
	@Column(name = "year")
	private int year;

	public MonthYearDTO()
	{
	}

	public MonthYearDTO(int month, int year)
	{
		this.month = month;
		this.year = year;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 47 * hash + this.month;
		hash = 47 * hash + this.year;
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final MonthYearDTO other = (MonthYearDTO) obj;
		if (this.month != other.month)
		{
			return false;
		}
		if (this.year != other.year)
		{
			return false;
		}
		return true;
	}

}
