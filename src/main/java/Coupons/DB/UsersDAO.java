package Coupons.DB;

import java.math.BigDecimal;
//import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import Coupons.Utils.JdbcUtils;

import Coupons.Enums.ClientType;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.Utils.DateUtils;
//import com.avi.coupons.utils.JdbcUtils;
@Repository
public class UsersDAO {
	

	public long createUser(User user) throws ApplicationException {
		//Turn on the connections
		BigDecimal companyID = null;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet = null;
		try {
			//Establish a connection from the connection manager
			connection=JdbcUtils.getConnection();

			//Creating the SQL query
			//CompanyID is defined as a primary key and auto incremented
			
			String sqlStatement = null;

			
			sqlStatement="INSERT INTO Users (user_name, password,email, type, Company_ID ) VALUES(?,?,?,?,?)";
			

			//Combining between the syntax and our connection
			preparedStatement=connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			//Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1,user.getUserName());
			preparedStatement.setString(2,user.getPassword());
			preparedStatement.setString(3,user.getEmail());
			preparedStatement.setString(4, user.getType().name());
			if (user.getCompanyId()!= null) {
				companyID=BigDecimal.valueOf(user.getCompanyId());
			}
			preparedStatement.setBigDecimal(5, companyID);


			//Executing the update
			preparedStatement.executeUpdate();
			
		    resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				
				return id;
			}
			throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to create user id");
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime() +" Create User failed");
		} 
		finally {
			//Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	public void deleteUserByMail(String userEmail) throws ApplicationException {

		Connection connection=null;
		PreparedStatement preparedStatement=null;


		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM users WHERE email=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setString(1, userEmail);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime() + "failed to delete the user");
		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);		
			}
	}
	
	public void deleteUserByID(long userID) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM users WHERE user_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, userID);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime() + "failed to delete the user");
		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	public void updateUser(User user) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			long userID = user.getId();
			String update = "UPDATE users SET user_Email=?, password=?, client_type=?, company_id=? WHERE user_id=?";
			preparedStatement = connection.prepareStatement(update);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getType().name());
			preparedStatement.setLong(4, user.getCompanyId());
			preparedStatement.setLong(5, userID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime() + "dailed to update user information");
		}  finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public void deleteCompanysUsers(long companyID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM users WHERE company_ID=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, companyID);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException( e, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" Failed delete users");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public Collection<User> getAllUsersByType(ClientType type) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllUsers = "SELECT * FROM users WHERE type=?";
			preparedStatement = connection.prepareStatement(getAllUsers);
			preparedStatement.setString(1, type.name());
			result = preparedStatement.executeQuery();

			Collection<User> allUsers = new ArrayList<User>();

			while (result.next()) {
				allUsers.add(extractUserFromResultSet(result));
			}

			return allUsers;
			}
			catch (SQLException exception)
			{
				exception.printStackTrace();
				throw new ApplicationException( exception, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
						+" get All Users by Type failed");
			} finally
			{
			JdbcUtils.closeResources(connection, preparedStatement);
			}
	}
	
	public Collection<User> getAllUsers() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCompanies = "SELECT * FROM users";
			preparedStatement = connection.prepareStatement(getAllCompanies);
			result = preparedStatement.executeQuery();

			Collection<User> allUsers = new ArrayList<User>();

			while (result.next()) {
				allUsers.add(extractUserFromResultSet(result));
			}

			return allUsers;

		}catch (SQLException exception) {
			exception.printStackTrace();
			throw new ApplicationException( exception, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" get All Users failed");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public User getUserByMail(String email) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCompanies = "SELECT * FROM users where email=?";
			preparedStatement = connection.prepareStatement(getAllCompanies);
			preparedStatement.setString(1, email);
			result = preparedStatement.executeQuery();

			User user = extractUserFromResultSet(result);			

			return user;

		}catch (SQLException exception) {
			exception.printStackTrace();
			throw new ApplicationException( exception, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" get User by Mail failed");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public User getUserByID(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllCompanies = "SELECT * FROM users where user_ID=?";
			preparedStatement = connection.prepareStatement(getAllCompanies);
			preparedStatement.setLong(1, id);
			result = preparedStatement.executeQuery();

			User user = extractUserFromResultSet(result);			

			return user;

		}catch (SQLException exception) {
			exception.printStackTrace();
			throw new ApplicationException( exception, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" get User by ID failed");
		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public UserData login(String userName, String password) throws ApplicationException {
		//Turn on the connections
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet result=null;

		try {
			//Establish a connection from the connection manager
			connection = JdbcUtils.getConnection();

			//Creating the SQL query
			String sqlStatement="SELECT * FROM Users WHERE user_name = ? && password = ?";

			//Combining between the syntax and our connection
			preparedStatement=connection.prepareStatement(sqlStatement);

			//Replacing the question marks in the statement above with the relevant data
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);

			//Executing the query and saving the DB response in the resultSet.
			result=preparedStatement.executeQuery();

			// Login had failed (!!!!!!!!!!!!!!!)
			if (!result.next()) {
				throw new ApplicationException(ErrorType.LOGIN_FAILED, "Failed login");
			}
			UserData loginData = new UserData(result.getLong("user_ID"),userName,ClientType.valueOf(result.getString("type")), result.getLong("company_id"));
			return loginData;
		} catch (SQLException exception) {
			exception.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException( exception, ErrorType.GENERAL_ERROR, DateUtils.getCurrentDateAndTime()
					+" login failed");
		}
		finally {
			//Closing the resources
			JdbcUtils.closeResources(connection, preparedStatement, result);
		}
	}
	
	private User extractUserFromResultSet(ResultSet result) throws SQLException {
		User user = new User();
		user.setId(result.getLong("user_id"));
		user.setUserName(result.getString("user_name"));
		user.setEmail(result.getString("email"));
		user.setPassword(result.getString("password"));
		user.setType(ClientType.valueOf(result.getString("type")));
		user.setCompanyId(result.getLong("company_id"));

		return user;
	}
}
