package Coupons.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import Coupons.Logic.UsersController;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.LoginData;
import Coupons.JavaBeans.LoginForm;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Logic.ICacheManager;

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

}
