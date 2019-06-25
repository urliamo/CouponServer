package Coupons.JavaBeans;

import Coupons.Enums.ClientType;

public class User {
	
	private Long userId;
	private String email;
	private String userName;
	private String password;
	private ClientType type;
	private Long companyId;
	
	public User(long id,String email, String userName, String password, Long companyId, ClientType clientType) {
		this(userName, email, password,id, clientType, companyId);
		this.userId = id;
	}
	
	/*public User(String userName,String email, String password, Long companyId, ClientType type) {
		this(userName,email, password, type);
		this.companyId = companyId;
	}*/
	public User(String userName, String email, String password, String clientType, Long companyId) {
		this();
		this.email= email;
		this.userName = userName;
		this.password = password;
		this.companyId = null;
		this.type = ClientType.valueOf(clientType);
	}
	public User(String userName, String email, String password,Long userId, ClientType clientType, Long companyId) {
		this();
		this.email= email;
		this.userName = userName;
		this.password = password;
		this.companyId = null;
		this.type = clientType;
		this.userId = null;
	}
	
	public User() {
		super();
	}

	public long getId() {
		return userId;
	}

	public void setId(Long id) {
		this.userId = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getCompanyId() {
		
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public ClientType getType() {
		return type;
	}

	public void setType(ClientType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
