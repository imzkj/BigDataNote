package com.ibeifeng.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WebAction {
	
	/**
	 * 执行方法
	 */
	public abstract void execute(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 跳转方法
	 */
	public void forward(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response,String page){
		//获得ServletContext
		ServletContext sc = servlet.getServletContext();
		//获得RequestDispatcher，并指定跳转页面
		RequestDispatcher rdispatcher = sc.getRequestDispatcher(page);
		//执行跳转
		try {
			rdispatcher.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
