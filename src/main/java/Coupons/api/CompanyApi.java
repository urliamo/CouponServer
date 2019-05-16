package Coupons.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Company;
import Coupons.Logic.CompanyController;

@RestController
@RequestMapping("/Companies")
public class CompanyApi {
	
	@Autowired
	private CompanyController companyController; 
	
	@PostMapping
	public void createCompany(@RequestBody Company company) throws ApplicationException {
	companyController.addCompany(company);
	}
	
	@DeleteMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId") long id) throws ApplicationException {
	companyController.deleteCompany(id);
	}
	
	
}
