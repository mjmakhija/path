package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity(name = "customer_change_type")
public class CustomerChangeTypeDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	public CustomerChangeTypeDTO()
	{
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 29 * hash + this.id;
		hash = 29 * hash + Objects.hashCode(this.name);
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
		final CustomerChangeTypeDTO other = (CustomerChangeTypeDTO) obj;
		if (this.id != other.id)
		{
			return false;
		}
		if (!Objects.equals(this.name, other.name))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "CustomerChangeTypeDTO{" + "id=" + id + ", name=" + name + '}';
	}

}
