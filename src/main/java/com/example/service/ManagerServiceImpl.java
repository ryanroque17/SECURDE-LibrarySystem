package com.example.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.RoomReservation;
import com.example.repository.ReadingMaterialRepository;
import com.example.repository.ReadingMaterialReservationRepository;
import com.example.repository.RoomRepository;
import com.example.repository.RoomReservationRepository;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {
	
	@Autowired
	private ReadingMaterialRepository readingMaterialRepository;
	@Autowired
	private ReadingMaterialReservationRepository readingMaterialReservationRepository;
	@Autowired
	private RoomRepository roomMaterialRepository;
	@Autowired
	private RoomReservationRepository roomReservationRepository;
	
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
	public void overrideReservation(String id) {

		readingMaterialReservationRepository.delete( readingMaterialReservationRepository.findByReservationId(id));
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

	@Override
	public ArrayList<ReadingMaterialReservation> getAllReadingMaterialReservation() {
		ArrayList<ReadingMaterialReservation> listReadingMaterialReservation = (ArrayList<ReadingMaterialReservation>) readingMaterialReservationRepository.findAll();
		return listReadingMaterialReservation;
	}

	@Override
	public ArrayList<ReadingMaterialReservation> getAllCurrentReadingMaterialReservation() {
		ArrayList<ReadingMaterialReservation> listReadingMaterialReservation = getAllReadingMaterialReservation();
		ArrayList<ReadingMaterialReservation> currentReadingMaterialReservation = new ArrayList<ReadingMaterialReservation>();
		Date today = new Date();

		for(int i=0; i<listReadingMaterialReservation.size();i++){
			if(listReadingMaterialReservation.get(i).getReturnDate().after(today)){
				currentReadingMaterialReservation.add(listReadingMaterialReservation.get(i));
			}
		}
		return currentReadingMaterialReservation;
	}

	@Override
	public ReadingMaterial findReadingMaterialByName(String name) {
		// TODO Auto-generated method stub
		return readingMaterialRepository.findByTitle(name);
	}

	@Override
	public ArrayList<RoomReservation> getAllRoomReservation(){
		ArrayList<RoomReservation> listRoomReservation = (ArrayList<RoomReservation>) roomReservationRepository.findAll();
		return listRoomReservation;
	}
	
	@Override
	public ArrayList<RoomReservation> getAllCurrentRoomReservation(){
		ArrayList<RoomReservation> listRoomReservation = (ArrayList<RoomReservation>) roomReservationRepository.findAll();
		ArrayList<RoomReservation> listCurrentRoomReservation = new ArrayList<RoomReservation>();
		Date today = new Date();

//		for(int i=0; i<listRoomReservation.size();i++){
//			if(listRoomReservation.get(i).getReturnDate().after(today)){
//				listCurrentRoomReservation.add(listCurrentRoomReservation.get(i));
//			}
//		}
		return listCurrentRoomReservation;
	}
	
}
