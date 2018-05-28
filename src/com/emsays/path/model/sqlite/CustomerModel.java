package com.emsays.path.model.sqlite;

import com.emsays.path.dto.CustomerDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

import java.util.Date;

@Table(name = "customer")
public class CustomerModel
{

	@Column(name = "id")
	private int id;

	@Column(name = "account_no")
	private int accountNo;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "house_no1")
	private String houseNo1;

	@Column(name = "house_no2")
	private String houseNo2;

	@Column(name = "house_no3")
	private String houseNo3;

	@Column(name = "area_id")
	private int areaId;

	@Column(name = "official_address")
	private String officialAddress;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "payment_type_id")
	private Integer paymentTypeId;

	@Column(name = "package_id")
	private Integer packageId;

	@Column(name = "amp")
	private boolean amp;
	@Column(name = "home")
	private boolean home;
	@Column(name = "repair")
	private boolean repair;
	@Column(name = "dc")
	private boolean dc;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "offer_id")
	private Integer offerId;

	@Column(name = "collect_from_id")
	private Integer collectFromId;

	@Column(name = "suspended")
	private boolean suspended;

	@Column(name = "note")
	private String note;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "created_at")
	private Date createdAt;

	public CustomerModel()
	{
	}

	public CustomerModel(CustomerDTO customer)
	{
		this.id = customer.getId();
		this.accountNo = customer.getAccountNo();
		this.customerName = customer.getCustomerName();
		this.houseNo1 = customer.getHouseNo1();
		this.houseNo2 = customer.getHouseNo2();
		this.houseNo3 = customer.getHouseNo3();
		this.areaId = customer.getArea() == null ? null : customer.getArea().getId();
		this.officialAddress = customer.getOfficialAddress();
		this.mobile = customer.getMobile();
		this.paymentTypeId = customer.getPaymentType() == null ? null : customer.getPaymentType().getId();
		this.packageId = customer.getPackage() == null ? null : customer.getPackage().getId();
		this.amp = customer.isAmp();
		this.home = customer.isHome();
		this.repair = customer.isRepair();
		this.dc = customer.isDc();
		this.amount = customer.getAmount();
		this.offerId = customer.getOffer() == null ? null : customer.getOffer().getId();
		this.collectFromId = customer.getCollectFrom() == null ? null : customer.getCollectFrom().getId();
		this.suspended = customer.isSuspended();
		this.note = customer.getNote();
		if (customer.getParent() != null)
		{
			this.parentId = customer.getParent().getId();
		}
		this.createdAt = customer.getCreatedAt();
	}

	public CustomerModel(CustomerModel customer)
	{
		this.id = customer.id;
		this.accountNo = customer.accountNo;
		this.customerName = customer.customerName;
		this.houseNo1 = customer.houseNo1;
		this.houseNo2 = customer.houseNo2;
		this.houseNo3 = customer.houseNo3;
		this.areaId = customer.areaId;
		this.officialAddress = customer.officialAddress;
		this.mobile = customer.mobile;
		this.paymentTypeId = customer.paymentTypeId;
		this.packageId = customer.packageId;
		this.amp = customer.amp;
		this.home = customer.home;
		this.repair = customer.repair;
		this.dc = customer.dc;
		this.amount = customer.amount;
		this.offerId = customer.offerId;
		this.collectFromId = customer.collectFromId;
		this.suspended = customer.suspended;
		this.note = customer.note;
		this.parentId = customer.parentId;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
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

	public int getAreaId()
	{
		return areaId;
	}

	public void setAreaId(int areaId)
	{
		this.areaId = areaId;
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

	public Integer getPaymentTypeId()
	{
		return paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId)
	{
		this.paymentTypeId = paymentTypeId;
	}

	public Integer getPackageId()
	{
		return packageId;
	}

	public void setPackageId(Integer packageId)
	{
		this.packageId = packageId;
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

	public Integer getOfferId()
	{
		return offerId;
	}

	public void setOfferId(Integer offerId)
	{
		this.offerId = offerId;
	}

	public Integer getCollectFromId()
	{
		return collectFromId;
	}

	public void setCollectFromId(Integer collectFromId)
	{
		this.collectFromId = collectFromId;
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

	public Integer getParentId()
	{
		return parentId;
	}

	public void setParentId(Integer parentId)
	{
		this.parentId = parentId;
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

		CustomerModel that = (CustomerModel) o;

		if (id != that.id)
		{
			return false;
		}
		if (accountNo != that.accountNo)
		{
			return false;
		}
		if (areaId != that.areaId)
		{
			return false;
		}
		if (amp != that.amp)
		{
			return false;
		}
		if (home != that.home)
		{
			return false;
		}
		if (repair != that.repair)
		{
			return false;
		}
		if (dc != that.dc)
		{
			return false;
		}
		if (suspended != that.suspended)
		{
			return false;
		}
		if (!customerName.equals(that.customerName))
		{
			return false;
		}
		if (!houseNo1.equals(that.houseNo1))
		{
			return false;
		}
		if (houseNo2 != null ? !houseNo2.equals(that.houseNo2) : that.houseNo2 != null)
		{
			return false;
		}
		if (houseNo3 != null ? !houseNo3.equals(that.houseNo3) : that.houseNo3 != null)
		{
			return false;
		}
		if (officialAddress != null ? !officialAddress.equals(that.officialAddress) : that.officialAddress != null)
		{
			return false;
		}
		if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null)
		{
			return false;
		}
		if (!paymentTypeId.equals(that.paymentTypeId))
		{
			return false;
		}
		if (!packageId.equals(that.packageId))
		{
			return false;
		}
		if (!amount.equals(that.amount))
		{
			return false;
		}
		if (!offerId.equals(that.offerId))
		{
			return false;
		}
		if (!collectFromId.equals(that.collectFromId))
		{
			return false;
		}
		if (note != null ? !note.equals(that.note) : that.note != null)
		{
			return false;
		}

		Integer abc = parentId;
		Integer xyz = that.parentId;

		System.out.println(abc);
		System.out.println(xyz);

		if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null)
		{
			return false;
		}

		System.out.println(createdAt.getTime());
		System.out.println(that.createdAt.getTime());
		boolean res = createdAt.getTime() == that.createdAt.getTime();

		return res;
	}

	@Override
	public int hashCode()
	{
		int result = id;
		result = 31 * result + accountNo;
		result = 31 * result + customerName.hashCode();
		result = 31 * result + houseNo1.hashCode();
		result = 31 * result + (houseNo2 != null ? houseNo2.hashCode() : 0);
		result = 31 * result + (houseNo3 != null ? houseNo3.hashCode() : 0);
		result = 31 * result + areaId;
		result = 31 * result + (officialAddress != null ? officialAddress.hashCode() : 0);
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + paymentTypeId.hashCode();
		result = 31 * result + packageId.hashCode();
		result = 31 * result + (amp ? 1 : 0);
		result = 31 * result + (home ? 1 : 0);
		result = 31 * result + (repair ? 1 : 0);
		result = 31 * result + (dc ? 1 : 0);
		result = 31 * result + amount.hashCode();
		result = 31 * result + offerId.hashCode();
		result = 31 * result + collectFromId.hashCode();
		result = 31 * result + (suspended ? 1 : 0);
		result = 31 * result + (note != null ? note.hashCode() : 0);
		result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
		result = 31 * result + createdAt.hashCode();
		return result;
	}
}
