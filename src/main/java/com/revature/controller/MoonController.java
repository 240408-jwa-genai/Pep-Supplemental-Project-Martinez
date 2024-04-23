package com.revature.controller;

import com.revature.MainDriver;
import com.revature.models.Moon;
import com.revature.service.MoonService;
import com.sun.tools.javac.Main;

//import java.lang.foreign.SequenceLayout;
import java.util.List;

public class MoonController {
	
	private MoonService moonService;
	public String currMoonName;

	public MoonController(MoonService moonService) {
		this.moonService = moonService;
	}

	public void getAllMoons(int currentUserId) {
		List<Moon> listMoons = moonService.getAllMoons(currentUserId);
		if(listMoons != null)
		{
			if(listMoons.size() > 0)
			{
				for(Moon m: listMoons)
				{
					System.out.format("%s has an id of %d and its planetId is %d\n", m.getName(), m.getId(), m.getMyPlanetId());
				}
			}
			else System.out.println("Oops...it seems that you don't have any moons...you should create some!");
		}
		else System.out.println("Something went wrong when trying to get your moons...");
		System.out.println();
	}

	public void getMoonByName(int planetId, String name) {
		// TODO: implement
		Moon moon = moonService.getMoonByName(planetId, name);
		if(moon.getId() != 0) System.out.format("Here is your moon %s, it has an id of %d\n", name, moon.getId());
		else System.out.format("Something went wrong when trying to find %s...\n", name);
	}

	public void getMoonById(int currentUserId, int id) {
		// TODO: implement
		Moon moon = moonService.getMoonById(MainDriver.currPlanetId, id);
		if(moon!=null)
		{
			currMoonName = moon.getName();
		}
		else System.out.println("Couldn't find that moon...");
	}

	public void createMoon(int currentUserId, Moon moon) {
		// TODO: implement
		Moon newMoon = moonService.createMoon(moon);
		if(newMoon.getId() != 0)
		{
			System.out.format("You have successfully created a moon, its name is %s with an id of %d and its owned its owned by the planet with an id of %d\n\n", newMoon.getName(), newMoon.getId(),newMoon.getMyPlanetId());
		}
		else System.out.println("Something went wrong when creating your new moon...\n");
	}

	public void deleteMoon(int id) {
		//get the name of the moon to be deleted and prompt user accordingly
		getMoonById(MainDriver.loggedInUserId, id);
		boolean rv = moonService.deleteMoonById(id, MainDriver.currPlanetId);
		if(rv)
		{
			System.out.format("Successfully was able to delete %s from your planetarium!\n", currMoonName);
			currMoonName = "";
		}
		else System.out.format("Something went wrong when trying to delete %s from your planetarium...\n", currMoonName);

		System.out.println("\n");
	}
	
	public void getPlanetMoons(int myPlanetId) {
		// TODO: implement
		List<Moon> temp = moonService.getMoonsFromPlanet(myPlanetId);
		if(temp != null)
		{
			if(temp.size() > 0)
			{
				System.out.println("It seems this planet has moon(s), here they are:  ");
				for(Moon moon: temp)
				{
					System.out.format("Moon name: %s has an Id of %d.\n", moon.getName(),moon.getId());
				}
			}
			else System.out.println("This planet doesn't have a moon!");
			MainDriver.numMoonsForPlanet = temp.size();
		}
		else System.out.println("Something went wrong when trying to get the planet's moon(s)!");

		System.out.println("\n");
	}
}
