package com.example.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@Service("adminService")
public class AdminServiceImpl implements AdminService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user, String role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        
        Role userRole = null;
		if(role.equals("libraryStaff")){
			userRole = roleRepository.findByRole("LIBRARY_STAFF");
		}else if(role.equals("libraryManager")){
			userRole = roleRepository.findByRole("LIBRARY_MANAGER");
		}
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public void unlockUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportLogs() {
		// TODO Auto-generated method stub
		
	}

}
