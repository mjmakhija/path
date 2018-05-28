package com.emsays.path.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Embeddable
public class InvoiceReceiptId implements Serializable
{

	@Column(name = "invoice_id")
	private Long invoiceId;

	@Column(name = "receipt_id")
	private Long receiptId;

	public InvoiceReceiptId()
	{
	}

	public InvoiceReceiptId(Long invoiceId, Long receiptId)
	{
		this.invoiceId = invoiceId;
		this.receiptId = receiptId;
	}

	public Long getInvoiceId()
	{
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId)
	{
		this.invoiceId = invoiceId;
	}

	public Long getReceiptId()
	{
		return receiptId;
	}

	public void setReceiptId(Long receiptId)
	{
		this.receiptId = receiptId;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.invoiceId);
		hash = 17 * hash + Objects.hashCode(this.receiptId);
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
		final InvoiceReceiptId other = (InvoiceReceiptId) obj;
		if (!Objects.equals(this.invoiceId, other.invoiceId))
		{
			return false;
		}
		if (!Objects.equals(this.receiptId, other.receiptId))
		{
			return false;
		}
		return true;
	}

}
