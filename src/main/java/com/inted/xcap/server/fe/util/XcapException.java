package com.inted.xcap.server.fe.util;

public class XcapException extends Exception {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;

	public XcapException() {
		super();
	}

	public XcapException(String errorMessage, int errorCode) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public XcapException(String message) {
		super(message);
	}

	public XcapException(String message, Throwable t) {
		super(message, t);
	}

	public XcapException(Throwable t) {
		super(t);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
