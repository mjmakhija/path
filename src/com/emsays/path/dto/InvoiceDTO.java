package com.emsays.path.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "invoice")
public class InvoiceDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private CustomerDTO customer;

	private int year;
	private int month;

	@Column(name = "sr_no")
	private int srNo;

	private int amount;

	@Column(nullable = true)
	private String note;

	@Column(name = "created_at")
	@Generated(value = GenerationTime.INSERT)
	private Date createdAt;

	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InvoiceReceiptDTO> receipts = new ArrayList<>();

	@Transient
	private int amountRecievedCash = 0;

	@Transient
	private int amountRecievedWriteOff = 0;

	@Transient
	private int amountDue = 0;

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

	public List<InvoiceReceiptDTO> getReceiptes()
	{
		return receipts;
	}

	public void addReceipt(ReceiptDTO receipt, int amount)
	{
		InvoiceReceiptDTO invoiceReceipt = new InvoiceReceiptDTO(this, receipt, amount);
		receipts.add(invoiceReceipt);
		receipt.getInvoices().add(invoiceReceipt);
	}

	public void addReceipt(InvoiceReceiptDTO invoiceReceipt)
	{
		receipts.add(invoiceReceipt);
		invoiceReceipt.getReceipt().getInvoices().add(invoiceReceipt);
	}

	public void removeReceipt(ReceiptDTO receipt)
	{
		for (Iterator<InvoiceReceiptDTO> iterator = receipts.iterator();
			iterator.hasNext();)
		{
			InvoiceReceiptDTO invoiceReceipt = iterator.next();

			if (invoiceReceipt.getReceipt().getId() == receipt.getId())
			{
				iterator.remove();
				receipts.remove(invoiceReceipt);
				invoiceReceipt.getReceipt().getInvoices().remove(invoiceReceipt);
			}
		}
	}

	public void removeReceipt(InvoiceReceiptDTO invoiceReceipt)
	{
		receipts.remove(invoiceReceipt);
		invoiceReceipt.getReceipt().getInvoices().remove(invoiceReceipt);
	}

	public int getAmountRecievedCash()
	{
		return amountRecievedCash;
	}

	public void setAmountRecievedCash(int amountRecievedCash)
	{
		this.amountRecievedCash = amountRecievedCash;
	}

	public int getAmountRecievedWriteOff()
	{
		return amountRecievedWriteOff;
	}

	public void setAmountRecievedWriteOff(int amountRecievedWriteOff)
	{
		this.amountRecievedWriteOff = amountRecievedWriteOff;
	}

	public int getAmountDue()
	{
		return amountDue;
	}

	public void setAmountDue(int amountDue)
	{
		this.amountDue = amountDue;
	}

	@Override
	public int hashCode()
	{
		int hash = 3;
		hash = 17 * hash + this.id;
		hash = 17 * hash + Objects.hashCode(this.customer);
		hash = 17 * hash + this.year;
		hash = 17 * hash + this.month;
		hash = 17 * hash + this.srNo;
		hash = 17 * hash + this.amount;
		hash = 17 * hash + Objects.hashCode(this.note);
		hash = 17 * hash + Objects.hashCode(this.createdAt);
		hash = 17 * hash + Objects.hashCode(this.receipts);
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
		if (!(obj instanceof InvoiceDTO))
		{
			return false;
		}
		final InvoiceDTO other = (InvoiceDTO) obj;
		if (this.id != other.getId())
		{
			return false;
		}
		if (this.year != other.getYear())
		{
			return false;
		}
		if (this.month != other.getMonth())
		{
			return false;
		}
		if (this.srNo != other.getSrNo())
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
		if (!Objects.equals(this.createdAt, other.getCreatedAt()))
		{
			return false;
		}
		if (!((this.getReceiptes().size() == other.getReceiptes().size()) && (this.getReceiptes().containsAll(other.getReceiptes()))))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "InvoiceDTO{" + "id=" + id + ", year=" + year + ", month=" + month + ", srNo=" + srNo + ", amount=" + amount + ", note=" + note + ", createdAt=" + createdAt + ", receipts=" + receipts + '}';
	}

}
