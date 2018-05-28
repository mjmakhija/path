package com.emsays.path.dto.app;

import java.util.*;
import javax.persistence.*;

@Entity(name = "company")  
public class CompanyDTO
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<YearDTO> years = new ArrayList<>();

	public CompanyDTO()
	{
	}

	public CompanyDTO(String name)
	{
		this.name = name;
	}

	public Integer getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public List<YearDTO> getYears()
	{
		return years;
	}

	public void addYear(YearDTO year)
	{
		years.add(year);
		year.setCompany(this);
	}

	public void removeYear(YearDTO year)
	{
		years.remove(year);
		year.setCompany(null);
	}

}
