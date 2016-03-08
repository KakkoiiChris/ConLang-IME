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
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 8:03:08 PM
 */
public class Editor extends JTabbedPane {
	private static final long serialVersionUID = -6729183148285169768L;

	private IME ime;

	private StatusBar statusBar;

	private BufferedImage bg;

	public Editor(IME ime, StatusBar statusBar) {
		super(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

		setPreferredSize(new Dimension(640,480));

		this.ime = ime;
		this.statusBar = statusBar;

		try {
			bg = ImageIO.read(getClass().getResourceAsStream("/img/tabBG.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(String text, boolean imeEnabled) {
		int totalCharacters = getCurrentTab().getDocument().getLength();
		
		int lineCount = (totalCharacters == 0) ? 1 : 0;

		try {
			int offset = totalCharacters;
			
			while (offset > 0) {
				offset = Utilities.getRowStart(getCurrentTab(), offset) - 1;
				lineCount++;
			}
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}

		statusBar.update(text, lineCount, getCurrentTab().getLangName(), imeEnabled);
	}

	public EditorTab getCurrentTab() {
		try {
			return (EditorTab) ((CloseButtonTab) getTabComponentAt(getSelectedIndex())).getComponent();
		}
		catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public EditorTab[] getAllTabs() {
		List<EditorTab> tabs = new ArrayList<>();

		for (int i = 0; i < getTabCount(); i++)
			tabs.add((EditorTab) ((CloseButtonTab) getTabComponentAt(i)).getComponent());

		return tabs.toArray(new EditorTab[tabs.size()]);
	}

	@Override
	public void addTab(String title, Icon icon, Component component, String tip) {
		JScrollPane scroll = new JScrollPane(component);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

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
		addTab("UNTITLED *", new EditorTab(this));
	}

	public void addFilledTab(String title, String[] text) {
		addTab(title, new EditorTab(text));
	}

	public IME getIME() {
		return ime;
	}

	public JFrame getFrame() {
		return ime;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
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
			button.setBackground(null);
			button.addActionListener(e -> pane.remove(tab.getParent().getParent()));
			add(button);
		}

		public Component getComponent() {
			return tab;
		}
	}
}