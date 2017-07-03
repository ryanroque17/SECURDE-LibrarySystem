package com.example.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
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
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        
        int role = 1;
        Role userRole = null;
		if(role==1){
			userRole = roleRepository.findByRole("STUDENT");
		}else if(role==2){
			userRole = roleRepository.findByRole("FACULTY");
		}else if(role==3){
			userRole = roleRepository.findByRole("LIBRARY_STAFF");
		}else if(role==4){
			userRole = roleRepository.findByRole("LIBRARY_MANAGER");
		}
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

}
