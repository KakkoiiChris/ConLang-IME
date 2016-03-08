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
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author Christian Bryce Alexander
 * @since Mar 7, 2016, 8:49:25 PM
 */
public class CharMapPanel extends JPanel {
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