package Coupons.JavaBeans;


import Coupons.Enums.ClientType;


public class UserData {
	
	private long userID;
	private String userName;
	private String password;
	private ClientType type;
	private Long companyID;
	
	public UserData(long userID, String userName, String password, ClientType type) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setPassword(password);
		this.type = type;
		this.setCompany(null);
	}
	
	public UserData(long userID,String userName, String password, ClientType type, long company) {
		this.setUserID(userID);
		this.userName = userName;
		this.password = password;
		this.type = type;
		this.setCompany(company);
	}

	public String getUserName() {
		return userName;
	}
	
	
	public void setUserName(String userId) {
		this.userName = userId;
	}
	
	public ClientType getType() {
		return type;
	}
	public void setType(ClientType type) {
		this.type = type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String companyID) {
		this.password = password;
	}

	public Long getCompany() {
		return companyID;
	}

	public void setCompany(Long companyID) {
		this.companyID = companyID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	
	
	

}
