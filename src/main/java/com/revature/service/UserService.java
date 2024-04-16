package com.revature.service;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;

public class UserService {

	private UserDao dao;

	public UserService(UserDao dao){
		this.dao = dao;
	}

	public User authenticate(UsernamePasswordAuthentication loginRequestData) {
		// TODO: authenticate entered credentials are valid
		//make sure username exists in DB
		User usr = dao.getUserByUsername(loginRequestData.getUsername());
		if(usr!=null)
		{
			//ensure given password is correct
			if(usr.getPassword().equals(loginRequestData.getPassword()))
			{
				//if passwords match, return created user object
				return  usr;
			}
		}
		//else return null object to indicate something went wrong
		return new User();
	}

	public User register(User registerRequestData) {
		/*
		Ensure password and username meet requirements:
			- username needs to be unique
			- username and password need be at most 30 characters
		 */

		if(registerRequestData.getUsername().length() <= 30 && registerRequestData.getPassword().length() <= 30)
		{
			//username and password meet length requirement
			//we check if username is unique
			//currently dao returns null, need sql which is next week
			User possibleDup = dao.getUserByUsername(registerRequestData.getUsername());

			/*
			using dao method to grab account that already has the username the registering user is trying to use
			we can check if the username is already in use. If not, we can go through with creating new account,
			but if it in use, we send back an empty user object to inform the controller layer that the account could
			not be made
			 */

			/*
			Two checks:
				1) possibleDup is not null, it will return null if something went wrong with the request
				2) assuming user obj was returned, does it have the same username as the register request username
			 */
			if(possibleDup != null) {
				String usernameFromDB = possibleDup.getUsername();
				String usernameFromRegisterRequest = registerRequestData.getUsername();
				if(!usernameFromRegisterRequest.equals(usernameFromDB))
				{
					UsernamePasswordAuthentication validUserCreds = new UsernamePasswordAuthentication();
					validUserCreds.setUsername(usernameFromRegisterRequest);
					validUserCreds.setPassword(registerRequestData.getPassword());

					//return user data once its persisted in database
					//we can return usr object that has been initialized
					//to inform

					return dao.createUser(validUserCreds);
				}
			}
		}
		//if all Business/Software requirements are not met, we return an empty user which we can use to
		//inform controller layer that registration process failed
		return new User();
	}
}
