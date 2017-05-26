package com.ricex.cartracker.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/log/")
public class LogViewController extends ViewController {

	@RequestMapping(value = "/reader", method = RequestMethod.GET)
	public ModelAndView carDetails() {
		return new ModelAndView("log/reader");
	}
	
}
