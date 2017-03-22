package com.kaamkaj.exceptions;

import javax.validation.ValidationException;

/**
 * @author: Amit Khandelwal
 * Date: 3/20/17
 */
public class EntityNotPresentException extends ValidationException {

	public EntityNotPresentException(String s) {
		super(s);
	}

	public EntityNotPresentException() {
	}

	public EntityNotPresentException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotPresentException(Throwable cause) {
		super(cause);
	}

}
