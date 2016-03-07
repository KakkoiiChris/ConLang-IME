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

import java.io.File;

/**
 * @author Christian Bryce Alexander
 * @since Aug 15, 2015, 2:23:28 AM
 */
public class FileSystem {
	private static File mainDir;
	private static File langs;
	private static File docs;

	public static void init() {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			mainDir = new File(System.getenv("APPDATA") + File.separator + "ConLang IME");
		}
		else if (os.contains("mac")) {
			mainDir = new File(System.getProperty("user.home") + "/Library/Application Support");
		}

		langs = new File(mainDir.getPath() + File.separator + "langs");

		if (!langs.exists())
			System.out.println("Languages Directory Created: " + langs.mkdirs());

		docs = new File(System.getProperty("user.home") + File.separator + "Documents");
	}

	public static File getLangs() {
		return langs;
	}

	public static File getDocs() {
		return docs;
	}

	public static void initPortable() {
	}
}