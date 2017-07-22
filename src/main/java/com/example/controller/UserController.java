
package com.example.controller;

import java.util.ArrayList;

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

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;
import com.example.model.User;
import com.example.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/library/reserve", method = RequestMethod.GET)
	public ModelAndView reserveReadingMaterial(@RequestParam("idReserve") int readingMaterialId, @RequestParam("idUser") String userId) {
		ModelAndView modelAndView = new ModelAndView();
		
		ReadingMaterial readingMaterial = userService.findReadingMaterialById(readingMaterialId);
		modelAndView.addObject("readingMaterial", readingMaterial);
		modelAndView.addObject("reservation", new ReadingMaterialReservation());
		modelAndView.addObject("userId", userId);

		modelAndView.setViewName("/library/reserve");

		return modelAndView;
	}

	@RequestMapping(value = "/library/reserve", method = RequestMethod.POST)
	public ModelAndView saveReserveReadingMaterial(@Valid @ModelAttribute("reservation") ReadingMaterialReservation readingMaterialReservation, BindingResult bindingResult, @RequestParam("idReserve") int readingMaterialId, @RequestParam("userId") String userId) {
		ModelAndView modelAndView = new ModelAndView();
			ReadingMaterial readingMaterial = userService.findReadingMaterialById(readingMaterialId);

			readingMaterial.setStatus("out");
			readingMaterialReservation.setUserId(userId);
			readingMaterialReservation.setReadingMaterialId(readingMaterialId);
			userService.reserveReadingMaterial(readingMaterial, readingMaterialReservation);

			modelAndView.addObject("successMessage", "A reading material has been reserved successfully");
			modelAndView.addObject("readingMaterial", readingMaterial);
			modelAndView.addObject("reservation", new ReadingMaterialReservation());
			modelAndView.addObject("userId", userId);

			modelAndView.setViewName("/library/reserve");
			modelAndView.addObject("canComment", "yes");

		return modelAndView;
	}
	
	@RequestMapping(value = "/library/review", method = RequestMethod.GET)
	public ModelAndView reviewReadingMaterial(@RequestParam("idReserve") int readingMaterialId, @RequestParam("userId") String userId) {
		ModelAndView modelAndView = new ModelAndView();
		
		ReadingMaterial readingMaterial = userService.findReadingMaterialById(readingMaterialId);
		modelAndView.addObject("readingMaterial", readingMaterial);
		modelAndView.addObject("reviewEntry", new Review());
		modelAndView.addObject("userId", userId);

		modelAndView.setViewName("/library/review");

		return modelAndView;
	}
	
	@RequestMapping(value = "/library/review", method = RequestMethod.POST)
	public ModelAndView addReviewReadingMaterial(@Valid @ModelAttribute("reviewEntry") Review review, @RequestParam("idReserve") int readingMaterialId, @RequestParam("idUser") String userId) {
		ModelAndView modelAndView = new ModelAndView();
		review.setUserId(userId);
		review.setReadingMaterialId(readingMaterialId);
		userService.addReview(review);

		ReadingMaterial readingMaterial = userService.findReadingMaterialById(readingMaterialId);
		modelAndView.addObject("readingMaterial", readingMaterial);
		modelAndView.addObject("reviewEntry", new Review());
		modelAndView.addObject("userId", userId);

		modelAndView.setViewName("/library/review");

		return modelAndView;
	}
	
	@RequestMapping(value="/library/room", method = RequestMethod.GET)
	public ModelAndView libraryRoom(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User user = userService.findUserByEmail(auth.getName());
		ArrayList<ReadingMaterial> listReadingMaterials = userService.getAllReadingMaterials();
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("library/room");
		return modelAndView;
	}
}
