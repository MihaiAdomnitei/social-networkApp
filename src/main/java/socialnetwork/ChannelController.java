package socialnetwork;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import socialnetwork.controller.MessageAlert;
import socialnetwork.domain.Conversation;
import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.service.ConversationService;

import java.util.List;

public class ChannelController {

    @FXML
    private TextArea msgArea;
    private Stage stage;
    private ConversationService conversationService;
    private Utilizator fromUser;
    private List<Utilizator> toUsers;


    public void setService(ConversationService conversationService, Stage stage, Utilizator fromUser, List<Utilizator> toUsers) {
        this.fromUser = fromUser;
        this.stage = stage;
        this.toUsers = toUsers;
        this.conversationService = conversationService;
    }


    public void handleSend() {
        String msgAreaText = msgArea.getText();
        Message message = new Message(msgAreaText);
        message.setId(conversationService.generateIdMessage());
        try{
            for(Utilizator user: toUsers){
                Conversation conversation = new Conversation(fromUser, user, message);
                conversationService.addMessage(conversation);
            }
            stage.close();
        }catch (Exception e){
            MessageAlert.showErrorMessage(null, "Eroare: " + e.getMessage());
        }
    }



}
