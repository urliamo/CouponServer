package com.avi.coupons.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.avi.coupons.logic.UsersController;

public class UsersApi {
	
	@Autowired
	private UsersController usersController;
	
	@PostMapping
	public void login(@RequestBody LoginData loginData) {
		this.usersController.login(user, password)
	}

}
