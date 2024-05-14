package com.revature;

<<<<<<< HEAD
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
=======
import java.util.Scanner;

import com.revature.controller.UserController;
import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.repository.UserDao;
>>>>>>> PepSupplementalMartinez/main
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

    //keep track if user has planets and moons for a given planet
    public static int numPlanets = 0;
    public static int currPlanetId = 0;
    public static int numMoonsForPlanet = 0;

    public static int currMoonId = 0;
    public static boolean logged = true;

    public static int loggedInUserId = 0;

    public static UserDao userDao = new UserDao();
    public static UserService userService = new UserService(userDao);
    public static UserController userController = new UserController(userService);

    /*
        An example of how to get started with the registration business and software requirements has been
        provided in this code base. Feel free to use it yourself, or adjust it to better fit your own vision
        of the application. The affected classes are:
            MainDriver
            UserController
            UserService
     */
    public static void main(String[] args) {
        // TODO: implement main method to initialize layers and run the application
<<<<<<< HEAD
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
                        System.out.println("Enter 5 to view all of your moon(s)");
                        System.out.println("Enter 6 to create a moon for a specific planet");
                        System.out.println("Enter 7 to delete a moon for a specific planet");
                        System.out.println("Or enter 8 to logout");
                        int choice = s.nextInt();
                        //.nextInt() only reads the integer, not the new line character that follows when hitting
                        //enter. Need to add .nextLine() to read the pending new line character
                        s.nextLine();

                        System.out.println("\n");
                        //use switch to toggle choices
                        if(choice == 1) {
                            System.out.print("You choose to view your planets!");
                            planetController.getAllPlanets(loggedInUserId);
                            System.out.println();
                        }
                        else if(choice == 2) {
                            System.out.println("You chose to add a planet to your planetarium!");
                            System.out.print("Now, whats the name of the new planet, remember it needs to be unique: ");
                            String newPlanetName = s.nextLine();
                            Planet planet = new Planet();
                            planet.setName(newPlanetName);
                            planetController.createPlanet(loggedInUserId, planet);
                        }
                        else if(choice == 3) {
                            //first check that user has planets before prompting for removal
                            if(numPlanets > 0) {
                                System.out.print("You chose to remove a planet from your planetarium, whats the name of the planet you wanted to remove: ");
                                String name = s.nextLine();
                                ///need the id of the planet in order to delete it
                                planetController.getPlanetByName(loggedInUserId, name);
                                //also need to check if planet has moons before deleting it
                                //by calling moonController
                                moonController.getPlanetMoons(currPlanetId);
                                if(numMoonsForPlanet == 0)
                                {
                                    planetController.deletePlanet(loggedInUserId, currPlanetId);
                                }
                                else System.out.format("%s has %d moon(s), delete those moons before deleting %s from your planetarium\n\n", name, numMoonsForPlanet, name);
                            }
                            else System.out.println("You do not have any planets to remove!");

                        }
                        else if(choice == 4)
                        {
                            System.out.println("You chose to view a specific planet's moon!");
                            System.out.print("Enter the name of the planet: ");
                            String name = s.nextLine();
                            //update current planet id using controller method
                            planetController.getPlanetByName(loggedInUserId, name);
                            //now we can call controller to get moons
                            moonController.getPlanetMoons(currPlanetId);
                        }

                        else if(choice == 5)
                        {
                            System.out.println("You choose to view all of your moons!");
                            System.out.println("Here they are: ");
                            moonController.getAllMoons(loggedInUserId);
                        }

                        else if(choice == 6)
                        {
                            System.out.println("You choose to create a moon!");
                            System.out.print("Whats the name of the planet that your new moon will orbit: ");
                            String planetName = s.nextLine();
                            System.out.print("Whats the name of the new moon: ");
                            String moonName = s.nextLine();

                            //update current planet id
                            planetController.getPlanetByName(loggedInUserId, planetName);
                            //make sure given planet actually exists
                            if(currPlanetId != 0) {
                                //now create a moon for that planet if it exists
                                Moon moon = new Moon();
                                moon.setName(moonName);
                                moonController.createMoon(loggedInUserId, moon);
                            }
                        }

                        else if(choice == 7)
                        {
                            System.out.println("You chose to delete a moon!");
                            //remind user of the moons they have for a given planet
                            System.out.print("Whats the name of the planet that the moon orbits: ");
                            String planetName = s.nextLine();
                            planetController.getPlanetByName(loggedInUserId, planetName);
                            //make sure that there are moons in the given planet and it exists
                            if(currPlanetId != 0) {
                                //make sure there are moons for that planet
                                if(numMoonsForPlanet != 0)
                                {
                                    moonController.getPlanetMoons(currPlanetId);
                                    System.out.print("Now which moon did you want to delete, please enter its id: ");
                                    int moonId = s.nextInt();
                                    s.nextLine();
                                    moonController.deleteMoon(moonId);
                                }
                            }
                        }
                        else if(choice == 8)
                        {
                            System.out.println("You chose to logout, please come back soon! ");
                            usrController.logout();
                            logged = false;
                        }
                        else System.out.println("Please enter a valid choice...");

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
=======

        // We are using a Try with Resources block to auto close our scanner when we are done with it
        try (Scanner scanner = new Scanner(System.in)){
            /*
                The userIsActive boolean is used to control our code loop. While the user is "active" the code
                will loop. When prompted, the user can enter "q" to quit the program
             */
            boolean userIsActive = true;
            while (userIsActive){
                System.out.println("\nHello! Welcome to the Planetarium! Enter 1 to register an account, 2 to log in, q to quit");
                String userChoice = scanner.nextLine();
                if (userChoice.equals("1")){
                    // remind the user of the choice they made
                    System.out.println("You chose to register an account!");

                    // get the prospective username of the new user
                    System.out.print("Please enter your desired username: ");
                    String potentialUsername = scanner.nextLine();

                    // get the prospective password of the new yser
                    System.out.print("Please enter your desired password: ");
                    String potentialPassword = scanner.nextLine();

                    // create a User object and provide it with the username and password
                    // keep in mind the id will be set by the database if the username and password
                    // are valid
                    User potentialUser = new User();
                    potentialUser.setUsername(potentialUsername);
                    potentialUser.setPassword(potentialPassword);

                    // pass the data into the service layer for validation
                    userController.register(potentialUser);

                } else if (userChoice.equals("2")){
                    System.out.println("\nYou chose to log in!");
                    System.out.print("Please enter your username: ");
                    String username = scanner.nextLine();

                    System.out.print("Please enter your password: ");
                    String password = scanner.nextLine();

                    UsernamePasswordAuthentication credentials = new UsernamePasswordAuthentication();
                    credentials.setUsername(username);
                    credentials.setPassword(password);

                    userController.authenticate(credentials);


                } else if (userChoice.equals("q")) {
                    System.out.println("Goodbye!");
                    userIsActive = false;
                } else {
                    System.out.println("Invalid choice, please try again");
                }
            }

        }


>>>>>>> PepSupplementalMartinez/main
    }

}
