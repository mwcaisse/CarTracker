package com.ricex.cartracker.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/user/")
public class UserViewController extends ViewController {

	/** Allows a user to view their authentication tokens
	 * 
	 * @return The view for authentication tokens
	 */
	@RequestMapping(value = "/tokens", method = RequestMethod.GET)
	public ModelAndView authTokens() {
		return new ModelAndView("user/tokens");
	}
	
}
