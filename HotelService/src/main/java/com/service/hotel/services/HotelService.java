package com.service.hotel.services;

import java.util.List;

import com.service.hotel.entities.Hotel;

public interface HotelService {

	//create
	
	Hotel create(Hotel hotel);
	
	//get all
	
	List<Hotel> getAll();
	
	//get single
	
	Hotel getSingleHotel(String id);
	
}
