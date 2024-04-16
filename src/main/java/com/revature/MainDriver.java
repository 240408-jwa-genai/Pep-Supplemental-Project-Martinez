package com.revature;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.controller.MoonController;
import com.revature.controller.PlanetController;
import com.revature.controller.UserController;
import com.revature.models.Moon;
import com.revature.models.Planet;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.MoonDao;
import com.revature.repository.PlanetDao;
import com.revature.repository.UserDao;
import com.revature.service.MoonService;
import com.revature.service.PlanetService;
import com.revature.service.UserService;
import com.revature.utilities.ConnectionUtil;

public class MainDriver {
    //declaring these as static to access objects without instantiating them
    public static UserDao usrDao = new UserDao();
    public static UserService usrService = new UserService(usrDao);
    public static UserController usrController = new UserController(usrService);

    //declaring necessary moon objects
    public static MoonDao moonDao = new MoonDao();
    public static MoonService moonService = new MoonService(moonDao);
    public static MoonController moonController = new MoonController(moonService);

    //declaring necessary planet methods
    public static PlanetDao planetDao = new PlanetDao();
    public static PlanetService planetService = new PlanetService(planetDao);
    public static PlanetController planetController = new PlanetController(planetService);

    //variable to keep track of logged  users
    public static int loggedInUserId = 0;

    //keep track if user has planets/moons
    public static int numPlanets = 0;
    public static int numMoons = 0;

    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application
        try(Scanner s = new Scanner(System.in))
        {
            boolean userActive = true;
            //infinite loop to keep program running
            while(userActive) {
                System.out.println("\nHello! Welcome to the Planetarium, Enter 1 to register an account, 2 to log in, or q to quit: ");
                String resp = s.nextLine();
                if (resp.equals("1")) {
                    //prompt user of their choice
                    System.out.println("You chose to register an account!");

                    //prompt user to input their desired username and password
                    System.out.print("Please enter your desired username: ");
                    String pUser = s.nextLine();
                    System.out.print("Please enter your desired password: ");
                    String pass = s.nextLine();
                    /*
                    System.out.println(pUser);
                    System.out.println(pass);
                     */

                    User usr = new User();
                    usr.setUsername(pUser);
                    usr.setPassword(pass);
                    // pass data into service layer for verification through controller
                    usrController.register(usr);
                    //once account has created, redirect user to main menu and ask them to sign in
                    System.out.println("Redirecting to main menu, please sign in!");

                }
                //User already has account
                else if (resp.equals("2"))
                {
                    //remind user of their choice
                    System.out.println("Welcome back, please enter your credentials below! ");
                    //ask user for credentials
                    System.out.print("Please enter your username: ");
                    String username = s.nextLine();
                    System.out.print("Please enter your password: ");
                    String password = s.nextLine();

                    UsernamePasswordAuthentication obj = new UsernamePasswordAuthentication();
                    obj.setUsername(username);
                    obj.setPassword(password);

                    usrController.authenticate(obj);


                    //check if user has any planetarium by checking if any planets

                    //infinite loop until logged user decides to quit
                    boolean logged = true;
                    //once logged, display planets of user if they have
                    //only need to do this once, we can use boolean to do so
                    boolean first_iter = true;
                    while(logged) {
                        //print out planets of user once logged in
                        if(first_iter) {
                            planetController.getAllPlanets(loggedInUserId);
                            System.out.println("Now that your logged in, what do you want to do: ");
                            first_iter = false;
                        }
                        //prompt user if they want to add/remove planets and moons
                        System.out.println("Enter 1 to view your planet(s)");
                        System.out.println("Enter 2 to add a new planet to your planetarium");
                        System.out.println("Enter 3 to remove a planet from your planetarium");
                        System.out.println("Enter 4 to view a specific planet's moon(s)");
                        System.out.println("Enter 5 to create a moon for a specific planet");
                        System.out.println("Enter 6 to delete a moon for a specific planet");
                        System.out.println("Or enter 7 to logout");
                        int choice = s.nextInt();
                        //.nextInt() only reads the integer, not the new line character that follows when hitting
                        //enter. Need to add .nextLine() to read the pending new line character
                        s.nextLine();

                        System.out.println("\n");
                        //use switch to toggle choices
                        if(choice == 1) {
                            System.out.print("You choose to view a planet by a given name, whats the name: ");
                            planetController.getAllPlanets(loggedInUserId);
                        }
                        if(choice == 2) {
                            System.out.println("You chose to add a planet to your planetarium!");
                            System.out.print("Now, whats the name of the new planet? ");
                            String newPlanetName = s.nextLine();
                            Planet planet = new Planet();
                            planet.setName(newPlanetName);
                            planetController.createPlanet(loggedInUserId, planet);
                        }
                        if(choice ==3) {
                            System.out.print("You chose to remove a planet from your planetarium, whats the name of the planet you wanted to remove: ");
                            String name = s.nextLine();
                            ///need the id of the planet in order to delete it

                        }
                    }



                }
                else if(resp.equals("q"))
                {
                    System.out.println("Closing program...Goodbye!");
                    userActive = false;
                    //close scanner before program stops
                    s.close();
                }
                else System.out.println("Invalid Choice, please try again");
            }

        }
    }

}
