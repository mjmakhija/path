package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "customer_change_log")
public class CustomerChangeLogDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	private CustomerDTO customer;

	@ManyToOne
	@JoinColumn(name = "change_type_id")
	private CustomerChangeTypeDTO customerChangeType;

	@Column(name = "old_value")
	private String oldValue;

	@Column(name = "created_at")
	@Generated(value = GenerationTime.INSERT)
	private Date createdAt;

	public CustomerChangeLogDTO()
	{
	}

	public CustomerChangeLogDTO(CustomerChangeTypeDTO customerChangeType, String oldValue)
	{
		this.customerChangeType = customerChangeType;
		this.oldValue = oldValue;
	}

	public int getId()
	{
		return id;
	}

	public CustomerDTO getCustomer()
	{
		return customer;
	}

	public void setCustomer(CustomerDTO customer)
	{
		this.customer = customer;
	}

	public CustomerChangeTypeDTO getCustomerChangeType()
	{
		return customerChangeType;
	}

	public void setCustomerChangeType(CustomerChangeTypeDTO customerChangeType)
	{
		this.customerChangeType = customerChangeType;
	}

	public String getOldValue()
	{
		return oldValue;
	}

	public void setOldValue(String oldValue)
	{
		this.oldValue = oldValue;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 83 * hash + this.id;
		hash = 83 * hash + Objects.hashCode(this.customer);
		hash = 83 * hash + Objects.hashCode(this.customerChangeType);
		hash = 83 * hash + Objects.hashCode(this.oldValue);
		hash = 83 * hash + Objects.hashCode(this.createdAt);
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
		final CustomerChangeLogDTO other = (CustomerChangeLogDTO) obj;
		if (this.id != other.id)
		{
			return false;
		}
		if (!Objects.equals(this.oldValue, other.oldValue))
		{
			return false;
		}
		if (!Objects.equals(this.customer, other.customer))
		{
			return false;
		}
		if (!Objects.equals(this.customerChangeType, other.customerChangeType))
		{
			return false;
		}
		if (!Objects.equals(this.createdAt, other.createdAt))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "CustomerChangeLogDTO{" + "id=" + id + ", customer=" + customer + ", customerChangeType=" + customerChangeType + ", oldValue=" + oldValue + ", createdAt=" + createdAt + '}';
	}

}
