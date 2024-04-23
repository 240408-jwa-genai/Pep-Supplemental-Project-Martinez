package com.revature.controller;

import com.revature.MainDriver;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.service.UserService;


public class UserController {
	
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void authenticate(UsernamePasswordAuthentication loginRequestData) {
		//pass data onto service layer
		User usr = userService.authenticate(loginRequestData);
		if(usr.getId()!=0)
		{
			MainDriver.loggedInUserId = usr.getId();
			System.out.println(String.format("Hello %s! Welcome back\n",usr.getUsername()));
		}
		else
		{
			System.out.println("Bad login. please try again");
		}

	}

	public void register(User registerRequestData) {
		/*
		Because controller is only responsible for getting user input and returning messages/data
		to the user, we will simply pass the user data into the service layer, and then depending
		on the response back from the service layer, tell the user their request was successful or not
		*/
		User usrResp = userService.register(registerRequestData);
		//until we can get DB data, id will always be 0
		if(usrResp.getId()!=0) System.out.println("Registration successful! Enjoy using the planetarium!");
		else System.out.println("Registration failed!");
	}

	public void logout() {
		//reset all variable variables from Main
		MainDriver.loggedInUserId = 0;
		MainDriver.numPlanets = 0;
		MainDriver.currPlanetId = 0;
		MainDriver.numMoonsForPlanet = 0;
		MainDriver.currMoonId = 0;

	}

	//get the sample methods
	public boolean checkAuthorization(int userId) {
		UsernamePasswordAuthentication obj = new UsernamePasswordAuthentication();

		return false;
	}

}
