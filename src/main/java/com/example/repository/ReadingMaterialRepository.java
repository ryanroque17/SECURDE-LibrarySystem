package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.ReadingMaterial;

@Repository("readingMaterialRepository")
public interface ReadingMaterialRepository extends CrudRepository<ReadingMaterial, Long>{
	ReadingMaterial findByReadingMaterialId(int id);
}