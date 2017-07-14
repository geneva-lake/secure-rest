package com.testtask.task.service;

import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;


public interface PersistentLoginService {

	PersistentLogin getPersistenloginbyUser(User user);
	
	PersistentLogin getPersistentLoginByToken(String token);

	void save(PersistentLogin persistentLogin);

}