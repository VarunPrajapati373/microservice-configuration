package com.service.hotel.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.hotel.entities.Hotel;
import com.service.hotel.exceptions.ResourceNotFoundException;
import com.service.hotel.repositories.HotelRepo;
import com.service.hotel.services.HotelService;


@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	private HotelRepo hotelRepo;
	
	@Override
	public Hotel create(Hotel hotel) {
		// TODO Auto-generated method stub
		String hotelId = UUID.randomUUID().toString();
		hotel.setId(hotelId);
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		// TODO Auto-generated method stub
		return hotelRepo.findAll();
	}

	@Override
	public Hotel getSingleHotel(String id) {
		// TODO Auto-generated method stub
		return hotelRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("hotel with given id not found"));
	}

}
