package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity(name = "invoice_receipt")
public class InvoiceReceiptDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	private InvoiceDTO invoice;

	@ManyToOne(fetch = FetchType.LAZY)
	private ReceiptDTO receipt;

	private int amount;

	@Column(name = "created_at")
	private Date createdAt;

	public InvoiceReceiptDTO()
	{
	}

	public InvoiceReceiptDTO(InvoiceDTO invoice, ReceiptDTO receipt, int amount)
	{
		this.invoice = invoice;
		this.receipt = receipt;
		this.amount = amount;
	}

	public int getId()
	{
		return id;
	}

	public InvoiceDTO getInvoice()
	{
		return invoice;
	}

	public void setInvoice(InvoiceDTO invoice)
	{
		this.invoice = invoice;
	}

	public ReceiptDTO getReceipt()
	{
		return receipt;
	}

	public void setReceipt(ReceiptDTO receipt)
	{
		this.receipt = receipt;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
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
	public int hashCode()
	{
		int hash = 7;
		hash = 73 * hash + Objects.hashCode(this.invoice);
		hash = 73 * hash + Objects.hashCode(this.receipt);
		hash = 73 * hash + this.amount;
		hash = 73 * hash + Objects.hashCode(this.createdAt);
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
		if (!(obj instanceof InvoiceReceiptDTO))
		{
			return false;
		}
		final InvoiceReceiptDTO other = (InvoiceReceiptDTO) obj;
		if (!Objects.equals(this.invoice.getId(), other.getInvoice().getId()))
		{
			return false;
		}
		if (!Objects.equals(this.receipt.getId(), other.getReceipt().getId()))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "InvoiceReceiptDTO{" + "invoice=" + String.valueOf(invoice.getId()) + ", receipt=" + String.valueOf(receipt.getId()) + ", amount=" + amount + ", createdAt=" + createdAt + '}';
	}

}
