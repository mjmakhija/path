package com.emsays.path.model.sqlite;

import com.emsays.path.dto.ReceiptDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "receipt")
public class ReceiptModel
{

	@Column(name = "id")
	private int id;

	@Column(name = "customer_id")
	private Integer customerId;

	@Column(name = "account_id")
	private Integer accountId;

	@Column(name = "amount")
	private int amount;

	@Column(name = "date")
	private Date date;

	@Column(name = "note")
	private String note;

	@Column(name = "syncd")
	private boolean syncd;

	public ReceiptModel()
	{
	}

	public ReceiptModel(ReceiptDTO receipt)
	{
		this.id = receipt.getId();
		this.customerId = receipt.getCustomer().getId();
		this.accountId = receipt.getAccount().getId();
		this.amount = receipt.getAmount();
		this.date = receipt.getDate();
		this.note = receipt.getNote();
		this.syncd = true;
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

	public void setCustomerId(Integer customerId)
	{
		this.customerId = customerId;
	}

	public Integer getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Integer accountId)
	{
		this.accountId = accountId;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public boolean isSyncd()
	{
		return syncd;
	}

	public void setSyncd(boolean syncd)
	{
		this.syncd = syncd;
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

		ReceiptModel that = (ReceiptModel) o;

		if (id != that.id)
		{
			return false;
		}
		if (amount != that.amount)
		{
			return false;
		}
		if (syncd != that.syncd)
		{
			return false;
		}
		if (!customerId.equals(that.customerId))
		{
			return false;
		}
		if (!accountId.equals(that.accountId))
		{
			return false;
		}
		if (!date.equals(that.date))
		{
			return false;
		}
		return note != null ? note.equals(that.note) : that.note == null;
	}

	@Override
	public int hashCode()
	{
		int result = id;
		result = 31 * result + customerId.hashCode();
		result = 31 * result + accountId.hashCode();
		result = 31 * result + amount;
		result = 31 * result + date.hashCode();
		result = 31 * result + (note != null ? note.hashCode() : 0);
		result = 31 * result + (syncd ? 1 : 0);
		return result;
	}
}
