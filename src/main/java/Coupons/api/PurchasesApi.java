package Coupons.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Coupons.JavaBeans.Purchase;
import Coupons.JavaBeans.UserData;

import Coupons.Logic.PurchasesController;

@RestController
@RequestMapping("/purchases")
public class PurchasesApi {
	
	@Autowired
	private PurchasesController purchasesController;
	
	@PostMapping
	public void purchaseCoupon(@RequestBody Purchase purchaseData, HttpServletRequest request) {
		UserData userData = (UserData) request.getAttribute("userData");
		this.purchasesController.purchaseCoupon(purchaseData.getCouponID(), userData.getUserID(), purchaseData.getAmount());
	}
	
}
