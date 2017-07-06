package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.User;

public interface AdminService {
	public User findUserByEmail(String email);
	public User findUserById(String id);
	public void saveUser(User user, String role);
	public void unlockUser(User user);
	public void exportLogs();
	public ArrayList<User> getAllInactiveUsers();
}
