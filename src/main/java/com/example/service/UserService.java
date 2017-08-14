package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;
import com.example.model.Room;
import com.example.model.RoomReservation;
import com.example.model.User;

public interface UserService {
	public ReadingMaterial findReadingMaterialById(int id);
	public User findUserByEmail(String email);
	public User findUserByUserId(String id);

	public void saveUser(User user, String role);
	public void reserveRoom(RoomReservation roomReservation);
	public void reserveReadingMaterial(ReadingMaterial readingMaterial, ReadingMaterialReservation readingMaterialReservation);
	public ArrayList<RoomReservation> getAllRoomReservationByDateAndRoomId(String date, String roomId);
	public ArrayList<ReadingMaterial> getAllReadingMaterials();
	public Room getRoomById(String roomId);
	public ArrayList<Room> getAllRooms();
	public void addReview(Review review);
	public boolean passwordValidator(String password);
	public void recordLoginFailure(User user);
	public void recordLoginSuccess(User user);
}
