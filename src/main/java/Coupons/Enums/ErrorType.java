package Coupons.Enums;

import Coupons.Utils.DateUtils;

public enum ErrorType {
GENERAL_ERROR(600, "General error"),
NAME_IS_ALREADY_EXISTS(600, "The name or mail you entered already exists"),
COMPANY_TYPE_NO_ID(600, "User is of type Company but with no CompanyID"),
COMPANY_ID_NOT_TYPE(600, "user has a company ID but is not Company type user"),
INVALID_NAME(600, "User does not exist with this name"),
INVALID_ID(600, "The ID you've enter is invalid"),
INVALID_AMOUNT(600,"The amount you've entered is invalid"),
EMPTY(600,"Empty value entered"),
USER_ID_DOES_NOT_EXIST(600,"no such userID in DB"),
COMPANY_ID_DOES_NOT_EXIST(600,"no such companyID in DB"),
CUSTOMER_ID_DOES_NOT_EXIST(600,"no such customerID in DB"),
COUPON_ID_DOES_NOT_EXIST(600,"no such couponID in DB"),
COMPANY_ID_MUST_BE_ASSIGNED(600,"attempt to add company with pre-existing ID"),
COMPANY_ID_ALREADY_EXIST(600,"company with this ID already exists"),
INVALID_PRICE(600,"The price you've entered is invalid"),
INVALID_EMAIL_OR_PASS(600,"The email or password you've entered is invalid."),
EXISTING_EMAIL(600,"The email you've entered is unavailable."),
INVALID_EMAIL(600,"The email you've entered is invalid."),
INVALID_PASSWORD(600,"The password you've entered is invalid."),
INVALID_DATES(600,"The dates you've entered is invalid."),
FIELD_IS_IRREPLACEABLE(600, "You can't change this field."),
NAME_IS_IRREPLACEABLE(600, "You can't change your name."),
COUPON_IS_OUT_OF_ORDER(600, "Coupon is out of order"),
EXISTING_COUPON_TITLE(600, "Coupon with this title already exists"),
COUPON_ALREADY_EXPIRED(600, "coupon is past its' end-date"),
COUPON_DATE_MISMATCH(600, "coupon start date is after end date"),
LOGIN_FAILED(600, "Login failed. credentials incorrect."), 
USER_TYPE_MISMATCH(600, "User Type Mismatch. User is:"),
USER_ID_MISMATCH(600, "user ID does not match cached ID"),
COMPANY_ID_MISMATCH(600, "company ID does not match cached ID");

	
	private int internalErrorCode;
	private String internalMessage;
	
	private ErrorType(int internalErrorCode, String internalMessage) {
		this.internalErrorCode=internalErrorCode;
		this.internalMessage=internalMessage+DateUtils.getCurrentDateAndTime();
	}

	public int getInternalErrorCode() {
		return internalErrorCode;
	}

	public String getInternalMessage() {
		return internalMessage;
	}
	
} 
