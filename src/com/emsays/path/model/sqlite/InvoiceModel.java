package com.emsays.path.model.sqlite;

import com.emsays.path.dto.InvoiceDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "invoice")
public class InvoiceModel
{

	@Column(name = "id")
	private int id;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "year")
	private int year;
	@Column(name = "month")
	private int month;

	@Column(name = "sr_no")
	private int srNo;

	@Column(name = "amount")
	private int amount;

	@Column(name = "note")
	private String note;

	@Column(name = "created_at")
	private Date createdAt;

	@Column(name = "sms_sent")
	private boolean smsSent;

	public InvoiceModel(InvoiceDTO invoice)
	{
		this.id = invoice.getId();
		this.customerId = invoice.getCustomer().getId();
		this.year = invoice.getYear();
		this.month = invoice.getMonth();
		this.srNo = invoice.getSrNo();
		this.amount = invoice.getAmount();
		this.note = invoice.getNote();
		this.createdAt = invoice.getCreatedAt();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Integer getCustomerId()
	{
		return customerId;
	}

	public void setCustomerId(java.lang.Integer customer)
	{
		this.customerId = customerId;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getSrNo()
	{
		return srNo;
	}

	public void setSrNo(int srNo)
	{
		this.srNo = srNo;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
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

	public boolean isSmsSent()
	{
		return smsSent;
	}

	public void setSmsSent(boolean smsSent)
	{
		this.smsSent = smsSent;
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

		InvoiceModel that = (InvoiceModel) o;

		if (id != that.id)
		{
			return false;
		}
		if (year != that.year)
		{
			return false;
		}
		if (month != that.month)
		{
			return false;
		}
		if (srNo != that.srNo)
		{
			return false;
		}
		if (amount != that.amount)
		{
			return false;
		}
		if (!customerId.equals(that.customerId))
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
		result = 31 * result + customerId.hashCode();
		result = 31 * result + year;
		result = 31 * result + month;
		result = 31 * result + srNo;
		result = 31 * result + amount;
		result = 31 * result + (note != null ? note.hashCode() : 0);
		result = 31 * result + createdAt.hashCode();
		return result;
	}
}
