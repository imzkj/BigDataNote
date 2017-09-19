package org.xdemo.example.site.web.user.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="hello",urlPatterns="/hello")
public class Hello extends HttpServlet{
	
	private static final long serialVersionUID = -4248672330788996271L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println(req.getParameter("p"));
		req.getRequestDispatcher("views/user/user.jsp").forward(req, resp);
	}
	
}
