package com.testtask.task.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import com.testtask.task.model.PersistentLogin;
import com.testtask.task.model.User;
import com.testtask.task.security.TokenCreator;
import com.testtask.task.service.PersistentLoginService;
import com.testtask.task.service.UserService;
import com.testtask.task.dto.UserDto;

@RestController
public class MainController {

	private static final Logger logger = LoggerFactory.getLogger(MainController.class);

	@Autowired
	UserService userService;

	@Autowired
	PersistentLoginService persistentLoginService;

	@RequestMapping(value = "/api/register", method = RequestMethod.POST)
	public String register(@ModelAttribute UserDto userDto) {
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getPassword(), userDto.getEmail());
		logger.info(userDto.toString());
		userService.saveUser(user);
		return "registrationsuccess";
	}

	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	public String login(@ModelAttribute UserDto userDto, final HttpServletResponse response) {
		String email = userDto.getEmail();
		if (email == null) {
			return "not authorized";
		}
		User user = userService.findByEmail(userDto.getEmail());
		if (user == null) {
			return "not authorized";
		}
		String token = TokenCreator.createToken();
		PersistentLogin persistentLogin = persistentLoginService.getPersistenloginbyUser(user);
		persistentLogin.setToken(token);
		persistentLoginService.save(persistentLogin);
		response.setHeader("Auth-Token", token);
		return "success";
	}

	@RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = { "application/json" })
	public User user(final HttpServletRequest request) {
		final String accessToken = request.getHeader("Auth-Token");
		if (accessToken == null) {
			return null;
		}
		PersistentLogin persistentLogin = persistentLoginService.getPersistentLoginByToken(accessToken);
		User user = userService.findByEmail(persistentLogin.getUsername());
		return user;
	}

	@RequestMapping(value = "/api/info", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public String info() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("\"companyName\"", "\"companyName\"");
		info.put("\"someInfo\"", "\"someInfo\"");
		return info.toString().replace("=", ":");
	}

}