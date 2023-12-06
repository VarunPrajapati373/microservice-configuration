package com.user.service.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.external.services.HotelService;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;

@Service
public class UserSeviceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestTemplate restTemplate; 
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserSeviceImpl.class);
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		//generating unique user id
		String randomUserId=UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return this.userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return this.userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		
		//get user from database with the help of User Repository
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID not found on server!!"));
		
		//fetch rating of the above user from RATING SERVICE
		Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
		logger.info("{} ",ratingOfUser);
		
		List<Rating> ratings = Arrays.stream(ratingOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
			//api call to hotel service to get  the hotel
			
			//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			
			//for getting more info from http we use getForEntity
			//logger.info("Response Status code: {}",forEntity.getStatusCode());
			
			//set the hotel to rating
			rating.setHotel(hotel);
			
			//return the rating
			return rating;
			
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		
		return user;
	}

}
