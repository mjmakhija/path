package com.emsays.path.dao.app;

import com.emsays.path.dto.app.LoginDTO;

public interface LoginDAO
{

	//public void saveUser(User user);
	public LoginDTO findByUsername(String username);
}
