package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.repository.ReadingMaterialRepository;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ReadingMaterialRepository readingMaterialRepository;
	
	@Override
	public void saveReadingMaterial(ReadingMaterial readingMaterial) {
		readingMaterialRepository.save(readingMaterial);
	}

	@Override
	public void editReadingMaterial(ReadingMaterial readingMaterial) {
		readingMaterialRepository.save(readingMaterial);
	}

	@Override
	public void deleteReadingMaterial(int id) {

		readingMaterialRepository.delete(readingMaterialRepository.findByReadingMaterialId(id));
	}

	@Override
	public void overrideReservation(ReadingMaterialReservation readingMaterialReservation) {
		
	}

	@Override
	public void exportLogs() {
		
	}

	@Override
	public ArrayList<ReadingMaterial> getAllReadingMaterials() {
		ArrayList<ReadingMaterial> listReadingMaterials = (ArrayList<ReadingMaterial>) readingMaterialRepository.findAll();
		return listReadingMaterials;
	}

	@Override
	public ReadingMaterial findReadingMaterialById(int id) {
		return 	readingMaterialRepository.findByReadingMaterialId(id);

	}

	

}