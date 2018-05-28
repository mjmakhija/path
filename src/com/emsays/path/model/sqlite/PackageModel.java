package com.emsays.path.model.sqlite;

import com.emsays.path.dto.PackageDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "package")
public class PackageModel
{

	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;
	@Column(name = "created_at")
	private Date createdAt;

	public PackageModel()
	{
	}

	public PackageModel(PackageDTO package_)
	{
		this.id = package_.getId();
		this.name = package_.getName();
		this.note = package_.getNote();
		this.createdAt = package_.getCreatedAt();
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

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		PackageModel that = (PackageModel) o;

		if (id != that.id)
		{
			return false;
		}
		if (!name.equals(that.name))
		{
			return false;
		}
		if (note != null ? !note.equals(that.note) : that.note != null)
		{
			return false;
		}
		return createdAt.equals(that.createdAt);
	}

	@Override
	public int hashCode()
	{
		int result = id;
		result = 31 * result + name.hashCode();
		result = 31 * result + (note != null ? note.hashCode() : 0);
		result = 31 * result + createdAt.hashCode();
		return result;
	}
}
