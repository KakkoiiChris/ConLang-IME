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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 9:43:14 PM
 */
public class FileIO {
	/**
	 * Reads in a text file line by line
	 * 
	 * @param filename
	 *            Name of file to read in
	 * 
	 * @return A {@code String[]} containing each individual line of text
	 */
	public static String[] readFile(String filename) {
		ArrayList<String> lines = new ArrayList<>();

		InputStream inStream = FileIO.class.getResourceAsStream(filename);

		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

		String line;

		try {
			while ((line = reader.readLine()) != null)
				lines.add(line);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Reads in a text file line by line
	 * 
	 * @param filename
	 *            Name of file to read in
	 * 
	 * @return A {@code String[]} containing each individual line of text
	 */
	public static String[] readFile(File file) {
		ArrayList<String> lines = new ArrayList<>();

		InputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

		String line;

		try {
			while ((line = reader.readLine()) != null)
				lines.add(line);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Writes a text file line by line
	 */
	public static void writeFile(String filename, String[] lines) {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));

			for (String line : lines) {
				writer.write(line);
				writer.newLine();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void writeFile(File file, String text) {
		BufferedWriter writer = null;

		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));

			writer.write(text);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				writer.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}