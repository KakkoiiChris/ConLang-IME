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

import java.util.Map;

/**
 * @author Christian Bryce Alexander
 * @since Sep 21, 2015, 11:51:37 PM
 */
public class Language {
	private String name;
	private String fontName;
	private Map<String, String> langMap;
	private int replaceSize;

	public Language(String name, String fontName, Map<String, String> langMap, int replaceSize) {
		this.name = name;
		this.fontName = fontName;
		this.langMap = langMap;
		this.replaceSize = replaceSize;
	}

	public String getName() {
		return name;
	}

	public String getFontName() {
		return fontName;
	}

	public Map<String, String> getLangMap() {
		return langMap;
	}

	public int getReplaceSize() {
		return replaceSize;
	}
}