package Coupons.Logic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.User;
import Coupons.Utils.NameUtils;



@Controller

public class CustomerController{
	
	@Autowired
	private Coupons.DB.CustomerDAO customerDAO;

	@Autowired
	private Coupons.DB.UsersDAO usersDAO;
	
	@Autowired
	private Coupons.DB.PurchasesDAO purchasesDAO;
	
	public CustomerController() {
		super();
	}


	/**
	 * returns the DB data of this customer
	 * 
	 * @see 		DB.customerDBDAO
	 * @see			JavaBeans.Customer
	 * @return 		customer object with this customers' data
	 */
	public Customer getCustomerDetails(long customerID) throws ApplicationException{
		return  customerDAO.getOneCustomer(customerID);	
		
	}
	
	public void deleteCustomer(long customerId) {
		try
		{
			purchasesDAO.deleteCustomerPurchases(customerId);
			customerDAO.deleteCustomer(customerId);
			usersDAO.deleteUserByID(customerId);
		}
		catch(Exception Ex){
			 System.out.println(Ex.getMessage());

		}
	}
	
	public void createCustomer(Customer customer) throws ApplicationException {
		try
			{
			if (customer == null) {
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage());
			}
			
			NameUtils.isValidName(customer.getFirstName());
			NameUtils.isValidName(customer.getLastName());
			User customerUser= customer.getUser();
			customer.setCustomerId(usersDAO.createUser(customerUser));
			customerDAO.addCustomer(customer);
			}
		
		catch(Exception Ex){
			System.out.println(Ex.getMessage());
			}

		}
	public void updateCustomer(Customer customer) throws ApplicationException {
		try
			{
			if (customer == null) {
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage());
			}
			
			NameUtils.isValidName(customer.getFirstName());
			NameUtils.isValidName(customer.getLastName());
			customerDAO.updateCustomer(customer);
			}
		
		catch(Exception Ex){
			System.out.println(Ex.getMessage());
			}

		}
}