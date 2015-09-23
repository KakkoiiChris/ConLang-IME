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

import static javax.swing.UIManager.getSystemLookAndFeelClassName;
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
			setLookAndFeel(getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		FileSystem.init();

		Image icon = null;

		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/img/icon.png"));
		} catch (IOException e) {
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
		} catch (IOException e) {
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

			zoom += (1f - zoom) * 0.005f;

			repaint();
		}
	}
}