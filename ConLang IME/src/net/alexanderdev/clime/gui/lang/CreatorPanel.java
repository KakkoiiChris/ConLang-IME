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
package net.alexanderdev.clime.gui.lang;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.alexanderdev.clime.util.CSV;
import net.alexanderdev.clime.util.Environment;
import net.alexanderdev.clime.util.FileSystem;
import net.alexanderdev.clime.util.XMLIO;

/**
 * @author Christian Bryce Alexander
 * @since Mar 2, 2016, 7:17:12 PM
 */
public class CreatorPanel extends JPanel {
	private static final long serialVersionUID = -6544949762532705851L;

	public static final int WIDTH = 640, HEIGHT = 480;

	private JLabel forName, forFont, forSize, forCopy;

	private JTextField name, copy;

	private JComboBox<String> font;

	private JSpinner size;

	private JTabbedPane sections;

	private JButton save;

	private CharMapPanel cmp;

	private DictionaryPanel dp;

	public CreatorPanel(JDialog parent) {
		super(null);

		Dimension d = new Dimension(WIDTH, HEIGHT);

		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);

		forName = new JLabel("Name:");
		forName.setOpaque(true);
		forName.setHorizontalAlignment(SwingConstants.CENTER);
		forName.setBounds(0, 0, WIDTH / 8, HEIGHT / 16);
		add(forName);

		name = new JTextField();
		name.setBounds(WIDTH / 8, 0, 7 * WIDTH / 8, HEIGHT / 16);
		add(name);

		forFont = new JLabel("Font:");
		forFont.setOpaque(true);
		forFont.setHorizontalAlignment(SwingConstants.CENTER);
		forFont.setBounds(0, HEIGHT / 16, WIDTH / 8, HEIGHT / 16);
		add(forFont);

		font = new JComboBox<>();
		for (Font f : Environment.getAllFonts())
			font.addItem(f.getName());
		font.setBounds(WIDTH / 8, HEIGHT / 16, 3 * WIDTH / 8, HEIGHT / 16);
		add(font);

		forSize = new JLabel("Size:");
		forSize.setOpaque(true);
		forSize.setHorizontalAlignment(SwingConstants.CENTER);
		forSize.setBounds(WIDTH / 2, HEIGHT / 16, WIDTH / 8, HEIGHT / 16);
		add(forSize);

		size = new JSpinner(new SpinnerNumberModel(16, 1, 100, 1));
		size.setBounds(5 * WIDTH / 8, HEIGHT / 16, 3 * WIDTH / 8, HEIGHT / 16);
		add(size);

		forCopy = new JLabel("Copyright:");
		forCopy.setOpaque(true);
		forCopy.setHorizontalAlignment(SwingConstants.CENTER);
		forCopy.setBounds(0, 2 * HEIGHT / 16, WIDTH / 8, HEIGHT / 16);
		add(forCopy);

		copy = new JTextField();
		copy.setBounds(WIDTH / 8, 2 * HEIGHT / 16, 7 * WIDTH / 8, HEIGHT / 16);
		add(copy);

		sections = new JTabbedPane();
		sections.setBounds(0, 3 * HEIGHT / 16, WIDTH, 12 * HEIGHT / 16);
		cmp = new CharMapPanel(parent);
		sections.addTab("Character Mapping", cmp);
		dp = new DictionaryPanel();
		sections.addTab("Dictionary", dp);
		add(sections);

