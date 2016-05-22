package model.system;

/**
 * Created by martijn on 5/22/16.
 */
public class ChatEvent {
    private int id;
    private String message;
    private FrontendEvent event;

    public ChatEvent(String message, FrontendEvent event) {
        this.message = message;
        this.event = event;
    }

    public ChatEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FrontendEvent getEvent() {
        return event;
    }

    public void setEvent(FrontendEvent event) {
        this.event = event;
    }
}
