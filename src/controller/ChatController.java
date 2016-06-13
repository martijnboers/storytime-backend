/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package controller;

import com.google.gson.Gson;
import dao.RoadmapDAO;
import model.quiz.Answer;
import model.quiz.Question;
import model.quiz.Quiz;
import model.roadmap.Roadmap;
import model.system.ChatEvent;
import model.system.FrontendEvent;
import model.user.Child;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martijn on 4/19/16.
 */
public class ChatController {
    private RoadmapDAO roadmapdao;
    private static int pollId;
    private static ArrayList<ChatEvent> pollStream = new ArrayList<>();
    private Gson gson;

    public ChatController() {
        roadmapdao = new RoadmapDAO();
        gson = new Gson();
    }

    /**
     * Returns one or multiple chat FrontendEvents
     *
     * @param child
     * @param id
     * @return
     */
    public String getPolling(Child child, int id) {
        ArrayList<ChatEvent> out = new ArrayList<>();
        for (ChatEvent poll : pollStream) {
            if (poll.getId() > id) {
                out.add(poll);
            }
        }
        return gson.toJson(out);
    }

    public String start(Child child, int id) {
        //List<Roadmap> maps = roadmapdao.getAllRoadmapsByChild(child);
        //return gson.toJson(maps.get(maps.size() - 1));

        // Doesn't really matter if this roadmap belongs to which child.
        return gson.toJson(roadmapdao.getRoadmapById(id));
    }

    public String insertAnswer(Child child, int quizid, int questionid, Answer answer) {
        return answer.toString();
    }

    public void addPollFeed(ChatEvent event) {
        // New poll added
        pollId++;
        event.setId(pollId);
        pollStream.add(event);
    }

    public String getQuestion(Child child, Quiz roadmap, int id) {
        //return gson.toJson(roadmap.getSteps().get(id));
        return null;
    }

    public String suggest(Answer answer) throws Exception {
        if (!answer.getAnswer().equals("")) {
            return gson.toJson(roadmapdao.getSuggestedRoadmap(answer.getAnswer().split(" ")));
        } else {
            throw new Exception("Geen suggesties gevonden");
        }
    }
}
