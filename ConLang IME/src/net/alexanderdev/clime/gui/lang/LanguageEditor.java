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
package net.alexanderdev.clime.gui.lang;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Christian Bryce Alexander
 * @since Mar 2, 2016, 7:04:52 PM
 */
public class LanguageEditor extends JDialog {
	private static final long serialVersionUID = 2516043559420978373L;

	private static LanguageEditor instance;

	private JFrame parent;

	private LanguageEditor(JFrame parent) {
		super(parent, "Language Editor", true);
		this.parent = parent;
	}

	private void setContent(JPanel content) {
		setContentPane(content);
		setResizable(false);
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);
	}

	public static void showCreateDialog(JFrame parent) {
		instance = new LanguageEditor(parent);

		instance.setContent(new CreatorPanel(instance));
	}

	public static void showCSVImportDialog(JFrame parent, String[] lines) {
		instance = new LanguageEditor(parent);
		
		instance.setContent(new CreatorPanel(instance, lines));
	}

	public static void showEditDialog(JFrame parent) {
	}

	public static void showDeleteDialog(JFrame parent) {
	}
}