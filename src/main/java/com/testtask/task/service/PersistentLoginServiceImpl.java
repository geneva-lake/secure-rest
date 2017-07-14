package com.testtask.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.testtask.task.dao.PersistentLoginDao;
import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;

@Service("PersistentLoginService")
@Transactional
public class PersistentLoginServiceImpl implements PersistentLoginService {
	
    @Autowired
    private PersistentLoginDao dao;
    
	public PersistentLogin getPersistenloginbyUser(User user) {
		return dao.getPersistenloginbyUser(user);
	}
	
	public PersistentLogin getPersistentLoginByToken(String token) {
		return dao.getPersistentLoginByToken(token);
	}

	public void save(PersistentLogin persistentLogin) {
		dao.save(persistentLogin);
	}
}