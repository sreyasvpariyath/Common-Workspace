package com.sreyasvpariyath.webservices.restfulwebservicesboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sreyasvpariyath.webservices.restfulwebservicesboot.dto.SampleBean;

@RestController
@RequestMapping("demo/rest")
public class Controller {

	@GetMapping
	public String hello()
	{
		return "Hello";
	}
	
	//Return a bean
	@GetMapping("/bean")
	public SampleBean helloBean()
	{
		return new SampleBean("Text from Bean");
	}
	
	@GetMapping("/bean/{name}")
	public SampleBean helloBean(@PathVariable String name)
	{
		return new SampleBean(name);
	}
	
}
