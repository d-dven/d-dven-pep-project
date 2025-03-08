package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {

    private MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }
    
    //==============================================TRIED ==================================================================

    public Message createNewMessage(String message_text, int posted_by , long time_posted_epoch) {

        if (message_text == "" || message_text.length() > 255) {
            return null;
        }
        Message createdMessage = messageDAO.createNewMessage(message_text, posted_by, time_posted_epoch);
        
        return createdMessage;
    }


    //=============================================== DONE ==================================================================
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    //=============================================== DONE =================================================================

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }
    //================================================= TRIED ================================================================

    public Message deleteMessageByID(int message_id) {
        Message deleted_msg = messageDAO.getMessageByID(message_id);
        if (deleted_msg != null) {
            messageDAO.deleteMessageByID(message_id);
        }
        return deleted_msg;
    }


//====================================================== TRIED ===========================================================

    public Message updateMessageByID(int message_id, String message_text) {
        if (message_text == "" || message_text.length() > 255) {
            return null;
        }
        return messageDAO.updateMessageByID(message_id, message_text);
    }

//==================================================== TRIED =============================================================

    public List<Message> getMessagesByAccountID(int account_id) {
        
        return messageDAO.getMessagesByAccountID(account_id);
    }
    
}
