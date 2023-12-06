package com.service.ratings.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.service.ratings.entities.Rating;

public interface RatingRepository extends MongoRepository<Rating, String> {

	//custom finder method
	List<Rating> findByUserId(String userId);
	List<Rating> findByHotelId(String hotelId);
	
}
