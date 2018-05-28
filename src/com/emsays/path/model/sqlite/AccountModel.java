package com.emsays.path.model.sqlite;

import com.emsays.path.dto.AccountDTO;
import com.hm.miniorm.Column;
import com.hm.miniorm.Table;

@Table(name = "account")
public class AccountModel
{

	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "note")
	private String note;

	public AccountModel()
	{
	}

	public AccountModel(AccountDTO account)
	{
		this.id = account.getId();
		this.name = account.getName();
		this.note = account.getNote();
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
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

		AccountModel that = (AccountModel) o;

		if (id != that.id)
		{
			return false;
		}
		if (!name.equals(that.name))
		{
			return false;
		}
		return note != null ? note.equals(that.note) : that.note == null;
	}

	@Override
	public int hashCode()
	{
		int result = id;
		result = 31 * result + name.hashCode();
		result = 31 * result + (note != null ? note.hashCode() : 0);
		return result;
	}
}
