package model.chat;

/**
 * Created by martijn on 6/15/16.
 */
public class ChatObject {
    private String answer;
    private String token;

    public ChatObject(String answer, String token) {
        this.answer = answer;
        this.token = token;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
