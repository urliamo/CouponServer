package Coupons.JavaBeans;

import Coupons.Enums.ClientType;

public class LoginData {
	
	private long userId;
	private Integer token;
	private ClientType clientType;
	
	public LoginData(long userId, int token, ClientType clientType) {
		this.setClientType(clientType);
		this.setUserId(userId);
		this.setToken(token);
	}
	
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getToken() {
		return token;
	}
	public void setToken(int token) {
		this.token = token;
	}


	public ClientType getClientType() {
		return clientType;
	}


	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

}
