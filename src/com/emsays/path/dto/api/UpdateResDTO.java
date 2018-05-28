package com.emsays.path.dto.api;

import java.util.ArrayList;
import java.util.List;

public class UpdateResDTO
{

	private boolean done;
	private String code;
	private String msg;
	private List<UpdateDataDTO> data = new ArrayList<>();

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

	public List<UpdateDataDTO> getData()
	{
		return data;
	}

	@Override
	public String toString()
	{
		return "RegisterResponseModel{" + "done=" + done + ", code=" + code + ", msg=" + msg + '}';
	}

}
