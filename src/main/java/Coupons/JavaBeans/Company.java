package Coupons.JavaBeans;
public class Company {

    private String name;
    private String email;
    private Long company_id;

	public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    	
    }



    public void setCompanyID(Long id) {
        this.company_id = id;
    }

    public String getName() {
        return this.name;
    }



    public String getEmail() {
        return this.email;
    }

    public Long getCompanyID() {
        return this.company_id;
    }
    

    
    public Company(String name, String email, long id) {
		this.setName(name);
		this.setEmail(email);
		this.setCompanyID(company_id);
	}
    public Company(String name, String email) {
  		this.setName(name);
  		this.setEmail(email);
  		this.setCompanyID(null);
  	}
    
    public Company() {
		super();
	}

	
}