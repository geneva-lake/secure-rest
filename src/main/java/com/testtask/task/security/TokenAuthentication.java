package com.testtask.task.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import com.testtask.task.service.PersistentLoginService;

public class TokenAuthentication extends AbstractAuthenticationToken {

	@Autowired
	PersistentLoginService persistentLoginService;
 
    private final String token;
 
    public TokenAuthentication(String token) {
        super(null);
        this.token = token;
    }
 
	public TokenAuthentication(String token, Collection authorities) {
        super(authorities);
        this.token = token;
    }
 
    @Override
    public Object getCredentials() {
        return token;
    }
 
    @Override
    public Object getPrincipal() {
        return token;
    }
 
}