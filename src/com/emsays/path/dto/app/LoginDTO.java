package com.emsays.path.dto.app;

import javax.persistence.*;

@Entity(name = "login")
public class LoginDTO  
{ 
 
	@Id
	private int id;   
	private String username;
	private String password;

	public int getId()
	{
		return id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
