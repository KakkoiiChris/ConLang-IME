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

import net.alexanderdev.clime.util.Language;
import net.alexanderdev.clime.util.XMLIO;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 9:16:17 PM
 */
public class EditorTab extends JTextPane {
	private static final long serialVersionUID = -5613025029571082598L;

	public String langName;

	private Map<String, String> langMap = new HashMap<>();

	private int replaceSize;

	public EditorTab(Editor editor) {
		setDocument(new DefaultStyledDocument());

		Font f = getFont();
		setFont(new Font(f.getFontName(), f.getStyle(), 20));

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						System.exit(0);
						break;
					default:
						replace();
						editor.update(getText());
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

	public void setLanguage(Language language) {
		this.langName = language.getName();
		this.langMap = language.getLangMap();
		this.replaceSize = language.getReplaceSize();
		
		Font f = getFont();
		setFont(new Font(language.getFontName(), f.getStyle(), f.getSize()));
	}

	private void replace() {
		String text = getText();

		for (int j = 1; j <= replaceSize; j++) {
			int prevCaretPos = getCaretPosition();
			int prevTextLen = text.length();

			try {
				String seq = text.substring(prevCaretPos - j, prevCaretPos);
				String rep = langMap.get(seq);

				if (rep != null) {
					text = text.substring(0, prevCaretPos - j) + rep + text.substring(prevCaretPos);

					int displace = prevTextLen - text.length();

					setCaretPosition(prevCaretPos - displace);
				}
			} catch (Exception e) {
			}
		}

		int caretPos = getCaretPosition();

		setText(text);

		setCaretPosition(caretPos);
	}
}