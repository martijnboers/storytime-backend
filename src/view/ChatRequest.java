/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package view;

import controller.ChatController;
import exceptions.InvalidTokenException;
import logging.Level;
import logging.Logger;
import model.quiz.Answer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

/**
 * Created by martijn on 4/19/16.
 */

@Path("/chat")
public class ChatRequest extends ViewSuper {
    Logger log = Logger.getInstance();
    ChatController chat = new ChatController();

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
     * @apiSuccessExample MESSAGE: User printable message ID: New poll id STATE: SUCCEEDED Action: Action to be
     * performed in front end
     * @apiErrorExample MESSAGE: User printable error message, STATE: ERROR
     */
    @GET
    @Path("/poll/{id}")
    @Produces(MediaType.APPLICATION_JSON)
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
     * @apiSuccessExample MESSAGE: User printable message ID: Next question id STATE: SUCCEEDED
     * @apiErrorExample MESSAGE: User printable error message, STATE: ERROR
     */
    @POST
    @Path("/answer")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertNewAnswer(@HeaderParam("token") String token, String json) throws SQLException, InvalidTokenException {
        return chat.insertAnswer(session.getChildFromToken(token), gson.fromJson(json, Answer.class));
    }


    /**
     * Get the individual question
     *
     * @api {GET} /chat/question/id Get the question that corresponds with the given id
     * @apiName getQuestion
     * @apiGroup chat
     * @apiParam {String} Child token for authentication
     * @apiParam {id} Question id
     * @apiSuccessExample MESSAGE: User printable message QUESTION: tobeimplemented id STATE: SUCCEEDED
     * @apiErrorExample MESSAGE: User printable error message, STATE: ERROR
     *
     * @param token
     * @param id
     * @return
     * @throws SQLException
     * @throws InvalidTokenException
     */
    @GET
    @Path("/question/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuestion(@HeaderParam("token") String token, @PathParam("id") int id) throws SQLException, InvalidTokenException {
        return chat.getQuestion(session.getChildFromToken(token), id);
    }


    /**
     * @param token
     * @return Json
     * @throws SQLException
     * @throws InvalidTokenException
     * @api {GET} /chat/start Start a chat and get the first question and the next id
     * @apiName start
     * @apiGroup chat
     * @apiParam {String} Child token for authentication
     * @apiSuccessExample MESSAGE: User printable message ID: Next question id QUESTION: String question, int questionId, boolean completed
     * private List<Answer> STATE: SUCCEEDED
     * @apiErrorExample MESSAGE: User printable error message, STATE: ERROR
     */
    @GET
    @Path("/start")
    public String startChat(@HeaderParam("token") String token) throws SQLException, InvalidTokenException {
        return chat.start(session.getChildFromToken(token));
    }
}
