package com.emsays.path.dao;

import antlr.emsays.path.AlphanumComparator;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.CustomerDTO_;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceDTO_;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.MonthYearDTO;
import com.emsays.path.dto.PaymentTypeDTO;
import com.emsays.path.dto.ReceiptDTO;
import java.util.*;
import javax.persistence.criteria.*;
import org.hibernate.Session;

public class InvoiceSer
{

	Session session;
	CustomerDAO customerDAO;
	InvoiceDAO invoiceDAO;

	public InvoiceSer(Session session)
	{
		this.session = session;
		invoiceDAO = new InvoiceDAO(session);
		customerDAO = new CustomerDAO(session);
	}

	public List<InvoiceDTO> retrieve()
	{
		return getInvoicesWAdditionalData(invoiceDAO.retrieve());
	}

	public boolean create(CustomerDTO customer, InvoiceDTO invoice, StringBuilder errorMsg)
	{
		if (!checkIsValid(invoice, errorMsg))
		{
			return false;
		}

		invoice.setSrNo(invoiceDAO.getNextInvoiceNo());
		invoice.setYear(invoice.getYear());
		customer.addInvoice(invoice);

		if ((customerDAO.createOrUpdate(customer)))
		{
			return true;
		}
		else
		{
			customer.removeInvoice(invoice);
			return false;
		}
	}

	public boolean create(PaymentTypeDTO paymentType, int month, int year)
	{

		StringBuilder errorMsg = new StringBuilder();
		List<CustomerDTO> customers = customerDAO.retrieve(
				null,
				null,
				null,
				null,
				paymentType.getId(),
				null,
				null,
				null,
				null,
				false,
				false,
				null
		);
		for (CustomerDTO customer : customers)
		{
			if (!exists(
					customer.getId(),
					month,
					year))
			{

				InvoiceDTO invoice = new InvoiceDTO();

				invoice.setMonth(month);
				invoice.setYear(year);
				invoice.setAmount(customer.getAmount());

				create(customer, invoice, errorMsg);

			}
		}
		return true;
	}

	public boolean update(CustomerDTO customer, InvoiceDTO invoice, StringBuilder errorMsg)
	{
		if (!checkIsValid(invoice, errorMsg))
		{
			return false;
		}

		invoice.setYear(invoice.getYear());

		if ((customerDAO.createOrUpdate(customer)))
		{
			return true;
		}
		else
		{
			customerDAO.resetObject(customer);
			return false;
		}
	}

