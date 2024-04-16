package com.revature.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.revature.models.Planet;
import com.revature.utilities.ConnectionUtil;

public class PlanetDao {
    
    public List<Planet> getAllPlanets(int userId) {
		// TODO: get all of the planets from DB
		try(Connection connection = ConnectionUtil.createConnection())
		{

			//construct query to get all planets based on userId
			String sql = "SELECT * FROM planets WHERE ownerId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			//inject given id into query
			ps.setInt(1,userId);
			//store in array list
			List<Planet> planetList = new ArrayList<>();
			ResultSet rs = ps.executeQuery();
			//checks to see if there is at least one row
			// .next() moves cursor from current row to next row


				//extract info from result set obj
			while(rs.next()) {
				int id = rs.getInt("id");
				String planetName = rs.getString("name");
				//create planet object based on given info
					//System.out.println(planetName);
				Planet temp = new Planet();
				temp.setId(id);
				temp.setName(planetName);
				planetList.add(temp);
				}

			return planetList;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Planet getPlanetByName(String planetName, int oId) {
		// TODO: implement
		try(Connection connection = ConnectionUtil.createConnection()){
			//create sql query
			String sql = "SELECT * FROM planets WHERE name = ? AND ownerId = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1,planetName);
			ps.setInt(2, oId);

			Planet planet = new Planet();
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				int ownerId = rs.getInt("ownerId");

				planet.setId(id);
				planet.setName(name);
				planet.setOwnerId(ownerId);
			}
			return planet;
		}catch (SQLException e)
		{
			e.printStackTrace();
			return new Planet();
		}
	}

	public Planet getPlanetById(int planetId) {
		// TODO: implement
		return null;
	}

	public Planet createPlanet(Planet p) {
		try(Connection connection = ConnectionUtil.createConnection())
		{
			String sql = "INSERT INTO planets (name,ownerId) VALUES (?,?)";
			//add Statement.RETURN_GENERATED_KEYS to return the generated key, in this case being the id
			//because id is a primary column, sql lite auto increments, and creates, that value
			//when a record gets added
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,p.getName());
			ps.setInt(2, p.getOwnerId());

			ps.executeUpdate(); //use executeUpdate is used for INSERT,DELETE, and UPDATE queries
			//once executed, get the generated keys after updating db
			ResultSet rs = ps.getGeneratedKeys();
			//then we can add properties to new object based on the generated keys
			Planet planet = new Planet();
			planet.setName(p.getName());
			planet.setOwnerId(p.getOwnerId());
			//make sure that a key was generated
			if(rs.next()) planet.setId(rs.getInt(1)); //recall that the first column is generated id
			return planet;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return new Planet();
		}
	}

	public boolean deletePlanetById(int planetId) {
		// TODO: implement
		return false;
	}


}
