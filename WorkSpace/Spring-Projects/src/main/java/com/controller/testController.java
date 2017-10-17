package com.controller;

import com.model.testModel;
import com.service.service;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.model.testplace;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller("zkj")
@RequestMapping("/")
public class testController {

    @Resource
    private service serviceImpl;

    @Resource
    private testplace testplace;

    //@RequestMapping(value = "testModel", method = RequestMethod.GET)
    @RequestMapping(value = "testModel")
    public ModelAndView submitTask(@Valid @ModelAttribute testModel model, BindingResult bingResult) {

        serviceImpl.send();
        ModelAndView modelAndView = new ModelAndView();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        modelAndView.setViewName("/index.jsp");
        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        modelAndView.setView(mappingJackson2JsonView);
        return modelAndView.addObject("请求URL", requestURI).addObject("请求人",
                "zkj");
    }

    @RequestMapping("jsp")
    public String testString() {
        serviceImpl.send();
        System.out.println(testplace);
        return "/web/test.jsp";
    }
}
