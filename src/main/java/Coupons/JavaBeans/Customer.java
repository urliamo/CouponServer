package Coupons.JavaBeans;

public class Customer {
		private String lastName;
		private String firstName;
	    private long customerId;
	    private User user;
	    
	    public String getLastName() {
			return lastName;
		}
		public void setLastName(String last_name) {
			this.lastName = last_name;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String first_name) {
			this.firstName = first_name;
		}
		
		public long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(long id) {
			this.customerId = id;
		}
		
		public Customer(String lastName, String firstName, long id,User user) {
			super();
			this.setLastName(lastName);
			this.setFirstName(firstName);
			this.setCustomerId(id);
			this.setUser(user);
		}
		
		public Customer(String lastName, String firstName, long id) {
			super();
			this.setLastName(lastName);
			this.setFirstName(firstName);
			this.setCustomerId(id);
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}

		
		
}
