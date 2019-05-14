package Coupons.DB;
import java.util.Collection;

import Coupons.JavaBeans.Coupon;


public interface ICouponsDAO {

	long addCoupon(Coupon coupon) throws Exception;
    void updateCoupon(Coupon coupon) throws Exception;   
	Collection<Coupon> getAllCoupons() throws Exception;
    Coupons.JavaBeans.Coupon getOneCoupon(long CouponID) throws Exception;
    void deleteCoupon(long CouponID) throws Exception;
}
