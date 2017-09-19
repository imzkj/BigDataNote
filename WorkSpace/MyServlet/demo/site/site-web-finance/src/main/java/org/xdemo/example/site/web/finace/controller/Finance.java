package org.xdemo.example.site.web.finace.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xdemo.example.site.web.user.Service.HelloService;
import org.xdemo.example.site.web.user.Service.HelloServiceImpl;

@WebServlet(name="finance",urlPatterns="/finance")
public class Finance extends HttpServlet{

	private static final long serialVersionUID = 6474427272497004622L;
	
	private HelloService service;
	
	

	@Override
	public void init() throws ServletException {
		service=new HelloServiceImpl();
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		service.sayHello(req.getParameter("p"));
	}
	
	

}
