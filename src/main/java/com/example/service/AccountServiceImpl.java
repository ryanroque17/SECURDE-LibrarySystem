package com.example.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;

@Service("accountService")
public class AccountServiceImpl implements AccountService{

	@Autowired
	private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	@Override
	public void changePassword(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        
		userRepository.save(user);
	}


	@Override
	public boolean verifyPassword(User user, String currentPassword) {
		
		return false;
	}

	

}
