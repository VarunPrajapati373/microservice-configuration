package com.service.ratings.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.service.ratings.entities.Rating;

@Service
public interface RatingService {

	//create
	Rating create(Rating rating);
	
	//get all ratings
	List<Rating> getRatings();
	
	//get specific rating by userId 
	List<Rating> getRatingByUserId(String userId);
	
	//get specific rating by hotelId
	List<Rating> getRatingByHotelId(String hotelId);
}
