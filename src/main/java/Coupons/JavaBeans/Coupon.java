package Coupons.JavaBeans;

//import java.sql.Date;
import java.util.Date;
//import java.util.Date;

import Coupons.Enums.Category;

public class Coupon {

	private long companyId;
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private double price;
	private String image;
	private Long couponId;

    
    
    //----------Setters & Getters-----------------------//
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getId() {
		return couponId;
	}
	public void setId(Long id) {
		this.couponId = id;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getStart_date() {
		return startDate;
	}
	public void setStart_date(Date start_Date) {
		
		this.startDate = start_Date;
	}
	public Date getEnd_date() {
		return endDate;
	}
	public void setEnd_date(Date end_date) {
		this.endDate = end_date;
	}
	public long getCompany_id() {
		return companyId;
	}
	public void setCompany_id(long company_id) {
		this.companyId = company_id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
    
	//-------------Constructors-----------------------------------------------//
	
	public Coupon(String description, String image, String title, long coupon_id, int amount, Date start_date, Date end_date,
			long company_id, Category category, double price) {
		super();
		this.setDescription(description);
		this.setImage(image);
		this.setTitle(title);
		this.setId(coupon_id);
		this.setAmount(amount);
		this.setStart_date(start_date);
		this.setEnd_date(end_date);
		this.setCompany_id(company_id);
		this.setCategory(category);
		this.setPrice(price);
	}
	
	public Coupon(long companyId, Category category, String title, String description, Date startDate, Date endDate,
			int amount, double price, String image, Long couponId) {
		super();
		this.setDescription(description);
		this.setImage(image);
		this.setTitle(title);
		this.setId(null);
		this.setAmount(amount);
		this.setStart_date(startDate);
		this.setEnd_date(endDate);
		this.setCompany_id(companyId);
		this.setCategory(category);
		this.setPrice(price);
		this.setId(couponId);
	}
	public Coupon() {
		super();
	
	}
    
}
