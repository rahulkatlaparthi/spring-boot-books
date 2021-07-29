package com.example.demo.controller;



import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exeception.BaseResponse;
import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;





@RestController
public class UserController {
	
	@Autowired
	private UserRepository res;
	
	
	boolean IsSuceess;
	
	@PostMapping("/register")
	public BaseResponse createRegister( @RequestBody Users users)  {
		Map<String, Object> response = new HashMap<>();
		
		
		List<Users> usd=new ArrayList();
		usd=res.findAll();
//		
		
		
		if(!(users.getPassword().equals(users.getConfirmpassword()))) {
			return new BaseResponse(false,"Please Check Password and Confirm Password",null);
		}  
		if(usd.size()>0) {	
			Users us=res.findByEmail(users.getEmail());
		     System.out.println(us);
		   if(us!=null) {
			   if(users.getEmail().equals(us.getEmail())) {
					return new BaseResponse(false,"Please give new email",null);
				   }  }
		   }
		
		
		   res.save(users);
		   
//		   response.put("value", users.getId());
		 return new BaseResponse(true,"Success", users.getId());
		
		
			
		}
		
		
		
		 
//		 return new ResponseEntity<String>("Success",HttpStatus.OK);
		 
		
	
	@GetMapping("/users")
	public BaseResponse  getUser(){
		List<Users> usd=new ArrayList();
		usd= res.findAll();
		 return new BaseResponse(true,"Success",usd);
	}
	@GetMapping("/user/email/{email}")
	public Users getMail(@PathVariable("email") String email) {
		return res.findByEmail(email);
		
	}
	@PostMapping("/login")
	public BaseResponse createLogin( @RequestBody Users use) {
		Map<String, Object> response = new HashMap<>();
		
	Users us=res.findByEmail(use.getEmail());
	
		if(us!=null) {
			if(us.getPassword().equals(use.getPassword())) {
//				response.put("value", us.getId());
				return new BaseResponse(true,"Success", us.getId());
			}
			
			
		}
	
		return new BaseResponse(false,"Please check Email and password",null);
	}
	
	@PostMapping("/dummy")
	public BaseResponse dummyPoint(@RequestBody Users use) {
		Map<String, Object> response = new HashMap<>();
		
	Users us=res.findByEmail(use.getEmail());
	
		if(us!=null) {
			if(us.getPassword().equals(use.getPassword())) {
//				response.put("value", us.getId());
				return new BaseResponse(true,"Success",us.getId());
			}
			
			
		}
	
		return new BaseResponse(false,"Please check Email and password",null);
	}
	
	@GetMapping("/getUser/{id}")
	public Optional<Users> getUser(@PathVariable int id) {
		return res.findById(id);
	}
	
	
	

}

