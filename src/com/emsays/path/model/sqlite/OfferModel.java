package com.emsays.path.model.sqlite;

import com.emsays.path.dto.OfferDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "offer")
public class OfferModel
{

	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;
	@Column(name = "created_at")
	private Date createdAt;

	public OfferModel()
	{
	}

	public OfferModel(OfferDTO offer)
	{
		this.id = offer.getId();
		this.name = offer.getName();
		this.note = offer.getNote();
		this.createdAt = offer.getCreatedAt();
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

		OfferModel that = (OfferModel) o;

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
