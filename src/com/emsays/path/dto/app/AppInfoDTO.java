package com.emsays.path.dto.app;

import javax.persistence.*;

@Entity(name = "app")
public class AppInfoDTO  
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
