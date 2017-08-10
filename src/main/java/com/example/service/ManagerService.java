package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.RoomReservation;
import com.example.model.User;


public interface ManagerService {
	public ReadingMaterial findReadingMaterialById(int id);
	public ReadingMaterial findReadingMaterialByName(String name);
	public void saveReadingMaterial(ReadingMaterial readingMaterial);
	public void editReadingMaterial(ReadingMaterial readingMaterial);
	public void deleteReadingMaterial(int i);
	public void overrideReservation(String readingMaterialReservation);
	public ArrayList<ReadingMaterial> getAllReadingMaterials();
	public ArrayList<ReadingMaterialReservation> getAllReadingMaterialReservation();
	public ArrayList<ReadingMaterialReservation> getAllCurrentReadingMaterialReservation();
	public ArrayList<RoomReservation> getAllRoomReservation();
	public ArrayList<RoomReservation> getAllCurrentRoomReservation();

	public void exportLogs();
}
