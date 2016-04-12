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

package dao;

import java.sql.Connection;

import logging.Logger;

/**
 * 
 * @author martijn
 *
 *         This class can be used to parent functions that will be benificial to
 *         all children DAO's. Function like 'Does x exist'.
 */
public class DataAccesObject {
	protected Logger log = Logger.getInstance();
	protected Connection con;

	public DataAccesObject() {
		try {
			con = ConnectorFactory.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}