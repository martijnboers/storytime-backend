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
package logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import exceptions.MissingPropertiesFile;

/**
 * @author martijn
 *
 */
public class LoggerLevel {
	String result = "";
	InputStream inputStream;

	public String getPropValues() throws IOException, MissingPropertiesFile {
		Properties prop = new Properties();
		String propFileName = "tosad.properties";
		inputStream = this.getClass().getClassLoader().getResource("init/tosad.properties").openStream();

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new MissingPropertiesFile("property file '" + propFileName + "' not found in the classpath");
		}
		return prop.getProperty("loggingLevel");
	}
}
