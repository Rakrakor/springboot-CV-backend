package com.samrak.CV.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException{

	public UserAlreadyExistsException(String errorMsg) {
			
			super(errorMsg);
		}
}
