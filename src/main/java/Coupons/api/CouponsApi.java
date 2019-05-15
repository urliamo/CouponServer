package Coupons.api;

import java.util.Collection;
import java.util.Date;

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

import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.CouponController;
import Coupons.Logic.ICacheManager;
import Coupons.Logic.PurchasesController;
import Coupons.Enums.Categories;
import Coupons.Exceptions.ApplicationException;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {
	
	@Autowired
	private CouponController couponsController; 
	
	@Autowired
	private PurchasesController purchasesController; 
	
	@Autowired
	private ICacheManager cacheManager;
	
	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponsController.addCoupon(coupon);
	}
	
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponsController.updateCoupon(coupon);
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
	
	@GetMapping("/company/")
	public Collection<Coupon> getCompanyCoupons(@RequestParam("token") int token, @RequestParam(value = "category", required = false) Categories category) throws ApplicationException {
		UserData userData =  (UserData) cacheManager.get(token);
		
		if (category != null){
			return couponsController.getCompanyCoupons(userData.getCompany(), category);
		}
		
		return couponsController.getCompanyCoupons(userData.getCompany());
	}
	
	@GetMapping("/customer/")
	public Collection<Coupon> getCustomerCoupons(@RequestParam("token") int token, @RequestParam(value = "category", required = false) Categories category) throws ApplicationException {
		UserData userData =  (UserData) cacheManager.get(token);
		
		if (category != null){
			return purchasesController.getCustomerCoupons(category, userData.getUserID());
		}
		
		return purchasesController.getCustomerCoupons(userData.getUserID());
	}
}
