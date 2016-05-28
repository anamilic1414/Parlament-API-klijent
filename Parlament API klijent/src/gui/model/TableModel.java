package gui.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import poslanik.Poslanik;

public class TableModel extends AbstractTableModel {

	private final String[] kolone = new String[] { "ID", "Name", "Last name", "Birth date" };
	private List<Poslanik> poslanik;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

	public TableModel(List<Poslanik> poslanik) {
		if (poslanik != null)
			this.poslanik = poslanik;
		else
			this.poslanik = new LinkedList<>();
	}

	public TableModel() {
		this.poslanik = new LinkedList<>();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		if (poslanik == null)
			return 0;
		return poslanik.size();
	}

	
	public Poslanik vrednostURedu(int rowIndex){
		return poslanik.get(rowIndex);
	}
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Poslanik p = poslanik.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getId();
		case 1:
			return p.getIme();
		case 2:
			return p.getPrezime();
		case 3:
			return p.getDatumRodjenja() != null ? sdf.format(p.getDatumRodjenja()) : "Nepoznat";
		default:
			return "NN";
		}
	}

	@Override
	public String getColumnName(int column) {
		return kolone[column];
	}

	public void ucitajTabelu(List<Poslanik> poslanik) {
		this.poslanik = poslanik;
		fireTableDataChanged();
	}

	public List<Poslanik> vratiPoslanike() {
		return poslanik;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (getColumnName(columnIndex) == "ID")
			return false;
		return true;
	}

	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Poslanik p = poslanik.get(rowIndex);
		String vrednost = (String) aValue;
		
		
		if(columnIndex == 1){
			p.setIme(vrednost);
		} else 
			if(columnIndex == 2){ 
				p.setPrezime(vrednost);
		} else 
			if(columnIndex == 3){
				try {
					p.setDatumRodjenja(sdf.parse(vrednost));
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null, "Datum mora biti u formatu dd.MM.yyyy.", "Greška!", JOptionPane.ERROR_MESSAGE);
				}
		}

	}
}
