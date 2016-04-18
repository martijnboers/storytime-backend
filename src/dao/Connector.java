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

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import exceptions.DatabaseException;
import exceptions.MissingPropertiesFile;
import logging.Level;
import logging.Logger;
import model.system.DbConfiguration;

/**
 * @author martijn
 * 
 *         Connector class to MariaDB database
 * 
 *         IMPORTANT: This class is only to be used from ConnectorFactory, every
 *         other connection should be requested from ConnectorFactory!
 *
 */
public class Connector {
	private Logger log;
	private DatabaseProperties dbProp;
	private DbConfiguration config;
	private Connection connection = null;

	/**
	 * Connector class that has the function to add own DBConfiguration. If this
	 * parameter is null it will get the configuration file from disk. Consider
	 * keeping this object in memmory instead of getting it from disk each call
	 * 
	 * @param _config
	 * @throws IOException
	 * @throws DatabaseException
	 * @throws MissingPropertiesFile
	 */
	public Connector(DbConfiguration _config) throws IOException, DatabaseException, MissingPropertiesFile {
		log = Logger.getInstance();
		dbProp = new DatabaseProperties();
		config = (_config == null) ? dbProp.getPropValues() : _config;

		try {
			log.out(Level.INFORMATIVE, "Connector",
					"Trying with connection string: " + "jdbc:mariadb://" + config.getHost() + ":" + config.getPort()
							+ "/" + config.getDatabase() + " " + config.getUser() + " " + config.getPassword());
			connection = DriverManager.getConnection(
					"jdbc:mariadb://" + config.getHost() + ":" + config.getPort() + "/" + config.getDatabase(),
					config.getUser(), config.getPassword());

		} catch (SQLException e) {
			log.out(Level.CRITICAL, "Connector", "Error invalid database connection");
			DatabaseException exception = new DatabaseException("Error invalid database connection");
			throw exception;
		}
		if (connection != null) {
			log.out(Level.INFORMATIVE, "Connector", "Connection succeeded");
		} else {
			log.out(Level.CRITICAL, "Connector", "Can't connect to database");
		}
	}

	/**
	 * Get Connection object
	 * 
	 * @return Connection connection object
	 * @throws SQLException
	 * @throws ConnectionClosedException
	 */
	public Connection getConnection() throws Exception {
		if (!connection.isClosed()) {
			return connection;
		} else {
			throw new Exception("Connection has been closed");
		}
	}

	/**
	 * Gets connection status for current connection
	 * 
	 * True means open
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean getConnectionStatus() throws SQLException {
		return !connection.isClosed();
	}

	/**
	 * Gets configuration
	 * 
	 * @return
	 */
	public DbConfiguration getConfiguration() {
		return config;
	}
}