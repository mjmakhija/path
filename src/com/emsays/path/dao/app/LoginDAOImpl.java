package com.emsays.path.dao.app;

import com.emsays.path.dto.app.LoginDTO;
import com.emsays.path.dto.app.LoginDTO_;
import java.util.List;
import javax.persistence.criteria.*;

public class LoginDAOImpl extends ParentDAO implements LoginDAO
{

	@Override
	public LoginDTO findByUsername(String username)
	{
		this.mOpenSession();

		CriteriaBuilder builder = vSession.getCriteriaBuilder();

		CriteriaQuery<LoginDTO> criteria = builder.createQuery(LoginDTO.class);
		Root<LoginDTO> root = criteria.from(LoginDTO.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get(LoginDTO_.username), username));

		List<LoginDTO> logins = vSession.createQuery(criteria).getResultList();

		this.mCloseSession();

		return logins.get(0); 
	}

}
