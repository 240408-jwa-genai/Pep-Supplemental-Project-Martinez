package com.revature.repository;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

import java.sql.*;

public class UserDao {
    
    public User getUserByUsername(String username){
<<<<<<< HEAD
        // TODO: implement
        //fetch given username from DB
        //if user exit, create User obj with provided credentials

        //we should wrap our COnnection in try catch bloc so it autocloses
        // when method is finished
        try(Connection connection = ConnectionUtil.createConnection())
        {
            //first we need to make a sql query
            String sql = "SELECT * FROM users WHERE username = ?";
            // next we need to create a Prepared Statement and pass our SQL statement into it
            PreparedStatement ps = connection.prepareStatement(sql);
            //now that we prepared statement is created, we can inject username into sql
            //indexing in databses start at 1
            ps.setString(1, username);
            //once we fully prepared statement, we can execute it with
            // executeQuery
            //create result set object to have access to resulting info
            User possibleUser = new User();
            ResultSet rs = ps.executeQuery();
            //use .next() to iterate through result set if data exists
            //if data doesnt exist then method returns false

            if(rs.next())
            {
                int ruserId = rs.getInt("id"); //can put column number instead
                String rname = rs.getString("username");
                String rpassword = rs.getString("password");

                possibleUser.setUsername(rname);
                possibleUser.setPassword(rpassword);
                possibleUser.setId(ruserId);

            }
            return possibleUser;
        } catch (SQLException e)
        {
            //TODO: handle sql exeption
            e.printStackTrace();
            return new User();
        }

        //else return an empty user object
        //
        //return null for now
    }

    public User createUser(UsernamePasswordAuthentication registerRequest) {
        try(Connection connection = ConnectionUtil.createConnection())
        {
            String sql = "INSERT INTO users (username,password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,registerRequest.getUsername());
            ps.setString(2,registerRequest.getPassword());
            //to execute our sql statement and generate id for new user we have to do two things
            //1) call executeUpdate() because it will tell you how many rows are affected by query
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            //once query is executed we can use generated keys to get the id of newly
            //created user

            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            if(rs.next())
            {
                newUser.setId(rs.getInt(1)); //referencing first column of result
                //return newUser;
            }
            return newUser;
        } catch (SQLException e)
        {
                e.printStackTrace();
                return new User();
        }
    }


    public static void main(String[] args)
    {
        UserDao dao = new UserDao();
        UsernamePasswordAuthentication cred = new UsernamePasswordAuthentication();
        cred.setUsername("David");
        cred.setPassword("nooo");
        System.out.println(cred.getUsername());
=======
        // we should wrap our Connection object in a try with resources block so it auto-closes when the method
        // is finished
        try (Connection connection = ConnectionUtil.createConnection()){
            // first we need to craft our sql statement
            String sql = "SELECT * FROM users WHERE username = ?";
            // next we create a PreparedStatement and pass our SQL statement into it
            PreparedStatement ps = connection.prepareStatement(sql);
            // now that the PreparedStatement is created we can inject our username into the SQL
            ps.setString(1, username);
            // once the sql statement is fully prepared we can execute it with the executeQuery method
            /*
                the executeQuery method will return a "ResultSet" object that holds any data taken from
                the database. We can use the "next" method of the resultSet to start accessing that data, if
                that data exists. If the data does not exist then the method will return a false boolean and
                we can write our code to respond accordingly
             */
            User possibleUser = new User(); // creating the user to be returned here
            ResultSet rs =  ps.executeQuery();
            if(rs.next()){
                // if the next method returns true then there is data we can put into our User object
                int retrievedId = rs.getInt("id"); // I could put the column number instead
                String retrievedUsername = rs.getString("username");
                String retrievedPassword = rs.getString("password");
                possibleUser.setId(retrievedId);
                possibleUser.setUsername(retrievedUsername);
                possibleUser.setPassword(retrievedPassword);
            }
            return possibleUser;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public User createUser(UsernamePasswordAuthentication registerRequest) {
        try (Connection connection = ConnectionUtil.createConnection()){
            // craft initial sql
            String sql = "INSERT INTO users (username, password) VALUES (?,?)";
            // create preppared statement
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // the second argument tells the database to return the id that is generated for the new record
            // inject information into our sql statement, order is determined by sql
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());
            // to execute our sql statement AND get the id generated for the new user we have a two-step process
            // step one: call executeUpdate (use this method for "INSERT", "UPDATE" and "DELETE" queries because
            // it will tell you how many rows are affected by your query
            ps.executeUpdate();
            // once the query is executed we can use the getGeneratedKeys method to get the id of the newly
            // created user
            ResultSet rs = ps.getGeneratedKeys();
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            if(rs.next()){
                // since we are only returning the generated id, we can get it by referencing column 1
                newUser.setId(rs.getInt(1));
            }
            return newUser;
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        UserDao dao = new UserDao();
        UsernamePasswordAuthentication newCreds = new UsernamePasswordAuthentication();
        newCreds.setUsername("new user");
        newCreds.setPassword("new pass");
        User returnedUser = dao.createUser(newCreds);
        System.out.println(returnedUser);

>>>>>>> PepSupplementalMartinez/main
    }

}
