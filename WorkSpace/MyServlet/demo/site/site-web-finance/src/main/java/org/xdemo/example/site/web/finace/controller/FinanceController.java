package org.xdemo.example.site.web.finace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/finance_spring")
public class FinanceController {
	
	@RequestMapping("/test")
	public ModelAndView test(ModelAndView mav){
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^");
		mav.setViewName("finance");
		return mav;
	}
	
}
