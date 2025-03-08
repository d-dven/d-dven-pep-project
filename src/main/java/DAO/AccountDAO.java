package DAO;
import Util.ConnectionUtil;
import Model.Account;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    
// =====================for internal use====================
    public boolean checkIfUsernameExists(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE username=? ;" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return false;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return true;
    }

// =====================for internal use====================

    public boolean checkIfAccountIDExists(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Account WHERE account_id=? ;" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return false;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return true;
    }
// ============================================================

    public Account insertNewAccount(String username, String password ) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, username);
            ps.setString(2, password);


            ps.executeUpdate();
            ResultSet pkeyResultSet = ps.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_acc_id = (int) pkeyResultSet.getInt(1);
                return new Account(generated_acc_id, username, password);
            }
        }
        catch (SQLException e ) {
            System.out.println(e.getMessage());
        }
        return null;
    }


// ============================================================

    //Login verification: retuns account_id if successful, returns null if unsuccessful
    public Integer verifyLoginInformation(String username, String password) {

        Connection connection = ConnectionUtil.getConnection();
        
        Integer account_id = null;
        String db_password = null;

        try {
            String sql = "SELECT * FROM Account WHERE username=? ;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);


            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                db_password = rs.getString("password");
                account_id = rs.getInt("account_id");
            }

        }
        catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

        if (db_password.equals(password) ){
            return account_id;
        }
        return null;
    }


    public List<Account> getAllAccounts() {
        Connection connection = ConnectionUtil.getConnection();
        List<Account> ret = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Account;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                ret.add(acc);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }
    

}
