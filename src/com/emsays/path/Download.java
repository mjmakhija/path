package com.emsays.path;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class Download
{

	public static boolean Download(String vUrl, String vSavePath)
	{
		boolean res = false;

		try
		{

			downloadUsingNIO(vUrl, vSavePath);
			res = true;

			//downloadUsingStream(url, System.getProperty("user.dir") + "/abc.jpg");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return res;
	}

	private static void downloadUsingStream(String urlStr, String file) throws IOException
	{
		URL url = new URL(urlStr);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		FileOutputStream fis = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1)
		{
			fis.write(buffer, 0, count);
		}
		fis.close();
		bis.close();
	}

	private static void downloadUsingNIO(String urlStr, String file) throws IOException
	{
		URL url = new URL(urlStr);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fos = new FileOutputStream(file);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
		rbc.close();
	}
}
