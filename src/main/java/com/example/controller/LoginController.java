	package com.example.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.crypt.BCrypt;
import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.User;
import com.example.service.AccountService;
import com.example.service.ManagerService;
import com.example.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="/error", method = RequestMethod.GET)
	public ModelAndView error(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		Logger log = LoggerFactory.getLogger(LoginController.class+"error()");
		log.info("404 Not Found");
		return modelAndView;
	}
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		System.out.println("ey");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
		
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("newUser", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value="/forgotPassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgotPassword");
		return modelAndView;
	}
	

	@RequestMapping(value="/forgotPassword", method = RequestMethod.POST)
	public ModelAndView forgotPasswordPost(@RequestParam String email){
		ModelAndView modelAndView = new ModelAndView();

		User user = userService.findUserByEmail(email);
		if (user == null) {
			Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
			log.info("Email is incorrect.");
			modelAndView.addObject("successMessage", "There is no user registered with the email provided");
			modelAndView.setViewName("forgotPassword");


		}else{
			Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
			log.info("Email is correct.");
			modelAndView.addObject("user", user);
			modelAndView.setViewName("secretAnswer");

		}

		return modelAndView;
	}
	
	@RequestMapping(value="/forgotPassword/secretAnswer", method = RequestMethod.POST)
	public ModelAndView secretAnswer(HttpSession session, @RequestParam String answer, @RequestParam int userId){
		session.setMaxInactiveInterval(120);
		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findUserByUserId(Integer.toString(userId));
		
		
		System.out.println(answer + " " + user.getSecretQuestionAnswer());
		if(!BCrypt.checkpw(answer, user.getSecretQuestionAnswer())){
			Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
			log.info(user.getEmail() + " secret answer is incorrect.");
			modelAndView.addObject("successMessage", "Secret answer is incorrect");
			modelAndView.addObject("user", user);

			modelAndView.setViewName("secretAnswer");

		}else{
			Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
			log.info(user.getEmail() + " secret answer is correct.");
			modelAndView.addObject("user", user);

			modelAndView.setViewName("changePassword");

		}

		return modelAndView;
	}
	
	@RequestMapping(value="/forgotPassword/changePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(@RequestParam String newPassword,  @RequestParam int userId){
		ModelAndView modelAndView = new ModelAndView();
		User user = userService.findUserByUserId(Integer.toString(userId));

		accountService.changePassword(newPassword, user);
		modelAndView.addObject("correct", "yes");
		modelAndView.addObject("user", user);
		Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
		log.info(user.getEmail() + "'s password is changed.");
		modelAndView.addObject("successMessage", "Successfuly changed the password");
		modelAndView.setViewName("login");

		return modelAndView;
	}
	
	@RequestMapping(value = {"/registration","/admin/create_employee"}, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult, @RequestParam("role") String role) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user", 
							"There is already a user registered with the email provided");
		} 
		else if (!userService.passwordValidator(user.getPassword())) {
			bindingResult
					.rejectValue("password", "password.user", "");
		}
		
		if (bindingResult.hasErrors()) {
			if(role.equals("user"))
				modelAndView.setViewName("registration");
			else
				modelAndView.setViewName("/admin/home");
			
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
		}
		else {
			
			userService.saveUser(user, role);
			Logger log = LoggerFactory.getLogger(LoginController.class+"-createNewUser");
			log.info(user.getEmail() + " account is created.");
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("newUser", new User());
			if(role.equals("user"))
				modelAndView.setViewName("registration");
			else
				modelAndView.setViewName("/admin/home");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView adminHome(HttpSession session){
		session.setMaxInactiveInterval(120);
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName());
		modelAndView.addObject("newUser", new User());

		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/library/home", method = RequestMethod.GET)
	public ModelAndView libraryHome(HttpSession session){
		session.setMaxInactiveInterval(120);
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		ArrayList<ReadingMaterial> listReadingMaterials = userService.getAllReadingMaterials();
		ArrayList<ReadingMaterialReservation> listReadingMaterialReservations = managerService.getAllCurrentReadingMaterialReservation();
		System.out.println("size" + listReadingMaterialReservations.size());
		
		for(int i=0; i<listReadingMaterials.size();i++){
			for(int j=0; j<listReadingMaterialReservations.size();j++)
				if(listReadingMaterials.get(i).getReadingMaterialId() == listReadingMaterialReservations.get(j).getReadingMaterialId()){
					listReadingMaterials.get(i).setStatus("Out");
					listReadingMaterials.get(i).setReturnDate(listReadingMaterialReservations.get(j).getReturnDate().toString().split(" ")[0]);
				}
		}
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("listReservations", listReadingMaterialReservations);
		
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("library/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/employee/manager/home", method = RequestMethod.GET)
	public ModelAndView managerHome(HttpSession session){
		session.setMaxInactiveInterval(120);
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("employee/manager/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/employee/staff/home", method = RequestMethod.GET)
	public ModelAndView staffHome(HttpSession session){
		session.setMaxInactiveInterval(120);
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("employee/staff/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/change-password", method = RequestMethod.POST)
	public ModelAndView changePassword(@RequestParam String currentPassword, @RequestParam String newPassword){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");

		if(!accountService.verifyPassword(user, currentPassword)){
			Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
			log.info(user.getEmail() + " - current password doesn't match with the account's password.");
			modelAndView.addObject("successMessage", "Current password doesn't match with the account's password.");
		}else{
			accountService.changePassword(newPassword, user);
			modelAndView.addObject("correct", "yes");
			modelAndView.addObject("user", user);
			Logger log = LoggerFactory.getLogger(LoginController.class+"- forgotPasswordPost()");
			log.info(user.getEmail() + "'s password is changed.");
			modelAndView.addObject("successMessage", "Successfuly changed the password");
			modelAndView.setViewName("/login");

		}
		return modelAndView;
	}
	
	@RequestMapping(value="/change-password", method = RequestMethod.GET)
	public ModelAndView changePassword(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("AAAA");
		User user = userService.findUserByEmail(auth.getName());
		System.out.println("AAA1");
		modelAndView.addObject("userId", user.getId());
		System.out.println("AAA2");
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		System.out.println("AAA3");
		modelAndView.setViewName("change-password");
		return modelAndView;
	}
}
