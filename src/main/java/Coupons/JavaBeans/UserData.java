package Coupons.JavaBeans;


import Coupons.Enums.ClientType;


public class UserData {
	
	private long userID;
	private String userName;
	private Customer userCustomer;
	private ClientType type;
	private Long companyID;
	
	public UserData(long userID, String userName,  ClientType type, Customer customer) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setType(type);
		this.setCompanyID(null);
		this.setUserCustomer(customer);

	}
	
	public UserData(long userID,String userName, ClientType type, long company) {
		this.setUserID(userID);
		this.setUserName(userName);
		this.setType(type);
		this.setCompanyID(company);
		this.setUserCustomer(null);
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

	public Long getCompany() {
		return companyID;
	}

	public void setCompanyID(Long companyID) {
		this.companyID = companyID;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public Customer getUserCustomer() {
		return userCustomer;
	}

	public void setUserCustomer(Customer userCustomer) {
		this.userCustomer = userCustomer;
	}

	
	
	

}
