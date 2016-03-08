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

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * A utitlity class for retrieving information about the system's hardware.
 * 
 * @author Christian Bryce Alexander
 * @since Mar 2, 2016, 6:22:16 PM
 */
public class Environment {
	private static GraphicsEnvironment graphicsEnvironment;

	private static GraphicsDevice[] graphicsDevices;

	private static DisplayMode defaultDisplayMode;

	static {
		graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

		graphicsDevices = graphicsEnvironment.getScreenDevices();

		defaultDisplayMode = graphicsEnvironment.getDefaultScreenDevice().getDisplayMode();
	}

	/**
	 * @return The width of the default display
	 */
	public static int getPhysicalWidth() {
		return defaultDisplayMode.getWidth();
	}

	/**
	 * @return The height of the default display
	 */
	public static int getPhysicalHeight() {
		return defaultDisplayMode.getHeight();
	}

	/**
	 * @return The dimensions of the default display
	 */
	public static Dimension getPhysicalSize() {
		return new Dimension(defaultDisplayMode.getWidth(), defaultDisplayMode.getHeight());
	}

	/**
	 * @return The bit depth of the default display
	 */
	public static int getPhysicalBitDepth() {
		return defaultDisplayMode.getBitDepth();
	}

	/**
	 * @return The refresh rate of the default display
	 */
	public static int getPhysicalRefreshRate() {
		return defaultDisplayMode.getRefreshRate();
	}

	/**
	 * @return The number of the displays currently connected
	 */
	public static int getPhysicalDeviceCount() {
		return graphicsDevices.length;
	}

	/**
	 * @return The width of the nth display
	 */
	public static int getPhysicalWidth(int n) {
		return getDisplayMode(n).getWidth();
	}

	/**
	 * @return The height of the nth display
	 */
	public static int getPhysicalHeight(int n) {
		return getDisplayMode(n).getHeight();
	}

	/**
	 * @return The dimensions of the nth display
	 */
	public static Dimension getPhysicalSize(int n) {
		return new Dimension(getDisplayMode(n).getWidth(), getDisplayMode(n).getHeight());
	}

	/**
	 * @return The bit depth of the nth display
	 */
	public static int getPhysicalBitDepth(int n) {
		return getDisplayMode(n).getBitDepth();
	}

	/**
	 * @return The refresh rate of the nth display
	 */
	public static int getPhysicalRefreshRate(int n) {
		return getDisplayMode(n).getRefreshRate();
	}

	/**
	 * @return The width of the nth display
	 */
	private static DisplayMode getDisplayMode(int n) {
		return graphicsDevices[n].getDisplayMode();
	}

	public static Font[] getAllFonts() {
		return graphicsEnvironment.getAllFonts();
	}

	public static String[] getAllFontNames() {
		return graphicsEnvironment.getAvailableFontFamilyNames();
	}
}