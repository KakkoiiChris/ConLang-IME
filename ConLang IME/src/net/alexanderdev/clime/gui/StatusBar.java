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

import javax.swing.JLabel;

/**
 * @author Christian Bryce Alexander
 * @since Sep 9, 2015, 10:53:36 PM
 */
public class StatusBar extends JLabel {
	private static final long serialVersionUID = 6030444605679916180L;

	private static final String DISPLAY = "CHARACTERS: %s  |  WORDS: %s  |  PARAGRAPHS: %s";

	public StatusBar() {
		super(String.format(DISPLAY, 0, 0, 0));
	}

	public void update(String text) {
		int chars = text.replace("\n", "").length();
		int words = text.replace("\n", "").split(" ").length;
		int paras = text.split("\n").length;

		setText(String.format(DISPLAY, chars, words, paras));
	}
}