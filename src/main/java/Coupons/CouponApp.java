package Coupons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.transaction.annotation.EnableTransactionManagement;

import Coupons.DB.DBCreator;

@SpringBootApplication
//@EnableTransactionManagement
public class CouponApp {
	public static void main(String[] args) {
		DBCreator.buildDB();
		SpringApplication.run(CouponApp.class, args);

	}
}