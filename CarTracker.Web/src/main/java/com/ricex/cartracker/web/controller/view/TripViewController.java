package com.ricex.cartracker.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/trip/")
public class TripViewController extends ViewController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView tripHome() {
		return new ModelAndView("trip/index");
	}
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView tripDetails() {
		return new ModelAndView("trip/details");
	}
	
}
