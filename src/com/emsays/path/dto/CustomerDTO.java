package com.emsays.path.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity(name = "customer")
public class CustomerDTO implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "account_no")
	private int accountNo;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "house_no1")
	private String houseNo1;

	@Column(name = "house_no2", nullable = true)
	private String houseNo2;

	@Column(name = "house_no3", nullable = true)
	private String houseNo3;

	//@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id", nullable = true)
	private AreaDTO area;

	@Column(name = "official_address", nullable = true)
	private String officialAddress;

	@Column(nullable = true)
	private String mobile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_type_id", nullable = true)
	private PaymentTypeDTO paymentType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "package_id")
	private PackageDTO package_;

	private boolean amp;
	private boolean home;
	private boolean repair;
	private boolean dc;

	@Column(nullable = true)
	private Integer amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = true)
	private OfferDTO offer;

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "collect_from_id", nullable = true)
	private CollectFromDTO collectFrom;

	private boolean suspended;

	@Column(nullable = true)
	private String note;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "parent_id", nullable = true)
	private CustomerDTO parent;

	@OneToMany(mappedBy = "parent", cascade =
	{
		CascadeType.ALL, CascadeType.REMOVE
	}, orphanRemoval = true)
	private List<CustomerDTO> childs = new ArrayList<CustomerDTO>();

	@OneToMany(mappedBy = "customer", cascade =
	{
		CascadeType.ALL, CascadeType.REMOVE
	}, orphanRemoval = true)
	private List<InvoiceDTO> invoices = new ArrayList<InvoiceDTO>();

	@OneToMany(mappedBy = "customer")
	@OrderBy("id")
	private List<ReceiptDTO> receipts = new ArrayList<ReceiptDTO>();

	@OneToMany(mappedBy = "customer", cascade =
	{
		CascadeType.ALL, CascadeType.MERGE, CascadeType.REMOVE
	}, orphanRemoval = true)
	private List<CustomerChangeLogDTO> changeLogs = new ArrayList<>();

	@Column(name = "created_at")
	@Generated(value = GenerationTime.INSERT)
	private Date createdAt;
	
	public CustomerDTO()
	{
	}

	public CustomerDTO(CustomerDTO customer)
	{
		this.id = customer.id;
		this.accountNo = customer.accountNo;
		this.customerName = customer.customerName;
		this.houseNo1 = customer.houseNo1;
		this.houseNo2 = customer.houseNo2;
		this.houseNo3 = customer.houseNo3;
		this.area = customer.area;
		this.officialAddress = customer.officialAddress;
		this.mobile = customer.mobile;
		this.paymentType = customer.paymentType;
		this.package_ = customer.package_;
		this.amp = customer.amp;
		this.home = customer.home;
		this.repair = customer.repair;
		this.dc = customer.dc;
		this.amount = customer.amount;
		this.offer = customer.offer;
		this.collectFrom = customer.collectFrom;
		this.suspended = customer.suspended;
		this.note = customer.note;
		this.parent = customer.parent;
	}

	public int getId()
	{
		return id;
	}

	public int getAccountNo()
	{
		return accountNo;
	}

	public void setAccountNo(int accountNo)
	{
		this.accountNo = accountNo;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getHouseNo1()
	{
		return houseNo1;
	}

	public void setHouseNo1(String houseNo1)
	{
		this.houseNo1 = houseNo1;
	}

	public String getHouseNo2()
	{
		return houseNo2;
	}

	public void setHouseNo2(String houseNo2)
	{
		this.houseNo2 = houseNo2;
	}

	public String getHouseNo3()
	{
		return houseNo3;
	}

	public void setHouseNo3(String houseNo3)
	{
		this.houseNo3 = houseNo3;
	}

	public AreaDTO getArea()
	{
		return area;
	}

	public void setArea(AreaDTO area)
	{
		this.area = area;
	}

	public String getOfficialAddress()
	{
		return officialAddress;
	}

	public void setOfficialAddress(String officialAddress)
	{
		this.officialAddress = officialAddress;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public PaymentTypeDTO getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(PaymentTypeDTO paymentType)
	{
		this.paymentType = paymentType;
	}

	public PackageDTO getPackage()
	{
		return package_;
	}

	public void setPackage(PackageDTO package_)
	{
		this.package_ = package_;
	}

	public boolean isAmp()
	{
		return amp;
	}

	public void setAmp(boolean amp)
	{
		this.amp = amp;
	}

	public boolean isHome()
	{
		return home;
	}

	public void setHome(boolean home)
	{
		this.home = home;
	}

	public boolean isRepair()
	{
		return repair;
	}

	public void setRepair(boolean repair)
	{
		this.repair = repair;
	}

	public boolean isDc()
	{
		return dc;
	}

	public void setDc(boolean dc)
	{
		this.dc = dc;
	}

	public Integer getAmount()
	{
		return amount;
	}

	public void setAmount(Integer amount)
	{
		this.amount = amount;
	}

	public OfferDTO getOffer()
	{
		return offer;
	}

	public void setOffer(OfferDTO offer)
	{
		this.offer = offer;
	}

	public CollectFromDTO getCollectFrom()
	{
		return collectFrom;
	}

	public void setCollectFrom(CollectFromDTO collectFrom)
	{
		this.collectFrom = collectFrom;
	}

	public boolean isSuspended()
	{
		return suspended;
	}

	public void setSuspended(boolean suspended)
	{
		this.suspended = suspended;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public CustomerDTO getParent()
	{
		return parent;
	}

	public void setParent(CustomerDTO parent)
	{
		this.parent = parent;
	}

	public List<CustomerDTO> getChilds()
	{
		return childs;
	}

	public void addChild(CustomerDTO child)
	{
		childs.add(child);
		child.setParent(this);
	}

	public void removeChild(CustomerDTO child)
	{
		childs.remove(child);
		child.setParent(null);
	}

	public List<InvoiceDTO> getInvoices()
	{
		return invoices;
	}

	public void addInvoice(InvoiceDTO invoice)
	{
		invoices.add(invoice);
		invoice.setCustomer(this);
	}

	public void removeInvoice(InvoiceDTO invoice)
	{
		invoices.remove(invoice);
		invoice.setCustomer(null);
	}

	public List<ReceiptDTO> getReceipts()
	{
		return receipts;
	}

	public void addChangeLog(CustomerChangeLogDTO changeLog)
	{
		changeLogs.add(changeLog);
		changeLog.setCustomer(this);
	}

	public List<CustomerChangeLogDTO> getChangeLogs()
	{
		return changeLogs;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	@Override
	public int hashCode()
	{
		int hash = 5;
		hash = 11 * hash + this.id;
		hash = 11 * hash + this.accountNo;
		hash = 11 * hash + Objects.hashCode(this.customerName);
		hash = 11 * hash + Objects.hashCode(this.houseNo1);
		hash = 11 * hash + Objects.hashCode(this.houseNo2);
		hash = 11 * hash + Objects.hashCode(this.houseNo3);
		hash = 11 * hash + Objects.hashCode(this.area);
		hash = 11 * hash + Objects.hashCode(this.officialAddress);
		hash = 11 * hash + Objects.hashCode(this.mobile);
		hash = 11 * hash + Objects.hashCode(this.paymentType);
		hash = 11 * hash + Objects.hashCode(this.package_);
		hash = 11 * hash + (this.amp ? 1 : 0);
		hash = 11 * hash + (this.home ? 1 : 0);
		hash = 11 * hash + (this.repair ? 1 : 0);
		hash = 11 * hash + (this.dc ? 1 : 0);
		hash = 11 * hash + Objects.hashCode(this.amount);
		hash = 11 * hash + Objects.hashCode(this.offer);
		hash = 11 * hash + Objects.hashCode(this.collectFrom);
		hash = 11 * hash + (this.suspended ? 1 : 0);
		hash = 11 * hash + Objects.hashCode(this.note);
		hash = 11 * hash + Objects.hashCode(this.parent);
		hash = 11 * hash + Objects.hashCode(this.childs);
		hash = 11 * hash + Objects.hashCode(this.invoices);
		hash = 11 * hash + Objects.hashCode(this.receipts);
		hash = 11 * hash + Objects.hashCode(this.changeLogs);
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
		if (!(obj instanceof CustomerDTO))
		{
			return false;
		}
		final CustomerDTO other = (CustomerDTO) obj;
		if (this.id != other.getId())
		{
			return false;
		}
		if (this.accountNo != other.getAccountNo())
		{
			return false;
		}
		if (this.amp != other.isAmp())
		{
			return false;
		}
		if (this.home != other.isHome())
		{
			return false;
		}
		if (this.repair != other.isRepair())
		{
			return false;
		}
		if (this.dc != other.isDc())
		{
			return false;
		}
		if (this.suspended != other.isSuspended())
		{
			return false;
		}
		if (!Objects.equals(this.customerName, other.getCustomerName()))
		{
			return false;
		}
		if (!Objects.equals(this.houseNo1, other.getHouseNo1()))
		{
			return false;
		}
		if (!Objects.equals(this.houseNo2, other.getHouseNo2()))
		{
			return false;
		}
		if (!Objects.equals(this.houseNo3, other.getHouseNo3()))
		{
			return false;
		}
		if (!Objects.equals(this.officialAddress, other.getOfficialAddress()))
		{
			return false;
		}
		if (!Objects.equals(this.mobile, other.getMobile()))
		{
			return false;
		}
		if (!Objects.equals(this.note, other.getNote()))
		{
			return false;
		}
		if (!Objects.equals(this.getArea(), other.getArea()))
		{
			return false;
		}
		if (!Objects.equals(this.getPaymentType(), other.getPaymentType()))
		{
			return false;
		}
		if (!Objects.equals(this.getPackage(), other.getPackage()))
		{
			return false;
		}
		if (!Objects.equals(this.amount, other.getAmount()))
		{
			return false;
		}
		if (!Objects.equals(this.getOffer(), other.getOffer()))
		{
			return false;
		}
		if (!Objects.equals(this.getCollectFrom(), other.getCollectFrom()))
		{
			return false;
		}
		if (!((this.getChilds().size() == other.getChilds().size()) && (this.getChilds().containsAll(other.getChilds()))))
		{
			return false;
		}
		if (!((this.getInvoices().size() == other.getInvoices().size()) && (this.getInvoices().containsAll(other.getInvoices()))))
		{
			return false;
		}
		if (!((this.getReceipts().size() == other.getReceipts().size()) && (this.getReceipts().containsAll(other.getReceipts()))))
		{
			return false;
		}
		if (!((this.getChangeLogs().size() == other.getChangeLogs().size()) && (this.getChangeLogs().containsAll(other.getChangeLogs()))))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "CustomerDTO{" + "id=" + id + ", accountNo=" + accountNo + ", customerName=" + customerName + ", houseNo1=" + houseNo1 + ", houseNo2=" + houseNo2 + ", houseNo3=" + houseNo3 + ", area=" + area + ", officialAddress=" + officialAddress + ", mobile=" + mobile + ", paymentType=" + paymentType + ", package_=" + package_ + ", amp=" + amp + ", home=" + home + ", repair=" + repair + ", dc=" + dc + ", amount=" + amount + ", offer=" + offer + ", collectFrom=" + collectFrom + ", suspended=" + suspended + ", note=" + note + ", childs=" + childs + ", invoices=" + invoices + ", receipts=" + receipts + ", changeLogs=" + changeLogs + '}';
	}

}
