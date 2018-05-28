package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "sms_template")
public class SMSTemplateDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	private String message;

	@Column(name = "created_at")
	@Generated(value = GenerationTime.INSERT)
	private Date createdAt;

	@Column(name = "updated_at")
	@Generated(value = GenerationTime.INSERT)
	private Date updatedAt;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

}
