package com.testtask.task.security;

import java.security.SecureRandom;


public class TokenCreator {
	
	public static String createToken() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		String token = bytes.toString();
		return token;
	}
}