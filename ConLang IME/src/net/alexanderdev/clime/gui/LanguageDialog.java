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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import net.alexanderdev.clime.util.Language;
import net.alexanderdev.clime.util.XMLIO;

/**
 * @author Christian Bryce Alexander
 * @since Aug 13, 2015, 10:56:49 PM
 */
public class LanguageDialog extends JDialog {
	private static final long serialVersionUID = -6315814947970867682L;

	private Editor editor;

	private JPanel content, options;

	private JLabel forLang;

	private JComboBox<String> langs;

	private Map<String, Language> languages = new HashMap<>();

	private JButton create, edit, delete, use;

	public LanguageDialog(Editor editor) {
		super(editor.getFrame(), "Style Menu", false);

		this.editor = editor;

		setAlwaysOnTop(true);

		addWindowFocusListener(new WindowFocusListener() {
			@Override
			public void windowGainedFocus(WindowEvent e) {
			}

			@Override
			public void windowLostFocus(WindowEvent e) {
			}
		});

		Dimension d = new Dimension(20 + (25 * 12), 120);

		content = new JPanel();
		content.setMinimumSize(d);
		content.setPreferredSize(d);
		content.setMaximumSize(d);
		content.setLayout(null);

		forLang = new JLabel("Language:");
		Font f = forLang.getFont();
		forLang.setFont(new Font(f.getName(), f.getStyle(), 20));
		forLang.setBounds(10, 10, d.width - 20, 30);
		content.add(forLang);

		File folder = new File("C:\\Conlang IME\\langs");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			System.out.println(listOfFiles[i].getName());
			addLanguage(listOfFiles[i].getName());
		}

		String[] names = languages.keySet().toArray(new String[languages.size()]);

		langs = new JComboBox<>(names);
		f = langs.getFont();
		langs.setFont(new Font(f.getName(), f.getStyle(), 15));
		langs.setBounds(10, 50, d.width - 20, 25);
		content.add(langs);

		options = new JPanel();
		options.setLayout(new GridLayout(1, 4));

		create = new JButton("Create");
		f = create.getFont();
		create.setFont(new Font(f.getName(), f.getStyle(), 15));
		create.addActionListener(e -> {
		});
		options.add(create);

		edit = new JButton("Edit");
		f = edit.getFont();
		edit.setFont(new Font(f.getName(), f.getStyle(), 15));
		edit.addActionListener(e -> {
		});
		options.add(edit);

		delete = new JButton("Delete");
		f = delete.getFont();
		delete.setFont(new Font(f.getName(), f.getStyle(), 15));
		delete.addActionListener(e -> {
		});
		options.add(delete);

		use = new JButton("Use");
		f = use.getFont();
		use.setFont(new Font(f.getName(), f.getStyle(), 15));
		use.addActionListener(e -> {
			editor.getCurrentTab().setLanguage(languages.get((String)langs.getSelectedItem()));
		});
		options.add(use);

		options.setBounds(10, 85, d.width - 20, 25);
		content.add(options);

		setContentPane(content);
		setResizable(false);
		pack();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private void addLanguage(String name) {
		try {
			Document doc = XMLIO.read("C:\\ConLang IME\\langs\\" + name, false);

			Element lang = doc.getDocumentElement();

			String langName = lang.getAttribute("name");
			String fontName = lang.getAttribute("font");

			Element map = (Element) lang.getElementsByTagName("map").item(0);

			NodeList sets = map.getElementsByTagName("set");

			int max = 1;

			Map<String, String> langMap = new HashMap<>();

			for (int i = 0; i < sets.getLength(); i++) {
				Element set = (Element) sets.item(i);

				String key = set.getAttribute("key");
				String val = set.getAttribute("val");
				langMap.put(key, val);

				if (key.length() > max)
					max = key.length();
			}

			languages.put(langName, new Language(langName, fontName, langMap, max));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showDialog(boolean show) {
		if (show)
			setLocationRelativeTo(editor);

		setVisible(show);
	}
}