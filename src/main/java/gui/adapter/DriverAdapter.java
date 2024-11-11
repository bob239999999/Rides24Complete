package gui.adapter;

import javax.swing.table.AbstractTableModel;
import domain.Driver;
import domain.Ride;

import java.sql.Date;
import java.util.List;

public class DriverAdapter extends AbstractTableModel {
    private Driver driver;
    private String[] columnNames = {"from", "to", "date", "places", "price"};
    private List<Ride> rides;
    
    public DriverAdapter(Driver driver) {
        this.driver = driver;
        this.rides = driver.getCreatedRides();
    }
    
    @Override
    public int getRowCount() {
        return rides.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        Ride ride = rides.get(row);
        switch (col) {
            case 0: return ride.getFrom();
            case 1: return ride.getTo();
            case 2: return ride.getDate();
            case 3: return ride.getnPlaces(); // Corrección: usar getnPlaces()
            case 4: return ride.getPrice();
            default: return null;
        }
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 2: return Date.class;
            case 3: return Integer.class;
            case 4: return Double.class; // Corrección: price es double
            default: return String.class;
        }
    }
}