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

import com.google.gson.Gson;

import controller.Json;
import controller.Security;

/**
 * Super class for view. Please add commonly used vars and functions here for
 * other views
 * 
 * @author martijn
 *
 */
public class ViewSuper {
	protected Gson gson = new Gson();
	protected Json json = new Json();
	protected Security sec;
	
	public ViewSuper() throws Exception{
		sec = new Security();
	}

}
