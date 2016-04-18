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

package controller;

import model.quiz.Answer;
import model.system.FrontendEvent;
import model.user.Child;

/**
 * Created by martijn on 4/19/16.
 */
public class ChatController {

    public String getPolling(Child child, int id) {
        // TODO
        return null;
    }

    public String start(Child child) {
        return null;
    }

    public String insertAnswer(Child child, Answer answer) {
        return null;
    }

    public boolean addToPollStream(Child child, FrontendEvent event, String message) {
        return true;
    }

    public String getQuestion(Child child, int id) {
        return null;
    }
}
