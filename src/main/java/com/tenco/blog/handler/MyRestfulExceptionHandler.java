package com.tenco.blog.handler;

import org.springframework.core.annotation.Order;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.blog.handler.exception.CustomPageException;
import com.tenco.blog.handler.exception.CustomRestfulException;


@Order(1)
@RestControllerAdvice
public class MyRestfulExceptionHandler {
	
	// 모든 예외 클래스 설정 
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("--------------");
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
		System.out.println("--------------");
	}
	
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + e.getMessage() + "');");
		sb.append("window.history.back();");	// 뒤로가기
		sb.append("</script>");
		return sb.toString();
	}
	
	@ExceptionHandler(CustomPageException.class) 
	public String page(CustomPageException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('" + e.getMessage() + "');");
		sb.append("location.href='/board/saveForm'");	// 만들러 가기
		sb.append("</script>");
		return sb.toString();
	}
	
}