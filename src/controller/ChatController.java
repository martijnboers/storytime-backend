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
import com.google.gson.JsonObject;
import dao.RoadmapDAO;
import model.chat.ChatObject;
import model.quiz.Answer;
import model.roadmap.Roadmap;
import model.system.ChatEvent;
import model.user.Child;

import org.alicebot.ab.*;

import java.util.ArrayList;

/**
 * Created by martijn on 4/19/16.
 */
public class ChatController {
    private RoadmapDAO roadmapdao;
    private static int pollId;
    private static ArrayList<ChatEvent> pollStream = new ArrayList<>();
    private Gson gson;
    private Bot bot;

    public ChatController() {
        roadmapdao = new RoadmapDAO();
        gson = new Gson();
        String path = "/home/martijn/code/storytime-backend/botfiles";
        bot = new Bot("robin", path);
    }

    public String chat(Child child, ChatObject chat) throws Exception {
        Chat chatSession = new Chat(bot);
        String answer = chatSession.multisentenceRespond(chat.getAnswer());

        if (answer.equals("QUESTION")) {
            return gson.toJson(new ChatObject("Kan ik je hiermee helpen?", "token", "ROADMAP", suggest(chat.getAnswer())));
        }
        return gson.toJson(new ChatObject(answer, "token", "CHAT", null));
    }

    public ArrayList<Roadmap> suggest(String answer) throws Exception {
        if (!answer.equals("")) {
            return roadmapdao.getSuggestedRoadmap(answer.split(" "));
        } else {
            throw new Exception("Geen suggesties gevonden");
        }
    }
}
