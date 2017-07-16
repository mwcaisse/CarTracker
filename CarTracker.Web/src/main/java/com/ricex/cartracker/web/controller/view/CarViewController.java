package com.ricex.cartracker.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/car/")
public class CarViewController extends ViewController {
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView carDetails() {
		return new ModelAndView("car/details");
	}
	
}
