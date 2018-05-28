package com.emsays.path.dto.app;

import java.util.Date;
import javax.persistence.*;

@Entity(name = "update_log")
public class UpdateLogDTO
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String version;

	@Column(name = "status_id")
	private int statusId;

	private String path;

	@Column(name = "created_at", insertable = false)
	private Date createdAt;

	@Column(name = "updated_at", insertable = false)
	private Date updatedAt;

	public int getId()
	{
		return id;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public int getStatusId()
	{
		return statusId;
	}

	public void setStatusId(int statusId)
	{
		this.statusId = statusId;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public Date getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt()
	{
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}

}
