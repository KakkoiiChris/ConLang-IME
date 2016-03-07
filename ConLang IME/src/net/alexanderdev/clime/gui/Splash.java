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

import static javax.swing.UIManager.getCrossPlatformLookAndFeelClassName;
import static javax.swing.UIManager.setLookAndFeel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import net.alexanderdev.clime.util.FileSystem;

/**
 * @author Christian Bryce Alexander
 * @since Aug 14, 2015, 12:38:29 AM
 */
public class Splash extends JFrame {
	private static final long serialVersionUID = 8696402497315910914L;

	public Splash() {
		super();

		try {
			setLookAndFeel(getCrossPlatformLookAndFeelClassName());
			//setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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

		FileSystem.init();

		Image icon = null;

		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/img/icon.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		setIconImage(icon);

		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		Dimension d = new Dimension(640, 400);
		content.setMinimumSize(d);
		content.setPreferredSize(d);
		content.setMaximumSize(d);

		BufferedImage logo = null;

		try {
			logo = ImageIO.read(getClass().getResourceAsStream("/img/logo.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		content.add(new Logo(logo, IME.VERSION), BorderLayout.CENTER);

		add(content);

		setFocusable(false);
		setResizable(false);
		setUndecorated(true);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private class Logo extends JLabel {
		private static final long serialVersionUID = -6910836391828556694L;

		private BufferedImage image;
		private String version;

		private float zoom = 100f;

		public Logo(BufferedImage image, String version) {
			this.image = image;
			this.version = version;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			int w = image.getWidth();
			int h = image.getHeight();

			g2.drawImage(image, (w / 2) - (int) (w / 2 * zoom), (h / 2) - (int) (h / 2 * zoom), (int) (w * zoom),
				(int) (h * zoom), null);

			Font f = new Font("Consolas", Font.BOLD, 25);
			g2.setFont(f);
			g2.setColor(Color.WHITE);
			g2.drawString(version, 10, 390);

			zoom += (1f - zoom) * 0.01f;

			repaint();
		}
	}
}