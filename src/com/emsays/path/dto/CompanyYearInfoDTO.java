package com.emsays.path.dto;

import javax.persistence.*;

@Entity(name = "info")
public class CompanyYearInfoDTO
{

	@Id
	private int id;
	@Column(name = "info_key")
	private String infoKey;
	@Column(name = "info_value")
	private String infoValue;

	public int getId()
	{
		return id;
	}

	public String getInfoKey()
	{
		return infoKey;
	}

	public String getInfoValue()
	{
		return infoValue;
	}

	public void setInfoValue(String infoValue)
	{
		this.infoValue = infoValue;
	}

}
