package gui;

import javax.swing.*;
import java.awt.*;
import domain.Driver;
import gui.adapter.DriverAdapter;

public class DriverTable extends JFrame {
    private Driver driver;
    private JTable tabla;
    
    public DriverTable(Driver driver) {
        super(driver.getUsername() + "'s rides");
        this.setBounds(100, 100, 700, 200);
        this.driver = driver;
        
        DriverAdapter adapt = new DriverAdapter(driver);
        tabla = new JTable(adapt);
        tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}