package Coupons.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import Coupons.Utils.JdbcUtils;

import Coupons.JavaBeans.Purchase;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.Utils.DateUtils;
@Repository
public class PurchasesDAO {

	/**
	 *  returns true if coupon  with specified ID belonging to customer with specified ID exists
	 *
	 * @param  coupondId the ID of the coupon to be searched
	 * @param  customerId the ID of the customer to be searched
	 * @return		true if coupon belongs to customer
	 */
	
	public boolean isCouponPurchaseExists(long customerID,long couponID) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
				connection = JdbcUtils.getConnection();

				String sql = String.format(
					"SELECT *  FROM purchases WHERE CUSTOMER_ID = ? AND COUPON_ID = ?",
					customerID,couponID);

				preparedStatement = connection.prepareStatement(sql);

				ResultSet resultSet = preparedStatement.executeQuery();

				if(!resultSet.next())
				{
						throw new ApplicationException(ErrorType.INVALID_ID,"purchase does not exist!", true);
				}
				else
				{
				return true;
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

	
	public Collection<Purchase> getAllPurchasesByCoupon(long couponID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllPurchasesByCoupon = "SELECT * FROM purchases WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(getAllPurchasesByCoupon);
			preparedStatement.setLong(1, couponID);
			result = preparedStatement.executeQuery();

			Collection<Purchase> allPurchasesByCoupon = new ArrayList<Purchase>();

			while (result.next()) {
				allPurchasesByCoupon.add(extractPurchaseFromResultSet(result));
			}

			return allPurchasesByCoupon;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		}  finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	
	public Collection<Purchase> getAllPurchasesbyCustomer(long customerID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllPurchasesByCustomer = "SELECT * FROM purchases WHERE customer_id=?";
			preparedStatement = connection.prepareStatement(getAllPurchasesByCustomer);
			preparedStatement.setLong(1, customerID);
			result = preparedStatement.executeQuery();

			Collection<Purchase> allPurchasesByCustomer = new ArrayList<Purchase>();

			while (result.next()) {
				allPurchasesByCustomer.add(extractPurchaseFromResultSet(result));
			}

			return allPurchasesByCustomer;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		}  finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	
	public void deleteCouponPurchase(long couponID, long customerID) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {

			connection = JdbcUtils.getConnection();
			String sql;
			
			//set string for all customers with coupon
			if (customerID==-1)
			{
				 sql = String.format("DELETE FROM PURCHASES WHERE COUPON_ID=?", couponID);

			}
			//set string for all coupons of customer
			else if(couponID==-1) {
				 sql = String.format("DELETE FROM PURCHASES WHERE CUSTOMER_ID=?", customerID); 
			}
			//set string for single customer coupon
			else {
			 sql = String.format("DELETE FROM PURCHASES WHERE CUSTOMER_ID=? AND COUPON_ID=?", customerID, couponID);
			}
			preparedStatement = connection.prepareStatement(sql);
			if (customerID==-1)
			{
				preparedStatement.setLong(1,couponID);

			}
			//set string for all coupons of customer
			else if(couponID==-1) {
				preparedStatement.setLong(1,customerID);
			}
			//set string for single customer coupon
			else {
				preparedStatement.setLong(1,customerID);
				preparedStatement.setLong(2,couponID);

			}

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
	 * adds a new coupon purchase to the customer.
	 *
	 * @param couponID the ID of the coupon to be purchased by the customer
	 * @param customerID the ID of the customer the coupon should be added to
	 */
	public long addCouponPurchase(long CouponID, long CustomerID, int amount) throws ApplicationException {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
				connection = JdbcUtils.getConnection();
				String sql = String.format("INSERT INTO CUSTOMERS_VS_COUPONS(CUSTOMER_ID, COUPON_ID, AMOUNT) " + "VALUES(?, ?, ?)");
				preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setLong(1,CustomerID);
				preparedStatement.setLong(2,CouponID);
				preparedStatement.setInt(3,amount);
				preparedStatement.executeUpdate();
				resultSet = preparedStatement.getGeneratedKeys();
				if (resultSet.next()) {
					long id = resultSet.getLong(1);
					
					return id;
				}
				else
				{
				throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to create purchase id", true);
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
	
	public void deletePurchaseBycouponId(long couponId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM purchases WHERE coupon_id=?";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	public Collection<Purchase> getAllPurchases() throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;

		try {
			connection = JdbcUtils.getConnection();
			String getAllPurchases = "SELECT * FROM purchases";
			preparedStatement = connection.prepareStatement(getAllPurchases);
			result = preparedStatement.executeQuery();

			Collection<Purchase> allPurchases = new ArrayList<Purchase>();

			while (result.next()) {
				allPurchases.add(extractPurchaseFromResultSet(result));
			}

			return allPurchases;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	
	private Purchase extractPurchaseFromResultSet(ResultSet result) throws SQLException{
		Purchase purchase = new Purchase();
		purchase.setPurchaseID(result.getLong("purchase_id"));
		purchase.setCustomerID(result.getLong("customer_id"));
		purchase.setCouponID(result.getLong("coupon_id"));
		purchase.setAmount(result.getInt("amount"));
		return purchase;
	}

	public void deleteCustomerPurchases(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM purchases WHERE customer_id=?)";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	public void deleteCompanyPurchases(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			connection = JdbcUtils.getConnection();
			String delete = "DELETE FROM purchases WHERE coupon_id IN (select coupon_id from coupons where company_id=?)";
			preparedStatement = connection.prepareStatement(delete);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(ErrorType.GENERAL_ERROR, ErrorType.GENERAL_ERROR.getInternalMessage(), true, e);

		} finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
}
