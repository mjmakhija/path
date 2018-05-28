package com.emsays.path.dto.app;

import java.util.Date;
import javax.persistence.*;

@Entity(name = "year")  
public class YearDTO
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	private CompanyDTO company;
	private Date date_from;
	private Date date_to;

	public YearDTO()
	{
	}

	public YearDTO(Date date_from, Date date_to)
	{
		this.date_from = date_from;
		this.date_to = date_to;
	}

	public int getId()
	{
		return id;
	}

	public CompanyDTO getCompany()
	{
		return company;
	}

	public void setCompany(CompanyDTO company)
	{
		this.company = company;
	}

	public Date getDate_from()
	{
		return date_from;
	}

	public void setDate_from(Date date_from)
	{
		this.date_from = date_from;
	}

	public Date getDate_to()
	{
		return date_to;
	}

	public void setDate_to(Date date_to)
	{
		this.date_to = date_to;
	}

}
