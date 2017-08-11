package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Room;
import com.example.model.RoomReservation;
import com.example.model.User;
import com.example.repository.ReadingMaterialRepository;
import com.example.repository.RoomRepository;
import com.example.repository.RoomReservationRepository;
import com.example.repository.UserRepository;

@Service("staffService")
public class StaffServiceImpl implements StaffService {

	@Autowired
	private ReadingMaterialRepository readingMaterialRepository;
	@Autowired
    private RoomReservationRepository roomReservationRepository;
	@Autowired
    private RoomRepository roomRepository;
	@Autowired
    private UserRepository userRepository;

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
	
	@Override
	public ArrayList<RoomReservation> getAllRoomReservationByDateAndRoomId(String date, String roomId) {
		ArrayList<RoomReservation> listRoomReservation = (ArrayList<RoomReservation>) roomReservationRepository.findAll();
		ArrayList<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		
		System.out.println(listRoomReservation.size());
		for(int i =0; i< listRoomReservation.size(); i++) 
		{
			System.out.println(date + " " + listRoomReservation.get(i).getDate());
			System.out.println(roomId + " " + listRoomReservation.get(i).getRoomId());
			if(listRoomReservation.get(i).getDate().equals(date) && listRoomReservation.get(i).getRoomId().equals(roomId)){
				
				roomReservations.add(listRoomReservation.get(i));
			}
		}
		
		return roomReservations;
	}

	@Override
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> rooms = (ArrayList<Room>) roomRepository.findAll();
		return rooms;		
	}

	@Override
	public Room getRoomById(String roomId) {
		ArrayList<Room> rooms = this.getAllRooms();
		for(int i =0; i< rooms.size(); i++)
			if(rooms.get(i).getRoomId().equals(roomId))
				return rooms.get(i);
		return null;
	}

	@Override
	public ArrayList<User> getAllUsers() {
		return userRepository.findAll();
	}

}