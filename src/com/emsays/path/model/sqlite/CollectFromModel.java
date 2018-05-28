package com.emsays.path.model.sqlite;

import com.emsays.path.dto.CollectFromDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "collect_from")
public class CollectFromModel
{

	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;
	@Column(name = "created_at")
	private Date createdAt;

	public CollectFromModel()
	{
	}

	public CollectFromModel(CollectFromDTO collectFrom)
	{
		this.id = collectFrom.getId();
		this.name = collectFrom.getName();
		this.note = collectFrom.getNote();
		this.createdAt = collectFrom.getCreatedAt();
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
