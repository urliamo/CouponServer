package Coupons.Logic;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.Enums.Categories;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;


/**
 * object returned when user logs in as Company. in charge of login and DAO actions for companies. 
 *
 * @param  companyID int containing the unique ID of the current company using this object instance.
 * @see         JavaBeans.Company
 * @see 		JavaBeans.Coupon
 * @see			Logic.LoginManager
 */
@Controller

public class CouponController {

	@Autowired
	private Coupons.DB.PurchasesDAO purchasesDAO;
	
	@Autowired
	private Coupons.DB.CompaniesDAO companiesDAO;

	@Autowired
	private Coupons.DB.CouponsDAO couponsDAO;


	public CouponController() {
		super();
	}

	/**
	 * adds a new coupon to the DB.
	 * <p> 
	 * this also checks for coupon validity of date or duplication.
	 *
	 * @param  coupon the new coupon to be added to the DB
	 * @exception coupon already exists
	 * @exception coupon starts after it expired
	 * @exception coupon already expired
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	
	public void addCoupon(Coupon coupon) throws ApplicationException{
				if (couponsDAO.getCompanyCouponsByTitle(coupon.getCompany_id(), coupon.getTitle()) != null) {
					throw new ApplicationException(ErrorType.EXISTING_COUPON_TITLE, ErrorType.EXISTING_COUPON_TITLE.getInternalMessage());
				}
			//check coupon expiration date vs. start date
			if (coupon.getStart_date().isAfter(coupon.getEnd_date())) {
				throw new ApplicationException(ErrorType.COUPON_DATE_MISMATCH, ErrorType.COUPON_DATE_MISMATCH.getInternalMessage());
			}
			if (LocalDate.now().isAfter(coupon.getEnd_date())) {
				throw new ApplicationException(ErrorType.COUPON_ALREADY_EXPIRED, ErrorType.COUPON_ALREADY_EXPIRED.getInternalMessage());
			}
			//add coupon
		couponsDAO.addCoupon(coupon);
		
		
	}
	
	/**
	 * updates an existing coupon in the DB.
	 *
	 * @param  coupon the  coupon to be added updated in the DB
	 * @exception coupon does not exist
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException{
		
			//check coupon with this id exists
			if (!couponsDAO.isCouponExists(coupon.getId()))
				throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage());
				//update coupon
		couponsDAO.updateCoupon(coupon);
	
		
	}
	
	/**
	 * removes a coupon from the DB.
	 * <p>
	 * this also removes any coupons purchased by customers
	 * 
	 * 
	 * @param  coupon the coupon to be removed from the DB
	 * @exception coupon does not exist!
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	public void deleteCoupon(long couponID) throws ApplicationException {
		
			//check if coupon actually exists
			if (!couponsDAO.isCouponExists(couponID)) {
				throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage());
			}
			
		//delete coupon customer purchases
		purchasesDAO.deletePurchaseBycouponId(couponID);
		//delete company coupon
		couponsDAO.deleteCoupon(couponID);
	}
	

	/**
	 * removes a coupon from the DB.
	 * <p>
	 * this also removes any coupons purchased by customers
	 * 
	 * 
	 * @param  coupon the coupon to be removed from the DB
	 * @exception coupon does not exist!
	 * @see 		couponsDAO
	 * @see			JavaBeans.Coupon
	 */
	public void deleteCompanyCoupons(long companyID) throws ApplicationException {
		
			//check if coupon actually exists
			if (companiesDAO.getCompanyByID(companyID)==null) {
				throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
			}
			
		//delete coupon customer purchases
		purchasesDAO.deleteCompanyPurchases(companyID);
		//delete company coupons
		couponsDAO.deleteCompanyCoupons(companyID);
	}
	/**
	 * returns all coupons belonging to this company.
	 * 
	 * @param  coupon the new coupon to be added to the DB
	 * @see 		companiesDAO
	 * @return ArrayList of coupon objects belonging to this company
	 */
	
	public Coupon getCoupon(long couponID) throws ApplicationException{
		if (couponsDAO.getOneCoupon(couponID)==null) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST, ErrorType.COUPON_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		return couponsDAO.getOneCoupon(couponID);
	}
	
	/**
	 * returns a list of all company coupon IDs of a 
	 * 
	 * @param  companyID the ID of the company coupons to return
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	
	public Collection<Long> getCompanyCouponIDs(long companyID) throws ApplicationException{
		if (companiesDAO.getCompanyByID(companyID)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		return couponsDAO.getCompanyCouponsID(companyID);
	}
	
	/**
	 * returns a list of all  coupons of a specified category
	 * 
	 * @param  Category the category of coupons to be returnes
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	public Collection<Coupon> getCouponsByCategory(Categories category) throws ApplicationException
	{
		//get list of all company coupons
		
		Collection<Coupon> coupons = couponsDAO.getAllCouponsByCategory(category);

		//remove coupons with different category from list
		
		return coupons;

	}
	/**
	 * returns a list of all company coupons of a specified category
	 * 
	 * @param  Category the category of coupons to be returnes
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @see			JavaBeans.Category
	 * @return 		ArrayList of coupons
	 */
	public Collection<Coupon> getCompanyCoupons(long companyID, Categories category) throws ApplicationException
	{
		//get list of all company coupons
		if (companiesDAO.getCompanyByID(companyID)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		Collection<Coupon> coupons = couponsDAO.getCompanyCoupons(companyID);

		//remove coupons with different category from list
		for (Coupon c : coupons) {
			if (c.getCategory()!=category)
				coupons.remove(c);
		}
		return coupons;

	}
	/**
	 * returns a list of all company coupons of a specified max price
	 * 
	 * @param  maxprice the highest price of the returned coupons
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @return ArrayList of coupons
	 */
	public Collection<Coupon> getCompanyCoupons(long companyID, double maxprice) throws ApplicationException{
		if (companiesDAO.getCompanyByID(companyID)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		Collection<Coupon> coupons = couponsDAO.getCompanyCoupons(companyID);

		//remove coupons with higher price from returned list
		for (Coupon c : coupons) {
			if (c.getPrice()>maxprice)
				coupons.remove(c);
		}
		return coupons;
	}
	/**
	 * returns a list of all company coupons
	 * 
	 * @param  maxprice the highest price of the returned coupons
	 * @see 		companiesDAO
	 * @see			JavaBeans.Coupon
	 * @return ArrayList of coupons
	 */
	public Collection<Coupon> getCompanyCoupons(long companyID) throws ApplicationException{
		if (companiesDAO.getCompanyByID(companyID)==null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		Collection<Coupon> coupons = couponsDAO.getCompanyCoupons(companyID);

		return coupons;
	}

	
}
