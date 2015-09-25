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
	public static final File MAIN_DIR = new File("C:\\ConLang IME");
	public static final File LANGS = new File(MAIN_DIR.getPath() + "\\langs");

	public static void init() {
		if (!LANGS.exists())
			System.out.println("Languages Directory Created: " + LANGS.mkdirs());
	}
	
	public static void initPortable() {
		
	}
}