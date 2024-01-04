package com.gianluca.exception;

import java.time.LocalDateTime;
import java.util.Objects;

public class ApiError {

	private String path;
	private String message;
	private Integer statusCode;
	private LocalDateTime localDateTime;

	public ApiError() {
		super();
	}

	public ApiError(String path, String message, Integer statusCode, LocalDateTime localDateTime) {
		super();
		this.path = path;
		this.message = message;
		this.statusCode = statusCode;
		this.localDateTime = localDateTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(localDateTime, message, path, statusCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApiError other = (ApiError) obj;
		return Objects.equals(localDateTime, other.localDateTime) && Objects.equals(message, other.message)
				&& Objects.equals(path, other.path) && Objects.equals(statusCode, other.statusCode);
	}

	@Override
	public String toString() {
		return "ApiError [path=" + path + ", message=" + message + ", statusCode=" + statusCode + ", localDateTime="
				+ localDateTime + "]";
	}

}
