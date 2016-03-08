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

import java.awt.Color;

import javax.swing.UIManager;

/**
 * @author Christian Bryce Alexander
 * @since Mar 7, 2016, 8:51:48 PM
 */
public class Themes {
	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		final Color FORE_A  = Color.WHITE;
		final Color BACK_A  = new Color(0x91008d);
		final Color BACK_AS = BACK_A.darker();
		final Color FORE_B  = Color.BLACK;
		final Color BACK_B  = new Color(0xffb700);
		final Color ACCEL   = new Color(0xdddddd);
		final Color ACC_A   = new Color(0x888888);
		final Color ACC_B   = new Color(0xffaafd);

		UIManager.put("MenuBar.foreground",                                 FORE_A);
		UIManager.put("MenuBar.background",                                 BACK_A);
		UIManager.put("MenuBar.opaque",                                     true);
		UIManager.put("Menu.foreground",                                    FORE_A);
		UIManager.put("Menu.background",                                    BACK_A);
		UIManager.put("Menu.selectionForeground",                           FORE_A);
		UIManager.put("Menu.selectionBackground",                           BACK_AS);
		UIManager.put("Menu.opaque",                                        true);
		UIManager.put("MenuItem.foreground",                                FORE_A);
		UIManager.put("MenuItem.background",                                BACK_A);
		UIManager.put("MenuItem.selectionForeground",                       FORE_A);
		UIManager.put("MenuItem.selectionBackground",                       BACK_AS);
		UIManager.put("MenuItem.acceleratorForeground",                     ACCEL);
		UIManager.put("MenuItem.acceleratorSelectionForeground",            ACCEL);
		UIManager.put("MenuItem.opaque",                                    true);
		UIManager.put("CheckBoxMenuItem.foreground",                        FORE_A);
		UIManager.put("CheckBoxMenuItem.background",                        BACK_A);
		UIManager.put("CheckBoxMenuItem.selectionForeground",               FORE_A);
		UIManager.put("CheckBoxMenuItem.selectionBackground",               BACK_AS);
		UIManager.put("CheckBoxMenuItem.acceleratorForeground",             ACCEL);
		UIManager.put("CheckBoxMenuItem.acceleratorSelectionForeground",    ACCEL);
		UIManager.put("CheckBoxMenuItem.opaque",                            true);
		UIManager.put("RadioButtonMenuItem.foreground",                     FORE_A);
		UIManager.put("RadioButtonMenuItem.background",                     BACK_A);
		UIManager.put("RadioButtonMenuItem.selectionForeground",            FORE_A);
		UIManager.put("RadioButtonMenuItem.selectionBackground",            BACK_AS);
		UIManager.put("RadioButtonMenuItem.acceleratorForeground",          ACCEL);
		UIManager.put("RadioButtonMenuItem.acceleratorSelectionForeground", ACCEL);
		UIManager.put("RadioButtonMenuItem.opaque",                         true);
		UIManager.put("Button.foreground",                                  FORE_A);
		UIManager.put("Button.background",                                  BACK_A);
		UIManager.put("Button.select",                                      BACK_AS);
		UIManager.put("Label.foreground",                                   FORE_B);
		UIManager.put("Label.background",                                   BACK_B);
		UIManager.put("Label.opaque",                                       true);
		UIManager.put("TabbedPane.foreground",                              FORE_B);
		UIManager.put("TabbedPane.background",                              BACK_B);
		UIManager.put("TabbedPane.opaque",                                  false);
		UIManager.put("Table.selectionBackground",                          ACC_B);
		UIManager.put("TableHeader.foreground",                             FORE_A);
		UIManager.put("TableHeader.background",                             ACC_A);
		UIManager.put("ScrollBar.background",new Color(0x888888));
	}
}