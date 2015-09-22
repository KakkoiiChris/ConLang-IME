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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 8:03:08 PM
 */
public class Editor extends JTabbedPane {
	private static final long serialVersionUID = -6729183148285169768L;

	private StyleDialog styleMenu = new StyleDialog(this);

	private LanguageDialog langMenu = new LanguageDialog(this);

	private IME ime;
	
	private StatusBar statusBar;

	public Editor(IME ime, StatusBar statusBar) {
		super(SwingConstants.TOP, SCROLL_TAB_LAYOUT);
		setPreferredSize(new Dimension(800, 600));
		addBlankTab();
		this.ime = ime;
		this.statusBar=statusBar;
	}
	
	public void update(String text) {
		statusBar.update(text);
	}

	public EditorTab getCurrentTab() {
		try {
			return (EditorTab) ((CloseButtonTab) getTabComponentAt(getSelectedIndex())).getComponent();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		JScrollPane scroll = new JScrollPane(component);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		super.addTab(title, icon, scroll, tip);
		setTabComponentAt(getTabCount() - 1, new CloseButtonTab(component, this, title, icon));
	}

	@Override
	public void addTab(String title, Icon icon, Component component) {
		addTab(title, icon, component, null);
	}

	@Override
	public void addTab(String title, Component component) {
		addTab(title, null, component);
	}

	public void addBlankTab() {
		addTab("untitled", new EditorTab(this));
	}

	public void addFilledTab(String title, String[] text) {
		addTab(title, new EditorTab(text));
	}

	public StyleDialog getStyleMenu() {
		return styleMenu;
	}

	public LanguageDialog getLangMenu() {
		return langMenu;
	}

	public JFrame getFrame() {
		return ime;
	}

	private class CloseButtonTab extends JPanel {
		private static final long serialVersionUID = 7257412809661116241L;

		private Component tab;

		public CloseButtonTab(final Component tab, JTabbedPane pane, String title, Icon icon) {
			this.tab = tab;

			setOpaque(false);
			setLayout(new FlowLayout(FlowLayout.CENTER, 3, 0));
			setVisible(true);

			add(new JLabel(title, icon, SwingConstants.LEFT));

			JButton button = new JButton();
			button.setIcon(new ImageIcon(getClass().getResource("/img/close.png")));
			button.setRolloverIcon(new ImageIcon(getClass().getResource("/img/closeR.png")));
			button.setPressedIcon(new ImageIcon(getClass().getResource("/img/closeP.png")));
			button.setMargin(new Insets(0, 0, 0, 0));
			button.addActionListener(e -> {
				pane.remove(tab.getParent().getParent());
			});
			add(button);
		}

		public Component getComponent() {
			return tab;
		}
	}
}