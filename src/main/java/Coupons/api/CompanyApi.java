package Coupons.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Coupons.JavaBeans.Company;
import Coupons.Logic.CompanyController;

@RestController
@RequestMapping("/Companies")
public class CompanyApi {
	
	@Autowired
	private CompanyController companyController; 
	
	@PostMapping
	public void createCompany(@RequestBody Company Company) {
		System.out.println(Company);
	}
	
	
	
	
}
