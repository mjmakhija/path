package com.emsays.path;

import com.emsays.path.dao.AccountSer;
import com.emsays.path.dao.AreaSer;
import com.emsays.path.dao.CollectFromSer;
import com.emsays.path.dao.CustomerSer;
import com.emsays.path.dao.InvoiceSer;
import com.emsays.path.dao.OfferSer;
import com.emsays.path.dao.PackageSer;
import com.emsays.path.dao.PaymentTypeSer;
import com.emsays.path.dao.ReceiptSer;
import com.emsays.path.dao.sqlite.AccountDAO;
import com.emsays.path.dao.sqlite.AreaDAO;
import com.emsays.path.dao.sqlite.CollectFromDAO;
import com.emsays.path.dao.sqlite.CustomerDAO;
import com.emsays.path.dao.sqlite.InvoiceDAO;
import com.emsays.path.dao.sqlite.OfferDAO;
import com.emsays.path.dao.sqlite.PackageDAO;
import com.emsays.path.dao.sqlite.PaymentTypeDAO;
import com.emsays.path.dao.sqlite.ReceiptDAO;
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
import com.emsays.path.model.sqlite.AccountModel;
import com.emsays.path.model.sqlite.AreaModel;
import com.emsays.path.model.sqlite.CollectFromModel;
import com.emsays.path.model.sqlite.CustomerModel;
import com.emsays.path.model.sqlite.InvoiceModel;
import com.emsays.path.model.sqlite.OfferModel;
import com.emsays.path.model.sqlite.PackageModel;
import com.emsays.path.model.sqlite.PaymentTypeModel;
import com.emsays.path.model.sqlite.ReceiptModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;

public class Server
{

	LogWriter lw = null;
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	String message = "";
	final int socketServerPORT = 5792;
	int count = 0;

	public Server()
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

	private class SendThread extends Thread
	{

		private String stringRead;
		private PrintWriter printWriter;
		private BufferedInputStream bufferedInputStream;

		SendThread()
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

			writeLog("asking client to send SEND");
			printWriter.print("SEND");
			printWriter.flush();

			writeLog("waiting for filesize");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (IOException e)
			{
				return;
			}
			
			writeLog("sending ok");
			printWriter.print("OK");
			printWriter.flush();

			writeLog("receiving file");
			try
			{
				receiveFile(clientSocket, Integer.parseInt(stringRead));
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}

			DBHelperSqlite dBHelperSqlite = new DBHelperSqlite("C:/sqlite/test.db");
			dBHelperSqlite.createConnection();

			recieveReceiptsFromSqlite(dBHelperSqlite.getConnection());
			transferDataToSqlite(GV.SQLITE_PATH);

			writeLog("sending file size");
			printWriter.print(new File(GV.SQLITE_PATH).length());
			printWriter.flush();

			writeLog("waiting for OK");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (IOException e)
			{
				return;
			}

			try
			{
				sendFile(clientSocket, GV.SQLITE_PATH);
			}
			catch (IOException ex)
			{
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}

			//receiveReceipts();
			writeLog("receipts received");

			writeLog("DONE");

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
					Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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

			writeLog("waiting for filesize");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (IOException e)
			{
				return;
			}

			writeLog("writing ok");
			printWriter.print("OK");
			printWriter.flush();

			writeLog("receiving file");
			try
			{
				receiveFile(clientSocket, Integer.parseInt(stringRead));
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}

			DBHelperSqlite dBHelperSqlite = new DBHelperSqlite(GV.SQLITE_PATH);
			dBHelperSqlite.createConnection();

			recieveReceiptsFromSqlite(dBHelperSqlite.getConnection());

			writeLog("sending file size");
			printWriter.print(new File(GV.SQLITE_PATH).length());
			printWriter.flush();

			writeLog("waiting for OK");
			try
			{
				stringRead = readInputStream(bufferedInputStream);
			}
			catch (IOException e)
			{
				return;
			}

			try
			{
				sendFile(clientSocket, GV.SQLITE_PATH);
			}
			catch (IOException ex)
			{
				Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
			}

			//receiveReceipts();
			writeLog("receipts received");

