package socialnetwork.domain;

public class Message extends Entity<Long> {

    private String text;
    private Long idReply;



    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



}