package com.emsays.path.dao;

import com.emsays.path.GV;
import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.CustomerChangeLogDTO;
import com.emsays.path.dto.CustomerDTO;
import java.util.List;
import java.util.Objects;
import org.hibernate.Session;

public class CustomerSer
{

	Session session;
	CustomerDAO customerDAO;

	public CustomerSer(Session session)
	{
		this.session = session;
		customerDAO = new CustomerDAO(session);
	}

	public boolean create(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (!checkIsValidParent(customer, errorMsg))
		{
			return false;
		}

		if (GV.getAppStatus() == GV.enmAppStatus.UNREGISTERED && retrieve().size() >= 50)
		{
			errorMsg.append("You cannot make more than 50 entries in trial version");
			return false;
		}

		return (customerDAO.createOrUpdate(customer));
	}

	public boolean createChild(CustomerDTO customer, CustomerDTO child, StringBuilder errorMsg)
	{
		if (!checkIsValidChild(child, errorMsg))
		{
			return false;
		}

		child.setHouseNo1(customer.getHouseNo1());
		child.setArea(customer.getArea());
		child.setAmp(customer.isAmp());
		child.setHome(customer.isHome());

		customer.addChild(child);
		return (customerDAO.createOrUpdate(customer));
	}

	public boolean update(CustomerDTO customerOld, CustomerDTO customerNew, StringBuilder errorMsg)
	{
		if (!checkIsValidParent(customerNew, errorMsg))
		{
			customerDAO.resetObject(customerNew);
			return false;
		}

		CustomerChangeTypeSer changeTypeSer = new CustomerChangeTypeSer(session);

		if (customerOld.getAccountNo() != customerNew.getAccountNo())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.ACCOUNT_NO),
							String.valueOf(customerOld.getAccountNo())
					)
			);
		}

		if (!customerOld.getCustomerName().equals(customerNew.getCustomerName()))
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.CUSTOMER_NAME),
							customerOld.getCustomerName()
					)
			);
		}

		if (!customerOld.getHouseNo1().equals(customerNew.getHouseNo1())
				|| !customerOld.getHouseNo2().equals(customerNew.getHouseNo2())
				|| !customerOld.getHouseNo3().equals(customerNew.getHouseNo3()))
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.ADDRESS),
							customerOld.getHouseNo1()
							+ (customerOld.getHouseNo2() == null || customerOld.getHouseNo2().equals("") ? "" : "-" + customerOld.getHouseNo2())
							+ (customerOld.getHouseNo3() == null || customerOld.getHouseNo3().equals("") ? "" : "-" + customerOld.getHouseNo3())
					)
			);

		}

		if (customerOld.getArea().getId() != customerNew.getArea().getId())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.AREA),
							String.valueOf(customerOld.getArea().getId())
					)
			);

		}

		String temp = customerOld.getMobile() == null ? "" : customerOld.getMobile();
		if (!temp.equals(customerNew.getMobile()))
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.MOBILE),
							customerOld.getMobile()
					)
			);

		}

		if (customerOld.getPaymentType().getId() != customerNew.getPaymentType().getId())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.PAYMENT_TYPE),
							String.valueOf(customerOld.getPaymentType().getId())
					)
			);

		}

		if (customerOld.getPackage().getId() != customerNew.getPackage().getId())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.PACKAGE),
							String.valueOf(customerOld.getPackage().getId())
					)
			);

		}

		if (customerOld.isAmp() != customerNew.isAmp())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.AMP),
							String.valueOf(customerOld.isAmp())
					)
			);
		}

		if (customerOld.isHome() != customerNew.isHome())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.HOME),
							String.valueOf(customerOld.isHome())
					)
			);

		}

		if (!Objects.equals(customerOld.getAmount(), customerNew.getAmount()))
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.AMOUNT),
							String.valueOf(customerOld.getAmount())
					)
			);

		}

		if (customerOld.getCollectFrom().getId() != customerNew.getCollectFrom().getId())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.COLLECT_FROM),
							String.valueOf(customerOld.getCollectFrom().getId())
					)
			);
		}

		if (customerOld.isSuspended() != customerNew.isSuspended())
		{
			customerNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.SUSPENDED),
							String.valueOf(customerOld.isSuspended())
					)
			);
		}

		if ((customerDAO.createOrUpdate(customerNew)))
		{
			return true;
		}
		else
		{
			customerDAO.resetObject(customerNew);
			return false;
		}

	}

	public boolean suspend(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (customer.isSuspended())
		{
			errorMsg.append("Already suspended");
			return true;
		}

		CustomerDTO customerOld;
		PaymentTypeSer paymentTypeSer;

		customerOld = new CustomerDTO(customer);
		paymentTypeSer = new PaymentTypeSer(session);

		customer.setSuspended(true);
		customer.setPaymentType(paymentTypeSer.retrieveByName("NA"));

		return update(customerOld, customer, errorMsg);

	}

	public boolean suspendChild(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (customer.isSuspended())
		{
			errorMsg.append("Already suspended");
			return true;
		}

		CustomerDTO parentCustomer;
		CustomerDTO childCustomerOld;
		CustomerDTO childCustomerNew;

		parentCustomer = customer.getParent();
		childCustomerOld = new CustomerDTO(customer);
		childCustomerNew = customer;

		childCustomerNew.setSuspended(true);

		return updateChild(parentCustomer, childCustomerOld, childCustomerNew, errorMsg);

	}

	public boolean dc(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (customer.isDc())
		{
			return true;
		}

		CustomerDTO customerOld;

		customerOld = new CustomerDTO(customer);

		customer.setDc(true);

		return update(customerOld, customer, errorMsg);

	}

	public boolean unDc(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (!customer.isDc())
		{
			return true;
		}

		CustomerDTO customerOld;

		customerOld = new CustomerDTO(customer);

		customer.setDc(false);

		return update(customerOld, customer, errorMsg);

	}

	public boolean dcChild(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (customer.isDc())
		{
			return true;
		}

		CustomerDTO parentCustomer;
		CustomerDTO childCustomerOld;
		CustomerDTO childCustomerNew;

		parentCustomer = customer.getParent();
		childCustomerOld = new CustomerDTO(customer);
		childCustomerNew = customer;

		childCustomerNew.setDc(true);

		return updateChild(parentCustomer, childCustomerOld, childCustomerNew, errorMsg);

	}

	public boolean unDcChild(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (!customer.isDc())
		{
			return true;
		}

		CustomerDTO parentCustomer;
		CustomerDTO childCustomerOld;
		CustomerDTO childCustomerNew;

		parentCustomer = customer.getParent();
		childCustomerOld = new CustomerDTO(customer);
		childCustomerNew = customer;

		childCustomerNew.setDc(false);

		return updateChild(parentCustomer, childCustomerOld, childCustomerNew, errorMsg);

	}

	public boolean updateChild(CustomerDTO customer, CustomerDTO childOld, CustomerDTO childNew, StringBuilder errorMsg)
	{
		if (!checkIsValidChild(childNew, errorMsg))
		{
			customerDAO.resetObject(customer);
			return false;
		}

		CustomerChangeTypeSer changeTypeSer = new CustomerChangeTypeSer(session);

		if (childOld.getAccountNo() != childNew.getAccountNo())
		{
			childNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.ACCOUNT_NO),
							String.valueOf(childOld.getAccountNo())
					)
			);
		}

		if (!childOld.getCustomerName().equals(childNew.getCustomerName()))
		{
			childNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.CUSTOMER_NAME),
							String.valueOf(childOld.getCustomerName())
					)
			);
		}

		if (childOld.getPackage().getId() != childNew.getPackage().getId())
		{
			childNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.PACKAGE),
							String.valueOf(childOld.getPackage().getId())
					)
			);
		}

		if (!childOld.getMobile().equals(childNew.getMobile()))
		{
			childNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.MOBILE),
							String.valueOf(childOld.getMobile())
					)
			);
		}

		if (childOld.isSuspended() != childNew.isSuspended())
		{
			childNew.addChangeLog(
					new CustomerChangeLogDTO(
							changeTypeSer.get(CustomerChangeTypeSer.enmKeys.SUSPENDED),
							String.valueOf(childOld.isSuspended())
					)
			);
		}

		if (customerDAO.createOrUpdate(customer))
		{
			return true;
		}
		else
		{
			customerDAO.resetObject(customer);
			return false;
		}
	}

	public CustomerDTO retrieve(int id)
	{
		return customerDAO.retrieve(id);
	}

	public List<CustomerDTO> retrieve()
	{
		return customerDAO.retrieve(
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null,
				null
		);
	}

	public List<CustomerDTO> retrieve(
			String accountNo,
			String customerName,
			String locality1,
			String mobile,
			Integer paymentType,
			Integer package_,
			Boolean amt,
			Boolean home,
			Boolean repairing,
			Boolean dc,
			Boolean suspended,
			AreaDTO area)
	{
		return customerDAO.retrieve(
				accountNo,
				customerName,
				locality1,
				mobile,
				paymentType,
				package_,
				amt,
				home,
				repairing,
				dc,
				suspended,
				area
		);
	}

	public boolean delete(CustomerDTO customer)
	{
		return customerDAO.delete(customer);
	}

	public boolean deleteChild(CustomerDTO customer)
	{
		CustomerDTO parentCustomer = customer.getParent();
		parentCustomer.removeChild(customer);
		return customerDAO.createOrUpdate(customer);
	}

	private boolean checkIsValidParent(CustomerDTO customer, StringBuilder errorMsg)
	{
		if (customer == null)
		{
			errorMsg.append("Account no is required");
			return false;
		}

		if (customer.getAccountNo() <= 0)
		{

			errorMsg.append("Account no is required");
			return false;
		}

		if (customer.getCustomerName() == null || customer.getCustomerName().equals(""))
		{
			errorMsg.append("Customer name is required");
			return false;
		}

		if (customer.getHouseNo1() == null || customer.getHouseNo1().equals(""))
		{
			errorMsg.append("House no 1 is required");
			return false;
		}

		if (customer.getArea() == null)
		{
			errorMsg.append("Area is required");
			return false;
		}

		if (customer.getPaymentType() == null)
		{
			errorMsg.append("Payment type is required");
			return false;
		}

		if (customer.getPackage() == null)
		{
			errorMsg.append("Package is required");
			return false;
		}

		if (customer.getAmount() == null || customer.getAmount() < 0)
		{
			errorMsg.append("Amount is required");
			return false;
		}

		if (customer.getCollectFrom() == null)
		{
			errorMsg.append("Collect from is required");
			return false;
		}

		return true;
	}

	private boolean checkIsValidChild(CustomerDTO child, StringBuilder errorMsg)
	{
		if (child.getAccountNo() <= 0)
		{
			errorMsg.append("Account no is required");
			return false;
		}

		if (child.getCustomerName() == null || child.getCustomerName().equals(""))
		{
			errorMsg.append("Customer name is required");
			return false;
		}

		if (child.getPackage() == null)
		{
			errorMsg.append("Package is required");
			return false;
		}

		return true;
	}

	public CustomerDTO resetObject(CustomerDTO customer)
	{
		customer = customerDAO.resetObject(customer);
		return customer;
	}

}
