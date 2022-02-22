package com.userfront.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.userfront.domain.Appointment;
import com.userfront.domain.User;
import com.userfront.service.AppointmentService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String loadAppointment(Model model, Principal principal) {
		if(!userService.isAuthenticated()) {
			return "redirect:/";
		}
		User user = userService.findByUsername(principal.getName());
		Appointment appointment = new Appointment();
		model.addAttribute("appointment", appointment);
		model.addAttribute("dateString","");
		userService.generalMethod(model, principal, user);
		return "appointment";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String postAppointment(@ModelAttribute("appointment") Appointment appointment,
			@ModelAttribute("dateString") String date, Model model, Principal principal) throws ParseException {
		
		//SimpleDateFormat format1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		Date d1 = (Date)formatter.parse(date);
		//Date d1 = format1.parse(date);
		appointment.setDate(d1);
		User user = userService.findByUsername(principal.getName());
		appointment.setUser(user);
		appointmentService.createAppointment(appointment);
		model.addAttribute("msg", "Successful");
		userService.generalMethod(model, principal, user);
		return "appointment";
	}
	
	
}