		save = new JButton("Save");
		save.addActionListener(e -> saveLanguage());
		save.setBounds(0, 15 * HEIGHT / 16, WIDTH, HEIGHT / 16);
		add(save);
	}

	public CreatorPanel(JDialog parent, String[] lines) {
		this(parent);

		for (String line : lines) {
			String[] data = CSV.parseLine(line);

			if (data.length == 2)
				cmp.addSet(data);
		}

		cmp.updateTable();
	}

	private void saveLanguage() {
		cmp.updateSets();

		forName.setForeground(Color.BLACK);
		forCopy.setForeground(Color.BLACK);

		if (name.getText().isEmpty()) {
			forName.setForeground(Color.RED);
			displaySuccess("Language needs a name...", false);
		}
		else if (copy.getText().isEmpty()) {
			forCopy.setForeground(Color.RED);
			displaySuccess("Language needs a copyright...", false);
		}
		else if (cmp.getSets() == null) {
			displaySuccess("Language needs at least one char map entry...", false);
		}
		else {
			try {
				Document xml = XMLIO.create();

				Comment copyright = xml.createComment(copy.getText());
				xml.appendChild(copyright);

				Element lang = xml.createElement("language");
				lang.setAttribute("name", name.getText());
				lang.setAttribute("font", (String) font.getSelectedItem());
				lang.setAttribute("size", String.valueOf(size.getValue()));

				Element map = xml.createElement("map");

				String[][] sets = cmp.getSets();
				for (String[] s : sets) {
					if (s == null)
						break;

					Element set = xml.createElement("set");
					set.setAttribute("key", s[0]);
					set.setAttribute("val", s[1]);
					map.appendChild(set);
				}

				lang.appendChild(map);

				Element dict = xml.createElement("dict");
				lang.appendChild(dict);

				xml.appendChild(lang);

				XMLIO.write(xml,
					FileSystem.getLangs().getAbsolutePath() + File.separator + name.getText().toLowerCase() + ".xml");

				displaySuccess("Language Saved!", true);
			}
			catch (Exception e) {
				e.printStackTrace();

				displaySuccess("Failed to Save Language...", false);
			}
		}
	}

	private void displaySuccess(String message, boolean succeeded) {
		save.setText(message);
		save.setForeground(succeeded ? Color.GREEN : Color.RED);

		new Thread(() -> {
			try {
				Thread.sleep(3000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}

			save.setForeground(Color.WHITE);
			save.setText("Save");
		}).start();
	}

	private class CharMapPanel extends JPanel {
		private static final long serialVersionUID = -4338602365705038310L;

		private JPanel options;

		private JTable table;

		private JButton add, remove, clear;

		private JScrollPane scroll;

		private final String[] NAMES = {
			"Sequence", "Replace With"
		};

		private List<String[]> sets = new ArrayList<>();

		public CharMapPanel(JDialog parent) {
			super(new BorderLayout());

			options = new JPanel(new GridLayout(1, 3));

			add = new JButton("Add Row");
			add.addActionListener(e -> {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				if (table.getSelectedRows().length > 0)
					for (int row : table.getSelectedRows())
						model.insertRow(row + 1, new String[] {
							null, null
						});
				else
					model.addRow(new String[] {
						null, null
					});
			});
			options.add(add);

			remove = new JButton("Remove Row");
			remove.addActionListener(e -> {
				DefaultTableModel model = (DefaultTableModel) table.getModel();

				if (model.getRowCount() == 0)
					return;

				if (table.getSelectedRows().length > 0)
					for (int i = table.getSelectedRows().length - 1; i >= 0; i--)
						model.removeRow(table.getSelectedRows()[i]);
				else
					model.removeRow(model.getRowCount() - 1);
			});
			options.add(remove);

			clear = new JButton("Clear Rows");
			clear.addActionListener(e -> {
				sets.clear();

				updateTable();
			});
			options.add(clear);

			add(options, BorderLayout.NORTH);

			DefaultTableModel model = new DefaultTableModel(NAMES, 1);

			table = new JTable(model);
			table.setRowHeight(CreatorPanel.HEIGHT / 16);
			table.setFillsViewportHeight(true);

			scroll = new JScrollPane();
			scroll.getViewport().setView(table);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			add(scroll, BorderLayout.CENTER);
		}

		public void addSet(String[] set) {
			sets.add(set);
		}

		public String[][] getSets() {
			if (sets.isEmpty())
				return null;
			return sets.toArray(new String[sets.get(0).length][sets.size()]);
		}

		public void updateSets() {
			sets.clear();

			DefaultTableModel model = (DefaultTableModel) table.getModel();

			for (int i = 0; i < model.getRowCount(); i++) {
				String l = (String) model.getValueAt(i, 0);
				String r = (String) model.getValueAt(i, 1);

				if (l != null && !l.isEmpty() && r != null && !r.isEmpty())
					sets.add(new String[] {
						l, r
					});
			}
		}

		public void updateTable() {
			table.removeAll();

			String[][] sets = getSets();

			DefaultTableModel model;

			if (sets == null)
				model = new DefaultTableModel(NAMES, 0);
			else
				model = new DefaultTableModel(sets, NAMES);

			if (model.getRowCount() == 0 || (String) (model.getValueAt(model.getRowCount() - 1, 0)) != null)
				model.addRow(new String[] {
					null, null
				});

			table.setModel(model);

			table.revalidate();
		}
	}

	private class DictionaryPanel extends JPanel {
		private static final long serialVersionUID = 1104877551712714469L;

	}
}