	public void mapReceipts(List<InvoiceDTO> invoices)
	{
		ReceiptSer receiptSer = new ReceiptSer(session);

		for (InvoiceDTO invoice : invoices)
		{
			if (invoice.getReceiptes().size() == 0)
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.set(Calendar.DAY_OF_MONTH, 1);
				cal.set(Calendar.MONTH, invoice.getMonth() - 1);
				cal.set(Calendar.YEAR, invoice.getYear());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(new Date());
				cal2.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				cal2.set(Calendar.MONTH, invoice.getMonth() - 1);
				cal2.set(Calendar.YEAR, invoice.getYear());
				cal2.set(Calendar.HOUR_OF_DAY, 23);
				cal2.set(Calendar.MINUTE, 59);
				cal2.set(Calendar.SECOND, 59);
				cal2.set(Calendar.MILLISECOND, 999);

				List<ReceiptDTO> receipts = receiptSer.retrieve(null, null, invoice.getCustomer().getId(), null, cal.getTime(), cal2.getTime(), invoice.getAmount(), null);

				List<ReceiptDTO> receiptsUnused = getReceiptsUnused(receipts);

				if (receiptsUnused.size() == 1)
				{
					invoice.addReceipt(receiptsUnused.get(0), invoice.getAmount());
					customerDAO.createOrUpdate(invoice.getCustomer());
				}
			}
		}
	}

	private List<InvoiceDTO> getInvoicesWAdditionalData(List<InvoiceDTO> invoices)
	{
		AccountSer accountSer = new AccountSer(session);

		for (InvoiceDTO invoice : invoices)
		{

			for (InvoiceReceiptDTO invoiceReceiptDTO : invoice.getReceiptes())
			{
				if (invoiceReceiptDTO.getReceipt().getAccount() == accountSer.get(AccountSer.enmKeys.WRITE_OFF))
				{
					invoice.setAmountRecievedWriteOff(
							invoice.getAmountRecievedWriteOff()
							+ invoiceReceiptDTO.getAmount()
					);

				}
				else if (invoiceReceiptDTO.getReceipt().getAccount() == accountSer.get(AccountSer.enmKeys.NA))
				{
					invoice.setAmountRecievedCash(
							invoice.getAmountRecievedCash()
							+ invoiceReceiptDTO.getAmount()
					);
				}

			}

			invoice.setAmountDue(invoice.getAmount() - invoice.getAmountRecievedCash() - invoice.getAmountRecievedWriteOff());
		}

		return invoices;
	}

	private List<ReceiptDTO> getReceiptsUnused(List<ReceiptDTO> receiptsAll)
	{
		List<ReceiptDTO> resReceipts = new ArrayList<>();

		for (ReceiptDTO receipt : receiptsAll)
		{
			if (receipt.getInvoices().size() == 0)
			{
				resReceipts.add(receipt);
			}

		}

		return resReceipts;

	}

	private boolean checkIsValid(InvoiceDTO invoice, StringBuilder errorMsg)
	{

		if (invoice.getYear() < 2000 || invoice.getYear() > 2099)
		{
			errorMsg.append("Year should be between 2000 to 2099");
			return false;
		}

		return true;
	}

	public boolean writeOff(InvoiceDTO invoice, StringBuilder errorMsg)
	{
		CustomerDTO customer;
		List<InvoiceReceiptDTO> receipts;
		ReceiptDTO receipt;
		AccountSer accountSer;
		Calendar cal;
		int amtReceived = 0;

		customer = invoice.getCustomer();
		receipts = invoice.getReceiptes();
		accountSer = new AccountSer(session);
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);

		for (InvoiceReceiptDTO receiptTemp : receipts)
		{
			amtReceived += receiptTemp.getAmount();
		}

		if (amtReceived >= invoice.getAmount())
		{
			errorMsg.append("No need to write off");
			return false;
		}

		receipt = new ReceiptDTO();
		receipt.setDate(cal.getTime());
		receipt.setAmount(invoice.getAmount() - amtReceived);
		receipt.setAccount(accountSer.get(AccountSer.enmKeys.WRITE_OFF));
		receipt.setCustomer(customer);

		if (!new ReceiptDAO(session).createOrUpdate(receipt))
		{
			errorMsg.append("Some error occured saving receipt");
			return false;
		}

		InvoiceReceiptDTO invoiceReceipt = new InvoiceReceiptDTO(invoice, receipt, receipt.getAmount());
		if (!new InvoiceReceiptDAO(session).createOrUpdate(invoiceReceipt))
		{
			errorMsg.append("Some error occured saving receipt invoice mapping");
			return false;
		}

		return true;
	}

	public List<MonthYearDTO> mGetMonthYear()
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<MonthYearDTO> criteria = builder.createQuery(MonthYearDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);

		criteria.multiselect(root.get(InvoiceDTO_.month), root.get(InvoiceDTO_.year));
		criteria.distinct(true);

		List<MonthYearDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return vObjInvoice;

	}

	public List<InvoiceDTO> mFindByCustomerId(int customerId)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get(InvoiceDTO_.customer).get(CustomerDTO_.id), customerId));
		List<InvoiceDTO> vObjReceipt = session.createQuery(criteria).getResultList();

		return getInvoicesWAdditionalData(vObjReceipt);
	}

	private boolean exists(Integer customerId, Integer month, Integer year)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(builder.equal(root.get(InvoiceDTO_.customer).get(CustomerDTO_.id), customerId));
		predicates.add(builder.equal(root.get(InvoiceDTO_.month), month));
		predicates.add(builder.equal(root.get(InvoiceDTO_.year), year));

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<InvoiceDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return (vObjInvoice.size() > 0);
	}

	public InvoiceDTO mFindByName(String vName)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		criteria.select(root);
		//criteria.where(builder.equal(root.get(InvoiceDTO_.customerName), vName));

		List<InvoiceDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return vObjInvoice.isEmpty() ? null : getInvoicesWAdditionalData(vObjInvoice).get(0);
	}

	public List<InvoiceDTO> mSearch(String vName, String vNote, Integer vMonth, Integer year)
	{

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaQuery<InvoiceDTO> criteria = builder.createQuery(InvoiceDTO.class);
		Root<InvoiceDTO> root = criteria.from(InvoiceDTO.class);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (vName != null && !vName.equals(""))
		{
			//predicates.add(builder.like(root.get(InvoiceDTO_.customerName), "%" + vName + "%"));
		}

		if (vNote != null && !vNote.equals(""))
		{
			predicates.add(builder.like(root.get(InvoiceDTO_.note), "%" + vNote + "%"));
		}

		if (vMonth != null && vMonth > 0)
		{
			predicates.add(builder.equal(root.get(InvoiceDTO_.month), vMonth));
		}

		if (year != null && year > 0)
		{
			predicates.add(builder.equal(root.get(InvoiceDTO_.year), year));
		}

		criteria.select(root);
		criteria.where(predicates.toArray(new Predicate[]
		{
		}));

		List<InvoiceDTO> vObjInvoice = session.createQuery(criteria).getResultList();

		return getInvoicesWAdditionalData(vObjInvoice);
	}

	public boolean delete(InvoiceDTO invoice)
	{
		CustomerDTO customer = invoice.getCustomer();
		customer.removeInvoice(invoice);
		return customerDAO.createOrUpdate(customer);
	}

	public static List<InvoiceDTO> sort(List<InvoiceDTO> invoices)
	{
		List<InvoiceDTO> res = new ArrayList<>();

		Comparator<InvoiceDTO> sortByArea
				= (p, o) -> p.getCustomer().getArea().getName().compareToIgnoreCase(o.getCustomer().getArea().getName());
		Comparator<InvoiceDTO> sortHouseNo1
				= (p, o) -> new AlphanumComparator().compare(p.getCustomer().getHouseNo1(), o.getCustomer().getHouseNo1());
		Comparator<InvoiceDTO> sortHouseNo2
				= (p, o) -> new AlphanumComparator().compare(p.getCustomer().getHouseNo2(), o.getCustomer().getHouseNo2());
		Comparator<InvoiceDTO> sortHouseNo3
				= (p, o) -> new AlphanumComparator().compare(p.getCustomer().getHouseNo3(), o.getCustomer().getHouseNo3());

		invoices
				.stream()
				.sorted(
						sortByArea
								.thenComparing(sortHouseNo1)
								.thenComparing(sortHouseNo2)
								.thenComparing(sortHouseNo3)
				)
				.forEach((t) -> res.add(t));

		return res;
	}

}
