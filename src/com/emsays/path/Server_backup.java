package com.emsays.path;

import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.CustomerSer;
import com.emsays.path.dao.ReceiptSer;
import com.emsays.path.dto.AccountDTO;
import com.emsays.path.dto.AreaDTO;
import com.emsays.path.dto.CollectFromDTO;
import com.emsays.path.dto.CustomerChangeLogDTO;
import com.emsays.path.dto.CustomerChangeTypeDTO;
import com.emsays.path.dto.CustomerDTO;
import com.emsays.path.dto.InvoiceDTO;
import com.emsays.path.dto.InvoiceReceiptDTO;
import com.emsays.path.dto.OfferDTO;
import com.emsays.path.dto.PackageDTO;
import com.emsays.path.dto.PaymentTypeDTO;
import com.emsays.path.dto.ReceiptDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

public class Server_backup
{

	LogWriter lw = null;
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	String message = "";
	final int socketServerPORT = 5792;
	int count = 0;

	public Server_backup()
	{
	}

	public void setLogWriter(LogWriter logWriter)
	{
		this.lw = logWriter;
	}

	public void startServer()
	{
		if (serverSocket == null || serverSocket.isClosed())
		{
			Thread socketServerThread = new Thread(new SocketServerThread());
			socketServerThread.start();
		}
		else
		{
			writeLog("Server already running");
		}
	}

	public synchronized void writeLog(String str)
	{
		if (lw != null)
		{
			lw.writeLog(str);
		}
	}

