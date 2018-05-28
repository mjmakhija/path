package com.emsays.path.dto.api;

public class RegisterResDTO
{

	private boolean done;
	private String code;
	private String msg;

	public boolean isDone()
	{
		return done;
	}

	public void setDone(boolean done)
	{
		this.done = done;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	@Override
	public String toString()
	{
		return "RegisterResponseModel{" + "done=" + done + ", code=" + code + ", msg=" + msg + '}';
	}

}
