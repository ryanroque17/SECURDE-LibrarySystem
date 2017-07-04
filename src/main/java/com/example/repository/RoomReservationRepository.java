package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.RoomReservation;

@Repository("roomMaterialReservationRepository")
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>{
	
}

