package com.example.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.ReadingMaterial;
import com.example.model.User;
import com.example.service.AdminService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/admin/manage", method = RequestMethod.GET)
	public ModelAndView manageAccounts(){
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName("/admin/manage");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/create", method = RequestMethod.GET)
	public ModelAndView createAccount(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("newUser", user);
		modelAndView.setViewName("/admin/create");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/create", method = RequestMethod.POST)
	public ModelAndView createNewAccount(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult, @RequestParam("role") String role) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = adminService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/admin/create");
			
			for (Object object : bindingResult.getAllErrors()) {
			    if(object instanceof FieldError) {
			        FieldError fieldError = (FieldError) object;

			        System.out.println(fieldError.getCode());
			    }

			    if(object instanceof ObjectError) {
			        ObjectError objectError = (ObjectError) object;

			        System.out.println(objectError.getCode());
			    }
			}
		} else {
			
			adminService.saveUser(user, role);
			Logger log = LoggerFactory.getLogger(AdminController.class+" - createNewUser()");
			log.info(user.getEmail() + " account is created.");
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("newUser", new User());
			modelAndView.setViewName("/admin/create");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/unlock", method = RequestMethod.GET)
	public ModelAndView unlockAccount(){
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<User> user = adminService.getAllInactiveUsers();
		

		modelAndView.addObject("inactiveUsers", user);
		modelAndView.setViewName("/admin/unlock");
		
		return modelAndView;
	}
	@RequestMapping(value = "/admin/unlock", method = RequestMethod.POST)
	public ModelAndView saveEditReadingMaterial(@RequestParam("inactiveId") String userId) {
		ModelAndView modelAndView = new ModelAndView();
		
			User user = adminService.findUserById(userId);
			adminService.unlockUser(user);
			Logger log = LoggerFactory.getLogger(AdminController.class+" - unlockAccount()");
			log.info(user.getEmail() + " account is unlocked.");
			ArrayList<User> inactiveUsers = adminService.getAllInactiveUsers();

			modelAndView.addObject("successMessage", "A user has been unlocked");
			modelAndView.addObject("inactiveUsers", inactiveUsers);
			modelAndView.setViewName("/admin/unlock");
		
		return modelAndView;
	}
}
