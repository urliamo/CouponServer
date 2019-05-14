package Coupons.Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale.Category;

import org.springframework.stereotype.Controller;

import Coupons.JavaBeans.Customer;

@Controller

public class CustomerController extends ClientController{
	

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
	public Customer getCustomerDetails(long customerID) throws Exception{
		return  customerDBDAO.getOneCustomer(customerID);	}
}
