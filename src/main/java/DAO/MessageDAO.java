package DAO;
import Util.ConnectionUtil;
import Model.Message;

import java.util.ArrayList;
import java.util.List;
import DAO.AccountDAO;

import java.sql.*;

public class MessageDAO {


    public Message createNewMessage(String message_text, int posted_by, long time_posted_epoch) {

        Connection connection = ConnectionUtil.getConnection();
        AccountDAO accountDAO = new AccountDAO();
        try {
            if (accountDAO.checkIfAccountIDExists(posted_by)) {

                String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, posted_by);
                ps.setString(2, message_text);
                ps.setLong(3, time_posted_epoch);
                

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){ 

                    Message message = new Message(rs.getInt("message_id"), posted_by, message_text,
                        time_posted_epoch);
                    
                return message;                        
                }   
            }
        }
        catch (SQLException e ) {
            System.out.println(e.getMessage());
        }

        return null;

    }
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> ret = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Message;" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                ret.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

    public Message getMessageByID(int message_id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id=?;" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message deleteMessageByID(int message_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id=?;" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, message_id);
            ps.executeUpdate();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    }
        

    public Message updateMessageByID(int message_id, String message_text) {
        Connection connection = ConnectionUtil.getConnection();
        Message msg_to_update = getMessageByID(message_id);
        try {
            String sql = "UPDATE Message SET message_text=? WHERE message_id=?;" ;
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, message_text);
            ps.setInt(2, message_id);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                System.out.println("FOUND MESSAGE TO UPDATE");              //PRINT STATEMENT
                Message message = new Message(rs.getInt("message_id"), msg_to_update.getPosted_by(), message_text,
                        msg_to_update.getTime_posted_epoch());
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
        
    }

    public List<Message> getMessagesByAccountID(int account_id) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> ret = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM Message WHERE posted_by=?;" ;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, account_id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                ret.add(message);
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return ret;
    }

}
