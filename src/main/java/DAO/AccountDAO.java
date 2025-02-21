package DAO;
import Util.ConnectionUtil;
import Model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    
    public Account createNewUser(String username, String password ) {
        Connection connection = ConnectionUtil.getConnection();
        Account newUser = new Account();
        
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
        return newUser;
    }


}
