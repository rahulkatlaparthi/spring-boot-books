package com.example.demo.exeception;




import java.util.Date;

public class BaseResponse <T> {

  private boolean isSuccess;
  private String message;
  private T returnValue;
public BaseResponse(boolean isSuccess, String message, T returnValue) {
	super();
	this.isSuccess = isSuccess;
	this.message = message;
	this.returnValue = returnValue;
}
public boolean isSuccess() {
	return isSuccess;
}
public void setSuccess(boolean isSuccess) {
	this.isSuccess = isSuccess;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public T getReturnValue() {
	return returnValue;
}
public void setReturnValue(T returnValue) {
	this.returnValue = returnValue;
}

  




 
}