	public synchronized void stopServer()
	{
		stopClients();
		if (serverSocket != null && !serverSocket.isClosed())
		{
			try
			{
				writeLog("Stopping server");
				serverSocket.close();
				writeLog("Stopped server");
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private synchronized void stopClients()
	{
		if (clientSocket != null && !clientSocket.isClosed())
		{
			try
			{
				String afterMessage = "Client closed #" + count + " from "
						+ clientSocket.getInetAddress() + ":"
						+ clientSocket.getPort() + "\n";
				writeLog("Closing client " + count + " from "
						+ clientSocket.getInetAddress() + ":"
						+ clientSocket.getPort() + "\n");

				clientSocket.close();
				count--;

				writeLog(afterMessage);
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getPort()
	{
		return socketServerPORT;
	}

	private class SocketServerThread extends Thread
	{

		@Override
		public void run()
		{
			try
			{
				// create ServerSocket using specified port
				serverSocket = new ServerSocket(socketServerPORT);

				writeLog("Server started \n");

				while (true)
				{
					// block the call until connection is created and return
					// Socket object
					if (count == 0)
					{
						Socket socket = serverSocket.accept();
						count++;
						message += "#" + count + " from "
								+ socket.getInetAddress() + ":"
								+ socket.getPort() + "\n";

						writeLog(message);
						clientSocket = socket;
					}

					//SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket, count);
					//socketServerReplyThread.run();
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 0
	 * out sending in ok out invoices in ok out inv 1 in ok out inv 2 ok out
	 * finish ok
	 */
	private class SendThread extends Thread
	{

		private List<String> accounts = new ArrayList<>();
		private List<String> customerChangeTypes = new ArrayList<>();
		private List<String> areas = new ArrayList<>();
		private List<String> collectFroms = new ArrayList<>();
		private List<String> offers = new ArrayList<>();
		private List<String> packages = new ArrayList<>();
		private List<String> paymentTypes = new ArrayList<>();
		private List<String> customers = new ArrayList<>();
		private List<String> invoices = new ArrayList<>();
		private List<String> receipts = new ArrayList<>();
		private List<String> invoiceReceipts = new ArrayList<>();
		private List<String> customerChangeLogs = new ArrayList<>();

		private String stringRead;
		private PrintWriter printWriter;
		private BufferedInputStream bufferedInputStream;

		SendThread(
				List<String> accounts,
				List<String> customerChangeTypes,
				List<String> areas,
				List<String> collectFroms,
				List<String> offers,
				List<String> packages,
				List<String> paymentTypes,
				List<String> customers,
				List<String> invoices,
				List<String> receipts,
				List<String> invoiceReceipts,
				List<String> customerChangeLogs
		)
		{
			this.accounts = accounts;
			this.customerChangeTypes = customerChangeTypes;
			this.areas = areas;
			this.collectFroms = collectFroms;
			this.offers = offers;
			this.packages = packages;
			this.paymentTypes = paymentTypes;
			this.customers = customers;
			this.invoices = invoices;
			this.receipts = receipts;
			this.invoiceReceipts = invoiceReceipts;
			this.customerChangeLogs = customerChangeLogs;

			try
			{

				printWriter = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream())), true);
				bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void run()
		{

			send("ACCOUNTS", accounts);
			send("CUSTOMERCHANGETYPES", customerChangeTypes);
			send("AREAS", areas);
			send("COLLECTFROMS", collectFroms);
			send("OFFERS", offers);
			send("PACKAGES", packages);
			send("PAYMENTTYPES", paymentTypes);
			send("CUSTOMERS", customers);
			send("CUSTOMERCHANGELOGS", customerChangeLogs);
			send("INVOICES", invoices);
			send("RECEIPTS", receipts);
			send("INVOICERECEIPTS", invoiceReceipts);

			writeLog("DONE");
		}

		private void send(String title, List<String> datas)
		{
			writeLog("SENDING");
			printWriter.print("SENDING");
			printWriter.flush();

			writeLog("waiting for ok");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (Exception e)
			{
				return;
			}
			if (!stringRead.equals("OK"))
			{
				return;
			}
			writeLog("got ok");

			writeLog("Informing about " + title);
			printWriter.print(title);
			printWriter.flush();

			writeLog("waiting for ok");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (Exception e)
			{
				return;
			}
			if (!stringRead.equals("OK"))
			{
				return;
			}
			writeLog("got ok");

			writeLog("sending size");
			printWriter.print(datas.size());
			printWriter.flush();
			writeLog("size sent");

			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (Exception e)
			{
				return;
			}
			if (!stringRead.equals("OK"))
			{
				return;
			}

			writeLog("got ok");

			for (String data : datas)
			{
				writeLog("sending " + data);

				printWriter.print(data);
				printWriter.flush();

				try
				{
					stringRead = readInputStream(bufferedInputStream);
				}
				catch (Exception e)
				{
					return;
				}

				if (!stringRead.equals("OK"))
				{
					return;
				}
				writeLog("got ok");

			}
			writeLog("invoices finished");
		}

	}

	private class SendSMSThread extends Thread
	{

		private List<String> smsStrings = new ArrayList<>();

		private String stringRead;
		private PrintWriter printWriter;
		private BufferedInputStream bufferedInputStream;

		SendSMSThread(List<String> smsStrings)
		{
			this.smsStrings = smsStrings;

			try
			{

				printWriter = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream())), true);
				bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void run()
		{

			for (String data : smsStrings)
			{
				writeLog("SEND_SMS");
				printWriter.print("SEND_SMS");
				printWriter.flush();
				writeLog("waiting for ok");
				try
				{
					stringRead = readInputStream(bufferedInputStream);
				}
				catch (Exception e)
				{
					return;
				}
				if (!stringRead.equals("OK"))
				{
					return;
				}
				writeLog("got ok");

				writeLog("sending " + data);

				printWriter.print(data);
				printWriter.flush();

				try
				{
					stringRead = readInputStream(bufferedInputStream);
				}
				catch (Exception e)
				{
					return;
				}

				if (!stringRead.equals("OK"))
				{
					return;
				}
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException ex)
				{
					Logger.getLogger(Server_backup.class.getName()).log(Level.SEVERE, null, ex);
				}
				writeLog("got ok");

			}

			writeLog("DONE");
		}

	}

	private class ReceiveThread extends Thread
	{

		private String stringRead;
		private PrintWriter printWriter;
		private BufferedInputStream bufferedInputStream;

		ReceiveThread()
		{
			try
			{

				printWriter = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(clientSocket.getOutputStream())), true);
				bufferedInputStream = new BufferedInputStream(clientSocket.getInputStream());

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void run()
		{

			receive();

			writeLog("DONE");
		}

		private void receive()
		{
			writeLog("asking client to send SEND");
			printWriter.print("SEND");
			printWriter.flush();

			writeLog("receiving receipts");
			receiveReceipts();
			writeLog("receipts received");

			writeLog("DONE");
		}

		private void receiveReceipts()
		{
			writeLog("waiting for receipts");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (IOException e)
			{
				return;
			}

			if (!stringRead.equals("RECEIPTS"))
			{
				return;
			}
			writeLog("got receipts");

			printWriter.write("OK");
			printWriter.flush();
			writeLog("ok sent");

			try
			{
				stringRead = readInputStream(bufferedInputStream);
				if (stringRead == null)
				{
					return;
				}
			}
			catch (IOException e)
			{

				return;

			}

			writeLog("got count " + stringRead);
			int receiptCount = 0;
			try
			{
				receiptCount = Integer.parseInt(stringRead);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
			printWriter.write("OK");
			printWriter.flush();

			List<String> receiptsString = new ArrayList<>();

			for (int i = 0; i < receiptCount; i++)
			{
				writeLog("waiting for receipt");
				try
				{
					stringRead = readInputStream(bufferedInputStream);
					if (stringRead == null)
					{
						return;
					}
				}
				catch (IOException e)
				{

					return;

				}

				writeLog("receipt got " + stringRead);
				receiptsString.add(stringRead);

				printWriter.write("OK");
				printWriter.flush();
				writeLog("sent ok");
			}

			writeLog("processing receipts");
			processReceipts(receiptsString);
			writeLog("processed receipts");

			printWriter.write("SAVED");
			printWriter.flush();
		}

		public void processReceipts(List<String> receiptsString)
		{
			/*
			Gson gson = new Gson();

			Session session = Database.getSessionCompanyYear();

			ReceiptDTOPhone receiptDTOPhone;
			ReceiptSer receiptSer = new ReceiptSer(session);
			CustomerSer customerSer = new CustomerSer(session);
			AccountSer accountSer = new AccountSer(session);

			for (String receiptString : receiptsString)
			{
				receiptDTOPhone = gson.fromJson(receiptString, ReceiptDTOPhone.class);

				CustomerDTO customer = customerSer.retrieve(receiptDTOPhone.getCustomerId());

				ReceiptDTO receipt = new ReceiptDTO();
				receipt.setAccount(accountSer.get(AccountSer.enmKeys.NA));
				receipt.setAmount(receiptDTOPhone.getAmount());
				receipt.setDate(receiptDTOPhone.getDate());
				receipt.setNote(receiptDTOPhone.getNote());
				receipt.setCustomer(customer);

				receiptSer.createOrUpdate(receipt);
			}
			 */

		}

	}

	public void send(
			List<com.emsays.path.dto.AccountDTO> accounts,
			List<com.emsays.path.dto.CustomerChangeTypeDTO> customerChangeTypes,
			List<com.emsays.path.dto.AreaDTO> areas,
			List<com.emsays.path.dto.CollectFromDTO> collectFroms,
			List<com.emsays.path.dto.OfferDTO> offers,
			List<com.emsays.path.dto.PackageDTO> packages,
			List<com.emsays.path.dto.PaymentTypeDTO> paymentTypes,
			List<com.emsays.path.dto.CustomerDTO> customers,
			List<com.emsays.path.dto.InvoiceDTO> invoices,
			List<com.emsays.path.dto.ReceiptDTO> receipts,
			List<com.emsays.path.dto.InvoiceReceiptDTO> invoiceReceipts,
			List<com.emsays.path.dto.CustomerChangeLogDTO> customerChangeLogs
	)
	{
		/*if (clientSocket == null || clientSocket.isClosed())
		{
			writeLog("Client is not connected or is closed");
			return;
		}*/
 /*
		GsonBuilder b = new GsonBuilder();
		b.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = b.create();

		List<String> stringAccounts = new ArrayList<>();
		List<String> stringCustomerChangeTypes = new ArrayList<>();
		List<String> stringAreas = new ArrayList<>();
		List<String> stringCollectFroms = new ArrayList<>();
		List<String> stringOffers = new ArrayList<>();
		List<String> stringPackages = new ArrayList<>();
		List<String> stringPaymentTypes = new ArrayList<>();
		List<String> stringCustomers = new ArrayList<>();
		List<String> stringInvoices = new ArrayList<>();
		List<String> stringReceipts = new ArrayList<>();
		List<String> stringInvoiceReceipts = new ArrayList<>();
		List<String> stringCustomerChangeLogs = new ArrayList<>();

		for (AccountDTO account : accounts)
		{
			stringAccounts.add(gson.toJson(new AccountDTOPhone(account)));
		}
		for (CustomerChangeTypeDTO customerchangetype : customerChangeTypes)
		{
			stringCustomerChangeTypes.add(gson.toJson(new CustomerChangeTypeDTOPhone(customerchangetype)));
		}
		for (AreaDTO area : areas)
		{
			stringAreas.add(gson.toJson(new AreaDTOPhone(area)));
		}
		for (CollectFromDTO collectfrom : collectFroms)
		{
			stringCollectFroms.add(gson.toJson(new CollectFromDTOPhone(collectfrom)));
		}
		for (OfferDTO offer : offers)
		{
			stringOffers.add(gson.toJson(new OfferDTOPhone(offer)));
		}
		for (PackageDTO package_ : packages)
		{
			stringPackages.add(gson.toJson(new PackageDTOPhone(package_)));
		}
		for (PaymentTypeDTO paymenttype : paymentTypes)
		{
			stringPaymentTypes.add(gson.toJson(new PaymentTypeDTOPhone(paymenttype)));
		}
		for (CustomerDTO customer : customers)
		{
			stringCustomers.add(gson.toJson(new CustomerDTOPhone(customer)));
		}
		for (InvoiceDTO invoice : invoices)
		{
			stringInvoices.add(gson.toJson(new InvoiceDTOPhone(invoice)));
		}
		for (ReceiptDTO receipt : receipts)
		{
			stringReceipts.add(gson.toJson(new ReceiptDTOPhone(receipt)));
		}
		for (InvoiceReceiptDTO invoiceReceipt : invoiceReceipts)
		{
			stringInvoiceReceipts.add(gson.toJson(new InvoiceReceiptDTOPhone(invoiceReceipt)));
		}
		for (CustomerChangeLogDTO customerChangeLog : customerChangeLogs)
		{
			stringCustomerChangeLogs.add(gson.toJson(new CustomerChangeLogDTOPhone(customerChangeLog)));
		}

		SendThread socketServerReplyThread = new SendThread(
				stringAccounts,
				stringCustomerChangeTypes,
				stringAreas,
				stringCollectFroms,
				stringOffers,
				stringPackages,
				stringPaymentTypes,
				stringCustomers,
				stringInvoices,
				stringReceipts,
				stringInvoiceReceipts,
				stringCustomerChangeLogs
		);
		socketServerReplyThread.start();
		 */
	}

	public void sendSMS()//(List<SMSDTOPhone> smsDTOs)
	{
		/*
		if (clientSocket == null || clientSocket.isClosed())
		{
			writeLog("Client is not connected or is closed");
			return;
		}

		GsonBuilder b = new GsonBuilder();
		Gson gson = b.create();

		List<String> smsDTOStrings = new ArrayList<>();

		for (SMSDTOPhone smsDTO : smsDTOs)
		{
			smsDTOStrings.add(gson.toJson(smsDTO));
		}

		SendSMSThread sendSMSThread = new SendSMSThread(smsDTOStrings);
		sendSMSThread.start();
		 */
	}

	public void receive()
	{
		if (clientSocket == null || clientSocket.isClosed())
		{
			writeLog("Client is not connected or is closed");
			return;
		}

		ReceiveThread socketServerReplyThread = new ReceiveThread();
		socketServerReplyThread.start();

	}

	public String getIpAddress()
	{
		String ip = "";
		try
		{
			Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
					.getNetworkInterfaces();
			while (enumNetworkInterfaces.hasMoreElements())
			{
				NetworkInterface networkInterface = enumNetworkInterfaces
						.nextElement();
				Enumeration<InetAddress> enumInetAddress = networkInterface
						.getInetAddresses();
				while (enumInetAddress.hasMoreElements())
				{
					InetAddress inetAddress = enumInetAddress
							.nextElement();

					if (inetAddress.isSiteLocalAddress())
					{
						ip += "Server running at : "
								+ inetAddress.getHostAddress();
					}
				}
			}

		}
		catch (SocketException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip += "Something Wrong! " + e.toString() + "\n";
		}
		return ip;
	}

	private static String readInputStream(BufferedInputStream _in) throws IOException
	{
		String data = "";
		int s = _in.read();
		if (s == -1)
		{
			return null;
		}
		data += "" + (char) s;
		int len = _in.available();
		System.out.println("Len got : " + len);
		if (len > 0)
		{
			byte[] byteData = new byte[len];
			_in.read(byteData);
			data += new String(byteData);
		}
		return data;
	}
}
