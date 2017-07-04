package com.example.service;

import com.example.model.User;

public interface AdminService {
	public User findUserByEmail(String email);
	public void saveUser(User user, String role);
	public void unlockUser(User user);
	public void exportLogs();
}
