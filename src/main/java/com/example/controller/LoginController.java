package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.User;
import com.example.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
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
	
	@RequestMapping(value = {"/registration","/admin/create_employee"}, method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute("newUser") User user, BindingResult bindingResult, @RequestParam("role") String role) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		System.out.println("Ads");
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
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
		} else {
			
			userService.saveUser(user, role);
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
	public ModelAndView adminHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome admin! What do you want to do?");
		modelAndView.addObject("newUser", new User());

		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/library/home", method = RequestMethod.GET)
	public ModelAndView libraryHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("library/home");
		return modelAndView;
	}
	
	@RequestMapping(value="/employee/home", method = RequestMethod.GET)
	public ModelAndView employeeHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("employee/home");
		return modelAndView;
	}

}
