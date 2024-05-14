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
<<<<<<< HEAD
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
=======
		// since this is where the credentials are actually authenticated we can use the username and newly finished
		// dao method to find users by username, and check to see if we get a user back
		User possibleUser = dao.getUserByUsername(loginRequestData.getUsername());
		if(possibleUser != null){
			// the commented line is redundant
//			boolean usernamesMatch = loginRequestData.getUsername().equals(possibleUser.getUsername());
			boolean passwordsMatch = loginRequestData.getPassword().equals(possibleUser.getPassword());
			if (passwordsMatch){
				return possibleUser;
			}
		}
>>>>>>> PepSupplementalMartinez/main
		return new User();
	}

	public User register(User registerRequestData) {
		/*
<<<<<<< HEAD
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
=======
			there are two software requirements I need to check in this method:
				username needs to be unique
				username/password need to be a maximum of 30 characters
		 */
		// we can check the length of the username and password without needing any information from the
		// database that has other user information. This is a simple if else check
		if (registerRequestData.getUsername().length() <= 30 && registerRequestData.getPassword().length() <= 30){
			/*
				This block of code should only be reached if the username and password are the proper length. If
				that is the case, we can then check that the username is unique, since there is no point in making
				this check if the username and/or password is the incorrect length
			 */
			// NOTE: the dao method currently returns a null value
			User databaseData = dao.getUserByUsername(registerRequestData.getUsername());
			/*
				Using the dao method to grab any account that already has the username the registering user
				is trying to use, we can check if the username is already in use. If not, we can go through with
				creating the new account, but if it is in use, we send back an empty User object to inform the
				controller layer that the account could not be made.
			 */

			/*
				I am checking two things here:
					1. is the usernameFromDatabase object not null (meaning there is a getUsername method I can call)
					2. assuming a User object was returned, does it have the same username as the register request username

			 */

			// if the data from the database is null then something has gone wrong and I should give a fail
			// message to the user
			if(databaseData != null){
				// for the sake of readability I am saving the data I work with in its own variables
				String usernameFromDatabase = databaseData.getUsername();
				String usernameFromRegisterRequest = registerRequestData.getUsername();
				// checking if the usernames are not the same
				if (!usernameFromRegisterRequest.equals(usernameFromDatabase)){
					// if the data is not the same, then the credentials are valid and I can go through
					// with account registration. Note the registration method requires a new object:
					// a UsernamePasswordAuthentication object specifically
>>>>>>> PepSupplementalMartinez/main
					UsernamePasswordAuthentication validUserCreds = new UsernamePasswordAuthentication();
					validUserCreds.setUsername(usernameFromRegisterRequest);
					validUserCreds.setPassword(registerRequestData.getPassword());

<<<<<<< HEAD
					//return user data once its persisted in database
					//we can return usr object that has been initialized
					//to inform

=======
					// We can shorten our code a bit and return the User data once it is persisted in the
					// database. We can return a User object with Data that has been initialized to inform
					// the controller layer the registration process was successful
>>>>>>> PepSupplementalMartinez/main
					return dao.createUser(validUserCreds);
				}
			}
		}
<<<<<<< HEAD
		//if all Business/Software requirements are not met, we return an empty user which we can use to
		//inform controller layer that registration process failed
=======
		// if any Business/Software requirements are not met, we return an empty user which we can use to
		// inform the controller layer that the registration process failed
>>>>>>> PepSupplementalMartinez/main
		return new User();
	}
}
