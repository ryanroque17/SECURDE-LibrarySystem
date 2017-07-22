package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;
import com.example.model.Room;
import com.example.model.User;

public interface UserService {
	public ReadingMaterial findReadingMaterialById(int id);
	public User findUserByEmail(String email);
	public void saveUser(User user, String role);
	public void reserveReadingMaterial(ReadingMaterial readingMaterial, ReadingMaterialReservation readingMaterialReservation);
	public ArrayList<ReadingMaterial> getAllReadingMaterials();
	public ArrayList<Room> getAllRooms();
	public void addReview(Review review);

}
