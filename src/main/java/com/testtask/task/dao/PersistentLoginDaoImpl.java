package com.testtask.task.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;

@Repository("persistentLoginDao")
public class PersistentLoginDaoImpl extends AbstractDao<Integer, PersistentLogin> implements PersistentLoginDao {
	public PersistentLogin getPersistenloginbyUser(User user){
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", user.getEmail()));
		PersistentLogin persistentLogin = (PersistentLogin)crit.uniqueResult();
		if (persistentLogin==null) {
			PersistentLogin persistentLoginCreated = new PersistentLogin();
			persistentLoginCreated.setUsername(user.getEmail());
			save(persistentLoginCreated);
			return persistentLoginCreated;
		}
		return persistentLogin;
	}
	
	public PersistentLogin getPersistentLoginByToken(String token){
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("token", token));
		PersistentLogin persistentLogin = (PersistentLogin)crit.uniqueResult();
		return persistentLogin;
	}
	
	public void save(PersistentLogin persistentLogin) {
		persist(persistentLogin);
	}
}