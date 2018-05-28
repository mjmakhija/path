package com.emsays.path;

import java.util.List;

public class Validation
{

	private boolean isValid;
	private List<String> errorMessages;

	public boolean isIsValid()
	{
		return isValid;
	}

	public void setIsValid(boolean isValid)
	{
		this.isValid = isValid;
	}

	public List<String> getErrorMessages()
	{
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages)
	{
		this.errorMessages = errorMessages;
	}

}
