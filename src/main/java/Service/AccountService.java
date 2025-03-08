package Service;

import Model.Account;
import DAO.AccountDAO;
import java.util.List;


public class AccountService {
    
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account createNewAccount(String username, String password) {
        if(username == "") {
            return null;
        }
        if (password.length() < 4) {
            return null;
        }
        if (accountDAO.checkIfUsernameExists(username)) {
            return null;
        }

        return accountDAO.insertNewAccount(username, password);
    }

    public Account loginToAccount(String username, String password) {
        
        Account info = new Account(username, password);
        Integer account_id = accountDAO.verifyLoginInformation(username, password);

        if(!accountDAO.checkIfUsernameExists(username)) {
            return null;
        }
        System.out.println("Username exists");                      //PRINT STATEMENT

        if (account_id == null) {
            System.out.println("verifyLoginInformation returned null");                      //PRINT STATEMENT
            return null;
        }

        info.account_id = account_id;
        return info;
    }
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

}
