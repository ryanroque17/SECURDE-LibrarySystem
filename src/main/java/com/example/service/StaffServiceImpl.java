package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.repository.ReadingMaterialRepository;

@Service("staffService")
public class StaffServiceImpl implements StaffService {

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
	public ArrayList<ReadingMaterial> getAllReadingMaterials() {
		ArrayList<ReadingMaterial> listReadingMaterials = (ArrayList<ReadingMaterial>) readingMaterialRepository
				.findAll();
		return listReadingMaterials;
	}

	@Override
	public ReadingMaterial findReadingMaterialById(int id) {
		return readingMaterialRepository.findByReadingMaterialId(id);

	}

	@Override
	public ReadingMaterial findReadingMaterialByName(String name) {
		// TODO Auto-generated method stub
		return readingMaterialRepository.findByTitle(name);
	}

}
