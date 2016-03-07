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
package net.alexanderdev.clime;

import net.alexanderdev.clime.gui.IME;
import net.alexanderdev.clime.gui.Splash;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 8:06:19 PM
 */
public class Main {
	public static void main(String[] args) {
		Splash s = new Splash();

		try {
			Thread.sleep(5000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		s.dispose();

		new IME();
	}
}