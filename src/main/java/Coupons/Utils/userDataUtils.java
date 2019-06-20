package Coupons.Utils;

import org.springframework.beans.factory.annotation.Autowired;

import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.Company;
import Coupons.JavaBeans.Customer;
import Coupons.JavaBeans.UserData;

public class userDataUtils {

		@Autowired
		private Coupons.DB.CustomerDAO customerDao;
		@Autowired
		private Coupons.DB.UsersDAO usersDao;
		
		public  void validateCustomerData(long customerId, UserData customerData) throws ApplicationException
		{
			if (customerData == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (!customerData.getType().name().equals("Customer"))
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage()+customerData.getType(), false);

			if (customerId != customerData.getUserID())
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);

			if (customerDao.getOneCustomer(customerId)==null)
				throw new ApplicationException(ErrorType.CUSTOMER_ID_DOES_NOT_EXIST,ErrorType.CUSTOMER_ID_DOES_NOT_EXIST.getInternalMessage(), false);

			if (usersDao.getUserByID(customerId)==null)
				throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage(),false);
		}
		
		public  void validateCustomerData(Customer customer, UserData customerData) throws ApplicationException
		{
			if (customerData == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (customer == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (customer.getUser() == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (!customerData.getType().name().equals("Customer"))
				throw new ApplicationException(ErrorType.USER_TYPE_MISMATCH, ErrorType.USER_TYPE_MISMATCH.getInternalMessage(), false);

			if (customer.getCustomerId() != customerData.getUserID())
				throw new ApplicationException(ErrorType.USER_ID_MISMATCH, ErrorType.USER_ID_MISMATCH.getInternalMessage(), true);

		}
		public  void validateCompanyData(Company company, UserData companyUserData) throws ApplicationException
		{
			if (company == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (companyUserData == null)
				throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage(), false);

			if (companyUserData.getCompany()!=company.getCompanyID())
				throw new ApplicationException(ErrorType.COMPANY_ID_MISMATCH, ErrorType.COMPANY_ID_MISMATCH.getInternalMessage(), false);

		
		}
}
