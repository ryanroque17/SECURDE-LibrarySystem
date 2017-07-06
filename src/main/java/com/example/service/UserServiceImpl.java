package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.ReadingMaterialRepository;
import com.example.repository.ReadingMaterialReservationRepository;
import com.example.repository.ReviewRepository;
import com.example.repository.RoleRepository;
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
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user, String role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        
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
	
}
