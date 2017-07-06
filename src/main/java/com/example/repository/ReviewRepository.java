package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;

@Repository("reviewRepository")
public interface ReviewRepository extends JpaRepository<Review, Long>{
	
}
