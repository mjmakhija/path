package com.emsays.path.dto;

import com.emsays.path.ObjectForCombobox;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "offer")
public class OfferDTO implements Serializable, ObjectForCombobox
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@Column(nullable = true)
	private String note;
	
	@Column(name = "created_at")
	@Generated(value = GenerationTime.INSERT)
	private Date createdAt;

	public OfferDTO()
	{
	}

	public OfferDTO(String name, String note)
	{
		this.name = name;
		this.note = note;
	}

	public int getId()
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
	
	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 83 * hash + this.id;
		hash = 83 * hash + Objects.hashCode(this.name);
		hash = 83 * hash + Objects.hashCode(this.note);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof OfferDTO))
		{
			return false;
		}
		final OfferDTO other = (OfferDTO) obj;
		if (this.id != other.getId())
		{
			return false;
		}
		if (!Objects.equals(this.name, other.getName()))
		{
			return false;
		}
		if (!Objects.equals(this.note, other.getNote()))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "OfferDTO{" + "id=" + id + ", name=" + name + ", note=" + note + '}';
	}

	@Override
	public String getDisplayName()
	{
		return getName();
	}
}
