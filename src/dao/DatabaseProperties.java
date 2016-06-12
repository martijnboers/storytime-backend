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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import exceptions.MissingPropertiesFile;
import logging.Level;
import logging.Logger;
import model.system.DbConfiguration;

/**
 * @author martijn
 *         <p>
 *         Get database properties
 */
public class DatabaseProperties {
    String result = "";
    InputStream inputStream;

    /**
     * Fetches database configuratioin to be used in the connector
     *
     * @return DbConfiguration
     * @throws IOException
     * @throws MissingPropertiesFile
     */
    public DbConfiguration getPropValues() throws IOException, MissingPropertiesFile {
        Logger log = Logger.getInstance();
        DbConfiguration configuration = null;
        Properties prop = new Properties();
        String propFileName = "init/storytime.properties";
        inputStream = this.getClass().getClassLoader().getResource(propFileName).openStream();

        if (inputStream != null) {
            prop.load(inputStream);
        } else {
            log.out(Level.CRITICAL, "DatabaseProperties", "property file '" + propFileName + "' not found in the classpath");
            throw new MissingPropertiesFile("property file '" + propFileName + "' not found in the classpath");
        }
        configuration = new DbConfiguration(prop.getProperty("host"), prop.getProperty("user"), prop.getProperty("password"),
                prop.getProperty("database"), prop.getProperty("port"), (prop.getProperty("production") == "true") ? true : false);

        inputStream.close();
        return configuration;
    }
}
