
package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.model.ReadingMaterial;
import com.example.model.ReadingMaterialReservation;
import com.example.model.Review;
import com.example.model.Room;
import com.example.model.RoomReservation;
import com.example.model.User;
import com.example.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/library/reserve", method = RequestMethod.POST)
	public ModelAndView reserveReadingMaterial(RedirectAttributes ra, @RequestParam("idReserve") int readingMaterialId, @RequestParam("idUser") String userId) {
		ModelAndView modelAndView = new ModelAndView();
		
		ReadingMaterial readingMaterial = userService.findReadingMaterialById(readingMaterialId);
		modelAndView.addObject("readingMaterial", readingMaterial);
		modelAndView.addObject("reservation", new ReadingMaterialReservation());
		modelAndView.addObject("userId", userId);

		modelAndView.setViewName("/library/reserve");

		return modelAndView;
	}

	@RequestMapping(value = "/library/reserve_again", method = RequestMethod.POST)
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
		modelAndView.addObject("successMessage", "A reading material has been reviewed successfully");

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
	
	@RequestMapping(value="/library/room", method = RequestMethod.POST)
	public ModelAndView libraryRoom(@RequestParam("date") String date, @RequestParam("roomId") String roomId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Room room = userService.getRoomById(roomId);
		ArrayList<RoomReservation> roomReservations = userService.getAllRoomReservationByDateAndRoomId(date, roomId);
		modelAndView.addObject("room", room);
		modelAndView.addObject("roomReservations", roomReservations);
		modelAndView.addObject("date", date);
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("library/room");
		return modelAndView;
	}
	
	@RequestMapping(value="/library/room", method = RequestMethod.GET)
	public ModelAndView libraryRoom(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Room room = userService.getRoomById("1");
		ArrayList<RoomReservation> roomReservations = userService.getAllRoomReservationByDateAndRoomId(dateFormat.format(date), room.getRoomId());
		modelAndView.addObject("room", room);
		modelAndView.addObject("roomReservations", roomReservations);
		modelAndView.addObject("date", dateFormat.format(date));
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("library/room");
		return modelAndView;
	}
	
	@RequestMapping(value="/library/room/reserve", method = RequestMethod.POST)
	public ModelAndView libraryRoomReserve(@RequestParam("roomId") String roomId, @RequestParam("userId") String userId, 
			@RequestParam("date") String date, @RequestParam("time") String time){
		ModelAndView modelAndView = new ModelAndView();
		RoomReservation roomReservation = new RoomReservation();
		roomReservation.setDate(date);
		roomReservation.setUserId(userId);
		roomReservation.setRoomId(roomId);
		String[] times = time.split(" - ");
		roomReservation.setStartTime(times[0]);
		roomReservation.setEndTime(times[1]);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		userService.reserveRoom(roomReservation);
		Room room = userService.getRoomById(roomId);
		ArrayList<RoomReservation> roomReservations = userService.getAllRoomReservationByDateAndRoomId(date, roomId);
		modelAndView.addObject("room", room);
		modelAndView.addObject("roomReservations", roomReservations);
		modelAndView.addObject("date", date);
		modelAndView.addObject("userId", user.getId());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with" + auth.getAuthorities()+ " Role");
		modelAndView.setViewName("library/room/reserve");
		return modelAndView;
	}
}
