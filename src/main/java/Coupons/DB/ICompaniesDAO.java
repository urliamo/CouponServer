package Coupons.DB;

import java.util.Collection;

import Coupons.JavaBeans.Company;



// DAO = Data access object
public interface ICompaniesDAO {
	

    long addCompany(Company company) throws Exception;
    void updateCompany(Company company) throws Exception;   
	Collection<Company> getAllCompanies() throws Exception;
    Company getCompanyByID(long companyID) throws Exception;
    boolean isCompanyExists(String email, String password) throws Exception;
    void deleteCompany(long companyID) throws Exception;

}