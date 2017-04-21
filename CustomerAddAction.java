package com.fred.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fred.action.CustomerAddAction;
import com.fred.action.WebAction;

/**
 * Servlet implementation class WebController
 */
@WebServlet("/action")
public class WebController extends HttpServlet {
	private Map map;
	
	public WebController() {
		map = new HashMap();
		map.put("CustomerAdd", CustomerAddAction.class);
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//接受action参数
		String action = request.getParameter("action");
		//判断是否为空，是否为null
		if(action != null && !"".equals(action)) {
			//取得相应的业务控制器类
			Class actionClass = (Class) map.get(action);
			//获得该类的实例，并进行强制类型转换
			try {
				WebAction webAction = (WebAction)(actionClass.newInstance());
				webAction.execute(this,request,response);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}
}
