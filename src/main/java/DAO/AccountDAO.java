package DAO;
import Util.ConnectionUtil;
import Model.Account;
import Model.Message;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account createNewUser(String username, String password ) {
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

    //Login verification: retuns account_id if successful, returns null if unsuccessful
    public Integer userLogin(String username, String password) {

        Connection connection = ConnectionUtil.getConnection();
        
        Integer account_id = null;
        String db_password = null;

        try {
            String sql = "SELECT password, account_id FROM Account WHERE username=? ;";
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
        if (db_password == password )
            return account_id;
        return null;
    }



}
