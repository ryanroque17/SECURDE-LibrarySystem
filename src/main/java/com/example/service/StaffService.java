
package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Room;
import com.example.model.RoomReservation;
import com.example.model.User;

public interface StaffService {
	public ReadingMaterial findReadingMaterialById(int id);
	public ReadingMaterial findReadingMaterialByName(String name);

	public void saveReadingMaterial(ReadingMaterial readingMaterial);
	public void editReadingMaterial(ReadingMaterial readingMaterial);
	public void deleteReadingMaterial(int i);
	public void overrideReservation(ReadingMaterialReservation readingMaterialReservation);
	public ArrayList<ReadingMaterial> getAllReadingMaterials();
	public ArrayList<RoomReservation> getAllRoomReservationByDateAndRoomId(String date, String roomId);
	public Room getRoomById(String id);
	public ArrayList<User> getAllUsers();
	public ArrayList<Room> getAllRooms();
}