package com.jwtDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwtDemo.JWT.JwtUtils;
import com.jwtDemo.Service.IUserService;
import com.jwtDemo.bindings.UserRequest;
import com.jwtDemo.bindings.UserResponse;
import com.jwtDemo.entities.User;

@RestController
@RequestMapping(path = "/user")
public class UserRestController {

	@Autowired
	private IUserService service;
	
	@Autowired
	private  JwtUtils utils;
	//1. save User data in database
	@PostMapping("/save")
	public ResponseEntity<String> saveUser(@RequestBody User user) {
		Integer id = service.saveUser(user);
		String body = "User "+id+" saved";
		return new ResponseEntity<String>(body,HttpStatus.OK);
		//short form
		//return ResponseEntity.ok(body);
	}
	
	//2. Validate user and Generate Token (Login)
	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest request){
		  String token = utils.generateToken(request.getUsername());
		  
		  return ResponseEntity.ok(new UserResponse(token,"Success! Generated By shrihari"));
	}
	
}
