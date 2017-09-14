package com.demo1.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {
	
	@RequestMapping("/test")
	public String test(){
		
		return "demo";
	}
}

