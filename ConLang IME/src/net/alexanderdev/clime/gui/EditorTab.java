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

import static net.alexanderdev.clime.gui.StyleDialog.BOLD;
import static net.alexanderdev.clime.gui.StyleDialog.ITALIC;
import static net.alexanderdev.clime.gui.StyleDialog.STRIKE;
import static net.alexanderdev.clime.gui.StyleDialog.SUB;
import static net.alexanderdev.clime.gui.StyleDialog.SUPER;
import static net.alexanderdev.clime.gui.StyleDialog.UNDER;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.alexanderdev.clime.util.XMLIO;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 9:16:17 PM
 */
public class EditorTab extends JTextPane {
	private static final long serialVersionUID = -5613025029571082598L;

	public String langName;

	private Map<String, String> langMap = new HashMap<>();

	private int maxReplaceSize;

	public EditorTab() {
		setDocument(new DefaultStyledDocument());
		setFont(new Font("Thaeonian", Font.PLAIN, 40));

		langMap.put("AA", "Ā");
		langMap.put("aa", "ā");
		langMap.put("EE", "Ē");
		langMap.put("ee", "ē");
		langMap.put("II", "Ī");
		langMap.put("ii", "ī");
		langMap.put("OO", "Ō");
		langMap.put("oo", "ō");
		langMap.put("UU", "Ū");
		langMap.put("uu", "ū");
		langMap.put("YY", "Ȳ");
		langMap.put("yy", "ȳ");
		langMap.put("Sh", "X");
		langMap.put("sh", "x");
		langMap.put("Zh", "Ź");
		langMap.put("zh", "ź");
		langMap.put("Th", "Θ");
		langMap.put("th", "θ");
		langMap.put("Yi", "Ŷ");
		langMap.put("yi", "ŷ");
		maxReplaceSize = 2;

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						System.exit(0);
						break;
					default:
						if (getText().length() >= maxReplaceSize)
							replace();
						break;
				}
			}
		});

		requestFocus();
	}

	public EditorTab(String[] text) {
		super();

		for (String line : text)
			setText(getText() + line + "\n");
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return (getSize().width < getParent().getSize().width);
	}

	@Override
	public void setSize(Dimension d) {
		if (d.width < getParent().getSize().width) {
			d.width = getParent().getSize().width;
		}

		super.setSize(d);
	}

	public void applyStyle(String font, int size, Color color, int styles) {
		StyledDocument doc = getStyledDocument();

		int start = getSelectionStart();
		int end = getSelectionEnd();

		if (start == end) {
			return;
		}

		if (start > end) {
			int life = start;
			start = end;
			end = life;
		}

		Style style = addStyle("MyHilite", null);

		StyleConstants.setFontFamily(style, font);
		StyleConstants.setFontSize(style, size);
		StyleConstants.setForeground(style, color);
		StyleConstants.setBold(style, (styles & BOLD) == BOLD);
		StyleConstants.setItalic(style, (styles & ITALIC) == ITALIC);
		StyleConstants.setUnderline(style, (styles & UNDER) == UNDER);
		StyleConstants.setStrikeThrough(style, (styles & STRIKE) == STRIKE);
		StyleConstants.setSubscript(style, (styles & SUB) == SUB);
		StyleConstants.setSuperscript(style, (styles & SUPER) == SUPER);

		doc.setCharacterAttributes(start, end - start, style, false);
	}

	public void setLanguage(String name) {
		try {
			Document doc = XMLIO.read("C:\\Program Files\\CLIME\\langs\\" + name + ".xml", false);

			langMap.clear();

			Element lang = doc.getDocumentElement();

			langName = lang.getAttribute("name");

			Font f = getFont();

			setFont(new Font(lang.getAttribute("font"), f.getStyle(), f.getSize()));

			Element map = (Element) lang.getElementsByTagName("map").item(0);

			NodeList sets = map.getElementsByTagName("set");

			int max = 1;

			for (int i = 0; i < sets.getLength(); i++) {
				Element set = (Element) sets.item(i);

				String key = set.getAttribute("key");
				String val = set.getAttribute("val");
				langMap.put(key, val);

				if (key.length() > max)
					max = key.length();
			}

			maxReplaceSize = max;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void replace() {
		String text = getText();

		for (int i = 0; i < text.length() - maxReplaceSize + 1; i++) {
			for (int j = 1; j <= maxReplaceSize; j++) {
				int prevCaretPos = getCaretPosition();
				int prevTextLen = text.length();

				try {
					String seq = text.substring(i, i + j);
					String rep;

					if ((rep = langMap.get(seq)) != null) {
						text = text.substring(0, i) + rep + text.substring(i + j);

						int displace = prevTextLen - text.length();

						setCaretPosition(prevCaretPos - displace);
					}
				} catch (Exception e) {
				}
			}
		}

		int caretPos = getCaretPosition();

		setText(text);

		setCaretPosition(caretPos);
	}
}