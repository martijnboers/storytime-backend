package model.chat;

import model.roadmap.Roadmap;

import java.util.ArrayList;

/**
 * Created by martijn on 6/15/16.
 */
public class ChatObject {
    private String answer;
    private String token;
    private ArrayList<Roadmap> roadmap;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public ChatObject(String answer, String token, String type, ArrayList<Roadmap> roadmap) {
        this.answer = answer;
        this.token = token;
        this.type = type;
        this.roadmap = roadmap;
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

    public ArrayList<Roadmap> getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(ArrayList<Roadmap> roadmap) {
        this.roadmap = roadmap;
    }
}
