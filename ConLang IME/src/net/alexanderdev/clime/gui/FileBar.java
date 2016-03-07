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
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import net.alexanderdev.clime.gui.lang.LanguageMenu;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 7:52:28 PM
 */
public class FileBar extends JMenuBar {
	private static final long serialVersionUID = -7517657275793776841L;

	private JMenu file, edit, view, options, help, show;
	private JMenuItem newFile, openFile, saveFile, importFile, exportFile, quit, about, faq;
	private JCheckBoxMenuItem style, enableIME, fullscreen;

	public FileBar(IME ime, Editor editor) {
		file = new JMenu("FILE");

		newFile = new JMenuItem("NEW");
		newFile.addActionListener(e -> editor.addBlankTab());
		newFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(newFile);

		openFile = new JMenuItem("OPEN");
		openFile.addActionListener(e -> editor.addFilledTab("opened", FileDialog.openFile()));
		openFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(openFile);

		saveFile = new JMenuItem("SAVE");
		saveFile.addActionListener(e -> {
			EditorTab tab;

			if ((tab = editor.getCurrentTab()) != null)
				FileDialog.saveFile(tab.getText());
		});
		saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(saveFile);

		file.add(new JSeparator());

		importFile = new JMenuItem("IMPORT");
		importFile.setEnabled(false);
		importFile.addActionListener(e -> editor.addFilledTab("opened", FileDialog.openFile()));
		importFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(importFile);

		exportFile = new JMenuItem("EXPORT");
		exportFile.setEnabled(false);
		exportFile.addActionListener(e -> editor.addFilledTab("opened", FileDialog.openFile()));
		exportFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK, false));
		file.add(exportFile);

		file.add(new JSeparator());

		quit = new JMenuItem("QUIT");
		quit.addActionListener(e -> {
			Toolkit.getDefaultToolkit().beep();
			int choice = JOptionPane.showConfirmDialog(null, "You have unsaved documents. Save changes?", "",
				JOptionPane.YES_NO_CANCEL_OPTION);

			switch (choice) {
				case JOptionPane.YES_OPTION:
					for (EditorTab tab : editor.getAllTabs())
						FileDialog.saveFile(tab.getText());

					System.exit(0);
					break;
				case JOptionPane.NO_OPTION:
					System.exit(0);
					break;
				case JOptionPane.CANCEL_OPTION:
					break;
			}
		});
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false));
		file.add(quit);

		add(file);

		edit = new JMenu("EDIT");
		add(edit);

		view = new JMenu("VIEW");

		fullscreen = new JCheckBoxMenuItem("FULLSCREEN");
		fullscreen.addItemListener(e -> ime.toggleFullscreen(fullscreen.isSelected()));
		fullscreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0, false));
		view.add(fullscreen);

		show = new JMenu("SHOW VIEW");

		style = new JCheckBoxMenuItem("STYLE");
		style.setEnabled(false);
		style.addActionListener(e -> editor.getStyleMenu().showDialog(style.isSelected()));
		style.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK, false));
		show.add(style);

		view.add(show);

		add(view);

		options = new JMenu("OPTIONS");

		options.add(new LanguageMenu(editor));

		enableIME = new JCheckBoxMenuItem("ENABLE IME");
		enableIME.addActionListener(e -> {
			if (editor.getCurrentTab() != null)
				editor.getCurrentTab().enableIME(enableIME.isSelected());
		});
		enableIME.setSelected(true);
		enableIME.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0, false));
		options.add(enableIME);

		add(options);

		help = new JMenu("HELP");

		about = new JMenuItem("ABOUT");
		about.setEnabled(false);
		help.add(about);

		faq = new JMenuItem("FAQ");
		faq.setEnabled(false);
		help.add(faq);

		add(help);
	}

	public void setEnableIMESelected(boolean selected) {
		enableIME.setSelected(selected);
	}
}