package com.example.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.RoomReservation;

@Repository("roomReservationRepository")
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long>{
	@Override
	ArrayList<RoomReservation> findAll();
}