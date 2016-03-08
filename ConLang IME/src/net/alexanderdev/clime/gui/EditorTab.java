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

import net.alexanderdev.clime.util.Language;

/**
 * @author Christian Bryce Alexander
 * @since Jul 19, 2015, 9:16:17 PM
 */
public class EditorTab extends JTextPane {
	private static final long serialVersionUID = -5613025029571082598L;

	private String langName;

	private Map<String, String> langMap = new HashMap<>();
	private Map<String, String> escapes = new HashMap<>();

	private int replaceSize;

	private boolean imeEnabled = true;

	private Editor editor;

	public EditorTab(Editor editor) {
		this.editor = editor;

		setDocument(new DefaultStyledDocument());

		Font f = getFont();
		setFont(new Font(f.getFontName(), f.getStyle(), 20));

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				editor.update(getText(), imeEnabled);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (imeEnabled)
					if (isPrintable(e.getKeyCode()))
						replace();
			}
		});

		requestFocus();
	}

	public EditorTab(String[] text) {
		super();

		for (String line : text)
			setText(getText() + line + "\n");
	}

	private boolean isPrintable(int keycode) {
		return !(keycode == KeyEvent.VK_LEFT      || keycode == KeyEvent.VK_RIGHT     || keycode == KeyEvent.VK_UP
			  || keycode == KeyEvent.VK_DOWN      || keycode == KeyEvent.VK_ENTER     || keycode == KeyEvent.VK_TAB
			  || keycode == KeyEvent.VK_CAPS_LOCK || keycode == KeyEvent.VK_DELETE    || keycode == KeyEvent.VK_BACK_SPACE
			  || keycode == KeyEvent.VK_ENTER     || keycode == KeyEvent.VK_SHIFT     || keycode == KeyEvent.VK_CONTROL
			  || keycode == KeyEvent.VK_ALT       || keycode == KeyEvent.VK_ALT_GRAPH || keycode == KeyEvent.VK_F1
			  || keycode == KeyEvent.VK_F2        || keycode == KeyEvent.VK_F3        || keycode == KeyEvent.VK_F4
			  || keycode == KeyEvent.VK_F5        || keycode == KeyEvent.VK_F6        || keycode == KeyEvent.VK_F7
			  || keycode == KeyEvent.VK_F8        || keycode == KeyEvent.VK_F9        || keycode == KeyEvent.VK_F10
			  || keycode == KeyEvent.VK_F11       || keycode == KeyEvent.VK_F12       || keycode == KeyEvent.VK_HOME
			  || keycode == KeyEvent.VK_END       || keycode == KeyEvent.VK_PAGE_UP   || keycode == KeyEvent.VK_PAGE_DOWN);
	}

	@SuppressWarnings("unused")
	private boolean modified(KeyEvent e) {
		return e.isControlDown() || e.isAltDown() || e.isAltGraphDown() || e.isMetaDown() || e.isShiftDown();
	}

	public void applyStyle(String font, int size, Color color, int styles) {
		StyledDocument doc = getStyledDocument();

		int start = getSelectionStart();
		int end = getSelectionEnd();

		if (start == end)
			return;

		if (start > end) {
			int life = start;
			start    = end;
			end      = life;
		}

		Style style = addStyle("MyHilite", null);

		StyleConstants.setFontFamily(style, font);
		StyleConstants.setFontSize  (style, size);
		StyleConstants.setForeground(style, color);

		StyleConstants.setBold         (style, (styles & BOLD)   == BOLD);
		StyleConstants.setItalic       (style, (styles & ITALIC) == ITALIC);
		StyleConstants.setUnderline    (style, (styles & UNDER)  == UNDER);
		StyleConstants.setStrikeThrough(style, (styles & STRIKE) == STRIKE);
		StyleConstants.setSubscript    (style, (styles & SUB)    == SUB);
		StyleConstants.setSuperscript  (style, (styles & SUPER)  == SUPER);

		doc.setCharacterAttributes(start, end - start, style, false);
	}

	public String getLangName() {
		return langName;
	}

	public void setLanguage(Language language) {
		if (language == null) {
			this.langName = "NONE";
			enableIME(false);
			((FileBar) editor.getIME().getJMenuBar()).setEnableIMESelected(false);
			return;
		}
		else {
			enableIME(true);
			((FileBar) editor.getIME().getJMenuBar()).setEnableIMESelected(true);
		}

		this.langName    = language.getName();
		this.langMap     = language.getLangMap();
		this.escapes     = language.getEscapes();
		this.replaceSize = language.getReplaceSize();

		Font f = getFont();
		setFont(new Font(language.getFontName(), f.getStyle(), language.getFontSize()));
	}

	public void enableIME(boolean enable) {
		imeEnabled = enable;
		editor.update(getText(), imeEnabled);
	}

	private void replace() {
		String text = getText();

		for (int j = 2; j <= replaceSize + 1; j++) {
			int prevCaretPos = getCaretPosition();
			int prevTextLen  = text.length();

			try {
				String seq = text.substring(prevCaretPos - j, prevCaretPos);
				String rep = escapes.get(seq);

				if (rep != null) {
					text = text.substring(0, prevCaretPos - j) + rep + text.substring(prevCaretPos);

					int displace = prevTextLen - text.length();
					setText(text);
					setCaretPosition(prevCaretPos - displace);

					return;
				}
			}
			catch (Exception e) {
			}
		}

		for (int j = replaceSize; j >= 1; j--) {
			int prevCaretPos = getCaretPosition();
			int prevTextLen  = text.length();

			try {
				String seq = text.substring(prevCaretPos - j, prevCaretPos);
				String rep = langMap.get(seq);

				if (rep != null) {
					text = text.substring(0, prevCaretPos - j) + rep + text.substring(prevCaretPos);

					int displace = prevTextLen - text.length();
					setText(text);
					setCaretPosition(prevCaretPos - displace);

					return;
				}
			}
			catch (Exception e) {
			}
		}
	}
}