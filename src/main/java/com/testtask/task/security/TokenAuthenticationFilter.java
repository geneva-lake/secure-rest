package com.testtask.task.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;
import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;
import com.testtask.task.service.PersistentLoginService;
import com.testtask.task.service.UserService;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	private final AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	PersistentLoginService persistentLoginService;

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponce = (HttpServletResponse) response;

		// extract token from header
		final String accessToken = httpRequest.getHeader("Auth-Token");
		if (accessToken != null) {
			PersistentLogin persistentLogin = persistentLoginService.getPersistentLoginByToken(accessToken);
			if (persistentLogin == null) {
				return;
			}
			User user = userService.findByEmail(persistentLogin.getUsername());
			if (user != null) {
				Authentication authentication = authenticationManager
						.authenticate(new TokenAuthentication(accessToken));
				SecurityContextHolder.getContext().setAuthentication(authentication);

				String token = TokenCreator.createToken();
				persistentLogin.setToken(token);
				persistentLoginService.save(persistentLogin);
				httpResponce.setHeader("Auth-Token", token);
				logger.info("Auth-Token ", token);

				chain.doFilter(request, response);
			} else {
				logger.info("Auth Filter not token");
			}
		}
	}

}