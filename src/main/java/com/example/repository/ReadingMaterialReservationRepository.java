package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ReadingMaterialReservation;

@Repository("readingMaterialReservationRepository")
public interface ReadingMaterialReservationRepository extends JpaRepository<ReadingMaterialReservation, Long>{
	
}
