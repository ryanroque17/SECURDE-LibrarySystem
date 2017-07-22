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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.ReadingMaterial;
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
		modelAndView.addObject("newReadingMaterial", new ReadingMaterial());
		modelAndView.addObject("listReadingMaterials", listReadingMaterials);
		modelAndView.setViewName("/employee/manager/edit");

		return modelAndView;
	}

	@RequestMapping(value = "/employee/manager/edit", method = RequestMethod.POST)
	public ModelAndView saveEditReadingMaterial(
			@Valid @ModelAttribute("newReadingMaterial") ReadingMaterial readingMaterial, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/employee/manager/edit");

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

			managerService.editReadingMaterial(readingMaterial);
			ArrayList<ReadingMaterial> listReadingMaterials = managerService.getAllReadingMaterials();

			modelAndView.addObject("successMessage", "A reading material has been edited successfully");
			modelAndView.addObject("newReadingMaterial", new ReadingMaterial());
			modelAndView.addObject("listReadingMaterials", listReadingMaterials);
			modelAndView.setViewName("/employee/manager/edit");
		}
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
		modelAndView.setViewName("/employee/manager/delete");

		return modelAndView;
	}
}
