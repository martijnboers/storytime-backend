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

/**
 * @author martijn
 * 
 * Handles all security functions
 *
 */

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;


public class Security {
	/**
	 * Escape HTML characters
	 * 
	 * @param in
	 * @return
	 */
	public String escape(String in) {
		return escapeHtml4(in);
	}
}
