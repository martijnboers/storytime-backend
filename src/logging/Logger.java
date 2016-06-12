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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import logging.Level;

/**
 * @author martijn en een outsourcer uit India 
 * Abstract (Singleton) logger
 */

public class Logger {

	private static Logger logger = new Logger();
	private Level level;
	private String message;
	private int propLevel;

	private Logger() {
		LoggerLevel loggerLevel = new LoggerLevel();
		try {
			propLevel = Integer.valueOf(loggerLevel.getPropValues());
		} catch (Exception e) {
			System.out.println("Please check if you have the latest properties file");
			propLevel = 3;
		}
	}

	/**
	 * Use for default error logging, indicate logging level: {critical, error,
	 * informate} if none given default is INFORMATIVE
	 * 
	 * @param level
	 * @param er
	 * @param message
	 */
	public void out(Level level, String er, String message) {
		if (propLevel == 1 && (level == Level.ERROR || level == Level.INFORMATIVE)) {
			return;
		}

		if (propLevel == 2 && level == Level.INFORMATIVE) {
			return;
		}

		String timeNow = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
		this.level = (level.equals(null)) ? Level.INFORMATIVE : level;
		
		System.out.println(timeNow + ":" + level.toString() + " [" + er + "] " + message);
	}

	public static Logger getInstance() {
		return logger;
	}
}