package com.ricex.cartracker.web.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/test/")
public class TestViewController extends ViewController {

	/**Test controller view
	 * 
	 * @return The login view
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("test/index");
	}
	
}
