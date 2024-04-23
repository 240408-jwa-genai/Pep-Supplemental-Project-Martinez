package com.revature.service;

import java.util.List;

import com.revature.MainDriver;
import com.revature.models.Moon;
import com.revature.repository.MoonDao;

public class MoonService {

	private MoonDao dao;

	public MoonService(MoonDao dao) {
		this.dao = dao;
	}

	public List<Moon> getAllMoons(int userId) {
		// TODO implement
		return dao.getAllMoons(userId);
	}

	public Moon getMoonByName(int myPlanetId, String moonName) {
		// TODO implement
		return dao.getMoonByName(moonName);
	}

	public Moon getMoonById(int myPlanetId, int moonId) {
		// TODO Aimplement
		return dao.getMoonById(moonId, myPlanetId);
	}

	public Moon createMoon(Moon m)
	{
		// first check if moon name meets length criteria
		if(m.getName().length() <= 30)
		{
			//make sure moon name is unique
			Moon moonDup = dao.getMoonByName(m.getName());
			//first check if the request went through
			if(moonDup!=null)
			{
				String nameFromDB = moonDup.getName();
				String givenName = m.getName();
				if(!givenName.equals(nameFromDB))
				{
					//set the associated planet id
					m.setMyPlanetId(MainDriver.currPlanetId);

					return dao.createMoon(m);
				}
			}
		}
		else if(m.getName().length() > 30)
		{
			//prompt user that name is too long
			System.out.println("The given name is too long, it needs to be 30 or less characters!");
		}
		return new Moon();
	}

	public boolean deleteMoonById(int moonId, int planetId) {
		return dao.deleteMoonById(moonId,planetId);
	}

	public List<Moon> getMoonsFromPlanet(int myPlanetId) {
		// TODO Auto-generated method stub
		return dao.getMoonsFromPlanet(myPlanetId);
	}
}
