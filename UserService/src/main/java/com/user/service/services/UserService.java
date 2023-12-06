package com.user.service.services;

import java.util.List;

import com.user.service.entities.User;

public interface UserService {
	
	//USERS operations
	
	//create
	User saveUser(User user);
	
	//get all user
	List<User> getAllUser();
	
	//get single user of given userId
	User getUser(String userId);
	

}
