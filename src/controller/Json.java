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

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.system.State;

/**
 * @author martijn
 *
 *         Class for creating JSON objects with the same format so it can be
 *         easily parsed in APEX
 * 
 *         TODO: Refactor to GSON
 */
public class Json {
	private String _state;
	private StringWriter _json;

	/**
	 * Outputs JSON string with given message, specify state {Error: something
	 * went wrong, Passed: everything is OK}
	 * 
	 * @param state
	 * @param message
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String createJson(State state, String message) {
		JSONObject jsonParse = new JSONObject();
		this._state = (state.equals(State.ERROR)) ? "ERROR" : "SUCCEEDED";
		jsonParse.put("STATE", _state);
		jsonParse.put("MESSAGE", message);
		_json = new StringWriter();
		try {
			jsonParse.writeJSONString(_json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return _json.toString();
	}

	@SuppressWarnings("unchecked")
	public String nestedJson(State state, JSONObject message) {
		JSONObject jsonParse = new JSONObject();
		this._state = (state.equals(State.ERROR)) ? "ERROR" : "SUCCEEDED";
		jsonParse.put("STATE", _state);
		jsonParse.put("MESSAGE", message);
		_json = new StringWriter();
		try {
			jsonParse.writeJSONString(_json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return _json.toString();
	}

	
	
	public String parseJsonKeyword(String jsonInput, String keyword) {
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject temp = (JSONObject) jsonParser.parse(jsonInput);
			return (String) temp.get(keyword);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
