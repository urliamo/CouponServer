package Coupons.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Company;
import Coupons.Utils.DateUtils;
import Coupons.Utils.JdbcUtils;


/**
 * DB data access object for companies. 
 *
 * @param  connectionPool connection pool assigned to this instance.
 * @see         JavaBeans.Company
 * @see 		JavaBeans.Coupon
 */

@Repository
public class CompaniesDAO implements ICompaniesDAO {
	
	/**
	 * compares input mail and pass to companies DB and returns true if company exists with this combination
	 *
	 * @param  email mail used to search
	 * @param password password used to search
	 * @return		true if company exists in DB
	 */
	
	public boolean isCompanyExists(long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			//create SQL statement

			connection =JdbcUtils.getConnection();

			String sql = String.format(
					"SELECT * FROM COMPANIES WHERE company_ID = ?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,id);
			resultSet = preparedStatement.executeQuery();

				if(!resultSet.next())
				{
					return false;
				}
				return true;
				
				
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	/**
	 * compares input mail and pass to companies DB and get the selected company ID 
	 * 
	 * @throws company does not exist!
	 * @param  email mail used to search
	 * @param password password used to search
	 * @return	int containing the found companyID
	 * @throws InterruptedException 
	 */
	public long getCompanyID(String email) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection =JdbcUtils.getConnection();

			String sql = String.format("SELECT COMPANY_ID FROM COMPANIES WHERE COMPANY_EMAIL");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,email);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			long id = resultSet.getLong("CompanyID");
			return id;
							
				
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	/**
	 * compares input mail and name to companies DB and returns true if company exists with either combination
	 * 
	 * @throws company does not exist!	 
	 * @param  email mail belonging to the company
	 * @param name name of company to be searched 
	 * @return		true if company exists in DB
	 */
	public boolean isCompanyMailExist(String email) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM COMPANIES WHERE company_EMAIL =?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,email);
			ResultSet resultSet = preparedStatement.executeQuery();
			return(resultSet.next());
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	public boolean isCompanyNameExist(String name) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;


		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM COMPANIES WHERE COMPANY_NAME =?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,name);
			ResultSet resultSet = preparedStatement.executeQuery();
			return(resultSet.next());
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 *adds a new company to the DB using the DBDAO.
	 *<p>
	 *this also generates a new sequential ID for the company in the DB and adds it to the input company object.
	 *
	 * @param  company the new company to be added to the DB.
	 * @see 		JavaBeans.Company
	 */
	
	public long addCompany(Company company) throws  ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {

			connection =JdbcUtils.getConnection();

			String sql = String.format("INSERT INTO COMPANIES(COMPANY_EMAIL, COMPANY_NAME) " + 
					"VALUES(?, ?)");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,company.getEmail());
			preparedStatement.setString(2,company.getName());
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			
				if (resultSet.next()) {
					long id = resultSet.getLong(1);
					return id;
				}
				else
				{
				throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to create company id", true);
				}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	/**
	 *updates an existing company in the DB.
	 *
	 * @param  company the company to be updated
	 * @see 		JavaBeans.Company
	 */
	public void updateCompany(Company company) throws  ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection =JdbcUtils.getConnection();

			String sql = String.format("UPDATE COMPANIES SET COMPANY_EMAIL = ? WHERE COMPANY_ID= ?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,company.getEmail());
			preparedStatement.setLong(2,company.getCompanyID());
			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			//If there was an exception in the "try" block above, it is caught here and notifies a level above.
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	 /**removes an existing company from the DB.
	 *
	 * @param  companyID the ID of the company to be removed
	 */

	public void deleteCompany(long companyID) throws ApplicationException  {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection =JdbcUtils.getConnection();

			String sql = String.format("DELETE FROM COMPANIES WHERE COMPANY_ID=?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);

			preparedStatement.executeUpdate();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	/**
	 *	returns an ArrayList of Company objects with all companies.
	 *
	 * @see 		companiesDBDAO
	 * @see 		JavaBeans.Company
	 * @return		ArrayList of all companies
	 */
	public List<Company> getAllCompanies() throws ApplicationException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection =JdbcUtils.getConnection();

			String sql = "SELECT * FROM COMPANIES";

			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

			List<Company> allCompanies = new ArrayList<Company>();
					
			while(resultSet.next()) {
						Company company = extractCompanyFromResultSet(resultSet);
						allCompanies.add(company);
					}
					
			return allCompanies;
				
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	
	/**
	 *	returns a company of the specified ID
	 *
	 * @param		companyID long containing the ID of the company to be returned
	 * @return		Company object with the company data of the specified ID.
	 */
	public Company getCompanyByID(long companyID) throws ApplicationException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection =JdbcUtils.getConnection();

			String sql = String.format("SELECT * FROM COMPANIES WHERE COMPANY_ID=?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			Company company = extractCompanyFromResultSet(resultSet);
			return company;			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);
		} 
		
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
	
	private Company extractCompanyFromResultSet(ResultSet result) throws SQLException {
		Company company = new Company( result.getString("company_name"),result.getString("company_email"), result.getLong("company_ID"));


		return company;

	}
}