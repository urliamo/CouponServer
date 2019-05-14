package Coupons.api;

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
import Coupons.Logic.CouponController;
import Coupons.Enums.Categories;
import Coupons.Exceptions.ApplicationException;

@RestController
@RequestMapping("/coupons")
public class CouponsApi {
	
	@Autowired
	private CouponController couponsController; 
	
	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponsController.addCoupon(coupon);
	}
	
	
	
	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		couponsController.updateCoupon(coupon);
	}
	
	@GetMapping("/{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long id) {
		Coupon coupon = new Coupon(12, "Coca cola", new Date().toString(), new Date().toString(), 100, Categories.groceries,"It will work believe me", 33.4, ":-(", 1111);
		return coupon;
	}
	
	@DeleteMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long id) throws ApplicationException {
		couponsController.deleteCoupon(id);
	}
	
	@GetMapping("/byType")
	// @RequestParam = get the value from the query string
	public void getCouponByType(@RequestParam("type") CouponType couponType) {
		return couponsController.getCouponByType(couponType);
	}
}
