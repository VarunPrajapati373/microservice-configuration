package com.service.hotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.hotel.entities.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, String> {

	
}
