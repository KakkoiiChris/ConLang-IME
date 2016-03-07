/****************************************************************
 *   ____            _                        ___ __  __ _____  *
 *  / ___|___  _ __ | |    __ _ _ __   __ _  |_ _|  \/  | ____| *
 * | |   / _ \| '_ \| |   / _` | '_ \ / _` |  | || |\/| |  _|   *
 * | |__| (_) | | | | |__| (_| | | | | (_| |  | || |  | | |___  *
 *  \____\___/|_| |_|_____\__,_|_| |_|\__, | |___|_|  |_|_____| *
 *                                    |___/                     *
 *                                                              *
 * Copyright © 2015, Christian Bryce Alexander                  *
 ****************************************************************/
package net.alexanderdev.clime.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian Bryce Alexander
 * @since Mar 7, 2016, 3:12:16 PM
 */
public class CSV {
	public static String[] parseLine(String line) {
		List<String> data = new ArrayList<>();

		String token = "";

		boolean inSingleQuotes = false;
		boolean inDoubleQuotes = false;

		line += "\0";

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (!inDoubleQuotes && c == '\'') {
				inSingleQuotes = !inSingleQuotes;
				continue;
			}

			if (!inSingleQuotes && c == '"') {
				inDoubleQuotes = !inDoubleQuotes;
				continue;
			}

			if (!inSingleQuotes && !inDoubleQuotes && (c == ',' || c == '\0')) {
				data.add(token);
				token = "";
				continue;
			}

			token += c;
		}

		return data.toArray(new String[data.size()]);
	}
}