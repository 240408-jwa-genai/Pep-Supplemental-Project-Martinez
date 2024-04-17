package com.revature.controller;

import com.revature.MainDriver;
import com.revature.models.Planet;
import com.revature.service.PlanetService;

import java.util.List;

public class PlanetController {
	
	private PlanetService planetService;
	private String planet_Name; //used for prompts related to adding/deleting planets

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
			//if user hasn't created planets, prompt user to create planets
			if(temp.size() > 0)
			{
				System.out.println("It seems you have planets, here they are: ");
				for(Planet p: temp)
				{
					System.out.format("Your planet %s has an id of %d\n", p.getName(),p.getId());
				}
			}
			else System.out.println("Either you entered the wrong plant name or you don't have any planets in your planetarium, you should add some!");
			//update number of planets user has
			System.out.println();
			MainDriver.numPlanets = temp.size();

		}
		else System.out.println("Something went wrong when trying to get planets!");
	}

	public void getPlanetByName(int currentUserId, String name) {
		// TODO: implement
		//reset planet id each time this method is called
		MainDriver.currPlanetId = 0;
		Planet p = planetService.getPlanetByName(currentUserId, name);
		if(p.getId()!=0) {
			MainDriver.currPlanetId = p.getId();
			//System.out.format("Your planet %s has an id of %d\n", p.getName(), p.getId());
		}
		else if(p.getId() == 0) System.out.println("You don't have any planets, create one please!");
		else System.out.println("Something went wrong when finding your planet...");

	}

	public void getPlanetByID(int currentUserId, int id) {
		// TODO: implement
		MainDriver.currPlanetId = 0;
		Planet p  =  planetService.getPlanetById(currentUserId, id);
		if(p.getId() != 0) {
			planet_Name = p.getName();
			MainDriver.currPlanetId = p.getId();
		}
		else System.out.println("Something went wrong when getting the planet...");
	}

	public void createPlanet(int currentUserId, Planet planet) {
		// pass data off to service layer
		Planet plt  = planetService.createPlanet(currentUserId,planet);
		if(plt.getId()!=0) System.out.println("You have successfully created a planet!");
		else System.out.println("Something went wrong when creating your planet!");
		System.out.println();

	}

	public void deletePlanet(int currentUserId, int id) {
		//get name of planet to prompt user which planet they deleted
		getPlanetByID(currentUserId,id);
		// if the given planet has moons, prompt user to delete those moons
		if(MainDriver.numMoonsForPlanet == 0) {
			// before deleting moon
			boolean deleted = planetService.deletePlanetById(id, currentUserId);
			//prompt user
			if (deleted) {
				System.out.format("You have successfully deleted the planet %s from your planetarium!\n", planet_Name);
				planet_Name = "";
			} else
				System.out.format("Something went wrong when deleting planet %s from your planetarium...\n", planet_Name);
		}
		else System.out.format("%s has %d moon(s), delete those moons before deleting %s from your planetarium", planet_Name, MainDriver.numMoonsForPlanet, planet_Name);
		System.out.println();
	}
}
