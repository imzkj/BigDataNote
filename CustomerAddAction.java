package com.ibeifeng.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibeifeng.dao.CustomerDAO;
import com.ibeifeng.daofactory.CustomerDAOFactory;
import com.ibeifeng.vo.Customer;

public class CustomerAddAction extends WebAction {
	/**
	 * 执行方法
	 */
	public void execute(HttpServlet servlet,HttpServletRequest request, HttpServletResponse response) {
		//获得DAO实例
		CustomerDAO cdao = CustomerDAOFactory.getCustomerDAO();
		
		//接受参数
		String customercode = request.getParameter("customercode");
		String page = null;
		//判断客户编号是否占用
		if(cdao.findByCustomerCode(customercode) == null) {
			String customername = request.getParameter("customername");
			String phone = request.getParameter("phone");
			String address = request.getParameter("address");
			String relationman = request.getParameter("relationman");
			String other = request.getParameter("other");
			
			//封装Customer对象
			Customer customer = new Customer();
			customer.setCustomercode(customercode);
			customer.setCustomername(customername);
			customer.setPhone(phone);
			customer.setAddress(address);
			customer.setRelationman(relationman);
			customer.setOther(other);
			
			//通过DAO实例完成客户添加
			cdao.addCustomer(customer);
			//执行跳转
			page = "/CustomerAdd.jsp";
		} else {
			page = "/CustomerAdd.jsp";
			//添加错误信息
			request.setAttribute("error", "客户编号被占用");
		}
		forward(servlet, request, response, page);
	}
}
