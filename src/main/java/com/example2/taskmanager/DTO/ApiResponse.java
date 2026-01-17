package com.example2.taskmanager.DTO;

import java.time.LocalDateTime;

public class ApiResponse<T>{
	
	private int status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
    
    public ApiResponse(int status, String message, LocalDateTime timestamp, T data) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }

	public int getStatus() {
		return status;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public T getData() {
		return data;
	}
}