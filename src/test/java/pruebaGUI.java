import org.mockito.Mockito;

import businessLogic.BLFacade;
import gui.MainGUI;

public class pruebaGUI {
	static BLFacade appFacadeInterface = Mockito.mock(BLFacade.class);
	public static void main(String args[]) {
	Mockito.doReturn(true).when(appFacadeInterface).isRegistered("a","a");
	//No importa el parametro, siempre devuelve driver
	Mockito.doReturn("Driver").when(appFacadeInterface).getMotaByUsername (Mockito.anyString());
	MainGUI a = new MainGUI(); 
	MainGUI.setBussinessLogic(appFacadeInterface);
	a.setVisible(true);
	}
}
