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
    
    public Message createNewMessage(String message_text, int posted_by ) {

        return null;
    }


    //=============================================== DONE ==================================================================
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }
    //=================================================================================================================

    public Message getMessageByID(int message_id) {
        return messageDAO.getMessageByID(message_id);
    }
    //=================================================================================================================

    public Message deleteMessageByID(int message_id) {
        return null;
    }

    public Message updateMessageByID(int message_id) {
        return null;
    }

    public List<Message> getMessagesByAccountID(int account_id) {
        return null;
    }
    
}
