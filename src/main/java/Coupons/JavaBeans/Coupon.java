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
	public void setId(Long couponId) {
		this.couponId = couponId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getstartDate() {
		return startDate;
	}
	public void setstartDate(Date startDate) {
		
		this.startDate = startDate;
	}
	public Date getendDate() {
		return endDate;
	}
	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}
	public long getcompanyId() {
		return companyId;
	}
	public void setcompanyId(long companyId) {
		this.companyId = companyId;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
    
	//-------------Constructors-----------------------------------------------//
	
	public Coupon(String description, String image, String title, long couponId, int amount, Date startDate, Date endDate,
			long companyId, Category category, double price) {
		super();
		this.setDescription(description);
		this.setImage(image);
		this.setTitle(title);
		this.setId(couponId);
		this.setAmount(amount);
		this.setstartDate(startDate);
		this.setendDate(endDate);
		this.setcompanyId(companyId);
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
		this.setstartDate(startDate);
		this.setendDate(endDate);
		this.setcompanyId(companyId);
		this.setCategory(category);
		this.setPrice(price);
		this.setId(couponId);
	}
	public Coupon() {
		super();
	
	}
    
}
