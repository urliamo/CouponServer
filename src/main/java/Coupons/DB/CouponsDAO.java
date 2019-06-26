package Coupons.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import Coupons.Enums.Category;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.Utils.JdbcUtils;


/**
 * DB data access object for coupons. 
 *
 * @param  connectionPool connection pool assigned to this instance.
 * @see 		JavaBeans.Coupon
 * 
 */
@Repository
public class CouponsDAO implements ICouponsDAO {
	
	
	
	/**
	 *  returns true if coupon exists with this ID
	 *
	 * @param  id the ID of the coupon to be searched
	 * @return		true if coupon exists in DB
	 */
	
	public boolean isCouponExists(long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection =JdbcUtils.getConnection();

			String sql = String.format(
					"SELECT * FROM Coupons WHERE coupon_ID = ?");
	
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,id);

				ResultSet resultSet = preparedStatement.executeQuery();

				if(resultSet.next())
				{
					return true;
				}
				
					return false;
			
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
	 * adds a new coupon to the DB.
	 *<p>
	 *this also generates a new sequential ID for the coupon and adds it to it the coupon Java object.
	 *
	 * @param  coupon the new coupon to be added to the DB
	 * @see			JavaBeans.Coupon
	 */
	
	public long addCoupon(Coupon Coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection =JdbcUtils.getConnection();
			
			
			String sql = String.format("INSERT INTO Coupons(DESCRIPTION, IMAGE, TITLE, AMOUNT, START_DATE, END_DATE, COMPANY_ID, CATEGORY_ID, PRICE) " + 
					"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1,Coupon.getDescription());
			preparedStatement.setString(2,Coupon.getImage());
			preparedStatement.setString(3, Coupon.getTitle());
			preparedStatement.setInt(4,Coupon.getAmount());
			preparedStatement.setDate(5,java.sql.Date.valueOf(Coupon.getStart_date().toString()));
			preparedStatement.setDate(6,java.sql.Date.valueOf(Coupon.getEnd_date().toString()));
			preparedStatement.setLong(7, Coupon.getCompany_id());
			preparedStatement.setString(8,Coupon.getCategory().toString());
			preparedStatement.setDouble(9, Coupon.getPrice());
			
			
				preparedStatement.executeUpdate();

				ResultSet resultSet = preparedStatement.getGeneratedKeys();
					if (!resultSet.next()) {
						long id = resultSet.getInt(1);
						//Coupon.setId(id); // Add the new created id into the Coupon object.
						return id;
						}
						else
						{
						throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to create coupon id", true);
						}
					
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
	 * updates an existing coupon in the DB.
	 *
	 * @param  coupon the  coupon to be added updated in the DB
	 * @see			JavaBeans.Coupon
	 */
	public void updateCoupon(Coupon Coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			
			connection =JdbcUtils.getConnection();

			String sql = String.format(
					"UPDATE Coupons SET DESCRIPTION=?, IMAGE=?, TITLE=?, AMOUNT=?, START_DATE=?, END_DATE=?, CATEGORY_ID=?, PRICE=? WHERE coupon_ID=?");
			
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1,Coupon.getDescription());
			preparedStatement.setString(2,Coupon.getImage());
			preparedStatement.setString(3, Coupon.getTitle());
			preparedStatement.setInt(4,Coupon.getAmount());
			preparedStatement.setDate(5,java.sql.Date.valueOf(Coupon.getStart_date().toString()));
			preparedStatement.setDate(6,java.sql.Date.valueOf(Coupon.getEnd_date().toString()));
			preparedStatement.setString(7,Coupon.getCategory().toString());
			preparedStatement.setDouble(8, Coupon.getPrice());
			preparedStatement.setLong(9, Coupon.getId());
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

	public void changeCouponAmount(long couponId, int amount) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {

			connection =JdbcUtils.getConnection();

			String sql = String.format(
					"UPDATE Coupons SET amount = amount-? where coupon_ID = ?");

			preparedStatement = connection.prepareStatement(sql);	
			preparedStatement.setInt(1,amount);
			preparedStatement.setLong(2,couponId);
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
	
	/*public int getCouponAmount(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		int amount = 0;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT amount from Coupons where couponID = ?");
			preparedStatement = connection.prepareStatement(sql);	
			preparedStatement.setLong(1,couponId);
			resultSet = preparedStatement.executeQuery();
			amount = resultSet.getInt("amount");
			return amount;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} 
		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}*/
	/**
	 * removes a coupon from the DB.
	 * 
	 * @param  couponID the Id of the coupon to be removed from the DB
	 * @see			JavaBeans.Coupon
	 */
	public void deleteCoupon(long CouponID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {

			connection =JdbcUtils.getConnection();

			String sql = String.format("DELETE FROM Coupons WHERE coupon_ID=?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, CouponID);

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

	/**
	 * removes a coupon from the DB.
	 * 
	 * @param  couponID the Id of the coupon to be removed from the DB
	 * @see			JavaBeans.Coupon
	 */
	public void deleteCompanyCoupons(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {

			connection =JdbcUtils.getConnection();

			String sql = String.format("DELETE FROM Coupons WHERE company_ID=?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);

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
	/**
	 * returns all coupons as a list.
	 * 
	 * @see			JavaBeans.Coupon
	 * @return List of all coupons as coupon objects.
	 */
	public List<Coupon> getAllCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection =JdbcUtils.getConnection();

			String sql = "SELECT * FROM Coupons";

			preparedStatement = connection.prepareStatement(sql);

			resultSet = preparedStatement.executeQuery();

					List<Coupon> allCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						Coupon Coupon = extractCouponFromResultSet(resultSet);	
						allCoupons.add(Coupon);
					}
					
					return allCoupons;
			
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
	 * deletes all expired coupons .
	 * 
	 * @see			JavaBeans.Coupon
	 */
	public void deleteExpiredCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection =JdbcUtils.getConnection();

			String sql = "DELETE * FROM Coupons where end_date < NOW()";

			preparedStatement = connection.prepareStatement(sql);

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
	
	/**
	 * returns coupon with specified ID.
	 * 
	 * @param		couponID the ID of the coupon to be returned.
	 * @return 		coupon object with the data of the coupon with specified ID.
	 */
	
	public Coupon getOneCoupon(long CouponID) throws ApplicationException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection =JdbcUtils.getConnection();

			String sql = String.format("SELECT * FROM Coupons WHERE COUPON_ID=?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,CouponID);

				resultSet = preparedStatement.executeQuery();
				resultSet.next();
				Coupon Coupon = extractCouponFromResultSet(resultSet);
				return Coupon;
			
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
	 * returns the category of the coupon with the specified ID.
	 * 
	 * @see			JavaBeans.Coupon
	 * @param		couponID the ID of the coupon to be returned.
	 * @return 		ID of the category of the coupon with specified ID.
	 */
	
	public Category getCouponCategory(int couponID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();

			String sql = String.format("SELECT category FROM coupons WHERE coupon_ID=?");

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1,couponID);

			resultSet = preparedStatement.executeQuery();

			resultSet.next();

			String name = resultSet.getString("NAME");
				    
			Category category = Category.valueOf(name);
					
			return category;
			
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
	
	public List<Coupon> getCustomerCouponsByMaxPrice(long customerID, double maxPrice) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * from purchases JOIN coupons ON coupons.COUPON_ID = purchases.COUPON_ID WHERE CUSTOMER_ID = ? AND PRICE<=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,customerID);
			preparedStatement.setDouble(2,maxPrice);

			resultSet = preparedStatement.executeQuery();

					List<Coupon> customerCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						customerCoupons.add(coupon);
					}
					
					return customerCoupons;
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
	public List<Coupon> getCustomerCouponsByCategory(long customerID, Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * from purchases JOIN coupons ON coupons.COUPON_ID = purchases.COUPON_ID WHERE CUSTOMER_ID = ? AND CATEGORY=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,customerID);
			preparedStatement.setString(2,category.toString());

			resultSet = preparedStatement.executeQuery();

					List<Coupon> customerCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						customerCoupons.add(coupon);
					}
					
					return customerCoupons;
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
	public List<Coupon> getCustomerCoupons(long customerID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * from purchases JOIN coupons ON coupons.COUPON_ID = purchases.COUPON_ID WHERE CUSTOMER_ID = ?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,customerID);

			resultSet = preparedStatement.executeQuery();

					List<Coupon> customerCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						customerCoupons.add(coupon);
					}
					
					return customerCoupons;
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
	public List<Coupon> getAllCouponsByCategory(Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM Coupons WHERE category=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1,category.toString());

			resultSet = preparedStatement.executeQuery();

					List<Coupon> customerCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						customerCoupons.add(coupon);
					}
					
					return customerCoupons;
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
	 * returns all coupons belonging to company with specified ID.
	 * 
	 * @param  companyID the ID of the company whose coupons are to be returned
	 * @return List of all coupon objects belonging to company with specified ID
	 */
	
	
	public List<Long> getCompanyCouponsID(long companyID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM Coupons WHERE company_id=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);

			resultSet = preparedStatement.executeQuery();

					List<Long> companyCoupons = new ArrayList<Long>();
					
					while(resultSet.next()) {
						long couponID = resultSet.getLong("coupon_id");

						companyCoupons.add(couponID);
						
					}
					
					return companyCoupons;
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
	public List<Coupon> getCompanyCoupons(long companyID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM Coupons WHERE company_id=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);

			resultSet = preparedStatement.executeQuery();

					List<Coupon> companyCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						companyCoupons.add(coupon);
					}
					
					return companyCoupons;
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
	public List<Coupon> getCompanyCouponsByCategory(long companyID, Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM Coupons WHERE company_id=? AND category = ?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);
			preparedStatement.setString(2,category.toString());

			resultSet = preparedStatement.executeQuery();

					List<Coupon> companyCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						companyCoupons.add(coupon);
					}
					
					return companyCoupons;
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
	
	public List<Coupon> getCompanyCouponsByMaxPrice(long companyID, double maxPrice) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM Coupons WHERE company_id=? AND price<= ?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);
			preparedStatement.setDouble(2,maxPrice);

			resultSet = preparedStatement.executeQuery();

					List<Coupon> companyCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						companyCoupons.add(coupon);
					}
					
					return companyCoupons;
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
	public List<Coupon> getCompanyCouponsByTitle(long companyID, String title) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection =JdbcUtils.getConnection();
			String sql = String.format("SELECT * FROM Coupons WHERE company_id=? && title=?");
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1,companyID);
			preparedStatement.setString(2,title);


			resultSet = preparedStatement.executeQuery();

					List<Coupon> customerCoupons = new ArrayList<Coupon>();
					
					while(resultSet.next()) {
						
						Coupon coupon = extractCouponFromResultSet(resultSet);

						customerCoupons.add(coupon);
					}
					
					return customerCoupons;
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
	
private Coupon extractCouponFromResultSet(ResultSet result) throws SQLException  {
		
		Coupon coupon = new Coupon(result.getString("description"),result.getString("image"), result.getString("title"),result.getLong("coupon_id"), result.getInt("amount"), result.getDate("start_date"), result.getDate("end_date"), result.getLong("company_id"), Category.valueOf(result.getString("category")), result.getDouble("price"));
	
		return coupon;
	}
	
	

	

}