package com.service.serviceImpl;

import com.service.service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("serviceImpl")
public class serviceImpl implements service {
    public void send() {
        System.out.println("serviceImpl");
    }
}
