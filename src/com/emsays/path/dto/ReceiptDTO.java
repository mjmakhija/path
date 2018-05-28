package com.emsays.path.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity(name = "receipt")
public class ReceiptDTO implements Serializable   
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
   
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private CustomerDTO customer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = true)
	private AccountDTO account;

	private int amount;

	@Column(name = "date")
	private Date date;

	@Column(nullable = true)
	private String note;

	@OneToMany(mappedBy = "receipt",cascade = CascadeType.ALL,  orphanRemoval = true)
	private List<InvoiceReceiptDTO> invoices = new ArrayList<>();

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

	public AccountDTO getAccount()
	{
		return account;
	}

	public void setAccount(AccountDTO account)
	{
		this.account = account;
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

	public List<InvoiceReceiptDTO> getInvoices()
	{
		return invoices;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 67 * hash + this.id;
		hash = 67 * hash + Objects.hashCode(this.customer);
		hash = 67 * hash + Objects.hashCode(this.account);
		hash = 67 * hash + this.amount;
		hash = 67 * hash + Objects.hashCode(this.date);
		hash = 67 * hash + Objects.hashCode(this.note);
		hash = 67 * hash + Objects.hashCode(this.invoices);
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
		if (!(obj instanceof ReceiptDTO))
		{
			return false;
		}
		final ReceiptDTO other = (ReceiptDTO) obj;
		if (this.id != other.getId())
		{
			return false;
		}
		if (this.amount != other.getAmount())
		{
			return false;
		}
		if (!Objects.equals(this.note, other.getNote()))
		{
			return false;
		}
		if (!Objects.equals(this.account, other.getAccount()))
		{
			return false;
		}
		if (!Objects.equals(this.date.getTime(), other.getDate().getTime()))
		{
			return false;
		}
		if (!((this.getInvoices().size() == other.getInvoices().size()) && (this.getInvoices().containsAll(other.getInvoices()))))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "ReceiptDTO{" + "id=" + id + ", account=" + account + ", amount=" + amount + ", date=" + date + ", note=" + note + ", invoices=" + invoices + '}';
	}

}
