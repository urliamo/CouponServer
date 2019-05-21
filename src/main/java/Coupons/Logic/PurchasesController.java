package Coupons.Logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.Enums.Categories;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Purchase;

@Controller

public class PurchasesController {
	
	
	@Autowired
	private static Coupons.DB.CouponsDAO couponsDAO;

	@Autowired
	private static Coupons.DB.PurchasesDAO purchasesDAO;

	@Autowired
	private static Coupons.DB.CustomerDAO customerDAO;

	public PurchasesController() {
	}


	/**
	 * adds a new coupon puchase to the customer
	 * <p>
	 * this also checks the coupon is available, not out of date and is not already owned by the customer.
	 *
	 * @param coupon the coupon to be purchased by the customer
	 * @see 		DB.couponsDAO
	 * @throws		coupon out of stock!
	 * @throws		coupon out of date!
	 * @throws 		coupon already owned by customer!
	 */
	
public void purchaseCoupon(long couponID, long customerID, int amount) {
	try
	{
		if (customerDAO.getOneCustomer(customerID)==null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage());

		}
		if (!couponsDAO.isCouponExists(couponID)) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage());

		}
		
		Coupon couponDB = couponsDAO.getOneCoupon(couponID);
		if (couponDB.getAmount()<1)
			throw new Exception("coupon out of stock");
		if (LocalDate.now().isAfter(couponDB.getEnd_date()))
			throw new Exception("Coupon out of date");
		
		purchasesDAO.addCouponPurchase(couponID, customerID, amount);
		couponDB.setAmount(couponDB.getAmount()-1);
		couponsDAO.updateCoupon(couponDB);

	}
	catch(Exception Ex){
		 System.out.println(Ex.getMessage());

	}
}

public static void deleteCustomerPurchases(long customerId) {
	try
	{
		if (customerDAO.getOneCustomer(customerId)==null) {
			throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage());

		}
		purchasesDAO.deleteCustomerPurchases(customerId);

	}
	catch(Exception Ex){
		 System.out.println(Ex.getMessage());

	}
}

public static void deleteCompanyPurchases(long companyId) {
	try
	{
		if (Coupons.Logic.CompanyController.getCompany(companyId)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());

		}
		purchasesDAO.deleteCompanyPurchases(companyId);

	}
	catch(Exception Ex){
		 System.out.println(Ex.getMessage());

	}
}

public static void deleteCouponPurchases(long couponId) {
	try
	{
		
		purchasesDAO.deletePurchaseBycouponId(couponId);

	}
	catch(Exception Ex){
		 System.out.println(Ex.getMessage());

	}
}
/**
 * returns a list of all customer coupons 
 * @see 		DB.CouponDAO
 * @see			JavaBeans.Coupon
 * @see			DB.CustomerDAO
 * @return 		ArrayList of coupons belonging to this customer	
 * @throws 		customer has no coupons
 */
	public Collection<Coupon> getCustomerCoupons(long customerID) {
		ArrayList<Coupon> customerCoupons = new ArrayList<Coupon>();

		try {
			if (customerDAO.getOneCustomer(customerID)==null) {
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage());

			}
			ArrayList<Purchase> customerPurchases = (ArrayList<Purchase>) purchasesDAO.getAllPurchasesbyCustomer(customerID);
			if (customerPurchases.size()==0)
				throw new Exception("Customer has no coupons");
			for (Purchase purchase : customerPurchases) {
				customerCoupons.add(couponsDAO.getOneCoupon(purchase.getCouponID()));
			}
		}
		catch(Exception Ex){
			 System.out.println(Ex.getMessage());

		}
		return customerCoupons;

	}
	/**
	 * returns a list of all customer coupons of a specified category
	 * 
	 * @param  Category the category of coupons to be returnes
	 * @see 		DB.customerDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons belonging to this customer of specified category
	 * @throws 		customer has no coupons
	 */
	
	public Collection<Coupon> getCustomerCoupons(Categories category, long customerID) {
		ArrayList<Coupon> customerCoupons = new ArrayList<Coupon>();

		try {
			if (customerDAO.getOneCustomer(customerID)==null) {
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage());

			}
			ArrayList<Purchase> customerPurchases = (ArrayList<Purchase>) purchasesDAO.getAllPurchasesbyCustomer(customerID);
			if (customerPurchases.size()==0)
				throw new Exception("Customer has no coupons");
			for (Purchase purchase : customerPurchases) {
				customerCoupons.add(couponsDAO.getOneCoupon(purchase.getCouponID()));
			}
			for (Coupon c : customerCoupons) {
				if (c.getCategory()!=category)
					customerCoupons.remove(c);
			}
		}
		catch(Exception Ex){
			 System.out.println(Ex.getMessage());

		}
		return customerCoupons;

	}
	
	/**
	 * returns a list of all customer coupons lower than input price
	 * 
	 * @param  maxprice the maximum price of returned coupons
	 * @see 		DB.customerDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons belonging to this customer of limited price
	 * @throws 		customer has no coupons
	 */
	public Collection<Coupon> getCustomerCoupons(double maxPrice, long customerID) {
		ArrayList<Coupon> customerCoupons = new ArrayList<Coupon>();

		try {
			if (customerDAO.getOneCustomer(customerID)==null) {
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST, ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage());

			}
			ArrayList<Purchase> customerPurchases = (ArrayList<Purchase>) purchasesDAO.getAllPurchasesbyCustomer(customerID);
			if (customerPurchases.size()==0)
				throw new Exception("Customer has no coupons");
			for (Purchase purchase : customerPurchases) {
				customerCoupons.add(couponsDAO.getOneCoupon(purchase.getCouponID()));
			}
			for (Coupon c : customerCoupons) {
				if (c.getPrice()>maxPrice)
					customerCoupons.remove(c);
			}
		}
		catch(Exception Ex){
			 System.out.println(Ex.getMessage());

		}
		return customerCoupons;
	}
	
}
	