package com.example.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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
import com.example.model.ReadingMaterialReservation;
import com.example.service.ManagerService;

@Controller
public class ManagerController {

	@Autowired
	ManagerService managerService;
	
	@Secured("LIBRARY_MANAGER")
	@RequestMapping(value = "/employee/manager/manage", method = RequestMethod.GET)
	public ModelAndView manageReadingMaterials() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/employee/manager/manage");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/create", method = RequestMethod.GET)
	public ModelAndView createReadingMaterial() {
		ModelAndView modelAndView = new ModelAndView();
		ReadingMaterial readingMaterial = new ReadingMaterial();
		modelAndView.addObject("newReadingMaterial", readingMaterial);
		modelAndView.setViewName("/employee/manager/create");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/create", method = RequestMethod.POST)
	public ModelAndView createNewReadingMaterial(
			@Valid @ModelAttribute("newReadingMaterial") ReadingMaterial readingMaterial, BindingResult bindingResult,
			@RequestParam("type") String type) {
		ModelAndView modelAndView = new ModelAndView();
		readingMaterial.setType(type);
		readingMaterial.setStatus("Available");
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/employee/manager/create");

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

			managerService.saveReadingMaterial(readingMaterial);
			modelAndView.addObject("successMessage", "A reading material has been added successfully");
			modelAndView.addObject("newReadingMaterial", new ReadingMaterial());
			modelAndView.setViewName("/employee/manager/create");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/edit", method = RequestMethod.GET)
	public ModelAndView editReadingMaterial() {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterial> listReadingMaterials = managerService.getAllReadingMaterials();
		modelAndView.addObject("readingMaterial", new ReadingMaterial());
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("hasSelected", "no");

		modelAndView.setViewName("/employee/manager/edit");

		return modelAndView;
	}
	@RequestMapping(value = "/employee/manager/edit/{title}", method = RequestMethod.POST)
	public ModelAndView editSpecificReadingMaterial(@PathVariable String title) {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterial> listReadingMaterials = managerService.getAllReadingMaterials();
		ReadingMaterial readingMaterial = managerService.findReadingMaterialByName(title);
		modelAndView.addObject("readingMaterial", readingMaterial);
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("hasSelected", "yes");

		modelAndView.setViewName("/employee/manager/edit");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/edit", method = RequestMethod.POST)
	public ModelAndView saveEditReadingMaterial(
			@Valid @ModelAttribute("readingMaterial") ReadingMaterial readingMaterial, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
			System.out.println(readingMaterial.getType() + " " + readingMaterial.getTitle());
			managerService.editReadingMaterial(readingMaterial);
			ArrayList<ReadingMaterial> listReadingMaterials = managerService.getAllReadingMaterials();
			
			modelAndView.addObject("successMessage", "A reading material has been edited successfully");
			modelAndView.addObject("readingMaterial", new ReadingMaterial());
			modelAndView.addObject("listReadingMaterials", listReadingMaterials);
			modelAndView.setViewName("/employee/manager/edit");
		
		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/delete", method = RequestMethod.GET)
	public ModelAndView deleteReadingMaterial() {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterial> listReadingMaterials = managerService.getAllReadingMaterials();
		int id = 0;
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);

		modelAndView.setViewName("/employee/manager/delete");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/delete", method = RequestMethod.POST)
	public ModelAndView deleteReadingMaterialFinish(@RequestParam("deleteId") int deleteId) {
		ModelAndView modelAndView = new ModelAndView();

		managerService.deleteReadingMaterial(deleteId);
		ArrayList<ReadingMaterial> listReadingMaterials = managerService.getAllReadingMaterials();

		modelAndView.addObject("successMessage", "A reading material has been deleted successfully");
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.addObject("hasSelected", "no");

		modelAndView.setViewName("/employee/manager/delete");

		return modelAndView;
	}
	@RequestMapping(value = "/employee/manager/override", method = RequestMethod.GET)
	public ModelAndView overrideReservation() {
		ModelAndView modelAndView = new ModelAndView();
		ArrayList<ReadingMaterialReservation> listReadingMaterialReservation = managerService.getAllReadingMaterialReservation();
		modelAndView.addObject("listReadingMaterialReservation", listReadingMaterialReservation);

		modelAndView.setViewName("/employee/manager/override");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/override", method = RequestMethod.POST)
	public ModelAndView overrideReservationFinish(@RequestParam("deleteId") String deleteId) {
		ModelAndView modelAndView = new ModelAndView();
		System.out.println("OVERRIDE POST");
		managerService.overrideReservation(deleteId);
		ArrayList<ReadingMaterialReservation> listReadingMaterialReservation = managerService.getAllReadingMaterialReservation();

		modelAndView.addObject("successMessage", "A reservation has been overriden successfully");
		modelAndView.addObject("listReadingMaterialReservation", listReadingMaterialReservation);
		modelAndView.setViewName("/employee/manager/override");

		return modelAndView;
	}
}
