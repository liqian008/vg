package com.example.demo.controller;

import com.example.demo.service.IGenerateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bruce
 */
@Slf4j
@RestController
public class TestController {

	@Autowired
	private IGenerateService generateService;

	@GetMapping("/hello")
	public String hello(String name){
		return "hello " + name;
	}


}
