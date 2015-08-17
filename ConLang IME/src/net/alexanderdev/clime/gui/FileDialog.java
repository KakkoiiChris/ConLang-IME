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
package net.alexanderdev.clime.gui;

import javax.swing.JFileChooser;

import net.alexanderdev.clime.util.FileIO;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 9:35:50 PM
 */
public class FileDialog {
	private static JFileChooser chooser;

	public static String[] openFile() {
		chooser = new JFileChooser();

		chooser.showOpenDialog(null);

		try {
			return FileIO.readFile(chooser.getSelectedFile());
		} catch (Exception e) {
			return new String[] {
				"ERROR!"
			};
		}
	}

	public static void saveFile(String text) {
		chooser = new JFileChooser();

		chooser.showSaveDialog(null);

		try {
			FileIO.writeFile(chooser.getSelectedFile(), text);
		} catch (Exception e) {
		}
	}
}