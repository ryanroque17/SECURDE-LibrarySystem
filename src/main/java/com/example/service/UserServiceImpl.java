package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;
import com.example.model.Role;
import com.example.model.Room;
import com.example.model.RoomReservation;
import com.example.model.User;
import com.example.repository.ReadingMaterialRepository;
import com.example.repository.ReadingMaterialReservationRepository;
import com.example.repository.ReviewRepository;
import com.example.repository.RoleRepository;
import com.example.repository.RoomRepository;
import com.example.repository.RoomReservationRepository;
import com.example.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private ReadingMaterialRepository readingMaterialRepository;
	@Autowired
	private ReadingMaterialReservationRepository readingMaterialReservationRepository;
	@Autowired
    private ReviewRepository reviewRepository;
	@Autowired
    private RoomRepository roomRepository;
	@Autowired
    private RoomReservationRepository roomReservationRepository;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user, String role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setSecretQuestionAnswer(bCryptPasswordEncoder.encode(user.getSecretQuestionAnswer()));
        
        Role userRole = null;
		if(role.equals("user")){
			int idChecker = Integer.parseInt(user.getId().substring(0, 4));
			
			if(idChecker == 1998 || (idChecker>2000 && idChecker<3000))
				userRole = roleRepository.findByRole("FACULTY");
			else
				userRole = roleRepository.findByRole("STUDENT");
		}else if(role.equals("libraryStaff")){
			userRole = roleRepository.findByRole("LIBRARY_STAFF");
		}else if(role.equals("libraryManager")){
			userRole = roleRepository.findByRole("LIBRARY_MANAGER");
		}
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public ArrayList<ReadingMaterial> getAllReadingMaterials() {
		ArrayList<ReadingMaterial> listReadingMaterials = (ArrayList<ReadingMaterial>) readingMaterialRepository.findAll();
		return listReadingMaterials;		
	}
	@Override
	public ArrayList<Room> getAllRooms() {
		ArrayList<Room> rooms = (ArrayList<Room>) roomRepository.findAll();
		return rooms;		
	}

	@Override
	public ReadingMaterial findReadingMaterialById(int id) {
		return 	readingMaterialRepository.findByReadingMaterialId(id);
	}

	@Override
	public void reserveReadingMaterial(ReadingMaterial readingMaterial, ReadingMaterialReservation readingMaterialReservation) {
		readingMaterialRepository.save(readingMaterial);
		readingMaterialReservationRepository.save(readingMaterialReservation);
	}

	@Override
	public void addReview(Review review) {
		reviewRepository.save(review);
	}

	@Override
	public void reserveRoom(RoomReservation roomReservation) {
		roomReservationRepository.save(roomReservation);
		
	}

	@Override
	public ArrayList<RoomReservation> getAllRoomReservationByDateAndRoomId(String date, String roomId) {
		ArrayList<RoomReservation> listRoomReservation = (ArrayList<RoomReservation>) roomReservationRepository.findAll();
		ArrayList<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		for(int i =0; i< listRoomReservation.size(); i++) 
		{
			System.out.println("AAA "  +i);
			System.out.println(date + " " + listRoomReservation.get(i).getDate());
			if(listRoomReservation.get(i).getDate().equals(date) && listRoomReservation.get(i).getRoomId().equals(roomId)){
				System.out.println(i);
				roomReservations.add(listRoomReservation.get(i));
			}
		}
		
		return roomReservations;
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
	public boolean passwordValidator(String password) {	
		if(password.length()>=6 && !StringUtils.containsWhitespace(password))
	    {
	        Pattern Upperletter = Pattern.compile("[A-z]");
	        Pattern Lowerletter = Pattern.compile("[a-z]");
	        Pattern digit = Pattern.compile("[0-9]");
	        Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

	        Matcher hasUpperLetter = Upperletter.matcher(password);
	        Matcher hasLowerLetter = Lowerletter.matcher(password);
	        Matcher hasDigit = digit.matcher(password);
	        Matcher hasSpecial = special.matcher(password);
	        return hasUpperLetter.find() && hasLowerLetter.find() && hasDigit.find() && hasSpecial.find();

	    }
	    else
	    	return false;
	}
}
