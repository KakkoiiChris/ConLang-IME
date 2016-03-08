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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 7:45:12 PM
 */
public class IME extends JFrame {
	private static final long serialVersionUID = 6961165635772608059L;

	public static final String TITLE = "ConLang IME";
	public static final String VERSION = "v1.9.1 alpha";

	private FileBar fileBar;
	private StyleToolBar styleToolBar;
	private Editor editor;
	private StatusBar statusBar;

	private Dimension previousSize;
	private Point previousLocation;

	public IME() {
		super(String.format("%s %s: [untitled]", TITLE, VERSION));

		setLayout(new BorderLayout());

		statusBar = new StatusBar();
		add(statusBar, BorderLayout.SOUTH);

		editor = new Editor(this, statusBar);
		add(editor, BorderLayout.CENTER);

		//styleToolBar = new StyleToolBar(editor);
		//add(styleToolBar, BorderLayout.NORTH);

		fileBar = new FileBar(this, editor);
		setJMenuBar(fileBar);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// Toolkit.getDefaultToolkit().beep();
			}
		});

		Image icon = null;

		try {
			icon = ImageIO.read(getClass().getResourceAsStream("/img/icon.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		setIconImage(icon);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		setMinimumSize(new Dimension(getWidth(), getHeight()));
	}

	public void toggleStyle(boolean selected) {
		styleToolBar.setVisible(selected);
	}

	public void toggleStatusBar(boolean selected) {
		statusBar.setVisible(selected);
	}

	public void toggleFullscreen(boolean selected) {
		if (selected) {
			previousSize = getContentPane().getSize();
			previousLocation = this.getLocationOnScreen();
			dispose();
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}
		else {
			dispose();
			setExtendedState(JFrame.NORMAL);
			setUndecorated(false);
			getContentPane().setPreferredSize(previousSize);
			getContentPane().setMinimumSize(previousSize);
			this.setLocation(previousLocation);
			this.pack();
		}

		setVisible(true);
	}
}