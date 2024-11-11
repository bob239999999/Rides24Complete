package gui;

import java.util.Locale;
import javax.swing.UIManager;
import configuration.ConfigXML;
import businessLogic.BLFacade;
import businessLogic.BLFactory;

public class ApplicationLauncher {
    public static void main(String[] args) {
        ConfigXML c = ConfigXML.getInstance();
        System.out.println(c.getLocale());
        Locale.setDefault(new Locale(c.getLocale()));
        
        System.out.println("Locale: " + Locale.getDefault());
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            
            // Usando la nueva Factory
            BLFactory factory = new BLFactory();
            BLFacade appFacadeInterface = factory.getBusinessLogicFactory(c.isBusinessLogicLocal());
            
            if (appFacadeInterface == null) {
                throw new Exception("Business logic interface is null");
            }
            
            MainGUI.setBussinessLogic(appFacadeInterface);
            MainGUI a = new MainGUI();
            a.setVisible(true);
            
        } catch (Exception e) {
            System.out.println("Error in ApplicationLauncher: " + e.toString());
        }
    }
}



