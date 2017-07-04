package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ReadingMaterial;

@Repository("readingMaterialRepository")
public interface ReadingMaterialRepository extends JpaRepository<ReadingMaterial, Long>{
	
}