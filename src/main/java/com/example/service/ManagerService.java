package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.User;


public interface ManagerService {
	public ReadingMaterial findReadingMaterialById(int id);
	public void saveReadingMaterial(ReadingMaterial readingMaterial);
	public void editReadingMaterial(ReadingMaterial readingMaterial);
	public void deleteReadingMaterial(int i);
	public void overrideReservation(ReadingMaterialReservation readingMaterialReservation);
	public ArrayList<ReadingMaterial> getAllReadingMaterials();
	public void exportLogs();
}