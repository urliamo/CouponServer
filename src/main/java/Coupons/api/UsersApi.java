package Coupons.api;

import javax.servlet.http.HttpServletRequest;

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
import Coupons.JavaBeans.LoginData;
import Coupons.JavaBeans.LoginForm;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;


@RestController
@RequestMapping("/users")
public class UsersApi {
	
	@Autowired
	private UsersController usersController;
	

	
	@PostMapping("/login")
	public LoginData login(@RequestBody LoginForm loginForm) throws ApplicationException {
		
		return usersController.login(loginForm);
	}
	
	@PostMapping
	public long addUser(@RequestBody User user,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");
		return usersController.createUser(user,userData);
	}
	
	@PutMapping
	public void updateUser(@RequestBody User user,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");

		usersController.updateUser(user,userData);	
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") long id,HttpServletRequest request) throws ApplicationException {
		UserData userData = (UserData) request.getAttribute("userData");

		usersController.deleteUser(id,userData);
	}
	
//	@GetMapping("/{userID}")
//	public User getUser(@PathVariable("userID") long id) throws ApplicationException {
//		User user = usersController.;
//		return user;
//	}

}
