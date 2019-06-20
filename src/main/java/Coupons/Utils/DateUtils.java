package Coupons.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;

public class DateUtils {


	public static String getCurrentDateAndTime(){
		//Creating a format for the date
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//By calling the date constructor, we get the current date
		Date today=new Date();
		//formatting the date to string
		String currentDateAndTime=dateFormat.format(today);

		return currentDateAndTime;
	}

	public static String getCurrentDate(){
		//creating a matching format for the date as it appears on the Database
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
		//By calling the date constructor, we get the current date
		Date today=new Date();
		//formatting the date to string
		String currentDate=dateFormat.format(today);

		return currentDate;
	}
	
	public static void validateDates(String startDate, String endDate) throws ApplicationException {
		try{
			//creating a matching format for the date as it appears on the Database
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy/MM/dd");
			//Parsing the dates from string to date type.
			Date date1=dateFormat.parse(startDate);
			Date date2=dateFormat.parse(endDate);
			//We check if date1 is before date2
			if (date1.after(date2)) {
				throw new ApplicationException(ErrorType.COUPON_DATE_MISMATCH, ErrorType.COUPON_DATE_MISMATCH.getInternalMessage(), false);
			}
			if (date2.before(dateFormat.parse(getCurrentDate()))) {
				throw new ApplicationException(ErrorType.COUPON_ALREADY_EXPIRED, ErrorType.COUPON_ALREADY_EXPIRED.getInternalMessage(), false);
			}
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
	

}
