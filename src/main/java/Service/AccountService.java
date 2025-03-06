package Service;

import Model.Account;
import DAO.AccountDAO;


public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createNewAccount(String username, String password) {
        return null;
    }

    public boolean loginToAccount(String username, String password) {
        return false;
    }

}
