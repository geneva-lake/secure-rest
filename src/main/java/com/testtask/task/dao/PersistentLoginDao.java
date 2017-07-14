package com.testtask.task.dao;

import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;

public interface PersistentLoginDao {
	public PersistentLogin getPersistenloginbyUser(User user);	
	public void save(PersistentLogin persistentLogin);
	PersistentLogin getPersistentLoginByToken(String token);
}