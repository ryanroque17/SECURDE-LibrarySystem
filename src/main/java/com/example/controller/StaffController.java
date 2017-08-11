package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.ReadingMaterial;
import com.example.model.Room;
import com.example.model.RoomReservation;
import com.example.model.User;
import com.example.service.StaffService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class StaffController {

	@Autowired
	StaffService staffService;

	@RequestMapping(value = "/employee/staff/manage", method = RequestMethod.GET)
	public ModelAndView staffeadingMaterials() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/employee/staff/manage");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/staff/create", method = RequestMethod.GET)
	public ModelAndView createReadingMaterial() {
		ModelAndView modelAndView = new ModelAndView();
		ReadingMaterial readingMaterial = new ReadingMaterial();
		modelAndView.addObject("newReadingMaterial", readingMaterial);
		modelAndView.setViewName("/employee/staff/create");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/staff/create", method = RequestMethod.POST)
	public ModelAndView createNewReadingMaterial(
			@Valid @ModelAttribute("newReadingMaterial") ReadingMaterial readingMaterial, BindingResult bindingResult,
			@RequestParam("type") String type) {
		ModelAndView modelAndView = new ModelAndView();
		readingMaterial.setType(type);
		readingMaterial.setStatus("Available");
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/employee/staff/create");

			for (Object object : bindingResult.getAllErrors()) {
				if (object instanceof FieldError) {
					FieldError fieldError = (FieldError) object;

					System.out.println(
							fieldError.getCode() + " " + fieldError.getDefaultMessage() + " " + fieldError.getField());
				}

				if (object instanceof ObjectError) {
					ObjectError objectError = (ObjectError) object;

					System.out.println(objectError.getCode());
				}
			}
		} else {
			
			staffService.saveReadingMaterial(readingMaterial);
			Logger log = LoggerFactory.getLogger(StaffController.class+" - createNewReadingMaterial()");
			log.info(readingMaterial.getTitle() + " is created.");
			modelAndView.addObject("successMessage", "A reading material has been added successfully");
			modelAndView.addObject("newReadingMaterial", new ReadingMaterial());
			modelAndView.setViewName("/employee/staff/create");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/employee/staff/edit", method = RequestMethod.GET)
	public ModelAndView editReadingMaterial() {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterial> listReadingMaterials = staffService.getAllReadingMaterials();
		modelAndView.addObject("readingMaterial", new ReadingMaterial());
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("hasSelected", "no");

		modelAndView.setViewName("/employee/staff/edit");

		return modelAndView;
	}
	@RequestMapping(value = "/employee/staff/edit/{title}", method = RequestMethod.POST)
	public ModelAndView editSpecificReadingMaterial(@PathVariable String title) {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterial> listReadingMaterials = staffService.getAllReadingMaterials();
		ReadingMaterial readingMaterial = staffService.findReadingMaterialByName(title);
		modelAndView.addObject("readingMaterial", readingMaterial);
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("hasSelected", "yes");

		modelAndView.setViewName("/employee/staff/edit");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/staff/edit", method = RequestMethod.POST)
	public ModelAndView saveEditReadingMaterial(
			@Valid @ModelAttribute("readingMaterial") ReadingMaterial readingMaterial, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			System.out.println(readingMaterial.getType() + " " + readingMaterial.getTitle());
			staffService.editReadingMaterial(readingMaterial);
			ArrayList<ReadingMaterial> listReadingMaterials = staffService.getAllReadingMaterials();
			Logger log = LoggerFactory.getLogger(StaffController.class+" - saveEditReadingMaterial()");
			log.info(readingMaterial.getTitle() + " is edited.");
			modelAndView.addObject("successMessage", "A reading material has been edited successfully");
			modelAndView.addObject("readingMaterial", new ReadingMaterial());
			modelAndView.addObject("listReadingMaterials", listReadingMaterials);
			modelAndView.setViewName("/employee/staff/edit");
		
		return modelAndView;
	}

	@RequestMapping(value = "/employee/staff/delete", method = RequestMethod.GET)
	public ModelAndView deleteReadingMaterial() {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterial> listReadingMaterials = staffService.getAllReadingMaterials();
		int id = 0;
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);

		modelAndView.setViewName("/employee/staff/delete");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/staff/delete", method = RequestMethod.POST)
	public ModelAndView deleteReadingMaterialFinish(@RequestParam("deleteId") int deleteId) {
		ModelAndView modelAndView = new ModelAndView();

		staffService.deleteReadingMaterial(deleteId);
		ArrayList<ReadingMaterial> listReadingMaterials = staffService.getAllReadingMaterials();
		ReadingMaterial readingMaterial = staffService.findReadingMaterialById(deleteId);
		Logger log = LoggerFactory.getLogger(StaffController.class+" - deleteReadingMaterial()");
		log.info(readingMaterial.getTitle() + " is deleted.");
		modelAndView.addObject("successMessage", "A reading material has been deleted successfully");
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.setViewName("/employee/staff/delete");

		return modelAndView;
	}
	
	@RequestMapping(value = "/employee/staff/room", method = RequestMethod.GET)
	public ModelAndView viewRoomReservations() {
		ModelAndView modelAndView = new ModelAndView();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		ArrayList<User> users = staffService.getAllUsers();
		ArrayList<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		Room room = staffService.getRoomById("1");
		modelAndView.addObject("users", users);
		modelAndView.addObject("room", room);
		modelAndView.addObject("date", dateFormat.format(date));
		roomReservations.addAll(staffService.getAllRoomReservationByDateAndRoomId(dateFormat.format(date), room.getRoomId()));
		modelAndView.addObject("roomReservations", roomReservations);
		return modelAndView;
	}
	
	@RequestMapping(value = "/employee/staff/room", method = RequestMethod.POST)
	public ModelAndView viewRoomReservations(@RequestParam("date") String date, @RequestParam("roomId") String roomId) {
		ModelAndView modelAndView = new ModelAndView();
		Room room = staffService.getRoomById(roomId);
		ArrayList<User> users = staffService.getAllUsers();
		ArrayList<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
		modelAndView.addObject("users", users);
		modelAndView.addObject("room", room);
		modelAndView.addObject("date",date);
		roomReservations.addAll(staffService.getAllRoomReservationByDateAndRoomId(date, roomId));
		Logger log = LoggerFactory.getLogger(StaffController.class+" - viewRoomReservations()");
		log.info("Available rooms of " + roomId + " on "+ date + " is viewed.");
		modelAndView.addObject("roomReservations", roomReservations);
		return modelAndView;
	}
}
