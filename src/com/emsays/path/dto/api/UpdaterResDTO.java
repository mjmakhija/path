package com.emsays.path.dto.api;

public class UpdaterResDTO
{

	private boolean done;
	private String code;
	private String msg;
	private UpdaterDataDTO data;

	public boolean isDone()
	{
		return done;
	}

	public String getCode()
	{
		return code;
	}

	public String getMsg()
	{
		return msg;
	}

	public UpdaterDataDTO getData()
	{
		return data;
	}

	@Override
	public String toString()
	{
		return "UpdaterResDTO{" + "done=" + done + ", code=" + code + ", msg=" + msg + ", data=" + data + '}';
	}

}
