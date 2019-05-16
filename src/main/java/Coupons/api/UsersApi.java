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

import Coupons.Logic.UsersController;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Coupon;
import Coupons.JavaBeans.LoginData;
import Coupons.JavaBeans.LoginForm;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.ICacheManager;

@RestController
@RequestMapping("/users")
public class UsersApi {
	
	@Autowired
	private UsersController usersController;
	
	@Autowired
	private ICacheManager cacheManager;
	
	@PostMapping
	public void login(@RequestBody LoginForm loginForm) throws ApplicationException {
		LoginData loginData = this.usersController.login(loginForm);
		UserData userData = new UserData(loginData.getUserId(),loginForm.getUserName(), loginForm.getPassword(), loginData.getClientType());
		cacheManager.put(loginData.getToken(), userData);
		
	}
	
	@PostMapping
	public void addUser(@RequestBody User user) throws ApplicationException {
		usersController.createUser(user);
	}
	
	@PutMapping
	public void updateUser(@RequestBody User user) throws ApplicationException {
		usersController.updateUser(user);	
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") long id) throws ApplicationException {
	usersController.deleteUser(id);
	}
	
//	@GetMapping("/{userID}")
//	public User getUser(@PathVariable("userID") long id) throws ApplicationException {
//		User user = usersController.;
//		return user;
//	}

}
