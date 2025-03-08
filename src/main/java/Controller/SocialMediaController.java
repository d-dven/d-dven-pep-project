package Controller;

import Service.MessageService;

import java.util.Map;

import Model.Account;
import Model.Message;

import Service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;
 

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);


        app.get("/messages", this::getAllMessagesHandler);

        app.get("/messages/{message_id}", this::getMessageByIDHandler);

        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountIDHandler);
        
        app.post("/register",this::insertNewAccountHandler);

        app.post("/login",this::loginToAccountHandler);

        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);

        app.post("/messages", this::createNewMessageHandler );

        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);

        app.get("/accounts", this::getAllAccountsHandler);
        
        return app;

        
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }



//========================================= WORKS =================================================

    private void getAllMessagesHandler(Context context) {
        MessageService ms = new MessageService();
        context.json(ms.getAllMessages());
    }

//========================================= WORKS =================================================
    private void getMessageByIDHandler(Context context) {
        MessageService ms = new MessageService();
        String message_id = context.pathParam("message_id");
        context.json(ms.getMessageByID(Integer.valueOf(message_id)));
    }


//========================================= WORKS I THINK=================================================

    private void getMessagesByAccountIDHandler(Context context) {
        MessageService ms = new MessageService();
        String account_id = context.pathParam("account_id");
        context.json(ms.getMessagesByAccountID(Integer.valueOf(account_id)));
    }

//========================================= WORKS =================================================
    private void insertNewAccountHandler(Context ctx) {

        AccountService as = new AccountService();
        Map<String, Object> body = ctx.bodyAsClass(Map.class); // Convert JSON to Map
        String username = (String) body.get("username");
        String password = (String) body.get("password");
        Account newAcc = as.createNewAccount(username, password);
        if (newAcc == null) {
            ctx.status(400);
        }
        else {
            ctx.status(200).json(newAcc);
        }
    }

//========================================= WORKS =================================================
    private void loginToAccountHandler(Context ctx) {
        AccountService as = new AccountService();
        Map<String, Object> body = ctx.bodyAsClass(Map.class); // Convert JSON to Map
        String username = (String) body.get("username");
        String password = (String) body.get("password");

        Account currentAccount = as.loginToAccount(username, password);
        if (currentAccount == null) {
            ctx.status(401);
        }
        else {
            ctx.status(200).json(currentAccount);
        }
    }

//========================================= WORKS =================================================
    private void deleteMessageByIDHandler(Context ctx){
        MessageService as = new MessageService();  
        int msg_id = Integer.valueOf(ctx.pathParam("message_id"));
        Message deleted_msg = as.deleteMessageByID(msg_id);
        ctx.status(200);

        if (deleted_msg != null) {
            ctx.json(as.deleteMessageByID(msg_id));
        }
    }

//========================================= WORKS =================================================
    private void createNewMessageHandler(Context ctx) {
        MessageService as = new MessageService();  
        Map<String, Object> body = ctx.bodyAsClass(Map.class); // Convert JSON to Map
        String username = (String) body.get("message_text");
        int password = (int) body.get("posted_by");
        long time_posted_epoch = (int) body.get("time_posted_epoch");
        Message new_msg = as.createNewMessage(username, password, time_posted_epoch);
        
        if (new_msg == null) {
            ctx.status(400);
        }
        else { 
            ctx.status(200).json(new_msg);
        }
    }

//========================================= WORKS =================================================
    private void updateMessageByIDHandler(Context ctx) {
        MessageService ms = new MessageService();
        Map<String, Object> body = ctx.bodyAsClass(Map.class); // Convert JSON to Map
        String message_text = (String)body.get("message_text");
        int posted_by = Integer.valueOf(ctx.pathParam("message_id"));

        Message updatedMsg = ms.updateMessageByID( posted_by, message_text);
        if (updatedMsg == null) {
            ctx.status(400);
        }
        else { 
            ctx.status(200).json(updatedMsg);
        }
    }


//=========================================  =================================================


//========================================= FOR TESTING =================================================
    private void getAllAccountsHandler(Context ctx) {
        AccountService as = new AccountService();
        ctx.json(as.getAllAccounts());
    }



}