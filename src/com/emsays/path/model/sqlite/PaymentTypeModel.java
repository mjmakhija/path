package com.emsays.path.model.sqlite;

import com.emsays.path.dto.PaymentTypeDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "payment_type")
public class PaymentTypeModel
{

	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "note")
	private String note;
	@Column(name = "created_at")
	private Date createdAt;

	public PaymentTypeModel()
	{
	}

	public PaymentTypeModel(PaymentTypeDTO paymentType)
	{
		this.id = paymentType.getId();
		this.name = paymentType.getName();
		this.note = paymentType.getNote();
		this.createdAt = paymentType.getCreatedAt();
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

		PaymentTypeModel that = (PaymentTypeModel) o;

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
