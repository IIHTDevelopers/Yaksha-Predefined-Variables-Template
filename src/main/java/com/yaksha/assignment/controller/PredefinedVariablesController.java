package com.yaksha.assignment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PredefinedVariablesController {

	// Display form for user input
	@GetMapping("/")
	public String showForm() {
		return "index";
	}

	// Display session and request info
	@GetMapping("/sessionInfo")
	public String showSessionInfo(HttpServletRequest request, HttpSession session, Model model) {
		// Access predefined variables
		String requestInfo = "Request URI: " + request.getRequestURI() + "<br>" + "Request Method: "
				+ request.getMethod() + "<br>" + "Request Parameter (name): " + request.getParameter("name");

		// Access session details
		String sessionInfo = "Session ID: " + session.getId() + "<br>" + "Session Creation Time: "
				+ session.getCreationTime();

		// Access application-wide information
		String appInfo = "Application Info: Java Version - " + System.getProperty("java.version");

		// Adding them to the model
		model.addAttribute("requestInfo", requestInfo);
		model.addAttribute("sessionInfo", sessionInfo);
		model.addAttribute("appInfo", appInfo);

		return "sessionInfo";
	}
}
