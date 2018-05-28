package com.emsays.path.model.sqlite;

import com.emsays.path.dto.AreaDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "area")
public class AreaModel
{

	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;
	@Column(name = "created_at")
	private Date createdAt;

	public AreaModel()
	{
	}

	public AreaModel(AreaDTO area)
	{
		this.id = area.getId();
		this.name = area.getName();
		this.note = area.getNote();
		this.createdAt = area.getCreatedAt();
	}

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

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}
}
