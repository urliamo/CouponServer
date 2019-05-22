package Coupons.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Customer;
import Coupons.Logic.CustomerController;
import Coupons.Logic.ICacheManager;

@RestController
@RequestMapping("/customers")
public class CustomersApi {
	
	@Autowired
	private CustomerController customerController; 
	
	@Autowired
	private ICacheManager cacheManager;
	
	@PostMapping
	public void createCustomer(@RequestBody Customer customer) throws ApplicationException {
		//add new customer to DB
		customerController.createCustomer(customer);
	}
	
	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
		customerController.updateCustomer(customer);
	}
	
	@GetMapping("/{customerId}")
	public Customer getCustomer(@PathVariable("customerId") long customerId) throws ApplicationException {
		Customer customer =customerController.getCustomerDetails(customerId);
		return customer;
	}
	
	@DeleteMapping("/{customerId}")
	public void deleteCoupon(@PathVariable("customerId") long customerId) throws ApplicationException {
		customerController.deleteCustomer(customerId);
	}
	
}
