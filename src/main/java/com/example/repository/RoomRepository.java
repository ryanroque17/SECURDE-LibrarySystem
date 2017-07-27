package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Room;

@Repository("roomRepository")
public interface RoomRepository extends JpaRepository<Room, Long>{
	@Override
	ArrayList<Room> findAll();
}
