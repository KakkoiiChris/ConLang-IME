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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Christian Bryce Alexander
 * @since Jul 20, 2015, 12:03:57 AM
 */
public class StyleDialog extends JDialog {
	private static final long serialVersionUID = -6100716103624014402L;

	public static final int NONE = 0x00;
	public static final int BOLD = 0x01;
	public static final int ITALIC = 0x02;
	public static final int UNDER = 0x04;
	public static final int STRIKE = 0x08;
	public static final int SUB = 0x10;
	public static final int SUPER = 0x20;

	private Editor editor;

	private JPanel content;

	private JLabel forFont, forColor, forStyle;
	private JComboBox<String> fonts;
	private JComboBox<Integer> sizes;
	private JButton[][] colors;
	private JCheckBox[] styles;

	private String font = "Consolas";
	private int size = 16;
	private Color color = Color.BLACK;
	private int style = NONE;

	public StyleDialog(Editor editor) {
		super(editor.getFrame(), "Style Menu", false);

		this.editor = editor;

		setAlwaysOnTop(true);

		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				color = Color.BLACK;
				style = NONE;

				for (JCheckBox style : styles)
					style.setSelected(false);
			}
		});

		Dimension d = new Dimension(20 + (25 * 12), 290);

		content = new JPanel();
		content.setMinimumSize(d);
		content.setPreferredSize(d);
		content.setMaximumSize(d);
		content.setLayout(null);

		forFont = new JLabel("Font:");
		Font f = forFont.getFont();
		forFont.setFont(new Font(f.getName(), f.getStyle(), 20));
		forFont.setBounds(10, 10, d.width - 20, 20);
		content.add(forFont);

		String fontNames[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		fonts = new JComboBox<>(fontNames);
		fonts.setSelectedItem("Consolas");
		fonts.addItemListener(e -> {
			font = (String) e.getItem();
			apply();
		});
		fonts.setBounds(10, 40, (3 * (d.width - 20)) / 4, 20);
		content.add(fonts);

		Integer[] s = new Integer[100];
		for (int i = 0; i < s.length; i++)
			s[i] = i + 1;

		sizes = new JComboBox<>(s);
		sizes.setSelectedItem(16);
		sizes.addItemListener(e -> {
			size = (Integer) e.getItem();
			apply();
		});
		sizes.setBounds(10 + ((3 * (d.width - 20)) / 4), 40, (d.width - 20) / 4, 20);
		content.add(sizes);

		forColor = new JLabel("Color:");
		f = forColor.getFont();
		forColor.setFont(new Font(f.getName(), f.getStyle(), 20));
		forColor.setBounds(10, 70, d.width - 20, 20);
		content.add(forColor);

		int[][] c = {
			{
				0xff8888, 0xffcc88, 0xffff88, 0xccff88, 0x88ff88, 0x88ffcc, 0x88ffff, 0x88ccff, 0x8888ff, 0xcc88ff,
				0xff88ff, 0xff88cc
			}, {
				0xff0000, 0xff8800, 0xffff00, 0x88ff00, 0x00ff00, 0x00ff88, 0x00ffff, 0x0088ff, 0x0000ff, 0x8800ff,
				0xff00ff, 0xff0088
			}, {
				0x880000, 0x884400, 0x888800, 0x448800, 0x008800, 0x008844, 0x008888, 0x004488, 0x000088, 0x440088,
				0x880088, 0x880044
			}, {
				0x000000, 0x171717, 0x2e2e2e, 0x464646, 0x5d5d5d, 0x747474, 0x8b8b8b, 0xa2a2a2, 0xb9b9b9, 0xd1d1d1,
				0xe8e8e8, 0xffffff
			}
		};

		BufferedImage palette = null;
		BufferedImage paletteR = null;
		BufferedImage paletteP = null;

		try {
			palette = ImageIO.read(getClass().getResourceAsStream("/img/palette.png"));
			paletteR = ImageIO.read(getClass().getResourceAsStream("/img/paletteR.png"));
			paletteP = ImageIO.read(getClass().getResourceAsStream("/img/paletteP.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		colors = new JButton[c.length][c[0].length];

		for (int y = 0; y < c.length; y++) {
			for (int x = 0; x < c[0].length; x++) {
				colors[y][x] = new JButton();

				colors[y][x].setBounds(10 + (x * 25), 100 + (y * 25), 25, 25);

				colors[y][x].setBackground(new Color(c[y][x]));

				colors[y][x].setIcon(new ImageIcon(palette.getSubimage(x * 25, y * 25, 25, 25)));
				colors[y][x].setRolloverIcon(new ImageIcon(paletteR.getSubimage(x * 25, y * 25, 25, 25)));
				colors[y][x].setPressedIcon(new ImageIcon(paletteP.getSubimage(x * 25, y * 25, 25, 25)));

				colors[y][x].addActionListener(this::changeColor);

				content.add(colors[y][x]);
			}
		}

		forStyle = new JLabel("Style:");
		f = forStyle.getFont();
		forStyle.setFont(new Font(f.getName(), f.getStyle(), 20));
		forStyle.setBounds(10, 210, d.width - 20, 20);
		content.add(forStyle);

		int[] st = {
			BOLD, ITALIC, UNDER, STRIKE, SUB, SUPER
		};

		String[] n = {
			"Bold", "Italic", "Underline", "Strike Thru", "Subscript", "Superscript"
		};

		styles = new JCheckBox[s.length];

		for (int i = 0; i < st.length; i++) {
			int x = i % 3;
			int y = i / 3;

			styles[i] = new JCheckBox(n[i]);
			styles[i].setBounds(10 + (x * 100), 240 + (y * 25), 100, 25);
			styles[i].setName(st[i] + "");
			styles[i].addItemListener(this::changeStyle);
			content.add(styles[i]);
		}

		setContentPane(content);
		setResizable(false);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void showDialog(boolean show) {
		if (show)
			setLocationRelativeTo(editor);

		setVisible(show);
	}

	private void changeColor(ActionEvent e) {
		color = ((JButton) e.getSource()).getBackground();
		apply();
	}

	private void changeStyle(ItemEvent e) {
		int s = NONE;

		for (JCheckBox style : styles)
			if (style.isSelected())
				s |= Integer.parseInt(style.getName());

		style = s;
		apply();
	}

	private void apply() {
		editor.getCurrentTab().applyStyle(font, size, color, style);
	}
}