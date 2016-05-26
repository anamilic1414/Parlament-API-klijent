package gui.model;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import poslanik.Poslanik;

public class TableModel extends AbstractTableModel{

	private final String[] kolone = new String[] {"ID", "Name", "Last name", "Birth date"};
	private List<Poslanik> poslanik;
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
	public TableModel(List<Poslanik> poslanik) {
		if(poslanik!= null)
			this.poslanik = poslanik;
		else 
			this.poslanik = new LinkedList<>();
	}

	@Override
	public int getColumnCount() {
		return kolone.length;
	}

	@Override
	public int getRowCount() {
		if(poslanik == null)
			return 0;
		return poslanik.size();
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
			return p.getDatumRodjenja() != null ? sdf.format(p.getDatumRodjenja()) : "Unknown";
		default:
			return "NN";
		}
	}
	
	@Override
	public String getColumnName(int column) {
		return kolone[column];
	}
	
	
	
}
