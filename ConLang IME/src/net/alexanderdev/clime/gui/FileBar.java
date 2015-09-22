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

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 7:52:28 PM
 */
public class FileBar extends JMenuBar {
	private static final long serialVersionUID = -7517657275793776841L;

	private JMenu file, edit, view, options;
	private JMenuItem newFile, openFile, saveFile, quit;

	private JCheckBoxMenuItem fullscreen;
	private JMenu show;
	private JCheckBoxMenuItem style, lang;

	public FileBar(IME ime, Editor editor) {
		file = new JMenu("File");

		newFile = new JMenuItem("New");
		newFile.addActionListener(e -> {
			editor.addBlankTab();
		});
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(newFile);

		openFile = new JMenuItem("Open");
		openFile.addActionListener(e -> {
			String[] text = FileDialog.openFile();
			editor.addFilledTab("opened", text);
		});
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(openFile);

		saveFile = new JMenuItem("Save");
		saveFile.addActionListener(e -> {
			EditorTab tab;

			if ((tab = editor.getCurrentTab()) != null)
				FileDialog.saveFile(tab.getText());
		});
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(saveFile);

		file.add(new JSeparator());

		quit = new JMenuItem("Quit");
		quit.addActionListener(e -> {
			Toolkit.getDefaultToolkit().beep();
			System.exit(0);
		});
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false));
		file.add(quit);

		add(file);

		edit = new JMenu("Edit");
		add(edit);

		view = new JMenu("View");

		fullscreen = new JCheckBoxMenuItem("Fullscreen");
		fullscreen.addItemListener(e -> {
			ime.toggleFullscreen(fullscreen.isSelected());
		});
		fullscreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, false));
		view.add(fullscreen);

		show = new JMenu("Show View");

		style = new JCheckBoxMenuItem("Style");
		style.addActionListener(e -> {
			editor.getStyleMenu().showDialog(style.isSelected());
		});
		style.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK, false));
		show.add(style);

		lang = new JCheckBoxMenuItem("Language");
		lang.addActionListener(e -> {
			editor.getLangMenu().showDialog(lang.isSelected());
		});
		lang.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.ALT_DOWN_MASK, false));
		show.add(lang);

		view.add(show);

		add(view);

		options = new JMenu("Options");
		add(options);
	}
}