package com.ricex.cartracker.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/")
public class AdminViewController extends ViewController {

	@RequestMapping(value = "/registrationKeys", method = RequestMethod.GET)
	public ModelAndView registrationKeys() {
		return new ModelAndView("admin/registrationKeys");
	}
	
}
