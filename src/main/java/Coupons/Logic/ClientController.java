package Coupons.Logic;

import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.LoginData;


/**
 * Parent class from which other facades inherit.
 *
 * @param  password hard-coded admin password for test
 * @param email hard-coded admin email for test
 * @see         DB.CompaniesDAO
 * @see 		DB.CustomerDAO
 * @see			DB.CouponsDAO
 */
public abstract class ClientController {
	
	protected static  Coupons.DB.CompaniesDAO companiesDBDAO= new Coupons.DB.CompaniesDAO();
	protected  Coupons.DB.CustomerDAO customerDBDAO= new Coupons.DB.CustomerDAO();
	protected static  Coupons.DB.CouponsDAO couponsDBDAO= new Coupons.DB.CouponsDAO();
	protected  Coupons.DB.UsersDAO usersDBDAO= new Coupons.DB.UsersDAO();
	protected static  Coupons.DB.PurchasesDAO purchasesDBDAO= new Coupons.DB.PurchasesDAO();


	/**
	 * login function to be implemented by each facade type.
	 *
	 * @param  mail mail used to login
	 * @param pass password used to login
	 * @throws ApplicationException 
	 * @throws wrong mail\password
	 */
	public LoginData login(String email, String password) throws ApplicationException {
		return usersDBDAO.login(email, password);
	}

}
