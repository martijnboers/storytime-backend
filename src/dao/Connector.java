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
package dao;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import exceptions.DatabaseException;
import exceptions.MissingPropertiesFile;
import logging.Level;
import logging.Logger;
import model.system.DbConfiguration;

/**
 * @author martijn
 *         <p>
 *         Connector class to MariaDB database
 *         <p>
 *         IMPORTANT: This class is only to be used from ConnectorFactory, every
 *         other connection should be requested from ConnectorFactory!
 */
public class Connector {
    private Logger log;
    private DatabaseProperties dbProp;
    private DbConfiguration config;
    private Connection connection = null;
    private boolean test = false;

    /**
     * Connector class that has the function to add own DBConfiguration. If this
     * parameter is null it will get the configuration file from disk. Consider
     * keeping this object in memmory instead of getting it from disk each call
     *
     * @param _config
     * @throws IOException
     * @throws DatabaseException
     */
    public Connector(DbConfiguration _config) throws IOException, DatabaseException {
        log = Logger.getInstance();
        try {
            dbProp = new DatabaseProperties();
            config = (_config == null) ? dbProp.getPropValues() : _config;
        } catch (Exception e) {
            // Geen properties file gevonden
            log.out(Level.ERROR, "Connector", "Database properties can't be found, serving local test db");
            test = true;
        }


        try {
            String connectString = (test) ? "jdbc:sqlite:testdatabase.sqlite"

                    :

                    "jdbc:mariadb://" + config.getHost() + ":" + config.getPort()
                            + "/" + config.getDatabase() + " " + config.getUser() + " " + config.getPassword();


            log.out(Level.INFORMATIVE, "Connector",
                    "Trying with connection string: " + connectString);

            connection = DriverManager.getConnection(connectString);

        } catch (Exception e) {
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
     * <p>
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