package Coupons.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Coupons.Enums.Categories;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.CouponController;
import Coupons.Logic.CustomerController;
import Coupons.Logic.ICacheManager;
import Coupons.Logic.PurchasesController;
import Coupons.Logic.UsersController;

@RestController
@RequestMapping("/customers")
public class CustomersApi {
	
	@Autowired
	private CustomerController customerController; 
	
	@Autowired
	private PurchasesController purchasesController; 
	
	@Autowired
	private ICacheManager cacheManager;
	
	@PostMapping
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException {
		//add new customer to DB
		customerController.createCustomer(customer);
	}
	
	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
		customerController.;
	}
	
	@GetMapping("/{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		Coupon coupon = couponsController.getCoupon(id);
		return coupon;
	}
	
	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		couponsController.deleteCoupon(id);
	}
	@GetMapping("/byType")
	public Collection<Coupon> getCouponByType(@PathVariable("type") Categories category) throws ApplicationException {
		return couponsController.getCouponsByCategory(category);
	}
	
	@GetMapping("/byCompany/")
	public Collection<Coupon> getCompanyCoupons(@RequestParam("token") int token, @RequestParam(value = "category", required = false) Categories category) throws ApplicationException {
		UserData userData =  (UserData) cacheManager.get(token);
		
		if (category != null){
			return couponsController.getCompanyCoupons(userData.getCompany(), category);
		}
		
		return couponsController.getCompanyCoupons(userData.getCompany());
	}
	
	@GetMapping("/byCustomer/")
	public Collection<Coupon> getCustomerCoupons(@RequestParam("token") int token, @RequestParam(value = "category", required = false) Categories category) throws ApplicationException {
		UserData userData =  (UserData) cacheManager.get(token);
		
		if (category != null){
			return purchasesController.getCustomerCoupons(category, userData.getUserID());
		}
		
		return purchasesController.getCustomerCoupons(userData.getUserID());
	}
}
