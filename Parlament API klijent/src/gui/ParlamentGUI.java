package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import gui.model.TableModel;
import poslanik.Poslanik;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ParlamentGUI extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JButton btnPoslanici;
	private JButton btnFillTable;
	private JButton btnUpdateMembers;
	private JPanel panel_1;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JScrollPane scrollPane_1;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public ParlamentGUI() {
		setTitle("Parlament Members");
		setForeground(new Color(219, 112, 147));
		setBackground(new Color(220, 20, 60));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 326);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.EAST);
		contentPane.add(getPanel_1(), BorderLayout.SOUTH);
		contentPane.add(getScrollPane_1(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(140, 10));
			panel.add(getBtnPoslanici());
			panel.add(getBtnFillTable());
			panel.add(getBtnUpdateMembers());
		}
		return panel;
	}
	private JButton getBtnPoslanici() {
		if (btnPoslanici == null) {
			btnPoslanici = new JButton("GET members");
			btnPoslanici.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIKontroler.vratiUJson();
				}
			});
			btnPoslanici.setPreferredSize(new Dimension(135, 25));
		}
		return btnPoslanici;
	}
	private JButton getBtnFillTable() {
		if (btnFillTable == null) {
			btnFillTable = new JButton("Fill table");
			btnFillTable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GUIKontroler.napuniTabelu();
				}
			});
			btnFillTable.setPreferredSize(new Dimension(135, 25));
		}
		return btnFillTable;
	}
	private JButton getBtnUpdateMembers() {
		if (btnUpdateMembers == null) {
			btnUpdateMembers = new JButton("Update members");
			btnUpdateMembers.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GUIKontroler.izTabeleUListu(table);
					GUIKontroler.update(GUIKontroler.vratiPoslanike());
				}
			});
			btnUpdateMembers.setPreferredSize(new Dimension(135, 25));
		}
		return btnUpdateMembers;
	}
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, "STATUS", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_1.setPreferredSize(new Dimension(15, 60));
			panel_1.setLayout(new BorderLayout(0, 0));
			panel_1.add(getScrollPane(), BorderLayout.CENTER);
		}
		return panel_1;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTextArea());
		}
		return scrollPane;
	}
	public JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
		}
		return textArea;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPane_1 == null) {
			scrollPane_1 = new JScrollPane();
			scrollPane_1.setViewportView(getTable());
		}
		return scrollPane_1;
	}
	public JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new gui.model.TableModel(GUIKontroler.vratiPoslanike()));
			table.setColumnSelectionAllowed(true);
			table.setCellSelectionEnabled(true);
			table.setFillsViewportHeight(true);
			
		}
		return table;
	}

	public void osveziTabelu() {
		TableModel model = (TableModel) table.getModel();
		model.ucitajTabelu(GUIKontroler.vratiPoslanike());
		
	}
	
	
}
