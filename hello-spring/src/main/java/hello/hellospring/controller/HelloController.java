package hello.hellospring.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@GetMapping("hello")
	public String hello(Model model) {
		model.addAttribute("data", "hello!!");
		model.addAttribute("random", new Random().nextInt());
		return "hello";
	}
	
	@GetMapping("hello-mvc")
	public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
		model.addAttribute("name", name);
		return "hello-template";
	}
	
	@GetMapping("hello-string")
	@ResponseBody
	public String helloString(@RequestParam("name") String name) {
		return "hello " + name;
	}
	
	@GetMapping("hello-api")
	@ResponseBody
	public Hello helloApi(@RequestParam("name") String name) {
		Hello hello = new Hello();
		hello.setName(name);
		return hello;
	}
	
	@GetMapping("hello-api2")
	@ResponseBody
	public Map<String, Object> helloApi2(@RequestParam(value = "name", required = false) String name) {
		Map<String, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("test", null);
		return map;
	}
	
	static class Hello {
		private String name;
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
	}
}
