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

    public String start(Child child) {
        List<Roadmap> maps = roadmapdao.getAllRoadmapsByChild(child);
        // Return the ID of the list that needs to be displayed to the user
        return null;
    }

    public String insertAnswer(Child child, Answer answer) {
        return null;
    }

    public void addPollFeed(ChatEvent event) {
        // New poll added
        pollId++;
        event.setId(pollId);
        pollStream.add(event);
    }

    public String getQuestion(Child child, Roadmap roadmap, int id) {
        return gson.toJson(roadmap.getSteps().get(id));
    }
}
