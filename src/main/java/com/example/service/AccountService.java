package com.example.service;

import java.util.ArrayList;

import com.example.model.ReadingMaterial;
import com.example.model.User;

public interface AccountService {
	public void changePassword(String newPassword, User user);
	public boolean verifyPassword(User user, String currentPassword);

	
}