package Coupons.DB;

import java.sql.*;



public class DBCreator  {
private static String USERNAME = "root";
private static String PASSWORD = "123456";
//private static String PASSWORD = "administrator";
private static String DRIVER = "com.mysql.cj.jdbc.Driver";
//private static String DRIVER = "org.mariadb.jdbc.Driver";
//private static String URL = "jdbc:mariadb://localhost:3306/";
private static String URL = "jdbc:mysql://127.0.0.1:3306/";




public static void buildDB() {
	
	Connection connection =null;
	Statement statement = null;
	
	try {
		Class.forName(DRIVER);
		// Create a connection to the database: 
		connection = DriverManager.getConnection(URL+"?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",USERNAME,PASSWORD);
		
		
		//Create SQL statement
		statement = connection.createStatement();	
		
		//Create JB DB if it does not exist
		String sql = "show databases like 'JB'";
		ResultSet isJBDB = statement.executeQuery(sql);
		if (!isJBDB.next()) {
				 sql = "CREATE DATABASE JB";
				statement.executeUpdate(sql);
				System.out.println(" JB Database created successfully...");
				
				}
		else
		{
			System.out.println(" JB Database already exists");
		}
				
		statement.close();
		connection.close();
		
		connection = DriverManager.getConnection(URL+"JB?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",USERNAME,PASSWORD);
		statement = connection.createStatement();	
		
		
		// Create string for statement including companies table creation
				 sql = "CREATE TABLE companies ("+
				"company_ID BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,"+
				"company_name VARCHAR(50) NOT NULL,"+
				"company_email VARCHAR(50) NOT NULL)";
				
				
				
				//Execute create companies table statement
				
				statement.executeUpdate(sql);
				
				System.out.println("companies table has been created.");	
				
				
		// Create string for statement including users table creation
		 sql = "CREATE TABLE users ("+
		"user_ID bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,"+
		" user_name VARCHAR(50) NOT NULL,"+
		" password VARCHAR(50) NOT NULL,"+
		" email VARCHAR(50) NOT NULL,"+
		" company_ID BIGINT,"+
		" Type VARCHAR(50) NOT NULL,"+
		"FOREIGN KEY (company_ID) REFERENCES companies(company_ID))";
		
		
		
		//Execute create users table statement
		
		statement.executeUpdate(sql);
		
		System.out.println("users table has been created.");	
				
		
		
		// Change string for statement to include customers table creation
				
		sql = "CREATE TABLE customers (" +
					"customer_ID BIGINT PRIMARY KEY, " +
					"first_name VARCHAR(50) NOT NULL, " +
					"last_name VARCHAR(50) NOT NULL, " +
					"FOREIGN KEY (customer_ID) REFERENCES users(user_ID))";
				
		//Execute create customers table statement

		statement.executeUpdate(sql);  
				
		System.out.println("customers table has been created.");
		

		
		// Change string for statement to include coupons table creation
		
				sql = "CREATE TABLE coupons (" +
							"coupon_ID BIGINT PRIMARY KEY AUTO_INCREMENT, " +
							"company_ID BIGINT NOT NULL, " +
							"category VARCHAR(50) NOT NULL, " +
							"title VARCHAR(50) NOT NULL, " +
							"description VARCHAR(100) NOT NULL, " +
							"image VARCHAR(50) NOT NULL, " +
							"amount INT NOT NULL, " +
							"price DOUBLE NOT NULL, " +
							"start_date DATE NOT NULL, " +
							"end_date DATE NOT NULL, " +
							"FOREIGN KEY (company_ID) REFERENCES companies(company_ID))";
						
				//Execute create coupons table statement

				statement.executeUpdate(sql);
						
				System.out.println("coupons table has been created.");

				
					// Change string for statement to include customers_vs_coupons table creation
				sql = "CREATE TABLE jb.purchases ("+
					"Purchase_ID BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,"+
					"customer_ID BIGINT NOT NULL,"+
					"coupon_ID BIGINT NOT NULL,"+
					"amount INT NOT NULL,"+
					"FOREIGN KEY (customer_ID) REFERENCES customers(customer_ID),"+
					"FOREIGN KEY (coupon_ID) REFERENCES coupons(coupon_ID))";
			

				//--old customer VS coupons--
				//sql = "CREATE TABLE jb.customers_vs_coupons (customerID INT , couponID INT , PRIMARY KEY (customerID, couponID), FOREIGN KEY (customerID) REFERENCES customers(customerID), FOREIGN KEY (couponID) REFERENCES coupons(couponID))";
						
				//Execute create purchases table statement

				statement.executeUpdate(sql);
						
				System.out.println("purchases table has been created.");
	}
	catch(Exception ex) {
		 System.out.println(ex.getMessage());
	}
	finally{
		//close all connections
		if(connection!=null)
			try {
				statement.close();
				connection.close();
			} catch (SQLException ex) {
				 System.out.println(ex.getMessage());
			}
	}
}

}