package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Objects;

public class CustomerDTOWrapper extends CustomerDTO implements Serializable
{

	private Long amtGTReceivable;  
	private Long amtGTReceived;
	private Long amtReceivable;

	public CustomerDTOWrapper(CustomerDTO customer, Long amtGTReceivable, Long amtGTReceived)
	{
		super(customer);

		this.amtGTReceivable = amtGTReceivable == null ? 0 : amtGTReceivable;
		this.amtGTReceived = amtGTReceived == null ? 0 : amtGTReceived;
		this.amtReceivable = this.amtGTReceivable - this.amtGTReceived;

	}

	public long getAmtGTReceivable()
	{
		return amtGTReceivable;
	}

	public void setAmtGTReceivable(Long amtGTReceivable)
	{
		this.amtGTReceivable = amtGTReceivable;
	}

	public long getAmtGTReceived()
	{
		return amtGTReceived;
	}

	public void setAmtGTReceived(Long amtGTReceived)
	{
		this.amtGTReceived = amtGTReceived;
	}

	public long getAmtReceivable()
	{
		return amtReceivable;
	}

	public void setAmtReceivable(Long amtReceivable)
	{
		this.amtReceivable = amtReceivable;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 47 * hash + Objects.hashCode(this.amtGTReceivable);
		hash = 47 * hash + Objects.hashCode(this.amtGTReceived);
		hash = 47 * hash + Objects.hashCode(this.amtReceivable);
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
		final CustomerDTOWrapper other = (CustomerDTOWrapper) obj;
		if (!Objects.equals(this.amtGTReceivable, other.amtGTReceivable))
		{
			return false;
		}
		if (!Objects.equals(this.amtGTReceived, other.amtGTReceived))
		{
			return false;
		}
		if (!Objects.equals(this.amtReceivable, other.amtReceivable))
		{
			return false;
		}
		return true;
	}
	

}
