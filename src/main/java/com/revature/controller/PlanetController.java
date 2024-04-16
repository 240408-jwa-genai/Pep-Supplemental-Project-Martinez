package com.revature.controller;

import com.revature.MainDriver;
import com.revature.models.Planet;
import com.revature.service.PlanetService;

import java.util.List;

public class PlanetController {
	
	private PlanetService planetService;

	public PlanetController(PlanetService planetService){
		this.planetService = planetService;
	}

	public void getAllPlanets(int currentUserId) {
		// TODO: implement
		//pass onto service layer
		List<Planet> temp = planetService.getAllPlanets(currentUserId);
		// first check if returned list is null
		if(temp != null)
		{
			//if user hasnt created planets, prompt user to create planets
			if(temp.size() > 0)
			{
				System.out.println("It seems you have planets, here they are: ");
				for(Planet p: temp)
				{
					System.out.format("Your planet %s has an id of %d\n", p.getName(),p.getId());
				}
			}
			else System.out.println("You don't have any planets in your planetarium, you should add some!");
			//update number of planets user has
			System.out.println();
			MainDriver.numPlanets = temp.size();

		}
		else System.out.println("Something went wrong when trying to get planets!");
	}

	public void getPlanetByName(int currentUserId, String name) {
		// TODO: implement
		Planet p = planetService.getPlanetByName(currentUserId, name);
		if(p.getId()!=0) System.out.format("Your planet %s has an id of %d\n", p.getName(),p.getId());
		else if(p.getId() == 0) System.out.println("You don't have any planets, create one please!");
		else System.out.println("Something went wrong when finding your planet...");

	}

	public void getPlanetByID(int currentUserId, int id) {
		// TODO: implement
	}

	public void createPlanet(int currentUserId, Planet planet) {
		// pass data off to service layer
		Planet plt  = planetService.createPlanet(currentUserId,planet);
		if(plt.getId()!=0) System.out.println("You have successfully created a planet!");
		else System.out.println("Something went wrong when creating your planet!");
		System.out.println();

	}

	public void deletePlanet(int currentUserId, int id) {
		// if the given planet has moons, prompt user to delete those moons
		// before deleting moon

		//else delete planet
	}
}
