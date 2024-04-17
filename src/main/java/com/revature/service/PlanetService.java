package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Planet;
import com.revature.repository.PlanetDao;

public class PlanetService {

	private PlanetDao dao;

	public PlanetService(PlanetDao dao){
		this.dao = dao;
	}

	public List<Planet> getAllPlanets(int userId) {
		// TODO Auto-generated method stub
		return dao.getAllPlanets(userId);
	}

	public Planet getPlanetByName(int ownerId, String planetName) {
		// TODO Auto-generated method stub

		return dao.getPlanetByName(planetName);
	}

	public Planet getPlanetById(int ownerId, int planetId) {
		// TODO Auto-generated method stub
		Planet p = dao.getPlanetById(planetId,ownerId);
		return p;
	}

	public Planet createPlanet(int ownerId, Planet planet) {
		// TODO Auto-generated method stub
		//first, make sure that the planet name meets criteria
		if(planet.getName().length() <= 30)
		{
			//check if planet name exists
			Planet planetDup = dao.getPlanetByName(planet.getName());
			//if returned object is null, then something went wrong with the request
			if(planetDup != null)
			{
				String nameFromDb = planetDup.getName();
				String givenName = planet.getName();
				//now check if they have the same name
				if(!givenName.equals(nameFromDb))
				{
					//associate planet with current user
					planet.setOwnerId(ownerId);
					//if not create the planet from given planet
					return dao.createPlanet(planet);
				}
			}

		}

		return new Planet();
	}

	public boolean deletePlanetById(int planetId, int ownerId) {
		// TODO Auto-generated method stub
		return dao.deletePlanetById(planetId, ownerId);
	}
}
