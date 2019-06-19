package Coupons.Logic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import Coupons.DB.CompaniesDAO;
import Coupons.Enums.ClientType;
import Coupons.Enums.ErrorType;
import Coupons.Exceptions.ApplicationException;
import Coupons.JavaBeans.LoginData;
import Coupons.JavaBeans.LoginForm;
import Coupons.JavaBeans.User;
import Coupons.JavaBeans.UserData;
import Coupons.Utils.NameUtils;
import Coupons.Utils.PasswordUtils;

//import com.avi.coupons.beans.User;
//import com.avi.coupons.beans.UserData;
//import com.avi.coupons.dao.CustomersDAO;
//import com.avi.coupons.dao.UsersDAO;
//import com.avi.coupons.enums.ClientType;
//import com.avi.coupons.enums.ErrorType;
//import com.avi.coupons.exceptions.ApplicationException;
//import com.avi.coupons.utils.IdUtils;
//import com.avi.coupons.utils.NameUtils;
//import com.avi.coupons.utils.PasswordUtils;
//import com.avi.coupons.utils.TypeUtils;

@Controller
public class UsersController {
	
	@Autowired
	private Coupons.DB.UsersDAO usersDao;
	
	@Autowired
	private Coupons.DB.CompaniesDAO companiesDAO;
	
	@Autowired
	private ICacheManager cacheManager;

	public UsersController() {
		
	}
	
	public LoginData login(LoginForm loginForm) throws ApplicationException {
		UserData userData = usersDao.login(loginForm.getUserName(),loginForm.getPassword());
		int token = generateEncryptedToken(loginForm.getUserName());
		cacheManager.put(token, userData);
		LoginData loginData = new LoginData(userData.getUserID(),token,userData.getType(), userData.getCompany());
		return loginData;
	}

	

	private int generateEncryptedToken(String username) {
		String token = "this too" + username + "shall hash";
		
		return token.hashCode();
	}

	public long createUser(User user) throws ApplicationException {
		if (user == null) {
			throw new ApplicationException(ErrorType.EMPTY, ErrorType.EMPTY.getInternalMessage());
		}
		
		NameUtils.isValidName(user.getUserName());
		PasswordUtils.isValidPassword(user.getPassword());

		if (usersDao.getUserByMail(user.getEmail()) != null) {
			throw new ApplicationException(ErrorType.EXISTING_EMAIL, ErrorType.EXISTING_EMAIL.getInternalMessage());
		}
		if ((user.getCompanyId() != 0 && user.getType().equals(ClientType.Customer))) {
			throw new ApplicationException(ErrorType.COMPANY_ID_NOT_TYPE, ErrorType.COMPANY_ID_NOT_TYPE.getInternalMessage());
		}
		if (user.getCompanyId() == 0 && user.getType().equals(ClientType.Company)) {
			throw new ApplicationException(ErrorType.COMPANY_TYPE_NO_ID, ErrorType.COMPANY_TYPE_NO_ID.getInternalMessage());
		}
		return usersDao.createUser(user);
	}

	

	public void updateUser(User user) throws ApplicationException {

		NameUtils.isValidName(user.getEmail());
		PasswordUtils.isValidPassword(user.getPassword());

		if (usersDao.getUserByID(user.getId()) == null) {
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		usersDao.updateUser(user);
	}

	public void deleteUser(long userId) throws ApplicationException {


		if (usersDao.getUserByID(userId) == null) {
			throw new ApplicationException(ErrorType.USER_ID_DOES_NOT_EXIST, ErrorType.USER_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		usersDao.deleteUserByID(userId); 
	}

	public void deleteUsersByCompanyId(long companyId) throws ApplicationException {


		if ( companiesDAO.getCompanyByID(companyId) == null) {
			throw new ApplicationException(ErrorType.COMPANY_ID_DOES_NOT_EXIST, ErrorType.COMPANY_ID_DOES_NOT_EXIST.getInternalMessage());
		}
		usersDao.deleteCompanysUsers(companyId);

	}

	public Collection<User> getAllUsers() throws ApplicationException
	{

		return usersDao.getAllUsers();

	}

}
