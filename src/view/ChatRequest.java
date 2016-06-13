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

package view;

import controller.ChatController;
import dao.QuizDAO;
import dao.RoadmapDAO;
import exceptions.InvalidTokenException;
import logging.Level;
import logging.Logger;
import model.quiz.Answer;
import model.roadmap.Roadmap;

import javax.ws.rs.*;
import java.sql.SQLException;

/**
 * Created by martijn on 4/19/16.
 * <p>
 * Chat workflow:
 * <p>
 * 1: Start /chat/polling to see for achievements or other reminders set by controllers while away. <b>Returns ChatEvent</b>. (needs to be persistent/database)
 * 2: (if polling == something) display on screen or start a Feedback Roadmap (check if previous roadmap is completed and if should be awarded)
 * 3: Ask /chat/start for what Roadmap to begin. This will check for recently added roadmaps or picks random roadmap. <b>Returns RoadmapId</b>  (room for AI).
 * 4: Display full roadmap until the end
 * 5: Ask /chat/question what question to display. This consumes a RoadmapId and a QuestionId. If first question call leave questionid empty
 * 6: User interacts with question, submit answer to /chat/answer. Answer can be String or Yes or No <needs further discussion> <b>Returns next QuestionId for Roadmap</b>
 * 7: GOTO: 5; REPEAT
 */

@Path("/chat")
@Produces("application/json")
public class ChatRequest extends ViewSuper {
    Logger log = Logger.getInstance();
    ChatController chat = new ChatController();
    QuizDAO quizdao = new QuizDAO();

    public ChatRequest() throws Exception {
        log.out(Level.INFORMATIVE, "Chat", "Initializing chat controller");
    }


    /**
     * This can be periodically polled to get next action to be performed, like get next question or start a new
     * quiz, show achievements etc.
     * <p>
     * This is more like a cron job that other functions can put messages in to be showed in the frontend UI
     *
     * @param token
     * @param id
     * @return String with new id and information on session
     * @throws SQLException
     * @throws InvalidTokenException
     * @api {GET} /chat/poll Info polling on current chat session.
     * @apiName pol
     * @apiGroup chat
     * @apiParam {String} Child token for authentication
     * @apiParam {int} Previous polling token given with last request, keep null for first time connecting
     * @apiSuccess MESSAGE: User printable message ID: New poll id STATE: SUCCEEDED Action: Action to be performed in front end
     * @apiError MESSAGE: User printable error message, STATE: ERROR
     */
    @GET
    @Path("/poll/{id}")
    public String getPolling(@HeaderParam("token") String token, @PathParam("id") int id) throws SQLException, InvalidTokenException {
        return chat.getPolling(session.getChildFromToken(token), id);
    }

    /**
     * Insert the answer and check if it's correct, return the new question id
     *
     * @param token
     * @param json
     * @return
     * @throws SQLException
     * @throws InvalidTokenException
     * @api {POST} /chat/answer Answer a question that was asked. Please start the session with /user/start
     * @apiName answer
     * @apiGroup chat
     * @apiParam {String} Child token for authentication
     * @apiParam {Answer} int id, String answer, boolean correct
     * @apiSuccess MESSAGE: User printable message ID: Next question id STATE: SUCCEEDED
     * @apiError MESSAGE: User printable error message, STATE: ERROR
     */
    @POST
    @Path("/answer/{quizid}/{id}")
    public String insertNewAnswer(@HeaderParam("token") String token, @PathParam("quizid") int quizid,
                                  @PathParam("id") int questionid, String json) throws SQLException, InvalidTokenException {
        return chat.insertAnswer(session.getChildFromToken(token), quizid, questionid, gson.fromJson(json, Answer.class));
    }


    /**
     * Get the individual question
     *
     * @param token
     * @param id
     * @return
     * @throws SQLException
     * @throws InvalidTokenException
     * @api {GET} /chat/question/id Get the question that corresponds with the given id
     * @apiName getQuestion
     * @apiGroup chat
     * @apiParam {String} Child token for authentication
     * @apiParam {roadmapid} Id of roadmap
     * @apiParam {id} Question id (Emtpy if first)
     * @apiSuccess MESSAGE: User printable message QUESTION: tobeimplemented id STATE: SUCCEEDED
     * @apiError MESSAGE: User printable error message, STATE: ERROR
     */
    @GET
    @Path("/question/{quizid}/{id}")
    public String getQuestion(@HeaderParam("token") String token, @PathParam("quizid") int roadmap, @PathParam("id") int id) throws SQLException, InvalidTokenException {
        //return chat.getQuestion(session.getChildFromToken(token), quizdao.get , id);
        return null;
    }

    /**
     * @param token
     * @return Json
     * @throws SQLException
     * @throws InvalidTokenException
     * @api {GET} /chat/start System decides to start Feedback Roadmap or start a new Roadmap
     * @apiName start
     * @apiGroup chat
     * @apiParam {String} Child token for authentication
     * @apiSuccess MESSAGE: User printable message ID: Next question id QUESTION: String question, int questionId, boolean completed
     * private List<Answer> STATE: SUCCEEDED
     * @apiError MESSAGE: User printable error message, STATE: ERROR
     */
    @GET
    @Path("/start/{id}")
    public String startChat(@HeaderParam("token") String token, @PathParam("id") int id) throws SQLException, InvalidTokenException {
        return chat.start(session.getChildFromToken(token), id);
    }

    /**
     * Get a suggestion for a roadmap based on the input given by actor.
     *
     * @param token
     * @param answer
     * @return
     * @apiName suggest
     * @apiGroup chat
     * @apiError MESSAGE: Kan geen instructies vinden over onderwerp, STATE: ERROR
     * @api {POST} /chat/suggest Let input from user decide which roadmap to start
     */
    @POST
    @Path("/suggest")
    public String getSuggestedRoadmap(@HeaderParam("token") String token, String answer) throws Exception {
        // security
        session.getChildFromToken(token);
        return chat.suggest(gson.fromJson(answer, Answer.class));
    }
}