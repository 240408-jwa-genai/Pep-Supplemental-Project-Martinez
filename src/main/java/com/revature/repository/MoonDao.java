package com.revature.repository;

import java.awt.desktop.PreferencesEvent;
import java.lang.invoke.SwitchPoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.MoonFailException;
import com.revature.models.Moon;
import com.revature.utilities.ConnectionUtil;

public class MoonDao {
    
    public List<Moon> getAllMoons(int userId) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection())
		{
			//need to get all moons that user made, two steps
			//where we only get a table with all planets associated with given user id
			String sql1 = "SELECT * FROM planets WHERE ownerId = ?";
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setInt(1,userId);
			ResultSet rs1 = ps.executeQuery();
			//store resulting moons in array list
			List<Moon> moonList = new ArrayList<>();
			//prepare another query string to get moons for each planet
			String sql2 = "SELECT * FROM moons WHERE myPlanetId = ?";
			//for each resulting planet, we need to get their moons
			while(rs1.next())
			{
				//get the planet id
				int pId = rs1.getInt("id");
				ps = connection.prepareStatement(sql2);
				ps.setInt(1,pId);
				ResultSet rs2 = ps.executeQuery();
				while(rs2.next())
				{
					int mId = rs2.getInt("id");
					String moonName = rs2.getString("name");
					int planetId = rs2.getInt("myPlanetId");

					Moon moon = new Moon();
					moon.setId(mId);
					moon.setName(moonName);
					moon.setMyPlanetId(planetId);
					moonList.add(moon);
				}
			}
			return moonList;
		}catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Moon getMoonByName(String moonName) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT * FROM moons WHERE name = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, moonName);
			ResultSet rs = ps.executeQuery();
			Moon possibleMoon = new Moon();

			if(rs.next())
			{
				int moonId = rs.getInt("id");
				String name = rs.getString("name");
				int planetid = rs.getInt("myPlanetId");

				possibleMoon.setId(moonId);
				possibleMoon.setName(name);
				possibleMoon.setMyPlanetId(planetid);
			}
			return possibleMoon;

		}catch (SQLException e)
		{
			e.printStackTrace();
			return new Moon();
		}
	}

	public Moon getMoonById(int moonId, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT * FROM moons WHERE id = ? AND myPlanetId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,moonId);
			ps.setInt(2, planetId);

			Moon moon = new Moon();
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int idPlanet = rs.getInt("myPlanetId");

				moon.setId(id);
				moon.setName(name);
				moon.setMyPlanetId(idPlanet);
			}
			return moon;
		}catch (SQLException e)
		{
			e.printStackTrace();
			return new Moon();
		}
	}

	public Moon createMoon(Moon m) {

		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "INSERT INTO moons (name,myPlanetId) VALUES (?,?)";
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, m.getName());
			ps.setInt(2, m.getMyPlanetId());

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			Moon newMoon = new Moon();

			newMoon.setName(m.getName());
			newMoon.setMyPlanetId(m.getMyPlanetId());
			if(rs.next()) newMoon.setId(rs.getInt(1));
			return newMoon;
		}catch (SQLException e)
		{
			e.printStackTrace();
			return new Moon();
		}
	}

	public boolean deleteMoonById(int moonId, int planetId) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "DELETE FROM moons WHERE id = ? AND myPlanetId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, moonId);
			ps.setInt(2, planetId);

			int deleted_rows = ps.executeUpdate();
			return deleted_rows > 0;

		}catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}

	}

	public List<Moon> getMoonsFromPlanet(int planetId) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "SELECT * FROM moons WHERE myPlanetId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1,planetId);
			ResultSet rs = ps.executeQuery();
			//use array list to store planets received from query
			List<Moon> moonList = new ArrayList<>();
			while(rs.next())
			{
				Moon moon = new Moon();
				//extract data from result set
				String name = rs.getString("name");
				int moon_id = rs.getInt("id");
				int myPlanetId = rs.getInt("myPlanetId");
				moon.setName(name);
				moon.setId(moon_id);
				moon.setMyPlanetId(myPlanetId);

				//add object to array list
				moonList.add(moon);
			}
			return moonList;
		}catch (SQLException e)
		{
			e.printStackTrace();;
			return null;
		}
	}


}