			writeLog("DONE");
		}

	}

	public void send()
	{
		if (clientSocket == null || clientSocket.isClosed())
		{
			writeLog("Client is not connected or is closed");
			return;
		}
		new SendThread().start();
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

	private void receiveFile(Socket clientSock, int file_size) throws IOException
	{
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream(GV.SQLITE_PATH);
		byte[] buffer = new byte[4096];

		int filesize = file_size; // Send file size in separate msg
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0)
		{
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}

		//fos.close();
		//dis.close();
	}

	public void sendFile(Socket s, String file) throws IOException
	{
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[4096];

		while (fis.read(buffer) > 0)
		{
			dos.write(buffer);
		}

		//fis.close();
		//dos.close();
	}

	private void recieveReceiptsFromSqlite(Connection c)
	{
		List<ReceiptModel> receipts = new ReceiptDAO(c).getNotSyncd();

		Session session = Database.getSessionCompanyYear();

		ReceiptSer receiptSer = new ReceiptSer(session);
		CustomerSer customerSer = new CustomerSer(session);
		AccountSer accountSer = new AccountSer(session);

		for (ReceiptModel receipt : receipts)
		{

			CustomerDTO customer = customerSer.retrieve(receipt.getCustomerId());

			ReceiptDTO receiptForMysql = new ReceiptDTO();
			receiptForMysql.setAccount(accountSer.get(AccountSer.enmKeys.NA));
			receiptForMysql.setAmount(receipt.getAmount());
			receiptForMysql.setDate(receipt.getDate());
			receiptForMysql.setNote(receipt.getNote());
			receiptForMysql.setCustomer(customer);

			receiptSer.createOrUpdate(receiptForMysql);
		}

		new ReceiptDAO(c).markAllSyncd();
	}

	private void transferDataToSqlite(String dbPath)
	{
		Session session = Database.getSessionCompanyYear();

		List<AccountDTO> accounts = new AccountSer(session).get();
		List<AreaDTO> areas = new AreaSer(session).retrieve();
		List<CollectFromDTO> collectFroms = new CollectFromSer(session).retrieve();
		List<OfferDTO> offers = new OfferSer(session).retrieve();
		List<PackageDTO> packages = new PackageSer(session).retrieve();
		List<PaymentTypeDTO> paymentTypes = new PaymentTypeSer(session).retrieve();
		List<CustomerDTO> customers = new CustomerSer(session).retrieve();
		List<InvoiceDTO> invoices = new InvoiceSer(session).retrieve();
		List<ReceiptDTO> receipts = new ReceiptSer(session).retrieve();

		List<AccountModel> stringAccounts = new ArrayList<>();
		List<AreaModel> stringAreas = new ArrayList<>();
		List<CollectFromModel> stringCollectFroms = new ArrayList<>();
		List<OfferModel> stringOffers = new ArrayList<>();
		List<PackageModel> stringPackages = new ArrayList<>();
		List<PaymentTypeModel> stringPaymentTypes = new ArrayList<>();
		List<CustomerModel> stringCustomers = new ArrayList<>();
		List<InvoiceModel> stringInvoices = new ArrayList<>();
		List<ReceiptModel> stringReceipts = new ArrayList<>();

		for (AccountDTO account : accounts)
		{
			stringAccounts.add(new AccountModel(account));
		}
		for (AreaDTO area : areas)
		{
			stringAreas.add(new AreaModel(area));
		}
		for (CollectFromDTO collectfrom : collectFroms)
		{
			stringCollectFroms.add(new CollectFromModel(collectfrom));
		}
		for (OfferDTO offer : offers)
		{
			stringOffers.add(new OfferModel(offer));
		}
		for (PackageDTO package_ : packages)
		{
			stringPackages.add(new PackageModel(package_));
		}
		for (PaymentTypeDTO paymenttype : paymentTypes)
		{
			stringPaymentTypes.add(new PaymentTypeModel(paymenttype));
		}
		for (CustomerDTO customer : customers)
		{
			stringCustomers.add(new CustomerModel(customer));
		}
		for (InvoiceDTO invoice : invoices)
		{
			stringInvoices.add(new InvoiceModel(invoice));
		}
		for (ReceiptDTO receipt : receipts)
		{
			stringReceipts.add(new ReceiptModel(receipt));
		}

		DBHelperSqlite dBHelperSqlite = new DBHelperSqlite(dbPath);
		dBHelperSqlite.createConnection();
		new AccountDAO(dBHelperSqlite.getConnection()).create(stringAccounts);
		new AreaDAO(dBHelperSqlite.getConnection()).create(stringAreas);
		new CollectFromDAO(dBHelperSqlite.getConnection()).create(stringCollectFroms);
		new CustomerDAO(dBHelperSqlite.getConnection()).create(stringCustomers);
		new InvoiceDAO(dBHelperSqlite.getConnection()).create(stringInvoices);
		new OfferDAO(dBHelperSqlite.getConnection()).create(stringOffers);
		new PackageDAO(dBHelperSqlite.getConnection()).create(stringPackages);
		new PaymentTypeDAO(dBHelperSqlite.getConnection()).create(stringPaymentTypes);
		new ReceiptDAO(dBHelperSqlite.getConnection()).create(stringReceipts);

	}
}
