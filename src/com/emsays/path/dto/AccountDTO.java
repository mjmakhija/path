package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity(name = "account")
public class AccountDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	@Column(nullable = true)
	private String note;

	public AccountDTO()
	{
	}

	public AccountDTO(String name, String note)
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

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 79 * hash + this.id;
		hash = 79 * hash + Objects.hashCode(this.name);
		hash = 79 * hash + Objects.hashCode(this.note);
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
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final AccountDTO other = (AccountDTO) obj;
		if (this.id != other.id)
		{
			return false;
		}
		if (!Objects.equals(this.name, other.name))
		{
			return false;
		}
		if (!Objects.equals(this.note, other.note))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "AccountDTO{" + "id=" + id + ", name=" + name + ", note=" + note + '}';
	}

}
