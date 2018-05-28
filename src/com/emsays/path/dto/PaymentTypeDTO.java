package com.emsays.path.dto;

import com.emsays.path.ObjectForCombobox;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "payment_type")
public class PaymentTypeDTO implements Serializable, ObjectForCombobox
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
	
	public PaymentTypeDTO()
	{
	}

	public PaymentTypeDTO(String name, String note)
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
		hash = 23 * hash + this.id;
		hash = 23 * hash + Objects.hashCode(this.name);
		hash = 23 * hash + Objects.hashCode(this.note);
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
		if (!(obj instanceof PaymentTypeDTO))
		{
			return false;
		}
		final PaymentTypeDTO other = (PaymentTypeDTO) obj;
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
		return "PaymentTypeDTO{" + "id=" + id + ", name=" + name + ", note=" + note + '}';
	}

	@Override
	public String getDisplayName()
	{
		return getName();
	}

}
