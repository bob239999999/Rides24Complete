package gui;

import businessLogic.BLFacade;
import businessLogic.BLFactory;
import domain.Driver;

public class Main {
    public static void main(String[] args) {
        boolean isLocal = true;
        BLFacade blFacade = new BLFactory().getBusinessLogicFactory(isLocal);
        Driver d = blFacade.getDriver("Urtzi");
        DriverTable dt = new DriverTable(d);
        dt.setVisible(true);
    }
}
