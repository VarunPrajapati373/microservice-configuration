package com.user.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.service.entities.User;
import com.user.service.entities.User.UserBuilder;
import com.user.service.services.UserService;

import feign.Logger;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//create
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user){
		
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
		
	}
	
	int retryCount =1;
	
	//single user get
	@GetMapping("/{userId}")
//	@CircuitBreaker(name= "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//	@Retry(name="ratingHotelService", fallbackMethod ="ratingHotelFallback")
	@RateLimiter(name="userRateLimiter", fallbackMethod ="ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId){
		
		User user = userService.getUser(userId);
		
		System.out.println("Retry count: "+retryCount);
		retryCount++;
		
		return ResponseEntity.ok(user);
	}
	
	//creating fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		
		System.out.println("Fallback Method is executed because service is down : " + ex.getMessage());
		User user = User.builder().email("dummy@gmail.com").name("Dummy").about("This user is created dummy because some services are down").userId("123452").build();
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}
	
	//all user get
	@GetMapping
	public ResponseEntity<List<User>> getAllUser(){
		List<User> allUser= userService.getAllUser();
		return ResponseEntity.ok(allUser);
	}
}
